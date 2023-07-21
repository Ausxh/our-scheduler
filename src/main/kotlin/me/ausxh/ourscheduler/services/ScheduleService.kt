package me.ausxh.ourscheduler.service

import java.util.UUID

import me.ausxh.ourscheduler.model.Course
import me.ausxh.ourscheduler.model.Subject
import me.ausxh.ourscheduler.model.toEvent

import me.ausxh.ourscheduler.repository.CourseRepository
import me.ausxh.ourscheduler.repository.AppUserRepository

import me.ausxh.scheduleGen.Event
import me.ausxh.scheduleGen.EventScheduleGenerator

import org.springframework.stereotype.Service

@Service
class ScheduleService(private val appUserRepository: AppUserRepository, private val courseRepository: CourseRepository) {
    fun generateSchedules(userId: UUID?) : List<List<Course?>> {
        val subjectList: List<Subject?> = appUserRepository.findUserById(userId!!).get(0)?.subjectList!!.toList()
        val courseGroups: MutableList<List<Course?>> = mutableListOf<List<Course?>>()
        val eventGroups: MutableList<List<Event>> = mutableListOf<List<Event>>()
        for (subject in subjectList) {
            courseGroups.add(courseRepository.findBySubject(subject))
        }

        for (courseGroup in courseGroups) {
            eventGroups.add(courseGroup.toEvent())
        }

        val schedGenerator: EventScheduleGenerator = EventScheduleGenerator()
        val indices = schedGenerator.generate(eventGroups)

        val finalCourseGroups: MutableList<MutableList<Course?>> = mutableListOf<MutableList<Course?>>()
        for ((i,j) in indices.withIndex()) {
            finalCourseGroups.add(mutableListOf<Course?>())
            for ((k, l) in j.withIndex()) {
                finalCourseGroups.get(i).add(courseGroups[k][l])
            }
        }
        return finalCourseGroups;
    }
}

