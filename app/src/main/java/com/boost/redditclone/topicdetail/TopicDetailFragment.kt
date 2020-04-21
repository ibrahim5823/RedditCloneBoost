package com.boost.redditclone.topicdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.boost.redditclone.R
import com.boost.redditclone.model.TopicModel
import com.boost.redditclone.databinding.FragmentTopicDetailBinding
import kotlinx.android.synthetic.main.item_topic_content.view.*

/*
* Fragment to view expanded topic
* */
class TopicDetailFragment : Fragment() {

    private var topic: String? = null
    private var upvote: Int = 0
    private var downvote: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            topic = it.getString("topic")
            upvote = it.getInt("upvote")
            downvote = it.getInt("downvote")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var bind = FragmentTopicDetailBinding.bind(
            inflater.inflate(
                R.layout.fragment_topic_detail,
                container,
                false
            )
        )

        bind.lTopic.clTopic.tv_topic.text = topic
        bind.lTopic.clTopic.tv_up_num.text = upvote.toString()
        bind.lTopic.clTopic.tv_down_num.text = downvote.toString()
        bind.lTopic.clTopic.img_up.setOnClickListener(View.OnClickListener {
            TopicModel.addUpVote(
                TopicModel(topic!!, upvote, downvote)
            )
            upvote++
            bind.lTopic.clTopic.tv_up_num.text = upvote.toString()
        })

        bind.lTopic.clTopic.img_down.setOnClickListener(View.OnClickListener {
            TopicModel.addDownVote(
                TopicModel(topic!!, upvote, downvote)
            )
            downvote++
            bind.lTopic.clTopic.tv_down_num.text = downvote.toString()
        })

        return bind.root
    }
}