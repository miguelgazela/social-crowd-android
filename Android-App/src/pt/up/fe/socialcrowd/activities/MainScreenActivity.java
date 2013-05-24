package pt.up.fe.socialcrowd.activities;


import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.definitions.Consts;
import pt.up.fe.socialcrowd.definitions.QBQueries;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quickblox.core.QBCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.result.Result;
import com.quickblox.module.auth.QBAuth;


public class MainScreenActivity extends Activity {
	
	private ProgressBar progressBar;

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
		case R.id.login_btn:
		{	
			System.out.println("Login");
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
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
	
	private void showMainScreen() {
		progressBar.setVisibility(View.INVISIBLE);
		System.out.println("Starting Sign In Activity");
		
		// making buttons visible
		Button button;
		if((button = (Button) findViewById(R.id.login_btn)) != null) {
			button.setVisibility(View.VISIBLE);
		}
		if((button = (Button) findViewById(R.id.signup_btn)) != null) {
			button.setVisibility(View.VISIBLE);
		}
	}
}
