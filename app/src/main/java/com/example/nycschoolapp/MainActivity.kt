@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.nycschoolapp

import MainViewModel
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nycschoolapp.ui.theme.NycSchoolAppTheme


class MainActivity : ComponentActivity() {
     private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NycSchoolAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(viewModel)
                }
            }
        }
    }
}
@Composable
fun MyApp(viewModel : MainViewModel) {
    val navController = rememberNavController()

    NycSchoolAppTheme {
        NavHost(navController, startDestination = "schoolList") {
            composable("schoolList") {
                ObserveSchools(viewModel , navController)
            }
            composable("schoolDetail/{dbn}") { backStackEntry ->
                val dbn = backStackEntry.arguments?.getString("dbn")
                Log.d("NYC" ,"db value in navhost : $dbn")
                if (dbn != null) {
                    val school =  viewModel.findSchoolByDBN(dbn)// Retrieve school data based on schoolId
                    Log.d("NYC" ,"school value in navhost : $school")
                    val schoolval = SchoolData(dbn="24Q296", school_name="Pan American International High School",
                        overview_paragraph="The Pan American International High School is a diverse learning community of recently-immigrated English Language Learners (ELLs). Our primary focus is English language development, while providing opportunities for students to enhance their Spanish language skills. Our partnerships with organizations such as Urban Arts cultivate the leadership of students in their communities through service, learning projects, internships, and the arts. We are committed to creating an environment that celebrates studentsÂ’ individual identities while preparing them to be successful in college and become productive members of the global community., academicopportunities2=Before and After-School Activities, Saturday Academy, CUNY LINCT, University of Albany College Now, and College Prep classes., building_code=Q744, location=45-10 94th Street, Elmhurst NY 11373 (40.743228, -73.871505), phone_number=718-271-3602, fax_number=718-271-4041, school_email=laquino@panamericanihs.org, website=www.panamericanihs.org,"
                        ,finalgrades="9-12",total_students="380", attendance_rate="0.899999976")
                    if (schoolval != null) {
                        SchoolDetail(navController , viewModel ,school = schoolval)
                    }
                }
            }
        }
    }
}

@Composable
  fun ObserveSchools(viewModel : MainViewModel , navController: NavController) {
    val schools by viewModel.schools.observeAsState(initial = emptyList())

    SchoolList(schools) { selectedSchool ->
        // Handle item click, maybe navigate to detail screen
        // You can use intents or navigation components for navigation
        Log.d("NYC" ,"db value in ObserveSchools : $selectedSchool")
        navController.navigate("schoolDetail/${selectedSchool.dbn}")
    }
}

@Composable
fun SchoolList(schools: List<SchoolData>, onItemClick: (SchoolData) -> Unit) {
    LazyColumn {
        items(schools) { school ->
            Text(
                text = school.school_name,
                modifier = Modifier
                    .clickable { onItemClick(school) }
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun SchoolDetail(navController: NavController, viewModel: MainViewModel, school: SchoolData) {
    Log.d("NYC","SchoolDetail - school data :$school ")


    if (school != null) {
        Column() {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            // Display school details
            Text(text = school.overview_paragraph)
        }
    } else {
        // School not found or error occurred
        Text(text = "School not found")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NycSchoolAppTheme {
    }
}