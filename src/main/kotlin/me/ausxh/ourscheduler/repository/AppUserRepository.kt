package me.ausxh.ourscheduler.repository;

import me.ausxh.ourscheduler.model.AppUser;
import me.ausxh.ourscheduler.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AppUserRepository : JpaRepository<AppUser, UUID>{
    fun findUserById(id: UUID): List<AppUser?>
}
