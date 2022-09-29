package com.bignerdranch.android.pract11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.edit
import androidx.room.Room
import com.bignerdranch.android.pract11.data.DATABASE_NAME
import com.bignerdranch.android.pract11.data.KnigaDB
import com.bignerdranch.android.pract11.data.models.KnigaTypes
import com.bignerdranch.android.pract11.data.models.KnigaZhanr
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import java.util.concurrent.Executors

class zapomnit : AppCompatActivity() {

    private val Books: MutableList<Book> = mutableListOf()
    private lateinit var b1: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zapomnit)
        val index = intent.getIntExtra("number", -1)
        val toast = Toast.makeText(applicationContext, "Запомнили)", Toast.LENGTH_SHORT)
        b1 = findViewById(R.id.buttons)
        var editText = findViewById<EditText>(R.id.editTextTextPersonName)
        var editText1 = findViewById<EditText>(R.id.editTextTextPersonName3)
        var editText2 = findViewById<EditText>(R.id.editTextNumber)
        var editText3 = findViewById<EditText>(R.id.editTextTextPersonName2)
        val bb = findViewById<ImageButton>(R.id.imageButton)
        val preferences = getSharedPreferences("pref", MODE_PRIVATE)


        var db: KnigaDB = Room.databaseBuilder(this, KnigaDB::class.java, DATABASE_NAME).build()

        val knigaDAO = db.knigaDAO()

        val executor = Executors.newSingleThreadExecutor()

        if (preferences.contains("json")) {
            val listBooks: List<Book> = Gson().fromJson<MutableList<Book>>(preferences.getString("json", "qwe").toString(), object : TypeToken<MutableList<Book>>() {}.type)
            Books.addAll(listBooks)
        }
        if(index > -1) {
            b1.setText("Изменить")
            editText.setText(Books[index].name)
            editText1.setText(Books[index].author)
            editText2.setText(Books[index].pages)
        }

        /*if(index == -2){
            b1.setText("удалить")
        }*/

        b1.setOnClickListener{
            /*if(index ==  -2){
                executor.execute{
                    knigaDAO.killBook(KnigaTypes(0, "${editText.text}", "${editText1.text}", "${editText2.text}"))
                }
            }*/
            if (index == -1) {
                /*val book: Book = Book(editText.text.toString(), editText1.text.toString(), editText2.text.toString())
                Books.add(book)
                preferences.edit {
                    this.putString("json", Gson().toJson(Books))
                }*/
                executor.execute{
                    knigaDAO.addBook(KnigaTypes(0, "${editText.text}", "${editText1.text}", "${editText2.text}"))
                    knigaDAO.addZhanr(KnigaZhanr(0, 0, "${editText3.text}", Date()))
                }

                val types = knigaDAO.getAllBooks()

                types.observe(this, androidx.lifecycle.Observer {
                    it.forEach {
                        Log.d("Kniga", it.toString())
                    }
                })

            }
            else if (index > -1){
                executor.execute{
                    knigaDAO.saveBook(KnigaTypes(0, "${editText.text}", "${editText1.text}", "${editText2.text}"))
                }

                val types = knigaDAO.getAllBooks()

                types.observe(this, androidx.lifecycle.Observer {
                    it.forEach {
                        Log.d("Kniga", it.toString())
                    }
                })
                /*Books[index].name = editText.text.toString()
                Books[index].author = editText1.text.toString()
                Books[index].pages = editText2.text.toString()*/
                /*val preferences = getSharedPreferences("pref", MODE_PRIVATE)
                preferences.edit{
                    this.putString("json", Gson().toJson(Books).toString())
                }*/
            }
            toast.show()
        }

        bb.setOnClickListener{
            super.onBackPressed()
        }
    }
}
/*

 */
