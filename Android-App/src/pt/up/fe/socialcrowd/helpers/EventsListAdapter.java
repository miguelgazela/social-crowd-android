package pt.up.fe.socialcrowd.helpers;

import java.util.ArrayList;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.logic.BaseEvent;
import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventsListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private ArrayList<BaseEvent> events;
	
	public EventsListAdapter(Context context, ArrayList<BaseEvent> events) {
		this.events = events;
		layoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return events.size();
	}

	@Override
	public Object getItem(int pos) {
		return events.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null) {
			convertView = layoutInflater.inflate(R.layout.event_list_row, null);
			holder = new ViewHolder();
			
			holder.eventName = (TextView) convertView.findViewById(R.id.event_name);
			holder.eventDescription = (TextView) convertView.findViewById(R.id.event_description);
			holder.eventCategory = (TextView) convertView.findViewById(R.id.event_category);
			holder.eventInfo = (TextView) convertView.findViewById(R.id.event_info);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.eventName.setText(events.get(pos).getName());
		holder.eventDescription.setText(getDescriptionExcerpt(events.get(pos).getDescription()));
		holder.eventCategory.setText(events.get(pos).getCategory());
		holder.eventInfo.setText("at Porto.... "); // TODO set right location and date
		
		return convertView;
	}
	
	private String getDescriptionExcerpt(String description) {
		if(description.length() <= 140) {
			return description;
		} else {
			// find first space after 140 characters and cut there
			int pos = description.indexOf(" ", 139);
			return description.substring(0, pos)+"...";
		}
	}
	
	private static class ViewHolder {
		TextView eventName, eventDescription, eventCategory, eventInfo;
	}
}


