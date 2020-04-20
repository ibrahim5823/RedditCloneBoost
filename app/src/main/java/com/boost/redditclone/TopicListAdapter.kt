package com.boost.redditclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.boost.redditclone.TopicListAdapter.TopicListViewHolder
import com.boost.redditclone.databinding.ItemTopicContentBinding

class TopicListAdapter(viewModel: HomeViewModel) :
    ListAdapter<TopicContent, TopicListViewHolder>(ListItemCallback()) {
    private var viewModel = viewModel

    class ListItemCallback : DiffUtil.ItemCallback<TopicContent>() {
        override fun areItemsTheSame(oldItem: TopicContent, newItem: TopicContent): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TopicContent, newItem: TopicContent): Boolean {
            return oldItem.topic == newItem.topic
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_topic_content, parent, false)
        return TopicListViewHolder(view, viewModel)
    }

    inner class TopicListViewHolder(view: View, viewModel: HomeViewModel) :
        RecyclerView.ViewHolder(view) {
        var binding = ItemTopicContentBinding.bind(view)
        fun setData(item: TopicContent, position: Int) {
            binding.tvTopic.text = item.topic
            binding.tvContent.text = item.content.take(50)
            binding.tvUpNum.text = item.upvote.toString()
            binding.tvDownNum.text = item.downvote.toString()

            binding.clTopic.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("topic", item.topic)
                bundle.putString("content", item.content)
                bundle.putInt("upvote", item.upvote)
                bundle.putInt("downvote", item.downvote)
                Navigation.findNavController(itemView)
                    .navigate(R.id.action_homeFragment_to_topicDetailFragment, bundle)
            }

            binding.imgUp.setOnClickListener {
                viewModel.addUpvote(item)
            }
        }

    }

    override fun onBindViewHolder(holder: TopicListViewHolder, position: Int) {
        holder.setData(getItem(position), position)
    }


    override fun submitList(list: MutableList<TopicContent>?) {
        super.submitList(list?.sortedBy { topicContent -> topicContent.upvote })
    }
}

