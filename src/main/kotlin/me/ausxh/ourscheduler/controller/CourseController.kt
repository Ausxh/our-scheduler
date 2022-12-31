package me.ausxh.ourscheduler.controller

import me.ausxh.ourscheduler.repository.CourseRepository

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/classes")
class CourseController(private val courseRepository: CourseRepository) {

    @GetMapping
    fun getAllProducts(): ResponseEntity<Any> {
        return ResponseEntity.ok(this.courseRepository.findAll());
    }

}

