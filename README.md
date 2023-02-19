# A sample app to show gallery of the Metropolitan Museum of Art

This is a sample app that is use 'https://metmuseum.github.io/' api to search object list and object detail.

## About Application

I use Kotlin programming language to write classes and xml as a view resource.
It is a modular application so There are 3 modules to implement a clean architecture. App is a presentation layer and also there are Domain and Data layer.
I use Hilt for dependency injection and Coroutine for concurrency.
For loading images I use Coil with storage cache.
There are unit tests for ViewModel and UseCase.


## Tech Stack

* Clean Architecture
* MVVM Architecture
* Coroutines
* Hilt (DI)
* Retrofit (Network)
* Coil (Image Loader)
* Unit Test