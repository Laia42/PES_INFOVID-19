package edu.upc.fib.pes_infovid19.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import edu.upc.fib.pes_infovid19.MainActivity
import edu.upc.fib.pes_infovid19.R
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.tarjeta_xat.view.*

class ChatListActivity : AppCompatActivity() {
    lateinit var rv: RecyclerView
    lateinit var adapter: ListaXatAdapter
    lateinit var nom: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)
        toolbar_activity_chat.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        rv = findViewById<RecyclerView>(R.id.rvXats)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val llm = LinearLayoutManager(this)
        adapter = ListaXatAdapter(this)
        rv.layoutManager = llm
        rv.adapter = adapter

        val user = FirebaseAuth.getInstance().currentUser
        var mDatabase = FirebaseDatabase.getInstance().reference.child("User").orderByChild("email").equalTo(user?.email)
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val nom = snapshot.child("username").getValue(String::class.java)!!
                    fillUser(nom)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }

        })

        mDatabase = FirebaseDatabase.getInstance().reference.child("User").orderByChild("type").equalTo("Voluntari")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val name = snapshot.child("username").getValue(String::class.java)!!
                    actualitza(name)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })

    }

    fun fillUser(user: String) {
        this.nom = user
    }

    fun actualitza(name: String) {
        if (nom != name) adapter.addPerson(name)

    }

    fun anarXat(v: View) {
        val xat = v.button.text.toString()
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("xat", xat)
        intent.putExtra("nombre", nom)
        startActivity(intent)
    }


}