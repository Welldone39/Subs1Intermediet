package com.wildan.subs1intermediet.ui.Fragment

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.shashank.sony.fancytoastlib.FancyToast
import com.wildan.subs1intermediet.R
import com.wildan.subs1intermediet.base.BaseFragment
import com.wildan.subs1intermediet.data.library.ApiResponse
import com.wildan.subs1intermediet.databinding.FragmentAddStoryBinding
import com.wildan.subs1intermediet.ui.ViewModel.ViewModelAddStory
import com.wildan.subs1intermediet.ui.views.LoadingDialogCustom
import com.wildan.subs1intermediet.utils.CameraHelper
import com.wildan.subs1intermediet.utils.disabled
import com.wildan.subs1intermediet.utils.enabled
import com.yalantis.ucrop.UCrop
import org.koin.android.ext.android.inject
import java.io.File
import java.util.Date

class FragmentAddStory : BaseFragment<FragmentAddStoryBinding>() {
    private var currentImageUri: Uri? = null
    private val viewModelAddStory: ViewModelAddStory by inject()
    private lateinit var dialogLoading: LoadingDialogCustom

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddStoryBinding? = FragmentAddStoryBinding.inflate(inflater, container, false)

    override fun initIntent() {
      //
    }

    override fun initUI() {
        dialogLoading = LoadingDialogCustom(requireActivity())
    }

    override fun initAction() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            btnCamera.setOnClickListener {
                currentImageUri = CameraHelper.getImageUri(requireContext())
                launcherCamera.launch(currentImageUri)
            }
            btnGallery.setOnClickListener {
                launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            buttonAdd.setOnClickListener {
                val description = binding.edAddDescription.text.toString()

                if (description.isNotEmpty() && currentImageUri != null) {
                    viewModelAddStory.uploadStory(currentImageUri!!, description)
                    buttonAdd.disabled()
                } else {
                    FancyToast.makeText(requireContext(), getString(R.string.please_fill_all_fields), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if(uri != null) {
            launchUCrop(uri)
        }
    }

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess->
        if(isSuccess) {
            launchUCrop(currentImageUri!!)
        }
    }

    private fun launchUCrop(uri: Uri) {
        val timestamp = Date().time
        val cachedImage = File(requireActivity().cacheDir, "cropped_image_${timestamp}.jpg")

        val destinationUri = Uri.fromFile(cachedImage)

        val uCrop = UCrop.of(uri, destinationUri).withAspectRatio(1f, 1f)

        uCrop.getIntent(requireContext()).apply {
            launcherUCrop.launch(this)
        }
    }

    private val launcherUCrop =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    currentImageUri = resultUri
                    setImagePreview()
                }
            }
        }

    private fun setImagePreview() {
        currentImageUri?.let {
            binding.imageStory.setImageURI(it)
        }
    }


    override fun initProcess() {
        //
    }

    override fun initObserver() {
        viewModelAddStory.resultAddStory.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ApiResponse.Loading -> dialogLoading.loadingDialogStart()
                is ApiResponse.Success -> {
                    dialogLoading.dialogDismiss()
                    findNavController().popBackStack()
                }
                is ApiResponse.Error -> {
                    dialogLoading.dialogDismiss()
                    binding.buttonAdd.enabled()
                    FancyToast.makeText(requireContext(), state.errorMessage, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()

                }
                else -> {
                    dialogLoading.dialogDismiss()
                    binding.buttonAdd.enabled()
                    FancyToast.makeText(requireContext(), getString(R.string.unknown_error), FancyToast.LENGTH_SHORT,  FancyToast.ERROR, false).show()

                }
            }
        }
    }
}