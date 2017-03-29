package com.github.qing.animswitchcomponent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.github.qing.animswitchcomponent.model.CardModel;
import com.github.qing.animswitchcomponent.widget.SimpleCardAnimView;
import com.github.qing.animswitchcomponent.widget.SimpleSwitchImageView;
import com.github.qing.animswitchlib.BaseAnimSwitchView;
import com.github.qing.animswitchlib.anim.BaseAnimator;
import com.github.qing.animswitchlib.anim.DownTransAnimator;
import com.github.qing.animswitchlib.anim.FadeInFadeOutAnimator;
import com.github.qing.animswitchlib.anim.LeftTransAnimator;
import com.github.qing.animswitchlib.widget.AnimSwitchTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    AnimSwitchTextView animTextView;


    SimpleSwitchImageView itemAnimImage1;
    SimpleSwitchImageView itemAnimImage2;
    SimpleSwitchImageView itemAnimImage3;
    AnimSwitchTextView itemAnimText1;
    AnimSwitchTextView itemAnimText2;
    AnimSwitchTextView itemAnimText3;

    SimpleCardAnimView cardAnimView;


    int count = 0;
    Random random = new Random();

    int[] imgRes = {R.mipmap.img1_shrink, R.mipmap.img2_shrink, R.mipmap.img3_shrink, R.mipmap.img4_shrink, R.mipmap.img5_shrink, R.mipmap.img6_shrink, R.mipmap.img7_shrink};

    List<String> textData = new ArrayList<>();

    private void initData() {
        for (int i = 0; i < 3; i++) {
            textData.add("测试文本" + i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

        setContentView(R.layout.activity_main);

        animTextView = (AnimSwitchTextView) findViewById(R.id.animTextView);
        itemAnimImage1 = (SimpleSwitchImageView) findViewById(R.id.itemAnimImage1);
        itemAnimImage2 = (SimpleSwitchImageView) findViewById(R.id.itemAnimImage2);
        itemAnimImage3 = (SimpleSwitchImageView) findViewById(R.id.itemAnimImage3);
        itemAnimText1 = (AnimSwitchTextView) findViewById(R.id.itemAnimText1);
        itemAnimText2 = (AnimSwitchTextView) findViewById(R.id.itemAnimText2);
        itemAnimText3 = (AnimSwitchTextView) findViewById(R.id.itemAnimText3);
        cardAnimView = (SimpleCardAnimView) findViewById(R.id.cardAnimView);

        itemAnimImage2.setDelayed(150);
        itemAnimImage3.setDelayed(300);
        cardAnimView.setDuration(500).setInterpolator(new AccelerateDecelerateInterpolator());

        // 自动切换
        animTextView
                // 自动变换的时间间隔
                .setAutoChangeDelay(3000)
                // 是否自动变换
                .isAutoChange(true)
                // 动画效果
                .setSwitchAnimator(new FadeInFadeOutAnimator())
                // 循环切换的数据集合
                .setData(textData)
                // 触发自动切换
                .showNext();

        // 设置点击监听
        animTextView.setItemClickListener(new BaseAnimSwitchView.OnItemClickListener<String>() {
            @Override
            public void onItemClick(String data, View view) {
                Toast.makeText(MainActivity.this, "点击了当前item:" + data, Toast.LENGTH_SHORT).show();
            }
        });
        itemAnimImage1.setItemClickListener(new BaseAnimSwitchView.OnItemClickListener<Integer>() {
            @Override
            public void onItemClick(Integer data, View view) {
                Toast.makeText(MainActivity.this, "点击的图片id:" + data, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        switchText(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.downTransAnim) {
            switchAnimObj(DownTransAnimator.class);
        } else if (item.getItemId() == R.id.LeftTransAnim) {
            switchAnimObj(LeftTransAnimator.class);
        } else if (item.getItemId() == R.id.FadeInFadeOut) {
            switchAnimObj(FadeInFadeOutAnimator.class);
        }
        return true;
    }

    private void switchAnimObj(Class animator) {
        try {
            itemAnimImage1.setSwitchAnimator((BaseAnimator) animator.newInstance());
            itemAnimImage2.setSwitchAnimator((BaseAnimator) animator.newInstance());
            itemAnimImage3.setSwitchAnimator((BaseAnimator) animator.newInstance());

            itemAnimText1.setSwitchAnimator((BaseAnimator) animator.newInstance());
            itemAnimText2.setSwitchAnimator((BaseAnimator) animator.newInstance());
            itemAnimText3.setSwitchAnimator((BaseAnimator) animator.newInstance());

            cardAnimView.setSwitchAnimator((BaseAnimator) animator.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchText(View view) {


        // 切换数据
        itemAnimImage1.changeData(imgRes[random.nextInt(imgRes.length - 1)]);
        itemAnimImage2.changeData(imgRes[random.nextInt(imgRes.length - 1)]);
        itemAnimImage3.changeData(imgRes[random.nextInt(imgRes.length - 1)]);


        itemAnimText1.changeData("测试数据" + count++);
        itemAnimText2.changeData("测试数据" + count++);
        itemAnimText3.changeData("测试数据" + count++);

        cardAnimView.changeData(new CardModel("最新标题：" + count, imgRes[random.nextInt(imgRes.length - 1)]));
    }
}