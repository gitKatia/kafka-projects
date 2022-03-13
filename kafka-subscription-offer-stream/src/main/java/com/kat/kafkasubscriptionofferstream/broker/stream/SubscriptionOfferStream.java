package com.kat.kafkasubscriptionofferstream.broker.stream;

import com.kat.kafkasubscriptionofferstream.config.TopicsProperties;
import com.kat.ordersmodel.InventoryMessage;
import com.kat.ordersmodel.SubscriptionOfferMessage;
import com.kat.ordersmodel.SubscriptionPurchaseMessage;
import com.kat.ordersmodel.UserSubscriptionMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;


@Configuration
@RequiredArgsConstructor
public class SubscriptionOfferStream {
	private final TopicsProperties topicsProperties;

	@Bean
	public KStream<String, SubscriptionOfferMessage> kStreamSubscriptionOffer(StreamsBuilder builder) {
		// Serializers and deserializers used by processors
		Serde<String> stringSerDe = Serdes.String();
		Serde<SubscriptionPurchaseMessage> purchaseSerDe = new JsonSerde<>(SubscriptionPurchaseMessage.class);
		Serde<UserSubscriptionMessage> userSerDe = new JsonSerde<>(UserSubscriptionMessage.class);
		Serde<SubscriptionOfferMessage> offerSerDe = new JsonSerde<>(SubscriptionOfferMessage.class);

		KStream<String, SubscriptionPurchaseMessage> purchaseStream = builder.stream(topicsProperties.getSubscriptionPurchaseTopic(),
				Consumed.with(stringSerDe, purchaseSerDe));
		purchaseStream.print(Printed.<String, SubscriptionPurchaseMessage>toSysOut().withLabel("Subscription Purchase Stream"));

		// Build the User table
		KTable<String, UserSubscriptionMessage> userTable = builder.table(topicsProperties.getUserSubscriptionTopic(), Consumed.with(stringSerDe, userSerDe));

		//In this case since there is no filtering we could use a globalKTable
		
		KStream<String, SubscriptionOfferMessage> offerStream = purchaseStream.join(userTable, this::joiner, Joined.with(stringSerDe, purchaseSerDe, userSerDe));
		offerStream.print(Printed.<String, SubscriptionOfferMessage>toSysOut().withLabel("Subscription Offer Stream Stream"));
		offerStream.to(topicsProperties.getSubscriptionOfferTopic(), Produced.with(stringSerDe, offerSerDe));

		return offerStream;
	}

	private SubscriptionOfferMessage joiner(SubscriptionPurchaseMessage purchaseSubscriptionMessage, UserSubscriptionMessage userSubscriptionMessage) {
		return SubscriptionOfferMessage.builder()
				.username(purchaseSubscriptionMessage.getUsername())
				.subscriptionNumber(purchaseSubscriptionMessage.getSubscriptionNumber())
				.duration(userSubscriptionMessage.getDuration())
				.build();
	}
}
