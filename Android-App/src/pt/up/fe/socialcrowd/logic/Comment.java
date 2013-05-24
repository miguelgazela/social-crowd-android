package sdis.socialcrowd.logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Comment {
	private String text;
	private Date date;
	private ArrayList<Upvote> upvotes;
	private ArrayList<Downvote> downvotes;
	private int id;
	private int author_id;
	private int eventid;

	/**
	 * @param id
	 * @param author_id
	 * @param text
	 * @param date
	 * @param upvotes
	 * @param downvotes
	 * @param eventid
	 */
	public Comment(int id, int author_id, int eventid, String text, Date date, ArrayList<Upvote> upvotes,
			ArrayList<Downvote> downvotes) {
		this.id = id;
		this.eventid = eventid;
		this.setAuthor_id(author_id);
		this.text = text;
		this.date = date;
		this.upvotes = upvotes;
		this.downvotes = downvotes;
	}

	public static Comment parseJSON(JSONObject json) throws JSONException, ParseException {
		JSONArray arrayupvotes = json.getJSONArray("upvotes");
		JSONArray arraydownvotes = json.getJSONArray("downvotes");
		
		ArrayList<Upvote> upvotes = new ArrayList<Upvote> ();
		ArrayList<Downvote> downvotes = new ArrayList<Downvote> ();
		for (int i = 0; i < arrayupvotes.length(); i++) {
			upvotes.add(Upvote.parseJSON(arrayupvotes.getJSONObject(i)));
		}
		for (int i = 0; i < arraydownvotes.length(); i++) {
			downvotes.add(Downvote.parseJSON(arraydownvotes.getJSONObject(i)));
		}
		return new Comment(json.getInt("id"),json.getInt("social_user"),json.getInt("eventid"), json.getString("input"),DateParser.parseString(json.getString("created_at")),upvotes,downvotes);
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
	 * @return the upvotes
	 */
	public ArrayList<Upvote> getUpvotes() {
		return upvotes;
	}

	/**
	 * @param upvotes the upvotes to set
	 */
	public void setUpvotes(ArrayList<Upvote> upvotes) {
		this.upvotes = upvotes;
	}

	/**
	 * @return the downvotes
	 */
	public ArrayList<Downvote> getDownvotes() {
		return downvotes;
	}

	/**
	 * @param downvotes the downvotes to set
	 */
	public void setDownvotes(ArrayList<Downvote> downvotes) {
		this.downvotes = downvotes;
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
}
