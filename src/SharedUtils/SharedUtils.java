package SharedUtils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SharedUtils {
	// 存储文件名的名称
	private static final String FILE_NAME = "dianping";
	// 存储对象的key
	private static final String NODE_NAME = "welcome";

	// 读出boolean类型的值
	public static boolean getWelcomeBoolean(Context context) {
		// 通过getSharedPreferences方法指定SharedPreference文件名为FILE_NAME,
		// 读写模式为MODE_PRIVATE 并取出键值为NODE_NAME的数据
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(NODE_NAME, true);// 默认值为true 即默认是第一次进入应用程序
	}

	// 写出boolean类型的值
	public static void putWelcomBoolean(Context context, boolean isFirst) {
		// 通过getSharedPreferences方法指定SharedPreference文件名为FILE_NAME,
		// 读写模式为MODE_APPEND 并通过edit方法得到一个editor对象
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_APPEND).edit();
		// 存入键值对
		editor.putBoolean(NODE_NAME, isFirst);
		editor.commit();
	}

	// 写入一个String类型的数据
	public static void putCityName(Context context, String cityName) {
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_APPEND).edit();
		editor.putString("cityName", cityName);
		editor.commit();
	}

	// 获取一个String类型的数据
	public static String getCityName(Context context) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND)
				.getString("cityName", "选择城市");
	}

}
