package pt.up.fe.sdis.socialcrowd.logic;
import org.json.JSONException;
import org.json.JSONObject;


public class Downvote extends Vote {

	public Downvote(int id, int user_id) {
		super(id, user_id);
	}

	@Override
	public int getScore() {
		return -1;
	}
	
	public static Downvote parseJSON(JSONObject json) throws JSONException {
		return new Downvote(json.getInt("id"), json.getInt("user_id"));
	}

}
