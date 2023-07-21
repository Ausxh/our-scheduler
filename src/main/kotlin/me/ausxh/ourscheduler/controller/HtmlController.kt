package me.ausxh.ourscheduler.controller;

import me.ausxh.ourscheduler.repository.*
import me.ausxh.ourscheduler.model.Course
import me.ausxh.ourscheduler.model.Subject

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
        if(userId != null) 
            subjectList = appUserRepository.findUserById(userId).get(0)?.subjectList!!.toList()

        model["subjects"] = subjectList
        return "ourscheduler"
    }

}

