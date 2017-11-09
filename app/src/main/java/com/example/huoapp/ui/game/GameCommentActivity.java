package com.example.huoapp.ui.game;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseSwipeBackActivity;
import com.example.huoapp.widget.emotion.EmotionFragment;

import butterknife.BindView;

/**
 * Created by tinle on 2017/11/9.
 */

public class GameCommentActivity extends BaseSwipeBackActivity {
    @BindView(R.id.et_motion)
    EditText etMotion;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;

    private EmotionFragment emotionMainFragment;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_game_comment;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        etMotion.setFocusable(true);
        etMotion.setFocusableInTouchMode(true);
        etMotion.requestFocus();
        InputMethodManager im = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
        im.showSoftInput(etMotion, 0);

        initEmotionMainFragment();
    }

    private void initEmotionMainFragment() {
        emotionMainFragment = EmotionFragment.newInstance(null);
        emotionMainFragment.bindToEditView(etMotion);
        emotionMainFragment.bindToContentView(etMotion);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_emotion_main,emotionMainFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        /**
         * 判断是否拦截返回键操作
         */
        if (!emotionMainFragment.isInterceptBackPress()) {
            super.onBackPressed();
        }
    }
}
