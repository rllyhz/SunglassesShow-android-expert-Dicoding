package id.rllyhz.core.data.local

import androidx.paging.DataSource
import androidx.room.*
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import kotlinx.coroutines.flow.Flow

@Dao
interface SunGlassesShowDao {
    @Query("SELECT * FROM tb_fav_movie")
    fun getFavMovies(): DataSource.Factory<Int, FavMovie>

    @Query("SELECT * FROM tb_fav_tv_show")
    fun getFavTVShows(): DataSource.Factory<Int, FavTVShow>

    @Query("SELECT * FROM tb_fav_movie WHERE id = :id")
    fun getFavMovieById(id: Int): Flow<FavMovie?>

    @Query("SELECT * FROM tb_fav_tv_show WHERE id = :id")
    fun getFavTVShowById(id: Int): Flow<FavTVShow?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavMovie(favMovie: FavMovie): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavTVShow(favTvShow: FavTVShow): Long

    @Delete
    suspend fun deleteFavMovie(favMovie: FavMovie): Int

    @Delete
    suspend fun deleteFavTVShow(favTvShow: FavTVShow): Int
}