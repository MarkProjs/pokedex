package dawsoncollege.android.pokedex

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.io.BufferedReader
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection


private const val LOG_TAG = "POKEAPI"

const val POKE_API_BASE_URL = "https://pokeapi.co/api/v2"
const val POKEDEX_BASE_URL = "$POKE_API_BASE_URL/pokedex"
const val POKEMON_BASE_URL = "$POKE_API_BASE_URL/pokemon"

private val GSON: Gson = GsonBuilder().setPrettyPrinting().create()
private var simplifiedPokedexEntries: String = ""
private var simplifiedPokemon: String = ""
private var pokedexEntries = arrayListOf<Pokemon>()

/**
 * Simplifies the API response from the pokedex endpoint.
 * The simplified JSON has the following format :
 * ```
 * [
 *   {
 *     "number" : 1,
 *     "name" : "bulbasaur"
 *   },
 *   {
 *     "number" : 2,
 *     "name" : "ivysaur"
 *   },
 *   ...
 *   {
 *     "number" : 151,
 *     "name" : "mew"
 *   }
 * ]
 * ```
 *
 * @param apiResponse the API response as received from PokeAPI's pokedex endpoint
 *                    (https://pokeapi.co/api/v2/pokedex)
 *
 * @return the simplified JSON as a String
 */
private fun simplifyPokedexEntries(apiResponse: String): String {
    val json = GSON.fromJson(apiResponse, JsonObject::class.java)

    val simplified = json["pokemon_entries"].asJsonArray.map {
        JsonObject().apply {
            addProperty(
                "number",
                it.asJsonObject["entry_number"].asInt
            )
            addProperty(
                "name",
                it.asJsonObject["pokemon_species"].asJsonObject["name"].asString
            )
        }
    }

    return GSON.toJson(simplified)
}

private fun getAPIFromWeb() {
    val url = URL("${POKEDEX_BASE_URL}/2/")
    val conn = url.openConnection() as HttpsURLConnection

    conn.requestMethod = "GET"
    //open the socket
    conn.connect()
    //check for 200 OK
    if (conn.responseCode == 200) {
        val inStream: InputStream = conn.inputStream
        val reader = BufferedReader(inStream.reader())
        reader.use { reader ->
            val tempResponse = reader.readText()
            simplifiedPokedexEntries = (simplifyPokedexEntries(tempResponse))
        }
    }
    conn.disconnect()
}

fun getPokedexEntries(): List<Pokemon> {
    getAPIFromWeb()
    //do the parsing
    val simplifiedPokeEntries = GSON.fromJson(simplifiedPokedexEntries, JsonArray::class.java)
    for (i in simplifiedPokeEntries.asJsonArray) {
        pokedexEntries.add(Pokemon(i.asJsonObject["number"].asInt, i.asJsonObject["name"].asString))
    }
    return pokedexEntries
}

//get the pokemon API
private fun getPokemonAPI(pokemonName: String) {
    val url = URL("${POKEMON_BASE_URL}/${pokemonName}")
    val conn = url.openConnection() as HttpsURLConnection
    conn.requestMethod = "GET"
    //open the socket
    conn.connect()
    //check the reposneCode
    if(conn.responseCode == 200) {
        val inStream: InputStream = conn.inputStream
        val reader = BufferedReader(inStream.reader())
        reader.use {read ->
            val tempResponse = read.readText()
            simplifiedPokemon = simplifyPokemon(tempResponse)
        }
    }
    conn.disconnect()
}

//turn into json object
fun getPokemonDetails(pokemonName: String): JsonObject {
    getPokemonAPI(pokemonName)
    
}

/**
 * Simplifies the API response from the pokemon endpoint.
 * The simplified JSON has the following format :
 * ```
 * {
 *   "name" : "bulbasaur",
 *   "base_exp_reward" : 64,
 *   "types" : [
 *     "grass", "poison"
 *   ],
 *   "base_maxHp" : 45,
 *   "base_attack" : 49,
 *   "base_defense" : 49,
 *   "base_special-attack" : 65,
 *   "base_special-defense" : 65,
 *   "base_speed" : 45,
 *   "back_sprite" : "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/transparent/back/1.png"
 *   "front_sprite" : "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/transparent/1.png"
 * }
 * ```
 *
 * @param apiResponse the API response as received from PokeAPI's pokemon endpoint
 *                    (https://pokeapi.co/api/v2/pokemon)
 *
 * @return the simplified JSON as a String
 */
private fun simplifyPokemon(apiResponse: String): String {
    val json = GSON.fromJson(apiResponse, JsonObject::class.java)

    val simplified = JsonObject().apply {
        addProperty(
            "name",
            json["name"].asString
        )
        addProperty(
            "base_exp_reward",
            json["base_experience"].asInt
        )
        add(
            "types",
            JsonArray().apply {
                json["types"].asJsonArray.map {
                    it.asJsonObject["type"].asJsonObject["name"].asString
                }.forEach { this.add(it) }
            }
        )
        json["stats"].asJsonArray.associate {
            it.asJsonObject.run {
                (this["stat"].asJsonObject["name"].asString) to (this["base_stat"].asInt)
            }
        }.forEach {
            val statName = if (it.key == "hp") "maxHp" else it.key
            this.addProperty("base_$statName", it.value)
        }
        addProperty(
            "back_sprite",
            json["sprites"].asJsonObject["versions"].asJsonObject["generation-i"]
                .asJsonObject["red-blue"].asJsonObject["back_transparent"].asString
        )
        addProperty(
            "front_sprite",
            json["sprites"].asJsonObject["versions"].asJsonObject["generation-i"]
                .asJsonObject["red-blue"].asJsonObject["front_transparent"].asString
        )
    }

    return GSON.toJson(simplified)
}