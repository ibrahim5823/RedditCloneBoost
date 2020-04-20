package com.boost.redditclone

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.boost.redditclone.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = HomeFragmentBinding.bind(view);
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val topicListAdapter = TopicListAdapter(viewModel)
        topicListAdapter.submitList(viewModel.topicContentList.value?.toMutableList())


        binding.rvHome.adapter= topicListAdapter

        viewModel.topicContentList.observe(viewLifecycleOwner, Observer {
            topicListAdapter.submitList(it.toMutableList())
            topicListAdapter.notifyDataSetChanged()
            Log.d("TAG", "itemcoint2: " + topicListAdapter.itemCount)
        })

        viewModel.topicContentList.postValue(viewModel.getExTopicContentList())
        Log.d("TAG", "itemcount1: " + topicListAdapter.itemCount)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // TODO: Use the ViewModel
    }

}