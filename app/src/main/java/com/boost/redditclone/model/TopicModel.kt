package com.boost.redditclone.model

import android.util.Log

/*
* data class to structure data
* */

data class TopicModel(
    val topic: String,
    var upvote: Int,
    var downvote: Int
) {


    override fun toString(): String {
        return "TopicContent(topic='$topic', upvote=$upvote, downvote=$downvote)"
    }


    companion object getTopicModelList {
        var topicModelList: MutableList<TopicModel> = mutableListOf()

        fun getTopicList(): MutableList<TopicModel> {

            if (topicModelList.isEmpty()) {
                topicModelList.addAll(
                    getExTopicContentList()
                )
            }

            return topicModelList
        }

        fun getExTopicContentList(): List<TopicModel> {
            var i = 0
            val list: MutableList<TopicModel> = mutableListOf()
            while (i < 10) {
                list.add(getExTopicContent())
                i++
            }
            return list.sortedBy { topicContent -> topicContent.upvote }
        }

        fun getExTopicContent(): TopicModel {
            val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
            Log.d("TAG", "getExTopicContent: " + charPool.size)
            val topic = (1..255)
                .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")

            val upvote = kotlin.random.Random.nextInt(0, 10)
            val downvote = kotlin.random.Random.nextInt(0, 10)
            return TopicModel(
                topic,
                upvote,
                downvote
            )
        }

        fun addUpVote(item: TopicModel) {
            topicModelList.find { topicContent -> topicContent == item }?.apply {
                this.upvote++
            }
        }

        fun addDownVote(item: TopicModel) {
            topicModelList.find { topicContent -> topicContent == item }?.apply {
                this.downvote++
            }
        }
    }
}
