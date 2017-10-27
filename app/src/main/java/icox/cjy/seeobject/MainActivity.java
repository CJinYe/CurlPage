package icox.cjy.seeobject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.icox.updateapp.defined_dialog.DownloadApp;
import com.icox.updateapp.service.UpdateAppService;
import com.icox.updateapp.utils.AES;
import com.icox.updateapp.utils.Util;

import icox.cjy.seeobject.bean.Constants;
import icox.cjy.seeobject.utils.ScaleAnimEffect;

import static icox.cjy.seeobject.bean.Constants.BIAO_ZI;
import static icox.cjy.seeobject.bean.Constants.BIAO_ZI_NAME;
import static icox.cjy.seeobject.bean.Constants.CHE_BIAO;
import static icox.cjy.seeobject.bean.Constants.DONG_WU;
import static icox.cjy.seeobject.bean.Constants.DONG_WU_NAME;
import static icox.cjy.seeobject.bean.Constants.GUO_QI;
import static icox.cjy.seeobject.bean.Constants.JIAO_TONG_GONG_JU;
import static icox.cjy.seeobject.bean.Constants.JIAO_TONG_GONG_JU_NAME;
import static icox.cjy.seeobject.bean.Constants.KONG_LONG;
import static icox.cjy.seeobject.bean.Constants.RI_YONG_PIN;
import static icox.cjy.seeobject.bean.Constants.RI_YONG_PIN_NAME;
import static icox.cjy.seeobject.bean.Constants.SHI_PIN;
import static icox.cjy.seeobject.bean.Constants.SHI_PIN_NAME;
import static icox.cjy.seeobject.bean.Constants.ZHI_YE;
import static icox.cjy.seeobject.bean.Constants.ZHI_YE_NAME;
import static icox.cjy.seeobject.bean.Constants.ZI_RAN;
import static icox.cjy.seeobject.bean.Constants.ZI_RAN_NAME;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        updateApp();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    public void ImageClicked(View v) {
        ScaleAnimEffect animEffect = new ScaleAnimEffect();
        animEffect.setAttributs(1.1F, 1.0F, 1.1F, 1.0F, 100L);
        v.startAnimation(animEffect.createAnimation());
        String className = "";
        String name = "";
        int ID = 0;
        switch (v.getId()) {
            case R.id.page_exit:
                finish();
                return;
            case R.id.launcher_pre_qmsw_bz:
                name = BIAO_ZI_NAME;
                ID = BIAO_ZI;
                className = "httpx://1&com.share.path.bz";
                break;
            case R.id.launcher_pre_qmsw_sp:
                name = SHI_PIN_NAME;
                ID = SHI_PIN;
                className = "httpx://8&com.share.path.sp";
                break;
            case R.id.launcher_pre_qmsw_dw:
                name = DONG_WU_NAME;
                ID = DONG_WU;
                className = "httpx://3&com.share.path.dw";
                break;
            case R.id.launcher_pre_qmsw_zr:
                name = ZI_RAN_NAME;
                ID = ZI_RAN;
                className = "httpx://9&com.share.path.zr";
                break;
            case R.id.launcher_pre_qmsw_zy:
                name = ZHI_YE_NAME;
                ID = ZHI_YE;
                className = "httpx://10&com.share.path.zy";
                break;
            case R.id.launcher_pre_qmsw_ryp:
                ID = RI_YONG_PIN;
                name = RI_YONG_PIN_NAME;
                className = "httpx://7&com.share.path.ryp";
                break;
            case R.id.launcher_pre_qmsw_jtgj:
                ID = JIAO_TONG_GONG_JU;
                name = JIAO_TONG_GONG_JU_NAME;
                className = "httpx://5&com.share.path.jtgj";
                break;
            case R.id.launcher_pre_qmsw_cheBiao:
                ID = CHE_BIAO;
                name = Constants.CHE_BIAO_NAME;
                break;
            case R.id.launcher_pre_qmsw_kong_long:
                ID = KONG_LONG;
                name = Constants.KONG_LONG_NAME;
                break;
            case R.id.launcher_pre_qmsw_guoqi:
                ID = GUO_QI;
                name = Constants.GUO_QI_NAME;
                break;
        }
        //        Intent intent = new Intent("andruid.intent.action.MAIN",
        //                Uri.parse(className));
        Intent intent = new Intent(MainActivity.this, SelectorActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("id", ID);
        try {
            startActivity(intent);
        } catch (Exception e) {
            //            ToastMessage.showMsg(getBaseContext(), "该模块缺失！");
            Log.e("myTest","模块缺失  = "+e);
            Toast.makeText(getBaseContext(), "该模块缺失！", Toast.LENGTH_SHORT).show();
        }

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
