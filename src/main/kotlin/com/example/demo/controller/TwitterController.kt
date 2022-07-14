package com.example.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import twitter4j.Status
import twitter4j.TwitterException
import twitter4j.TwitterFactory


@Controller
@RequestMapping("/twitter")
class TwitterController {

    @GetMapping("/home")
    fun index(model: Model?): String? {
        return "tweet"
    }

    @PostMapping("/tweet")
    @Throws(TwitterException::class)
    fun tweet(@RequestBody message: String?): Status {
        val twitter = TwitterFactory.getSingleton()
        val status: Status = twitter.updateStatus(message)
        return status
    }
}