package pt.up.fe.socialcrowd.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.logic.BaseEvent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
				startActivity(new Intent(getApplicationContext(), HomeActivity.class));
			}
		});
	}

	private void addEvent() {
		Log.i("NewEventActivity - addEvent()", "Adding new event");
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {

				String name = ((EditText) findViewById(R.id.event_name)).getText().toString();
				String description = ((EditText) findViewById(R.id.event_description)).getText().toString();
				String category = ((EditText) findViewById(R.id.event_category)).getText().toString();
				String singleLineTags = ((EditText) findViewById(R.id.tags_field)).getText().toString();
				String alltags[] = singleLineTags.split(",");
				ArrayList<String> tags = new ArrayList<String>();
				for(String s : alltags){
					tags.add(s);
				}

				RadioGroup rbg = (RadioGroup) findViewById(R.id.newEvent_radioButtonGroup);	
				RadioButton selected = (RadioButton) findViewById(rbg.getCheckedRadioButtonId());
				String type = selected.getText().toString();
				int eventType = 0;

				if(type == "Private"){
					eventType = BaseEvent.PRIVATE;
				}else{
					if(type == "Public"){
						eventType = BaseEvent.PUBLIC;
					}else{
						if(type == "Geolocated"){
							eventType = BaseEvent.GEOLOCATED;
						}
					}
				}

				DatePicker iniDatePicker = (DatePicker) findViewById(R.id.begin_datepicker);
				DatePicker endDatePicker = (DatePicker) findViewById(R.id.end_datepicker);
				GregorianCalendar gc = new GregorianCalendar(
						iniDatePicker.getYear(),
						iniDatePicker.getMonth(),
						iniDatePicker.getDayOfMonth());		
				Date ini = gc.getTime();
				gc = new GregorianCalendar(
						endDatePicker.getYear(),
						endDatePicker.getMonth(),
						endDatePicker.getDayOfMonth());
				Date end = gc.getTime();


				Log.i("eventType", type);
				Log.i("tags", singleLineTags);
				Log.i("name", name);
				Log.i("category", category);
				Log.i("descrition", description);
				Log.i("IniDate", ini.toString());
				Log.i("endDate", end.toString());


				try {
					Request.createEvent(
							"dummy_id", eventType, name, description,
							ini, end, null, tags, category);
				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}
		}.execute();
	}


}
