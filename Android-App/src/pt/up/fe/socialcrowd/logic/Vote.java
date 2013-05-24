package pt.up.fe.socialcrowd.logic;

public abstract class Vote {
	public static final int UPVOTE = 1;
	public static final int DOWNVOTE = -1;
	private int user_id;
	private int id;
	/**
	 * @param user_id
	 * @param id
	 */
	public Vote(int id, int user_id) {
		this.user_id = user_id;
		this.setId(id);
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
	
	public abstract int getScore();
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
