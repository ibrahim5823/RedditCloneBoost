package com.boost.redditclone.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.boost.redditclone.model.TopicModel

/*
* ViewModel class to hold data for Topic list in HomeFragment
* */
class HomeViewModel : ViewModel() {
    var topicModelList: MutableLiveData<List<TopicModel>> = MutableLiveData()

    init {
        topicModelList = MutableLiveData(TopicModel.getTopicList())
//        topicModelList = MutableLiveData()
    }
}