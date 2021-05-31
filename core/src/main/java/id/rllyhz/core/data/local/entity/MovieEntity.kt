package id.rllyhz.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "genres")
    val genres: String?,
    @ColumnInfo(name = "durationInMinutes")
    val durationInMinutes: Int?,
    @ColumnInfo(name = "rate")
    val rate: Float,
    @ColumnInfo(name = "released_at")
    val releasedAt: String,
    @ColumnInfo(name = "synopsis")
    val synopsis: String?,
    @ColumnInfo(name = "year")
    val year: Int
)