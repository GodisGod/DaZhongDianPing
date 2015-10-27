package com.example.dazhongdianping;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomGuide extends Activity {
	@ViewInject(R.id.welcom_guide_btn)
	private Button btn;
	@ViewInject(R.id.welcom_pager)
	private ViewPager pager;

	private List<View> list;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom_guide);
		ViewUtils.inject(this);
		initViewPager();
	}

	@OnClick(R.id.welcom_guide_btn)
	public void click(View view) {
		// 页面跳转
		startActivity(new Intent(getBaseContext(), MainActivity.class));
		finish();
	}

	// 初始化ViewPager的方法
	public void initViewPager() {
		list = new ArrayList<View>();
		ImageView iv = new ImageView(this);
		iv.setImageResource(R.drawable.guide_01);
		list.add(iv);
		ImageView iv2 = new ImageView(this);
		// 这里错了
		// iv.setImageResource(R.drawable.guide_02);
		iv2.setImageResource(R.drawable.guide_02);
		list.add(iv2);
		ImageView iv3 = new ImageView(this);
		iv3.setImageResource(R.drawable.guide_03);
		list.add(iv3);
		pager.setAdapter(new MyPagerAdapter());

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// 如果是第二个页面
				if (arg0 == 2) {
					btn.setVisibility(View.VISIBLE);
				} else {
					btn.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	// 定义ViewPager的适配器
	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		// 初始化item实例的方法
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));
			return list.get(position);
		}

		// item销毁的方法
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list.get(position));
		}
	}
}
