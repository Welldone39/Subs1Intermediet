package com.wildan.subs1intermediet.ui.Fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.wildan.subs1intermediet.base.BaseFragment
import com.wildan.subs1intermediet.data.config.ItemStory
import com.wildan.subs1intermediet.databinding.FragmentDetailStoryBinding


class FragmentDetailStory : BaseFragment<FragmentDetailStoryBinding>(){
    private var storyItem: ItemStory? = null
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailStoryBinding = FragmentDetailStoryBinding.inflate(inflater, container, false)

    override fun initIntent() {
        storyItem = FragmentDetailStoryArgs.fromBundle(requireArguments()).story
    }

    override fun initUI() {
        with(binding) {
            tvDetailDescription.text = storyItem?.description
            tvDetailName.text= storyItem?.name
            tvDetailPhoto.load(storyItem?.photoUrl)
        }
    }

    override fun initAction() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initProcess() {
       //
    }

    override fun initObserver() {
        //
    }

}