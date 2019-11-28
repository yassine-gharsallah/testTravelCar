package fr.travelcar.test.presentation

import android.os.Handler
import android.os.Looper
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import fr.travelcar.test.R
import fr.travelcar.test.data.model.response.Car
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.After
import org.junit.Before
import org.junit.Rule

@LargeTest
class CarAppTest {

    companion object {
        const val DELAY_START: Long = 4000
        const val DELAY_ON_CHANGE_SCREEN: Long = 2000
    }

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {

        Intents.init()
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


        Handler(Looper.getMainLooper()).post {
            val activity = (mActivityTestRule.activity as MainActivity)
            activity.carViewModel.apply {
                carsResponse.postValue(arrayListOf(carA, carB))
            }
            activity.cars_recycler.adapter?.notifyDataSetChanged()
        }
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    /**
     * Test click on List and redirection to details and check data well set
     */
    //@Test
    fun checkTransmissionAndDataValid() {
        Thread.sleep(DELAY_START)
        onView(withId(R.id.title))
            .perform(click())

        // Email screen
        Thread.sleep(DELAY_ON_CHANGE_SCREEN)
        intended(hasComponent(CarDetailsActivity::class.java.name))

        onView(withId(R.id.image))
            .check(matches(isDisplayed()))
            .perform(click())

    }
}