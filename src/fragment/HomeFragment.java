package fragment;

import java.io.IOException;
import java.util.List;

import com.example.dazhongdianping.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import SharedUtils.SharedUtils;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment implements LocationListener {
	// ע����ʽ�ĳ�ʼ��
	@ViewInject(R.id.index_top_city)
	private TextView topCity;
	// ��ǰ��������
	private String cityName;
	private LocationManager locationManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_index, null);
		ViewUtils.inject(this, view);
		// ��ȡ���ݲ���ʾ
		topCity.setText(SharedUtils.getCityName(getActivity()));
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		checkGPSIsOpen();
	}

	// ����Ƿ�����GPS
	private void checkGPSIsOpen() {
		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		boolean isOpen = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!isOpen) {
			// ����GPS����ҳ��
			Intent intent = new Intent();
			// ????????????????????????
			intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivityForResult(intent, 0);
			Log.i("LHD", "isopen");
		}
		// ��ʼ��λ
		startLocation();
	}

	private void startLocation() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				2000, 10, this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// �������
		SharedUtils.putCityName(getActivity(), cityName);
		// ֹͣ��λ
		stopLocation();
	}

	// ֹͣ��λ
	private void stopLocation() {
		locationManager.removeUpdates(this);
	}

	// ���ܲ��Ҵ�����Ϣ
	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			if (msg.what == 1) {
				topCity.setText(cityName);
			}
			return false;
		}
	});

	// λ����Ϣ����ִ�еķ���
	@Override
	public void onLocationChanged(Location location) {
		// ���µ�ǰ��λ����Ϣ
		updataWithNewLocation(location);
	}

	// ��ȡ��Ӧλ�õľ�γ�� ���Ҷ�λ����
	private void updataWithNewLocation(Location location) {
		double latitude = 0.0;
		double longitude = 0.0;
		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			Log.i("LHD", "�����ǣ�  " + latitude + "  ά���ǣ�  " + longitude);
		} else {
			cityName = "�޷���ȡ������Ϣ";
		}
		// ͨ����γ�Ȼ�ȡ��ַ ���ڵ�ַ���ж��������;�γ�Ⱦ�ȷ���й� ����׼��ʱ���õ��������ֵ
		// ��ʵ���ж��������ķ�����2 ���ڼ�����������ֵ
		List<Address> list = null;
		// ����γ��ת��Ϊ��ַ��Ϣ ���߷�����
		Geocoder geocoder = new Geocoder(getActivity());
		try {
			list = geocoder.getFromLocation(latitude, longitude, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Address address = list.get(i);
				cityName = address.getLocality();// ��ȡ����
			}
		}
		// ���Ϳ���Ϣ
		handler.sendEmptyMessage(1);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
}
