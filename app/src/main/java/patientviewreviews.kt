package com.example.wellness_app

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PatientReviewsActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.patientviewreview)

        dbHelper = DatabaseHelper(this)

        // Fetch reviews with details
        lifecycleScope.launch(Dispatchers.Main) {
            val reviews = dbHelper.getAllReviewsWithDetails()

            // Populate table layout
            populateTable(reviews)
        }
    }

    private fun populateTable(reviews: List<ReviewWithDetails>) {
        val tableLayout = findViewById<TableLayout>(R.id.tableLayoutReviews)

        for (review in reviews) {
            val row = TableRow(this)

            val usernameTextView = TextView(this)
            usernameTextView.text = review.patientUsername ?: "-"
            usernameTextView.setTextColor(resources.getColor(android.R.color.white))
            usernameTextView.textSize = 16f
            usernameTextView.setPadding(8, 8, 8, 8)
            usernameTextView.gravity = android.view.Gravity.CENTER
            row.addView(usernameTextView)

            val therapistNameTextView = TextView(this)
            therapistNameTextView.text = review.therapistName ?: "-"
            therapistNameTextView.setTextColor(resources.getColor(android.R.color.white))
            therapistNameTextView.textSize = 16f
            therapistNameTextView.setPadding(8, 8, 8, 8)
            therapistNameTextView.gravity = android.view.Gravity.CENTER
            row.addView(therapistNameTextView)

            val reviewTextView = TextView(this)
            reviewTextView.text = review.reviewText
            reviewTextView.setTextColor(resources.getColor(android.R.color.white))
            reviewTextView.textSize = 16f
            reviewTextView.setPadding(8, 8, 8, 8)
            reviewTextView.gravity = android.view.Gravity.CENTER
            row.addView(reviewTextView)

            tableLayout.addView(row)
        }
    }
}
