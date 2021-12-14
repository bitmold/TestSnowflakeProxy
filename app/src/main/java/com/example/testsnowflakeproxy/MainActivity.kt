package com.example.testsnowflakeproxy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File

import IPtProxy.IPtProxy;
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stateLocation = File(cacheDir, "pt")
        if (!stateLocation.exists()) stateLocation.mkdir()

        IPtProxy.setStateLocation(stateLocation.absolutePath)

        // android activity config
        val start = findViewById<Button>(R.id.start)
        val stop = findViewById<Button>(R.id.stop)
        val label = findViewById<TextView>(R.id.label)

        // snowflake proxy config
        val capacity : Long = 0
        // all String args are null to use defaults included in IPtProxy
        val broker = null
        val relay = null
        val stun = null
        val logFile: String? = null
        val keepLocalAddresses = false
        val unsafeLogging = true

        start.setOnClickListener {
            IPtProxy.startSnowflakeProxy(capacity, broker, relay, stun, logFile, keepLocalAddresses, unsafeLogging) {
                runOnUiThread {
                    count++
                    Toast.makeText(this, "Client connected", Toast.LENGTH_LONG).show()
                    label.text = "Connected Clients: $count"
                }
            }
        }

        stop.setOnClickListener {
            IPtProxy.stopSnowflakeProxy()
        }
    }
}