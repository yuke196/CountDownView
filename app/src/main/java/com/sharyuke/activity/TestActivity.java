package com.sharyuke.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sharyuke.R;
import com.sharyuke.view.CountDownView;

public class TestActivity extends Activity {

  CountDownView mcdv;
  EditText et;
  Button bt1, bt2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    mcdv = (CountDownView) findViewById(R.id.test);
    bt1 = (Button) findViewById(R.id.start);
    bt2 = (Button) findViewById(R.id.stop);
    et = (EditText) findViewById(R.id.count);

    mcdv.setUnits("天", "时", "分", "秒");
  }

  public void click(View v) {
    switch (v.getId()) {
      case R.id.start:

        mcdv.setCountTime(et.getText().toString());
        mcdv.startCount();
        break;

      case R.id.stop:
        mcdv.stopCount();
        break;
    }
  }
}