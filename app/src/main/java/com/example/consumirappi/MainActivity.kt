package com.example.consumirappi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Response
import com.example.consumirappi.modelo.PokemonClass
import com.example.consumirappi.volley.GsonRequest
import com.example.consumirappi.volley.VolleySingleton


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)




        BuscarPokemon();
    }



    fun BuscarPokemon(){

        val url = "https://pokeapi.co/api/v2/pokemon/pikachu/"
        val mapHeaders : MutableMap<String, String> = mutableMapOf<String,String>() //agregar headers necesarios
        val myGsonRequest: GsonRequest<PokemonClass> = GsonRequest<PokemonClass>(
            url,
            PokemonClass::class.java,
            mapHeaders,
            myRequestSuccessListener(), //Success Listener
            myRequestErrorListener() //Error Listener
        )
        VolleySingleton.getInstance(this).addToRequestQueue(myGsonRequest)
    }

    private fun myRequestSuccessListener(): Response.Listener<PokemonClass> {
        return Response.Listener<PokemonClass> {response ->

            val txt_link_imagen_pokemon = findViewById<TextView>(R.id.textPokemon)
            txt_link_imagen_pokemon.text =   "Una de las habilidades de pikachu es :" + response.abilities[1].ability.name



            /*if(response.abilities[1].ability.name == null){
                txt_link_imagen_pokemon.text = "null"


            }else{


            }*/

        }
    }

    private fun myRequestErrorListener(): Response.ErrorListener {
        return Response.ErrorListener {error ->
            Log.e("ERROR", error.toString())

        }
    }
}