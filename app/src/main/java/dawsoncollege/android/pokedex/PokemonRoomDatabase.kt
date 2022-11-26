package dawsoncollege.android.pokedex

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Annotates class to be a Room Database with ta table (entity) of the Pokemon class
@Database(entities = [Pokemon::class], version = 1)
public abstract class PokemonRoomDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        //singleton to prevent multiple instances of database opening at the
        //same time
        @Volatile
        private var INSTANCE: PokemonRoomDatabase? = null

        fun getDatabase(context: Context): PokemonRoomDatabase {
            //if the INSTANCE is not null, then return it,
            //if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonRoomDatabase::class.java,
                    "pokemon_database"
                ).build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}