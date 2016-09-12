package com.robert.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class MyCalendar extends LinearLayout {

	private static Context context;

	public MyCalendar(Context context) {
		super(context);
		this.context = context;
	}

	public MyCalendar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void init(final Calendar calendar, int daysOfSelect, String startDate, String endDate, String startName, String endName) {
		View v = LayoutInflater.from(context).inflate(R.layout.calendar, this, true);//获取布局，开始初始化
		TextView yearAndMonth = (TextView) v.findViewById(R.id.tv_year_month);
		ExpandGridView calendarGrid = (ExpandGridView) v.findViewById(R.id.gv_calendar_layout);
		yearAndMonth.setText(calendar.get(Calendar.YEAR) + context.getString(R.string.year)
				+ (calendar.get(Calendar.MONTH) + 1) + context.getString(R.string.month));
		CalendarAdapter cAdapter = new CalendarAdapter(context, calendar, daysOfSelect, startDate, endDate, startName, endName);
		calendarGrid.setAdapter(cAdapter);
		calendarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				Calendar cl = (Calendar) calendar.clone();
				cl.set(Calendar.DAY_OF_MONTH, 1);
				int day = position + 2 - cl.get(Calendar.DAY_OF_WEEK);
				TextView dayTv = (TextView) view.findViewById(R.id.tv_calendar_item);
				if (day <= 0 || !dayTv.isEnabled())
					return;
				String dateSelect = cl.get(Calendar.YEAR) + "-" + DateUtil.addzero(cl.get(Calendar.MONTH) + 1, 2) + "-" + DateUtil.addzero(day, 2);
				if (null != callBack) {
					callBack.onDaySelectListener(view, (Day) adapterView.getAdapter().getItem(position), dateSelect);
				}
				cl.clear();
				cl = null;
			}
		});

	}

	OnDaySelectListener callBack;

	public void setOnDaySelectListener(OnDaySelectListener o) {
		callBack = o;
	}
}
