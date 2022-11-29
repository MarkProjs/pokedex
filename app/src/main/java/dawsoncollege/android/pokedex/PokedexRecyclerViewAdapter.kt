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

class PokedexRecyclerViewAdapter(private val pokemonList: List<Pokemon>) :
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
        val pokedexEntry = pokemonList[position]

        binding.pokedexNumberTxt.text = pokedexEntry.number.toString()
        binding.pokemonNameTxt.text = pokedexEntry.name

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
                    context.getString(R.string.error_intent_show_pokemon, pokedexEntry.name),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun getItemCount(): Int = pokemonList.size
}