package icox.cjy.seeobject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.icox.updateapp.defined_dialog.DownloadApp;
import com.icox.updateapp.service.UpdateAppService;
import com.icox.updateapp.utils.AES;
import com.icox.updateapp.utils.Util;

/**
 * @author 陈锦业
 * @version $Rev$
 * @time 2017-9-18 14:05
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateApp();
        initWindow();
    }

    public void initWindow() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected void updateApp() {
        AES aes = new AES();
        String packageName = aes.stringFromJNICheck(this, this.getPackageName());
        if (!packageName.equals(this.getPackageName())) {
            Toast.makeText(this, "该软件为盗版软件,请安装正版软件！", Toast.LENGTH_LONG).show();
        }

        Intent intentService = new Intent(this, UpdateAppService.class);
        this.startService(intentService);
        SharedPreferences mCheckSettings = this.getSharedPreferences("UpdateAppService", 0);
        int appUpdate = mCheckSettings.getInt("appUpdate", 0);
        String versionName = mCheckSettings.getString("versionName", "");
        if (!versionName.equals(Util.getAPPVersionName(this)) && appUpdate == 2) {
            Intent intentUpdate = new Intent(this, DownloadApp.class);
            this.startActivity(intentUpdate);
            this.finish();
        }

    }

}
