package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchActivity extends DashboardActivity {

	private Spinner spinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setTitleFromActivityLabel(R.id.title_text);
		/*
		spinner = (Spinner) findViewById(R.id.search_option_menu);
		
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(
										this,
										R.array.search_options_array,
										android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);*/
	}
}
