package com.boost.redditclone

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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