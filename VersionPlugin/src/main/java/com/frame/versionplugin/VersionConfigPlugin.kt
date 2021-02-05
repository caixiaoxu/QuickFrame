package com.frame.versionplugin

import Testing.androidTestImplementation
import Testing.testImplementation
import com.android.build.gradle.*
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.logging.Logger

/**
 * Title :
 * Author: Lsy
 * Date: 2/3/21 4:34 PM
 * Version:
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
const val api = "api"
const val implementation = "implementation"

class VersionConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        //配置项目内模块
        project.plugins.config(project)
        //打印各流程时间
        project.gradle.addListener(TimingsListener())
    }

    /**
     * 配置项目内的模块
     * 根据AppModel或Library,分别进行相应的配置信息
     */
    private fun PluginContainer.config(project: Project) {
        whenPluginAdded {
            when (this) {
                is AppPlugin -> {
                    //公共插件
                    project.configCommonPlugin()
                    //公共 android 配置项
                    project.extensions.getByType<AppExtension>().applyAppCommons(project)
                    //公共依赖
                    project.configAppDependencies()
                }
                is LibraryPlugin -> {
                    project.configCommonPlugin()
                    //公共 android 配置项
                    project.extensions.getByType<LibraryExtension>().applyLibraryCommons(project)
                    //公共依赖
                    project.configLibraryDependencies()
                }
                is KotlinAndroidPluginWrapper -> {
                    //根据 project build.gradle.kts 中的配置动态设置 kotlinVersion
                    project.getKotlinPluginVersion()?.also { kotlinVersion ->
                        BuildConfig.kotlin_version = kotlinVersion
                    }
                }
            }
        }
    }

    /**
     * 公共plugin插件依赖
     */
    private fun Project.configCommonPlugin() {
        plugins.apply("kotlin-android")
        plugins.apply("kotlin-android-extensions")
    }

    /**
     * app Module 配置项，此处固定了 applicationId
     */
    private fun AppExtension.applyAppCommons(project: Project) {
        defaultConfig { applicationId = BuildConfig.applicationId }
        applyBaseCommons(project)
    }

    /**
     * library Module 配置项
     */
    private fun LibraryExtension.applyLibraryCommons(project: Project) {
        applyBaseCommons(project)

        /*onVariants.withBuildType("debug") {
            androidTest {
                enabled = false
            }
        }*/
    }

    /**
     * 公共需要添加的设置，如sdk目标版本，jdk版本，jvm目标版本等
     */
    private fun BaseExtension.applyBaseCommons(project: Project) {
        compileSdkVersion(BuildConfig.compileSdkVersion)
        buildToolsVersion(BuildConfig.buildToolsVersion)

        defaultConfig {
            minSdkVersion(BuildConfig.minSdkVersion)
            targetSdkVersion(BuildConfig.targetSdkVersion)
            versionCode = BuildConfig.versionCode
            versionName = BuildConfig.versionName
            testInstrumentationRunner = BuildConfig.testInstrumentationRunner
            consumerProguardFiles(BuildConfig.consumerrules)
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        project.tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    /**
     * app Module 公共依赖
     */
    private fun Project.configAppDependencies() {
        dependencies.apply {
            add(implementation, fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
            add(implementation, BuildConfig.Kotlin.kotlin_stdlib)
            add(implementation, BuildConfig.Kotlin.core_ktx)
            add(implementation, AndroidX.appcompat)
            add(implementation, AndroidX.constraintlayout)
            add(implementation, Google.material)
            configTestDependencies()

            // 统一引入
            //frame-baselib 项目基础库
            add(implementation, (project(":frame-baselib")))
            //kotlin-base Kotlin扩展库
            add(implementation, (project(":kotlin-base")))
        }
    }

    /**
     * library Module 公共依赖
     */
    private fun Project.configLibraryDependencies() {
        dependencies.apply {
            add(implementation, fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
            add(implementation, BuildConfig.Kotlin.kotlin_stdlib)
            add(implementation, BuildConfig.Kotlin.core_ktx)
            add(implementation, AndroidX.appcompat)
            add(implementation, AndroidX.constraintlayout)
            add(implementation, Google.material)
            configTestDependencies()
        }
    }

    /**
     * test 依赖配置
     */
    private fun DependencyHandler.configTestDependencies() {
        testImplementation(Testing.testLibraries)
        androidTestImplementation(Testing.androidTestLibraries)
    }
}