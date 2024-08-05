package com.blueeve.dailyhitokoto

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog

class Assignment : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 创建并显示对话框
        val dialog = AlertDialog.Builder(this, R.style.Theme_MyDialog)
            .setView(R.layout.assignment) // 使用不同的对话框布局文件
            .create()

        dialog.show()

        // 获取对话框的窗口
        val window = dialog.window
        window?.let {
            // 获取窗口的布局参数
            val layoutParams = it.attributes

            // 设置对话框的位置为屏幕中心
            layoutParams.gravity = Gravity.CENTER

            // 设置对话框的宽度和高度
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.8).toInt()
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

            // 更新窗口的布局参数
            it.attributes = layoutParams
        }
    }
}
