# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep the entire PayWithTransactpay class and its methods
-keep class com.transactpay.transactpay_android.PayWithTransactpay {
    *;
}

# Keep the companion object of PayWithTransactpay and the newIntent method
-keepclassmembers class com.transactpay.transactpay_android.PayWithTransactpay$Companion {
    public static *** newIntent(...);
}

# Keep the Success and Failed classes to avoid obfuscation
-keep class com.transactpay.transactpay_android.Success {
    *;
}

-keep class com.transactpay.transactpay_android.Failed {
    *;
}

# Avoid obfuscating Kotlin companion objects in general
-keepclassmembers class * {
    companion object;
}

# If you use reflection, ensure to preserve those methods
-keepclassmembers class * {
    *;
}
