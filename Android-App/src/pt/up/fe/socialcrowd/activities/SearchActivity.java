package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchActivity extends DashboardActivity {

	private Spinner spinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setTitleFromActivityLabel(R.id.title_text);
		
		spinner = (Spinner) findViewById(R.id.search_option_menu);
		
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(
										this,
										R.array.search_options_array,
										android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		Button submit = (Button) findViewById(R.id.search_submit_button);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String parameters = ((EditText) findViewById(R.id.search_input_field)).getText().toString();
				search(parameters);
			//startActivity(new Intent(getApplicationContext(), HomeActivity.class));
			}
		});
	}
		
		

	private void search(String parameters) {
		// TODO Auto-generated method stub	
	}
}
