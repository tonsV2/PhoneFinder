package dk.fitfit.phonefinder.domain

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Contact(@ColumnInfo(name = "conatctId") var contactId: String, @PrimaryKey(autoGenerate = true) var id: Long = 0) {
    constructor() : this("")
}
