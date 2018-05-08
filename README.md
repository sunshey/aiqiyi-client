项目简介
----
这是aiqiyi项目的客户端，服务端使用node开发接口：[项目地址](https://github.com/sunshey/aiqiyi-server)，客户端使用kotlin语言开发。这个app由三个模块组成：`首页`-展示爱奇艺免费电影列表，`论坛`-展示所有人的发言和自己的发言，`我的`-登录、注册、修改图像、关注微信号、分享、清除缓存、检查更新等功能

kotlin介绍
----
kotlin是Google在2017年5月22日I/O大会提出的替代Java编写Android的开发语言，由于它的非空检查、lambda语法、直接使用id等一些属性让它很快成为众多程序员的首选语言，当然我也不例外，这里特别记录一下使用kotlin的过程和心得：  
* **使用前准备**
1. 在AndroidStudio中安装kotlin插件
2. 在project的根build.grade中添加：
```
buildscript {
    ext.kotlin_version = '1.2.40'
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url 'https://maven.google.com' }
    }
   dependencies {
        classpath 'com.android.tools.build:gradle:3.0.1'
        classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.7.2'
        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.5'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
  }
```
3. 在app的build.grade中添加：
```
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
 }
```
4. clean一下，就可以使用了
* **使用过程**
1. kotlin没有Java中的extends,implements这些表示继承、实现的关键字，取而代之的是：
2. kotlin中表示类对象的关键词有class、object、interface、file等，使用方法和Java中区别不大
3. kotlin中表示实例对象的关键字有var、val,var表示可变的，val表示不可变的，一般常量用val，其他的都用var
4. kotlin的一大特色就是非空检查，因此之前声明使用？的对象在使用时要在后面加两个！，否则编译器会报错
5. 在build.grade文件中添加了apply plugin: 'kotlin-android-extensions'，就可以直接在activity或fragment中使用控件id，不必在通过findviewById或butterknife来查找控件
6. kotlin中需要使用某个类时比如跳转到TestActivity,使用类名是TestActivity::class.java,和Java略有不同，在activity某个匿名方法中使用this，表示方式是this@TestActivity
7. 所有方法的关键字都是fun,如果有返回值，直接在方法后面加 :和返回类型
8. 如果使用了recyclerview，添加adapter时需要有数据且不为空，否则直接会报空指针，不知道是我使用的不对，还是kotlin就是这样的用法:blush:
9. kotlin中可以使用$符号将非string类型的对象转换成string，比如 var a = 0  var c ="$movieId" 或者 var c ="${movieId}",加大括号用在非单一类型的对象 如 person类等
10. kotlin中语法糖就是lambda表达式，这样可以让代码更好看，比如：
```
 indexMovieAdapter!!.setOnItemClickListener { _, _, position ->
            val moviewInfo = indexMovieAdapter!!.getItem(position)
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra("id", moviewInfo!!.id)
            startActivity(intent)

        }
   toolbar.setNavigationOnClickListener {
            //it 代表view
            finish() }
 ```
 如果只有一个缺省参数，缺省参数用it来代替


技术总结
---
* kotlin
