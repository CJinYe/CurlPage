package icox.cjy.seeobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.icox.synfile.info.Ebagbook;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import java.io.IOException;
import java.util.List;

import icox.cjy.seeobject.bean.Bean;
import icox.cjy.seeobject.bean.Constants;
import icox.cjy.seeobject.views.CurlPage;
import icox.cjy.seeobject.views.CurlView;

/**
 * @author 陈锦业
 * @version $Rev$
 * @time 2017-9-18 11:20
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class ReadActivity extends BaseActivity {

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
    private CurlView mCurlView;
    private Context mContext;
    private int mPageCount;
    private Button mButNex;
    private Button mButLast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        mContext = this;
        modID = getIntent().getIntExtra(Bean.MODSIGN, 1);
        mFilesName = getIntent().getStringExtra("name");

        try {
            mEbagbook = Ebagbook.newInstance(Constants.FILE_PATH);
        } catch (Exception e) {
            Toast.makeText(this, "资源文件不存在！", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        net.lingala.zip4j.core.ZipFile ebagbook = mEbagbook.getZipFile();
        List files = null;
        try {
            files = ebagbook.getFileHeaders();
            for (int i = 0; i < files.size(); i++) {
                FileHeader header = (FileHeader) files.get(i);
                String name = header.getFileName();
                if (name.contains(mFilesName)
                        && name.contains(Bean.NAME_START + modID)
                        && (name.contains(".jpg") || name.contains(".png")))
                    mPageCount++;
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }


        int index = 0;
        if (getLastNonConfigurationInstance() != null) {
            index = (Integer) getLastNonConfigurationInstance();
        }
        mCurlView = (CurlView) findViewById(R.id.cur_view);
        mCurlView.setPageProvider(new PageProvider());
        mCurlView.setSizeChangedObserver(new SizeChangedObserver());
        mCurlView.setCurrentIndex(index);
        mCurlView.setBackgroundColor(0xFF202830);
        mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);

        mButNex = (Button) findViewById(R.id.topage_down);
        mButLast = (Button) findViewById(R.id.topage_up);

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

    private int lastPageIndex = 0;

    private class PageProvider implements CurlView.PageProvider {
        @Override
        public int getPageCount() {
            return mPageCount + 1;
        }

        @Override
        public void updatePage(CurlPage page, int width, int height, int index) {

            //            if (index - lastPageIndex > 0) {//下一页
            //                mp3ID = mp3ID + 5;
            //            } else if (index - lastPageIndex < 0) {//上一页
            //                if (picID > 1) {
            //                    mp3ID = mp3ID - 5;
            //                }
            //            }
            //            picID = index + 1;
            int picID = index + 1;

            lastPageIndex = index;

            String fileName = Bean.NAME_START + modID
                    + Bean.NAME_MIDDLE + picID + Bean.NAME_END;
            String file = mEbagbook.getExtractFile(mContext, "" + mFilesName + "/" + fileName + ".jpg");

            if (file == null || TextUtils.isEmpty(file))
                file = mEbagbook.getExtractFile(mContext, "" + mFilesName + "/" + fileName + ".png");

            Bitmap bitmap = BitmapFactory.decodeFile(file);
            page.setTexture(bitmap, CurlPage.SIDE_BOTH);

        }
    }

    private long lastClickTime = 0;

    public void PageButtonClicked(View v) {
        if (System.currentTimeMillis() - lastClickTime < 1000) {
            return;
        }

        switch (v.getId()) {
            case R.id.topage_up:
                if (picID > 1) {
                    picID--;
                    mp3ID = mp3ID - 5;
                    StartPlay(R.raw.fanshu);
                    mButNex.setBackgroundResource(R.drawable.pre_btn_next);
                }
                if (picID == 1)
                    mButLast.setBackgroundResource(R.drawable.pre_btn_pre_shouye);
                autoTouchLast();
                break;
            case R.id.topage_down:
                picID++;
                mp3ID = mp3ID + 5;
                if (picID > mPageCount) {
                    mp3ID = mp3ID - 5;
                    picID--;
                    return;
                }
                mButLast.setBackgroundResource(R.drawable.pre_btn_pre);
                if (picID == mPageCount)
                    mButNex.setBackgroundResource(R.drawable.pre_btn_next_weiye);

                autoTouchNext();
                StartPlay(R.raw.fanshu);
                break;

            default:
                break;
        }

        lastClickTime = System.currentTimeMillis();

    }

    /**
     * CurlView size changed observer.
     */
    private class SizeChangedObserver implements CurlView.SizeChangedObserver {
        @Override
        public void onSizeChanged(int w, int h) {
            mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
            if (w > h) {
                //                mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
                //                mCurlView.setMargins(.1f, .05f, .1f, .05f);
            } else {
                //                mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
                //                mCurlView.setMargins(.1f, .1f, .1f, .1f);
            }
        }
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

    }

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

    private void StartPlay(int id) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        }
        mediaPlayer = MediaPlayer.create(mContext, R.raw.fanshu);
        mediaPlayer.start();
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

    private Handler touchHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TouchBean bean = (TouchBean) msg.obj;
            mCurlView.onTouchs(mCurlView, MotionEvent.obtain(0, 0, bean.action, bean.x, bean.y, 0));
        }
    };

    private void autoTouchLast() {
        for (int i = 0; i < 100; i += 5) {
            Message message = new Message();
            TouchBean bean = new TouchBean();
            bean.x = i * 7;
            bean.y = i * 5;
            bean.action = 2;
            if (i == 0)
                bean.action = 0;
            else if (i == 95)
                bean.action = 1;
            bean.x = bean.x > MyApplication.mScreenWidth - 100 ?
                    bean.x = MyApplication.mScreenWidth - 100 : bean.x;
            bean.y = bean.y > MyApplication.mScreenHeight - 50 ?
                    bean.y = MyApplication.mScreenHeight - 50 : bean.y;

            message.what = 1;
            message.obj = bean;
            touchHandle.sendMessageDelayed(message, i * 5);
        }
    }

    private void autoTouchNext() {
        for (int i = 0; i < 100; i += 5) {
            Message message = new Message();
            TouchBean bean = new TouchBean();
            bean.x = MyApplication.mScreenWidth - 100 - i * 7;
            bean.y = MyApplication.mScreenHeight - 50 - i * 5;
            bean.action = 2;
            if (i == 0)
                bean.action = 0;
            else if (i == 95)
                bean.action = 1;
            message.what = 1;
            message.obj = bean;
            touchHandle.sendMessageDelayed(message, i * 5);
        }
    }

    private class TouchBean {
        public int x;
        public int y;
        public int action;
    }

}
