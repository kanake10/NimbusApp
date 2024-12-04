# NimbusApp

NimbusApp App using [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/), in 100% Kotlin, using Android Jetpack Components, and in Compose :rocket:

The Nimbus App is a user-friendly Android application built using Kotlin and Jetpack Compose to display historical weather data. It provides an intuitive calendar-based interface for selecting dates and viewing detailed hourly weather conditions. Designed with offline functionality, the app ensures seamless user experience even without internet access.

## Tech-stack
* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a modern, cross-platform, statically typed, general-purpose programming language with type inference.
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - lightweight threads to perform asynchronous tasks.
    * [Room Database](https://kotlinlang.org/docs/reference/coroutines-overview.html) - lightweight library used to locally store our data.
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - a stream of data that emits multiple values sequentially.
    * [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#:~:text=StateFlow%20is%20a%20state%2Dholder,property%20of%20the%20MutableStateFlow%20class.) - Flow APIs that enable flows to emit updated state and emit values to multiple consumers optimally.
    * [Dagger Hilt](https://dagger.dev/hilt/) - a dependency injection library for Android built on top of [Dagger](https://dagger.dev/) that reduces the boilerplate of doing manual injection.
    * [Gson](https://github.com/google/gson) A Java and Kotlin serialization/deserialization library to convert Kotlin/Java Objects into JSON and back
    * [Jetpack](https://developer.android.com/jetpack)
        * [Jetpack Compose](https://developer.android.com/jetpack/compose) - A modern toolkit for building native Android UI
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform actions in response to a change in the lifecycle state.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data lifecycle in a conscious manner and survive configuration change.


* Architecture
    * MVVM—Model View ViewModel pattern with layer-based modularization. 
  
* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - An alternative syntax for writing Gradle build scripts using Koltin.
* CI/CD
    * [GitHub Actions](https://github.com/features/actions)
