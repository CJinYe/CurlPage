package icox.cjy.seeobject;

import android.view.View;
import android.widget.AbsoluteLayout;

import com.icox.synfile.SynFileApplication;

/**
 * Created by icoxdev on 15-5-7.
 */
public class MyApplication extends SynFileApplication {

    public final static int BG_WIDTH = 1024;
    public final static int BG_HEIGHT = 768;

    // Android设备型号
    public final static String ANDROID_DEV_MODEL = android.os.Build.MODEL;

    public static int mScreenWidth = 0;
    public static int mScreenHeight = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppState.setApplicationContext(this);
        mAppState.getInstance();
    }

    public static void initViewPosition(View view, int[][] item, int i){
        int itemWith = (item[i][2] - item[i][0]) * mScreenWidth / BG_WIDTH;
        int itemHeight = (item[i][3] - item[i][1]) * mScreenHeight / BG_HEIGHT;
        AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(
                itemWith,
                itemHeight,
                item[i][0] * mScreenWidth / BG_WIDTH,
                item[i][1] * mScreenHeight / BG_HEIGHT);

        view.setLayoutParams(lp);
    }
}
