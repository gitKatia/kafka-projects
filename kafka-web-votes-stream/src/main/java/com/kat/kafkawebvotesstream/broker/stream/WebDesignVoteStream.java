package com.kat.kafkawebvotesstream.broker.stream;

import com.kat.kafkawebvotesstream.config.TopicsProperties;
import com.kat.ordersmodel.InventoryMessage;
import com.kat.ordersmodel.WebColorVoteMessage;
import com.kat.ordersmodel.WebDesignVoteMessage;
import com.kat.ordersmodel.WebLayoutVoteMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;


@Configuration
@RequiredArgsConstructor
public class WebDesignVoteStream {

	private final TopicsProperties topicsProperties;

	@Bean
	public KStream<String, WebDesignVoteMessage> kStreamWebDesignVote(StreamsBuilder builder) {

		// Serialization and deserialization
		Serde<String> stringSerDe = Serdes.String();
		Serde<WebColorVoteMessage> colorSerDe = new JsonSerde<>(WebColorVoteMessage.class);
		Serde<WebLayoutVoteMessage> layoutSerDe = new JsonSerde<>(WebLayoutVoteMessage.class);
		Serde<WebDesignVoteMessage> designSerDe = new JsonSerde<>(WebDesignVoteMessage.class);

		// Color stream
		KStream<String, String> webColorStream = builder.stream(topicsProperties.getWebColorVoteTopic(),
				Consumed.with(stringSerDe, colorSerDe, new WebColorVoteTimestampExtractor(), null))
				.mapValues(v -> v.getColor());
		webColorStream.print(Printed.<String, String>toSysOut().withLabel("Web User-Color Stream"));

		// User and color table
		KTable<String, String> colorTable = webColorStream.toTable();

		// Layout stream
		KStream<String, String> webLayoutStream = builder.stream(topicsProperties.getWebLayoutVoteTopic(),
				Consumed.with(stringSerDe, layoutSerDe, new WebLayoutVoteTimestampExtractor(), null))
				.mapValues(v -> v.getLayout());
		webLayoutStream.print(Printed.<String, String>toSysOut().withLabel("Web User-Layout Stream"));

		// User and layout table
		KTable<String, String> layoutTable = webLayoutStream.toTable();

		// join of two tables
		KTable<String, WebDesignVoteMessage> joinTable = colorTable.join(layoutTable, this::voteJoiner, Materialized.with(stringSerDe, designSerDe));
		KStream<String, WebDesignVoteMessage> webDesignStream = joinTable.toStream();
		webDesignStream.print(Printed.<String, WebDesignVoteMessage>toSysOut().withLabel("Web Design Stream"));
		webDesignStream.to(topicsProperties.getWebColorLayoutTopic());

		// leftJoin and OuterJoin are also supported

		// vote result
		joinTable.groupBy((username, votedDesign) -> KeyValue.pair(votedDesign.getColor(), votedDesign.getColor()))
				.count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Color Votes Stream"));

		joinTable.groupBy((username, votedDesign) -> KeyValue.pair(votedDesign.getLayout(), votedDesign.getLayout()))
				.count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Layout Votes Stream"));

		return joinTable.toStream();
	}

	private WebDesignVoteMessage voteJoiner(String color, String layout) {
		return WebDesignVoteMessage.builder()
				.color(color)
				.layout(layout)
				.build();
	}
}
