package pt.up.fe.socialcrowd.logic;
import org.json.JSONException;
import org.json.JSONObject;


public class Location {
	private String text;
	private GPSCoords gps;
	private int id;
	
	public Location(int id, String t, GPSCoords g) {
		setText(t);
		setGps(g);
	}
	
	public static Location parseJSON(JSONObject json) throws JSONException {
		return new Location(json.getInt("id"),json.getString("name"),GPSCoords.parseJSON(json.getJSONObject("gps")));
	}

	/**
	 * @return the gps
	 */
	public GPSCoords getGps() {
		return gps;
	}

	/**
	 * @param gps the gps to set
	 */
	public void setGps(GPSCoords gps) {
		this.gps = gps;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
}
