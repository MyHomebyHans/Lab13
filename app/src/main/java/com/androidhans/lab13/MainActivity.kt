package com.androidhans.lab13

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    //建立BroadcastReceiver物件
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //接收廣播後，解析Intent取的字串訊息
            intent.extras?.let {
                val tv_msg = findViewById<TextView>(R.id.tv_msg)
                tv_msg.text = "${it.getString("msg")}"
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //將三個按鈕設定監聽器，內部將頻道名稱傳入register()方法並執行
        findViewById<Button>(R.id.btn_music).setOnClickListener {
            register("music")
        }
        findViewById<Button>(R.id.btn_new).setOnClickListener {
            register("new")
        }
        findViewById<Button>(R.id.btn_sport).setOnClickListener {
            register("sport")
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)  //註銷廣播
        super.onDestroy()
    }
    //register()方法內將頻道註冊後，啟動Service並使用intent傳遞使用者切換的頻道資訊，當receiver收到廣播後，將訊息顯示於TextView
        private fun register(channel: String) {
           //建立IntentFilter物件來指定接收的識別標籤為channel頻道，並註冊Receiver
            val initentFilter = IntentFilter(channel)
        //註冊Receiver
        registerReceiver(receiver,initentFilter)
        //建立Intent物件，使其夾帶頻道資料，並啟動MyService服務
        val i = Intent(this, MyService::class.java)
        startService(i.putExtra("channel", channel))

        }
    }