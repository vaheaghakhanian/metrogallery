package com.ramonapp.metgallery.presentation.objectlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ramonapp.metgallery.app.R
import com.ramonapp.metgallery.app.databinding.FragmentObjectListBinding
import com.ramonapp.metgallery.component.GeneralToolbar
import com.ramonapp.metgallery.extension.safeNavigate
import com.ramonapp.metgallery.presentation.objectdetail.ObjectDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ObjectListFragment : Fragment() {

    private var _binding: FragmentObjectListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ObjectListViewModel by viewModels()
    private var adapter = ObjectAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentObjectListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        setupToolbar()
        setupObservers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.fetchObjects()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListAdapter() {
        binding.objectRcl.adapter = adapter
        binding.objectRcl.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.HORIZONTAL
            )
        )
        binding.objectRcl.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        adapter.setOnItemClick { id ->
            safeNavigate(R.id.action_objectListFragment_to_objectDetailFragment, Bundle().apply {
                putInt(ObjectDetailFragment.ARG_OBJECT_ID, id)
            })
        }
    }

    private fun setupToolbar() {
        binding.listToolbar.setOnBackOrCloseClick {
            activity?.finishAfterTransition()
        }
        binding.listToolbar.setOnSearchListener(object : GeneralToolbar.IToolbarSearchListener {
            override fun onQueryChanged(query: String) {
                viewModel.fetchObjects(query)
            }

            override fun onSearchStateChanged(isOpen: Boolean) {
            }

        })
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.objectList.collectLatest { list ->
                adapter.submitList(list)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.showLoading.collectLatest { show ->
                binding.progressBar.visibility = if (show) View.VISIBLE else View.INVISIBLE
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.showError.collectLatest { error ->
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage(getString(error))
                    .setPositiveButton("OK") { _, _ ->

                    }
                    .show()
            }
        }
    }
}