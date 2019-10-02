Page Table of Contents
- [Introduction](#introduction)
- [Futures](#futures)
- [Async & Await](#async--await)
- [FutureBuilder](#futurebuilder)
- [Yield](#yield)

## Introduction

Asynchronous Programming is an essential part of any modern application. There will always be network calls, user input or any number of other unpredictable things that your app has to wait for. Luckily Dart (Dart Team 2019a) and Flutter (Flutter Dev Team 2018e) have a very good integration for Asynchronous Programming. This chapter will teach you the basics of Futures, async/await(Dart Team 2019a) and Streams (Dart Team 2019b). Throughout this chapter I will be using the *http* package [(Dart Team 2019c)](https://pub.dev/packages/http) to make network requests. Communication with the web is one of the most common usecases for Asynchronous Programming, so I though it would only be fitting.

## Futures

Futures (Dart Team 2019a) are the most basic way of dealing with asynchronous code. If you have ever worked with JavaScripts (ECMA 1997) Promises before, they are basically the exact same thing. Here is a small example, this is a simplified version is Wisgens Api Repository. It can make a request to the AdviceSlip API [(Kiss 2019)](https://api.adviceslip.com/) to fetch some new advice texts.

``` dart
class Api {
  static const _adviceURI = 'https://api.adviceslip.com/advice'; //Delivers 1 random advice as JSON

  Future<Wisdom> fetch() {
    //Define the Future and what the result will look like
    Future<http.Response> apiCall = http.get(_adviceURI); 

    //Define what will happen once it's resolved
    return apiCall.then((response) => Wisdom.fromResponse(response)); 
  }
}
```

*Codesnippt 11: Wisgen API Repository (Futures) [(Faust 2019)](https://github.com/Fasust/wisgen)*

As you can see, you simply call *get()* on the HTTP module and give it the URL it should request. The get() methode returns a Future. A Future object is a reference to an event that will take place at some point in the *future*. We can assinge it a callback function with *then()* that will execute once that event is resolved. The callback we define will get access to the result of the Future IE the type `Future<Type>`. So here, the Future *apiCall* object is a reference to when the API call will be resolved. Once the call is complete, *then* will be called and we get access to the http.Response. We then tell the future to transform the Response into a wisdom object (Google LLC 2019c, 2019b). We can also handle errors with the *catchError()* function:

``` dart
class Api {
  static const _adviceURI = 'https://api.adviceslip.com/advice'; //Delivers 1 random advice as JSON

  Future<Wisdom> fetch() {
    Future<http.Response> apiCall = http.get(_adviceURI);
    return apiCall
      .then((response) => Wisdom.fromResponse(response))
      .catchError((exception) => Wisdom.Empty);
  }
}
```

*Codesnippt 12: Wisgen API Repository (Futures with Error) [(Faust 2019)](https://github.com/Fasust/wisgen)*

### Async & Await

If you have ever worked with Promises or Futures before, you know that this can get really ugly really quickly: callbacks in callbacks in callbacks. Luckily Dart supports the Async & Await keywords, which give us the ability to structure our asynchrones code the same way we would if it was synchronous. Let’s take the same example as in
Snippet 11.

``` dart
class Api {
  static const _adviceURI = 'https://api.adviceslip.com/advice'; //Delivers 1 random advice as JSON

  Future<Wisdom> fetch() async {
    http.Response response = await http.get(_adviceURI);
    return Wisdom.fromResponse(response);
  }
}
```

*Codesnippt 13: Wisgen API Repository (Async) [(Faust 2019)](https://github.com/Fasust/wisgen)*

We can use the *await* keyword to tell Flutter to wait at on specific point until a Future is resolved. In this example Flutter waits until the *http.Response* has arrived and then proceeds to transform it into a Wisdom. If we want to use the await keyword in a function, we have to mark it as *async*. This forces the return type to be a Future. Because if we wait during the function, the function will never return instantly, thus it **has** to return a Future. Error handling in async function can be done with try / catch:

``` dart
class Api {
  static const _adviceURI = 'https://api.adviceslip.com/advice'; //Delivers 1 random advice as JSON

  Future<Wisdom> fetch() async {
    try {
      http.Response response = await http.get(_adviceURI);
      return Wisdom.fromResponse(response);
    } catch (exception) {
      return Wisdom.Empty;
    }
  }
}
```

*Codesnippt 14: Wisgen API Repository (Async with Error) [(Faust 2019)](https://github.com/Fasust/wisgen)*
\#\# Streams

### Yield

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/150-Communication-with-the-Web">Next Chapter: Architecting a Flutter App ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>