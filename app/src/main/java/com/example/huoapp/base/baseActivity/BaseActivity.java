package com.example.huoapp.base.baseActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.huoapp.R;
import com.example.huoapp.manager.AppManager;
import org.greenrobot.eventbus.EventBus;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tinle on 2017/11/2.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private Unbinder mUnbinder;

    /**
     * activity切換动画
     */
    protected enum TransitionMode{
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()){
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);

        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getContentViewLayoutId() != 0){
            setContentView(getContentViewLayoutId());
        }


        Bundle bundle = getIntent().getExtras();
        if (null != bundle){
            getBundleExtras(bundle);
        }

        if (isBindEventBusHere()){
            EventBus.getDefault().register(this);
        }

        AppManager.getInstance().addActivity(this);

        initViewsAndEvents(savedInstanceState);

    }

    protected abstract int getContentViewLayoutId();

    protected abstract void initViewsAndEvents(Bundle savedInstanceState);

    /**
     * 绑定EventBus控制
     * @return
     */
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mUnbinder = ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mUnbinder){
            mUnbinder.unbind();
        }

        if (isBindEventBusHere()){
            EventBus.getDefault().unregister(this);
        }

        AppManager.getInstance().removeActivity(this);
    }

    protected void getBundleExtras(Bundle bundle){}

    @Override
    public void finish() {
        super.finish();
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
    }

    /**
     * 是否Activity跳转的时候添加动画
     * @return
     */
    private boolean toggleOverridePendingTransition() {
        return false;
    }


    protected TransitionMode getOverridePendingTransitionMode(){
        return TransitionMode.RIGHT;
    }

    /**Activity跳转---------------------------------------------------*/

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
