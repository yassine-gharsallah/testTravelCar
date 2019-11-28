package fr.travelcar.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.travelcar.test.R
import fr.travelcar.test.data.model.response.Car

class CarAdapter(private val clickOfferListner: ((Car) -> Unit)? = null) :
        RecyclerView.Adapter<GenericViewHolder>() {


    var mData = arrayListOf<CarViewItem>()


    fun setData(items: List<Car>) {
        mData.clear()
        this.mData = ArrayList(items.map { CarViewItem.CarView(it) })



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return CarViewHolder(view) {
            clickOfferListner?.invoke(it)
        }
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(pos: Int): CarViewItem = mData[pos]

    override fun getItemViewType(position: Int): Int {
        return when (mData[position]) {
            is CarViewItem.CarView -> R.layout.item_car

        }
    }

    override fun getItemCount(): Int = mData.size

    sealed class CarViewItem {
        data class CarView(val car: Car) : CarViewItem()

    }
}
