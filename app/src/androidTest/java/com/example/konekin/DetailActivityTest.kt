package com.example.konekin

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.konekin.model.Data
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {
    @Test
    fun detailDisplaysSelectedEmployeeWithoutNetworkRequest() {
        val employee = Data(7, "Herrod Chandler", 137500, 59, "")
        val intent = DetailActivity.createIntent(ApplicationProvider.getApplicationContext(), employee)
        ActivityScenario.launch<DetailActivity>(intent).use {
            onView(withId(R.id.employee_id)).check(matches(withText("7")))
            onView(withId(R.id.employee_name)).check(matches(withText("Herrod Chandler")))
            onView(withId(R.id.employee_age)).check(matches(withText("59")))
            onView(withId(R.id.employee_salary)).check(matches(withText("137500")))
        }
    }
}
