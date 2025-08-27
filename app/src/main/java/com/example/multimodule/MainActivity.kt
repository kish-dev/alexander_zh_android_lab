package com.example.multimodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.navigation_impl.NavigationActivity
import com.example.navigation_impl.NavigationFragment

internal class MainActivity : AppCompatActivity(), NavigationActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getNavigationFragment(): NavigationFragment? = supportFragmentManager.fragments
        .filterIsInstance<NavigationFragment>()
        .firstOrNull()
}