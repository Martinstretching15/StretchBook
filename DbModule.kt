
package com.example.physionotes.data

import android.content.Context
import androidx.room.Room

object DbModule {
    fun db(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "physio.db")
            .fallbackToDestructiveMigration()
            .build()
}
