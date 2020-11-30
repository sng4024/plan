package com.example.plan

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan.databinding.ItemBinding
class MyViewHolder(val binding: ItemBinding):RecyclerView.ViewHolder(binding.root){
}

class MyAdapter(val context: Context, private var items: List<Habit>)
    :RecyclerView.Adapter<MyViewHolder>(){

    interface OnItemClickListener{
       fun onClick(habit_id:Int)
    }
    private var itemClickListener:OnItemClickListener?=null

    fun setOnItemClickListener(listener:OnItemClickListener){
        itemClickListener=listener
    }

    fun updateList(newList:List<Habit>){
        items=newList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{

        val inflater=LayoutInflater.from(parent.context)
        val binding:ItemBinding= ItemBinding.inflate(inflater,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val item=items[position]
        holder.binding.habitName.text=item.habit
        holder.binding.habitName.setOnClickListener{
            itemClickListener?.onClick((item.habit_id))
        }

    }

    override fun getItemCount():Int { return items.size}


}