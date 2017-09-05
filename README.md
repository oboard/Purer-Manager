# Purer-Manager
什么是Purer？能吃吗？很抱歉不能吃😂 
Purer 框架的功能是让APP做到纯净，支持的APP 需要调用Purer API，提供的API只需要我来维护
Purer Manager内置权限管理，指定程序可以调用框架的哪些API
##
###首先把Purer 服务添加到项目中，文件链接：
https://github.com/oboard/Purer-Manager/blob/master/Purer-Test/app/src/main/java/com/oboard/purertest1/PurerService.java

然后是配置Purer

在AndroidManifest.xml文件中的applicaion 里添加这段
```xml
<!--purermodule的值为on 将会显示在Purer Manager的列表中-->
<meta-data android:name="purermodule" android:value="on" /> 
<!--purerdescription的值为此模块的描述-->
<meta-data android:name="purerdescription" android:value="我是描述" />
<!--purerminversion的值为支持Purer API 的最低版本-->
<meta-data android:name="purerminversion" android:value="1" />
```
现在就可以开始调用Purer API 了

初始化Purer 服务和检测状态栏


```javascript
 @Override
 protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main); 
     PurerService.init(this);
     if (PurerService.getState()) {
        setTitle("Purer框架可用"); 
        //下拉通知栏
        //PurerService.notification(true);
    }
    else
        setTitle("Purer框架不可用");
 }
```
教程就到这里，下列是各个API 的描述


###API 2

died()//程序自杀

notification(String ticker, String title, String text, int number, Icon icon)//通知

notificationpage(Boolean show)//关闭和下拉通知栏

open(String package)//启动应用程序

toast(String message)//全局吐司

snack(String message)//全局快餐

###API 1

notification(Boolean show)//关闭和下拉通知栏

open(String package)//启动应用程序

toast(String message)//全局吐司

snack(String message)//全局快餐
