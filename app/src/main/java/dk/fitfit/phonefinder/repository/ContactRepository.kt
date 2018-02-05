package dk.fitfit.phonefinder.repository

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import dk.fitfit.phonefinder.domain.Contact

@Dao
interface ContactRepository {
    @Insert(onConflict = REPLACE)
    fun insert(contact: Contact)

    @Query("select * from contact where id = :arg0")
    fun findById(id: String): Contact

    @Query("select * from contact")
    fun findAll(): List<Contact>

    @Delete
    fun delete(contact: Contact)
}
