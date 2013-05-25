package pt.up.fe.socialcrowd.activities;

import java.util.ArrayList;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.API.RequestException;
import pt.up.fe.socialcrowd.logic.BaseEvent;
import android.os.Bundle;

public class EventsListActivity extends DashboardActivity {

	private ArrayList<BaseEvent> events = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitleFromActivityLabel (R.string.title_activity_events_list);
		setContentView(R.layout.activity_list_events);
		
		// check what kind 
		String listingType = getIntent().getStringExtra("LIST_TYPE");
		
		if(listingType.equalsIgnoreCase(DashboardActivity.LIST_EVENTS)) {
			listEvents();
		} else if(listingType.equalsIgnoreCase(DashboardActivity.LIST_MY_EVENTS)) {
			listUserEvents();
		} else if(listingType.equalsIgnoreCase(DashboardActivity.LIST_SUBSCRIPTIONS)) {
			listSubscriptions();
		} else {
			// do what? TODO
		}
		
	}

	private void listSubscriptions() {
		
	}

	private void listUserEvents() {
		
	}

	private void listEvents() {
		try {
			events = Request.getEvents();
			for(int i = 0; i < events.size(); i++) {
				System.out.println(events.get(i).getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
	
	
}
