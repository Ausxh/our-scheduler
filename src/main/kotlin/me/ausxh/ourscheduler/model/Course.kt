package me.ausxh.ourscheduler.model

import me.ausxh.scheduleGen.Event

import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.time.LocalTime

import javax.persistence.*

fun List<Course?>.toEvent() : List<Event> {
    var eventList: MutableList<Event> = mutableListOf<Event>()
    for (course: Course? in this) {
        eventList.add(course!!.toEvent())
    }

    return eventList
}

@Entity
@Table(name = "course")
class Course constructor() {
@Id
    var id: Int? = null

    var classNumber: Int? = null
    var instruction_type: String? = null
    var type: String? = null
    var days: String? = null 
    var times: String? = null
    var start_date: String? = null
    var end_date: String? = null
    var start_time: String? = null
    var end_time: String? = null
    var instructor: String? = null
    var room: String? = null
    var dates: String? = null
    var seats: Int? = null
    var comments: String? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    var subject: Subject? = null

    fun toEvent() : Event {
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mma")
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/uu")

        return Event(
            endTime = if (end_time?.isEmpty() ?: true) null else LocalTime.parse(end_time, timeFormatter),
            startTime = if (start_time?.isEmpty() ?: true) null else LocalTime.parse(start_time, timeFormatter),
            days = days?.map { when(it) {
                'M' -> DayOfWeek.MONDAY
                'T' -> DayOfWeek.TUESDAY
                'W' -> DayOfWeek.WEDNESDAY
                'R' -> DayOfWeek.THURSDAY
                'F' -> DayOfWeek.FRIDAY
                'S' -> DayOfWeek.SATURDAY
                'U' -> DayOfWeek.SUNDAY
                else -> null
            }}
            ?.filterNotNull()
            ?.toSet(),
            startDate = LocalDate.parse(start_date, dateFormatter),
            endDate = LocalDate.parse(end_date, dateFormatter)
        )
    }

}
