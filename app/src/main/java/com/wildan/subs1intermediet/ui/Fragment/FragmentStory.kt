package com.wildan.subs1intermediet.ui.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.wildan.subs1intermediet.R
import com.wildan.subs1intermediet.base.BaseFragment
import com.wildan.subs1intermediet.data.library.ApiResponse
import com.wildan.subs1intermediet.databinding.FragmentStoryBinding
import com.wildan.subs1intermediet.ui.Adapter.AdapterStory
import com.wildan.subs1intermediet.ui.ViewModel.ViewModelStory
import com.wildan.subs1intermediet.ui.views.LoadingDialogCustom
import com.wildan.subs1intermediet.utils.ManagerPreference
import org.koin.android.ext.android.inject
import androidx.recyclerview.widget.LinearLayoutManager
import com.shashank.sony.fancytoastlib.FancyToast

class FragmentStory : BaseFragment<FragmentStoryBinding>() {
    private val viewModelStory: ViewModelStory by inject()
    private val loadingDialog: LoadingDialogCustom by lazy { LoadingDialogCustom(requireActivity()) }
    private val prefs: ManagerPreference by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentStoryBinding =FragmentStoryBinding.inflate(inflater, container, false)

    override fun initIntent() {
        //
    }

    override fun initUI() {
        //
    }

    override fun initAction() {
       with(binding) {
            buttonAdd.setOnClickListener {
                val navigateAddStory = FragmentStoryDirections.homeActionFragmentToFragmentAddStory()
                findNavController().navigate(navigateAddStory)
            }
       }
    }

    override fun initProcess() {
        viewModelStory.get()
    }

    override fun initObserver() {
        viewModelStory.story.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ApiResponse.Loading -> loadingDialog.loadingDialogStart()
                is ApiResponse.Success -> {
                    val adapterStory = AdapterStory()
                    adapterStory.submitList(state.data.listStory)
                    binding.story.apply {
                        adapter = adapterStory
                        layoutManager = LinearLayoutManager(context)
                    }
                    loadingDialog.dialogDismiss()

                }
                is ApiResponse.Error -> {
                    loadingDialog.dialogDismiss()
                    FancyToast.makeText(requireContext(), state.errorMessage, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show()

                }else -> {
                    loadingDialog.dialogDismiss()
                    FancyToast.makeText(requireContext(), getString(R.string.unknown_error), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show()
                }
            }
        }
    }

}