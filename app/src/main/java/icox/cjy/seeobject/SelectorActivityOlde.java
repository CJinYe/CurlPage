package icox.cjy.seeobject;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import icox.cjy.seeobject.bean.Bean;

/**
 * @author 陈锦业
 * @version $Rev$
 * @time 2017-9-18 10:58
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class SelectorActivityOlde extends BaseActivity {
    private String mName;

    //
    private final int[][] ITEM_OTHER = new int[][]{
            {180, 442, 517, 637, -1},
            {475, 47, 743, 312, -1},
            {58, 218, 269, 423, -1},
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        mName = getIntent().getStringExtra("name");
        int ID = getIntent().getIntExtra("id",0);
        switch (ID) {
            case 1:
                getWindow().setBackgroundDrawableResource(R.drawable.background_bz);
                break;
            case 2:
                //车标
                getWindow().setBackgroundDrawableResource(R.drawable.background_cb);
                break;
            case 3:
                getWindow().setBackgroundDrawableResource(R.drawable.background_dw);
                break;
            case 4:
                //国旗
                getWindow().setBackgroundDrawableResource(R.drawable.background_gq);
                break;
            case 5:
                getWindow().setBackgroundDrawableResource(R.drawable.background_jtgj);
                break;
            case 6:
                //恐龙
                getWindow().setBackgroundDrawableResource(R.drawable.background_kl);
                break;
            case 7:
                getWindow().setBackgroundDrawableResource(R.drawable.background_ryp);
                break;
            case 8:
                getWindow().setBackgroundDrawableResource(R.drawable.background_sp);
                break;
            case 9:
                getWindow().setBackgroundDrawableResource(R.drawable.background_zr);
                break;
            case 10:
                getWindow().setBackgroundDrawableResource(R.drawable.background_zy);
                break;
        }

        initView();

        findViewById(R.id.page_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView(){
        // 获取屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        MyApplication.mScreenWidth = dm.widthPixels;
        MyApplication.mScreenHeight = dm.heightPixels;
        if (MyApplication.ANDROID_DEV_MODEL.equals("GS705B")){
            MyApplication.mScreenHeight = 600;
        }

        for (int i = 0; i < ITEM_OTHER.length; i++){
            Button button = (Button) findViewById(R.id.fenlei01 + i);
            MyApplication.initViewPosition(button, ITEM_OTHER, i);
        }
    }

    public void AntiDecompile() {
        while (true) {
            int i = 0;
            i++;
            System.out.println("" + i);
        }
    }

    public void PacthButtonClick(View v) {

        Intent intent = new Intent();
        intent.setClass(this, ReadActivity.class);
        //		intent.putExtra(Bean.PKGSIGN, resourcePath);
        int ModID = 1;
        switch (v.getId()) {
            case R.id.fenlei01:
                ModID = 1;
                break;
            case R.id.fenlei02:
                ModID = 2;
                break;
            case R.id.fenlei03:
                ModID = 3;
                break;
        }
        intent.putExtra(Bean.MODSIGN, ModID);
        intent.putExtra("name", mName);
        startActivity(intent);
    }

}
