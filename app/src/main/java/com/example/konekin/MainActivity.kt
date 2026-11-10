package com.example.konekin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.konekin.databinding.ActivityMainBinding
import com.example.konekin.model.Data
import com.example.konekin.model.Users
import com.example.konekin.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var employeeCall: Call<Users>? = null
    private var employees: List<Data> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lvNama.setOnItemClickListener { _, _, position, _ ->
            startActivity(DetailActivity.createIntent(this, employees[position]))
        }
        loadEmployees()
    }

    private fun loadEmployees() {
        employeeCall = ApiClient.getInstance().getAllUsers()
        employeeCall?.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (call.isCanceled) return
                val body = response.body()
                val data = body?.data
                if (!response.isSuccessful || body?.status != "success" || data == null) {
                    showError()
                    return
                }
                employees = data
                binding.lvNama.adapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_list_item_1,
                    employees.map { it.employeeName }
                )
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                if (!call.isCanceled) showError()
            }
        })
    }

    private fun showError() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        employeeCall?.cancel()
        super.onDestroy()
    }
}
