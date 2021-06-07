package com.tryzens.task.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tryzens.task.R
import com.tryzens.task.databinding.FragmentRepoDetailsBinding
import com.tryzens.task.rest.data.RepositoriesData
import com.tryzens.task.viewmodels.RepositoryViewModel.Companion.loadImageFromURL
import kotlinx.android.synthetic.main.fragment_repo_details.*

/**
 * Just a simple class to show the some details of the selected repository. I know it was not necessary but it looked to me a bit
 * dull to show only the list and not demonstrate that the list is clickable.
 */
class RepoDetailsFragment : Fragment() {
    companion object {
        fun newInstance(repositoriesData: RepositoriesData) = RepoDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable("repository_details_data", repositoriesData)
            }
        }
    }

    private var repositoryDetails: RepositoriesData? = null
    private lateinit var mViewDataBinding: FragmentRepoDetailsBinding
    private var backArrow: ImageView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        repositoryDetails = arguments?.getParcelable("repository_details_data")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_repo_details, container, false
        )
        val mRootView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewDataBinding.repository = repositoryDetails
        repositoryDetails?.owner?.let { repo_img.loadImageFromURL(it.avatar_url) }

        backArrow = activity?.findViewById(R.id.back_arrow_button)
        if (backArrow != null) {
            (backArrow ?: return).visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        backArrow?.visibility = View.GONE
        val searchIcon = activity?.findViewById<ImageView>(R.id.search_icon)
        if (searchIcon != null) {
            searchIcon.visibility = View.VISIBLE
        }
        super.onDestroy()
    }
}