package com.example.fucai

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val ballViewText = findViewById<TextView>(R.id.ballText)

        ballViewText.movementMethod = ScrollingMovementMethod.getInstance()

        findViewById<Button>(R.id.btnOpen).setOnClickListener {
            val redBalls = generateRedBalls().joinToString(separator=","){String.format("%2d", it)}
            val blueBall = generateBlueBall()

            val span = SpannableStringBuilder(ballViewText.text)
            var lastLen = span.length
//            span.append("Red:")
            span.append(redBalls)
            span.setSpan(ForegroundColorSpan(Color.RED), lastLen, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            lastLen = span.length
            span.append("  ")
//            span.append("Blue: ")
            span.append(blueBall.toString())
            span.setSpan(ForegroundColorSpan(Color.BLUE), lastLen, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            span.append("\n\n")

            ballViewText.text = span
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            ballViewText.text = ""
            ballViewText.scrollY = 0
        }

    }
    // 使用Fisher-Yates洗牌算法（时间复杂度O(n)）
    private fun generateRedBalls(): List<Int> {
        val pool = (1..32).toMutableList()
        val selected = mutableListOf<Int>()

        // 抽6次
        repeat(6) {
            val index = (0 until pool.size).random()
            selected.add(pool.removeAt(index)) // 核心：移除已选元素避免重复
        }
        return selected.sorted() // 按数字大小排序显示
    }
    private fun generateBlueBall(): Int {
        return (1..16).random() // 简单范围随机
    }
}

