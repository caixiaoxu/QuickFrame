package com.frame.baselib.base.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Title : Activity基类
 * Author: Lsy
 * Date: 2/5/21 5:35 PM
 * Version:
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getStyleId()?.let { setTheme(it) }
        setContentView(getLayoutViewId())

        initView()
        initListener()
        initObserver()
        initData()
    }

    /**
     * 样式Id
     */
    fun getStyleId(): Int? = null

    /**
     * 布局Id
     */
    abstract fun getLayoutViewId(): Int

    /**
     * 初始化控件
     */
    abstract fun initView()

    /**
     * 初始化事件
     */
    abstract fun initListener()

    /**
     * 初始化监听
     */
    abstract fun initObserver()

    /**
     * 初始化数据
     */
    abstract fun initData()

    //点击间隔时间
    private val minTime = 1000

    //存储每个View的上次点击时间
    private val clickTimes = HashMap<Int, Long>()

    /**
     * 快速点击事件
     */
    protected fun setOnClickListener(vararg viewId: Int, clickListener: View.OnClickListener) {
        viewId.forEach { resId ->
            findViewById<View>(resId).setOnClickListener { view ->
                //防止多次点击问题
                val cur = System.currentTimeMillis()
                if ((cur - (clickTimes[resId] ?: 0)) < minTime)
                    return@setOnClickListener
                clickTimes[resId] = cur
                clickListener.onClick(view)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}