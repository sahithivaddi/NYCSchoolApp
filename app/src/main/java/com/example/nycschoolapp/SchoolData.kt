package com.example.nycschoolapp

import android.os.Parcel
import android.os.Parcelable

data class SchoolData(
    var dbn : String,
    var school_name : String,
    val overview_paragraph : String,
    val finalgrades : String,
    val total_students : String,
    val attendance_rate : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
        ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dbn)
        parcel.writeString(school_name)
        parcel.writeString(overview_paragraph)
        parcel.writeString(finalgrades)
        parcel.writeString(total_students)
        parcel.writeString(attendance_rate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SchoolData> {
        override fun createFromParcel(parcel: Parcel): SchoolData {
            return SchoolData(parcel)
        }

        override fun newArray(size: Int): Array<SchoolData?> {
            return arrayOfNulls(size)
        }
    }
}
