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
        intent.extras?.let{
            binding.etName.setText(it.getString("k_nombre"))
            binding.etEmail.setText(it.getString("k_email"))
            binding.etWebsite.setText(it.getString("k_website"))
            binding.etPhone.setText(it.getString("k_phone"))
            binding.etLat.setText(it.getDouble("k_latitud").toString())
            binding.etLong.setText(it.getDouble("k_longitud").toString())
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> sendData()
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    fun sendData(){
        val intent = Intent()
        with(binding){
            intent.apply{
                putExtra("k_nombre", etName.text.toString())
                putExtra("k_email", etEmail.text.toString())
                putExtra("k_website", etWebsite.text.toString())
                putExtra("k_phone", etPhone.text.toString())
                putExtra("k_latitud", etLat.text.toString().toDouble())
                putExtra("k_longitud", etLong.text.toString().toDouble())
            }

        }

        setResult(RESULT_OK, intent)
        finish()
    }
}