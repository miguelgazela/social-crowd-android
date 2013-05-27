package pt.up.fe.socialcrowd.activities;

import java.util.ArrayList;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.helpers.EventsListAdapter;
import pt.up.fe.socialcrowd.logic.BaseEvent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class EventsListActivity extends DashboardActivity {

	private ArrayList<BaseEvent> events = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_events);
		setTitleFromActivityLabel(R.id.title_text);
		
		// check what kind 
		String listingType = getIntent().getStringExtra("LIST_TYPE");
		
		if(listingType.equalsIgnoreCase(DashboardActivity.LIST_EVENTS)) {
			getAllEvents();
		} else if(listingType.equalsIgnoreCase(DashboardActivity.LIST_MY_EVENTS)) {
			getUserEvents();
		} else if(listingType.equalsIgnoreCase(DashboardActivity.LIST_SUBSCRIPTIONS)) {
			getUserSubscriptions();
		} else {
			// TODO show some default events ?
		}
	}

	private void getUserSubscriptions() {
		Log.i("EventsListActivity - getUserSubscriptions()", "Requesting user subscriptions");
	}

	private void getUserEvents() {
		Log.i("EventsListActivity - getUserEvents()", "Requesting user events");
	}

	private void getAllEvents() {
		Log.i("EventsListActivity - getAllEvents()", "Requesting all events");
		
		// getting stuff from the internet must be done in another thread
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					events = Request.getEvents();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				insertContent();
			}
		}.execute();
	}
	
	private void insertContent() {
		final ListView eventsList = (ListView) findViewById(R.id.eventsList);
		eventsList.setAdapter(new EventsListAdapter(this, events));
		eventsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
		    public void onItemClick(AdapterView<?> a, View v, int pos, long id) {
		        Object obj = eventsList.getItemAtPosition(pos);
		        BaseEvent event = (BaseEvent) obj;

		        // Launching new Activity on selecting single List Item
		        Intent intent = new Intent(getApplicationContext(), EventActivity.class);
		        // sending event id to new activity
		        intent.putExtra("event_id", event.getId());
		        startActivity(intent);
			}
		});
	}
}
