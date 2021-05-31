package id.rllyhz.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class TVShowResponse(
    val id: Int,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("name")
    val title: String,
    @SerializedName("overview")
    val synopsis: String,
    @SerializedName("first_air_date")
    val releasedDate: String,
    @SerializedName("vote_average")
    val rate: Float,
    @SerializedName("original_language")
    val language: String
)