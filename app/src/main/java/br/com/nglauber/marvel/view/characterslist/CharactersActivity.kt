package br.com.nglauber.marvel.view.characterslist

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import br.com.nglauber.marvel.R
import br.com.nglauber.marvel.model.api.MarvelApi.loadCharacters

class CharactersActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by lazy({
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    })

    private val adapter: CharactersAdapter by lazy {
        CharactersAdapter()
    }

    private var recyclerState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        val llm = LinearLayoutManager(this)
        recyclerCharacters.layoutManager = llm
//      recyclerCharacters.adapter = Companion.adapter
        
        recyclerCharacters.adapter = adapter
        recyclerCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition = llm.findLastVisibleItemPosition()
//                if (lastVisibleItemPosition == Companion.adapter.itemCount - 1 && !viewModel.isLoading) {
                  if (lastVisibleItemPosition == adapter.itemCount - 1 && !viewModel.isLoading) {
                    Log.d("NGVL", "Loading more...")
                    loadCharacters(viewModel.currentPage + 1)

                }
            }
        })
        loadCharacters(0)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("lmState", recyclerCharacters.layoutManager.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    private fun loadCharacters(page: Int) {
//        viewModel.load(page)
        viewModel.load(page, {  characters ->
            characters.forEach{ character ->
                Log.d("NGVL", "${character.id} - ${character.name}")
                adapter.add(character)
            }
            if (recyclerState != null) {
                recyclerCharacters.layoutManager.onRestoreInstanceState(recyclerState)
                recyclerState = null
            }
        }}
    }
}