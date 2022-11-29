package dawsoncollege.android.pokedex

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemons(): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemon: Pokemon)

    @Query("SELECT (SELECT COUNT(*) FROM pokemon_table) == 0")
    fun isEmpty(): Boolean
}