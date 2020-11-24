package com.demo.mvvm.app

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

/**
 * @HiltAndroidApp triggers Hilt's code generation, including a base class for your application
 * that serves as the application-level dependency container.
 */
@HiltAndroidApp
class App: MultiDexApplication() {
}