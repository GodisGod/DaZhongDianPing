package SharedUtils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SharedUtils {
	// �洢�ļ���������
	private static final String FILE_NAME = "dianping";
	// �洢�����key
	private static final String NODE_NAME = "welcome";

	// ����boolean���͵�ֵ
	public static boolean getWelcomeBoolean(Context context) {
		// ͨ��getSharedPreferences����ָ��SharedPreference�ļ���ΪFILE_NAME,
		// ��дģʽΪMODE_PRIVATE ��ȡ����ֵΪNODE_NAME������
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(NODE_NAME, true);// Ĭ��ֵΪtrue ��Ĭ���ǵ�һ�ν���Ӧ�ó���
	}

	// д��boolean���͵�ֵ
	public static void putWelcomBoolean(Context context, boolean isFirst) {
		// ͨ��getSharedPreferences����ָ��SharedPreference�ļ���ΪFILE_NAME,
		// ��дģʽΪMODE_APPEND ��ͨ��edit�����õ�һ��editor����
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_APPEND).edit();
		// �����ֵ��
		editor.putBoolean(NODE_NAME, isFirst);
		editor.commit();
	}

	// д��һ��String���͵�����
	public static void putCityName(Context context, String cityName) {
		Editor editor = context.getSharedPreferences(FILE_NAME,
				Context.MODE_APPEND).edit();
		editor.putString("cityName", cityName);
		editor.commit();
	}

	// ��ȡһ��String���͵�����
	public static String getCityName(Context context) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND)
				.getString("cityName", "ѡ�����");
	}

}
