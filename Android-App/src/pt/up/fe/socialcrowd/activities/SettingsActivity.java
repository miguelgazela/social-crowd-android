package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import android.os.Bundle;

public class SettingsActivity extends DashboardActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitleFromActivityLabel (R.string.title_activity_settings);
		setContentView(R.layout.activity_settings);
	}
}
