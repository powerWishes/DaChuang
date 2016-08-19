package com.jlu.mzx.tiaoji;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jlu.mzx.tiaoji.Adapter.MyFragmentAdapter;
import com.jlu.mzx.tiaoji.Frag.MeFragment;
import com.jlu.mzx.tiaoji.Frag.ZhiyuanFragment;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Fragment> ls;
    private Fragment mConverstationlist = null;
    private Fragment zhiyuanFragment = null;
    private Fragment meFragment = null;
    private RadioGroup radiogroup;
    private RadioButton xiaoxi, zhiyuan, me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
    }

    /**
     * 初始化view
     */
    private void initview() {
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        xiaoxi = (RadioButton) findViewById(R.id.xiaoxi);
        zhiyuan = (RadioButton) findViewById(R.id.zhiyuan);
        me = (RadioButton) findViewById(R.id.me);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ls = new ArrayList<>();
        mConverstationlist = initConverstationlist();
        meFragment = new MeFragment();
        zhiyuanFragment = new ZhiyuanFragment();

        ls.add(mConverstationlist);
        ls.add(zhiyuanFragment);
        ls.add(meFragment);


        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), ls));
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.xiaoxi:
                        /**
                         * setCurrentItem第二个参数控制页面切换动画
                         * true:打开/false:关闭
                         */
                        viewPager.setCurrentItem(0, true);
                        break;
                    case R.id.zhiyuan:
                        viewPager.setCurrentItem(1, true);
                        break;
                    case R.id.me:
                        viewPager.setCurrentItem(2, true);
                        break;
                }
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        radiogroup.check(R.id.xiaoxi);
                        break;
                    case 1:
                        radiogroup.check(R.id.zhiyuan);
                        break;
                    case 2:
                        radiogroup.check(R.id.me);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化聊天界面fragment
     *
     * @return
     */
    private Fragment initConverstationlist() {
        if (mConverstationlist == null) {
            ConversationListFragment fragment = new ConversationListFragment();

            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "flase") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                    .build();

            fragment.setUri(uri);
            return fragment;
        } else {
            return mConverstationlist;
        }
    }
}