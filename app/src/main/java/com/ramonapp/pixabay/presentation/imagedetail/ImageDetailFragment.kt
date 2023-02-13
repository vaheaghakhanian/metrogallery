package com.ramonapp.pixabay.presentation.imagedetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ramonapp.pixabay.app.R
import com.ramonapp.pixabay.app.databinding.FragmentImageDetailBinding
import com.ramonapp.pixabay.app.databinding.FragmentImageListBinding
import com.ramonapp.pixabay.component.GeneralToolbar
import com.ramonapp.pixabay.domain.model.ImageModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ImageDetailFragment : Fragment() {

    companion object {
        const val ARG_IMAGE_MODEL = "argImage"
    }

    private var _binding: FragmentImageDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupDetail()
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

    @SuppressLint("SetTextI18n")
    private fun setupDetail() {
        arguments?.getParcelable<ImageModel>(ARG_IMAGE_MODEL)?.apply {
            binding.largeImageImg.load(largeImageUrl) {
                placeholder(android.R.drawable.ic_menu_report_image)
            }
            binding.userTxt.text = "User:  $user"
            binding.tagsTxt.text = "Tags:  $tags"
            binding.likeTxt.text = "Likes:  $likeCount"
            binding.commentTxt.text = "Comments:  $commentCount"
            binding.downloadTxt.text = "Downloads:  $downloadCount"
        }
    }

}