package pt.up.fe.socialcrowd.managers;

import com.quickblox.module.users.model.QBUser;

public abstract class DataHolder {

    private static QBUser signInQbUser;

    public static void setSignInQbUser(QBUser singInQbUser) {
        signInQbUser = singInQbUser;
    }

    public static QBUser getSignInQbUser() {
        return signInQbUser;
    }
}
