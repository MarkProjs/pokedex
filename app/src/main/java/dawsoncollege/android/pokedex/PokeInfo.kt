package dawsoncollege.android.pokedex

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_info_table")
class PokeInfo(
    @PrimaryKey val name: String,
    @ColumnInfo val base_exp_reward: Int,
    @ColumnInfo val types: List<String>,
    @ColumnInfo val base_maxHp: Int,
    @ColumnInfo val base_attack: Int,
    @ColumnInfo val base_defense: Int,
    @ColumnInfo val base_special_attack: Int,
    @ColumnInfo val base_special_defense: Int,
    @ColumnInfo val base_speed: Int,
    @ColumnInfo val back_sprite: String,
    @ColumnInfo val front_sprite: String
)