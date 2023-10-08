package me.ausxh.ourscheduler.controller;

import me.ausxh.ourscheduler.repository.*
import me.ausxh.ourscheduler.model.Course
import me.ausxh.ourscheduler.model.Subject
import me.ausxh.ourscheduler.model.AppUser

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.CookieValue


import java.util.UUID

@Controller
class HtmlController(private val appUserRepository: AppUserRepository) {

    @GetMapping("/")
    fun ourscheduler(@CookieValue(value = "id") userId: UUID?, model: Model): String {
        model["pageTitle"] = "ourscheduler"
        var subjectList: List<Subject?> = listOf<Subject?>()
        var classList: List<Course?> = listOf<Course?>()
        if(userId != null) {
            val user: AppUser? = appUserRepository.findUserById(userId).get(0)
            subjectList = user?.subjectList.orEmpty().toList()
            classList = user?.courseList.orEmpty().toList()
        }

        model["subjects"] = subjectList
        model["classes"] = classList
        return "ourscheduler"
    }

}

