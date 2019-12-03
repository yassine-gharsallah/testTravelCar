package fr.travelcar.test.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import fr.travelcar.test.R
import fr.travelcar.test.data.model.response.Car

class CarAdapter(private val clickOfferListner: ((Car) -> Unit)? = null) :
        RecyclerView.Adapter<GenericViewHolder>(), Filterable {


    private var carList = arrayListOf<Car>()
    private var carListFiltered = arrayListOf<Car>()

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    carListFiltered = carList
                } else {
                    val filteredList = arrayListOf<Car>()
                    if (carList != null) {
                        for (row in carList) {
                            if (row.make.toLowerCase().contains(charString.toLowerCase()) || row.model.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row)
                            }
                        }
                    }
                    carListFiltered = filteredList
                    mData = ArrayList(carListFiltered.map { CarViewItem.CarView(it) })
                }

                val filterResults = FilterResults()
                filterResults.values = carListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                carListFiltered = filterResults?.values as ArrayList<Car>
                if (carList != null && carListFiltered != null) {
                    mData = ArrayList(carListFiltered.map { CarViewItem.CarView(it) })
                    notifyDataSetChanged()
                }
            }
        }
    }


    var mData = arrayListOf<CarViewItem>()


    fun setData(items: List<Car>) {
        mData.clear()
        carList = items as ArrayList<Car>
        carListFiltered = items
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
