# A sample app to show PIXABAY image

This is a sample app that is use Pixabay api to show image list and image detail.

## About Application

I use Kotlin programming language to write classes and xml as a view resource.
It a modular application so There are 3 modules to implement a clean architecture. App is a presentation layer and also there are Domain and Data layer.
I use Hilt for dependency injection and Coroutine for concurrency.
For loading images I use Coil with storage cache.
For cache the api response I use room.
There is a test for ViewModel.


## Tech Stack

* Clean Architecture
* MVVM Architecture
* Coroutines
* Hilt (DI)
* Retrofit (Network)
* Coil (Image Loader)
* Room (ORM)
* Unit Test