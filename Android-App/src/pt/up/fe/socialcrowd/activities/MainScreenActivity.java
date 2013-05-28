package pt.up.fe.socialcrowd.activities;


import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.definitions.Consts;
import pt.up.fe.socialcrowd.definitions.QBQueries;
import pt.up.fe.socialcrowd.managers.DataHolder;
import pt.up.fe.socialcrowd.managers.QBManager;
import android.app.ProgressDialog;
import android.os.Bundle;
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

public class MainScreenActivity extends DashboardActivity {
	
	private ProgressBar progressBar;
	private EditText username, password;
	private ProgressDialog progressDialog;
	
	@Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_mainscreen);
        
        // show progress bar while creating session for QB
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Button btn = (Button)findViewById(R.id.signup_here_btn);
		if(btn != null) {
			btn.setClickable(false);
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				// Initialize QuickBlox application with credentials.
				QBSettings.getInstance().fastConfigInit(Consts.APP_ID, Consts.AUTH_KEY, Consts.AUTH_SECRET);
				
				// Authorize application
				
				QBAuth.createSession(new QBCallback() {
					@Override public void onComplete(Result result) {}
					@Override
					public void onComplete(Result result, Object context) {
						if (result.isSuccess()) {
							Button btn = (Button)findViewById(R.id.signup_here_btn);
							if(btn != null) {
								btn.setClickable(true);
							}
							System.out.println("DONE!");
//							showMainScreen();
						} else {
							// print errors that came from server
//							Toast.makeText(getBaseContext(), result.getErrors().get(0), Toast.LENGTH_SHORT).show();
//							progressBar.setVisibility(View.INVISIBLE);
						}

					}
				}, QBQueries.QB_QUERY_AUTHORIZE_APP);
			}
		}).start();
        
//        new AsyncTask<Void, Void, Void>() {
//
//			@Override
//			protected Void doInBackground(Void... params) {
//
//				// Initialize QuickBlox application with credentials.
//				QBSettings.getInstance().fastConfigInit(Consts.APP_ID, Consts.AUTH_KEY, Consts.AUTH_SECRET);
//				
//				// Authorize application
//				
//				QBAuth.createSession(new QBCallback() {
//					@Override public void onComplete(Result result) {}
//					@Override
//					public void onComplete(Result result, Object context) {
//						if (result.isSuccess()) {
//							Button btn = (Button)findViewById(R.id.signup_here_btn);
//							if(btn != null) {
//								btn.setClickable(true);
//							}
//							showMainScreen();
//						} else {
//							// print errors that came from server
//							Toast.makeText(getBaseContext(), result.getErrors().get(0), Toast.LENGTH_SHORT).show();
//							progressBar.setVisibility(View.INVISIBLE);
//						}
//
//					}
//				}, QBQueries.QB_QUERY_AUTHORIZE_APP);
//				return null;
//			}
//		}.execute();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void onClickSignIn(View v) {
		progressDialog.show();
		QBManager.signInUser(username.getText().toString(), password.getText().toString(), new SignInQBCallBack(), QBQueries.QB_QUERY_SIGN_IN_USER);
	}

	public void onClickSignUp(View v) {
		progressDialog.show();
		QBManager.signUpUser(username.getText().toString(), password.getText().toString(), new QBCallback() {

			@Override 
			public void onComplete(Result result, Object query) {
				QBQueries qbQueryType = (QBQueries) query;

				if (result.isSuccess()) {
					switch (qbQueryType) {
					case QB_QUERY_SIGN_UP_USER:
						QBManager.signInUser(username.getText().toString(), password.getText().toString(), new SignInQBCallBack(), QBQueries.QB_QUERY_SIGN_IN_USER);
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
	}
	
	private class SignInQBCallBack implements QBCallback {

		@Override
		public void onComplete(Result arg0) {}

		@Override
		public void onComplete(Result result, Object context) {
			QBQueries qbQueryType = (QBQueries) context;

			if (result.isSuccess()) {
				switch (qbQueryType) {
				case QB_QUERY_SIGN_IN_USER:
					setResult(RESULT_OK);
					QBUserResult qbUserResult = (QBUserResult) result;
					DataHolder.setSignInQbUser(qbUserResult.getUser());
					Toast.makeText(getBaseContext(), getResources().getString(R.string.user_successfully_sign_in), Toast.LENGTH_LONG).show();
					goToHomeActivity();
					finish(); // TODO try this
					break;
				default:
					break;
				}
			} else { // print errors that came from server
				Toast.makeText(getBaseContext(), result.getErrors().get(0), Toast.LENGTH_SHORT).show();
			}
			progressDialog.hide();
		}
	}

	public void onClickSignUpHere(View v) {
		manageButtons(View.INVISIBLE, View.VISIBLE, false);
	}
	
	public void onClickCancelSignUp(View v) {
		manageButtons(View.VISIBLE, View.INVISIBLE, true);
	}
	
	private void manageButtons(int signInVisibility, int signUpAndCancelVisibility, boolean signUpHereState) {
		Button button;
		
		// sign in btn
		if((button = (Button) findViewById(R.id.signin_btn)) != null) {
			button.setVisibility(signInVisibility);
		}
		// sign up here button
		if((button = (Button) findViewById(R.id.signup_here_btn)) != null) {
			button.setClickable(signUpHereState);
		}
		
		// sign up and cancel buttons
		if((button = (Button) findViewById(R.id.signup_btn)) != null) {
			button.setVisibility(signUpAndCancelVisibility);
		}
		if((button = (Button) findViewById(R.id.cancel_signup_btn)) != null) {
			button.setVisibility(signUpAndCancelVisibility);
		}
	}
	
	private void goToHomeActivity() {
		goHome(this);
	}
	
	private void initializeMainScreen() {
		username = (EditText) findViewById(R.id.signin_username);
		password = (EditText)findViewById(R.id.signin_password);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(getResources().getString(R.string.please_wait));
	}
	
	private void showMainScreen() {
		initializeMainScreen();
		progressBar.setVisibility(View.INVISIBLE);
		
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
