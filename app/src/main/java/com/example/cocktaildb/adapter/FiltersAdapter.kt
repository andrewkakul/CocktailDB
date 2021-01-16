package com.example.cocktaildb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktaildb.R
import com.example.cocktaildb.model.Filters

class FiltersAdapter(): RecyclerView.Adapter<FiltersAdapter.ViewHolder>() {

    constructor(onItemListener: OnItemListener) : this(){
        this.onItemListener = onItemListener
    }

    private var filtersList = ArrayList<Filters>()
    private var curentFiltersList = ArrayList<Filters>()
    private lateinit var onItemListener: OnItemListener

    class ViewHolder(view: View, onItemListener: OnItemListener) : RecyclerView.ViewHolder(view),
        CompoundButton.OnCheckedChangeListener {
        private var filterName: TextView? = null
        private var checkBox: CheckBox? =  null
        private var onItemListener: OnItemListener
        private lateinit var filterTMP: Filters

        init {
            filterName = itemView.findViewById(R.id.filter_name)
            checkBox = itemView.findViewById(R.id.filter_checkbox)
            this.onItemListener = onItemListener
            checkBox?.setOnCheckedChangeListener(this)
        }

        fun bind(filter: Filters, curentFilters: ArrayList<Filters>){
            filterName?.text = filter.strCategory
            filterTMP = filter
            curentFilters.forEach {
               if (filter == it)
                   checkBox?.isChecked = true
            }
        }

        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            onItemListener.onCheckBoxClicked(adapterPosition, filterTMP, isChecked)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        return ViewHolder(itemView, onItemListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filtersList[position], curentFiltersList)
    }

    override fun getItemCount(): Int {
      return filtersList.size
    }

    fun setData(allFiltersList: ArrayList<Filters>, curentFilters: ArrayList<Filters>) {
        filtersList = allFiltersList
        curentFiltersList = curentFilters
        notifyDataSetChanged()
    }
}

    interface OnItemListener{
        fun onCheckBoxClicked(position: Int, filter: Filters, isChecked: Boolean)
    }