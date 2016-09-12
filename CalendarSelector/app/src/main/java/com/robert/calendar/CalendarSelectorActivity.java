package com.robert.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class CalendarSelectorActivity extends AppCompatActivity {

	/**
	 * 可选天数
	 */
	private int daysOfSelect = 90;
	private LinearLayout layCalendar;
	private String startDate, endDate;
	private String startName = "起始日";
	private String endName = "结束日";
	TextView tvTip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_selector);
		setTitle("日期选择");
		layCalendar = (LinearLayout) findViewById(R.id.ll);
		tvTip = (TextView) findViewById(R.id.tvTip);
		init();
	}

	private void init() {
		startDate = getIntent().getStringExtra("startDate");
		endDate = getIntent().getStringExtra("endDate");
		if (TextUtils.isEmpty(startDate)) {
			tvTip.setVisibility(View.VISIBLE);
			tvTip.setText("请设置起始日期");
		} else {
			tvTip.setVisibility(View.GONE);
		}
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		int monthSize = CalendarUtils.throughMonth(Calendar.getInstance(), daysOfSelect) + 1;
		for (int i = 0; i < monthSize; i++) {
			MyCalendar c1 = new MyCalendar(this);
			Calendar calendar = Calendar.getInstance();
			c1.setLayoutParams(params);
			calendar.add(Calendar.MONTH, i);
			int diffDay;
			if (i == 0) {
				diffDay = daysOfSelect;
			} else {
				diffDay = daysOfSelect - CalendarUtils.currentMonthRemainDays() - CalendarUtils.getFlowMonthDays(i - 1);
			}
			c1.init(calendar, diffDay, startDate, endDate, startName, endName);
			c1.setOnDaySelectListener(new OnDaySelectListener() {
				@Override
				public void onDaySelectListener(View view, Day d, String date) {
					if (TextUtils.isEmpty(startDate)) {
						//没有起始日期的，设置当前选中日期为起始日
						setView(d, view, date, true);
						tvTip.setVisibility(View.VISIBLE);
						tvTip.setText("请设置结束日期");
					} else {
						if (startDate.equals(date)) {
							//当前选中日期与起始日期相同，取消选中
							resetView(true);
							if (!TextUtils.isEmpty(endDate)) {
								resetView(false);
							}
							tvTip.setVisibility(View.VISIBLE);
							tvTip.setText("请设置起始日期");
						} else if (!TextUtils.isEmpty(endDate) && endDate.equals(date)) {
							//当前选中日期与结束日期相同，取消结束日期
							resetView(false);
							tvTip.setVisibility(View.VISIBLE);
							tvTip.setText("请设置结束日期");
						} else if (DateUtil.stringtoDate(date, DateUtil.LONG_DATE_FORMAT).before(DateUtil.stringtoDate(startDate, DateUtil.LONG_DATE_FORMAT))) {
							//当前选中日期在起始时间之前，重置当前选中日期为起始时间
							//重置旧视图
							resetView(true);
							//设置新视图
							setView(d, view, date, true);
							if (!TextUtils.isEmpty(endDate)) {
								resetView(false);
							}
							tvTip.setVisibility(View.VISIBLE);
							tvTip.setText("请设置结束日期");
						} else if (!TextUtils.isEmpty(endDate)) {
							//当前选中时间在起始日期之后，并且有结束日期的，重置视图
							resetView(true);
							resetView(false);
							//设置新视图
							setView(d, view, date, true);
							tvTip.setVisibility(View.VISIBLE);
							tvTip.setText("请设置结束日期");
						} else {
							//设置当前选中日期为结束日期
							setView(d, view, date, false);
							Intent it = new Intent();
							it.putExtra("startDate", startDate);
							it.putExtra("endDate", endDate);
							setResult(RESULT_OK, it);
							finish();
						}
					}
				}
			});
			layCalendar.addView(c1);
		}
	}

	/**
	 * 设置当前视图为选中状态
	 *
	 * @param day
	 * @param view
	 * @param selectDate
	 * @param isStart    是否起始日期
	 */
	private void setView(Day day, View view, String selectDate, boolean isStart) {
		CalendarAdapter.ViewHolder holder;
		if (isStart) {
			CalendarAdapter.viewStart = view;
			startDate = selectDate;
			holder = (CalendarAdapter.ViewHolder) CalendarAdapter.viewStart.getTag();
		} else {
			CalendarAdapter.viewEnd = view;
			endDate = selectDate;
			holder = (CalendarAdapter.ViewHolder) CalendarAdapter.viewEnd.getTag();
		}
		holder.tv_calendar_item.setTextColor(Color.WHITE);
		holder.layItem.setBackgroundResource(R.drawable.calendar_order_item_bg);
		holder.tv_calendar_item.setTextSize(10);
		holder.tv_calendar_item.setText(day.getName() + "\n" + startName);
		holder.tv_calendar_lunar.setVisibility(View.GONE);

		Day dayItem = (Day) holder.tv_calendar_item.getTag();
		switch (dayItem.getType()) {
			case TODAY:
				holder.tv_calendar_item.setText(getString(R.string.today) + "\n" + (isStart ? startName : endDate));
				break;
			case TOMORROW:
				holder.tv_calendar_item.setText(getString(R.string.tomorrow) + "\n" + (isStart ? startName : endDate));
				break;
			case T_D_A_T:
				holder.tv_calendar_item.setText(getString(R.string.t_d_a_t) + "\n" + (isStart ? startName : endDate));
				break;
		}
	}

	/**
	 * 重置视图
	 *
	 * @param isStart 是否重置起始日期
	 */
	private void resetView(boolean isStart) {
		CalendarAdapter.ViewHolder holder;
		if (isStart) {
			holder = (CalendarAdapter.ViewHolder) CalendarAdapter.viewStart.getTag();
		} else {
			holder = (CalendarAdapter.ViewHolder) CalendarAdapter.viewEnd.getTag();
		}
		holder.tv_calendar_item.setTextColor(getResources().getColor(
				R.color.calendar_enable_color));
		holder.layItem.setBackgroundResource(R.drawable.normal_calendar_order_item_bg);
		holder.tv_calendar_item.setTextSize(12);
		holder.tv_calendar_lunar.setVisibility(View.VISIBLE);
		Day dayItem = (Day) holder.tv_calendar_item.getTag();
		switch (dayItem.getType()) {
			case TODAY:
				holder.tv_calendar_item.setText(getString(R.string.today));
				break;
			case TOMORROW:
				holder.tv_calendar_item.setText(getString(R.string.tomorrow));
				break;
			case T_D_A_T:
				holder.tv_calendar_item.setText(getString(R.string.t_d_a_t));
				break;
			case ENABLE:
				holder.tv_calendar_item.setText(dayItem.getName());
				break;
		}
		if (isStart) {
			CalendarAdapter.viewStart = null;
			startDate = "";
		} else {
			CalendarAdapter.viewEnd = null;
			endDate = "";
		}
	}

}
