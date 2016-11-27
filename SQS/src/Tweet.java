import java.util.Date;
import com.google.gson.Gson;
//import org.json.simple.JSONObject;

// The structure of a tweet

public class Tweet {
//	private BasicDBObject obj;
	private String userName;
	private String time;
	private String text;
	private double lng;
	private double lat;

	
	public Tweet(String userName, String time, String text, double lng, double lat) {
		this.userName = userName;
		this.time = time;
		this.text = text;
		this.lng = lng;
		this.lat = lat;

	}


	
//	public Tweet(JSONObject obj) {
//		if(obj != null) {
//			
//			this.userName = temp.getString("userName");
//			this.time = temp.getDate("time");
//			this.Id = temp.getLong("Id");
//			this.text = temp.getString("text");				
//			this.sentiment_type = temp.getString("sentiment_type");			
//			this.timestamp = Double.parseDouble(temp.getString("timestamp"));
//			
//			this.lat = Double.parseDouble(((DBObject)temp.get("location")).get("lat").toString());
//			this.lng = Double.parseDouble(((DBObject)temp.get("location")).get("lng").toString());
//			
//		}
//	}
//}


	
//	public String getSQSJson() {
//		message msg = new message();
	
		
		Gson gson = new Gson();
//		return gson.toJson();
//	}
	
}