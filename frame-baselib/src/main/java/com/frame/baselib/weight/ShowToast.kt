package com.frame.baselib.weight

import android.view.View
import android.widget.Toast
import com.frame.baselib.common.GlobalParamter

/**
 * Title : Toast 封装
 * Author: Lsy
 * Date: 2/5/21 6:25 PM
 * Version:
 * Description:
 * {
 *      统一使用Application Context，避免Activity关闭后报错
 *      静态Toast 避免多次显示问题
 *      通过闭包添加可自定配置的回调
 *      自定义View在Android Q(11)已经被弃用
 * }
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
object ShowToast {
    //使用同一个Toast，避免多次显示问题
    private val mToast: Toast by lazy { Toast(GlobalParamter.globalContext) }

    /**
     * 基本显示Toast方法
     * @param time 显示时长 Toast.LENGTH_SHORT/Toast.LENGTH_LONG
     * @param config 额外的配置回调
     */
    private fun show(time: Int, config: ((Toast) -> Unit)?) {
        mToast.duration = time
        config?.invoke(mToast)
        mToast.show()
    }

    /**
     * 显示Toast文本方法
     * @param msg 显示文本
     * @param time 显示时长 Toast.LENGTH_SHORT/Toast.LENGTH_LONG
     * @param config 额外的配置回调
     */
    private fun showTxt(msg: String, time: Int, config: ((Toast) -> Unit)?) {
        mToast.setText(msg)
        show(time, config)
    }

    /**
     * 显示Toast文本资源方法
     * @param msg 显示文本资源
     * @param time 显示时长 Toast.LENGTH_SHORT/Toast.LENGTH_LONG
     * @param config 额外的配置回调
     */
    private fun showTxtRes(resId: Int, time: Int, config: ((Toast) -> Unit)?) {
        mToast.setText(resId)
        show(time, config)
    }

    /**
     * 显示文本短消息
     * @param msg 文本
     * @param config 额外的配置回调
     */
    fun showShort(msg: String, config: ((Toast) -> Unit)? = null) {
        showTxt(msg, Toast.LENGTH_SHORT, config)
    }

    /**
     * 显示文本长消息
     * @param msg 文本
     * @param config 额外的配置回调
     */
    fun showLong(msg: String, config: ((Toast) -> Unit)? = null) {
        showTxt(msg, Toast.LENGTH_LONG, config)
    }

    /**
     * 显示文本资源短消息
     * @param resId 文本资源
     * @param config 额外的配置回调
     */
    fun showShort(resId: Int, config: ((Toast) -> Unit)? = null) {
        showTxtRes(resId, Toast.LENGTH_SHORT, config)
    }

    /**
     * 显示文本资源长消息
     * @param resId 文本资源
     * @param config 额外的配置回调
     */
    fun showLong(resId: Int, config: ((Toast) -> Unit)? = null) {
        showTxtRes(resId, Toast.LENGTH_LONG, config)
    }

    /**
     * 显示自定义布局Toast（方法在Android R 被弃用）
     * @param view 自定义View
     * @param time 显示时长 Toast.LENGTH_SHORT/Toast.LENGTH_LONG
     * @param config 额外的配置回调 (Toast,布局)
     */
    private fun showCustom(view: View, time: Int, config: ((Toast, View) -> Unit)? = null) {
        mToast.view = view
        mToast.duration = time
        config?.invoke(mToast, view)
        mToast.show()
    }

    /**
     * 显示自定义布局短消息（方法在Android R 被弃用）
     * @param view 自定义View
     * @param config 额外的配置回调 (Toast,布局)
     */
    fun showCustomShort(view: View, config: ((Toast, View) -> Unit)? = null) {
        showCustom(view, Toast.LENGTH_SHORT, config)
    }

    /**
     * 显示自定义布局长消息（方法在Android R 被弃用）
     * @param view 自定义View
     * @param config 额外的配置回调 (Toast,布局)
     */
    fun showCustomLong(view: View, config: ((Toast, View) -> Unit)? = null) {
        showCustom(view, Toast.LENGTH_LONG, config)
    }
}