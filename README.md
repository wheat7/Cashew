# 腰果
腰果Cashew是一个[gank.io](http://gank.io)的客户端，腰果的目标是简洁，但是有质感、漂亮，立志做最好看的[gank.io](http://gank.io)客户端，而不是一个Demo（捂脸，逃），并且，腰果除了网络、图片加载、基本没有使用其他的第三方开源项目，基本都基于原生实现，腰果整体上使用了DataBinding，能作为一个很好的Databinding的使用范例，所以我又想称腰果为：Gank with DataBinding

<img src="/screenshots/cashew1.png" alt="screenshot" title="screenshot" width="270" height="486" />   <img src="/screenshots//cashew2.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/cashew3.png" alt="screenshot" title="screenshot" width="270" height="486" />  

<img src="/screenshots/cashew4.png" alt="screenshot" title="screenshot" width="270" height="486" /> <img src="/screenshots/cashew5.png" alt="screenshot" title="screenshot" width="270" height="486" /> <img src="/screenshots/cashew6.png" alt="screenshot" title="screenshot" width="270" height="486" />

  <img src="/screenshots/cashew7.png" alt="screenshot" title="screenshot" width="270" height="486" />   <img src="/screenshots/cashew8.png" alt="screenshot" title="screenshot" width="270" height="486" /> <img src="/screenshots/cashew9.png" alt="screenshot" title="screenshot" width="270" height="486" />
  
 <img src="/screenshots/cashew10.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/cashew11.png" alt="screenshot" title="screenshot" width="270" height="486" />  <img src="/screenshots/cashew12.png" alt="screenshot" title="screenshot" width="270" height="486" />
  
## 技术细节
看到腰果的UI同学心里肯定想的是：这到底用了什么库啊？其实，腰果除了网络模块使用了Retrofit+RxJava，图片加载模块使用了Glide，基本所有的UI都是基于原生实现，或是在原生上进行修改，并没有依赖什么UI库，上拉加载都是手动实现的喔，腰果的首要目标是质感，是一个App，是一个产品，而不是一个Demo，不仅为了漂亮，也为了更高的参考价值，因为我们工作中要做的，是一个有质感的产品，而并不是一个Demo      

腰果使用了以下开源库

* Retrofit
* OkHttp
* RxJava & RxAndroid
* Glide
* PhotoView
* SwipeBackHelper
* Leakcanary
* BottomNavigationViewEx

腰果实现的技术

* 使用Databinding作为整体架构
* 欢迎界面的BulingBulingTextView 
* 手动实现了上拉加载更多，并加入了加载错误的容错，点击重新加载回调
* CardView + 多种type的RecyclerView展示干货列表
* 结合BottomNavigationViewEx实现的底部导航，BottomNavigationViewEx继承于原生的BottomNavigationView
* 使用自定义不可滑动，没有动画的ViewPager来管理不同的Fragment模块
* 使用ViewPager来展示不同日期，不同类型的干货列表
* OkHttp + Retrofit + RxJava 实现的网络模块
* Glide加载图片，处理图片缓存
* webview展示干货，水平进度条显示加载进度
* 结合SwipeBackHelper实现的滑动返回功能（现在貌似很流行）
* 简单的分享功能
* 网络错误时的界面容错

腰果的数据来自

* [gank.io](http://gank.io) 感谢！
  
## 腰果的功能
~~腰果的功能当然是补肾咯！所以腰果暂时没有加入福利（妹子）功能，后续考虑加入~~

**已上**

### 腰果现在实现的功能
* 每日干货
* 分类干货
* 妹纸
* 清除图片缓存
* webview查看干货
* 分享

### 后续考虑完善的功能 

* 提交干货
* 搜索
* 无网缓存

## 源码分析&教程
后续会发布到我的[小站](http:wheat7.com)和[简书](http://www.jianshu.com/u/6005415e3069)

## 更新日志
* v1.0(2017-8-20): 初版
* v1.1(2017-8-21) : 加入网络错误时的界面容错，点击重新加载，修复一些bug
* v1.2(2017-8-23) : 熬夜上了妹纸 这个功能  v1.2.1(2017-8-24) 修复bug，妹子大图浏览改为ViewPager可互动

## 一起讨论
感觉gank没有官方的讨论群，斗胆建一个，大家加群一起讨论     
群号：198233012

  <img src="/screenshots/qq.jpeg" alt="screenshot" title="screenshot" width="270" height="370" />  
  
## 我的其他项目推荐
### [VRPlayer-一个极简但是强大的本地VR视频播放器](https://github.com/wheat7/VRPlayer) 带源码分析喔，有对这块感兴趣的同学可以看一看，Star&Fork，滋瓷一下，吼不吼阿

## License

Apache-2.0

