package dawsoncollege.android.pokedex

import androidx.room.Database
import androidx.room.RoomDatabase

//Annotates class to be a Room Database with ta table (entity) of the Pokemon class
@Database(entities = [Pokemon::class, PokeInfo::class], version = 1)
abstract class PokemonRoomDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokeInfoDao(): PokeInfoDao
}
