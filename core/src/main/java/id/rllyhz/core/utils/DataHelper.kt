package id.rllyhz.core.utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import id.rllyhz.core.data.remote.response.GenreResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DataHelper {
    fun getGenresStringFormat(genres: List<GenreResponse>): String = run {
        var result = ""

        if (genres.isNotEmpty()) {
            for (genre in genres) {
                result += if (genres.last() != genre)
                    "${genre.name}, "
                else
                    genre.name
            }
        }

        result
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateInString(date: String): String {
        val dateTime = date.split("-")

        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                val time =
                    LocalDateTime.of(
                        dateTime[0].toInt(),
                        dateTime[1].toInt(),
                        dateTime[2].toInt(),
                        0,
                        0
                    )
                val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
                time.format(formatter)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                val time = Calendar.getInstance().apply {
                    set(Calendar.YEAR, dateTime[0].toInt())
                    set(Calendar.MONTH, dateTime[1].toInt())
                    set(Calendar.DATE, dateTime[2].toInt())
                }.timeInMillis

                SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(time))
            }
            else -> date
        }

    }
}