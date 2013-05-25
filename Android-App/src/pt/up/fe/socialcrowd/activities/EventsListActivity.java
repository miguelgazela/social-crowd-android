package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.R.layout;
import pt.up.fe.socialcrowd.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EventsListActivity extends DashboardActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_list);
		setTitleFromActivityLabel (R.string.title_activity_events_list);
	}
	
	

}
