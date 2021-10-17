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

