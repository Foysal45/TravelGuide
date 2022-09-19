package com.btb.explorebangladesh.presentation.fragments.home.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.btb.explorebangladesh.databinding.SimpleDistrictItemBinding
import com.btb.explorebangladesh.domain.model.District
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.PowerSpinnerInterface
import com.skydoves.powerspinner.PowerSpinnerView

class DistrictAdapter(
    private val powerSpinner: PowerSpinnerView
) : RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder>(),
    PowerSpinnerInterface<District> {

    companion object {
        private const val NO_SELECTED_INDEX = -1
    }

    private val districts = mutableListOf<District>()

    override var index: Int = powerSpinner.selectedIndex
    override var onSpinnerItemSelectedListener: OnSpinnerItemSelectedListener<District>? = null
    override val spinnerView: PowerSpinnerView = powerSpinner

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictViewHolder {
        return DistrictViewHolder(
            SimpleDistrictItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DistrictViewHolder, position: Int) {
        val district = districts[position]
        holder.binding.tvDistrictName.text = district.name
        holder.binding.root.setOnClickListener {
            notifyItemSelected(position)
        }
    }

    override fun getItemCount() = if (districts.isNotEmpty()) districts.size else 0


    inner class DistrictViewHolder(
        val binding: SimpleDistrictItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun notifyItemSelected(index: Int) {
        if (index == NO_SELECTED_INDEX) return
        val oldIndex = this.index
        this.index = index
        this.powerSpinner.notifyItemSelected(index, districts[index].name)
        this.onSpinnerItemSelectedListener?.onItemSelected(
            oldIndex = oldIndex,
            oldItem = oldIndex.takeIf { it != NO_SELECTED_INDEX }?.let { districts[oldIndex] },
            newIndex = index,
            newItem = districts[index]
        )
    }

    override fun setItems(itemList: List<District>) {
        districts.clear()
        districts.addAll(itemList)
    }


}