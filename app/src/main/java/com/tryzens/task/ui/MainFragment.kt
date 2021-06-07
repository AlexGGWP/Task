package com.tryzens.task.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tryzens.task.R
import com.tryzens.task.databinding.FragmentMainBinding
import com.tryzens.task.rest.data.RepositoriesData
import com.tryzens.task.ui.adapters.RepoAdapter
import com.tryzens.task.utils.addAndReplaceFragment
import com.tryzens.task.utils.filterList
import com.tryzens.task.viewmodels.RepositoryViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import net.cachapa.expandablelayout.ExpandableLayout
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : Fragment(), RepoAdapter.OnItemClickListener {

    private val repositoryViewModel by viewModel<RepositoryViewModel>()
    private lateinit var repoAdapter: RepoAdapter
    private lateinit var mViewDataBinding: FragmentMainBinding
    private var unSortedList: List<RepositoriesData>? = null
    private var listLoaded: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main, container, false
        )
        val mRootView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchView = activity?.findViewById<EditText>(R.id.search_tv)

        setData()
        mViewDataBinding.viewModel = repositoryViewModel

        //Avoid loading executing the rest call multiple times
        if (!listLoaded) {
            repositoryViewModel.getRepositories(requireActivity())
        }

        repositoryViewModel.listOfRepositories.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty() && it != null) {
                repoAdapter.setRepositories(it)
                unSortedList = it
                listLoaded = true
            }
        })

        //Add a text change listener to filter the list of repository
        searchView?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.isNotEmpty()) {
                    unSortedList?.let { filterList(s.toString(), it) }?.let {
                        repoAdapter.setRepositories(it)
                    }
                }
            }
        })
    }

    private fun setData() {
        repoAdapter = RepoAdapter(this)
        repo_recycler_view.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

        repo_recycler_view.adapter = repoAdapter
        repo_recycler_view.isNestedScrollingEnabled = true
        repo_recycler_view.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onItemClick(repository: RepositoriesData) {

        //Hide search icon from toolbar. Nothing to filter there
        val searchIcon = activity?.findViewById<ImageView>(R.id.search_icon)
        if (searchIcon != null) {
            searchIcon.visibility = View.GONE
        }

        //make sure that the expanded layout is hidden while transitioning to the details screen
        val expandableView = activity?.findViewById<ExpandableLayout>(R.id.expandable_view)
        if (expandableView?.isExpanded ?: return) {
            searchIcon?.setImageResource(R.drawable.ic_search_white)
            expandableView.toggle()
        }

        (activity as MainActivity).addAndReplaceFragment(
            RepoDetailsFragment.newInstance(repository),
            R.id.fragment_container, RepoDetailsFragment.javaClass.simpleName
        )
    }
}