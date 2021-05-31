package id.rllyhz.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Int,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("original_title")
    val title: String,
    @SerializedName("overview")
    val synopsis: String,
    @SerializedName("release_date")
    val releasedDate: String,
    @SerializedName("vote_average")
    val rate: Float,
    @SerializedName("original_language")
    val language: String
)