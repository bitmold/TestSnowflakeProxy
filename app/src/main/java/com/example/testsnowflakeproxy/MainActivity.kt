package com.example.testsnowflakeproxy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File

import IPtProxy.IPtProxy
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cacheDir = File(cacheDir, "pt")
        if (!cacheDir.exists()) cacheDir.mkdir()

        IPtProxy.setStateLocation(cacheDir.absolutePath)

        val start = findViewById<Button>(R.id.start)
        val stop = findViewById<Button>(R.id.stop)
        val label = findViewById<TextView>(R.id.label)

        val capacity = 1
        val broker = "https://snowflake-broker.bamsoftware.com/"
        val relay = "wss://snowflake.bamsoftware.com/"
        val stun = "stun:stun.stunprotocol.org:3478"
        val logFile: String? = null
        val keepLocalAddresses = true
        val unsafeLogging = false

        var count = 0

        start.setOnClickListener {
            IPtProxy.startSnowflakeProxy(capacity.toLong(), broker, relay, stun, logFile, keepLocalAddresses, unsafeLogging) {
                count++
                label.setText("Update: " + count)
            }
        }

        stop.setOnClickListener {
            IPtProxy.stopSnowflakeProxy()
        }

    }
}