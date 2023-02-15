package com.blogspot.devofandroid.sozluk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener {

    private lateinit var kelimelerListe:ArrayList<Kelimeler>
    private lateinit var adapter:KelimelerAdapter
    private lateinit var refKelimeler:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "İngilizce Sözlük Uygulaması"
        setSupportActionBar(toolbar)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)

         val db = FirebaseDatabase.getInstance()
        refKelimeler = db.getReference("kelimeler")


        kelimelerListe = ArrayList()
        adapter = KelimelerAdapter(this,kelimelerListe)

        rv.adapter = adapter
        tumkelimeler()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)

        val item = menu?.findItem(R.id.action_ara)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        aramayap(query!!)
        if (query != null) {
            Log.e("Gönderilen arama",query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        aramayap(newText!!)
        if (newText != null) {
            Log.e("Harf girdikçe",newText)
        }
        return true
    }

    fun tumkelimeler(){
        refKelimeler.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(ds: DataSnapshot) {
                kelimelerListe.clear()
                for(c in ds.children){
                    val kelime = c.getValue(Kelimeler::class.java)
                    if(kelime !=null){
                        kelime.kelime_id = c.key
                        kelimelerListe.add(kelime)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun aramayap(aranankelime:String){
        refKelimeler.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(ds: DataSnapshot) {
                kelimelerListe.clear()
                for(c in ds.children){
                    val kelime = c.getValue(Kelimeler::class.java)
                    if(kelime !=null){

                        if(kelime.ingilizce!!.contains(aranankelime)){
                            kelime.kelime_id = c.key
                            kelimelerListe.add(kelime)
                        }

                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
