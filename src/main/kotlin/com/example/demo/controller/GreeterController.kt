package com.example.demo.controller

import com.example.demo.request.HelloRequest
import com.example.demo.response.HelloResponse
import org.springframework.web.bind.annotation.*

/**
 * Rest API
 *
 * 戻り値は全てjson (Responseを使用)
 */
@RestController
@RequestMapping("greeter") /** Base Class URL */
class GreeterController {

    /**
     * query string
     */
    @GetMapping("/hello") /** Method URL */
    fun hello(@RequestParam("name") name: String): HelloResponse {
        return HelloResponse("Hello ${name}")
    }

    /**
     * path parameter
     */
    @GetMapping("/hello/{name}")
    fun helloPathValue(@PathVariable("name") name: String): HelloResponse {
        return HelloResponse("Hello $name")
    }

    /**
     * json in - out
     */
    @PostMapping("/hello/json") /** HTTP Post Method enable */
    fun helloPost(@RequestBody request: HelloRequest): HelloResponse {
        return HelloResponse("Hello ${request.name}")
    }
}