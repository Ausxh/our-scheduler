package me.ausxh.ourscheduler.controller

import java.util.UUID
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.Cookie

import me.ausxh.ourscheduler.model.AppUser
import me.ausxh.ourscheduler.model.Subject

import me.ausxh.ourscheduler.repository.AppUserRepository
import me.ausxh.ourscheduler.repository.CourseRepository
import me.ausxh.ourscheduler.repository.SubjectRepository

import com.fasterxml.jackson.databind.node.ObjectNode

import org.springframework.data.repository.findByIdOrNull

import org.springframework.http.MediaType

import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.stereotype.Controller


@Controller
class CourseController(private val appUserRepository: AppUserRepository, private val courseRepository: CourseRepository, private val subjectRepository: SubjectRepository) {

    @PostMapping("/getSections", MediaType.APPLICATION_JSON_VALUE)
    fun table(@RequestBody json: ObjectNode, model: Model): String {
        val subSymbol = json.get("subject").asText()
        val subject = subjectRepository.findBySymbol(subSymbol).get(0)
        println(subject)
        model["sections"] = courseRepository.findBySubject(subject)
        return "sectionList"
    }

    @GetMapping("/addClass")
    fun edit(model: Model): String {
        model["pageTitle"] = "ourscheduler"
        model["courseTitles"] = subjectRepository.findAll()
        return "edit"
    }

    @PostMapping("/addSubject", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    fun addSubject(subjectId: Int?, @CookieValue("id", required=false) userId: UUID?, response: HttpServletResponse) : String {
        var user: AppUser? = null
        if(userId != null) {
            user = appUserRepository.findUserById(userId).get(0)
        } 
        if (user == null) {
            user = AppUser()
            appUserRepository.save(user)
            response.addCookie(Cookie("id", user.id.toString()))
        }

        if(subjectId != null) {
            val subject: Subject? = subjectRepository.findByIdOrNull(subjectId)
            user.subjectList += subject
        }

        appUserRepository.save(user)
        return "redirect:/"
    }
}

