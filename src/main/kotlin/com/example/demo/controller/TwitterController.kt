package com.example.demo.controller

import com.example.demo.request.TweetRequest
import com.example.demo.response.StatusResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import twitter4j.Status
import twitter4j.TwitterFactory


@RestController
@RequestMapping("/twitter")
class TwitterController {

    @GetMapping("/home")
    fun index(model: Model?): String? {
        return "tweet"
    }

    @PostMapping("/tweet")
    fun tweet(@RequestBody request: TweetRequest): StatusResponse {

        val twitter = TwitterFactory.getSingleton()
        val status = twitter.updateStatus(request.message)

        return StatusResponse(status)
    }
}