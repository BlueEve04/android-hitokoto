package com.blueeve.dailyhitokoto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class Floatsett : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_floatsett) // 设置布局文件

        val asmt: Button = findViewById(R.id.asmt)
        asmt.setOnClickListener {
            val intent = Intent(this@Floatsett, Assignment::class.java)
            startActivity(intent)
        }

        val mngt: Button = findViewById(R.id.mngt)
        mngt.setOnClickListener {
            val intent = Intent(this@Floatsett, Moneyget::class.java)
            startActivity(intent)
        }

        // 不需要在这里创建对话框
        // dialog.show() 的调用可以移到按钮点击事件中
    }
}
