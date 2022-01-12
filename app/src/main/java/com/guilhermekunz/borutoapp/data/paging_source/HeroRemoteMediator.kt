package com.guilhermekunz.borutoapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.guilhermekunz.borutoapp.data.local.BorutoDatabase
import com.guilhermekunz.borutoapp.data.remote.BorutoApi
import com.guilhermekunz.borutoapp.domain.model.Hero
import com.guilhermekunz.borutoapp.domain.model.HeroRemoteKeys
import javax.inject.Inject

@ExperimentalPagingApi
class HeroRemoteMediator @Inject constructor(
    private val borutoApi: BorutoApi,
    private val borutoDatabase: BorutoDatabase
) : RemoteMediator<Int, Hero>() {

    private val heroDao = borutoDatabase.heroDao()
    private val heroRemoteKeysDao = borutoDatabase.heroRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Hero>): RemoteMediator.MediatorResult {
        return try {
            val response = borutoApi.getAllHeroes(page = )
             if (response.heroes.isNotEmpty()){
                 borutoDatabase.withTransaction {
                     if (loadType == LoadType.REFRESH) {
                         heroDao.deleteAllHeroes()
                         heroRemoteKeysDao.deleteAllRemoteKeys()
                     }
                     val prevPage = response.prevPage
                     val nextPage = response.nextPage
                     val keys = response.heroes.map { hero ->
                         HeroRemoteKeys(
                             id = hero.id,
                             prevPage = prevPage,
                             nextPage = nextPage
                         )
                     }
                     heroRemoteKeysDao.addAllRemoteKeys(heroRemoteKeys = keys)
                     heroDao.addHeroes(heroes = response.heroes)
                 }
             }
             MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
         } catch (e: Exception){
             return MediatorResult.Error(e)
         }
    }

}