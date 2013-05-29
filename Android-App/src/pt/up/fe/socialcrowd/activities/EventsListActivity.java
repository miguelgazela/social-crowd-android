package pt.up.fe.socialcrowd.activities;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONException;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.API.RequestException;
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
		} else{
			String name = getIntent().getStringExtra("SEARCH_QUERY_NAME");
			String category = getIntent().getStringExtra("SEARCH_QUERY_CATEGORY");
			String allTags = getIntent().getStringExtra("SEARCH_QUERY_ALLTAGS");
			
			String tagArray[] = allTags.split(",");
			ArrayList<String> tags = new ArrayList<String>();
			
			for(String s : tagArray){	
				tags.add(s);			
			}
		
			try {
				events = Request.getEventsBySearch(null, BaseEvent.PUBLIC , name, category, tags);
				insertContent();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}
	
	private void getUserSubscriptions() {
		Log.i("EventsListActivity - getUserSubscriptions()", "Requesting user subscriptions");

		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
//					events = Request.getEventsBySubscriberID(DataHolder.getSignInQbUser().getId());
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
//					events = Request.getEventsByOwnerID(DataHolder.getSignInQbUser().getId());
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
