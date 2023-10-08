package me.ausxh.ourscheduler.controller

import java.time.format.DateTimeFormatter
import java.time.LocalTime

import java.util.UUID
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.Cookie

import me.ausxh.ourscheduler.model.AppUser
import me.ausxh.ourscheduler.model.Course
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.stereotype.Controller

@Controller
class CourseController(private val appUserRepository: AppUserRepository, private val courseRepository: CourseRepository, private val subjectRepository: SubjectRepository) {

    @PostMapping("/getSections", MediaType.APPLICATION_JSON_VALUE)
    fun table(@RequestBody json: ObjectNode, model: Model): String {
        val subSymbol = json.get("subject").asText()
        val subject = subjectRepository.findBySymbol(subSymbol).get(0)
        model["sections"] = courseRepository.findBySubject(subject)
        return "sectionList"
    }

    @GetMapping("/addClass")
    fun edit(model: Model): String {
        model["pageTitle"] = "ourscheduler"
        model["courseTitles"] = subjectRepository.findByTypeOrderBySymbolAsc("LEC")
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

    @GetMapping("/viewSchedule")
    fun viewSchedule(@RequestParam("classes") classList: Array<Int>, model: Model) : String{
        model["pageTitle"] = "ourscheduler"

        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mma")
        var currentSuffix = "am";
        val timeSuffixes = arrayOf<String>(":00", ":15", ":30", ":45");
        val startTime: Int = 8;
        val endTime: Int = 20;
        val times = mutableListOf<String>();
        for (i in startTime..endTime) {
            if(i >= 12) {
                currentSuffix = "pm";
            }
            for(suffix in timeSuffixes) {
                times.add("" + i + suffix + currentSuffix);
            }
        }

        val timeIncrement: Double = 100.0 / ((endTime - startTime + 1) * 4)
        val courseTimings = arrayOfNulls<MutableList<Triple<Course?, Double, Double>>>(7)
        for (i in 0..6) {
            courseTimings[i] = mutableListOf<Triple<Course?, Double, Double>>()
        }
        for (course in classList) {
            var curCourse = courseRepository.findByIdOrNull(course)
            val sTime = if (curCourse?.start_time?.isEmpty() ?: true) null else LocalTime.parse(curCourse!!.start_time, timeFormatter)
            val eTime = if (curCourse?.end_time?.isEmpty() ?: true) null else LocalTime.parse(curCourse!!.end_time, timeFormatter)
            val height: Double = if(eTime == null || sTime == null) 0.0 else ((eTime.getHour() - sTime.getHour()) * 4) + (eTime.getMinute() - sTime.getMinute()) / 15.0
            println("height")
            val topPos: Double = if(eTime == null || sTime == null) 0.0 else ((sTime.getHour() - startTime) * 4) + (sTime.getMinute()) / 15.0

            if(curCourse?.days?.contains("M") ?: false) {
                courseTimings[0]?.add(Triple(curCourse, height * timeIncrement, topPos * timeIncrement))
            }
            if(curCourse?.days?.contains("T") ?: false) {
                courseTimings[1]?.add(Triple(curCourse, height * timeIncrement, topPos * timeIncrement))
            }
            if(curCourse?.days?.contains("W") ?: false) {
                courseTimings[2]?.add(Triple(curCourse, height * timeIncrement, topPos * timeIncrement))
            }
            if(curCourse?.days?.contains("R") ?: false) {
                courseTimings[3]?.add(Triple(curCourse, height * timeIncrement, topPos * timeIncrement))
            }
            if(curCourse?.days?.contains("F") ?: false) {
                courseTimings[4]?.add(Triple(curCourse, height * timeIncrement, topPos * timeIncrement))
            }
            if(curCourse?.days?.contains("S") ?: false) {
                courseTimings[5]?.add(Triple(curCourse, height * timeIncrement, topPos * timeIncrement))
            }
            if(curCourse?.days?.contains("U") ?: false) {
                courseTimings[6]?.add(Triple(curCourse, height * timeIncrement, topPos * timeIncrement))
            }
        }
        model["times"] = times;
        model["sections"] = courseTimings

        return "viewSchedule"
    }

    @PostMapping("/addUserClasses")
    fun addUserClasses(@RequestParam("classes") classList: Array<Int>, @CookieValue("id", required=false) userId: UUID?, response: HttpServletResponse) : String {

        var user: AppUser? = null
        if(userId != null) {
            user = appUserRepository.findUserById(userId).get(0)
        } 

        if (user == null) {
            user = AppUser()
            appUserRepository.save(user)
            response.addCookie(Cookie("id", user.id.toString()))
        }
        
        user.courseList.clear()
        for(course in classList) {
            val curCourse: Course? = courseRepository.findByIdOrNull(course)
            user.courseList += curCourse
        }

        appUserRepository.save(user)
        return "redirect:/"
    }


}

