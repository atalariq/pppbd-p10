package com.example.konekin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.konekin.databinding.ActivityDetailBinding
import com.example.konekin.model.Data

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!intent.hasExtra(EXTRA_ID)) {
            finish()
            return
        }

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            employeeId.text = intent.getIntExtra(EXTRA_ID, 0).toString()
            employeeName.text = intent.getStringExtra(EXTRA_NAME)
            employeeAge.text = intent.getIntExtra(EXTRA_AGE, 0).toString()
            employeeSalary.text = intent.getIntExtra(EXTRA_SALARY, 0).toString()
        }
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
