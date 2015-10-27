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
	// ʹ��xUtils��� ʹ��ע����ʽ��ʼ���ؼ�
	@ViewInject(R.id.main_bottom_tabs)
	private RadioGroup group;
	@ViewInject(R.id.main_home)
	private RadioButton main_home;
	// ����fragment
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		// ��ʼ��FragmentManager
		fragmentManager = getSupportFragmentManager();
		// ����Ĭ��ѡ��
		main_home.setChecked(true);
		group.setOnCheckedChangeListener(this);
		// �л���ͬ��fragment
		changeFragment(new HomeFragment(), false);

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.main_home:// ��ҳ
			changeFragment(new HomeFragment(), true);
			break;
		case R.id.main_tuan:// �Ź�
			changeFragment(new TuanFragment(), true);
			break;
		case R.id.main_faxian:// ����
			changeFragment(new SearchFragment(), true);
			break;
		case R.id.main_wode:// �ҵ�
			changeFragment(new MyFragment(), true);
			break;

		default:
			break;
		}
	}

	// �л���ͬ��fragment
	public void changeFragment(Fragment fragment, boolean isInit) {
		// ����һ������
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// ���main_content
		transaction.replace(R.id.main_content, fragment);
		if(!isInit){
			transaction.addToBackStack(null);
		}
		// �ύ����
		transaction.commit();
	}
}
