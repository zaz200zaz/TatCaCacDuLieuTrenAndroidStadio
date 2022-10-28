package com.example.kotlinlogin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Parcel
import android.os.Parcelable
import java.util.prefs.PreferencesFactory

val DATABSE_NAME: String = "MyDB"

class DBHelper (var context: Context): SQLiteDatabase(context,DATABSE_NAME,null,1), Parcelable {
    constructor(parcel: Parcel) : this(TODO("context")) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DBHelper> {
        override fun createFromParcel(parcel: Parcel): DBHelper {
            return DBHelper(parcel)
        }

        override fun newArray(size: Int): Array<DBHelper?> {
            return arrayOfNulls(size)
        }
    }

}