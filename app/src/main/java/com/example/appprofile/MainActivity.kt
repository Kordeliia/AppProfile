package com.example.appprofile

import android.app.SearchManager
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.appprofile.databinding.ActivityMainBinding
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.edit
import androidx.preference.PreferenceManager


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var imgUri: Uri
    private var lat : Double = 0.0
    private var long: Double = 0.0
    private val editResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK) {
            imgUri = Uri.parse(it.data?.getStringExtra("k_image"))
            val name = it.data?.getStringExtra("k_nombre")
            val email = it.data?.getStringExtra("k_email")
            val website = it.data?.getStringExtra("k_website")
            val phone = it.data?.getStringExtra("k_phone")
            lat = it.data?.getDoubleExtra("k_latitud", 0.0) ?: 0.0
            long = it.data?.getDoubleExtra("k_longitud", 0.0) ?: 0.0
            saveUserData(name, email, website, phone)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        getUserData()
        setupIntents()

        binding.tvEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvEmail.text.toString()))
                putExtra(Intent.EXTRA_SUBJECT, "Prueba Android Nuncha App")
                putExtra(Intent.EXTRA_TEXT, "Desde la app Profile de Nunchakita.")
            }
            launchIntent(intent)
        }
        binding.tvWebsite.setOnClickListener {
            val webPage = Uri.parse(binding.tvWebsite.text.toString())
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            launchIntent(intent)
        }
        binding.tvPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL).apply { // Tarea: Llamar
                val phone = (it as TextView).text
                data = Uri.parse("tel:$phone")
            }
            launchIntent(intent)
        }
        binding.tvLocation.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("geo:0,0?q=$lat, $long(App Profile Nuncha)")
                `package` = "com.google.android.apps.maps"
            }
            launchIntent(intent)
        }
        binding.tvSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            launchIntent(intent)
        }

    }

    private fun saveUserData(name: String?, email: String?, website: String?, phone: String?) {
        sharedPreferences.edit {
            putString(getString(R.string.key_image), imgUri.toString())
            putString(getString(R.string.key_nombre), name)
            putString(getString(R.string.key_email), email)
            putString(getString(R.string.key_website), website)
            putString(getString(R.string.key_phone), phone)
            putString(getString(R.string.key_latitud), lat.toString())
            putString(getString(R.string.key_longitud), long.toString())
            apply()
        }
        updateUI(name, email, website, phone)
    }

    private fun launchIntent(intent: Intent){
        if(intent.resolveActivity(packageManager) != null){
            startActivity(intent)
        }else{
            Toast.makeText(this, getString(R.string.ToastMsgNoCompatibilidad), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupIntents() {
        binding.tvName.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH).apply{
                putExtra(SearchManager.QUERY, binding.tvName.text)
            }
            startActivity(intent)
        }
    }
    private fun getUserData() {
        imgUri = Uri.parse(sharedPreferences.getString("k_image", ""))
        val name = sharedPreferences.getString("k_nombre", null)
        val email = sharedPreferences.getString("k_email", null)
        val website = sharedPreferences.getString("k_website", null)
        val phone = sharedPreferences.getString("k_phone", null)
        lat = sharedPreferences.getString("k_latitud", "0.0")!!.toDouble()
        long = sharedPreferences.getString("k_longitud", "0.0")!!.toDouble()
        updateUI(name, email, website, phone)
    }
    private fun updateUI(name: String?, email: String?, website : String?, phone : String?) {
        with(binding){
            binding.imgProfile.setImageURI(imgUri)
            binding.tvName.text = name ?: "Laura Alvarez Muñoz"
            binding.tvEmail.text = email ?: "lalvmun@gmail.com"
            binding.tvWebsite.text = website ?: "https://www.linkedin.com/in/laura-%C3%A1lvarez-mu%C3%B1oz-165749209/"
            binding.tvPhone.text = phone ?: "+34632323232"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit) {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(getString(R.string.key_nombre), binding.tvName.text)
            intent.putExtra(getString(R.string.key_email), binding.tvEmail.text.toString())
            intent.putExtra(getString(R.string.key_website), binding.tvWebsite.text.toString())
            intent.putExtra(getString(R.string.key_phone), binding.tvPhone.text)
            intent.putExtra(getString(R.string.key_latitud), lat)
            intent.putExtra(getString(R.string.key_longitud), long)
            intent.putExtra(getString(R.string.key_image), imgUri.toString())
            editResult.launch(intent)
          //  startActivityForResult(intent, RC_EDIT)
        }
        return super.onOptionsItemSelected(item)
    }

}