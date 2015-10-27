package com.example.dazhongdianping;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import fragment.HomeFragment;
import fragment.MyFragment;
import fragment.SearchFragment;
import fragment.TuanFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity implements
		OnCheckedChangeListener {
	// 使用xUtils框架 使用注解形式初始化控件
	@ViewInject(R.id.main_bottom_tabs)
	private RadioGroup group;
	@ViewInject(R.id.main_home)
	private RadioButton main_home;
	// 管理fragment
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		// 初始化FragmentManager
		fragmentManager = getSupportFragmentManager();
		// 设置默认选中
		main_home.setChecked(true);
		group.setOnCheckedChangeListener(this);
		// 切换不同的fragment
		changeFragment(new HomeFragment(), false);

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.main_home:// 首页
			changeFragment(new HomeFragment(), true);
			break;
		case R.id.main_tuan:// 团购
			changeFragment(new TuanFragment(), true);
			break;
		case R.id.main_faxian:// 发现
			changeFragment(new SearchFragment(), true);
			break;
		case R.id.main_wode:// 我的
			changeFragment(new MyFragment(), true);
			break;

		default:
			break;
		}
	}

	// 切换不同的fragment
	public void changeFragment(Fragment fragment, boolean isInit) {
		// 开启一个事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 填充main_content
		transaction.replace(R.id.main_content, fragment);
		if(!isInit){
			transaction.addToBackStack(null);
		}
		// 提交事务
		transaction.commit();
	}
}
