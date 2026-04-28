package com.example.testsnowflakeproxy

import IPtProxy.Controller
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File

import IPtProxy.SnowflakeProxy
import android.annotation.SuppressLint
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.testsnowflakeproxy5.R

class MainActivity : AppCompatActivity() {

    private var count: Int = 0

    private lateinit var snowflakeProxy: SnowflakeProxy

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "App B"

        val stateLocation = File(cacheDir, "pt")
        if (!stateLocation.exists()) stateLocation.mkdir()

        // save logs to the state dir
        val enableLogging = true

        // if true, disable the address scrubber
        val unsafeLogging = false

        val logLevel = "INFO"

        // Set `ptDir` first, before accessing this the first time!
        val controller: Controller by lazy {
            Controller(
                cacheDir.path,
                enableLogging,
                unsafeLogging,
                logLevel,
                object : IPtProxy.OnTransportEvents {
                    override fun connected(name: String?) {}
                    override fun error(name: String?, error: java.lang.Exception?) {}
                    override fun stopped(name: String?, error: java.lang.Exception?) {}
                })
        }
        val label = findViewById<TextView>(R.id.label)

        snowflakeProxy = SnowflakeProxy().apply {
            capacity = 0L
            brokerUrl = "https://snowflake-broker.torproject.net/"
            relayUrl = "wss://snowflake.bamsoftware.com"
            stunServer = "stun:stun.epygi.com:3478"
            natProbeUrl = "https://snowflake-broker.torproject.net:8443/probe"
            pollInterval = 120
            clientEvents = object : IPtProxy.SnowflakeClientEvents {
                override fun connected() {
                    runOnUiThread {
                        count++
                        Toast.makeText(this@MainActivity, "Client connected", Toast.LENGTH_LONG)
                            .show()
                        label.text = "Connected Clients:$count"
                    }
                }

                override fun connectionFailed() {}
                override fun disconnected(country: String?) {}
                override fun natTypeUpdated(natType: String?) {}
                override fun stats(
                    connectionCount: Long,
                    failedConnectionCount: Long,
                    inboundBytes: Long,
                    outboundBytes: Long,
                    inboundUnit: String?,
                    outboundUnit: String?,
                    summaryInterval: Long
                ) {
                }
            }
        }


        // android activity config
        val start = findViewById<Button>(R.id.start)
        val stop = findViewById<Button>(R.id.stop)

        start.setOnClickListener {
            snowflakeProxy.start()
        }

        stop.setOnClickListener {
            runOnUiThread {
                snowflakeProxy.stop()
            }
        }

//        val startObfs4 = findViewById<Button>(R.id.startObfs)
//        val stopObfs4 = findViewById<Button>(R.id.stopObfs)

    }
}