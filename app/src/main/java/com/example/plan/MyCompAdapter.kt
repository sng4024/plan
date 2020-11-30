package com.example.plan

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plan.databinding.Item2Binding
class MyCompViewHolder(val binding: Item2Binding):RecyclerView.ViewHolder(binding.root){
}

class MyCompAdapter(val context: Context, private var items: List<Habit>)
    :RecyclerView.Adapter<MyCompViewHolder>(){

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCompViewHolder{

        val inflater=LayoutInflater.from(parent.context)
        val binding:Item2Binding= Item2Binding.inflate(inflater,parent,false)
        return MyCompViewHolder(binding)
    }

    override fun onBindViewHolder(holder:MyCompViewHolder, position: Int) {
        val item=items[position]
        holder.binding.comphabitName.text=item.habit
        holder.binding.comphabitName.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG or holder.binding.comphabitName.paintFlags
        holder.binding.comphabitName.setOnClickListener{
            itemClickListener?.onClick((item.habit_id))
        }

    }

    override fun getItemCount():Int { return items.size}


}