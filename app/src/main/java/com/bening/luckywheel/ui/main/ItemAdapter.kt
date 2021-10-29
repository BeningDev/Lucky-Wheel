package com.bening.luckywheel.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bening.luckywheel.data.DataItem
import com.bening.luckywheel.databinding.ListItemBinding
import com.oratakashi.viewbinding.core.binding.recyclerview.ViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.viewBinding
import com.oratakashi.viewbinding.core.tools.onClick

class ItemAdapter: RecyclerView.Adapter<ViewHolder<ListItemBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ListItemBinding> = viewBinding(parent)

    override fun onBindViewHolder(holder: ViewHolder<ListItemBinding>, position: Int) {
        with(holder.binding) {
            tvTitle.text = data[position].nama
            btnDelete.onClick {
                delete(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun delete(data: DataItem){
        this.data.remove(data)
        notifyDataSetChanged()
    }

    fun addData(data: DataItem) {
        this.data.add(data)
        notifyDataSetChanged()
    }

    fun getList() : ArrayList<DataItem> {
        return ArrayList(this.data)
    }

    val data: MutableList<DataItem> by lazy {
        ArrayList()
    }
}