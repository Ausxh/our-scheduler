package me.ausxh.ourscheduler.repository;

import me.ausxh.ourscheduler.model.Course;
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Int>{
}
