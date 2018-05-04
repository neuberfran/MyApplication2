package br.com.nglauber.marvel.model.api

import br.com.nglauber.marvel.model.api.entity.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiDef {
    @GET("characters")
    fun allCharacters(@Query("offset") offset: Int? = 0): Call<Response>
}