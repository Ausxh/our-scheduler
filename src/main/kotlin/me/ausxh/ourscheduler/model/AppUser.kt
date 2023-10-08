package me.ausxh.ourscheduler.model;

import javax.persistence.*

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type

import java.util.UUID

@Entity
@Table(name = "app_user")
class AppUser constructor() {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    var id: UUID? = null

    var username: String? = null
    var password: String? = null

    @ManyToMany(cascade = [CascadeType.ALL])
    var courseList: MutableSet<Course?> = mutableSetOf<Course?>()

    @ManyToMany(cascade = [CascadeType.ALL])
    var subjectList: MutableSet<Subject?> = mutableSetOf<Subject?>()
}
