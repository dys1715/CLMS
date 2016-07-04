package dys.clms.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dys on 2016/6/30 0030.
 * 数据库操作
 */
public class CommonMethod {
    public static int setSharedPreference(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("use_count", Context.MODE_PRIVATE);
        int count = sharedPreferences.getInt("count", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        count += 1;
        editor.putInt("count", count);
        editor.apply();
        return count;
    }
}
