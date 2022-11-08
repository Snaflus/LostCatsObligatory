package com.example.lostcats.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lostcats.R

class CatsAdapter(
    private val items: List<Cat>,
    private val onItemClicked: (position: Int) -> Unit) :
    RecyclerView.Adapter<CatsAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_cat, viewGroup, false)
        return MyViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        //viewHolder.textViewCatId.text = items[position].id.toString() //int
        viewHolder.textViewCatName.text = items[position].name //string
        viewHolder.textViewCatDesc.text = items[position].description //string
        viewHolder.textViewCatPlace.text = items[position].place //string
        viewHolder.textViewCatReward.text = items[position].reward.toString() //int
        viewHolder.textViewCatUserId.text = items[position].userId //string
        viewHolder.textViewCatDate.text = items[position].humanDate() //string
//        if (items[position].pictureUrl == "") {
//            set to fallback image
//        } else {
//            viewHolder.imageViewCatPicture.drawable = items[position].pictureUrl //string
//        }
    }

    class MyViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewCatId: TextView = itemView.findViewById(R.id.list_cat_id)
        val textViewCatName: TextView = itemView.findViewById(R.id.list_cat_name)
        val textViewCatDesc: TextView = itemView.findViewById(R.id.list_cat_desc)
        val textViewCatPlace: TextView = itemView.findViewById(R.id.list_cat_place)
        val textViewCatReward: TextView = itemView.findViewById(R.id.list_cat_reward)
        val textViewCatUserId: TextView = itemView.findViewById(R.id.list_cat_userid)
        val textViewCatDate: TextView = itemView.findViewById(R.id.list_cat_date)
        val imageViewCatPicture: ImageView = itemView.findViewById(R.id.list_cat_picture)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }
}