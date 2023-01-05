package me.ausxh.ourscheduler.controller;

import me.ausxh.ourscheduler.repository.CourseRepository
import org.springframework.http.ResponseEntity
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class HtmlController {

    @GetMapping("/")
    fun ourscheduler(model: Model): String {
        model["pageTitle"] = "ourscheduler"
        return "ourscheduler"
    }

}

