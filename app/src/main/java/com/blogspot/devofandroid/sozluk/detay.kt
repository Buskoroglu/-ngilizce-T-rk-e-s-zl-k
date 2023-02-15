package com.blogspot.devofandroid.sozluk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detay.*

class detay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detay)
        val kelime = intent.getSerializableExtra("nesne") as Kelimeler
        textViewingilizce.text = kelime.ingilizce
        textViewTurkce.text = kelime.turkce
    }
}