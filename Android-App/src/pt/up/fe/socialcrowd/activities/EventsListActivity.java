package pt.up.fe.socialcrowd.activities;

import java.util.ArrayList;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.logic.BaseEvent;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventsListActivity extends DashboardActivity {

	private ListView eventsListView;
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
		eventsList.setAdapter(new EventsListAdapter(this));
		eventsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
		    public void onItemClick(AdapterView<?> a, View v, int pos, long id) {
		        Object obj = eventsList.getItemAtPosition(pos);
		        BaseEvent event = (BaseEvent) obj;
		        Toast.makeText(EventsListActivity.this, "Selected :" + " " + event, Toast.LENGTH_LONG).show();
		    }
		});
	}
	
	private class EventsListAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		
		public EventsListAdapter(Context context) {
			layoutInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return events.size();
		}

		@Override
		public Object getItem(int pos) {
			return events.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}
		
		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			ViewHolder holder;
			
			if(convertView == null) {
				convertView = layoutInflater.inflate(R.layout.event_list_row, null);
				holder = new ViewHolder();
				holder.eventName = (TextView) convertView.findViewById(R.id.event_name);
				holder.eventDescription = (TextView) convertView.findViewById(R.id.event_description);
				holder.eventCategory = (TextView) convertView.findViewById(R.id.event_category);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.eventName.setText(events.get(pos).getName());
			holder.eventDescription.setText(events.get(pos).getDescription());
			holder.eventCategory.setText(events.get(pos).getCategory());
			
			return convertView;
		}
	}
	
	private static class ViewHolder {
		TextView eventName, eventDescription, eventCategory;
	}
}
