import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschoolapp.SchoolApiService
import com.example.nycschoolapp.SchoolData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://data.cityofnewyork.us/resource/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(SchoolApiService::class.java)
    private val _schools = MutableLiveData<List<SchoolData>>()
    val schools: LiveData<List<SchoolData>> = _schools


    init {
        fetchSchools()
    }

     fun fetchSchools() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val schoolsData: List<SchoolData> = service.getSchools()
                _schools.postValue(schoolsData)
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }

    fun findSchoolByDBN(schoolId: String): SchoolData? {
        var schoolData : SchoolData? = null
        viewModelScope.launch(Dispatchers.IO) {
          schoolData = getSchoolById(schoolId)
            Log.d("NYC" ,"school value in navhost : $schoolData")

        }
        return schoolData
    }

    suspend fun getSchoolById(schoolId: String):SchoolData? {
      return try{
            val schools = service.getSchools()
          Log.d("NYC" ,"school value in getSchoolById : ${schools.firstOrNull{it.dbn == schoolId}}")

          schools.firstOrNull { it.dbn == schoolId }
        } catch (e: Exception) {
            null
        }
    }
}
