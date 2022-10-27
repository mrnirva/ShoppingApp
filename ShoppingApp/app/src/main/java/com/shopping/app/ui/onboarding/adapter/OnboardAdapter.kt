package com.shopping.app.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shopping.app.data.model.OnBoardModel
import com.shopping.app.databinding.ItemOnboardingBinding

class OnboardAdapter(private val onboardList: List<OnBoardModel>) :
    RecyclerView.Adapter<OnboardAdapter.OnboardViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardViewHolder {
        return OnboardViewHolder(
            ItemOnboardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: OnboardViewHolder, position: Int) {
        holder.bind(onboardList[position])
    }

    override fun getItemCount(): Int {
        return onboardList.size
    }

    inner class OnboardViewHolder(private val bnd: ItemOnboardingBinding) : RecyclerView.ViewHolder(bnd.root){

        fun bind(onBoardModel: OnBoardModel){
            bnd.dataHolder = onBoardModel
            bnd.executePendingBindings()
        }

    }

}