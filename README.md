# tweetTrend
A Java Web App that does geo-location clustering and sentiment analysis of real time tweets, based on TwittMap, adding Amazon SQS service, Amazon SNS service, apache Kafka.
# Contributor
Jingmei Zhao(jz2685), Jingtao Zhu(jz2664),
# Website for demo
http://35.164.101.75:5000/


# Features
1. Deploy kafka and zookeeper in AWS EC2, consume the Twitter Streaming API and send all messages to a Kafka broker.
2. Display twitts' location on map with predefined keywords search ('trump', 'food', 'NewYork', 'fashion',etc.)

# Techniques
1. Fetch data from twitter streaming API by twitter4j.
2. Use Amazon SQS as a queue service for asynchronous processing of the tweets with keywords. 
3. Use a workerthreadManager to spawns worker threads. Each worker thread reads a message from the queue.
4. Use ALCHEMY API for sentiment analysis of the real time by returning sentiment evaluation for the text of the submitted Tweet. Sentiments are segregated into Positive, Negative and are displayed as Markers on the google Map.
5. Use Amazon SNS to sends a notification that contains the tweet information to the User Interface, and to update the UI and display the processed sentiment about the tweet.
6. Index the tweets with sentiment into AWS elasticsearch.


