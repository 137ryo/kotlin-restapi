package com.example.demo.response

import twitter4j.Status

data class TweetAllResponse(val resultMap :MutableMap<Int, Map<String, Status>>)
