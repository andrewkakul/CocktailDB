package com.example.cocktaildb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktaildb.R
import com.example.cocktaildb.adapter.FiltersAdapter
import com.example.cocktaildb.adapter.OnItemListener
import com.example.cocktaildb.model.Filters
import com.example.cocktaildb.model.FiltersResponse
import com.example.cocktaildb.network.NetworkManager
import kotlinx.android.synthetic.main.activity_filter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilterActivity : AppCompatActivity(), OnItemListener{

    private var allFilters = ArrayList<Filters>()
    private lateinit var recyclerView: RecyclerView
    private val adapter = FiltersAdapter(this)
    var tmp = ArrayList<Filters>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        getAllFilters()

        recyclerView = filters_rv
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        apply_btn.setOnClickListener{
            MainActivity.filters = tmp

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getAllFilters(){
        val service = NetworkManager.createService()
        val callFilters = service.getFilters("list")
        callFilters.enqueue(object: Callback<FiltersResponse> {
            override fun onResponse(call: Call<FiltersResponse>, response: Response<FiltersResponse>) {
                allFilters = response.body()!!.drinks as ArrayList<Filters>
                adapter.setData(allFilters, MainActivity.filters)
            }
            override fun onFailure(call: Call<FiltersResponse>, t: Throwable) {
            }
        })
    }

    override fun onCheckBoxClicked(position: Int, filter: Filters, isChecked: Boolean) {
        if(isChecked)
            tmp.add(filter)
        else
            tmp.remove(filter)
    }
}