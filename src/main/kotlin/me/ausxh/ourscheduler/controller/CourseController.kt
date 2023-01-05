package me.ausxh.ourscheduler.controller

import me.ausxh.ourscheduler.repository.CourseRepository

import com.fasterxml.jackson.databind.node.ObjectNode

import org.springframework.http.MediaType

import org.springframework.ui.Model
import org.springframework.ui.set

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.stereotype.Controller;


@Controller
class CourseController(private val courseRepository: CourseRepository) {

    @PostMapping("/getSections", MediaType.APPLICATION_JSON_VALUE)
    fun table(@RequestBody json: ObjectNode, model: Model): String {
        val title = json.get("courseTitle").asText()
        model["sections"] = courseRepository.findByTitle(title)
        return "sectionList"
    }

    @GetMapping("/addClass")
    fun edit(model: Model): String {
        model["pageTitle"] = "ourscheduler"
        model["courseTitles"] = courseRepository.findDistinctTitle()
        return "edit"
    }

}

