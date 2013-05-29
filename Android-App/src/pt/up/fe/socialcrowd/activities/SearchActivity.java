package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends DashboardActivity {

//	private Spinner spinner;
//	private String searchModifier = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setTitleFromActivityLabel(R.id.title_text);
	}
		
	public void onClickSearch(View v) {
		String name = ((EditText) findViewById(R.id.search_name_input_field)).getText().toString();
		String category = ((EditText) findViewById(R.id.search_category_input_field)).getText().toString();
		String allTags = ((EditText) findViewById(R.id.search_tags_input_field)).getText().toString();	
		search(name, category, allTags);
	}

	private void search(String name, String category, String allTags) {
		if(name != "" && category != "" && allTags != ""){
			Intent i = new Intent(getApplicationContext(), EventsListActivity.class);
			
			if(name.length() != 0) {
				i.putExtra("SEARCH_QUERY_NAME", name);
			}
			if(category.length() != 0) {
				i.putExtra("SEARCH_QUERY_CATEGORY", category);
			}
			if(allTags.length() != 0) {
				i.putExtra("SEARCH_QUERY_ALLTAGS", allTags);
			}
			i.putExtra("LIST_TYPE", DashboardActivity.LIST_SEARCH_RESULTS);

			startActivity(i);
		}
	}
	
}


