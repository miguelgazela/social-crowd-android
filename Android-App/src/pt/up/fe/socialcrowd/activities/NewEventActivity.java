package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.R.layout;
import pt.up.fe.socialcrowd.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NewEventActivity extends DashboardActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		setTitleFromActivityLabel (R.string.title_activity_new_event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_event, menu);
		return true;
	}

}
