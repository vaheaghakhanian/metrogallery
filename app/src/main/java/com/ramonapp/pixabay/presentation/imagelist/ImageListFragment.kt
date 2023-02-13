package com.ramonapp.pixabay.presentation.imagelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ramonapp.pixabay.app.R
import com.ramonapp.pixabay.app.databinding.FragmentImageListBinding
import com.ramonapp.pixabay.component.GeneralToolbar
import com.ramonapp.pixabay.extension.safeNavigate
import com.ramonapp.pixabay.presentation.imagedetail.ImageDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ImageListFragment : Fragment() {

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ImageListViewModel by viewModels()
    private var adapter = ImageListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
        setupToolbar()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListAdapter() {
        binding.imageListRcl.adapter = adapter
        adapter.setOnItemClick { imageModel ->
            MaterialAlertDialogBuilder(requireContext())
                .setMessage("Do you want to open this Image?")
                .setPositiveButton("OK") { _, _ ->
                    safeNavigate(R.id.action_imageListFragment_to_imageDetailFragment,
                        Bundle().apply {
                            putParcelable(ImageDetailFragment.ARG_IMAGE_MODEL, imageModel)
                        }
                    )
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun setupToolbar() {
        binding.listToolbar.setOnBackOrCloseClick {
            activity?.finishAfterTransition()
        }
        binding.listToolbar.setOnSearchListener(object : GeneralToolbar.IToolbarSearchListener {
            override fun onQueryChanged(query: String) {
                viewModel.fetchImages(query)
            }

            override fun onSearchStateChanged(isOpen: Boolean) {
            }

        })
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.imageList.collectLatest { list ->
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
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}