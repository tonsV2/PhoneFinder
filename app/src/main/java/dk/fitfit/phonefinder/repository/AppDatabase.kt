package dk.fitfit.phonefinder.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import dk.fitfit.phonefinder.domain.Contact

@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactRepository(): ContactRepository
}
