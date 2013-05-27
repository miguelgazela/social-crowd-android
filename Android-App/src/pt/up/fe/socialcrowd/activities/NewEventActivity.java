package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NewEventActivity extends DashboardActivity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		setTitleFromActivityLabel(R.id.title_text);
		
		Button submit = (Button) findViewById(R.id.new_event_submit_button);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addEvent();
				
			}
		});
		
		
	}
	
	private void addEvent() {
		Log.i("NewEventActivity - addEvent()", "Adding new event");
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Request.createSession("1");
				} catch (Exception e) {
					e.printStackTrace();
				}
//				Request.createEvent(session_id, type, name, description, start_date, end_date, location, tags, category)
				return null;
			}
		}.execute();
	}
	
	
}
