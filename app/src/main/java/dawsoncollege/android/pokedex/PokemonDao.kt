package dawsoncollege.android.pokedex

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemons(): List<Pokemon>

    @Query("SELECT * FROM pokemon_table WHERE name = :name ")
    fun getAPokemon(name: String): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemon: Pokemon)

//    @Query("DELETE FROM pokemon_table where name = :name")
//    suspend fun deleteAPokemon(name: String)
}