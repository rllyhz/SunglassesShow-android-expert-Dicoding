package id.rllyhz.core.data.local

import androidx.paging.DataSource
import androidx.room.*
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.data.local.entity.MovieEntity
import id.rllyhz.core.data.local.entity.TVShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SunGlassesShowDao {
    @Query("SELECT * FROM tb_movie")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tb_tv_show")
    fun getTVShows(): Flow<List<TVShowEntity>>

    @Query("SELECT * FROM tb_movie WHERE id = :id")
    fun getMovieById(id: Int): Flow<MovieEntity>

    @Query("SELECT * FROM tb_tv_show WHERE id = :id")
    fun getTVShowById(id: Int): Flow<TVShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTVShow(tvShow: TVShowEntity)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MovieEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTVShows(tvShows: List<TVShowEntity>)

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