package com.example.covid19

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid19.databinding.ItemFeedbackBinding
import com.github.nitrico.lastadapter.LastAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.activity_user.noData
import java.util.*

class FeedbackActivity : AppCompatActivity() {
    private val mDatabase = FirebaseDatabase.getInstance()
    private val mGetReference = mDatabase.getReference("Feedback")
    val feedbackList = ArrayList<FeedBackModel>()
    private lateinit var adapter: LastAdapter
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Feedback List"
        rvFeedback.layoutManager = LinearLayoutManager(this)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Loading")
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()

        adapter = LastAdapter(feedbackList, BR.feedback).map<FeedBackModel, ItemFeedbackBinding>(R.layout.item_feedback) {
            onBind {
                if (feedbackList.isEmpty()) {
                    noData.visibility = View.VISIBLE
                    rvFeedback.visibility = View.GONE
                } else {
                    noData.visibility = View.GONE
                    rvFeedback.visibility = View.VISIBLE
                }
            }
        }.into(rvFeedback)

        mGetReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                feedbackList.clear()
                for (snapshot in dataSnapshot.children) {
                    Log.d("Response", snapshot.getValue(UserModel::class.java).toString())
                    val feedback = snapshot.getValue(FeedBackModel::class.java)
                    feedbackList.add(feedback!!)
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