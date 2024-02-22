package com.example.nycschoolapp

import MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Mock
    lateinit var apiService: SchoolApiService

    lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel()
    }

    @Test
    fun `test fetch schools success`() {
        runBlocking {
            val mockSchools = listOf(SchoolData(dbn="24Q296", school_name="Pan American International High School",
            overview_paragraph="The Pan American International High School is a diverse learning community of recently-immigrated English Language Learners (ELLs). Our primary focus is English language development, while providing opportunities for students to enhance their Spanish language skills. Our partnerships with organizations such as Urban Arts cultivate the leadership of students in their communities through service, learning projects, internships, and the arts. We are committed to creating an environment that celebrates studentsÂ’ individual identities while preparing them to be successful in college and become productive members of the global community., academicopportunities2=Before and After-School Activities, Saturday Academy, CUNY LINCT, University of Albany College Now, and College Prep classes., building_code=Q744, location=45-10 94th Street, Elmhurst NY 11373 (40.743228, -73.871505), phone_number=718-271-3602, fax_number=718-271-4041, school_email=laquino@panamericanihs.org, website=www.panamericanihs.org,"
            ,finalgrades="9-12",total_students="380", attendance_rate="0.899999976"))
            `when`(apiService.getSchools()).thenReturn(mockSchools)

            viewModel.fetchSchools()

            assertEquals(mockSchools, viewModel.schools.value)
        }
    }
}