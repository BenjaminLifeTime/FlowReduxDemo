package com.example.flowreduxkmm.android

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flowreduxkmm.FlowReduxSession
import com.example.flowreduxkmm.FlowReduxSessionState
import com.example.flowreduxkmm.Greeting
import kotlinx.coroutines.flow.collectLatest

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    private val session = FlowReduxSession()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()
        val resumeButton: Button = findViewById(R.id.resume_button)
        val suspendButton: Button = findViewById(R.id.suspend_button)
        val stateTextView: TextView = findViewById(R.id.state_text_view)

        resumeButton.setOnClickListener {
            session.resume()
        }
        suspendButton.setOnClickListener {
            session.suspend()
        }
        lifecycleScope.launchWhenStarted {
            session.sessionStateFlow.collectLatest {
                when (it) {
                    FlowReduxSessionState.Running -> stateTextView.text = "Running"
                    FlowReduxSessionState.Suspended -> stateTextView.text = "Suspended"
                }
            }
        }
    }
}
