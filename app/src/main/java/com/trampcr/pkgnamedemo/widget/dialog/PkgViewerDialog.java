package com.trampcr.pkgnamedemo.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trampcr.pkgnamedemo.R;
import com.trampcr.pkgnamedemo.bean.PkgInfoBean;
import com.trampcr.pkgnamedemo.common.CommonConstants;

/**
 * Created by trampcr on 2018/3/28.
 * dialog
 */

public class PkgViewerDialog implements View.OnClickListener{


    private TextView mDialogAppName;
    private Button mDialogBtnDetail;
    private Button mDialogBtnSendMessage;
    private Button mDialogBtnCancel;

    private Dialog mDialog;
    private Context mContext;
    private PkgInfoBean mPkgInfoBean;

    public PkgViewerDialog(Context context) {
        mContext = context;
    }

    public void showViewerDialog(PkgInfoBean pkgInfoBean) {
        if (null == pkgInfoBean) {
            return;
        }
        mPkgInfoBean = pkgInfoBean;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_pkg_detail, null);

        createDialogWindow();
        setDialogViewValue(view);

        mDialog.addContentView(view, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , RelativeLayout.LayoutParams.WRAP_CONTENT));
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    private void createDialogWindow() {
        mDialog = new Dialog(mContext, R.style.PkgDetailDialogTheme);
        Window window = mDialog.getWindow();
        if (null == window) {
            return;
        }

        window.setWindowAnimations(R.style.PkgDetailDialogTheme);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
    }

    private void setDialogViewValue(View view) {
        if (null == view) {
            return;
        }

        initDialogView(view);
        initDialogData();
        initDialogListener();
    }

    private void initDialogListener() {
        mDialogBtnDetail.setOnClickListener(this);
        mDialogBtnSendMessage.setOnClickListener(this);
        mDialogBtnCancel.setOnClickListener(this);
    }

    private void initDialogView(View view) {
        if (null == view) {
            return;
        }

        mDialogAppName = view.findViewById(R.id.dialog_text_app_name);
        mDialogBtnDetail = view.findViewById(R.id.btn_dialog_app_detail);
        mDialogBtnSendMessage  = view.findViewById(R.id.btn_dialog_send_message);
        mDialogBtnCancel = view.findViewById(R.id.btn_dialog_cancel);
    }

    private void initDialogData() {
        mDialogAppName.setText(mPkgInfoBean.getAppName());
        mDialogBtnDetail.setText(mContext.getString(R.string.dialog_app_detail));
        mDialogBtnSendMessage.setText(mContext.getString(R.string.dialog_send_message));
        mDialogBtnCancel.setText(mContext.getString(R.string.dialog_cancel));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_app_detail:
                startAppDetailPage(mPkgInfoBean.getPkgName());
                mDialog.dismiss();
                break;
            case R.id.btn_dialog_send_message:
                sendMessage();
                mDialog.dismiss();
                break;
            case R.id.btn_dialog_cancel:
                mDialog.dismiss();
                break;
            default:
                break;
        }
    }

    private void sendMessage() {
        String appName = mContext.getString(R.string.send_message_app_name) + mPkgInfoBean.getAppName();
        String pkgName = mContext.getString(R.string.send_message_pkg_name) + mPkgInfoBean.getPkgName();
        String type = mContext.getString(R.string.send_message_type) + mPkgInfoBean.getType();

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType(CommonConstants.Strings.INTENT_TYPE_TEXT_PLAIN);
        sendIntent.putExtra(Intent.EXTRA_TEXT,  appName + "\n" + pkgName + "\n" + type);
        try {
            mContext.startActivity(Intent.createChooser(sendIntent, mContext.getString(R.string.dialog_send_message_title)));
        } catch(android.content.ActivityNotFoundException ex) {
            // if no app handles it, do nothing
        }
    }

    private void startAppDetailPage(String pkgName) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(CommonConstants.Actions.APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts(CommonConstants.Strings.URI_SHEME_PACKAGE, pkgName, null));

        mContext.startActivity(intent);
    }
}
