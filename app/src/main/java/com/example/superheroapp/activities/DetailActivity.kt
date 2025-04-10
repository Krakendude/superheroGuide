package com.example.superheroapp.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.superheroapp.R
import com.example.superheroapp.data.Superhero
import com.example.superheroleague.utils.superheroService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity() {

    companion object {
        const val SUPERHERO_ID = "SUPERHERO_ID"
    }

    lateinit var nameTextView: TextView
    lateinit var avatarImageView: ImageView

    lateinit var superhero: Superhero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra(SUPERHERO_ID)!!

        avatarImageView = findViewById(R.id.avatarImageView)
        nameTextView = findViewById(R.id.nameTextView)

        getSuperheroById(id)
    }

    fun getSuperheroById(id: String) {
        // Llamada en un hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = superheroService.getInstance()
                superhero = service.findSuperheroById(id)

                // Volvemos al hilo principal
                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadData() {
        nameTextView.text = superhero.name
        Picasso.get().load(superhero.image.url).into(avatarImageView)
    }
}