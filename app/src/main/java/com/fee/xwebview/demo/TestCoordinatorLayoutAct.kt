package com.fee.xwebview.demo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/11/16<br>
 * Time: 20:15<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
class TestCoordinatorLayoutAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_coordinator_with_collapsingtoolbar)
//        setContentView(R.layout.activity_test_coordinator)
        val tvContent: TextView = findViewById(R.id.tv)
        tvContent.text = buildContentText(30)
        findViewById<Toolbar>(R.id.toolbar)?.let {
            it.setOnClickListener {
                startActivity(Intent(this, TestBehaviorActivity::class.java))
            }
        }
        supportActionBar?.hide()
//        supportActionBar?.customView = findViewById(R.id.toolbar)
    }

    private fun buildContentText(count: Int): String {
        var content = ""
        for (index in 0 until count) {
            content += "$index +\n"
        }
        return content
    }
}