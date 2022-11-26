package me.ausxh.ourscheduler

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class HtmlController {

    @GetMapping("/")
    fun ourscheduler(model: Model): String {
        model["title"] = "ourscheduler"
        return "ourscheduler"
    }
}

