### AnimSwitchComponent

> 该库主要用来针对在同一个组件或布局内，动态更换内容的一个封装库，使用主要是扩展库中的`BaseAnimSwitchView`抽象类，根据要切换内容实现内部的抽象方法。库中封装了一个`TextView`的切换控件，可直接使用，`demo`中简单封装了`ImageVIew`和一个自定义布局的切换。切换动画提供了抽象父类，库中定义了`下移`、`左移`、`淡入淡出`3中动画，可以继承`BaseAnimator`进行扩展。

#### 效果图

<img width="300" width=“500” src="screen/3月-29-2017 11-13-10.gif"/>

#### 添加依赖

```
// add to root build.gralde
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

// add to app build.gradle
dependencies {
    compile 'com.github.dcq123:StepView:v0.0.2'
}
```

#### 使用及扩展

##### AnimSwitchTextView

> 该view是进行文本切换的`TextView`容器，可控制自动循环切换提供的数据集合，也可手动触发切换，使用如下：

xml中添加布局：

```xml
<com.github.qing.animswitchlib.widget.AnimSwitchTextView
        android:id="@+id/animTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        app:textColor="#fff"
        app:textPadding="16dp"
        app:textSize="16sp" />
```

代码中设置：

```java
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
```

最终的效果就如效果图最顶部的魂环淡入淡出的效果。

##### 扩展BaseAnimSwitchView

该抽象类本质是个`FrameLayout`，定义了3中泛型，`MODEL`、`V`、`CHILD`，声明如下：

```java
abstract class BaseAnimSwitchView<MODEL, V extends View, CHILD 
                        extends BaseAnimSwitchView> extends FrameLayout
```

其中`MODEL`代表的是填充数据的model类型，如`demo`中定义的`CardModel`，`V`代表具体切换的控件，它是一个`View`的子类，所以可以使用任意的`View`，`CHILD`是扩展的`BaseAnimSwitchView`的类型(即自己扩展的子类)，之所以声明`CHILD`类型是为了让`API`方法的链式调用。

抽象方法：

```java
// 定义要扩展的View类型
protected abstract V createNewItemView();

// 绑定View数据
protected abstract void bindItemView(MODEL data, V view);
```

扩展时实现以上两个方法进行`View`的创建和绑定。

暴露的API方法：

| 名称                                       | 描述       |
| ---------------------------------------- | -------- |
| setDuration(int duration)                | 设置动画持续时间 |
| setDelayed(int delayed)                  | 设置动画延迟时间 |
| setInterpolator(Interpolator interpolator) | 设置动画插值器  |
| setSwitchAnimator(BaseAnimator animator) | 切换动画实现类  |
| changeData(MODEL data)                   | 触发内容切换   |

以上API都支持链式调用。

示例最底部自定义的扩展如下，具体细节可参考`demo`：

```java
public class SimpleCardAnimView extends BaseAnimSwitchView<CardModel, View, SimpleCardAnimView> {

    .....

    @Override
    protected View createNewItemView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.card_item_layout, this, false);
    }

    @Override
    protected void bindItemView(CardModel data, View view) {
        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText(data.getTitle());

        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        imageView.setImageResource(data.getIconId());
    }
}
```

可根据需求按照上面的方式自行扩展。

##### 扩展BaseAnimator

该类触发具体的切换动画执行，抽象了具体切换动画的设置：

```java
// 传递了当前View和下一个要切换显示的view，以及控件的尺寸
// 定义自己的动画只需实现该方法即可
public abstract void setAnimator(View currentView, View nextView, Rect rect);

```

淡入淡出实现：

```java
 @Override
public void setAnimator(View currentView, View nextView, Rect rect) {

    ObjectAnimator currentAlphaAnim = ObjectAnimator.ofFloat(currentView, "alpha", 1f, 0f);
    ObjectAnimator nextAlphaAnim = ObjectAnimator.ofFloat(nextView, "alpha", 0f, 1f);

    ViewCompat.setTranslationY(nextView, 0);
    ViewCompat.setTranslationX(nextView, 0);
    ViewCompat.setAlpha(nextView, 0);
    this.mAnimatorSet.play(nextAlphaAnim).after(100).after(currentAlphaAnim);
}
```

可按照上面的方式自行扩展，已提供的切换动画类有：`DownTransAnimator`，`LeftTransAnimator`，`FadeInFadeOutAnimator`。