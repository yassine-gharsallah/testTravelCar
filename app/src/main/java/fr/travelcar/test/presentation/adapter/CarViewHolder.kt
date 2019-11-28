package fr.travelcar.test.presentation.adapter

import android.view.View
import com.bumptech.glide.Glide
import fr.travelcar.test.data.model.response.Car
import kotlinx.android.synthetic.main.item_car.view.*

class CarViewHolder(itemView: View, private val clickOfferListener: (Car) -> Unit) :
    GenericViewHolder(itemView) {
    override fun <T> bind(t: T) {
        val data = (t as CarAdapter.CarViewItem.CarView).car

        itemView.title.text = "${data.make + " "+ data.model}"

        Glide.with(itemView.context)
            .load(data.picture)
            .into(itemView.image)


        itemView.setOnClickListener {
            clickOfferListener.invoke(data)
        }
    }


}