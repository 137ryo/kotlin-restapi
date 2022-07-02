package com.example.demo.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.thymeleaf.util.StringUtils

@Service
class JsonPostService {

    /**
     * 検索IDのバリデーション
     */
    fun validateId(id: String?): Boolean {

        var checkId: Boolean = true

        if(StringUtils.isEmpty(id)) {
            checkId = false
        }

        return checkId
    }

    /**
     * 検索パラメータを使用してJsonPostsを取得する
     */
    fun PostsSearchId(id: String): String? {

        // 取得対象のAPI
        val url = "https://jsonplaceholder.typicode.com/posts/{id}"

        // リクエストの送信
        val restTemplate = RestTemplate()
        val response = restTemplate.getForObject(url, String::class.java, id)

        return response
    }
}