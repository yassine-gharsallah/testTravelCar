package fr.travelcar.test.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class GenericViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun <T> bind(t: T)
}
