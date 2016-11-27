__author__ = 'Jingmei'
import tweepy
import json,sys
from kafka import KafkaProducer

# Twitter Information
API_KEY = ""
API_SECRET =  ""
ACCESS_TOKEN = ""
ACCESS_TOKEN_SECRET = ""


class StdOutListener(tweepy.StreamListener):
    """ A listener handles tweets that are received from the stream.
    This is a basic listener that just prints received tweets to stdout.
    """
    def on_status(self, status):
        if status.coordinates:
            doc = {
                'text': status.text,
                'user': status.user.screen_name,
                #'time': str(status.created_at),
                #status.geo lat+long
                #status.coordinates long+lat
                'geo': str(status.geo['coordinates'][0])+','+str(status.geo['coordinates'][1])
            }
            print status.geo
            producer.send('trend', key='hi', value=json.dumps(doc))
    def on_error(self, status):
        print status 

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding("utf-8")

    producer = KafkaProducer(bootstrap_servers='localhost:9092')

    auth = tweepy.OAuthHandler(API_KEY, API_SECRET)
    auth.set_access_token(ACCESS_TOKEN, ACCESS_TOKEN_SECRET)
    api = tweepy.API(auth)

    stream = tweepy.streaming.Stream(auth, StdOutListener())
    stream.filter(track=['Love','Food','Trump','Travel','New York','Thanksgiving','Hillary','Fashion','LOL','Vegas'],languages=['en']) 


