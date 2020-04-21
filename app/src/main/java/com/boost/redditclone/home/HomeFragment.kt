package com.boost.redditclone.home

import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.boost.redditclone.R
import com.boost.redditclone.model.TopicModel
import com.boost.redditclone.databinding.HomeFragmentBinding

/*
* Main Fragment, first fragment to show when app run
* contain list of topics
* */
class HomeFragment : Fragment() {

    lateinit var viewModel: HomeViewModel // viewmodel to hold data
    private lateinit var binding: HomeFragmentBinding //viewbinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = HomeFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val topicListAdapter =
            TopicListAdapter(viewModel)
        topicListAdapter.submitList(viewModel.topicModelList.value?.toMutableList())

        binding.rvHome.adapter = topicListAdapter

        viewModel.topicModelList.observe(viewLifecycleOwner, Observer {
            topicListAdapter.submitList(it.toMutableList())
            topicListAdapter.notifyDataSetChanged()
        })

        var addTopicDialog = getAddTopicDialog()
        binding.btnAddTopic.setOnClickListener {

            addTopicDialog.show()
        }

    }

    //create ui to add topic
    fun getAddTopicDialog(): AlertDialog {
        val builder = AlertDialog.Builder(this.requireContext())
//            builder.setView(R.layout.fragment_add_topic)
        val edittext = EditText(this.requireContext())
        edittext.id = View.generateViewId()
        var id = edittext.id
        var btnAddTopic = R.id.btn_add_topic
        edittext.filters = arrayOf<InputFilter>(LengthFilter(255))
        builder.setView(edittext)
        builder.setMessage("Enter topic to be inserted")
        builder.setPositiveButton("Submit") { dialog, which ->
            val topic = edittext.text.toString().trim()
            if (topic.isNotEmpty()) {
                TopicModel.topicModelList.add(
                    TopicModel(topic, 0, 0)
                )
                viewModel.topicModelList.postValue(TopicModel.getTopicList())
            } else {
                Toast.makeText(
                    this.requireContext(),
                    "Failure. Please input something",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return builder.create()
    }
}