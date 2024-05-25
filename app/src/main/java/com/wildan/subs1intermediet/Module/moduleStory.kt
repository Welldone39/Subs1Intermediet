package com.wildan.subs1intermediet.Module

import com.wildan.subs1intermediet.data.repository.StoryRepository
import com.wildan.subs1intermediet.data.repository.StoryRepositoryImpl
import org.koin.dsl.module

val moduleStory = module {
    single<StoryRepository> { StoryRepositoryImpl(get()) }
}