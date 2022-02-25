package com.example.animationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_alpha;
    private Button btn_scale;
    private Button btn_tran;
    private Button btn_rotate;
    private Button btn_set;
    private Button sx_one;
    private Button sx_two;
    private Button sx_three;
    private Button sx_four;
    private Button sx_five;
    private ImageView img_show;
    private Animation animation = null;
    private LinearLayout ly_root;
    private int width;
    private int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }
    private void bindViews() {
        ly_root = findViewById(R.id.ly_root);
        btn_alpha = (Button) findViewById(R.id.btn_alpha);
        btn_scale = (Button) findViewById(R.id.btn_scale);
        btn_tran = (Button) findViewById(R.id.btn_tran);
        btn_rotate = (Button) findViewById(R.id.btn_rotate);
        btn_set = (Button) findViewById(R.id.btn_set);
        sx_one = findViewById(R.id.one);
        sx_two = findViewById(R.id.two);
        sx_three = findViewById(R.id.three);
        sx_four = findViewById(R.id.four);
        sx_five = findViewById(R.id.five);
        img_show = (ImageView) findViewById(R.id.img_show);

        btn_alpha.setOnClickListener(this);
        btn_scale.setOnClickListener(this);
        btn_tran.setOnClickListener(this);
        btn_rotate.setOnClickListener(this);
        btn_set.setOnClickListener(this);
        sx_one.setOnClickListener(this);
        sx_two.setOnClickListener(this);
        sx_three.setOnClickListener(this);
        sx_four.setOnClickListener(this);
        sx_five.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_alpha:
                animation = AnimationUtils.loadAnimation(this,
                        R.anim.anim_alpha);
                img_show.startAnimation(animation);
                break;
            case R.id.btn_scale:
                animation = AnimationUtils.loadAnimation(this,
                        R.anim.anim_scale);
                img_show.startAnimation(animation);
                break;
            case R.id.btn_tran:
                animation = AnimationUtils.loadAnimation(this,
                        R.anim.anim_translate);
                img_show.startAnimation(animation);
                break;
            case R.id.btn_rotate:
                animation = AnimationUtils.loadAnimation(this,
                        R.anim.anim_rotate);
                img_show.startAnimation(animation);
                break;
            case R.id.btn_set:
                animation = AnimationUtils.loadAnimation(this,
                        R.anim.anim_set);
                img_show.startAnimation(animation);
                break;
            case  R.id.one:
                lineAnimator();
                break;
            case R.id.two:
                scaleAnimator();
                break;
            case  R.id.three:
                raAnimator();
                break;
            case R.id.four:
                circleAnimator();
                break;
            case R.id.five:
                tryMass();
                break;
        }
    }
    //定义一个修改ImageView位置的方法
    private void moveView(View view, int rawX, int rawY) {
        int left = rawX - img_show.getWidth() / 2;
        int top = rawY - img_show.getHeight();
        int width = left + view.getWidth();
        int height = top + view.getHeight();
        view.layout(left, top, width, height);
    }


    //定义属性动画的方法：

    //按轨迹方程来运动
    private void lineAnimator() {
        width = ly_root.getWidth();
        height = ly_root.getHeight();
        ValueAnimator xValue = ValueAnimator.ofInt(height,0,height / 4,height / 2,height / 4 * 3 ,height);
        xValue.setDuration(3000L);
        xValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 轨迹方程 x = width / 2
                int y = (Integer) animation.getAnimatedValue();
                int x = width / 2;
                moveView(img_show, x, y);
            }
        });
        xValue.setInterpolator(new LinearInterpolator());
        xValue.start();
    }

    //缩放效果
    private void scaleAnimator(){

        //这里故意用两个是想让大家体会下组合动画怎么用而已~
        final float scale = 0.5f;
        AnimatorSet scaleSet = new AnimatorSet();
        ValueAnimator valueAnimatorSmall = ValueAnimator.ofFloat(1.0f, scale);
        valueAnimatorSmall.setDuration(500);

        ValueAnimator valueAnimatorLarge = ValueAnimator.ofFloat(scale, 1.0f);
        valueAnimatorLarge.setDuration(500);

        valueAnimatorSmall.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (Float) animation.getAnimatedValue();
                img_show.setScaleX(scale);
                img_show.setScaleY(scale);
            }
        });
        valueAnimatorLarge.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (Float) animation.getAnimatedValue();
                img_show.setScaleX(scale);
                img_show.setScaleY(scale);
            }
        });

        scaleSet.play(valueAnimatorLarge).after(valueAnimatorSmall);
        scaleSet.start();

        //其实可以一个就搞定的
//        ValueAnimator vValue = ValueAnimator.ofFloat(1.0f, 0.6f, 1.2f, 1.0f, 0.6f, 1.2f, 1.0f);
//        vValue.setDuration(1000L);
//        vValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float scale = (Float) animation.getAnimatedValue();
//                img_babi.setScaleX(scale);
//                img_babi.setScaleY(scale);
//            }
//        });
//        vValue.setInterpolator(new LinearInterpolator());
//        vValue.start();
    }


    //旋转的同时透明度变化
    private void raAnimator(){
        ValueAnimator rValue = ValueAnimator.ofInt(0, 360);
        rValue.setDuration(1000L);
        rValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int rotateValue = (Integer) animation.getAnimatedValue();
                img_show.setRotation(rotateValue);
                float fractionValue = animation.getAnimatedFraction();
                img_show.setAlpha(fractionValue);
            }
        });
        rValue.setInterpolator(new DecelerateInterpolator());
        rValue.start();
    }

    //圆形旋转
    protected void circleAnimator() {
        width = ly_root.getWidth();
        height = ly_root.getHeight();
        final int R = width / 4;
        ValueAnimator tValue = ValueAnimator.ofFloat(0,
                (float) (2.0f * Math.PI));
        tValue.setDuration(1000);
        tValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 圆的参数方程 x = R * sin(t) y = R * cos(t)
                float t = (Float) animation.getAnimatedValue();
                int x = (int) (R * Math.sin(t) + width / 2);
                int y = (int) (R * Math.cos(t) + height / 2);
                moveView(img_show, x, y);
            }
        });
        tValue.setInterpolator(new DecelerateInterpolator());
        tValue.start();
    }
    private void tryMass(){
        final float scale = 0.5f;
        width = ly_root.getWidth();
        height = ly_root.getHeight();
        AnimatorSet shark = new AnimatorSet();
        ValueAnimator small =ValueAnimator.ofFloat(1.0f,1.2f,0.8f,1.2f,1.0f);
        small.setDuration(500);

        ValueAnimator xValue = ValueAnimator.ofInt(height,0,height / 4,height / 2,height / 4 * 3 ,height);
        xValue.setDuration(3000);
        xValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 轨迹方程 x = width / 2
                int y = (Integer) animation.getAnimatedValue();
                int x = width / 2;
                moveView(img_show, x, y);
            }
        });
        small.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (float) valueAnimator.getAnimatedValue();
                img_show.setScaleX(scale);
                img_show.setScaleY(scale);
            }
        });

        shark.play(xValue).after(small);
        shark.start();
    }
}
