package sdis.socialcrowd.logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sdis.socialcrowd.API.RequestException;


public class BaseEvent {
	
	public static final int PUBLIC = 1;
	public static final int PRIVATE = 2;
	public static final int GEOLOCATED = 3;
	protected int type;
	protected int id;
	protected int author_id;
	protected String name;
	protected String description;
	protected Date start_date;
	protected Date end_date;
	protected Location location;
	protected ArrayList<String> tags;
	protected String category;
	protected double rating;
	
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
	 */
	public BaseEvent(int type, int id, int author_id, String name, String description,
			Date start_date, Date end_date, Location location,
			ArrayList<String> tags, String category, double rating) {
		this.type = type;
		this.id = id;
		this.author_id = author_id;
		this.name = name;
		this.description = description;
		this.start_date = start_date;
		this.end_date = end_date;
		this.location = location;
		this.tags = tags;
		this.category = category;
		this.rating = rating;
	}
	public static BaseEvent parseJSON(JSONObject data) throws JSONException, RequestException, ParseException {
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
		 
		 return new BaseEvent(type, data.getInt("id"), data.getInt("author_id"), data.getString("name"),
				 data.getString("description"), DateParser.parseString(data.getString("start_date")), 
				 DateParser.parseString(data.getString("end_date")), Location.parseJSON(data.getJSONObject("location")), 
				 tags, data.getString("category"), data.getDouble("rating"));
	}
	
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
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
	 * @return the author_id
	 */
	public int getAuthorId() {
		return author_id;
	}
	/**
	 * @param id the author_id to set
	 */
	public void setAuthorId(int id) {
		this.author_id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the start_date
	 */
	public Date getStart_date() {
		return start_date;
	}
	/**
	 * @param start_date the start_date to set
	 */
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	/**
	 * @return the end_date
	 */
	public Date getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date the end_date to set
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * @return the tags
	 */
	public ArrayList<String> getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
}
