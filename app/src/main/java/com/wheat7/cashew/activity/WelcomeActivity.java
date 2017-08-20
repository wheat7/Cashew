package com.wheat7.cashew.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.DecelerateInterpolator;

import com.wheat7.cashew.R;
import com.wheat7.cashew.base.BaseActivity;
import com.wheat7.cashew.databinding.ActivityWelcomeBinding;

/**
 * Created by wheat7 on 2017/8/19.
 */

public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(getBinding().welcome, "alpha", 1f, 0f);
                animator.setDuration(1500);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        jump();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.start();
            }
        }, 3000);
    }

    private void jump() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();;
    }
}
