package com.wildan.subs1intermediet.Module


import com.wildan.subs1intermediet.data.repository.AuthRepositoryImpl
import com.wildan.subs1intermediet.data.repository.RepositoryAuth
import org.koin.dsl.module

val moduleAuth = module {
    single<RepositoryAuth> { AuthRepositoryImpl(get(), get())  }

}