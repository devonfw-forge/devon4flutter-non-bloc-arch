Page Table of Contents
- [Introduction](#introduction)
- [Futures](#futures)
- [Async & Await](#async--await)
- [FutureBuilder](#futurebuilder)
- [Yield](#yield)

## Introduction

Asynchronous Programming is an essential part of any modern application. There will always be network calls, user input or any number of other unpredictable things that your app has to wait for. Luckily Dart (Dart Team 2019a) and Flutter (Flutter Dev Team 2018e) have a very good integration for Asynchronous Programming. This chapter will teach you the basics of Futures (Dart Team 2019a), Streams (Dart Team 2019b) and how to connect those to your UI.

## Futures

Futures are the most basic way of dealing with asynchronous code. If you have ever worked with JavaScripts (ECMA 1997) Promises before, they are basically the exact same thing. Here is a small example, this is a simplified version is Wisgens Api Repository. It can make a request to the AdviceSlip API [(Kiss 2019)](https://api.adviceslip.com/) to fetch some new advice texts. In this Example it just prints the wisdom to the console.

``` dart
import 'package:http/http.dart' as http;

class Api implements Repository<Wisdom> {
  static const _adviceURI = 'https://api.adviceslip.com/advice/search/%20';
  
  makeWisdom(){
    Future apiCall = http.get(_adviceURI);
    
    apiCall.then((response) => () {
        print(json.decode(response.body));
    });
  }
}
```

*Codesnippt 11: Wisgen API Repository [(Faust 2019)](https://github.com/Fasust/wisgen)*

### FutureBuilders

### Async & Await

## Streams

### Yield

### StreamBuilders

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/150-Communication-with-the-Web">Next Chapter: Architecting a Flutter App ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>