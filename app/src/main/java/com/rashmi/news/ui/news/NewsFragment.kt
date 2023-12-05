package com.rashmi.news.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rashmi.news.R
import com.rashmi.news.databinding.FragmentHomeBinding
import com.rashmi.news.model.ArticlesItem
import com.rashmi.news.ui.viewmodel.HomeViewModel
import com.rashmi.news.ui.viewmodel.SharedViewModel

class NewsFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        homeViewModel.news.observe(viewLifecycleOwner, Observer { newsList ->
            // println("in list " + newsList?.articles?.size)
            if (newsList?.articles?.isNotEmpty() == true) {
                _binding?.tblHomeNews?.removeAllViews()

                val newsAdapter = _binding?.tblHomeNews?.tag as? NewsAdapter
                    ?: NewsAdapter(newsList.articles as List<ArticlesItem>).apply {
                        _binding?.tblHomeNews?.tag = this
                    }

                for (i in 0 until newsAdapter.count) {
                    val tableRow = newsAdapter.getView(i, null, _binding?.tblHomeNews)
                    _binding?.tblHomeNews?.addView(tableRow)

                    tableRow.setOnClickListener {
                        val selectedNewsItem = newsList.articles[i]
                        sharedViewModel.selectedNewsItem.value = selectedNewsItem
                        navigateToNewsDescriptionFragment()
                    }
                }
            }
        })
        return root
    }

    private fun navigateToNewsDescriptionFragment() {
        findNavController().navigate(R.id.action_nav_home_to_newsDescriptionFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}