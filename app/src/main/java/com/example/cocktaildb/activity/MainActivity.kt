package com.example.cocktaildb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktaildb.R
import com.example.cocktaildb.adapter.CocktailAdapter
import com.example.cocktaildb.model.CocktailDBResponse
import com.example.cocktaildb.model.Filters
import com.example.cocktaildb.model.FiltersResponse
import com.example.cocktaildb.network.NetworkManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = CocktailAdapter()
    private val service = NetworkManager.createService()
    private var drinkList = ArrayList<Any>()

    companion object{
        var filters =  ArrayList<Filters>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        executeSearch()
        initRecyclerView()
    }

    private fun executeSearch(){
        if(filters.isEmpty())
            searchAllFilters()
        else
            searchCocktail(filters)
    }
    private fun initRecyclerView(){
        recyclerView = drinks_rv
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun searchAllFilters(){
        val callFilters = service.getFilters("list")
        callFilters.enqueue(object: Callback<FiltersResponse>{
            override fun onResponse(call: Call<FiltersResponse>, response: Response<FiltersResponse>) {
                val allFilters = response.body()!!.drinks as ArrayList<Filters>
                filters = allFilters
                searchCocktail(allFilters)
            }
            override fun onFailure(call: Call<FiltersResponse>, t: Throwable) {
            }
        })
    }

    private fun searchCocktail(filters: ArrayList<Filters>){
        filters.forEach {
          val callCocktails = service.getCocktails(it.strCategory)
          callCocktails.enqueue(object : Callback<CocktailDBResponse> {
              override fun onResponse(call: Call<CocktailDBResponse>, response: Response<CocktailDBResponse>) {
                  createCocktailList(response.body()!!, it.strCategory)
              }
              override fun onFailure(call: Call<CocktailDBResponse>, t: Throwable) {
                  Log.e("Main", t.toString())
              }
          })
        }
    }

    private fun createCocktailList(cocktailDBResponse: CocktailDBResponse, filter: String){
        drinkList.add(filter)
        cocktailDBResponse.drinks.forEach {
            drinkList.add(it)
        }
        adapter.setData(drinkList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filters -> {
                val intent = Intent(this, FilterActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}