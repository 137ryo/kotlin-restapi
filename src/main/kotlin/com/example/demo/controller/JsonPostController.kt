package com.example.demo.controller

import com.example.demo.request.SearchRequest
import com.example.demo.service.JsonPostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class JsonPostController {

    @Autowired
    lateinit var jsonPostService: JsonPostService

    /**
     * 検索パラメータを使用
     */
    @PostMapping("/search")
    fun jsonPostSearch(@RequestBody request: SearchRequest): String? {

        // 取得対象のAPI
        val url = "https://jsonplaceholder.typicode.com/posts/{word}"

        // リクエストの送信
        val restTemplate = RestTemplate()

        return restTemplate.getForObject(url, String::class.java, request.word)
    }

    /**
     * それっぽく
     */
    @PostMapping("/search/stg")
    fun jsonPostSearchStg(@RequestBody request: SearchRequest): String? {

        // リクエストパラメータの入力チェック
        val checkId = jsonPostService.validateId(request.word)

        if(!checkId) {
            return "is empty"
        }

        // パラメータを引数にAPI取得
        val response = jsonPostService.PostsSearchId(request.word)

        return response
    }
}