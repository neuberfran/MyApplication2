package br.com.nglauber.marvel.view.characterslist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.nglauber.marvel.model.api.MarvelApi
import br.com.nglauber.marvel.model.api.entity.Character
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.gildor.coroutines.retrofit.await

class CharactersViewModel : ViewModel() {

    var isLoading: Boolean = false
        private set

    var currentPage = -1
        private set

    private val characters = MutableLiveData<List<Character>>()

    fun getCharacters(): LiveData<List<Character>> {
        return characters
    }

    init {
        characters.value = emptyList()
    }

    fun load(page: Int, param: (Any) -> Unit) {
        launch(UI) {
            isLoading = true
            if (page > currentPage) {
                val result = MarvelApi.loadCharacters(page).await()
                val list = characters.value?.toMutableList()
                list?.addAll(result.data.results)
                characters.value = list
            }
            isLoading = false
            currentPage++
        }
    }

    fun reset() {
        isLoading = false
        currentPage = -1
        characters.value = emptyList()
    }
}