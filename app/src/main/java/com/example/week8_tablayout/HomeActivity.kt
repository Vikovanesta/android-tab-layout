package com.example.week8_tablayout

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        title = "??????"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this,R.color.Primary)))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}