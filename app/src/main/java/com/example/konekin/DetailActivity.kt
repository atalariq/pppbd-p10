package com.example.konekin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.konekin.databinding.ActivityDetailBinding
import com.example.konekin.model.Data

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!intent.hasExtra(EXTRA_ID)) {
            finish()
            return
        }
        enableEdgeToEdge()
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
        binding.employeeId.text = intent.getIntExtra(EXTRA_ID, 0).toString()
        binding.employeeName.text = intent.getStringExtra(EXTRA_NAME)
        binding.employeeAge.text = intent.getIntExtra(EXTRA_AGE, 0).toString()
        // The API supplies a number without a currency; display its original value.
        binding.employeeSalary.text = intent.getIntExtra(EXTRA_SALARY, 0).toString()
    }

    companion object {
        private const val EXTRA_ID = "employee_id"
        private const val EXTRA_NAME = "employee_name"
        private const val EXTRA_AGE = "employee_age"
        private const val EXTRA_SALARY = "employee_salary"

        fun createIntent(context: Context, employee: Data): Intent =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_ID, employee.id)
                putExtra(EXTRA_NAME, employee.employeeName)
                putExtra(EXTRA_AGE, employee.employeeAge)
                putExtra(EXTRA_SALARY, employee.employeeSalary)
            }
    }
}
