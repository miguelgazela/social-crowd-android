package pt.up.fe.socialcrowd.logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.up.fe.socialcrowd.API.RequestException;

public class DetailedEvent extends BaseEvent {

	private ArrayList<Subscription> subscriptions;
	private ArrayList<Comment> comments;
	private ArrayList<Rating> ratings;
	/**
	 * @param type
	 * @param id
	 * @param name
	 * @param description
	 * @param start_date
	 * @param end_date
	 * @param location
	 * @param tags
	 * @param category
	 * @param rating
	 * @param subscriptions
	 * @param comments
	 */
	public DetailedEvent(int type, int id, int author_id, String name, String description,
			Date start_date, Date end_date, Location location,
			ArrayList<String> tags, String category, double rating,
			ArrayList<Subscription> subscriptions, ArrayList<Comment> comments, ArrayList<Rating> ratings) {
		super(type, id, author_id, name, description, start_date, end_date, location,
				tags, category, rating);
		this.subscriptions = subscriptions;
		this.comments = comments;
		this.ratings = ratings;
	}
	
	public static DetailedEvent parseJSON(JSONObject data) throws JSONException, RequestException, ParseException {
		int type;
		 String strtype = data.getString("type");
		 if (strtype.equals("public"))
			 type = BaseEvent.PUBLIC;
		 else if (strtype.equals("private"))
			 type = BaseEvent.PRIVATE;
		 else if (strtype.equals("geolocated"))
			 type = BaseEvent.GEOLOCATED;
		 else
			 throw new RequestException("invalid event type");
		 
		 JSONArray arraytags = data.getJSONArray("tags");
		 ArrayList<String> tags = new ArrayList<String>();
		 for (int i = 0; i < arraytags.length(); i++) {
			 tags.add(arraytags.getString(i));
		 }
		 
		 JSONArray arraysubscriptions = data.getJSONArray("subscriptions");
		 ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();
		 for (int i = 0; i < arraysubscriptions.length(); i++) {
			 subscriptions.add(Subscription.parseJSON(arraysubscriptions.getJSONObject(i)));
		 }
		 
		 JSONArray arraycomments = data.getJSONArray("comments");
		 ArrayList<Comment> comments = new ArrayList<Comment>();
		 for (int i = 0; i < arraycomments.length(); i++) {
			 comments.add(Comment.parseJSON(arraycomments.getJSONObject(i)));
		 }

		 JSONArray arrayratings = data.getJSONArray("ratings");
		 ArrayList<Rating> ratings = new ArrayList<Rating>();
		 for (int i = 0; i < arrayratings.length(); i++) {
			 ratings.add(Rating.parseJSON(arrayratings.getJSONObject(i)));
		 }

		 return new DetailedEvent(type, data.getInt("id"), data.getInt("author_id"), data.getString("name"),
				 data.getString("description"), DateParser.parseString(data.getString("start_date")), 
				 DateParser.parseString(data.getString("end_date")), Location.parseJSON(data.getJSONObject("location")), 
				 tags, data.getString("category"), data.getDouble("rating"), subscriptions, comments, ratings);
	}
	
	/**
	 * @return the subscriptions
	 */
	public ArrayList<Subscription> getSubscriptions() {
		return subscriptions;
	}
	/**
	 * @param subscriptions the subscriptions to set
	 */
	public void setSubscriptions(ArrayList<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
	/**
	 * @return the comments
	 */
	public ArrayList<Comment> getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * @return the ratings
	 */
	public ArrayList<Rating> getRatings() {
		return ratings;
	}

	/**
	 * @param ratings the ratings to set
	 */
	public void setRatings(ArrayList<Rating> ratings) {
		this.ratings = ratings;
	}
}
