package pe.wemake.dixi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.wemake.dixi.repository.PetRepository
import pe.wemake.dixi.dao.PetDao
import pe.wemake.dixi.data.DixiDatabase
import pe.wemake.dixi.model.Pet


class PetViewModel(application: Application) : AndroidViewModel(application){

    private val repository: PetRepository
    val allPets: LiveData<List<Pet>>

    init {
        var petDao: PetDao = DixiDatabase.getDatabase(application, viewModelScope).petDao()
        repository  = PetRepository(petDao)
        allPets = repository.allPets
    }

    fun insert(pet: Pet) = viewModelScope.launch(Dispatchers.IO){
        Log.d("Inserting", pet.id.toString())
        Log.d("Inserting", pet.name)
        repository.insert(pet)

    }


}
