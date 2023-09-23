package com.example.appprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedDispatcher
import com.example.appprofile.databinding.ActivityEditBinding
import com.example.appprofile.databinding.ActivityMainBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding :ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.etName.setText(intent.extras?.getString("k_name"))
        binding.etEmail.setText(intent.extras?.getString("k_email"))
        binding.etWebsite.setText(intent.extras?.getString("k_website"))
        binding.etPhone.setText(intent.extras?.getString("k_phone"))
        binding.etLat.setText(intent.extras?.getDouble("k_lat").toString())
        binding.etLong.setText(intent.extras?.getDouble("k_long").toString())
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_save)
        {
            finish()
        } else if (item.itemId == android.R.id.home){
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}