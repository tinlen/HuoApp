package com.example.huoapp.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseActivity;
import com.example.huoapp.manager.AppManager;
import com.lzy.okgo.OkGo;
import com.socks.library.KLog;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends BaseActivity {
    private static long DOUBLE_CLICK_TIME = 0L;
    private int position = 0;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        MainActivityPermissionsDispatcher.getPermissionWithPermissionCheck(this);
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
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    void getPermission(){
        KLog.i("Has get Write external storage!");
    }

    //向用户说明为什么需要这些权限（可选）
    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(final PermissionRequest request){
        KLog.i("showRationaleForCamera");
    }

    //用户拒绝授权回调（可选）
    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera(){
        KLog.i("OnPermissionDenied");
    }

    //用户勾选了“不再提醒”时调用（可选）
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera(){
        KLog.i("OnNeverAskAgain");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (null != intent){
            position = intent.getIntExtra("position",0);
        }
    }
}
