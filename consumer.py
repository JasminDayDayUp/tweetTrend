__author__ = 'Jingmei'
from kafka import KafkaConsumer
from alchemyapi import AlchemyAPI
import boto3
import logging
import json

##SNS
TOPIC_NAME = 'cloud-assign2'
sns = boto3.resource('sns', 'us-east-1')
topic_arn=''
logging.basicConfig()  # http://stackoverflow.com/questions/27411778/no-handlers-found-for-logger-main
logger = logging.getLogger(__name__)
##sentiment
alchemyapi = AlchemyAPI()

# To consume latest messages and auto-commit offsets
consumer=KafkaConsumer('trend',
            group_id='my-group',
            bootstrap_servers=['localhost:9092'],
            consumer_timeout_ms=100000
            )

for message in consumer:
    # message value and key are raw bytes -- decode if necessary!
    # e.g., for unicode: `message.value.decode('utf-8')`
    msg=json.loads(str(message.value.decode('utf-8')))
    response = alchemyapi.sentiment("text", msg['text'])
    try:
        sentiment=response["docSentiment"]["type"]
        msg={
            "text": msg['text'],
            "user": msg['user'],
            #"time": msg['time'],
            "sentiment": sentiment,
            "geo": msg['geo']
        }
        # Publish a message
        response = sns.Topic(topic_arn).publish(
    	    Subject="test",
    	    Message=json.dumps(msg),
    	)
        #print "response={}".format(response)
        print 'receive'
    except:
        pass


