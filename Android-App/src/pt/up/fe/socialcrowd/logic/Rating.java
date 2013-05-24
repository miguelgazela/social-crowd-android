package pt.up.fe.sdis.socialcrowd.logic;
import org.json.JSONException;
import org.json.JSONObject;


public class Rating {

	private int user_id;
	private int id;
	private int value;
	/**
	 * @param user_id
	 * @param id
	 * @param value
	 */
	public Rating(int user_id, int id, int value) {
		this.user_id = user_id;
		this.id = id;
		this.value = value;
	}
	
	public static Rating parseJSON(JSONObject json) throws JSONException {
		return new Rating(json.getInt("social_user"),json.getInt("id"), json.getInt("value"));
	}
	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

}
