package me.ausxh.ourscheduler.repository;

import me.ausxh.ourscheduler.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Int>{
    fun findByTitle(title: String): List<Course?>
    fun findByClassNumber(number: Int): List<Course?>
    
    @Query("SELECT DISTINCT a.title FROM Course a ORDER BY title")
    fun findDistinctTitle(): List<String?>;

}
