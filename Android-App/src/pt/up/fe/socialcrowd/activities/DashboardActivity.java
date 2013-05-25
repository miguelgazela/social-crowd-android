package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.R.layout;
import pt.up.fe.socialcrowd.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public abstract class DashboardActivity extends Activity {

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
	
	/**
	 * Handle the click on the search button
	 * @param v View
	 */
	public void onClickSearch (View v) {
	    startActivity (new Intent(getApplicationContext(), SearchActivity.class));
	}

	/**
	 * Handle the click on the about button
	 * @param v View
	 */
	public void onClickAbout (View v) {
		startActivity (new Intent(getApplicationContext(), AboutActivity.class));
	}

	public void onClickFeature (View v) {
		int id = v.getId ();
		/*
		switch (id) {
		case R.id.home_btn_newEvent :
			startActivity (new Intent(getApplicationContext(), NewEventActivity.class));
			break;
		case R.id.home_btn_listEvents :
			// needs to pass information
			startActivity (new Intent(getApplicationContext(), EventsListActivity.class));
			break;
		case R.id.home_btn_listSubscriptions :
			// needs to pass information
			startActivity (new Intent(getApplicationContext(), EventsListActivity.class));
			break;
		case R.id.home_btn_listMyEvents :
			// needs to pass information
			startActivity (new Intent(getApplicationContext(), EventsListActivity.class));
			break;
		case R.id.home_btn_settings :
			startActivity (new Intent(getApplicationContext(), SettingsActivity.class));
			break;
		case R.id.home_btn_logout :
			// TODO logout
			break;
		default: 
			break;
		}
		*/
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}

}
