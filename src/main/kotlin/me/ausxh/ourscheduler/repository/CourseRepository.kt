package me.ausxh.ourscheduler.repository;

import me.ausxh.ourscheduler.model.Course;
import me.ausxh.ourscheduler.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Int>{
    fun findByClassNumber(number: Int): List<Course?>
    fun findBySubject(subject: Subject?): List<Course?>
}
