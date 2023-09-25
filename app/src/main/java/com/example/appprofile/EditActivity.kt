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
        with(binding){
            intent.extras?.let{
                etName.setText(it.getString(getString(R.string.key_nombre)))
                etEmail.setText(it.getString(getString(R.string.key_email)))
                etWebsite.setText(it.getString(getString(R.string.key_website)))
                etPhone.setText(it.getString(getString(R.string.key_phone)))
                etLat.setText(it.getDouble(getString(R.string.key_latitud)).toString())
                etLong.setText(it.getDouble(getString(R.string.key_longitud)).toString())
            }
            etEmail.setOnFocusChangeListener{view, isFocused ->
                if(isFocused){ etEmail.text?.let{ etEmail.setSelection(it.length) } }
            }
            etWebsite.setOnFocusChangeListener { view, isFocused ->
                if (isFocused) {
                    etWebsite.text?.let { etWebsite.setSelection(it.length) } }
            }
            etPhone.setOnFocusChangeListener { view, isFocused ->
                if (isFocused) {
                    etPhone.text?.let { etPhone.setSelection(it.length) } }
            }
            etLat.setOnFocusChangeListener{view, isFocused ->
                if(isFocused){ etLat.text?.let{ etLat.setSelection(it.length) } }
            }
            etLong.setOnFocusChangeListener{view, isFocused ->
                if(isFocused){ etLong.text?.let{ etLong.setSelection(it.length) } }
            }
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