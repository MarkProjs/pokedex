package dawsoncollege.android.pokedex

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokeInfoDao {


    @Query("SELECT * FROM pokemon_info_table WHERE name = :name")
    fun getPokeInfo(name: String): PokeInfo

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokeInfo(pokeInfo: PokeInfo)

    @Query ("SELECT (SELECT COUNT(*) FROM pokemon_info_table)== 0")
    fun isEmpty(): Boolean
}