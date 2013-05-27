package pt.up.fe.socialcrowd.activities;

import com.quickblox.core.QBCallback;
import com.quickblox.core.result.Result;
import com.quickblox.module.auth.QBAuth;

import pt.up.fe.socialcrowd.R;
import android.os.Bundle;
import android.util.Log;

public class HomeActivity extends DashboardActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("HomeActivity - onDestroy()", "This activity is being destroyed");
		// destroy session after app close
		QBAuth.deleteSession(new QBCallback() {
			@Override public void onComplete(Result arg0, Object arg1) {}
			@Override public void onComplete(Result arg0) {}
		});
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
