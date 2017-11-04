package com.example.huoapp.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseActivity;
import com.example.huoapp.manager.AppManager;
import com.example.huoapp.model.TabEntity;
import com.example.huoapp.ui.adapter.MainPagerAdapter;
import com.example.huoapp.widget.HuoViewPager;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_main)
    HuoViewPager vpMain;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    private static long DOUBLE_CLICK_TIME = 0L;
    private int position = 0;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"游戏", "开服", "搜索", "我的"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private int[] mIconUnselectIds = {
            R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private int[] mIconSelectIds = {
            R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        MainActivityPermissionsDispatcher.getPermissionWithPermissionCheck(this);

        for (String title : mTitles){
            mFragments.add(SimpleFragment.newInstance(title));
        }

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        vpMain.setOffscreenPageLimit(mTitles.length);
        vpMain.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));

        tabLayout.setTabData(mTabEntities);

        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpMain.setCurrentItem(position,false);//false去除切换动画
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                DOUBLE_CLICK_TIME = System.currentTimeMillis();
                Toast.makeText(this,getString(R.string.exit_app),Toast.LENGTH_SHORT).show();
            } else {
                AppManager.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //获取多个权限
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void getPermission(){
        LogUtils.i("CAMERA!");
    }

    //向用户说明为什么需要这些权限（可选）
    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForCamera(final PermissionRequest request){
        LogUtils.i("showRationaleForCamera");
    }

    //用户拒绝授权回调（可选）
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForCamera(){
        LogUtils.i("OnPermissionDenied");
    }

    //用户勾选了“不再提醒”时调用（可选）
    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForCamera(){
        LogUtils.i("OnNeverAskAgain");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (null != intent){
            position = intent.getIntExtra("position",0);
        }
    }
}
