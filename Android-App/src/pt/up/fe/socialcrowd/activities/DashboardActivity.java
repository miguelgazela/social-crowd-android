package pt.up.fe.socialcrowd.activities;

import com.quickblox.module.auth.QBAuth;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.managers.DataHolder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public abstract class DashboardActivity extends Activity {

	static public final String LIST_EVENTS = "LIST_EVENTS";
	static public final String LIST_SUBSCRIPTIONS = "LIST_SUBSCRIPTIONS";
	static public final String LIST_MY_EVENTS = "LIST_MY_EVENTS";
	static public final String LIST_SEARCH_RESULTS = "LIST_SEARCH_RESULTS";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_dashboard);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	protected void onPause () {
	   super.onPause ();
	}
	protected void onRestart () {
	   super.onRestart ();
	}
	protected void onResume () {
	   super.onResume ();
	}
	protected void onStart () {
	   super.onStart ();
	}
	protected void onStop () {
	   super.onStop ();
	}
	
	// Click methods
	
	public void onClickHome (View v) {
	    goHome (this);
	    finish();
	}
	
	public void onClickSearch (View v) {
	    startActivity (new Intent(getApplicationContext(), SearchActivity.class));
	}

	public void onClickAbout (View v) {
		startActivity (new Intent(getApplicationContext(), AboutActivity.class));
	}

	public void onClickFeature (View v) {
		int id = v.getId ();
		Intent intent;
		switch (id) {
		case R.id.home_btn_newEvent :
			startActivity (new Intent(getApplicationContext(), NewEventActivity.class));
			break;
		case R.id.home_btn_listEvents :
			intent = new Intent(getApplicationContext(), EventsListActivity.class);
			intent.putExtra("LIST_TYPE", DashboardActivity.LIST_EVENTS);
			startActivity(intent);
			break;
		case R.id.home_btn_listSubscriptions :
			intent = new Intent(getApplicationContext(), EventsListActivity.class);
			intent.putExtra("LIST_TYPE", DashboardActivity.LIST_SUBSCRIPTIONS);
			startActivity(intent);
			break;
		case R.id.home_btn_listMyEvents :
			intent = new Intent(getApplicationContext(), EventsListActivity.class);
			intent.putExtra("LIST_TYPE", DashboardActivity.LIST_MY_EVENTS);
			startActivity(intent);
			break;
		case R.id.home_btn_settings :
			startActivity (new Intent(getApplicationContext(), SettingsActivity.class));
			break;
		case R.id.home_btn_logout :
			new AsyncTask<Void, Void, Boolean>() {
				@Override
				protected Boolean doInBackground(Void... userInfo) {
					try {
						Request.deleteSession(
								DataHolder.getCurrentUserSession().getSession_id(),
								QBAuth.getBaseService().getToken());
						DataHolder.deleteCurrentUserSession();
						return true;
					} catch(Exception e) {
						e.printStackTrace();
						return false;
					}
				}
				
				@Override
				protected void onPostExecute(Boolean result) {
					if(result) {
						finish();
						startActivity(new Intent(getApplicationContext(), MainScreenActivity.class));
					} else {
						Toast.makeText(getBaseContext(), "Error logging out", Toast.LENGTH_LONG).show();
					}
				}
			}.execute();
			break;
		default: 
			break;
		}
	}

	// Other methods

	public void goHome(Context context) {
	    final Intent intent = new Intent(context, HomeActivity.class);
	    intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    context.startActivity (intent);
	}
	
	public void setTitleFromActivityLabel (int textViewId) {
	    TextView tv = (TextView) findViewById (textViewId);
	    if (tv != null) {
	    	tv.setText (getTitle ());
	    }
	}
}
