package com.example.googlemapsexamples.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.googlemapsexamples.R
import com.example.googlemapsexamples.application.MyApplication
import com.example.googlemapsexamples.home.model.MenuModel
import kotlinx.android.synthetic.main.inflate_menu_item.view.*


class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private var arrayList: ArrayList<MenuModel> = ArrayList()
    private var menuCommunicator: MenuCommunicator? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.inflate_menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.onBindItems(holder.adapterPosition)
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBindItems(position: Int) {
            val menuModel = arrayList[position]
            itemView.tvMenuIcon.text =
                MyApplication.getApplicationContext().getString(menuModel.menuIcon)
            itemView.tvMenuTitle.text = menuModel.menuTitle
            itemView.setOnClickListener {
                menuCommunicator?.menuClick(menuModel.menuId)
            }
            if (position == itemCount - 1) {
                itemView.viewMenuBottom.visibility = View.INVISIBLE
            } else {
                itemView.viewMenuBottom.visibility = View.VISIBLE
            }
        }
    }

    fun addList(menuList: ArrayList<MenuModel>) {
        this.arrayList.addAll(menuList)
        notifyDataSetChanged()
    }

    fun setOnMenuItemClickListener(menuCommunicator: MenuCommunicator) {
        this.menuCommunicator = menuCommunicator
    }

    interface MenuCommunicator {
        fun menuClick(menuId: Int)
    }
}