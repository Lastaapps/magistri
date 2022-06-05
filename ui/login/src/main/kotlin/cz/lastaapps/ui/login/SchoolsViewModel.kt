package cz.lastaapps.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.lastaapps.api.login.entities.School
import cz.lastaapps.api.login.entities.Town
import cz.lastaapps.api.login.school.SchoolRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(
    private val schoolRepo: SchoolRepo
) : ViewModel() {

    val errors = Channel<String>(Channel.BUFFERED)

    val selectedTown = MutableStateFlow<Town?>(null)

    val townList = MutableStateFlow(emptyList<Town>())
    val townLoading = MutableStateFlow(false)

    fun reloadTowns() {
        viewModelScope.launch {
            loadTowns()
        }
    }

    private suspend fun loadTowns() {
        if (townLoading.value) return
        townLoading.value = true

        schoolRepo.loadTowns()?.let { towns ->
            townList.emit(towns)
            selectTown(towns.first())
        }

        townLoading.value = false
    }

    fun selectTown(town: Town) {
        selectedTown.value = town
    }

    init {
        viewModelScope.launch {
            loadTowns()
        }
    }


    val selectedSchool = MutableStateFlow<School?>(null)
    val schoolList = MutableStateFlow(emptyList<School>())
    val schoolLoading = MutableStateFlow(false)

    fun reloadSchools() {
        viewModelScope.launch {
            loadSchools(selectedTown.value!!)
        }
    }

    private suspend fun loadSchools(town: Town) {
        if (schoolLoading.value) return
        schoolLoading.value = true

        schoolRepo.loadSchools(town)?.let { schools ->
            schoolList.emit(schools)
            selectSchool(schools.first())
        }

        schoolLoading.value = false
    }

    fun selectSchool(school: School?) {
        selectedSchool.value = school
    }

    init {
        viewModelScope.launch {
            selectedTown.filterNotNull().collectLatest { town ->
                selectSchool(null)
                schoolList.value = emptyList()
                loadSchools(town)
            }
        }
        viewModelScope.launch {
            schoolRepo.errors.receiveAsFlow()
                .collect { errors.send(it.message ?: "Unknown error") }
        }
    }
}