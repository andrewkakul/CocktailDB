package com.example.cocktaildb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktaildb.R
import com.example.cocktaildb.model.Cocktails
import kotlinx.android.synthetic.main.item_title.view.*
import kotlinx.android.synthetic.main.item_cocktail.view.*

class CocktailAdapter(): RecyclerView.Adapter<CocktailAdapter.BaseViewHolder>() {

    companion object{
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }

    private var CocktailList = ArrayList<Any>()

    abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when(viewType){
            TYPE_HEADER ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_title, parent, false)
                TitleViewHolder(view)
            }
            TYPE_CONTENT ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cocktail, parent, false)
                ContentViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (CocktailList[position]) {
            is String -> TYPE_HEADER
            is Cocktails -> TYPE_CONTENT
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val element = CocktailList[position]
        when (holder) {
            is TitleViewHolder -> holder.bind(element as String)
            is ContentViewHolder -> holder.bind(element as Cocktails)
            else -> throw IllegalArgumentException()
        }
    }

    class TitleViewHolder(itemView: View) : BaseViewHolder(itemView) {
        fun bind(content: String) = with(itemView) {
            content.let {
                title.text = it
            }
        }
    }

    class ContentViewHolder(itemView: View) : BaseViewHolder(itemView) {
        fun bind(content: Cocktails) = with(itemView) {
            content.let {
                Glide.with(itemView.context)
                        .load(it.strDrinkThumb)
                        .into(cocktail_image)
            }
            cocktail_name.text = content.strDrink
        }
    }

    override fun getItemCount(): Int {
        return CocktailList.size
    }

    fun setData(cocktailList: ArrayList<Any>){
        CocktailList = cocktailList
        notifyDataSetChanged()
    }

}