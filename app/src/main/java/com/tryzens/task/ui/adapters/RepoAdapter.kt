package com.tryzens.task.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tryzens.task.R
import com.tryzens.task.databinding.RepositoryListItemBinding
import com.tryzens.task.rest.data.RepositoriesData
import com.tryzens.task.viewmodels.RepositoryViewModel.Companion.loadImageFromURL

class RepoAdapter(val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<RepoAdapter.RepositoryViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(repository: RepositoriesData)
    }

    var repositoriesList: List<RepositoriesData> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryViewHolder {
        val binding: RepositoryListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.repository_list_item,
            parent,
            false
        )
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return repositoriesList.size
    }

    fun setRepositories(repositoryList: List<RepositoriesData>) {
        this.repositoriesList = repositoryList
        notifyDataSetChanged()
    }

    inner class RepositoryViewHolder(private val item: RepositoryListItemBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun onBind(position: Int) {
            val repository = repositoriesList[position]
            item.repository = repository
            item.repositoryImg.loadImageFromURL(repository.owner.avatar_url)
            item.itemClickInterface = clickListener
        }
    }
}