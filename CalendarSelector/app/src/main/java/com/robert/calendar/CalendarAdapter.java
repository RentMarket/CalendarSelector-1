package com.robert.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter {

	private ArrayList<Day> days;
	private LayoutInflater mInflater;
	private Calendar c;
	private Context context;
	public static View viewStart;
	public static View viewEnd;
	private String startName, endName;

	public CalendarAdapter(Context context, Calendar c, int passDays, String startDay, String endDay,String startName, String endName) {
		this.c = c;
		this.context = context;
		this.startName = startName;
		this.endName = endName;
		days = CalendarUtils.getDaysOfMonth(this.c, passDays, startDay, endDay, context);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return days.size();
	}

	@Override
	public Day getItem(int arg0) {
		return days.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_calendar_view, arg2, false);
			holder = new ViewHolder();
			holder.tv_calendar_item = (TextView) convertView.findViewById(R.id.tv_calendar_item);
			holder.tv_calendar_lunar = (TextView) convertView.findViewById(R.id.tv_calendar_lunar);
			holder.layItem = (LinearLayout) convertView.findViewById(R.id.layItem);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Day d = getItem(arg0);
		holder.tv_calendar_lunar.setText(d.getLunar());
		holder.tv_calendar_lunar.setVisibility(d.isStartDate() || d.isEndDate() ? View.GONE : View.VISIBLE);
		holder.tv_calendar_item.setTag(d);
		switch (d.getType()) {
			case TODAY:
				setOrderThreeDayStyle(holder, d, context.getString(R.string.today));
				if (d.isStartDate()) {
					viewStart = convertView;
				} else if (d.isEndDate()) {
					viewEnd = convertView;
				}
				break;
			case TOMORROW:
				setOrderThreeDayStyle(holder, d, context.getString(R.string.tomorrow));
				if (d.isStartDate()) {
					viewStart = convertView;
				} else if (d.isEndDate()) {
					viewEnd = convertView;
				}
				break;
			case T_D_A_T:
				setOrderThreeDayStyle(holder, d, context.getString(R.string.t_d_a_t));
				if (d.isStartDate()) {
					viewStart = convertView;
				} else if (d.isEndDate()) {
					viewEnd = convertView;
				}
				break;
			case ENABLE:
				if (d.isStartDate()) {
					holder.tv_calendar_item.setText(d.getName() + "\n" + startName);
					viewStart = convertView;
				} else if (d.isEndDate()) {
					holder.tv_calendar_item.setText(d.getName() + "\n" + endName);
					viewEnd = convertView;
				} else {
					holder.tv_calendar_item.setText(d.getName());
				}
				holder.tv_calendar_item.setEnabled(true);
				holder.tv_calendar_item.setTextColor(d.isStartDate() || d.isEndDate() ? Color.WHITE : context.getResources().getColor(
						R.color.calendar_enable_color));
				holder.layItem.setBackgroundResource(d.isStartDate() || d.isEndDate() ? R.drawable.calendar_order_item_bg
						: R.drawable.normal_calendar_order_item_bg);
				holder.tv_calendar_item.setTextSize(d.isStartDate() || d.isEndDate() ? 10 : 12);
				holder.tv_calendar_lunar.setTextColor(d.isSolarTerm() ? context.getResources().getColor(R.color.calendar_solarterm_color) : context.getResources().getColor(R.color.calendar_enable_color));
				break;
			case NOT_ENABLE:
				holder.tv_calendar_item.setText(d.getName());
				holder.tv_calendar_item.setEnabled(false);
				holder.tv_calendar_item.setTextColor(context.getResources().getColor(R.color.calendar_disable_color));
				holder.layItem.setBackgroundColor(Color.WHITE);
				holder.tv_calendar_item.setTextSize(12);
				holder.tv_calendar_lunar.setTextColor(context.getResources().getColor(R.color.calendar_disable_color));
				break;
		}
		return convertView;
	}

	private void setOrderThreeDayStyle(ViewHolder holder, Day day, String dayStr) {
		if (day.isStartDate()) {
			holder.tv_calendar_item.setText(dayStr + "\n" + startName);
		} else if (day.isEndDate()) {
			holder.tv_calendar_item.setText(dayStr + "\n" + endName);
		} else {
			holder.tv_calendar_item.setText(dayStr);
		}
		holder.tv_calendar_item.setEnabled(true);
		holder.tv_calendar_item.setTextColor(day.isStartDate() || day.isEndDate() ? Color.WHITE : context.getResources().getColor(R.color.calendar_threeday_color));
		holder.tv_calendar_item.setTextSize(day.isStartDate() || day.isEndDate() ? 10 : 12);
		holder.layItem.setBackgroundResource(day.isStartDate() || day.isEndDate() ? R.drawable.calendar_order_item_bg : R.drawable.normal_calendar_order_item_bg);
		holder.tv_calendar_lunar.setTextColor(day.isSolarTerm() ? context.getResources().getColor(R.color.calendar_solarterm_color) : context.getResources().getColor(R.color.calendar_threeday_color));
	}

	static class ViewHolder {
		TextView tv_calendar_item, tv_calendar_lunar;
		LinearLayout layItem;
	}

}
