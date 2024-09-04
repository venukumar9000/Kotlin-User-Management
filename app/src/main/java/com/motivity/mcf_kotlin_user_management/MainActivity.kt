package com.motivity.mcf_kotlin_user_management
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.motivity.mcf_kotlin_user_management.api.Api
import com.motivity.mcf_kotlin_user_management.api.RetrofitClient
import com.motivity.mcf_kotlin_user_management.models.Employee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var tableLayout: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        // Log a screen view event
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "screen_main")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Main Screen")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

        tableLayout = findViewById(R.id.tableLayout)
        val getEmployeesButton: Button = findViewById(R.id.getEmployeesButton)
        getEmployeesButton.setOnClickListener {
            // Call the API when the button is clicked
            fetchEmployees()
        }
    }

    private fun fetchEmployees() {
        // Create an instance of the API interface using RetrofitClient
        val api: Api = RetrofitClient.instance

        // Call the API to get a list of employees
        api.getAllEmployees().enqueue(object : Callback<List<Employee>> {
            override fun onResponse(
                call: Call<List<Employee>>,
                response: Response<List<Employee>>
            ) {
                if (response.isSuccessful) {
                    // API call was successful, display the list of employees in the table
                    displayEmployees(response.body())
                } else {
                    // API call failed, display an error message
                  //  displayError("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                // API call failed, display an error message
               // displayError("Error: ${t.message}")
            }
        })
    }


    private fun displayEmployees(employees: List<Employee>?) {
        // Clear existing rows, excluding the header row
        tableLayout.removeViews(1, tableLayout.childCount - 1)

        if (employees != null) {
            // Create and add rows dynamically for each employee
            for (employee in employees) {
                val row = TableRow(this)

                val params = TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                row.layoutParams = params
                row.setPadding(8, 8, 8, 8)

                val idTextView = TextView(this)
                idTextView.text = employee.id.toString()
                idTextView.layoutParams =
                    TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

                val designationTextView = TextView(this)
                designationTextView.text = employee.designation
                designationTextView.layoutParams =
                    TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

                val departmentTextView = TextView(this)
                departmentTextView.text = employee.department
                departmentTextView.layoutParams =
                    TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

                val organizationTextView = TextView(this)
                organizationTextView.text = employee.organization
                organizationTextView.layoutParams =
                    TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

                val phoneNumberTextView = TextView(this)
                phoneNumberTextView.text = employee.phoneNumber
                phoneNumberTextView.layoutParams =
                    TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

                row.addView(idTextView)
                row.addView(designationTextView)
                row.addView(departmentTextView)
                row.addView(organizationTextView)
                row.addView(phoneNumberTextView)

                tableLayout.addView(row)
            }
        } else {
            // Display an error message if the list of employees is null
          //  displayError("Unexpected result: employees list is null")
        }
    }
}