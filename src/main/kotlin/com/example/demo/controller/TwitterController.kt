package com.example.demo.controller

import com.example.demo.request.TweetRequest
import com.example.demo.response.StatusResponse
import com.example.demo.response.TweetAllResponse
import com.example.demo.response.TweetResponse
import org.springframework.web.bind.annotation.*
import twitter4j.Query
import twitter4j.Status
import twitter4j.TwitterFactory


@RestController
@RequestMapping("/twitter")
class TwitterController {

    /**
     * ツイートを投稿する
     *
     * @args request ツイート本文
     * @return status ステータス
     */
    @PostMapping("/tweet")
    fun tweet(@RequestBody request: TweetRequest): StatusResponse {

        val twitter = TwitterFactory.getSingleton()
        val status = twitter.updateStatus(request.message)

        return StatusResponse(status)
    }

    /**
     * 特定のワードで検索し、ツイートを取得する
     *
     * @args request 検索文字
     * @return resultMap 検索結果(本文, ユーザー名, 投稿日時)
     */
    @PostMapping("/get")
    fun tweetGet(@RequestBody request: TweetRequest): TweetResponse {

        // 初期化
        val twitter = TwitterFactory().instance
        val query = Query()

        // 検索ワードを設定し検索
        query.setQuery(request.message)
        val result = twitter.search(query)

        val resultMap :MutableMap<Int, Map<String, String>> = mutableMapOf()

        // 検索結果を取得
        for ((i, tweet) in result.tweets.withIndex()) {
            val tweetMap :Map<String, String> = mapOf(
                "tweet" to tweet.text,
                "username" to tweet.user.name,
                "tweet_date" to tweet.createdAt.toString())
            resultMap[i] = tweetMap
        }

        return TweetResponse(resultMap)
    }

    /**
     * 特定のワードで検索し、ツイートを取得する(全情報)
     *
     * @args request 検索文字
     * @return resultMap 検索結果(ステータス)
     */
    @PostMapping("/all")
    fun tweetGetAll(@RequestBody request: TweetRequest): TweetAllResponse {

        // 初期化
        val twitter = TwitterFactory().instance
        val query = Query()

        // 検索ワードを設定し検索
        query.setQuery(request.message)
        val result = twitter.search(query)

        // 結果件数
        println("ヒット数 : " + result.tweets.size)

        val resultMap :MutableMap<Int, Map<String, Status>> = mutableMapOf()

        // ステータスを取得
        for ((i, tweet) in result.tweets.withIndex()) {
            val tweetMap :Map<String, Status> = mapOf("status" to tweet)
            resultMap[i] = tweetMap
        }

        return TweetAllResponse(resultMap)
    }
}