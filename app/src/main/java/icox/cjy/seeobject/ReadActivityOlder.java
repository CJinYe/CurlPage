package icox.cjy.seeobject;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.icox.synfile.info.Ebagbook;

import java.io.IOException;

import icox.cjy.seeobject.bean.Bean;
import icox.cjy.seeobject.bean.Constants;

/**
 * @author 陈锦业
 * @version $Rev$
 * @time 2017-9-18 11:20
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class ReadActivityOlder extends BaseActivity {

    private Context ontherContext;
    MediaPlayer mediaPlayer;
    int modID = 1;// 内部分类id，每次+1
    int picID = 1;// 图片id，每次+1
    int mp3ID = 31;// mp3序列id，每次+5
    int maxID = 0;// 单个分类图片总数
    boolean isSetMax = false;// 是否为计算总数模式

    //
    private final int[][] ITEM_OTHER = new int[][]{
            //            {202, 108, 532, 235, -1},
            //            {548, 142, 857, 333, -1},
            //            {205, 312, 535, 590, -1},
            //            {631, 588, 857, 741, -1},
            //            {17, 359, 170, 476, -1},
            //            {800, 377, 987, 466, -1},
            {202, 88, 532, 215, -1},
            {548, 132, 857, 333, -1},
            {205, 282, 535, 560, -1},
            {631, 518, 857, 671, -1},
            {17, 319, 170, 436, -1},
            {800, 347, 987, 436, -1},
            //            {202, 88, 532, 215, -1},
            //            {548, 132, 857, 333, -1},
            //            {205, 282, 535, 560, -1},
            //            {631, 508, 857, 661, -1},
            //            {17, 319, 170, 436, -1},
            //            {800, 347, 987, 436, -1},
    };
    private String mFilesName;
    private Ebagbook mEbagbook;
    private RelativeLayout mRelativeLayout;
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        modID = getIntent().getIntExtra(Bean.MODSIGN, 1);
        mFilesName = getIntent().getStringExtra("name");

        try {
            mEbagbook = Ebagbook.newInstance(Constants.FILE_PATH);
        } catch (Exception e) {
            Toast.makeText(this, "资源文件不存在！", Toast.LENGTH_LONG).show();
            finish();
        }

        Button toHome = (Button) findViewById(R.id.toHome);
        toHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        initView();
    }

    //
    private void initView() {


        // 获取屏幕宽高
        if (MyApplication.mScreenWidth == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getRealMetrics(dm);
            MyApplication.mScreenWidth = dm.widthPixels;
            MyApplication.mScreenHeight = dm.heightPixels;
            if (MyApplication.ANDROID_DEV_MODEL.equals("GS705B")) {
                MyApplication.mScreenHeight = 600;
            }
        }

        for (int i = 0; i < ITEM_OTHER.length; i++) {
            Button button = (Button) findViewById(R.id.yingyu01 + i);
            MyApplication.initViewPosition(button, ITEM_OTHER, i);
        }

        mImageView = (ImageView) findViewById(R.id.main_iv);
        pageBackgroundSetting();
    }

    private String pageBackgroundSetting() {
        String fileName = Bean.NAME_START + modID
                + Bean.NAME_MIDDLE + picID + Bean.NAME_END;
        String file = mEbagbook.getExtractFile(this, "" + mFilesName + "/" + fileName + ".jpg");

        if (file == null || TextUtils.isEmpty(file))
            file = mEbagbook.getExtractFile(this, "" + mFilesName + "/" + fileName + ".png");

        Glide.with(this).load(file).into(mImageView);

        return file;
    }

    @SuppressWarnings("rawtypes")

    private void StartPlay(String path) {
        if (path != null) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            }
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void ButtonClicked(View v) {
        int resourceId = 0;
        String fileName = null;
        String filePath = null;
        switch (v.getId()) {
            case R.id.zhongwen01:
            case R.id.zhongwen02:
            case R.id.zhongwen03:

                fileName = Bean.NAME_START + modID + Bean.MP3NAME_MIDDLE + picID + "_" + picID
                        + Bean.MP3NAME_END;

                if (mFilesName.equals(Constants.SHI_PIN_NAME) || mFilesName.equals(Constants.ZHI_YE_NAME))
                    fileName = Bean.NAME_START
                            + modID + Bean.MP3NAME_MIDDLE + picID + "_" + (mp3ID + 4)
                            + Bean.MP3NAME_END;

                //                if (resourcesPackage.contains(".sp")
                //                        || resourcesPackage.contains(".zy")) {
                //                    fileName = Bean.NAME_START + modID + Bean.MP3NAME_MIDDLE + picID + "_"
                //                            + (mp3ID + 4) + Bean.MP3NAME_END;
                //                }
                break;
            case R.id.yingyu01:
            case R.id.yingyu02:

                fileName = Bean.NAME_START
                        + modID + Bean.MP3NAME_MIDDLE + picID + "_" + mp3ID
                        + Bean.MP3NAME_END;
                break;
            case R.id.duanju:
                fileName = Bean.NAME_START
                        + modID + Bean.MP3NAME_MIDDLE + picID + "_" + (mp3ID + 4)
                        + Bean.MP3NAME_END;
                if (mFilesName.equals(Constants.SHI_PIN_NAME) || mFilesName.equals(Constants.ZHI_YE_NAME))
                    fileName = Bean.NAME_START + modID + Bean.MP3NAME_MIDDLE + picID + "_" + picID
                            + Bean.MP3NAME_END;

                //                if (resourcesPackage.contains(".sp")
                //                        || resourcesPackage.contains(".zy")) {
                //                    fileName = Bean.NAME_START
                //                            + modID + Bean.MP3NAME_MIDDLE + picID + "_" + picID
                //                            + Bean.MP3NAME_END;
                //                }
                break;
        }

        if (fileName != null)
            filePath = mEbagbook.getExtractFile(this, "" + mFilesName + "/" + fileName + ".ogg");

        if (filePath == null || TextUtils.isEmpty(filePath))
            filePath = mEbagbook.getExtractFile(this, "" + mFilesName + "/" + fileName + ".mp3");

        if (filePath != null)
            StartPlay(filePath);
    }

    public void PageButtonClicked(View v) {
        boolean isDown = false;
        switch (v.getId()) {
            case R.id.topage_up:
                if (picID > 1) {
                    picID--;
                    mp3ID = mp3ID - 5;
                }
                break;

            case R.id.topage_down:
                //                if (picID < maxID) {
                //                }

                isDown = true;
                picID++;
                mp3ID = mp3ID + 5;
                break;
        }
        String filePath = pageBackgroundSetting();
        if (isDown && (filePath == null || TextUtils.isEmpty(filePath))) {
            mp3ID = mp3ID - 5;
            picID--;
            pageBackgroundSetting();
        }


    }

    @Override
    public void finish() {
        super.finish();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
    }

    public void AntiDecompile() {
        while (true) {
            int i = 0;
            i++;
            System.out.println("" + i);
        }
    }

}
