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
	// 注解形式的初始化
	@ViewInject(R.id.index_top_city)
	private TextView topCity;
	// 当前城市名称
	private String cityName;
	private LocationManager locationManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home_index, null);
		ViewUtils.inject(this, view);
		// 获取数据并显示
		topCity.setText(SharedUtils.getCityName(getActivity()));
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		checkGPSIsOpen();
	}

	// 检查是否开启了GPS
	private void checkGPSIsOpen() {
		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		boolean isOpen = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!isOpen) {
			// 进入GPS设置页面
			Intent intent = new Intent();
			// ????????????????????????
			intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivityForResult(intent, 0);
			Log.i("LHD", "isopen");
		}
		// 开始定位
		startLocation();
	}

	private void startLocation() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				2000, 10, this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 保存城市
		SharedUtils.putCityName(getActivity(), cityName);
		// 停止定位
		stopLocation();
	}

	// 停止定位
	private void stopLocation() {
		locationManager.removeUpdates(this);
	}

	// 接受并且处理信息
	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			if (msg.what == 1) {
				topCity.setText(cityName);
			}
			return false;
		}
	});

	// 位置信息更改执行的方法
	@Override
	public void onLocationChanged(Location location) {
		// 更新当前的位置信息
		updataWithNewLocation(location);
	}

	// 获取对应位置的经纬度 并且定位城市
	private void updataWithNewLocation(Location location) {
		double latitude = 0.0;
		double longitude = 0.0;
		if (location != null) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			Log.i("LHD", "经度是：  " + latitude + "  维度是：  " + longitude);
		} else {
			cityName = "无法获取城市信息";
		}
		// 通过经纬度获取地址 由于地址会有多个，这个和经纬度精确度有关 不精准的时候会得到多个返回值
		// 本实例中定义了最大的返回数2 即在即合理有两个值
		List<Address> list = null;
		// 将经纬度转化为地址信息 或者反过来
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
				cityName = address.getLocality();// 获取城市
			}
		}
		// 发送空消息
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
