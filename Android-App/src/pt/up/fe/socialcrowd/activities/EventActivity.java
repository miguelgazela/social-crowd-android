package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.R.layout;
import pt.up.fe.socialcrowd.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class EventActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		displayEvent();
	}
	
	private void displayEvent() {
		String event_id = this.getIntent().getStringExtra("event_id");
		EditText text = (EditText) findViewById(R.id.event_id);
		text.setText(event_id);
	}
}
