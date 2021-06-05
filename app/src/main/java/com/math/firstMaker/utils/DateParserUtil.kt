package com.math.firstMaker

import java.text.SimpleDateFormat
import java.util.*


class DateParserUtil {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
    private val fallbackFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.KOREA)
    private val fallback2Format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA)
    private val fallback3Format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)

    fun parseToD(src : Calendar) : String {
        val date = src[Calendar.DATE]
        return String.format("%02d", date)

    }

    fun parseToDayOfWeek( src : Calendar) : String {
        val date = src[Calendar.DAY_OF_WEEK]
        return when ( date ) {
            1 -> "SUN"
            2 -> "MON"
            3 -> "TUE"
            4 -> "WED"
            5 -> "THU"
            6 -> "FRI"
            else -> "SUN"
        }
    }

    fun parseToYMD(src: Calendar, separator: String = "."): String {

        val year = src[Calendar.YEAR]
        val month = src[Calendar.MONTH] + 1
        val date = src[Calendar.DATE]
        return String.format("%04d", year) + separator + String.format(
            "%02d",
            month
        ) + separator + String.format("%02d", date)
    }

    fun parseToYMDHM(
        src: Calendar,
        ymdSeparator: String = ".",
        timeSeparator: String = ":"
    ): String {
        src.add(Calendar.HOUR_OF_DAY,9)
        val ymd = parseToYMD(src, ymdSeparator)
        val hour = src[Calendar.HOUR_OF_DAY]
        val minute = src[Calendar.MINUTE]
        return ymd + " " + String.format("%02d", hour) + timeSeparator + String.format(
            "%02d",
            minute
        )
    }


    fun parseString(raw: String): Calendar {
        val date = try {
            simpleDateFormat.parse(raw)
        } catch (t: Throwable) {
            try {
                fallbackFormat.parse(raw)
            } catch (t: Throwable) {
                try {
                    fallback2Format.parse(raw)
                } catch (t: Throwable) {
                    try {
                        fallback3Format.parse(raw)
                    } catch (t: Throwable) {
                        throw RuntimeException()
                    }
                }
            }
        }
        return Calendar.getInstance().apply { time = date }
    }

    fun calculateDiffWithCurrent(rawString: String): Int {

        return calculateDiffWithCurrent(parseString(rawString).timeInMillis)
    }

    private fun calculateDiffWithCurrent(timeMs: Long): Int {
        val dayFormat = SimpleDateFormat("yyyy-MM-dd 00:00:00")

        val today = Calendar.getInstance()
        today.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val startDate = dayFormat.format(today.time.time)

        // 9시간을 빼준다
        var diff = timeMs + 32400000 - parseString(startDate).timeInMillis

        val minDiff = diff / 1000 / 60
        val hourDiff = minDiff / 60

        return (hourDiff / 24).toInt()
    }

    fun calculateTime(timeMs: Int): String {


        val second = timeMs % 600 / 10
        val minute = timeMs / 600
        val hour = minute / 600

        if (hour > 0) {
            return "${hour}시간 : ${minute}분"
        } else if (minute > 0) {
            return "${minute}분 : ${second}초"
        } else {
            return "${second}초"
        }

    }

    fun calculateTimeBySecond(timeMs: Int): String {

        val second = timeMs % 600 / 10
        val minute = timeMs / 600
        val hour = timeMs / 36000

        if (hour > 0) {

            if (minute > 0) {
                return "${hour}시간 ${minute}분 ${second}초"
            } else {
                return "${hour}시간 ${second}초"
            }
        } else {
            if (minute > 0) {
                return "${minute}분 ${second}초"
            } else {
                return "${second}초"
            }

        }
    }

}