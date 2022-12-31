package me.ausxh.ourscheduler.model;

import javax.persistence.*

@Entity
@Table(name = "course")
class Course constructor() {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null;

    var section: String? = null
    var classNumber: Int? = null
    var instructionType: String? = null
    var title: String? = null
    var satisifes: String? = null
    var credits: Double? = null
    var type: String? = null
    var days: String? = null
    var times: String? = null
    var instructor: String? = null
    var room: String? = null
    var dates: String? = null
    var seats: Int? = null
    var comments: String? = null
}
