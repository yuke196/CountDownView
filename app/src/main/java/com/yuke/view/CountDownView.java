package com.yuke.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * @author Created by yuke on 7/3/14.
 * @version CountDownView yuke 0.0.1
 */
public class CountDownView extends TextView {
  private static final String TAG = "CountDownView";
  private static final int DAY = 86400;
  private static final int HOUR = 3600;
  private static final int MINUTE = 60;

  private CountOver mCountOver;

  private int mCountTime = 0;
  private TimeHandler mTimeHandler;
  private CountRunnable mCountRunnable;
  private String mDay, mHour, mMinute, mSecond;

  public CountDownView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mTimeHandler = new TimeHandler(this);
  }

  public void setCountTime(String time) {
    this.mCountTime = str2int(time);
  }

  public void setUnits(String day, String hour, String minute, String second) {
    setDay(day);
    setHour(hour);
    setMinute(minute);
    setSecond(second);
  }

  public void setDay(String day) {
    this.mDay = day;
  }

  public void setHour(String hour) {
    this.mHour = hour;
  }

  public void setMinute(String minute) {
    this.mMinute = minute;
  }

  public void setSecond(String second) {
    this.mSecond = second;
  }

  private int getTime() {
    if (mCountTime > 0) {
      return mCountTime--;
    } else {
      mCountRunnable.stopCount();
      if (mCountOver != null) {
        mCountOver.onCountOver();
      }
      return mCountTime;
    }
  }

  public void setCountTime(int time) {
    this.mCountTime = time;
  }

  public void setCountTimeByRes(int res) {
    setCountTime(getContext().getResources().getString(res));
  }

  private void checkIsValid(String time) {
    if (TextUtils.isEmpty(time)) {
      throw new IllegalArgumentException("count time must not be null or empty");
    }

    for (int i = 0; i < time.length(); i++) {
      if ('0' > time.charAt(i) || time.charAt(i) > '9') {
        throw new IllegalArgumentException("count time must be positive number");
      }
    }
  }

  private int str2int(String time) {
    checkIsValid(time);
    return Integer.parseInt(time);
  }

  public void startCount() {
    if (mCountRunnable != null) {
      mCountRunnable.stopCount();
    }
    mCountRunnable = new CountRunnable(mTimeHandler);
    new Thread(mCountRunnable).start();
  }

  public void clear() {
    if (mCountRunnable != null) {
      mCountRunnable.stopCount();
    }
    this.setText("");
  }

  public void setOnCountOverListener(CountOver countOver) {
    this.mCountOver = countOver;
  }

  public void stopCount() {
    mCountRunnable.stopCount();
  }

  private class TimeHandler extends Handler {
    CountDownView tv;

    public TimeHandler(CountDownView tv) {
      this.tv = tv;
    }

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      int time = tv.getTime();
      StringBuilder timeStr = new StringBuilder();
      if (mDay != null) {
        timeStr.append(time / DAY + mDay + " ");
        time = time % DAY;
      }

      if (mHour != null) {
        timeStr.append(time / HOUR + mHour + " ");
        time = time % HOUR;
      }

      if (mMinute != null) {
        timeStr.append(time / MINUTE + mMinute + " ");
        time = time % MINUTE;
      }

      if (mSecond != null) {
        timeStr.append(time + mSecond);
      } else {
        timeStr.append(time);
      }

      tv.setText(timeStr.toString());
    }
  }

  private class CountRunnable implements Runnable {
    Handler mHandler;
    boolean isRunning;

    public CountRunnable(Handler mHandler) {
      this.mHandler = mHandler;
      this.isRunning = true;
    }

    @Override
    public void run() {
      while (isRunning) {
        mHandler.sendEmptyMessage(0x001);
        try {
          Thread.sleep(1000);
        } catch (Exception e) {
          Log.e(TAG, e.getMessage());
        }
      }
    }

    public void stopCount() {
      this.isRunning = false;
    }
  }

  interface CountOver {
    void onCountOver();
  }
}
