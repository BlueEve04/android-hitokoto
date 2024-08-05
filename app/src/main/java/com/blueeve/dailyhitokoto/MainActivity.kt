// src/main/java/com/blueeve/dailyhitokoto/MainActivity.kt
package com.blueeve.dailyhitokoto

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : ComponentActivity(), CardAdapter.ColorChangeListener {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: CardAdapter
    private val maxCards = 5

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
//        val totbView = findViewById<View>(R.id.totb)
//
//        // Apply the appropriate background color based on the current theme
//        val isDarkMode = resources.configuration.uiMode and
//                android.content.res.Configuration.UI_MODE_NIGHT_MASK ==
//                android.content.res.Configuration.UI_MODE_NIGHT_YES;
//        var backgroundColor=0x000000
//        if (isDarkMode) {
//            backgroundColor=0xFFFFD3
//
//        } else {
//            backgroundColor=0x464522
//        }
//        totbView.setBackgroundColor(backgroundColor)

        try {
            Log.d("OnStart","1......目前一切正常")
            val aboutbutt: Button = findViewById(R.id.aboutbutt)
            aboutbutt.setOnClickListener{
                Log.d("OnStart","4事件绑定......目前一切正常")
                val intent = Intent(this@MainActivity, Floatsett::class.java)
                Log.d("OnStart","5......目前一切正常")
                startActivity(intent)

            }
        }catch (e:Exception){

            Log.d("FATAL","FATAL!!!启动不了辣O△O")
        }
        // Initialize ViewPager
        viewPager = findViewById(R.id.viewPager)
        adapter = CardAdapter(
            mutableListOf(
                Pair("欢迎回到Daily Hitokoto！", ""),
                Pair("来看看今天的句子~~", ""),
                Pair("向右滑动继续-->","")
            ),
            this
        )
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.offscreenPageLimit = 3
        viewPager.setPageTransformer { page, position ->
            page.alpha = 0.0f + (1 - abs(position)) // 控制透明度
            page.translationX = -position * page.width // 控制滑动效果
            page.scaleX = 1 - 0.2f * abs(position) // 控制缩放效果
            page.scaleY = 1 - 0.2f * abs(position) // 控制缩放效果
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d(TAG, "Page selected: $position")

                if (position == adapter.itemCount - 2) {
                    Log.d(TAG, "Near end of list, adding new card")
                    addNewCard()
                }

                if (adapter.itemCount > maxCards && position == 1) {
                    Log.d(TAG, "More than $maxCards cards, removing the first card")
                    adapter.removeItem(0)
                }
            }
        })
    }

    private fun addNewCard() {
        try {
            val datafrom = "HitokotoOfficial"
            val fileid = generateRandomInt(1, 25)
            val innerid = generateRandomInt(0, 557)
            val data = extractDataById(this, "$datafrom/$fileid.json", innerid)

            if (data != null) {
                val hitokoto = data["hitokoto"]
                val from = data["from"]
                val fromWho: String = if (data["from_who"]=="null"){
                    ""
                } else{
                    data["from_who"].toString()
                }

                val newCardHitokoto = "$hitokoto"
                val newCardFromFromWho = "————「$from」 $fromWho"
                adapter.addItem(newCardHitokoto, newCardFromFromWho)
                Log.d(TAG, "New card added: $newCardHitokoto id: $fileid - $innerid")
            } else {
                throw Exception("Failed in reading things")
            }
        } catch (e: Exception) {
            Log.d("JsonREAD", "Fatal! Unable!臣妾做不到！")
        }
    }

    private fun generateRandomInt(min: Int, max: Int): Int {
        return Random.nextInt(min, max + 1) // 生成[min, max]范围内的随机整数
    }

    override fun onColorChange(cardBackgroundColor: Int, textColor: Int) {
        val titleBack = findViewById<View>(R.id.titleback)
        val titleTextView = findViewById<TextView>(R.id.title)
        val aboutButt = findViewById<TextView>(R.id.aboutbutt)

        // Animate background color change
        animateColorChange(titleBack, "backgroundColor", titleBack.backgroundTintList?.defaultColor ?: Color.GRAY, cardBackgroundColor)
        animateColorChange(titleTextView, "textColor", titleTextView.currentTextColor, textColor)
        animateColorChange(aboutButt, "textColor", aboutButt.currentTextColor, textColor)
    }

    private fun animateColorChange(view: View, propertyName: String, startColor: Int, endColor: Int) {
        ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor).apply {
            duration = 500 // Animation duration in milliseconds
            addUpdateListener { animator ->
                val animatedColor = animator.animatedValue as Int
                when (propertyName) {
                    "backgroundColor" -> view.setBackgroundColor(animatedColor)
                    "textColor" -> (view as TextView).setTextColor(animatedColor)
                }
            }
            start()
        }
    }
}
