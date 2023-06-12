package me.ausxh.ourscheduler.controller;

import me.ausxh.ourscheduler.repository.*
import me.ausxh.ourscheduler.model.Course

import org.springframework.http.ResponseEntity
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CookieValue

import java.util.UUID

@Controller
class HtmlController(private val appUserRepository: AppUserRepository) {

    @GetMapping("/")
    fun ourscheduler(@CookieValue(value = "id") userId: UUID?, model: Model): String {
        model["pageTitle"] = "ourscheduler"
        var courseList: List<Course?> = listOf<Course?>()
        if(userId != null) 
            courseList = appUserRepository.findUserById(userId).get(0)?.courseList!!.toList()

        model["courses"] = courseList
        return "ourscheduler"
    }

}

