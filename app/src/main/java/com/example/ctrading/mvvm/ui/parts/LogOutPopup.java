package com.example.ctrading.mvvm.ui.parts;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.ctrading.R;
import com.example.ctrading.app.global.Constant;
import com.example.ctrading.app.utils.MmkvUtils;
import com.example.ctrading.mvvm.ui.activity.LoginActvity;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.core.CenterPopupView;

import static com.blankj.utilcode.util.ActivityUtils.finishOtherActivities;

/**
 * @Author: JianTours
 * @Data: 2022/4/13 22:42
 * @Description:
 */
public class LogOutPopup extends CenterPopupView {
    public LogOutPopup(Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_logout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.btPCancel).setOnClickListener(view -> {
            dismiss();
        });

        findViewById(R.id.btPConfirm).setOnClickListener(view -> {
            MmkvUtils.put(Constant.IS_LOGIN,false);
            MmkvUtils.put(Constant.MY_PHONE,"");
            Context context = this.getContext();
            context.startActivity(new Intent(context, LoginActvity.class));
            //关闭所有Act
            finishOtherActivities(LoginActvity.class);
            dismiss();
        });
    }
}
