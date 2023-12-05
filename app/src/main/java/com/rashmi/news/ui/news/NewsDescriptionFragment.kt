package com.rashmi.news.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rashmi.news.R
import com.rashmi.news.databinding.FragmentNewsDescriptionBinding
import com.rashmi.news.ui.viewmodel.SharedViewModel

class NewsDescriptionFragment : Fragment() {
    private var _binding: FragmentNewsDescriptionBinding? = null
    private val binding get() = _binding!!


    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedNewsItem.observe(viewLifecycleOwner, Observer { selectedNewsItem ->
            if (selectedNewsItem != null) {
                Glide.with(requireActivity())
                    .load(selectedNewsItem.urlToImage)
                    .placeholder(R.drawable.ic_menu_camera)
                    .into(binding.newsDetailImage)
                binding.newsDetailTitle.text = selectedNewsItem.title
                binding.newsDetailContent.text = selectedNewsItem.content
                binding.newsDetailUrl.text = selectedNewsItem.url
            }
        })
    }

    fun onUrlClicked(view: View) {
        val url = (view as TextView).text.toString()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
