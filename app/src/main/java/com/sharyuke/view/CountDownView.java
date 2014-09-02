package com.sharyuke.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Created by yuke on 7/3/14.
 * @version CountDownView yuke 0.0.４
 */
public class CountDownView extends TextView {
  private static final String TAG = "CountDownView";
  private static final int DAY = 86400;
  private static final int HOUR = 3600;
  private static final int MINUTE = 60;
  private static final int SECOND = 1000;

  /**
   * 计时状态
   */
  private static final int STATUS_COUNTING = 0x001;
  /**
   * 停止状态
   */
  private static final int STATUS_STOP = 0x002;

  private CountOver mCountOver;

  private int mCountTime = 0;
  private TimeHandler mTimeHandler;
  private String mDay, mHour, mMinute, mSecond;
  private boolean isFormat = true;

  /**
   * 构造函数　一般用于xml布局
   */
  public CountDownView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mTimeHandler = new TimeHandler(this);
  }

  /**
   * 设置倒计时总时间
   *
   * @param time 到时间总时间
   */
  public void setCountTime(String time) {
    this.mCountTime = str2int(time);
  }

  /**
   * 设置显示单位
   *
   * @param day 　天的单位　{@link CountDownView#setDay(String)}
   * @param hour 小时的单位　{@link CountDownView#setHour(String)}
   * @param minute 分的单位　{@link CountDownView#setMinute(String)}
   * @param second 秒的单位　{@link CountDownView#setSecond(String)}
   */
  public void setUnits(String day, String hour, String minute, String second) {
    setDay(day);
    setHour(hour);
    setMinute(minute);
    setSecond(second);
  }

  /**
   * {@link CountDownView#setUnits(String, String, String, String)}
   */
  public void setDay(String day) {
    this.mDay = day;
  }

  /**
   * {@link CountDownView#setUnits(String, String, String, String)}
   */
  public void setHour(String hour) {
    this.mHour = hour;
  }

  /**
   * {@link CountDownView#setUnits(String, String, String, String)}
   */
  public void setMinute(String minute) {
    this.mMinute = minute;
  }

  /**
   * {@link CountDownView#setUnits(String, String, String, String)}
   */
  public void setSecond(String second) {
    this.mSecond = second;
  }

  private int getTime() {
    if (mCountTime > 0) {
      return mCountTime--;
    } else {
      stopCount();
      if (mCountOver != null) {
        mCountOver.onCountOver();
      }
      return mCountTime;
    }
  }

  /**
   * 设置倒计时时间
   *
   * @param time 　倒计时时间
   */
  public void setCountTime(int time) {
    if (time < 0) {
      throw new IllegalArgumentException("count time must be positive number");
    }
    this.mCountTime = time;
  }

  /**
   * 设置倒计时时间　引用string.xml资源文件
   *
   * @param res 资源Id
   */
  public void setCountTimeByRes(int res) {
    setCountTime(getContext().getResources().getString(res));
  }

  /**
   * 是否格式化时间显示，例如９秒显示为09秒，
   *
   * @param isFormat 　true 需要
   */
  public void setFormat(boolean isFormat) {
    this.isFormat = isFormat;
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

  /**
   * 开始倒计时
   */
  public void startCount() {
    if (mTimeHandler != null) {
      mTimeHandler.removeMessages(STATUS_COUNTING);
      mTimeHandler.sendEmptyMessageDelayed(STATUS_COUNTING, 0);
    }
  }

  /**
   * 清除显示
   */
  public void clear() {
    stopCount();
    this.setText("");
  }

  /**
   * 设置倒计时归零回调函数
   *
   * @param countOver 回调
   */
  public void setOnCountOverListener(CountOver countOver) {
    this.mCountOver = countOver;
  }

  /**
   * 停止倒计时
   */
  public void stopCount() {
    if (mTimeHandler != null) {
      mTimeHandler.removeMessages(STATUS_COUNTING);
      mTimeHandler.sendEmptyMessageDelayed(STATUS_STOP, 0);
    }
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

      if (msg.what == STATUS_COUNTING) {
        sendEmptyMessageDelayed(STATUS_COUNTING, SECOND);
      } else {
        return;
      }
      StringBuilder timeStr = new StringBuilder();
      if (mDay != null) {
        timeStr.append(time / DAY + mDay + " ");
        time = time % DAY;
      }

      if (mHour != null) {
        int t = time / HOUR;
        timeStr.append((mDay == null ? t : formatTime(t)) + mHour + " ");
        time = time % HOUR;
      }

      if (mMinute != null) {
        int t = time / MINUTE;
        timeStr.append((mHour == null ? t : formatTime(t)) + mMinute + " ");
        time = time % MINUTE;
      }

      if (mSecond != null) {
        timeStr.append((mMinute == null ? time : formatTime(time)) + mSecond);
      } else {
        timeStr.append(time);
      }

      tv.setText(timeStr.toString());
    }
  }

  /**
   * 倒计时回调接口
   */
  public interface CountOver {
    void onCountOver();
  }

  private String formatTime(int time) {
    return !isFormat ? "" + time : (time > 9 ? "" + time : "0" + time);
  }
}
