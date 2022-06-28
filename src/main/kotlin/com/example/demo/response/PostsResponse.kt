package com.example.demo.response

import org.springframework.http.HttpStatus

data class PostsResponse(val status:HttpStatus, val body: String?)
