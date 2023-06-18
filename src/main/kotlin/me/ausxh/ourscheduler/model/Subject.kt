package me.ausxh.ourscheduler.model;

import javax.persistence.*

@Entity
@Table(name = "subject")
class Subject constructor() {

    @Id
    var id: Int? = null

    var symbol: String? = null
    var title: String? = null
    var satisfies: String? = null
    var credits: Double? = null

}
