package fr.travelcar.test.utils


interface RealInteractor<in I, out T> {
    fun execute(input: I? = null): T
}