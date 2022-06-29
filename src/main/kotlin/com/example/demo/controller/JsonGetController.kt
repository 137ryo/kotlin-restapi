package com.example.demo.controller

import com.example.demo.request.HelloRequest
import com.example.demo.request.SearchRequest
import com.example.demo.response.MapResponse
import com.example.demo.response.PostsResponse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.*
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

        // リクエストの送信
        val restTemplate = RestTemplate()
        val response = restTemplate.exchange(url2, HttpMethod.GET, null, String::class.java)

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
     * 検索パラメータを使用
     */
    @PostMapping("/search")
    fun jsonPostSearch(@RequestBody request: SearchRequest): String? {

        // 取得対象のAPI
        val url = "https://jsonplaceholder.typicode.com/{word}"

        // リクエストの送信
        val restTemplate = RestTemplate()

        return restTemplate.getForObject(url, String::class.java, request.word)
    }

}