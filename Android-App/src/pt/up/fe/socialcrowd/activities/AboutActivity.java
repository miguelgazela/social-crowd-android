package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import android.os.Bundle;

public class AboutActivity extends DashboardActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitleFromActivityLabel(R.string.title_activity_about);
		setContentView(R.layout.activity_about);
	}
}
