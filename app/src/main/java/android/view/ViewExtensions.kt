package android.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.nonNullObserve

fun View.linkVisibilityTo(liveData: LiveData<Boolean>, lifecycleOwner: LifecycleOwner) {
    liveData.nonNullObserve(lifecycleOwner) {
        setVisible(it)
    }
}

fun <T> View.linkVisibilityTo(
    liveData: LiveData<T>,
    lifecycleOwner: LifecycleOwner,
    transform: ((T) -> Boolean)
) {
    liveData.nonNullObserve(lifecycleOwner) {
        setVisible(transform(it))
    }
}

fun View.setVisible(state: Boolean) {
    visibility = if (state) View.VISIBLE else View.GONE
}

fun View.showOrGone(show: Boolean) {
    if (show) this.visibility = View.VISIBLE else this.visibility = View.GONE
}