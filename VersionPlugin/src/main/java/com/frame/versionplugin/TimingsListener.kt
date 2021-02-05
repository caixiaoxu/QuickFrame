package com.frame.versionplugin

import org.codehaus.groovy.runtime.DefaultGroovyMethods.printf
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState
import org.gradle.internal.impldep.com.google.api.client.util.ArrayMap
import java.util.concurrent.TimeUnit

/**
 * Title :
 * Author: Lsy
 * Date: 2/4/21 3:42 PM
 * Version:
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class TimingsListener : TaskExecutionListener, BuildListener {
    private var startTime: Long = 0
    private val timings = ArrayMap<Long, String>()

    override fun beforeExecute(task: Task) {
        startTime = System.nanoTime()
    }

    override fun afterExecute(task: Task, state: TaskState) {
        val ms = TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        timings.add(ms, task.path)
        task.project.logger.warn("${task.path} took ${ms}ms")
    }

    override fun buildStarted(gradle: Gradle) {
        println("build Started.")
    }

    override fun settingsEvaluated(settings: Settings) {
        println("settings Evaluated.")
    }

    override fun projectsLoaded(gradle: Gradle) {
        println("projects Loaded.")
    }

    override fun projectsEvaluated(gradle: Gradle) {
        println("project Evaluated.")
    }

    override fun buildFinished(result: BuildResult) {
        println("Task timings:")
        for (timing in timings) {
            if (timing.key >= 50) {
                println("${timing.key}ms  ${timing.value}")
            }
        }
    }
}