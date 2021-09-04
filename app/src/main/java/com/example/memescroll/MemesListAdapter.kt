package com.example.memescroll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MemesListAdapter( private val listener : MemeItemClicked) : RecyclerView.Adapter<MemeViewHolder>() {

    private val items : ArrayList<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_memes, parent, false)
        val viewHolder : MemeViewHolder = MemeViewHolder(view)


        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val currentItem = items[position]

        Glide.with(holder.itemView.context).load(currentItem).into(holder.image)


    }
    fun updateNews( updatedNews : ArrayList<String>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }


}

class MemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val image : ImageView = itemView.findViewById<ImageView>(R.id.image)


}

interface MemeItemClicked{
    fun onItemClicked(item:String)
}
