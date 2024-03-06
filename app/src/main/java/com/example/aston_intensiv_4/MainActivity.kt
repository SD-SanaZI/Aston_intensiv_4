package com.example.aston_intensiv_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_tag, MenuFragment.newInstance())
            .addToBackStack("menu")
            .commit()
    }
}