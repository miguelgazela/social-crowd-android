package pt.up.fe.socialcrowd.managers;

import pt.up.fe.socialcrowd.logic.Session;

public abstract class DataHolder {

	private static Session currentUserSession;
	
	public static void setCurrentUserSession(Session session) {
		currentUserSession = session;
	}
	
	public static Session getCurrentUserSession() {
		return currentUserSession;
	}
	
	public static void deleteCurrentUserSession() {
		currentUserSession = null;
	}
}
