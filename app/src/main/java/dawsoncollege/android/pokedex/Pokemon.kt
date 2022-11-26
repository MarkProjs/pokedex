package dawsoncollege.android.pokedex

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class Pokemon(
    @PrimaryKey val number: Int,
    @ColumnInfo val name: String)