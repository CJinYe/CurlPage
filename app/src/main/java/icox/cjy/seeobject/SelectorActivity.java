package icox.cjy.seeobject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import icox.cjy.seeobject.bean.Bean;
import icox.cjy.seeobject.bean.Constants;

/**
 * @author 陈锦业
 * @version $Rev$
 * @time 2017-9-20 18:07
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class SelectorActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private LinearLayout mLlBaoBao;
    private LinearLayout mLlYouEr;
    private LinearLayout mLlErTong;
    private String mName;
    private int mId;
    private ImageView mIvBaoBao;
    private ImageView mIvYouEr;
    private ImageView mIvErTong;
    private ImageView mIvGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        mName = getIntent().getStringExtra("name");
        mId = getIntent().getIntExtra("id", 0);
        initView();
        initData();
    }

    private void initData() {
        String title ="";
        switch (mId) {
            case Constants.BIAO_ZI:
                title = Constants.BIAO_ZI_TITLE;
                mIvBaoBao.setImageResource(R.drawable.selector_biaozhi1);
                mIvYouEr.setImageResource(R.drawable.selector_biaozhi2);
                mIvErTong.setImageResource(R.drawable.selector_biaozhi3);
                break;
            case Constants.CHE_BIAO:
                title = Constants.CHE_BIAO_TITLE;
                mIvBaoBao.setImageResource(R.drawable.sele_chebiao1);
                mIvYouEr.setImageResource(R.drawable.sele_chebiao2);
                mIvErTong.setImageResource(R.drawable.sele_chebiao3);
                break;
            case Constants.DONG_WU:
                title = Constants.DONG_WU_TITLE;
                mIvBaoBao.setImageResource(R.drawable.selector_dongwu1);
                mIvYouEr.setImageResource(R.drawable.selector_dongwu2);
                mIvErTong.setImageResource(R.drawable.selector_dongwu3);
                break;
            case Constants.GUO_QI:
                title = Constants.GUO_QI_TITLE;
                mIvBaoBao.setImageResource(R.drawable.selector_guoqi1);
                mIvYouEr.setImageResource(R.drawable.selector_guoqi2);
                mIvErTong.setImageResource(R.drawable.selector_guoqi3);
                break;
            case Constants.JIAO_TONG_GONG_JU:
                title = Constants.JIAO_TONG_GONG_JU_TITLE;
                mIvBaoBao.setImageResource(R.drawable.selector_jiaotonggongju1);
                mIvYouEr.setImageResource(R.drawable.selector_jiaotonggongju2);
                mIvErTong.setImageResource(R.drawable.selector_jiaotonggongju3);
                break;
            case Constants.KONG_LONG:
                title = Constants.KONG_LONG_TITLE;
                mIvBaoBao.setImageResource(R.drawable.selector_konglong1);
                mIvYouEr.setImageResource(R.drawable.selector_konglong2);
                mIvErTong.setImageResource(R.drawable.selector_konglong3);
                break;
            case Constants.RI_YONG_PIN:
                title = Constants.RI_YONG_PIN_TITLE;
                mIvBaoBao.setImageResource(R.drawable.selector_riyongp1);
                mIvYouEr.setImageResource(R.drawable.selector_riyongp2);
                mIvErTong.setImageResource(R.drawable.selector_riyongp3);
                break;
            case Constants.SHI_PIN:
                title = Constants.SHI_PIN_TITLE;
                mIvBaoBao.setImageResource(R.drawable.selector_shipin1);
                mIvYouEr.setImageResource(R.drawable.selector_shipin2);
                mIvErTong.setImageResource(R.drawable.selector_shipin3);
                break;
            case Constants.ZHI_YE:
                title = Constants.ZHI_YE_TITLE;
                mIvBaoBao.setImageResource(R.drawable.selector_zhiye1);
                mIvYouEr.setImageResource(R.drawable.selector_zhiye2);
                mIvErTong.setImageResource(R.drawable.selector_zhiye3);
                break;
            case Constants.ZI_RAN:
                title = Constants.ZI_RAN_TITLE;
                mIvBaoBao.setImageResource(R.drawable.selector_ziran1);
                mIvYouEr.setImageResource(R.drawable.selector_ziran2);
                mIvErTong.setImageResource(R.drawable.selector_ziran3);
                break;

            default:
                break;
        }

        mTvTitle.setText(title);
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.selector_tv_title);
        mLlBaoBao = (LinearLayout) findViewById(R.id.selector_ll_baobao);
        mLlYouEr = (LinearLayout) findViewById(R.id.selector_ll_youer);
        mLlErTong = (LinearLayout) findViewById(R.id.selector_ll_ertong);

        mIvBaoBao = (ImageView) findViewById(R.id.selector_baobao_iv);
        mIvYouEr = (ImageView) findViewById(R.id.selector_youer_iv);
        mIvErTong = (ImageView) findViewById(R.id.selector_ertong_iv);

        mIvGoBack = (ImageView) findViewById(R.id.selector_go_back);

        mLlBaoBao.setOnClickListener(this);
        mLlYouEr.setOnClickListener(this);
        mLlErTong.setOnClickListener(this);
        mIvGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this, ReadActivity.class);
        int ModID = 1;
        switch (v.getId()) {
            case R.id.selector_ll_baobao:
                ModID = 1;
                break;
            case R.id.selector_ll_youer:
                ModID = 2;
                break;
            case R.id.selector_ll_ertong:
                ModID = 3;
                break;

            default:
                break;
        }

        intent.putExtra(Bean.MODSIGN, ModID);
        intent.putExtra("name", mName);
        startActivity(intent);
    }
}
