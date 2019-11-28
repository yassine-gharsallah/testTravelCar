package fr.travelcar.test.data.datasource


import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import fr.travelcar.test.data.model.response.Car
import fr.travelcar.test.room.CarDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomCarDataSourceTest {

    private lateinit var carDataSource: IDataSource<List<Car>>
    private lateinit var dataBase: CarDatabase
    @Before
    fun setUp() {
        dataBase = Room
            .inMemoryDatabaseBuilder(
                InstrumentationRegistry.getTargetContext(),
                CarDatabase::class.java
            )
            .build()
        carDataSource = CarLocalDataSource(dataBase.carDao())
    }

    @After
    fun tearDown() {
        dataBase.close()
    }


    @Test
    fun addAll() {



        val carA = Car(
                make = "Peugeot",
                model = "206",
                year = "2003",
                picture = "desc",
                equipements = listOf()

        )
        val carB = Car(
                make = "Toyota",
                model = "yaris",
                year = "2016",
                picture = "desc",
                equipements = listOf()
        )


        val toAdd = listOf(carA, carB)
        carDataSource.add(toAdd).test().assertComplete()

        val test = carDataSource.get(IDataSource.Spec.ByRef("1")).test()

        test.awaitTerminalEvent()
        test.assertValueAt(0, arrayListOf(carA))
        test.assertValueAt(1, arrayListOf(carB))
        test.assertComplete()
    }


    @Test
    fun query() {
        val test = carDataSource.get(IDataSource.Spec.All).test()
        test.awaitTerminalEvent()
        test.assertNoValues()
        test.assertComplete()
    }
}