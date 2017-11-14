package com.example.huoapp.widget.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.huoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tinle on 2017/11/14.
 */

public class CommonDialog extends DialogFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private String title;
    private String content;
    private String cancel;
    private String confirm;
    private boolean hideCancelBtn;

    private ICancelListener cancelListener;
    private IConfirmListener confirmListener;

    public static CommonDialog newInstance() {

        Bundle args = new Bundle();

        CommonDialog fragment = new CommonDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_fragment_common, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }

        if (!TextUtils.isEmpty(content)){
            tvContent.setText(content);
        }

        if (hideCancelBtn){
            btnCancel.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(cancel)){
            btnCancel.setText(cancel);
        }

        if (!TextUtils.isEmpty(confirm)){
            btnConfirm.setText(confirm);
        }
    }

    @OnClick({R.id.btn_cancel,R.id.btn_confirm})
    void actionClick(View view){
        switch (view.getId()){
            case R.id.btn_cancel:

                if (null != cancelListener){
                    cancelListener.onCancel();
                }

                break;

            case R.id.btn_confirm:

                if (null != confirmListener){
                    confirmListener.onConfirm();
                }
                break;
        }
    }

    public CommonDialog setTitle(String title){
        this.title = title;
        return this;
    }

    public CommonDialog setContent(String content){
        this.content = content;
        return this;
    }

    public CommonDialog setCancel(String cancel){
        this.cancel = cancel;
        return this;
    }

    public CommonDialog setConfirm(String confirm){
        this.confirm = confirm;
        return this;
    }

    public CommonDialog hideCancel(){
        hideCancelBtn = true;
        return this;
    }

    public CommonDialog setCancelListener(ICancelListener listener){
        this.cancelListener = listener;
        return this;
    }

    public CommonDialog setConfirmListener(IConfirmListener listener){
        this.confirmListener = listener;
        return this;
    }


    public interface ICancelListener{
        void onCancel();
    }

    public interface IConfirmListener{
        void onConfirm();
    }
}
