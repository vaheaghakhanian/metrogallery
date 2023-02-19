package com.ramonapp.metgallery.presentation.objectdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ramonapp.metgallery.app.R
import com.ramonapp.metgallery.app.databinding.FragmentObjectDetailBinding
import com.ramonapp.metgallery.domain.model.ObjectModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ObjectDetailFragment : Fragment() {

    companion object {
        const val ARG_OBJECT_ID = "objectId"
    }

    private var _binding: FragmentObjectDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ObjectDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentObjectDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupObservers()
        if (savedInstanceState == null) {
            viewModel.getObject(arguments?.getInt(ARG_OBJECT_ID))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.detailToolbar.setOnBackOrCloseClick {
            activity?.onBackPressed()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.obj.collectLatest { obj ->
                if (obj == null) return@collectLatest
                setupDetail(obj)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.showLoading.collectLatest { show ->
                binding.progressBar.visibility = if (show) View.VISIBLE else View.INVISIBLE
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.showError.collectLatest { error ->
                MaterialAlertDialogBuilder(requireContext()).setMessage(getString(error))
                    .setPositiveButton("OK") { _, _ ->

                    }.show()
            }
        }
    }

    private fun setupDetail(detail: ObjectModel) {
        detail.primaryImage.takeUnless { it.isBlank() }?.let { img ->
            binding.largeImageImg.load(img) {
                placeholder(android.R.drawable.ic_menu_report_image)
            }
        } ?: apply { binding.largeImageImg.visibility = View.GONE }
        binding.objectNameTxt.text = getString(R.string.object_name_title, detail.objectName)
        binding.cultureTxt.text = getString(R.string.culture_title, detail.culture)
        binding.artistTxt.text = getString(R.string.artist_title, detail.artistName)
        binding.departmentTxt.text = getString(R.string.department_title, detail.department)
        binding.periodTxt.text = getString(R.string.period_title, detail.period)
        binding.galleryRcl.adapter = GalleryAdapter().apply { submitList(detail.additionalImages) }
    }

}