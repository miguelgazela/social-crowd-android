package pt.up.fe.socialcrowd.activities;

import java.util.ArrayList;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.API.Request;
import pt.up.fe.socialcrowd.helpers.CommentsListAdapter;
import pt.up.fe.socialcrowd.logic.Comment;
import pt.up.fe.socialcrowd.logic.DetailedEvent;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EventActivity extends DashboardActivity implements OnClickListener {
	
	private DetailedEvent event = null;
	private ProgressDialog progressDialog = null;
	private TextView eventName, eventLocation, eventDescription, eventTags, eventCategory;
	private EditText inputComment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(getResources().getString(R.string.please_wait));
		progressDialog.show();
		displayEvent();
	}
	
	private void displayEvent() {
		final int event_id = this.getIntent().getIntExtra("event_id", -1);
		
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					event = Request.getEventByID(event_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				if(event != null) {
					eventName = (TextView) findViewById(R.id.event_name);
					eventLocation = (TextView) findViewById(R.id.event_location);
					eventDescription = (TextView) findViewById(R.id.event_description);
					eventTags = (TextView) findViewById(R.id.event_tags);
					eventCategory = (TextView) findViewById(R.id.event_category);
					inputComment = (EditText) findViewById(R.id.inputComment);
					
					inputComment.setOnClickListener(EventActivity.this);
					
					progressDialog.dismiss();
					
					eventName.setText(event.getName());
					eventDescription.setText(event.getDescription());
					eventCategory.setText(event.getCategory());
					eventLocation.setText(event.getLocation().getText());
					ArrayList<String> tags = event.getTags();
					eventTags.setText(tags.toString());
					
					insertComments();
				} else {
					progressDialog.dismiss();
					// display warning
				}
			}
		}.execute();
	}
	
	private void insertComments() {
		ArrayList<Comment> comments = event.getComments();
		
		if(comments != null) {
			if(comments.size() != 0) {
				ImageView separator = (ImageView) findViewById(R.id.commentsSeparator);
				if(separator != null) {
					separator.setVisibility(View.VISIBLE);
				}
				TextView commentsTitle = (TextView) findViewById(R.id.commentsTitle);
				if(commentsTitle != null) {
					commentsTitle.setVisibility(View.VISIBLE);
				}
			}
			
			final ListView commentsList = (ListView) findViewById(R.id.commentsList);
			commentsList.setAdapter(new CommentsListAdapter(this, comments));
		}
	}
	
	public void addComment(View v) {
		
	}

	public void onClickBack(View v) {
		finish();
	}
	
	public void onClickRefresh(View v) {
		// update comments here!
	}

	@Override
	public void onClick(View v) {
		Log.i("onClick", "it worked");
	}
}
