package com.example.demo.controller

import com.example.demo.entity.scJson
import com.example.demo.response.MapResponse
import com.example.demo.response.PostsResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate


/**
 * 外部APIを取得する
 */
@RestController
@RequestMapping("/json")
class JsonGetController {

    /**
     * jsonPlaceholder API
     */
    @GetMapping("/posts")
    fun jsonGetPosts(): PostsResponse {

        // 取得対象のAPI
        val url = "https://jsonplaceholder.typicode.com/posts"
        val url2 = "https://pixabay.com/api/?key=25609556-1b7bd31363d6a0d9c2a010f12"
        val url3 = "https://twitter.com/search?q=%E3%81%82%E3%81%84%E3%81%86%E3%81%88%E3%81%8A%E3%80%80-%E3%81%8B%E3%81%8D%E3%81%8F%E3%81%91%E3%81%93&src=typed_query&f=top"

        // リクエストの送信
        val restTemplate = RestTemplate()
        val response = restTemplate.exchange(url3, HttpMethod.GET, null, String::class.java)

        // 結果の取得
        val status = response.statusCode
        val body  = response.body

        return PostsResponse(status, body)
    }

    /**
     * mapで返す
     */
    @GetMapping("/map")
    fun jsonGetPostsMap(): MapResponse {

        // 取得対象のAPI
        val url = "https://jsonplaceholder.typicode.com/posts"

        // リクエストの送信
        val restTemplate = RestTemplate()
        val response = restTemplate.exchange(url, HttpMethod.GET, null, String::class.java)

        // 結果の取得
        val status = response.statusCode
        val body  = response.body

        // mapに変換
        //TODO デシリアライズに失敗する
        val mapper = ObjectMapper()
        val jsonMap: Map<String?, Any?>? = mapper.readValue<Map<String?, Any?>>(body, object : TypeReference<Map<String?, Any?>?>() {})

        return MapResponse(status, jsonMap)
    }

    /**
     * APIキーを秘匿にする
     */
    @GetMapping("/secret")
    fun jsonGetSecret(): PostsResponse {

        // 取得対象のAPI
        val url = System.getenv("JSON_URL")

        // リクエストの送信
        val restTemplate = RestTemplate()
        val response = restTemplate.exchange(url, HttpMethod.GET, null, String::class.java)

        // 結果の取得
        val status = response.statusCode
        val body  = response.body

        return PostsResponse(status, body)
    }

    /**
     * スクレイピング
     */
    @GetMapping("/sc")
    fun jsonGetPSc(): scJson? {

        // 解析
        val doc: Document = Jsoup.connect("https://qiita.com/derakudo/items/a2e11e6d9eaa72e36de2").get()
        // 各記事のaタグを取得。jQueryのセレクターと同じ感じで記載
        val newsHeadlines: Elements = doc.select(".logly-lift-ad-title")

        var hoge: scJson? = null
        for (headline in newsHeadlines) {
            hoge = scJson(headline.ownText(), headline.absUrl("href"))
        }

        return hoge
    }

}