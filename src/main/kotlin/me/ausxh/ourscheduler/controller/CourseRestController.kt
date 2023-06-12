package me.ausxh.ourscheduler.controller

import me.ausxh.ourscheduler.repository.*
import me.ausxh.ourscheduler.model.AppUser
import me.ausxh.ourscheduler.model.Course

import com.fasterxml.jackson.databind.node.ObjectNode

import java.util.UUID

import org.springframework.http.MediaType

import org.springframework.ui.set

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class CourseRestController(private val courseRepository: CourseRepository, private val appUserRepository: AppUserRepository) {

    @PostMapping("/confirmAdd", MediaType.APPLICATION_JSON_VALUE)
    fun add(@RequestBody json: ObjectNode): HashMap<String, UUID?> {
        var user: AppUser? = null
        if(json.has("id")) {
            val id: UUID = UUID.fromString(json.get("id").asText())
            user = appUserRepository.findUserById(id).get(0)
        } 
        if (user == null) {
            user = AppUser()
        }

        if(json.has("classId")) {
            val reqCourse: Course? = courseRepository.findByClassNumber(json.get("classId").asInt()).get(0)
            user.courseList += reqCourse
        }

        appUserRepository.save(user)

        return HashMap<String, UUID?>().apply {
            put("id", user.id)
        }
    }

}

