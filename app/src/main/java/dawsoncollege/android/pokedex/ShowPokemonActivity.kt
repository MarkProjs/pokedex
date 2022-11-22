package dawsoncollege.android.pokedex

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import dawsoncollege.android.pokedex.ShowPokemonActivity.ShowPokemonLoadState.*
import dawsoncollege.android.pokedex.databinding.ActivityShowPokemonBinding

class ShowPokemonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowPokemonBinding

    companion object {
        const val POKEDEX_ENTRY_KEY = "pokedex_entry"
    }

    private val GSON: Gson = Gson()

    /**
     * [IN_PROGRESS] The activity is currently loading data from disk or network
     * [COMPLETED] The activity has successfully finished loading the data
     * [FAILED] The activity has finished loading, but the data isn't there
     */
    private enum class ShowPokemonLoadState {
        IN_PROGRESS, COMPLETED, FAILED
    }

    /**
     * Sets the loading state for the activity.
     * This means it shows/hide the appropriate Views, depending on the given current load state.
     */
    private fun setLoadState(state: ShowPokemonLoadState) {
        binding.pokedexNumberTxt.visibility = View.GONE
        binding.dash.visibility = View.GONE
        binding.pokemonNameTxt.visibility = View.GONE
        binding.frontImg.visibility = View.GONE
        binding.backImg.visibility = View.GONE
        binding.pokemonInfoGrid.visibility = View.GONE

        binding.loadingIndicator.visibility = View.GONE

        binding.tryAgainBtn.isEnabled = false
        binding.tryAgainBtn.visibility = View.GONE

        when (state) {
            IN_PROGRESS -> binding.loadingIndicator.visibility = View.VISIBLE
            COMPLETED -> {
                binding.pokedexNumberTxt.visibility = View.VISIBLE
                binding.dash.visibility = View.VISIBLE
                binding.pokemonNameTxt.visibility = View.VISIBLE
                binding.frontImg.visibility = View.VISIBLE
                binding.backImg.visibility = View.VISIBLE
                binding.pokemonInfoGrid.visibility = View.VISIBLE
            }
            FAILED -> {
                binding.tryAgainBtn.isEnabled = true
                binding.tryAgainBtn.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            // TODO : get the result from the intent, into a variable
            GSON.fromJson(it.getString(POKEDEX_ENTRY_KEY), Any::class.java)
        }

        loadPokemon()
        binding.tryAgainBtn.setOnClickListener { loadPokemon() }
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
            getString(R.string.error_fetch_pokemon),
            Toast.LENGTH_LONG
        ).show()

    private fun loadPokemon() {
        setLoadState(IN_PROGRESS)

        // TODO : try to get the pokemon data (for the pokedex entry received in the intent) from the local database

        // TODO : if necessary get the pokemon data from the web (and cache it in the local database)

        // TODO : if the pokemon is loaded, get the sprites from the web

        // TODO : display the pokemon data
    }

    private fun displayPokemon() {
        // TODO : from the pokemon data that was loaded by [loadPokemon], display it
//        binding.pokedexNumberTxt.text = /* pokedex entry number e.g "#023" */
//        binding.pokemonNameTxt.text = /* pokedex entry name e.g. "pikachu" */
//
//        binding.frontImg.setImageBitmap(/* pokemon's front sprite */)
//        binding.backImg.setImageBitmap(/* pokemon's back sprite */)
//
//        binding.pokemonTypesTxt.text = /* pokemon types e.g. "poison, grass" */
//        binding.pokemonExpTxt.text =
//        binding.pokemonMaxhpTxt.text =
//        binding.pokemonAttTxt.text =
//        binding.pokemonDefTxt.text =
//        binding.pokemonSpAttTxt.text =
//        binding.pokemonSpDefTxt.text =
//        binding.pokemonSpeedTxt.text =
    }
}