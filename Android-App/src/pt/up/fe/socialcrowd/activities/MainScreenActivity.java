package pt.up.fe.socialcrowd.activities;


import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.definitions.Consts;
import pt.up.fe.socialcrowd.definitions.QBQueries;
import pt.up.fe.socialcrowd.managers.QBManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quickblox.core.QBCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.result.Result;
import com.quickblox.module.auth.QBAuth;
import com.quickblox.module.users.result.QBUserResult;


public class MainScreenActivity extends Activity {
	
	private ProgressBar progressBar;
	private EditText login, password;
	private ProgressDialog progressDialog;

	@Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_mainscreen);
        
        // show progress bar while creating session for QB
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Initialize QuickBlox application with credentials.
        QBSettings.getInstance().fastConfigInit(Consts.APP_ID, Consts.AUTH_KEY, Consts.AUTH_SECRET);

        // Authorize application
        QBAuth.createSession(new QBCallback() {
        	@Override public void onComplete(Result result) {}
        	@Override
        	public void onComplete(Result result, Object context) {
        		QBQueries qbQueryType = (QBQueries) context;
        		if (result.isSuccess()) {
        			switch (qbQueryType) {
        			case QB_QUERY_AUTHORIZE_APP:
        				initializeMainScreen();
        				showMainScreen();
        				break;
        			}
        		} else {
        			// print errors that came from server
        			Toast.makeText(getBaseContext(), result.getErrors().get(0), Toast.LENGTH_SHORT).show();
        			progressBar.setVisibility(View.INVISIBLE);
        		}
        	}
        }, QBQueries.QB_QUERY_AUTHORIZE_APP);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		// destroy session after app close
		QBAuth.deleteSession(new QBCallback() {
			@Override public void onComplete(Result arg0, Object arg1) {}
			@Override public void onComplete(Result arg0) {}
		});
	}
	
	public void onClickBtn(View v) {
		Intent intent;
		
		switch (v.getId()) {
		case R.id.signin_btn:
		{	
			System.out.println("clicked sign in button");
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
							//finish();
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
		}
		case R.id.signup_btn:
		{
			System.out.println("Signup");
			intent = new Intent(this, SignupActivity.class);
			startActivity(intent);
			break;
		}
		default:
			break;
		}
	}
	
	private void initializeMainScreen() {
		login = (EditText) findViewById(R.id.signin_username);
		password = (EditText)findViewById(R.id.signin_password);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(getResources().getString(R.string.please_wait));
	}
	
	private void showMainScreen() {
		progressBar.setVisibility(View.INVISIBLE);
		System.out.println("Showing main screen");
		
		// making elements visible
		EditText editText;
		if((editText = (EditText) findViewById(R.id.signin_username)) != null) {
			editText.setVisibility(View.VISIBLE);
		}
		if((editText = (EditText) findViewById(R.id.signin_password)) != null) {
			editText.setVisibility(View.VISIBLE);
		}
		
		Button button;
		if((button = (Button) findViewById(R.id.signin_btn)) != null) {
			button.setVisibility(View.VISIBLE);
		}
	}
}
