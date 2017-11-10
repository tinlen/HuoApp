package com.example.huoapp.widget.emotion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.example.huoapp.R;
import com.example.huoapp.listener.IEmotionBarListener;
import com.example.huoapp.widget.NoHorizontalScrollerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 表情主界面
 * Created by tinle on 2017/11/9.
 */

public class EmotionFragment extends Fragment{

    //表情面板
    private EmotionKeyboard mEmotionKeyboard;
    private NoHorizontalScrollerViewPager viewPager;
    private ImageButton ibEmotion;
    private ImageButton ibPic;
    private TextView tvSubmit;
    private View contentView;
    private EditText editView;

    private IEmotionBarListener listener;

    public void setIEmotionBarListener(IEmotionBarListener listener){
        this.listener = listener;
    }

    List<Fragment> fragments=new ArrayList<>();

    public static EmotionFragment newInstance(Bundle args) {
        EmotionFragment fragment = new EmotionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emotion_keyboard,container,false);

        initView(view);
        mEmotionKeyboard = EmotionKeyboard.with(getActivity())
                .setEmotionView(view.findViewById(R.id.ll_emotion_layout))//绑定表情布局
                .bindToContent(contentView)
                .bindToEditText(editView)
                .bindToEmotionButton(ibEmotion)//绑定表情按钮
                .build();

        replaceFragment();
        GlobalOnItemClickManagerUtils clickManagerUtils = GlobalOnItemClickManagerUtils.getInstance(getActivity());

        clickManagerUtils.attachToEditText(editView);

        return view;
    }

    /**
     * 绑定内容view
     * @param contentView
     * @return
     */
    public void bindToContentView(View contentView){
        this.contentView=contentView;
    }


    /**
     * 绑定编辑
     * @param editView
     * @return
     */
    public void bindToEditView(EditText editView){
        this.editView=editView;
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.vp_emotionview_layout);
        ibEmotion = view.findViewById(R.id.ib_emotion);
        ibPic = view.findViewById(R.id.ib_pic);
        tvSubmit = view.findViewById(R.id.tv_submit);

        ibPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != listener){
                    listener.picClick();
                }
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != listener){
                    listener.submitClick();
                }
            }
        });
    }

    private void replaceFragment(){
        //创建fragment的工厂类
        FragmentFactory factory=FragmentFactory.getSingleFactoryInstance();
        //创建修改实例
        EmotiomComplateFragment f1= (EmotiomComplateFragment) factory.getFragment(EmotionUtils.EMOTION_CLASSIC_TYPE);
        fragments.add(f1);
        NoHorizontalScrollerVPAdapter adapter =new NoHorizontalScrollerVPAdapter(getActivity().getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }

    /**
     * 是否拦截返回键操作，如果此时表情布局未隐藏，先隐藏表情布局
     * @return true则隐藏表情布局，拦截返回键操作
     *         false 则不拦截返回键操作
     */
    public boolean isInterceptBackPress(){
        return mEmotionKeyboard.interceptBackPress();
    }
}
