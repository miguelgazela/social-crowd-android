package pt.up.fe.socialcrowd.API;

public class RequestException extends Throwable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2986727414090581254L;
	private String message;

	public RequestException(String msg) {
		setMessage(msg);
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
