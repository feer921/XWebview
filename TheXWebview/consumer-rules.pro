# X5 库的混淆配置 ******** @start
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**

-keep class com.tencent.smtt.** {
    *;
}

-keep class com.tencent.tbs.** {
    *;
}

# X5 库的混淆配置 ******** @end