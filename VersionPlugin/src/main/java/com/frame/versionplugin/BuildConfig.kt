/**
 * Title : 编译配置信息
 * Author: Lsy
 * Date: 2/5/21 2:16 PM
 * Version:
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object BuildConfig {
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.3"
    const val minSdkVersion = 21
    const val targetSdkVersion = 30

    const val applicationId = "com.frame.quickframe"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val consumerrules = "consumer-rules.pro"

    var versionName = "1.0"
    var versionCode = 1

    var kotlin_version = "1.4.21"
    var gradle_version = "4.1.2"

    object Kotlin {
        val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
        val core_ktx = "org.jetbrains.kotlin:kotlin-stdlib:1.2.0"
        val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$gradle_version"
    }
}