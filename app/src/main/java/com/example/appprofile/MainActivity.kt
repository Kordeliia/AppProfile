package com.example.appprofile

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.appprofile.databinding.ActivityMainBinding
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var lat : Double = 0.0
    private var long: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateUI()
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
            val intent = Intent(Intent.ACTION_DIAL).apply {
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

    private fun updateUI(name: String = "Laura Alvarez Muñoz",
                         email: String = "lalvmun@gmail.com",
                         website : String ="https://www.linkedin.com/in/laura-%C3%A1lvarez-mu%C3%B1oz-165749209/",
                         phone : String = "+34622321232",
                         lat: Double =37.0,
                         long: Double = -122.0) {
        binding.tvName.text = name
        binding.tvEmail.text = email
        binding.tvWebsite.text = website
        binding.tvPhone.text = phone
      //  lat = 37.7448
      //  long = -1.1951
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
            editResult.launch(intent)
          //  startActivityForResult(intent, RC_EDIT)
        }
        return super.onOptionsItemSelected(item)
    }
    private val editResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK) {
                val name = it.data?.getStringExtra(getString(R.string.key_nombre))
                val email = it.data?.getStringExtra(getString(R.string.key_email))
                val website = it.data?.getStringExtra(getString(R.string.key_website))
                val phone = it.data?.getStringExtra(getString(R.string.key_phone))
                lat = it.data?.getDoubleExtra(getString(R.string.key_latitud), 0.0) ?: 0.0
                long = it.data?.getDoubleExtra(getString(R.string.key_longitud), 0.0) ?: 0.0
                updateUI(name!!, email!!, website!!, phone!!, lat!!, long!!)
        }
    }
}