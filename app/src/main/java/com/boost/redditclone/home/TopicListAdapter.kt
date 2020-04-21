package com.boost.redditclone.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.boost.redditclone.R
import com.boost.redditclone.model.TopicModel
import com.boost.redditclone.home.TopicListAdapter.TopicListViewHolder
import com.boost.redditclone.databinding.ItemTopicContentBinding

/*
* Adapter for recyclerview in HomeFragment to show list of topics
* */

class TopicListAdapter(viewModel: HomeViewModel) :
    ListAdapter<TopicModel, TopicListViewHolder>(
        ListItemCallback()
    ) {
    val viewModel = viewModel

    class ListItemCallback : DiffUtil.ItemCallback<TopicModel>() {
        override fun areItemsTheSame(oldItem: TopicModel, newItem: TopicModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TopicModel, newItem: TopicModel): Boolean {
            return oldItem.topic == newItem.topic
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_topic_content, parent, false)
        return TopicListViewHolder(view)
    }

    inner class TopicListViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var binding = ItemTopicContentBinding.bind(view)
        fun setData(item: TopicModel, position: Int) {
            if (item.topic.length > 50)
                binding.tvTopic.text = item.topic.take(50).plus("...")
            else
                binding.tvTopic.text = item.topic
            binding.tvUpNum.text = item.upvote.toString()
            binding.tvDownNum.text = item.downvote.toString()

            binding.clTopic.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("topic", item.topic)
                bundle.putInt("upvote", item.upvote)
                bundle.putInt("downvote", item.downvote)
                Navigation.findNavController(itemView)
                    .navigate(R.id.action_homeFragment_to_topicDetailFragment, bundle)
            }

            binding.imgUp.setOnClickListener {
                TopicModel.addUpVote(item)
                viewModel.topicModelList.postValue(TopicModel.getTopicList())
            }
            binding.imgDown.setOnClickListener {
                TopicModel.addDownVote(item)
                viewModel.topicModelList.postValue(TopicModel.getTopicList())
            }
        }

    }

    override fun onBindViewHolder(holder: TopicListViewHolder, position: Int) {
        holder.setData(getItem(position), position)
    }


    override fun submitList(list: MutableList<TopicModel>?) {
        super.submitList(list?.sortedByDescending { topicContent -> topicContent.upvote }?.take(20))
    }
}

