package com.example.covid19

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19.databinding.ItemPositiveCasesBinding
import com.github.nitrico.lastadapter.LastAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_positive_cases.*
import java.util.*

class PositiveCasesActivity : AppCompatActivity() {
    private val mDatabase = FirebaseDatabase.getInstance()
    private val mGetReference = mDatabase.getReference("User")
    val userList = ArrayList<UserModel>()
    private lateinit var adapter: LastAdapter
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_positive_cases)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Positive User List"
        rvPositiveCases.layoutManager = LinearLayoutManager(this)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Loading")
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()

        adapter = LastAdapter(userList, BR.user).map<UserModel, ItemPositiveCasesBinding>(R.layout.item_positive_cases) {
            onBind {
                if (userList.isEmpty()) {
                    noData.visibility = View.VISIBLE
                    rvPositiveCases.visibility = View.GONE
                } else {
                    noData.visibility = View.GONE
                    rvPositiveCases.visibility = View.VISIBLE
                }
            }
        }.into(rvPositiveCases)

        mGetReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()
                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(UserModel::class.java)
                    if (user!!.coronaStatus.equals("Positive", true)) {
                        userList.add(user)
                    }
                    adapter.notifyDataSetChanged()
                    progressDialog!!.dismiss()
                }
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