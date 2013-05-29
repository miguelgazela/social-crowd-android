package pt.up.fe.socialcrowd.activities;

import java.util.ArrayList;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.helpers.EventsListAdapter;
import pt.up.fe.socialcrowd.logic.BaseEvent;
import pt.up.fe.socialcrowd.managers.DataHolder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EventsListActivity extends DashboardActivity {

	private ArrayList<BaseEvent> events = null;
	private String listingType;
	ProgressDialog progressDialog;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_events);
		setTitleFromActivityLabel(R.id.title_text);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(getResources().getString(R.string.please_wait));
		progressDialog.show();
			
		// check what kind of listing we want
		listingType = getIntent().getStringExtra("LIST_TYPE");
		
		if(listingType.equalsIgnoreCase(DashboardActivity.LIST_EVENTS)) {
			getAllEvents();
		} else if(listingType.equalsIgnoreCase(DashboardActivity.LIST_MY_EVENTS)) {
			getUserEvents();
		} else if(listingType.equalsIgnoreCase(DashboardActivity.LIST_SUBSCRIPTIONS)) {
			getUserSubscriptions();
		} else {
			final String name = getIntent().getStringExtra("SEARCH_QUERY_NAME");
			final String category = getIntent().getStringExtra("SEARCH_QUERY_CATEGORY");
			String allTags = getIntent().getStringExtra("SEARCH_QUERY_ALLTAGS");
			
			ArrayList<String> tags = null;
			if(allTags != null) {
				String tagArray[] = allTags.split(" ");
				tags = new ArrayList<String>();

				for(String s : tagArray){	
					tags.add(s);			
				}
			}
			
			new AsyncTask<ArrayList<String>, Void, Void>() {
				@Override
				protected Void doInBackground(ArrayList<String>... params) {
					try {
						events = Request.getEventsBySearch(null, null, name, category, params[0]);
						events = Request.filterEvents(events, DataHolder.getCurrentUserSession().getUser_id(), null);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					insertContent();
				}
			}.execute(tags);
		} 
	}
	
	
	private void getUserSubscriptions() {
		Log.i("EventsListActivity - getUserSubscriptions()", "Requesting user subscriptions");

		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					events = Request.getEventsBySubscriberID(DataHolder.getCurrentUserSession().getUser_id());
					events = Request.filterEvents(events, DataHolder.getCurrentUserSession().getUser_id(), null);
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

	private void getUserEvents() {
		Log.i("EventsListActivity - getUserEvents()", "Requesting user events");
		
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					events = Request.getEventsByOwnerID(DataHolder.getCurrentUserSession().getUser_id());
					events = Request.filterEvents(events, DataHolder.getCurrentUserSession().getUser_id(), null);
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

	private void getAllEvents() {
		Log.i("EventsListActivity - getAllEvents()", "Requesting all events");
		
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					events = Request.getEvents();
					events = Request.filterEvents(events, DataHolder.getCurrentUserSession().getUser_id(), null);
				} catch (Exception e) {
					Log.i("EXCEPTION", e.getMessage());
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
		progressDialog.dismiss();
		
		if(events != null) {
			final ListView eventsList = (ListView) findViewById(R.id.eventsList);
			eventsList.setAdapter(new EventsListAdapter(this, events));

			// add click handler to view single event
			eventsList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> a, View v, int pos, long id) {
					Object obj = eventsList.getItemAtPosition(pos);
					BaseEvent event = (BaseEvent) obj;

					// Launching new Activity on selecting single List Item
					Intent intent = new Intent(getApplicationContext(), EventActivity.class);
					// sending event id to new activity
					intent.putExtra("event_id", event.getId());
					if(listingType.equals(DashboardActivity.LIST_SUBSCRIPTIONS)){
						intent.putExtra("subscribed_event", true);
					}
					startActivity(intent);
				}
			});
		}
	}
	
	public void onClickRefresh(View v) {
		progressDialog.show();
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
}
