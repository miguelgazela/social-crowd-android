package pt.up.fe.socialcrowd.activities;

import pt.up.fe.socialcrowd.R;
import android.os.Bundle;

public class HomeActivity extends DashboardActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	protected void onPause () {
	   super.onPause ();
	}
	protected void onRestart () {
	   super.onRestart ();
	}
	protected void onResume () {
	   super.onResume ();
	}
	protected void onStart () {
	   super.onStart ();
	}
	protected void onStop () {
	   super.onStop ();
	}

}
