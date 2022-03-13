package com.kat.kafkapremiumofferstream.broker.stream;

import com.kat.kafkapremiumofferstream.config.TopicsProperties;
import com.kat.ordersmodel.PremiumOfferMessage;
import com.kat.ordersmodel.PremiumPurchaseMessage;
import com.kat.ordersmodel.PremiumUserMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import static com.kat.kafkapremiumofferstream.util.StreamsUtil.isPremiumUser;


@Configuration
@RequiredArgsConstructor
public class PremiumOfferStream {
	private final TopicsProperties topicsProperties;

	@Bean
	public KStream<String, PremiumOfferMessage> kStreamPremiumOffer(StreamsBuilder builder) {
		// Used for serialization and deserialization
		Serde<String> stringSerDe = Serdes.String();
		Serde<PremiumPurchaseMessage> purchaseSerDe = new JsonSerde<>(PremiumPurchaseMessage.class);
		Serde<PremiumUserMessage> userSerDe = new JsonSerde<>(PremiumUserMessage.class);
		Serde<PremiumOfferMessage> offerSerDe = new JsonSerde<>(PremiumOfferMessage.class);

		// Here we change the Key to username for the record. The join is done by Key
		KStream<String, PremiumPurchaseMessage> purchaseStream = builder.stream(topicsProperties.getPremiumPurchaseTopic(), Consumed.with(stringSerDe, purchaseSerDe))
				.selectKey((k, v) -> v.getUsername());
		purchaseStream.print(Printed.<String, PremiumPurchaseMessage>toSysOut().withLabel("Premium Purchase Stream"));

		KTable<String, PremiumUserMessage> userTable = builder.table(topicsProperties.getPremiumUserTopic(),
				Consumed.with(stringSerDe, userSerDe))
				.filter((k, v) -> isPremiumUser(v));

		// join
		KStream<String, PremiumOfferMessage> offerStream = purchaseStream.join(userTable, this::joiner,
				Joined.with(stringSerDe, purchaseSerDe, userSerDe));

		offerStream.to(topicsProperties.getPremiumOfferTopic(), Produced.with(stringSerDe, offerSerDe));
		offerStream.print(Printed.<String, PremiumOfferMessage>toSysOut().withLabel("Premium Offer Stream"));

		return offerStream;
	}

	private PremiumOfferMessage joiner(PremiumPurchaseMessage premiumPurchaseMessage, PremiumUserMessage premiumPurchaseUser) {
		return PremiumOfferMessage.builder()
				.username(premiumPurchaseMessage.getUsername())
				.purchaseNumber(premiumPurchaseMessage.getPurchaseNumber())
				.level(premiumPurchaseUser.getLevel())
				.build();
	}
}
