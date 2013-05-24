package pt.up.fe.socialcrowd.activities;

import com.quickblox.core.QBCallback;
import com.quickblox.core.result.Result;
import com.quickblox.module.users.result.QBUserResult;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.definitions.QBQueries;
import pt.up.fe.socialcrowd.managers.QBManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity which displays a signup screen to the user, offering registration in the app
 */
public class SignupActivity extends Activity {

	EditText username;
    EditText password;
    ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		setupActionBar();
		initialize();
	}

	private void initialize() {
//		username = (EditText) findViewById(R.id.signup_username);
//		password = (EditText) findViewById(R.id.signup_password);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(getResources().getString(R.string.please_wait));
	}
	
	public void onClickBtn(View v) {
        switch (v.getId()) {
            case R.id.signup_btn:
                progressDialog.show();
                // call QB Signup query
                QBManager.signUpUser(username.getText().toString(), password.getText().toString(), new QBCallback() {
					
					@Override 
					public void onComplete(Result result, Object query) {
						QBQueries qbQueryType = (QBQueries) query;
						
						if (result.isSuccess()) {
							switch (qbQueryType) {
							case QB_QUERY_SIGN_UP_USER:
								
								//Result for QBUsers.signUp() ----> QBUserResult
								System.out.println(result);
								// after You sign up user, You must sign in by this user
								//QBManager.singIn(DataHolder.getDataHolder().getLastQBUser().getLogin(), password.getText().toString(), this, QBQueries.QB_QUERY_SIGN_IN_QB_USER);
								break;
							default:
								break;
							}
						} else {
							// print errors that came from server
							Toast.makeText(getBaseContext(), result.getErrors().get(0), Toast.LENGTH_SHORT).show();
						}
						progressDialog.hide();
					}
					
					@Override public void onComplete(Result result) {}
				}, QBQueries.QB_QUERY_SIGN_UP_USER);
                break;
        }
    }

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
}
