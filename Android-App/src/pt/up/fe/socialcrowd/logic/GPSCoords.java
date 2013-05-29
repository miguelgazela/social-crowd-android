package pt.up.fe.socialcrowd.logic;

import org.json.JSONException;
import org.json.JSONObject;

public class GPSCoords {
	private double longitude;
	private double latitude;
	/**
	 * @param longitude
	 * @param latitude
	 */
	public GPSCoords(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public static GPSCoords parseJSON(JSONObject json) throws JSONException {
		return new GPSCoords(json.getDouble("longitude"),json.getDouble("latitude"));
	}
	
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
