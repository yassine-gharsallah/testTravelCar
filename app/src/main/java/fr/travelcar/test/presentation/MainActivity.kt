package fr.travelcar.test.presentation

import android.content.Intent
import android.os.Bundle
import android.view.showOrGone
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.nonNullObserve
import fr.travelcar.test.R
import fr.travelcar.test.presentation.adapter.CarAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

     val carViewModel: CarViewModel by viewModel()

    private val carsAdapter = CarAdapter {
        val intent = Intent(this, CarDetailsActivity::class.java)
        intent.putExtras(CarDetailsActivity.setData(it))
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        carViewModel.getCars()

        carViewModel.loading.nonNullObserve(this) { displayProgressBar ->
            cars_loader.showOrGone(displayProgressBar)
        }

        carViewModel.error.nonNullObserve(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }


        carViewModel.carsResponse.nonNullObserve(this) {
            carsAdapter.setData(it)
            carsAdapter.notifyDataSetChanged()
        }

        cars_recycler.adapter = carsAdapter
    }
}
