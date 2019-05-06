package pe.wemake.dixi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet_table")
data class Pet(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "perro",
    var picture: String = "url" )