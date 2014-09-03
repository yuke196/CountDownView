package com.sharyuke.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;
import com.sharyuke.R;
import com.sharyuke.view.ClassifyChooserView;
import com.sharyuke.view.CountDownView;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Activity {

  CountDownView mcdv;
  EditText et;
  Button bt1, bt2;
  ClassifyChooserView mClassifyChooserView;
  List<String> ls;
  String[][] content = {
      { "香蕉", "香蕉" }, { "床", "美丽的小三", "枕头", "蚊香" },
      { "横多数据", "横多数据", "横多数据", "横多数据", "横多数据", "横多数据", "横多数据", "横多数据", "横多数据", "横多数据", "横多数据", "横多数据", "横多数据", "横多数据",
          "横多数据", "横多数据", "横多数据" },
      { "内裤", "不知道穿傻子", "明天给我穿", "1", "2", "3", "全部脱了嘛", "5", "6", "7", "8", "9", "", "" }, { "全部" }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    mcdv = (CountDownView) findViewById(R.id.test);
    bt1 = (Button) findViewById(R.id.start);
    bt2 = (Button) findViewById(R.id.stop);
    et = (EditText) findViewById(R.id.count);
    mClassifyChooserView = (ClassifyChooserView) findViewById(R.id.test_chooser);
    ls = new ArrayList<String>();
    ls.add("吃的");
    ls.add("睡的");
    ls.add("耍的");
    ls.add("穿的");
    ls.add("走的");
    ls.add("天使");
    mClassifyChooserView.setRootMenu(ls)
        .setChooserItemOnclickListener(new ClassifyChooserView.ChooserItemOnclickListener() {
          @Override
          public String[] onItemClick(int menuTier, int position) {
            if (menuTier == 0 || menuTier == 1) {
              return content.length < position + 1 ? null : content[position];
            } else {
              Toast.makeText(TestActivity.this, "点击了---> " + position, Toast.LENGTH_LONG).show();
              return null;
            }
          }
        })
        .build();

    mcdv.setUnits("天", "时", "分", "秒");
    mcdv.setOnCountOverListener(new CountDownView.CountOver() {
      @Override
      public void onCountOver() {
        Toast.makeText(TestActivity.this, "回调了！！！", Toast.LENGTH_LONG).show();
      }
    });
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