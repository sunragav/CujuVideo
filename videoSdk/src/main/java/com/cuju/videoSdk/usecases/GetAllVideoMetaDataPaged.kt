package com.cuju.videoSdk.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.cuju.videoSdk.domain.models.VideoMetaData
import com.cuju.videoSdk.mappers.VideoMetaDataEntityToDomainMapper
import com.cuju.videoSdk.repostories.VideoMetaDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllVideoMetaDataPaged(private val videoMetaDataRepository: VideoMetaDataRepository) {
    operator fun invoke(): Flow<PagingData<VideoMetaData>> = Pager(
        PagingConfig(
            pageSize = 10,
            prefetchDistance = 20
        )
    ) {
        videoMetaDataRepository.getAllPaged()
    }.flow.map { pagingData ->
        pagingData.map(
            VideoMetaDataEntityToDomainMapper::map
        )
    }
}
