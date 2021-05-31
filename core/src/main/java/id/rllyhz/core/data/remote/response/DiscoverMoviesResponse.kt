package id.rllyhz.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class DiscoverMoviesResponse(
    @SerializedName("results")
    val movies: List<MovieResponse>
)