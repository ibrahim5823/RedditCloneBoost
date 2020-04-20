package com.boost.redditclone

data class TopicContent(
    val topic: String,
    val content: String,
    var upvote: Int,
    var downvote: Int
) {


    override fun toString(): String {
        return "TopicContent(topic='$topic', content='$content', upvote=$upvote, downvote=$downvote)"
    }
}