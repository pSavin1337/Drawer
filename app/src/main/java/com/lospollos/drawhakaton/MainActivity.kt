package com.lospollos.drawhakaton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var drawCustomView: DrawCustomView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawCustomView = findViewById(R.id.draw_custom_view)
        drawCustomView.color = DrawCustomView.redColor
    }

}