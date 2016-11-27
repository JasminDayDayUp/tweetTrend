import java.util.Date;
import com.google.gson.Gson;

// The structure of a tweet

public class Tweet {

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
		Gson gson = new Gson();
}
