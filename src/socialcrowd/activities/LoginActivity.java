package socialcrowd.activities;

import com.quickblox.core.QBCallback;
import com.quickblox.core.result.Result;
import com.quickblox.module.users.result.QBUserResult;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.definitions.QBQueries;
import pt.up.fe.socialcrowd.managers.QBManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user
 */
public class LoginActivity extends Activity {
	EditText login;
	EditText password;
	ProgressDialog progressDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.activity_login);
		initialize();
	}
	
	private void initialize() {
		login = (EditText) findViewById(R.id.login_username);
		password = (EditText)findViewById(R.id.login_password);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(getResources().getString(R.string.please_wait));
	}
	
	public void onClickBtn(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			progressDialog.show();
			QBManager.signInUser(login.getText().toString(), password.getText().toString(), new QBCallback() {
				
				@Override 
				public void onComplete(Result result, Object query) {
					QBQueries qbQueryType = (QBQueries) query;
					
					if (result.isSuccess()) {
						switch (qbQueryType) {
						case QB_QUERY_SIGN_IN_USER:
							setResult(RESULT_OK);
							QBUserResult qbUserResult = (QBUserResult) result;
							Toast.makeText(getBaseContext(), getResources().getString(R.string.user_successfully_sign_in), Toast.LENGTH_LONG).show();
							finish();
							break;
						default:
							break;
						}
					} else { // print errors that came from server
						Toast.makeText(getBaseContext(), result.getErrors().get(0), Toast.LENGTH_SHORT).show();
					}
					progressDialog.hide();
				}
				
				@Override public void onComplete(Result result) {}
			}, QBQueries.QB_QUERY_SIGN_IN_USER);
			break;

		default:
			break;
		}
	}
}
