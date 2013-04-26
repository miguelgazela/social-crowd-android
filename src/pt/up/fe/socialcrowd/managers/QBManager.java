package pt.up.fe.socialcrowd.managers;

import pt.up.fe.socialcrowd.definitions.QBQueries;

import com.quickblox.core.QBCallback;
import com.quickblox.module.users.QBUsers;
import com.quickblox.module.users.model.QBUser;

public class QBManager {

	static public void signUpUser(String username, String password, QBCallback callback, QBQueries context) {
        QBUser qbUser = new QBUser();
        qbUser.setLogin(username);
        qbUser.setPassword(password);
        QBUsers.signUp(qbUser, callback, context);
    }
}
