package com.example.week2_0706012110057.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.week2_0706012110057.Interface.cardListener
import com.example.week2_0706012110057.Model.Animal
import com.example.week2_0706012110057.R
import com.example.week2_0706012110057.databinding.AnimalistBinding

class ListdataRVadapter (val listanimal : ArrayList<Animal>, val cardListener: cardListener) :
    RecyclerView.Adapter<ListdataRVadapter.viewHolder>() {


    class viewHolder(val itemView: View, val cardListener: cardListener) :
        RecyclerView.ViewHolder(itemView) {

        val binding = AnimalistBinding.bind(itemView)

        fun setData(data: Animal) {
            binding.textName.text= data.name
            binding.textType.text = "Type = " + data.type
            binding.textOld.text = "Old = " + data.age.toString()

            if (data.imageUri.isNotEmpty()) {
                binding.imageView.setImageURI(Uri.parse(data.imageUri))
            }
            binding.button2.setOnClickListener {
                cardListener.onEditClick(adapterPosition)
            }
            binding.button3.setOnClickListener {

                //remove
                cardListener.onDeleteClick(adapterPosition)
            }
            binding.button4.setOnClickListener {
                Toast.makeText(itemView.context, data.sound, Toast.LENGTH_SHORT).show();
            }
            binding.button5.setOnClickListener {
                Toast.makeText(itemView.context, data.eat, Toast.LENGTH_SHORT).show();
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.animalist, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listanimal[position])
    }

    override fun getItemCount(): Int {
        return listanimal.size
    }
}
