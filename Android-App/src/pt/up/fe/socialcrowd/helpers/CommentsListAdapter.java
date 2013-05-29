package pt.up.fe.socialcrowd.helpers;

import java.util.ArrayList;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.logic.Comment;
import pt.up.fe.socialcrowd.logic.DateParser;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentsListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private ArrayList<Comment> comments;
	
	public CommentsListAdapter(Context context, ArrayList<Comment> comments) {
		this.comments = comments;
		layoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public Object getItem(int pos) {
		return comments.get(pos);
	}
	
	public void addItem(Comment newComment) {
		comments.add(newComment);
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null) {
			convertView = layoutInflater.inflate(R.layout.comment_list_row, null);
			holder = new ViewHolder();
			
			holder.commentUsername = (TextView) convertView.findViewById(R.id.comment_username);
			holder.commentDate = (TextView) convertView.findViewById(R.id.comment_date);
			holder.commentBody = (TextView) convertView.findViewById(R.id.comment_body);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.commentUsername.setText("username_here");
		
		holder.commentDate.setText(DateParser.getPrettyDate(comments.get(pos).getDate()));
		holder.commentBody.setText(comments.get(pos).getText());
		return convertView;
	}
	
	private static class ViewHolder {
		TextView commentUsername, commentDate, commentBody;
	}
}



