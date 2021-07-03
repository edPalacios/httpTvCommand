package com.gompa.buildsrc

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.0-alpha02"
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.0.9"

    const val junit = "junit:junit:4.13"
    const val mockito = "org.mockito:mockito-core:3.9.0"

    const val material = "com.google.android.material:material:1.3.0"

    object Accompanist {
        const val version = "0.12.0"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
    }

    object Kotlin {
        private const val version = "1.5.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.4.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Network {

        object Okhttp {
            private const val okhttpVersion = "4.9.0"
            const val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
            const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
        }
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.3.0"
        const val coreKtx = "androidx.core:core-ktx:1.6.0-beta02"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.0-beta02"
        }

        object Compose {
            const val version = "1.0.0-beta09"

            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsCore = "androidx.compose.material:material-icons-core:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val test = "androidx.compose.ui:ui-test:$version"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:$version"
            const val viewBinding = "androidx.compose.ui:ui-viewbinding:$version"
            const val navigation = "androidx.navigation:navigation-compose:2.4.0-alpha03"
        }

        object Navigation {
            private const val version = "2.3.4"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Test {
            private const val version = "1.3.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
        }

        object Lifecycle {
            private const val version = "2.3.1"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
        }
    }

}