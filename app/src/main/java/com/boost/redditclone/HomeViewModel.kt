package com.boost.redditclone

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.log

class HomeViewModel : ViewModel() {
    var topicContentList: MutableLiveData<List<TopicContent>> = MutableLiveData()

    init {
     topicContentList = MutableLiveData(getExTopicContentList())
    }

    fun getExTopicContentList(): List<TopicContent> {
        var i = 0
        val list: MutableList<TopicContent> = mutableListOf()
        while (i < 10) {
            list.add(getExTopicContent())
            i++
        }
        return list.sortedBy { topicContent -> topicContent.upvote }
    }

    fun addUpvote(item: TopicContent) {
        var value = topicContentList.value
        value?.find { topicContent -> topicContent == item }?.apply {
            this.upvote++
        }
        topicContentList.postValue(value)

    }

    fun getExTopicContent(): TopicContent {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z')
        Log.d("TAG", "getExTopicContent: " + charPool.size)
        val topic = (1..10)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")

        val content = (1..255)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")

        val upvote = kotlin.random.Random.nextInt(0, 30)
        val downvote = kotlin.random.Random.nextInt(0, 30)
        return TopicContent(topic, content, upvote, downvote)
    }

}