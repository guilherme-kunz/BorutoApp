package com.guilhermekunz.borutoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guilhermekunz.borutoapp.data.local.dao.HeroDao
import com.guilhermekunz.borutoapp.domain.model.Hero

@Database(entities = [Hero::class], version = 1)
abstract class BorutoDatabase: RoomDatabase() {

    abstract fun heroDao(): HeroDao

}