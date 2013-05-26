package pt.up.fe.socialcrowd.activities;

import java.util.ArrayList;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.API.RequestException;
import pt.up.fe.socialcrowd.logic.BaseEvent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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
		
		/**
		 *  getting stuff from the internet must be done in another thread,
		 *  AsynkTask is awesome for that!
		 */
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
		}.execute();
	}
}
