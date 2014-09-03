package com.sharyuke.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.sharyuke.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类选择器
 * Created by sharyuke on 9/3/14.
 */
public class ClassifyChooserView extends LinearLayout {
  private static final String TAG = "ClassifyChooserView";
  private Activity mActivity;
  private String[] mRootMenu;
  private ChooserItemOnclickListener mChooserItemOnclickListener;
  private int firstPosition = 0;
  private LinearLayout mContainerFirst, mContainerSecond, mContainerThird;
  private FragmentManager mFragmentManager;
  private Map<String, ChooserFragment> mTags;

  public ClassifyChooserView(Context context, AttributeSet attrs) {
    super(context, attrs);
    if (!(context instanceof Activity)) {
      Log.e(TAG, "ClassifyChooserView can not load view,context must instanceof Activity ");
      return;
    }
    addView(LayoutInflater.from(context).inflate(R.layout.sharyuke_layout_classify_chooser, null));
    mActivity = (Activity) context;
    mFragmentManager = mActivity.getFragmentManager();
    mTags = new HashMap<String, ChooserFragment>();
    mContainerFirst = (LinearLayout) findViewById(R.id.sharyuke_container_first);
    mContainerSecond = (LinearLayout) findViewById(R.id.sharyuke_container_second);
    mContainerThird = (LinearLayout) findViewById(R.id.sharyuke_container_third);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    mContainerFirst.getLayoutParams().width = (r - l) / 3;
    mContainerSecond.getLayoutParams().width = (r - l) / 3;
    mContainerThird.getLayoutParams().width = (r - l) / 3;
  }

  public ClassifyChooserView setRootMenu(String[] rootMenu) {
    this.mRootMenu = rootMenu;
    return this;
  }

  public ClassifyChooserView setRootMenu(List<String> rootMenu) {
    if (rootMenu == null) {
      Log.e(TAG, "you can not set rootMenu null!");
      return this;
    }
    String[] data = new String[rootMenu.size()];
    for (int i = 0; i < rootMenu.size(); i++) {
      data[i] = rootMenu.get(i);
    }
    this.mRootMenu = data;
    return this;
  }

  public ClassifyChooserView setChooserItemOnclickListener(
      ChooserItemOnclickListener chooserItemOnclickListener) {
    this.mChooserItemOnclickListener = chooserItemOnclickListener;
    return this;
  }

  private void dismiss() {
    this.setVisibility(View.GONE);
  }

  public void build() {
    if (mRootMenu == null) {
      Log.e(TAG, "rootMenu must not be null!");
      return;
    }
    loadContent(mRootMenu, 0, 0);
  }

  private void loadContent(String[] data, int menuTier, int position) {

    ChooserFragment chooserFragment = (ChooserFragment) mFragmentManager.findFragmentByTag(
        menuTier + "-" + String.valueOf(position));
    if (chooserFragment == null) {
      chooserFragment = ChooserFragment.getInstance(menuTier, data, mActivity, position);
    }
    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

    if (menuTier == firstPosition + 3) {
      ChooserFragment f0 =
          (ChooserFragment) mFragmentManager.findFragmentById(R.id.sharyuke_container_first);
      ChooserFragment f1 =
          (ChooserFragment) mFragmentManager.findFragmentById(R.id.sharyuke_container_second);
      ChooserFragment f2 =
          (ChooserFragment) mFragmentManager.findFragmentById(R.id.sharyuke_container_third);
      mTags.put(String.valueOf(firstPosition), f0);
      mTags.put(String.valueOf(firstPosition + 1), f1);
      mTags.put(String.valueOf(firstPosition + 2), f2);
      fragmentTransaction.remove(f0);
      fragmentTransaction.remove(f1);
      fragmentTransaction.remove(f2);
      ChooserFragment chooserFragment1 = mTags.get(String.valueOf(firstPosition + 1));
      ChooserFragment chooserFragment2 = mTags.get(String.valueOf(firstPosition + 2));
      fragmentTransaction.replace(R.id.sharyuke_container_first,
          ChooserFragment.getInstance(chooserFragment1.getmMenuTier(), chooserFragment1.getData(),
              mActivity, chooserFragment1.getPosition())
              .setChooserItemOnclickListener(mInnerChooserItemOnclickListener))
          .replace(R.id.sharyuke_container_second,
              ChooserFragment.getInstance(chooserFragment2.getmMenuTier(),
                  chooserFragment2.getData(), mActivity, chooserFragment2.getPosition())
                  .setChooserItemOnclickListener(mInnerChooserItemOnclickListener))
          .replace(R.id.sharyuke_container_third, chooserFragment)
          .commit();
      firstPosition++;
    } else if (menuTier - 1 == firstPosition && firstPosition != 0) {
      ChooserFragment f0 =
          (ChooserFragment) mFragmentManager.findFragmentById(R.id.sharyuke_container_first);
      ChooserFragment f1 =
          (ChooserFragment) mFragmentManager.findFragmentById(R.id.sharyuke_container_second);
      ChooserFragment f2 =
          (ChooserFragment) mFragmentManager.findFragmentById(R.id.sharyuke_container_third);
      mTags.put(String.valueOf(firstPosition), f0);
      mTags.put(String.valueOf(firstPosition + 1), f1);
      fragmentTransaction.remove(f0);
      fragmentTransaction.remove(f1);
      fragmentTransaction.remove(f2);
      ChooserFragment chooserFragment0 = mTags.get(String.valueOf(firstPosition - 1));
      ChooserFragment chooserFragment1 = mTags.get(String.valueOf(firstPosition));
      ChooserFragment chooserFragment2 = mTags.get(String.valueOf(firstPosition + 1));
      fragmentTransaction.replace(R.id.sharyuke_container_first,
          ChooserFragment.getInstance(chooserFragment0.getmMenuTier(), chooserFragment0.getData(),
              mActivity, chooserFragment0.getPosition())
              .setChooserItemOnclickListener(mInnerChooserItemOnclickListener))
          .replace(R.id.sharyuke_container_second,
              ChooserFragment.getInstance(chooserFragment1.getmMenuTier(),
                  chooserFragment1.getData(), mActivity, chooserFragment1.getPosition())
                  .setChooserItemOnclickListener(mInnerChooserItemOnclickListener))
          .replace(R.id.sharyuke_container_third,
              ChooserFragment.getInstance(chooserFragment2.getmMenuTier(),
                  chooserFragment2.getData(), mActivity, chooserFragment2.getPosition())
                  .setChooserItemOnclickListener(mInnerChooserItemOnclickListener))
          .commit();
      mTags.remove(String.valueOf((firstPosition - 1)));
      firstPosition--;
    } else if (menuTier == firstPosition + 1) {
      fragmentTransaction.replace(R.id.sharyuke_container_second, chooserFragment);
      Fragment fragment = mFragmentManager.findFragmentById(R.id.sharyuke_container_third);
      if (fragment != null) {
        fragmentTransaction.remove(fragment);
      }
      fragmentTransaction.commit();
    } else if (menuTier == firstPosition + 2) {
      mFragmentManager.beginTransaction()
          .replace(R.id.sharyuke_container_third, chooserFragment)
          .commit();
    } else if (firstPosition == 0 && menuTier == 0) {
      fragmentTransaction.replace(R.id.sharyuke_container_first, chooserFragment);
      Fragment fragment = mFragmentManager.findFragmentById(R.id.sharyuke_container_second);
      if (fragment != null) {
        fragmentTransaction.remove(fragment);
      }
      fragmentTransaction.commit();
    }

    chooserFragment.setChooserItemOnclickListener(mInnerChooserItemOnclickListener);
  }

  ChooserItemOnclickListener mInnerChooserItemOnclickListener = new ChooserItemOnclickListener() {
    @Override
    public String[] onItemClick(int menuTier, int position) {
      if (mChooserItemOnclickListener == null) {
        return null;
      }
      String[] data = mChooserItemOnclickListener.onItemClick(menuTier, position);
      if (data == null) {
        dismiss();
      } else {
        loadContent(data, menuTier + 1, position);
      }
      return data;
    }
  };

  public static class ChooserFragment extends Fragment {

    private Context mContext;
    private String[] data;
    private ChooserItemOnclickListener mChooserItemOnclickListener;
    private int mMenuTier;
    private int position;

    public static ChooserFragment getInstance(int menuTier, String[] data, Context context,
        int position) {
      ChooserFragment chooserFragment = new ChooserFragment();
      chooserFragment.mContext = context;
      chooserFragment.data = data;
      chooserFragment.mMenuTier = menuTier;
      chooserFragment.position = position;
      return chooserFragment;
    }

    public String[] getData() {
      return data;
    }

    public int getmMenuTier() {
      return mMenuTier;
    }

    public int getPosition() {
      return position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      View layout = inflater.inflate(R.layout.sharyuke_layout_list_view, null);
      ListView dataList = (ListView) layout.findViewById(R.id.sharyuke_classify_chooser_list);
      dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          if (mChooserItemOnclickListener != null) {
            mChooserItemOnclickListener.onItemClick(mMenuTier, position);
          }
        }
      });
      dataList.setAdapter(new ChooserAdapter());
      return layout;
    }

    public ChooserFragment setChooserItemOnclickListener(
        ChooserItemOnclickListener chooserItemOnclickListener) {
      this.mChooserItemOnclickListener = chooserItemOnclickListener;
      return this;
    }

    private class ChooserAdapter extends BaseAdapter {

      @Override
      public int getCount() {
        return data == null ? 0 : data.length;
      }

      @Override
      public Object getItem(int position) {
        return data == null ? null : data[position];
      }

      @Override
      public long getItemId(int position) {
        return position;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
          vh = new ViewHolder();
          convertView =
              LayoutInflater.from(mContext).inflate(R.layout.sharyuke_layout_item_view, null);
          vh.textView = (TextView) convertView.findViewById(R.id.sharyuke_item_text_view);
          convertView.setTag(vh);
        } else {
          vh = (ViewHolder) convertView.getTag();
        }
        vh.textView.setText(data[position]);
        return convertView;
      }

      class ViewHolder {
        TextView textView;
      }
    }
  }

  public interface ChooserItemOnclickListener {
    String[] onItemClick(int menuTier, int position);
  }
}
