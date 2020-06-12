package com.example.covid19

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19.databinding.ItemUserBinding
import com.github.nitrico.lastadapter.LastAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_user.*
import java.util.*

class UserActivity : AppCompatActivity() {

    private val mDatabase = FirebaseDatabase.getInstance()
    private val mGetReference = mDatabase.getReference("User")
    val userList = ArrayList<UserModel>()
    private lateinit var adapter: LastAdapter
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "User List"
        rvUser.layoutManager = LinearLayoutManager(this)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Loading")
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()

        adapter = LastAdapter(userList, BR.user).map<UserModel, ItemUserBinding>(R.layout.item_user) {
            onBind {
                if (userList.isEmpty()) {
                    noData.visibility = View.VISIBLE
                    rvUser.visibility = View.GONE
                } else {
                    noData.visibility = View.GONE
                    rvUser.visibility = View.VISIBLE
                }
            }
        }.into(rvUser)

        mGetReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()
                for (snapshot in dataSnapshot.children) {
                    Log.d("Response", snapshot.getValue(UserModel::class.java).toString())
                    val user = snapshot.getValue(UserModel::class.java)
//                    println(user)
                    userList.add(user!!)
                }
                adapter.notifyDataSetChanged()
                progressDialog!!.dismiss()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("PositiveCase", databaseError.message)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}