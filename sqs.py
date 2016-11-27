import boto3,json,logging
from alchemyapi import AlchemyAPI
from key2 import access_key,secret_key,zone

##queue
queue_name = 'TweetQueue'
max_queue_messages = 10
message_bodies = []
sqs = boto3.resource('sqs', region_name=zone,
        aws_access_key_id=access_key,
        aws_secret_access_key=secret_key)
queue = sqs.get_queue_by_name(QueueName=queue_name)

##SNS
TOPIC_NAME = 'cloud-assign2'
sns = boto3.resource('sns', 'us-east-1')
topic_arn='arn:aws:sns:us-east-1:756933836766:cloud-assign2'
logging.basicConfig()  # http://stackoverflow.com/questions/27411778/no-handlers-found-for-logger-main
logger = logging.getLogger(__name__)

##sentiment
alchemyapi = AlchemyAPI()

while True:
    #messages_to_delete = []
    for message in queue.receive_messages(MaxNumberOfMessages=max_queue_messages):
        try:
            # process message body
            text,user,date,geo,last=message.body.split('\n')
            print date
            #delete message, handle any errors
            messages_to_delete=[{'Id': message.message_id,
                'ReceiptHandle': message.receipt_handle}]
            delete_response = queue.delete_messages(
                    Entries=messages_to_delete)
            lon,lat=geo.split(',')
            #print date
            try:
                responsea = alchemyapi.sentiment("text", text)
                sentiment=responsea["docSentiment"]["type"]
                msg={
                "text": text,
                "user": user,
                "sentiment": sentiment,
                "geo": lat+','+lon
                }
                print msg
                # Publish a message
                response = sns.Topic(topic_arn).publish(
                    Subject="test",
                    Message=json.dumps(msg),
                )
                print "response={}".format(response)
            except:
                pass
        except:
                pass
        
    '''
        # add message to delete
        messages_to_delete.append({
            'Id': message.message_id,
            'ReceiptHandle': message.receipt_handle
        })

    # if you don't receive any notifications the
    # messages_to_delete list will be empty
    if len(messages_to_delete) == 0:
        break
    # delete messages to remove them from SQS queue
    # handle any errors
    else:
        delete_response = queue.delete_messages(
                Entries=messages_to_delete)
                '''