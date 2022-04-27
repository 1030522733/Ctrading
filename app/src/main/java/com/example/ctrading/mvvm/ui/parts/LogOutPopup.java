package com.example.ctrading.mvvm.ui.parts;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ctrading.R;
import com.example.ctrading.app.global.Constant;
import com.example.ctrading.app.global.EventBusTag;
import com.example.ctrading.app.utils.CacheUtils;
import com.example.ctrading.app.utils.MmkvUtils;
import com.example.ctrading.mvvm.ui.activity.LoginActvity;
import com.example.ctrading.mvvm.ui.activity.MainActivity;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.core.CenterPopupView;

import static com.blankj.utilcode.util.ActivityUtils.finishOtherActivities;

import org.simple.eventbus.EventBus;

/**
 * @Author: JianTours
 * @Data: 2022/4/13 22:42
 * @Description:
 */
public class LogOutPopup extends CenterPopupView {

    public LogOutPopup(Context context,int flag) {
        super(context);
        this.flag = flag;
    }

    /**
     * 0  退出登录弹窗
     * 1 下单确认弹窗
     * 2  清除缓存弹窗
     * 3  是否保存信息弹窗
     */
    private int flag = 0;

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_logout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView(flag);
        findViewById(R.id.btPCancel).setOnClickListener(view -> {
            dismiss();
        });
        findViewById(R.id.btPConfirm).setOnClickListener(view -> {
            switch (flag){
                case 0:
                    MmkvUtils.put(Constant.IS_LOGIN,false);
                    MmkvUtils.put(Constant.MY_PHONE,"");
                    Context context = this.getContext();
                    context.startActivity(new Intent(context, LoginActvity.class));
                    //关闭所有Act
                    finishOtherActivities(LoginActvity.class);
                    dismiss();
                    break;
                case 1:
                    Message message = Message.obtain();
                    EventBus.getDefault().post(message, EventBusTag.ORDER_OK);
                    dismiss();
                    break;
                case 2:
                    CacheUtils.clearAllCache(this.getContext());
                    Message message2 = Message.obtain();
                    EventBus.getDefault().post(message2, EventBusTag.CLEAR_OK);
                    dismiss();
                case 3:
                    //关闭所有Act
                    finishOtherActivities(MainActivity.class);
                    dismiss();
                    break;
                default:
                    break;
            }
        });
    }

    private void initView(int flag){
        TextView textView = (TextView) findViewById(R.id.tvPLogout);
        if (flag==0){
            textView.setText("是否退出登录？");
        }
        if (flag==1){
            textView.setText("您确认下单吗？");
        }
        if (flag==2){
            textView.setText("您确定清除缓存吗？");
        }
        if (flag==3){
            textView.setText("您确定不保存直接退出吗？");
        }
    }
}
