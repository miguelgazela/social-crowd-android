package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchActivity extends DashboardActivity {

	private Spinner spinner;
	private String searchModifier = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setTitleFromActivityLabel(R.id.title_text);
		
		Button submit = (Button) findViewById(R.id.search_submit_button);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String name = ((EditText) findViewById(R.id.search_name_input_field)).getText().toString();
				String category = ((EditText) findViewById(R.id.search_category_label)).getText().toString();
				String allTags = ((EditText) findViewById(R.id.search_tags_label)).getText().toString();	
				search(name, category, allTags);
			//startActivity(new Intent(getApplicationContext(), HomeActivity.class));
			}
		});
	}
		
		

	private void search(String name, String category, String allTags) {
		

		if(name != "" && category != "" && allTags != ""){
			Intent i = new Intent(getApplicationContext(), EventsListActivity.class);
			i.putExtra("SEARCH_QUERY_NAME", name);
			i.putExtra("SEARCH_QUERY_CATEGORY", category);
			i.putExtra("SEARCH_QUERY_ALLTAGS", allTags);
			i.putExtra("LIST_TYPE", DashboardActivity.LIST_EVENTS);

			startActivity(i);
		}
	}
	
}


