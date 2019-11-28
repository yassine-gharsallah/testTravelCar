package fr.travelcar.test.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import fr.travelcar.test.R
import fr.travelcar.test.data.model.response.Car
import kotlinx.android.synthetic.main.activity_car_details.*

class CarDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (intent.hasExtra(SELECTED_CAR)) {
            val currentCar = intent.getSerializableExtra(SELECTED_CAR) as Car
            carTitle.text = "${currentCar.make + " "+ currentCar.model}"
            Glide.with(this)
                .load(currentCar.picture)
                .into(imageDetails)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val SELECTED_CAR = "SELECTED_CAR"
        fun setData(aCar: Car): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(SELECTED_CAR, aCar)
            return bundle
        }
    }
}
