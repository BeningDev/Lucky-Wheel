package com.bening.luckywheel.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bening.luckywheel.data.DataWheel
import com.bening.luckywheel.databinding.ListWheelBinding
import com.oratakashi.viewbinding.core.binding.recyclerview.ViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.viewBinding
import com.oratakashi.viewbinding.core.tools.onClick

class WheelAdapter(
    private val onDelete: (DataWheel) -> Unit,
    private val onClick: (DataWheel) -> Unit,
    private val onEdit: (DataWheel) -> Unit
): RecyclerView.Adapter<ViewHolder<ListWheelBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ListWheelBinding> = viewBinding(parent)

    override fun onBindViewHolder(holder: ViewHolder<ListWheelBinding>, position: Int) {
        with(holder.binding) {
            tvTitle.text = data[position].nama
            btnDelete.onClick {
                onDelete.invoke(data[position])
                delete(data[position])
            }

            btnEdit.onClick {
                onEdit.invoke(data[position])
            }

            root.onClick {
                onClick.invoke(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    fun addData(data: DataWheel) {
        this.data.add(data)
        notifyDataSetChanged()
    }

    fun clear() {
        this.data.clear()
    }

    fun getList() : ArrayList<DataWheel> {
        return ArrayList(this.data)
    }

    fun delete(data: DataWheel) {
        this.data.remove(data)
        notifyDataSetChanged()
    }

    val data: MutableList<DataWheel> by lazy {
        ArrayList()
    }
}