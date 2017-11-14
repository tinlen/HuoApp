package com.example.huoapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseActivity;
import com.example.huoapp.manager.AppManager;
import com.example.huoapp.model.TabEntity;
import com.example.huoapp.ui.adapter.MainPagerAdapter;
import com.example.huoapp.ui.game.GameFragment;
import com.example.huoapp.ui.home.HomeFragment;
import com.example.huoapp.ui.mine.MineFragment;
import com.example.huoapp.ui.pupil.PupilFragment;
import com.example.huoapp.widget.HuoViewPager;
import com.example.huoapp.widget.dialog.CommonDialog;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import qiu.niorgai.StatusBarCompat;

@RuntimePermissions
public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_main)
    HuoViewPager vpMain;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    private static long DOUBLE_CLICK_TIME = 0L;
    private int position = 0;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "游戏", "收徒", "我的"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_default, R.mipmap.tab_game_default,
            R.mipmap.tab_shoutu_default, R.mipmap.tab_mine_default};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_selected, R.mipmap.tab_game_selected,
            R.mipmap.tab_shoutu_selected, R.mipmap.tab_mine_selected};
    private CommonDialog commonDialog;

    @Override
    protected int getContentViewLayoutId() {
        StatusBarCompat.translucentStatusBar(this,true);
        return R.layout.activity_main;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        MainActivityPermissionsDispatcher.getPermissionWithPermissionCheck(this);
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(GameFragment.newInstance());
        mFragments.add(PupilFragment.newInstance());
        mFragments.add(MineFragment.newInstance());

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

        if (isNoOption() && !isNoSwitch()){
            commonDialog = CommonDialog
                    .newInstance()
                    .setContent("需要获取您的app运营状态，点击确认开启权限。")
                    .hideCancel()
                    .setConfirm("立即前往设置")
                    .setConfirmListener(new CommonDialog.IConfirmListener() {
                        @Override
                        public void onConfirm() {
                            openUsageAccess();
                        }
                    });
            commonDialog.show(getSupportFragmentManager(),"common");
        }

    }

    private boolean isNoOption() {
        PackageManager packageManager = getApplicationContext()
                .getPackageManager();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean isNoSwitch() {
        long ts = System.currentTimeMillis();
        @SuppressLint("WrongConstant") UsageStatsManager usageStatsManager = (UsageStatsManager) getApplicationContext()
                .getSystemService("usagestats");
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST, 0, ts);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return false;
        }
        return true;
    }

    private void openUsageAccess(){
        Intent intent = new Intent( Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
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

    public void switchTab(int position){
        tabLayout.setCurrentTab(position);
    }
}
