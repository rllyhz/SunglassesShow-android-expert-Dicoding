package id.rllyhz.core.domain.model

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
data class Movie(
    val id: Int,
    val posterPath: String,
    val backdropPath: String,
    val title: String,
    val genres: String?,
    val durationInMinutes: Int?,
    val rate: Float,
    val releasedAt: String,
    val language: String?,
    val synopsis: String?,
    val status: String?
) : Parcelable {

    val year: Int
        @RequiresApi(Build.VERSION_CODES.O)
        get() =
            if (releasedAt.isEmpty()) 0 else LocalDate.parse(
                releasedAt,
                DateTimeFormatter.ISO_DATE
            ).year

    val rating: Float
        get() = rate.let { score ->
            return when {
                score <= 1 -> 1f
                score <= 2 -> 1.5f
                score <= 3 -> 2f
                score <= 4 -> 2.5f
                score <= 5 -> 3f
                score <= 6 -> 3.4f
                score <= 7 -> 3.8f
                score <= 8 -> 4f
                score <= 9 -> 4.5f
                score <= 10 -> 5f
                else -> 0f
            }
        }

    val duration: String
        get() = when (durationInMinutes) {
            null -> ""
            else -> getDurationFormat(durationInMinutes)
        }

    private fun getDurationFormat(durationInMinutes: Int): String =
        "${durationInMinutes / 60}h ${durationInMinutes % 60}min"
}