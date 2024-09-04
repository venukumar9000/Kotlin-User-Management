import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.motivity.mcf_kotlin_user_management.R
import com.motivity.mcf_kotlin_user_management.activities.UserRegistrationActivity
import com.motivity.mcf_kotlin_user_management.models.UserRegistration
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import retrofit2.Call
import retrofit2.Response

class UserRegistrationActivityTest {

    private lateinit var activityScenario: ActivityScenario<UserRegistrationActivity>

    @Before
    fun setup() {
        // Launch the activity before each test
        activityScenario = ActivityScenario.launch(UserRegistrationActivity::class.java)
    }

    @Test
    fun testSignUpButtonClick() {
        // Mock input data
        val firstName = "John"
        val lastName = "Doe"
        val email = "john.doe@example.com"
        val password = "Password123"
        val contactNumber = "1234567890"

        // Set input values in the UI
        onView(withId(R.id.fname)).perform(typeText(firstName))
        onView(withId(R.id.lname)).perform(typeText(lastName))
        onView(withId(R.id.email)).perform(typeText(email))
        onView(withId(R.id.password)).perform(typeText(password))
        onView(withId(R.id.phoneNumber)).perform(typeText(contactNumber))

        // Perform button click
        onView(withId(R.id.SignUpRegister)).perform(click())

        // You can add assertions to verify UI changes or any other behavior
    }
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(UserRegistrationActivity::class.java)

    @Test
    fun signUpButtonClick_shouldPerformRegistration() {
        // Perform user interactions (simulate SignUp button click)
        onView(withId(R.id.SignUpRegister)).perform(click())

        // Add assertions based on your expected behavior
        // For example, check if a loading indicator is shown or navigate to another activity
    }
    // Add more test cases for validation functions, error handling, etc.
}
