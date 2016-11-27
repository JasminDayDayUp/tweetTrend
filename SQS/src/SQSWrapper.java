import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SQSWrapper {

	private static AmazonSQS sqs;
	private static String myQueueUrl;

	private static void createSQS() {
		AWSCredentials credentials = null;
		try {
			 credentials = new ProfileCredentialsProvider().getCredentials();
//			credentials = new PropertiesCredentials(
//					FetchTweet.class
//							.getResourceAsStream("AwsCredentials.properties"));
			sqs = new AmazonSQSClient(credentials);
			Region usWest2 = Region.getRegion(Regions.US_WEST_2);
			sqs.setRegion(usWest2);

		     System.out.println("===========================================");
		     System.out.println("Getting Started with Amazon SQS");
		     System.out.println("===========================================\n");
			
			String queueName = "TweetQueue";
			CreateQueueRequest createQueueRequest = new CreateQueueRequest(
					queueName);
			myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addMessage(String msg) {
		if (sqs == null || myQueueUrl.isEmpty()) {
			createSQS();
		}
		sqs.sendMessage(new SendMessageRequest(myQueueUrl, msg));
		System.out.println(msg);
	}
	
	
	public static Message retrieveMessage(){
		if (sqs == null || myQueueUrl.isEmpty()) {
			createSQS();
		}
		
		ReceiveMessageRequest request = new ReceiveMessageRequest(myQueueUrl);
		request.setMaxNumberOfMessages(1);
		
		ReceiveMessageResult result = sqs.receiveMessage(request);
		
		if(result.getMessages().size() > 0){
			return result.getMessages().get(0);			
		}
		
		return null;
	}
	
	
	public static void deleteMessage(Message message){
		if (sqs == null || myQueueUrl.isEmpty()) {
			createSQS();
		}
		
		DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(myQueueUrl, message.getReceiptHandle());			
		sqs.deleteMessage(deleteMessageRequest);
		
	}
}
