package pe.wemake.dixi.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pe.wemake.dixi.model.Pet

@Dao
interface PetDao{

    @Query("SELECT * FROM pet_table")
    fun getAllPets() : LiveData<List<Pet>>


    @Insert
    suspend fun insert(pet: Pet)

    @Query("DELETE FROM pet_table")
    fun deleteAll()

    @Delete
    suspend fun delete(pet: Pet)
}
