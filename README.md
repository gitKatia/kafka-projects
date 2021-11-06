# kafka-projects
Multi-module project of kafka consumers and producers using Spring boot
# Instructions
## Start a Kafka instance including one Zookeeper and one broker
### head to the Kafka bin directory and execute the following commands:

./kafka-zookeeper-server-start.sh ../config/zookeeper.properties

./kafka-server-start.sh ../config/server.properties
## Create a topic named first_topic
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic first_topic --partitions 1 --replication-factor 1
## List all topics
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --list
## Describe the topic
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --topic first_topic --describe
## Create a topic named second_topic
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic second_topic --partitions 1 --replication-factor 1
## Create a topic named third_topic having 3 partitions
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic third_topic --partitions 3 --replication-factor 1
## Describe the topic
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --topic third_topic --describe
## Create a topic named user_topic
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic user_topic --partitions 1 --replication-factor 1
## Create a topic named product_topic
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic product_topic --partitions 1 --replication-factor 1
## We have two consumer groups for this topic: p-analytics-cg and p-dashboard-cg
## We can describe the consumer groups using the commands below:
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group p-analytics-cg --describe

./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group p-dashboard-cg --describe
## Create a topic named rebalanced_topic
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic rebalanced_topic --partitions 1 --replication-factor 1
## Add partition to the topic rebalanced_topic while consumer and producer are running
./kafka-topics.sh --bootstrap-server localhost:9092 --alter --topic rebalanced_topic --partitions 2
## Create a topic named person_topic
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic person_topic --partitions 1 --replication-factor 1
## Create a topic named item_order_topic
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic item_order_topic --partitions 1 --replication-factor 1
# Global error handler: kafka-publication-consumer and kafka-publication-producer
## Create a topic named article_topic
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic article_topic --partitions 1 --replication-factor 1
## Create a topic named book_topic
### head to the Kafka bin directory and execute the following command:
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic book_topic --partitions 1 --replication-factor 1
# Other useful scripts
## Create a topic named my_topic with one single partition
./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic my_topic --partitions 1 --replication-factor 1
## Create a console consumer for a topic named my_topic
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic my_topic --offset earliest --partition 0
## Reset the offset of consumer group named my_consumer_group for the topic partition 0
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my_consumer_group --execute --reset-offsets --to-offset 10 --topic my_topic:0
## Reset the offset of consumer group named my_consumer_group for all the topic partitions
./kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my_consumer_group --execute --reset-offsets --to-offset 10 --topic my_topic
