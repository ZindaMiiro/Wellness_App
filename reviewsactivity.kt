package com.example.wellness_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat // Make sure this import is present

class ReviewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reviewsactivity)

        // Set tooltip and click listener for "Add Review" button
        findViewById<Button>(R.id.button_addReview).apply {
            ViewCompat.setTooltipText(this, "Navigate to add review page")
            setOnClickListener {
                val intent = Intent(this@ReviewsActivity, PatientInReview::class.java)
                startActivity(intent)
            }
        }

        // Set tooltip and click listener for "Check Reviews" button
        findViewById<Button>(R.id.button_checkReviews).apply {
            ViewCompat.setTooltipText(this, "Navigate to check reviews page")
            setOnClickListener {
                val intent = Intent(this@ReviewsActivity, PatientReviewsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
