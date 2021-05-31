package id.rllyhz.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.data.local.entity.MovieEntity
import id.rllyhz.core.data.local.entity.TVShowEntity

@Database(
    entities = [MovieEntity::class, TVShowEntity::class, FavMovie::class, FavTVShow::class],
    version = 1
)
abstract class SunGlassesShowDB : RoomDatabase() {
    abstract fun getDao(): SunGlassesShowDao
}