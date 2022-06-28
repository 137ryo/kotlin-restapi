package com.example.demo.response

import com.example.demo.entity.PostsJson
import org.springframework.http.HttpStatus

data class MapResponse(val status: HttpStatus, val body: Map<String?, Any?>?)
