package com.example.dazhongdianping;

import java.util.Timer;
import java.util.TimerTask;

import SharedUtils.SharedUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Welcom extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		// ��ʱ��ת����1 ʹ��Handler��ʱ������Ϣ
		// new Handler(new Handler.Callback() {
		//
		// @Override
		// public boolean handleMessage(Message arg0) {
		// //������յ�����Ϣ�ķ���
		// startActivity(new
		// Intent(getApplicationContext(),MainActivity.class));
		// return false;
		// }
		// }).sendEmptyMessageDelayed(0, 3000);//��ʱ3�뷢��

		// ��ʱ��ת����2 ʹ��Timer��ʱ��
		Timer timer = new Timer();
		timer.schedule(new Task(), 1000);
	}

	class Task extends TimerTask {

		@Override
		public void run() {
			// ҳ����ת
			// ����ǵ�һ�ν��� getWelcomeBooleanĬ��Ϊfalse
			if (SharedUtils.getWelcomeBoolean(getBaseContext())) {
				startActivity(new Intent(Welcom.this, WelcomGuide.class));
				// ������ʼ�¼ �Ѿ����ǵ�һ������ ��NODE_NAME����Ϊfalse
				SharedUtils.putWelcomBoolean(getBaseContext(), false);
			} else {
				startActivity(new Intent(Welcom.this, MainActivity.class));
			}
		}

	}
}
