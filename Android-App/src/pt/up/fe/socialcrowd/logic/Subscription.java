package pt.up.fe.sdis.socialcrowd.logic;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;


public class Subscription {
	private int author_id;
	private int eventid;
	private int id;
	private Date date;
	/**
	 * @param author_id
	 * @param date
	 */
	public Subscription(int id, int eventid, int author_id, Date date) {
		this.author_id = author_id;
		this.id = id;
		this.eventid = eventid;
		this.date = date;
	}
	
	public static Subscription parseJSON(JSONObject json) throws JSONException, ParseException {
		return new Subscription(json.getInt("id"), json.getInt("eventid"), json.getInt("user_id"),DateParser.parseString(json.getString("created_at")));
	}
	
	/**
	 * @return the author_id
	 */
	public int getAuthor_id() {
		return author_id;
	}
	/**
	 * @param author_id the author_id to set
	 */
	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the eventid
	 */
	public int getEventid() {
		return eventid;
	}

	/**
	 * @param eventid the eventid to set
	 */
	public void setEventid(int eventid) {
		this.eventid = eventid;
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
