package com.wildan.subs1intermediet.Module

import com.wildan.subs1intermediet.utils.ManagerPreference
import org.koin.dsl.module

val modulePreference = module {
        single { ManagerPreference(get()) }
}