package com.robert.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvStartDate, tvEndDate;
    final int SELECTOR_DATA = 1101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        findViewById(R.id.btnSelectDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), CalendarSelectorActivity.class);
                if(!TextUtils.isEmpty(tvStartDate.getText().toString()))
                it.putExtra("startDate", tvStartDate.getText().toString());
                if(!TextUtils.isEmpty(tvEndDate.getText().toString()))
                it.putExtra("endDate", tvEndDate.getText().toString());
                startActivityForResult(it, SELECTOR_DATA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECTOR_DATA && resultCode == RESULT_OK) {
            tvStartDate.setText(data.getStringExtra("startDate"));
            tvEndDate.setText(data.getStringExtra("endDate"));
        }
    }
}
