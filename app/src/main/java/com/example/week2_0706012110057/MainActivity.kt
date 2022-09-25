package com.example.week2_0706012110057

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week2_0706012110057.Adapter.ListdataRVadapter
import com.example.week2_0706012110057.Database.Globalvar
import com.example.week2_0706012110057.Interface.cardListener
import com.google.android.material.snackbar.Snackbar
import com.example.week2_0706012110057.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), cardListener {
    private lateinit var viewBind: ActivityMainBinding
    private var tot: Int = 0
    private val adapter = ListdataRVadapter(Globalvar.listAnimaldata, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        permission()
        setupRecyclerView()
        listener()
    }

    override fun onResume() {
        super.onResume()
        tot = Globalvar.listAnimaldata.size
        if (tot == 0) {
            viewBind.textView2.alpha = 1f
        } else {
            viewBind.textView2.alpha = 0f
        }
        adapter.notifyDataSetChanged()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Globalvar.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun permission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Globalvar.STORAGE_PERMISSION_CODE
            )
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun listener() {
        viewBind.button.setOnClickListener {
            val myIntent = Intent(this, AddActivity::class.java)
            startActivity(myIntent)
        }


    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        viewBind.listdata.layoutManager = layoutManager   // Set layout
        viewBind.listdata.adapter = adapter   // Set adapter
    }


    override fun onEditClick(position: Int) {
        val myintent = Intent(this, AddActivity::class.java).apply {
            putExtra("position", position)
        }
        startActivity(myintent)
    }
    override fun onDeleteClick(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Animal List")
        builder.setMessage("Are you sure you want to delete this list?")


        builder.setPositiveButton(android.R.string.yes) { function, which ->
            val snackbar =
                Snackbar.make(viewBind.listdata, "List Deleted", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Dismiss") { snackbar.dismiss() }
            snackbar.setActionTextColor(Color.BLUE)
            snackbar.setBackgroundTint(Color.BLACK)
            snackbar.show()

            Globalvar.listAnimaldata.removeAt(position)
            finish();
            startActivity(getIntent());
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext, android.R.string.no, Toast.LENGTH_SHORT)
                .show()
        }
        builder.show()

        //remove

    }

}