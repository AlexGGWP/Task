package com.tryzens.task.viewmodels

import android.app.Activity
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.tryzens.task.rest.NetworkResponseHandler
import com.tryzens.task.rest.data.RepositoriesData
import com.tryzens.task.rest.fetcher.BaseFetcher
import com.tryzens.task.ui.MainActivity
import com.tryzens.task.ui.dialog.CustomErrorDialog
import com.tryzens.task.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class RepositoryViewModel(private val fetcher: BaseFetcher) : ViewModel() {

    val showLoading = ObservableBoolean()
    val listOfRepositories = MutableLiveData<List<RepositoriesData>>()
    val showError = SingleLiveEvent<Boolean>()

    fun getRepositories(activity: Activity) {
        showLoading.set(true)
        //we use launch because we are handling the result by NetworkResponseHandler thus we don't care whether it returns a result or not
        viewModelScope.launch {
            val result = fetcher.getAllRepositories()
            showLoading.set(false)
            when (result) {
                is NetworkResponseHandler.Success -> {
                    listOfRepositories.value = result.successData!!
                    showError.value = false
                }
                is NetworkResponseHandler.Error -> {
                    showError.value = true
                    result.exception.message?.let {
                        CustomErrorDialog().showErrorDialog(
                            activity as MainActivity,
                            it
                        )
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun ImageView.loadImageFromURL(imgURL: String) {
            Glide.with(this)
                .load(imgURL)
                .into(this)
        }
    }
}