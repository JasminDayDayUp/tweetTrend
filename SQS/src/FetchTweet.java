
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClient;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
//import java.util.*;
import com.google.gson.Gson;

public class FetchTweet {

	private static double latitude;
	private static double longtitude;
	private static int count = 0;
	private static String name;
	private static String place;
	private static String message;
	private static String id;
	private static String date;
	private static GeoLocation loc;

    	public static void main(String[] args) throws TwitterException {
    
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        	.setOAuthConsumerKey("******************")
   			.setOAuthConsumerSecret("******************")
   			.setOAuthAccessToken("******************")
   			.setOAuthAccessTokenSecret("******************");
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                if (status.getGeoLocation() != null && status.getUser() != null) {
                	loc = status.getGeoLocation();
                	latitude = status.getGeoLocation().getLatitude();
					longtitude = status.getGeoLocation().getLongitude();
					place = status.getPlace().getCountry() + ","
							+ status.getPlace().getFullName();
					date = status.getCreatedAt().toString();
					id = Integer.toString(++count);
					name = status.getUser().getScreenName();
					message = status.getText();

                    
					Tweet tw = new Tweet(name, date, message, longtitude, latitude);

					try {
						SQSWrapper.addMessage(message + "\n"			
					  + name +"\n" + date +"\n"  + longtitude + ","+ latitude +"\n");

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					
//                    String queueUrl = SQS.getQueueUrl(new GetQueueUrlRequest(SQS_QUEUE_NAME)).getQueueUrl();
//                    SQS.sendMessage(new SendMessageRequest(queueUrl, t.getId()));
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
               System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        twitterStream.addListener(listener);
        // Filter
        FilterQuery filtre = new FilterQuery();
        String[] keywordsArray = { "food", "trump","hillary","newyork" ,"love","job","fashion","lol","vegas"};
        filtre.track(keywordsArray);
        twitterStream.filter(filtre);
    }

//   
}
