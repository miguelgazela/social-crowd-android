package pt.up.fe.socialcrowd.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.logic.BaseEvent;
import android.R.integer;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
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
		
		name = (EditText) findViewById(R.id.event_name);
		description = (EditText) findViewById(R.id.event_description);
		category = (EditText) findViewById(R.id.event_category);
		tags = (EditText) findViewById(R.id.tags_field);
		start_date = (EditText) findViewById(R.id.start_date);
		end_date = (EditText) findViewById(R.id.end_date);
		rGroup = (RadioGroup) findViewById(R.id.newEvent_radioButtonGroup);	
	}

	public void onClickCreateEvent(View v) {
		Log.i("NewEventActivity - addEvent()", "Adding new event");
		
		// get values
		String 	nameStr = name.getText().toString().trim(),
				descriptionStr = description.getText().toString().trim(),
				categoryStr = category.getText().toString().trim(),
				tagsStr = tags.getText().toString().trim(),
				startStr = start_date.getText().toString().trim(),
				endStr = end_date.getText().toString().trim();
		
		// validate inputs
		if(nameStr.length() <= 3 || nameStr.length() > 60) {
			Toast.makeText(getBaseContext(), "Name must be between 4 and 60 chars", Toast.LENGTH_SHORT).show();
			return;
		}
		if(categoryStr.length() <= 3 || categoryStr.length() > 30) {
			Toast.makeText(getBaseContext(), "Category must be between 4 and 30 chars", Toast.LENGTH_SHORT).show();
			return;
		}
		if(descriptionStr.length() < 10) {
			Toast.makeText(getBaseContext(), "Description must be at least 10 chars long", Toast.LENGTH_SHORT).show();
			return;
		}
		if(tagsStr.length() == 0) {
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
		
		new AsyncTask<String, Void, Void>() {
			@Override
			protected Void doInBackground(String... params) {

				// get all tags to an array
				String alltags[] = params[2].split(" ");
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

//
//				try {
//					Request.createEvent(QBAuth.getBaseService().getToken(), eventType, name, description, ini, end, null, tags, category);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
				return null;
			}
		}.execute(nameStr, descriptionStr, tagsStr, categoryStr, startStr, endStr);
	}

	private int validateDates(String startStr, String endStr) {
		
		String regEx = "^(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|20)\\d{2}$";
		if(!startStr.matches(regEx)) {
			return NewEventActivity.INVALID_START_DATE;
		}
		if(!endStr.matches(regEx)) {
			return NewEventActivity.INVALID_END_DATE;
		}
		
		SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date 	startDate = dateformat.parse(startStr),
					endDate = dateformat.parse(endStr),
					now = new Date();
			
			if(startDate.after(endDate)) {
				return NewEventActivity.INVALID_START_DATE;
			}
			if(startDate.before(now)) {
				return NewEventActivity.INVALID_START_DATE;
			}
			return 0;
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		return -1;
	}


}
