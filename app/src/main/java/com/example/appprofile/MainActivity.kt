package com.example.appprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.appprofile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var lat : Double = 0.0
    private var long: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateUI()
        binding.tvLocation.setOnClickListener {
            binding.tvLocation.text = "Lat: $lat Long: $long"

        }

    }

    private fun updateUI(name: String = "Laura Alvarez Muñoz",
                         email: String = "lalvmun@gmail.com",
                         website : String ="miwebsite.nunchakita.com",
                         phone : String = "+34622321232") {
        binding.tvName.text = name
        binding.tvEmail.text = email
        binding.tvWebsite.text = website
        binding.tvPhone.text = phone
        lat = 37.7448
        long = -1.1951
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_edit)
        {
            val  intent : Intent = Intent(this, EditActivity::class.java)
            intent.putExtra("k_name", binding.tvName.text)
            intent.putExtra("k_email", binding.tvEmail.text)
            intent.putExtra("k_website", binding.tvWebsite.text)
            intent.putExtra("k_phone", binding.tvPhone.text)
            intent.putExtra("k_lat", lat)
            intent.putExtra("k_long", long)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}