package dawsoncollege.android.pokedex

import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.lifecycle.lifecycleScope
import androidx.room.RoomDatabase
import dawsoncollege.android.pokedex.MainActivity.MainActivityLoadState.*
import dawsoncollege.android.pokedex.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: PokedexRecyclerViewAdapter
    private var pokedexEntries = listOf<Pokemon>()
    private lateinit var db: PokemonRoomDatabase
    private lateinit var pokemonDao: PokemonDao

    /**
     * [IN_PROGRESS] The activity is currently loading data from disk or network
     * [COMPLETED] The activity has successfully finished loading the data
     * [FAILED] The activity has finished loading, but the data isn't there
     */
    private enum class MainActivityLoadState {
        IN_PROGRESS, COMPLETED, FAILED
    }

    /**
     * Sets the loading state for the activity.
     * This means it shows/hide the appropriate Views, depending on the given current load state.
     */
    private fun setLoadState(state: MainActivityLoadState) {
        binding.loadingIndicator.visibility = View.GONE
        binding.pokedexRecyclerView.visibility = View.GONE
        binding.tryAgainBtn.isEnabled = false
        binding.tryAgainBtn.visibility = View.GONE

        when (state) {
            IN_PROGRESS -> binding.loadingIndicator.visibility = View.VISIBLE
            COMPLETED -> binding.pokedexRecyclerView.visibility = View.VISIBLE
            FAILED -> {
                binding.tryAgainBtn.isEnabled = true
                binding.tryAgainBtn.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //build the db
        db = Room.databaseBuilder(
            this@MainActivity,
            PokemonRoomDatabase::class.java, "pokemon_db").build()
            //dao
        pokemonDao = db.pokemonDao()
        loadPokedexEntries()
        binding.tryAgainBtn.setOnClickListener { loadPokedexEntries() }
    }

    // TODO : call this when data was loaded from the local cache
    private suspend fun showLoadFromDbToast() = coroutineScope {
        launch {
            Toast.makeText(
                applicationContext,
                getString(R.string.load_from_db),
                Toast.LENGTH_LONG
            ).show()
        }
    }


    // TODO : call this when data had to be loaded from the network API
    private suspend fun showLoadFromAPIToast() = coroutineScope {
        launch {
            Toast.makeText(
                applicationContext,
                getString(R.string.load_from_api),
                Toast.LENGTH_LONG
            ).show()
        }
    }


    // TODO : call this when data loading from cache and network failed
    private suspend fun showErrorLoadToast() = coroutineScope {
        launch {
            Toast.makeText(
                applicationContext,
                getString(R.string.error_fetch_entries),
                Toast.LENGTH_LONG
            ).show()
        }

    }


    private fun loadPokedexEntries() {
        setLoadState(IN_PROGRESS)

        // TODO : try to get the list of pokedex entries from the local database
        lifecycleScope.launch(Dispatchers.Main) {
            pokedexEntries = fetchData()
            showPokedexEntries()
            setLoadState(COMPLETED)
        }

        // TODO : if necessary get the list from the web (and cache it in the local database)

    }


    private suspend fun fetchData(): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            var tempList: List<Pokemon> = listOf<Pokemon>()
            if(pokemonDao.isEmpty()) {
                tempList = getPokedexEntries()
                withContext(Dispatchers.Main) {
                    showLoadFromAPIToast()
                }
                for(i in tempList) {
                    pokemonDao.insertPokemon(i)
                }
            }
            else {
                tempList = pokemonDao.getAllPokemons()
                withContext(Dispatchers.Main) {
                    showLoadFromDbToast()
                }

            }

            return@withContext tempList

        }
    }


    private fun showPokedexEntries() {
        adapter = PokedexRecyclerViewAdapter(pokedexEntries)
        binding.pokedexRecyclerView.adapter = adapter
        binding.pokedexRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}