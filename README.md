# XProjectFramework
基于MVVM架构搭配ARouter搭建的项目框架

## 一、说明
#### 本项目主要基于MVVM + ARouter搭建整体的框架，在新项目的时候方便移植并根据需求做定制化修改，提升开发速度


## 二、引用的依赖库

- **框架：ARouter + MVVM + DataBinding**
- **网络：RxEasyHttp（Retrofit+okHttp）**
- **异步：RxJava2 + RxAndroid2**
- **通信：EventBus/RxBus**
- **RV：  BRVAH**
- **图片：Glide**
- **存储：GreenDao + MMKV**
- **日志：XLog**
- **适配：autoSize**
- **推送：友盟/信鸽**
- **打包：walle**
- **埋点：根据埋点规则来，是否能单独抽取出一个定制库**


## 三、注意事项

### （一）新建模块步骤
- **1、New Module -> Android Library**
- **2、删除新模块中不必要的内容：lib、src\test、src\androidTest目录，加快编译速度**
- **3、AndroidManifest.xml中application配置拷贝app模块下的AndroidManifest.xml配置，并拷贝缺失的公共资源**
- **4、新模块的build.gradle配置**

```groovy
// 1、kotlin需使用 kotlin-kapt 插件
apply plugin: 'kotlin-kapt'
android {
    compileSdkVersion 30

    defaultConfig {
        ···  
        // 2、Java添加以下编译参数
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: "vip"]
            }
        }
        // 2、Kotlin添加以下编译参数 
        kapt {
            arguments {
                arg("AROUTER_MODULE_NAME", "vip")
            }
        }
        ···
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

    /* ------------ARouter-------------- */
    implementation "com.alibaba:arouter-api:$rootProject.ext.arouterVersion"
    // 3、kotlin使用 kapt, Java使用 annotationProcessor
    kapt "com.alibaba:arouter-compiler:$rootProject.ext.arouterVersion" 
}
``` 

#### 注意：<br>1、每个模块都需要添加上述的2、3点，加入相应的APT配置，否则ARouter跨模块路由跳转时会失败
<br>2、ARouter跳转其他模块时，记得在build.gradle中引入相应的模块依赖，否则也是ARouter也会跳转失败

- **5、单独调试模块** 