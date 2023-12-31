package dawsoncollege.android.pokedex

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dawsoncollege.android.pokedex.MainActivity.MainActivityLoadState.*
import dawsoncollege.android.pokedex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: PokedexRecyclerViewAdapter

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

        loadPokedexEntries()
        binding.tryAgainBtn.setOnClickListener { loadPokedexEntries() }
    }

    // TODO : call this when data was loaded from the local cache
    private fun showLoadFromDbToast() =
        Toast.makeText(
            applicationContext,
            getString(R.string.load_from_db),
            Toast.LENGTH_LONG
        ).show()

    // TODO : call this when data had to be loaded from the network API
    private fun showLoadFromAPIToast() =
        Toast.makeText(
            applicationContext,
            getString(R.string.load_from_api),
            Toast.LENGTH_LONG
        ).show()

    // TODO : call this when data loading from cache and network failed
    private fun showErrorLoadToast() =
        Toast.makeText(
            applicationContext,
            getString(R.string.error_fetch_entries),
            Toast.LENGTH_LONG
        ).show()

    private fun loadPokedexEntries() {
        setLoadState(IN_PROGRESS)

        // TODO : try to get the list of pokedex entries from the local database

        // TODO : if necessary get the list from the web (and cache it in the local database)

        // TODO : display the list in the adapter
    }

    private fun showPokedexEntries() {
        // TODO : pass the pokedex entries to the adapter
        adapter = PokedexRecyclerViewAdapter()
        binding.pokedexRecyclerView.adapter = adapter
        binding.pokedexRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}