package sdis.socialcrowd.logic;

public class Session {
	private String session_id;
	
	public Session(String sid) {
	      setSession_id(sid);
	}

	/**
	 * @return the session_id
	 */
	public String getSession_id() {
		return session_id;
	}

	/**
	 * @param session_id the session_id to set
	 */
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
}
