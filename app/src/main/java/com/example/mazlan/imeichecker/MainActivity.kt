package com.example.mazlan.imeichecker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.telephony.TelephonyManager
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val READ_PERMISSION_CODE = 1
    lateinit var btnDetail : Button
    lateinit var txtDetail : TextView
    lateinit var information : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        btnDetail = findViewById(R.id.btn_detail)
        txtDetail = findViewById(R.id.text_imei)

        btnDetail.setOnClickListener {

            checkPermission()
        }


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }


    private fun checkPermission() {

        val checkPermission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE)

        if(checkPermission == PackageManager.PERMISSION_GRANTED)
        {
            telephoneManagerDetails()
        }
        else
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_PHONE_STATE), READ_PERMISSION_CODE )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            READ_PERMISSION_CODE -> {

                if(grantResults.size >=0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    telephoneManagerDetails()
                }
                else
                {
                    Toast.makeText(applicationContext,"You don't have permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun telephoneManagerDetails() {

        val telephonyManager  = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager


        val IMEINumber = telephonyManager.deviceId

        information = "$IMEINumber\n"
        txtDetail.text = information

    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.nav_about -> {
                val ic = Intent(this@MainActivity, About::class.java)
                startActivity(ic)
            }

            R.id.nav_share -> {
                val sharingIntent = Intent(android.content.Intent.ACTION_SEND)

                sharingIntent.type = "text/plain/link"
                val shareBody = "'This app is a IMEI Checker. You can see your IMEI number in your phone with this app.'"
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here")
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(sharingIntent, "Share via"))
                return true
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
