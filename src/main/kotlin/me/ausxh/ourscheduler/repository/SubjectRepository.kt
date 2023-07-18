package me.ausxh.ourscheduler.repository;

import me.ausxh.ourscheduler.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository : JpaRepository<Subject, Int>{

    fun findBySymbol(symbol: String): List<Subject?>
    
}
