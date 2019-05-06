package pe.wemake.dixi.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import pe.wemake.dixi.dao.PetDao
import pe.wemake.dixi.model.Pet


class PetRepository(private var petDao: PetDao){
    var allPets: LiveData<List<Pet>> = petDao.getAllPets()

    @WorkerThread
    suspend fun insert(pet: Pet){
        petDao.insert(pet)
    }


}