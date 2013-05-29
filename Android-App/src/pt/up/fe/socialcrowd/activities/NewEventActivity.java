package pt.up.fe.socialcrowd.activities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.logic.BaseEvent;
import pt.up.fe.socialcrowd.logic.DateParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.quickblox.module.auth.QBAuth;

public class NewEventActivity extends DashboardActivity {

	private static final int INVALID_START_DATE = 1;
	private static final int INVALID_END_DATE = 2;
			
	private EditText name, description, category, tags, start_date, end_date;
	private RadioGroup rGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		setTitleFromActivityLabel(R.id.title_text);
		
		name = ((EditText) findViewById(R.id.event_name));
		description = ((EditText) findViewById(R.id.event_description));
		category = ((EditText) findViewById(R.id.event_category));
		tags = ((EditText) findViewById(R.id.tags_field));
		rGroup = (RadioGroup) findViewById(R.id.newEvent_radioButtonGroup);	
		
	}

	private void addEvent() {
		Log.i("NewEventActivity - addEvent()", "Adding new event");
		
		// get values
		String 	nameStr = name.getText().toString(),
				descriptionStr = description.getText().toString(),
				categoryStr = category.getText().toString(),
				tagsStr = tags.getText().toString(),
				startStr = start_date.getText().toString(),
				endStr = end_date.getText().toString();
		
		// validate inputs
		if(nameStr.length() <= 3 && nameStr.length() > 60) {
			Toast.makeText(getBaseContext(), "Name must be between 4 and 60 chars", Toast.LENGTH_SHORT).show();
			return;
		}
		if(descriptionStr.length() < 10) {
			Toast.makeText(getBaseContext(), "Description must be at least 10 chars long", Toast.LENGTH_SHORT).show();
			return;
		}
		if(tagsStr.length() != 0) {
			Toast.makeText(getBaseContext(), "You must add at least 1 tag", Toast.LENGTH_SHORT).show();
			return;
		}
		if(validateDates(startStr, endStr) == NewEventActivity.INVALID_START_DATE) {
			Toast.makeText(getBaseContext(), "Invalid start date", Toast.LENGTH_SHORT).show();
			return;
		}
		if(validateDates(startStr, endStr) == NewEventActivity.INVALID_END_DATE) {
			Toast.makeText(getBaseContext(), "Invalid end date", Toast.LENGTH_SHORT).show();
			return;
		}
		
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {

				String name = ((EditText) findViewById(R.id.event_name)).getText().toString();
				String description = ((EditText) findViewById(R.id.event_description)).getText().toString();
				String category = ((EditText) findViewById(R.id.event_category)).getText().toString();
				String singleLineTags = ((EditText) findViewById(R.id.tags_field)).getText().toString();

				
				// get all tags to an array

				String alltags[] = singleLineTags.split(" ");
				ArrayList<String> tags = new ArrayList<String>();
				for(String s : alltags){
					tags.add(s);
				}

				RadioGroup rbg = (RadioGroup) findViewById(R.id.newEvent_radioButtonGroup);	
				RadioButton selected = (RadioButton) findViewById(rbg.getCheckedRadioButtonId());
				String type = selected.getText().toString();
				int eventType = 0;
				
				if(type.equalsIgnoreCase("Public")) {
					eventType = BaseEvent.PUBLIC;
				} else if(type.equalsIgnoreCase("Private")) {
					eventType = BaseEvent.PRIVATE;
				} else if(type.equalsIgnoreCase("Geolocated")) {
					eventType = BaseEvent.GEOLOCATED;
				}
				
				String iniDate = ((EditText) findViewById(R.id.start_date)).getText().toString();
				String endDate = ((EditText) findViewById(R.id.end_date)).getText().toString();
				
				
				Date ini = null;
				Date end = null;
				try {
					ini = DateParser.parseString(iniDate);
					end = DateParser.parseString(endDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
		
				Log.i("eventType", type);
				Log.i("tags", singleLineTags);
				Log.i("name", name);
				Log.i("category", category);
				Log.i("descrition", description);
				Log.i("IniDate", iniDate);
				Log.i("endDate", endDate);

				try {
					Request.createEvent(QBAuth.getBaseService().getToken(), eventType, name, description, ini, end, null, tags, category);
				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}
		}.execute();
	}

	private int validateDates(String startStr, String endStr) {
		
		return 0;
	}


}
