package fr.travelcar.test.data.model.response

import java.io.Serializable


data class Car(
    var make: String,
    var model: String,
    var year: String,
    var picture: String,
    var equipements: List<String>
) : Serializable



