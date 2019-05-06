package pe.wemake.dixi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.wemake.dixi.dao.PetDao
import pe.wemake.dixi.model.Pet

@Database(entities = [Pet::class], version = 1)
abstract class DixiDatabase : RoomDatabase() {

    abstract fun petDao(): PetDao

    companion object{
        @Volatile
        private var INSTANCE: DixiDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DixiDatabase {
           return INSTANCE ?: synchronized(this){
               val instance = Room.databaseBuilder(
                   context.applicationContext,
                   DixiDatabase::class.java,
                   "dixi_database"
               ).addCallback(DixiDatabaseCallback(scope))
                   .fallbackToDestructiveMigration()
                   .build()
               INSTANCE = instance
               instance
           }
        }
        private class DixiDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.petDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(petDao: PetDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            petDao.deleteAll()

            var pet = Pet(1, "Bobby", "http://placekitten.com/450/450")
            petDao.insert(pet)
            pet = Pet(2, "Petty", "http://placekitten.com/350/350")
            petDao.insert(pet)
            pet = Pet(3, "John", "http://placekitten.com/300/300")
            petDao.insert(pet)
            pet = Pet(4, "Stewart", "http://placekitten.com/200/200")
            petDao.insert(pet)

        }
    }
}