package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import android.os.Bundle;

public class EventActivity extends DashboardActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		displayEvent();
	}
	
	private void displayEvent() {
		String event_id = this.getIntent().getStringExtra("event_id");
		System.out.println("Event id: "+event_id);
	}
	
	private void onClickBack() {
		finish();
	}
}
