package com.tryzens.task.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.tryzens.task.base.BaseApplication

class CustomRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader,
        className: String,
        context: Context
    ): Application {
        return super.newApplication(
            cl,
            BaseApplication::class.java.name,
            context
        )
    }
}