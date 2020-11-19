package edu.upc.fib.pes_infovid19.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import edu.upc.fib.pes_infovid19.R
import kotlinx.android.synthetic.main.activity_create_myth.*

class CreateMythActivity : AppCompatActivity() {
    private val viewModel: MythsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_myth)
        setSupportActionBar(toolbarCreateMyth)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        createNewMythButton.setOnClickListener {
            val myth = constructNewMyth()
            viewModel.addMyth(myth)
            onSupportNavigateUp()
        }
    }

    fun constructNewMyth(): Myth {
        val myth = Myth()
        myth.title = titleTextMyth.text.toString()
        myth.text = textMyth.text.toString()
        myth.date = dateMyth.text.toString()
        myth.source = sourceMyth.text.toString()
        return myth
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}