package pt.up.fe.socialcrowd.helpers;

import java.util.ArrayList;

import pt.up.fe.socialcrowd.R;
import pt.up.fe.socialcrowd.logic.BaseEvent;
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
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.eventName.setText(events.get(pos).getName());
		holder.eventDescription.setText(events.get(pos).getDescription());
		holder.eventCategory.setText(events.get(pos).getCategory());
		
		return convertView;
	}
	
	private static class ViewHolder {
		TextView eventName, eventDescription, eventCategory;
	}
}


