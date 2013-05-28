package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.logic.DetailedEvent;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventActivity extends DashboardActivity {
	
	private DetailedEvent event = null;
	private ProgressDialog progressDialog = null;
	private TextView eventName, eventLocation, eventDescription, eventTags, eventCategory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(getResources().getString(R.string.please_wait));
		progressDialog.show();
		displayEvent();
	}
	
	private void displayEvent() {
		final int event_id = this.getIntent().getIntExtra("event_id", -1);
		
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					event = Request.getEventByID(event_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				if(event != null) {
					eventName = (TextView) findViewById(R.id.event_name);
					eventLocation = (TextView) findViewById(R.id.event_location);
					eventDescription = (TextView) findViewById(R.id.event_description);
					eventTags = (TextView) findViewById(R.id.event_tags);
					eventCategory = (TextView) findViewById(R.id.event_category);
					
					progressDialog.dismiss();
					
					eventName.setText(event.getName());
					
				} else {
					progressDialog.dismiss();
					// display warning
				}
			}
		}.execute();
	}
	
	public void onClickBack(View v) {
		progressDialog.dismiss();
		finish();
	}
	
	
}
