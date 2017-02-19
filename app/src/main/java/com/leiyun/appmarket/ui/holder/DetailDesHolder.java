package com.leiyun.appmarket.ui.holder;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.AppInfo;
import com.leiyun.appmarket.utils.UIUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.nineoldandroids.animation.ValueAnimator.ofInt;

/**
 * 详情页-应用描述
 * Created by LeiYun on 2017/2/19 0019.
 */

public class DetailDesHolder extends BaseHolder<AppInfo> {

    private final int MAXLINES = 7;

    private TextView tvDes;
    private TextView tvAuthor;
    private ImageView ivArrow;
    private RelativeLayout rlToggle;
    private LinearLayout.LayoutParams mParams;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_desinfo);
        tvDes = (TextView) view.findViewById(R.id.tv_detail_des);
        tvAuthor = (TextView) view.findViewById(R.id.tv_detail_author);
        ivArrow = (ImageView) view.findViewById(R.id.iv_des_arrow);
        rlToggle = (RelativeLayout) view.findViewById(R.id.rl_detail_toggle);

        rlToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tvDes.setText(data.des);
        tvAuthor.setText(data.author);

        tvDes.post(new Runnable() { // 这样子可以解决只有3行的内容，也使用7行的高度来显示内容
            @Override
            public void run() {
                // 默认展示MAXLINES行的高度
                int shortHeight = getShortHeight();
                mParams = (LinearLayout.LayoutParams)tvDes.getLayoutParams();
                mParams.height = shortHeight;
                tvDes.setLayoutParams(mParams);
            }
        });
    }

    private boolean isOpen = false;
    private void toggle() {
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();
        ValueAnimator animator = null;
        if (isOpen) {
            // 关闭
            isOpen = false;
            if (longHeight > shortHeight) { // 只有描述信息大于MAXLINES，才启动动画
                animator = ValueAnimator.ofInt(longHeight, shortHeight);
                ivArrow.setImageResource(R.drawable.arrow_up);
            }
        } else {
            // 打开
            isOpen = true;
            if (longHeight > shortHeight) { // 只有描述信息大于MAXLINES，才启动动画
                animator = ValueAnimator.ofInt(shortHeight, longHeight);
                ivArrow.setImageResource(R.drawable.arrow_down);
            }
        }

        if (animator != null) {
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Integer height = (Integer) valueAnimator.getAnimatedValue();
                    mParams.height = height;
                    tvDes.setLayoutParams(mParams);
                }
            });

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    // ScrollView要滑动到最底部
                    final ScrollView scrollView = getScrollvView();

                    // 为了运行更加安全和稳定，可以将滑动到底部方法放在消息队列中执行
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);// 滑动到底部
                        }
                    });

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            animator.setDuration(200);
            animator.start();
        }
    }

    /**
     * 获取ScrollView ，一层层往上找，直到找到ScrollView后才返回；
     * Tips：一定要保证父控件或祖宗控件有ScrollView，否则死循环
     * @return ScrollView
     */
    private ScrollView getScrollvView() {
        ViewParent parent = tvDes.getParent();
        while (!(parent instanceof ScrollView)) {
            parent = parent.getParent();
        }
        return (ScrollView)parent;
    }

    /**
     * 获取7行TextView的高度
     * 模拟一个TextView，设置最大行数为7行，计算该虚拟TextView的高度
     * 从而得知tvDes在展示7行时应该有多高
     * @return 返回测量后的高度
     */
    private int getShortHeight() {

        int width = tvDes.getMeasuredWidth();
        TextView view = new TextView(UIUtils.getContext());
        view.setText(getData().des); //设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // 文字大小一致
        view.setMaxLines(MAXLINES); // 最大行数为7

        int widthMeasureSpec = View.MeasureSpec
                .makeMeasureSpec(width, View.MeasureSpec.EXACTLY);// 宽不变，确定值，match_parent
        int heightMeasureSpec = View.MeasureSpec
                .makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);// 高度包裹内容，wrap_content;当包裹内容时；
                                                                // 参1表示尺寸最大值，暂写2000，也可以是屏幕高度

        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight(); // 返回测量后的高度
    }

    /**
     * 获取完整的高度
     * @return 返回测量后的高度
     */
    private int getLongHeight() {

        int width = tvDes.getMeasuredWidth();
        TextView view = new TextView(UIUtils.getContext());
        view.setText(getData().des); //设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // 文字大小一致

        int widthMeasureSpec = View.MeasureSpec
                .makeMeasureSpec(width, View.MeasureSpec.EXACTLY);// 宽不变，确定值，match_parent
        int heightMeasureSpec = View.MeasureSpec
                .makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);// 高度包裹内容，wrap_content;当包裹内容时；
        // 参1表示尺寸最大值，暂写2000，也可以是屏幕高度

        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight(); // 返回测量后的高度
    }
}
