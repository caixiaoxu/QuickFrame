buildscript {
    repositories {
        // 需要添加 jcenter 否则会提示找不到 gradlePlugin
        jcenter()
        google()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
    }
}
plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
    google()
    jcenter()
}

dependencies {
    compileOnly(gradleApi())
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
    compileOnly("com.android.tools.build:gradle:4.1.2")
}

gradlePlugin {
    plugins {
        create("version") {
            //自定义plugin的id，其他module引用要用到
            id = "version-plugin"
            //指向自定义plugin类，因为我直接放在目录下，所以没有多余路径
            implementationClass = "com.frame.versionplugin.VersionConfigPlugin"
        }
    }
}