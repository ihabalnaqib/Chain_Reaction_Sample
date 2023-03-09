package com.chainreaction.sample.view.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cainreaction.sample.databinding.InventoryItemBinding
import com.chainreaction.sample.model.model.InventoryModel
import com.chainreaction.sample.model.utils.INVENTORY_ITEM
import com.chainreaction.sample.view.interfaces.OnAdapterItemClicked

class InventoryAdapter(private val onAdapterItemClicked: OnAdapterItemClicked) :
    RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    private var mInventoryData: ArrayList<InventoryModel> = arrayListOf()

    fun setInventories(mInventoryData: List<InventoryModel>?) {
        if (mInventoryData.isNullOrEmpty())
            return
        this.mInventoryData.addAll(mInventoryData)
        notifyItemRangeInserted(this.mInventoryData.size - 1, mInventoryData.size)

    }

    fun clearData() {
        mInventoryData.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder =

        InventoryViewHolder(
            InventoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        holder.bind(mInventoryData[position])
    }

    override fun getItemCount() = mInventoryData.size

    inner class InventoryViewHolder(private val view: InventoryItemBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(data: InventoryModel) {

            view.data = data

            view.root.setOnClickListener {
                onAdapterItemClicked.onItemClicked(INVENTORY_ITEM, data)
            }
        }

    }
}