package pt.up.fe.socialcrowd.logic;
import org.json.JSONException;
import org.json.JSONObject;


public class Upvote extends Vote {

	public Upvote(int id, int user_id) {
		super(id, user_id);
	}

	@Override
	public int getScore() {
		return 1;
	}
	
	public static Upvote parseJSON(JSONObject json) throws JSONException {
		return new Upvote(json.getInt("id"), json.getInt("social_user"));
	}

}
