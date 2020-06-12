package com.example.covid19

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19.databinding.ItemFeedsBinding
import com.github.nitrico.lastadapter.LastAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_feeds.*
import kotlinx.android.synthetic.main.activity_positive_cases.noData
import java.util.*


class FeedsActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    var ref = database.getReference("Feeds")
    lateinit var adapter: LastAdapter
    val postList = ArrayList<AddPostModel>()
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeds)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Feeds"
        rvFeeds.layoutManager = LinearLayoutManager(this)

        init()
        setAdapter()
    }

    private fun init() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Loading")
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                postList.clear()
                println(dataSnapshot.childrenCount)
                for (snapshot in dataSnapshot.children) {
                    val post = snapshot.getValue(AddPostModel::class.java)
                    println(post)
                    postList.add(post!!)
                }
                progressDialog!!.dismiss()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }

    private fun setAdapter() {
        adapter = LastAdapter(postList, BR.post).map<AddPostModel, ItemFeedsBinding>(R.layout.item_feeds) {
            onBind {
                if (postList.isEmpty()) {
                    noData.visibility = View.VISIBLE
                    rvFeeds.visibility = View.GONE
                } else {
                    noData.visibility = View.GONE
                    rvFeeds.visibility = View.VISIBLE
                }
            }
        }.into(rvFeeds)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}