package androidx.lifecycle

fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

fun <T> LiveData<T>.nonNullObserveConsume(owner: LifecycleOwner, action: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let {
            action(it)
            this.postValue(null)
        }
    })
}