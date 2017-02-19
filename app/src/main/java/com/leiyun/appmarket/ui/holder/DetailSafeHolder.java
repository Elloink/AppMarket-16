package com.leiyun.appmarket.ui.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.AppInfo;
import com.leiyun.appmarket.http.HttpHelper;
import com.leiyun.appmarket.utils.BitmapHelper;
import com.leiyun.appmarket.utils.LogUtils;
import com.leiyun.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;

/**
 * 应用详情页-安全模块
 * Created by LeiYun on 2017/2/17 0017.
 */

public class DetailSafeHolder extends BaseHolder<AppInfo> {
    private boolean isOpen = false;// 标记安全描述开关状态，默认关闭

    private ImageView[] mSafeIcons; // 安全标识图片
    private ImageView[] mDesIcons; // 安全描述图片
    private TextView[] mSafeDes; // 安全描述文字

    private LinearLayout[] mSafeDesBar; // 安全描述的条目（图片+文字）
    private BitmapUtils mBitmapUtils;
    private RelativeLayout rlDesRoot;
    private LinearLayout llDesRoot;
    private int mDesHeight;
    private LinearLayout.LayoutParams mParams;
    private ImageView ivArrow; // 箭头ImageView


    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_safeinfo);
        mSafeIcons = new ImageView[4];
        mSafeIcons[0] = (ImageView) view.findViewById(R.id.iv_safe1);
        mSafeIcons[1] = (ImageView) view.findViewById(R.id.iv_safe2);
        mSafeIcons[2] = (ImageView) view.findViewById(R.id.iv_safe3);
        mSafeIcons[3] = (ImageView) view.findViewById(R.id.iv_safe4);

        mDesIcons = new ImageView[4];
        mDesIcons[0] = (ImageView) view.findViewById(R.id.iv_des1);
        mDesIcons[1] = (ImageView) view.findViewById(R.id.iv_des2);
        mDesIcons[2] = (ImageView) view.findViewById(R.id.iv_des3);
        mDesIcons[3] = (ImageView) view.findViewById(R.id.iv_des4);

        mSafeDes = new TextView[4];
        mSafeDes[0] = (TextView) view.findViewById(R.id.tv_des1);
        mSafeDes[1] = (TextView) view.findViewById(R.id.tv_des2);
        mSafeDes[2] = (TextView) view.findViewById(R.id.tv_des3);
        mSafeDes[3] = (TextView) view.findViewById(R.id.tv_des4);

        mSafeDesBar = new LinearLayout[4];
        mSafeDesBar[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
        mSafeDesBar[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
        mSafeDesBar[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
        mSafeDesBar[3] = (LinearLayout) view.findViewById(R.id.ll_des4);

        llDesRoot = (LinearLayout) view.findViewById(R.id.ll_des_root);

        rlDesRoot = (RelativeLayout) view.findViewById(R.id.rl_des_root);
        rlDesRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);;

        mBitmapUtils = BitmapHelper.getBitmapUtils();

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        ArrayList<AppInfo.SafeInfo> safe = data.safe;
        for (int i = 0; i < 4; i++) {
            if (i < safe.size()) {
                AppInfo.SafeInfo safeInfo = safe.get(i);
                // 安全标识图片
                mBitmapUtils.display(mSafeIcons[i],
                        HttpHelper.URL + "image?name=" + safeInfo.safeUrl);

                // 安全的描述文字
                mSafeDes[i].setText(safeInfo.safeDes);

                // 安全描述文字前的小图标
                mBitmapUtils.display(mDesIcons[i],
                        HttpHelper.URL + "image?name=" + safeInfo.safeDesUrl);

            } else {
                // 剩下不应该显示的布局
                mSafeIcons[i].setVisibility(View.GONE);
                mSafeDes[i].setVisibility(View.GONE);
                mDesIcons[i].setVisibility(View.GONE);
            }
        }

        // 获取安全描述的完整高度
        // 这里为什么两个参数都是0，那是因为在xml布局文件中我们已经定义了该view的宽和高填0后系统会自动根据xml来确定数值
        llDesRoot.measure(0,0);
        mDesHeight = llDesRoot.getMeasuredHeight();

        // 修改安全描述布局高度0，达到隐藏效果
        mParams = (LinearLayout.LayoutParams) llDesRoot.getLayoutParams();
        mParams.height = 0;
        llDesRoot.setLayoutParams(mParams);
    }

    /**
     * 打开或者关闭安全描述信息
     * 导入jar包：nineoldandroids-2.4.0.jar
     */
    private void toggle() {
        // 属性动画
        ValueAnimator animator = null;
        if (isOpen) {
            // 关闭
            isOpen = false;

            // 属性动画
            animator = ValueAnimator.ofInt(mDesHeight, 0);
        } else {
            // 开启
            isOpen = true;

            // 属性动画
            animator = ValueAnimator.ofInt(0, mDesHeight);
        }

        // 动画更新的监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            // 启动动画之后，会不断回调此方法来获取最新的值
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 获取最新的高度值
                Integer height = (Integer)valueAnimator.getAnimatedValue();

                // 重新修改布局高度
                mParams.height = height;
                llDesRoot.setLayoutParams(mParams);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                LogUtils.e("" + isOpen);
                // 动画结束后检测
                // 更新箭头方向
                if (isOpen) {
                    ivArrow.setImageResource(R.drawable.arrow_up);
                } else {
                    ivArrow.setImageResource(R.drawable.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animator.setDuration(200);//动画时间
        animator.start(); // 启动动画

    }
}
