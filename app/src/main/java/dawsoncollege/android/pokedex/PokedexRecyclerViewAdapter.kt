package dawsoncollege.android.pokedex

import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dawsoncollege.android.pokedex.databinding.PokedexItemBinding

// TODO : take a list of pokedex entries as parameter to [PokedexRecyclerViewAdapter]'s constructor
class PokedexRecyclerViewAdapter :
    RecyclerView.Adapter<PokedexRecyclerViewAdapter.PokedexViewHolder>() {
    private val LOG_TAG = "POKEDEX_ADAPTER"
    private val GSON: Gson = GsonBuilder().setPrettyPrinting().create()

    class PokedexViewHolder(val binding: PokedexItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexViewHolder =
        PokedexViewHolder(
            PokedexItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: PokedexViewHolder, position: Int) {
        val binding = holder.binding
        val context = binding.root.context
        // TODO : get the pokedex entry from the list that this adapter should have
        val pokedexEntry: Any = {}

        // TODO : change to show the information of the pokedex entry
        binding.pokedexNumberTxt.text = "999"
        binding.pokemonNameTxt.text = "pokemon name"

        binding.infoBtn.setOnClickListener {
            val intent = Intent(context, ShowPokemonActivity::class.java).also {
                it.putExtra(ShowPokemonActivity.POKEDEX_ENTRY_KEY, GSON.toJson(pokedexEntry))
            }

            try {
                context.startActivity(intent)
            } catch (exc: ActivityNotFoundException) {
                Log.w(LOG_TAG, "Could not launch intent ShowPokemon", exc)
                Toast.makeText(
                    context,
                    // TODO : change "pokemon name" to the name of the pokemon
                    context.getString(R.string.error_intent_show_pokemon, "pokemon name"),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun getItemCount(): Int = 0 // TODO : change that to get the real item count
}