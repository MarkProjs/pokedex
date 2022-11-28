package dawsoncollege.android.pokedex

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonElement

@Entity(tableName = "pokemon_table")
data class Pokemon(
    @PrimaryKey val number: Int,
    @ColumnInfo val name: String)