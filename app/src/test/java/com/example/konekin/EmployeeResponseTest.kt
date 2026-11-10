package com.example.konekin

import com.example.konekin.model.Users
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class EmployeeResponseTest {
    @Test
    fun employeeJsonMapsFieldsUsedByListAndDetail() {
        val json = """{
            "status": "success",
            "data": [{"id": 7, "employee_name": "Herrod Chandler",
                "employee_salary": 137500, "employee_age": 59, "profile_image": ""}]
        }"""
        val response = Gson().fromJson(json, Users::class.java)
        val employee = response.data!!.single()
        assertEquals("success", response.status)
        assertEquals(7, employee.id)
        assertEquals("Herrod Chandler", employee.employeeName)
        assertEquals(137500, employee.employeeSalary)
        assertEquals(59, employee.employeeAge)
        assertEquals("", employee.profileImage)
    }

    @Test
    fun errorEnvelopeCanBeRecognizedWithoutDereferencingMissingData() {
        val response = Gson().fromJson("""{"status":"error","message":"Unavailable"}""", Users::class.java)
        assertEquals("error", response.status)
        assertNull(response.data)
    }
}
