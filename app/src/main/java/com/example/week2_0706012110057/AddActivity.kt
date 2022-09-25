package com.example.week2_0706012110057

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import com.example.week2_0706012110057.Database.Globalvar
import com.example.week2_0706012110057.Model.Chicken
import com.example.week2_0706012110057.Model.Animal
import com.example.week2_0706012110057.Model.Goat
import com.example.week2_0706012110057.Model.Cow
import com.example.week2_0706012110057.databinding.ActivityAddBinding
class AddActivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityAddBinding
    private lateinit var animal: Animal
    var i = -1 // for index
    var img: String = ""

    private val GetResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val uri = it.data?.data
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (uri != null) {
                        baseContext.getContentResolver().takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                    }
                }
                viewBind.imageView2.setImageURI(uri)
                img = uri.toString()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityAddBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        getintent()
        listener()
    }

    private fun getintent() {

        i = intent.getIntExtra("position", -1)
        if (i != -1) {
            val animall = Globalvar.listAnimaldata[i]
            viewBind.toolbar2.title = "Edit Animal Data"
            viewBind.Save.text = "Edit"
            viewBind.imageView2.setImageURI(Uri.parse(Globalvar.listAnimaldata[i].imageUri))
            viewBind.Age.editText?.setText(animall.age.toString())
            viewBind.Name.editText?.setText(animall.name)
            if(animall.type == "Chicken"){
                viewBind.radioGroup.check(-1);
            }else if(animall.type == "Cow") {
                viewBind.radioGroup.check(-1);
            }else if(animall.type == "Goat"){
                viewBind.radioGroup.check(-1);
            }

        }
    }


    private fun listener() {
        viewBind.Save.setOnClickListener {
            if (viewBind.radioGroup.checkedRadioButtonId == -1) {
                var name = viewBind.Name.editText?.text.toString().trim()
                var age = 0
                var type = ""
                animal = Chicken(name, type, age)
                checker()
            } else {
                var name = viewBind.Name.editText?.text.toString().trim()
                var age = 0
                var type =
                    findViewById<RadioButton>(viewBind.radioGroup.checkedRadioButtonId).text.toString()
                if (type == "Chicken") {
                    animal = Chicken(name, type, age)
                    checker()
                } else if (type == "Cow") {
                    animal = Cow(name, type, age)
                    checker()
                } else {
                    animal = Goat(name, type, age)
                    checker()
                }

            }
        }

        viewBind.imageView2.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        viewBind.toolbar2.getChildAt(1).setOnClickListener {
            finish()
        }


    }

    private fun checker(): Boolean {
        var isCompleted: Boolean = true
        animal.imageUri = img.toString()

        if (animal.name!!.isEmpty()) {
            viewBind.Name.error = "Please Fill Animal Name"
            isCompleted = false
        } else {
            viewBind.Name.error = ""
        }
        if(animal.type!!.isEmpty()){
            viewBind.radioButton.error = "?"
            viewBind.radioButton2.error = "?"
            viewBind.radioButton3.error = "?"
            isCompleted = false
        }


        if (viewBind.Age.editText?.text.toString().isEmpty() || viewBind.Age.editText?.text.toString().toInt() < 0
        ) {
            viewBind.Age.error = "Animal Age Can't Be 0"
            isCompleted = false
        }else{
            viewBind.Age.error = ""
        }

        if (isCompleted == true) {
            if (i == -1) {
                animal.age = viewBind.Age.editText?.text.toString().toInt()
                Globalvar.listAnimaldata.add(animal)

            } else {
                animal.age = viewBind.Age.editText?.text.toString().toInt()
                Globalvar.listAnimaldata[i] = animal
            }
            finish()
        }
        return isCompleted
    }
}