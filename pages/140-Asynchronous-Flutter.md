Page Table of Contents
- [Introduction](#introduction)
- [Futures](#futures)
  - [Async & Await](#async--await)
- [Streams](#streams)
- [Side Note on Communication with the Web](#side-note-on-communication-with-the-web)

## Introduction

Asynchronous Programming is an essential part of any modern application. There will always be network calls, user input or any number of other unpredictable things that your app has to wait for. Luckily Dart [(Dart Team 2019a)](https://dart.dev/) and Flutter [(Flutter Dev Team 2018e)](https://flutter.dev/) have a very good integration of Asynchronous Programming. This chapter will teach you the basics of Futures, async/await [(Dart Team 2019a)](https://dart.dev/) and Streams [(Dart Team 2019b)](https://dart.dev/tutorials/language/streams). Throughout this chapter I will be using the *http* package [(Dart Team 2019c)](https://pub.dev/packages/http) to make network requests. Communication with the web is one of the most common usecases for Asynchronous Programming, so I though it would only be fitting.

## Futures

Futures [(Dart Team 2019a)](https://dart.dev/) are the most basic way of dealing with asynchronous code in Flutter. If you have ever worked with JavaScripts [(ECMA 1997)](https://www.ecma-international.org/publications/standards/Ecma-262.htm) Promises before, they are basically the exact same thing. Here is a small example: This is a simplified version is Wisgens Api Repository. It can make requests to the AdviceSlip API [(Kiss 2019)](https://api.adviceslip.com/) to fetch some new advice texts.

``` dart
class Api {
  //Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() {
    //Define the Future and what the result will look like
    Future<http.Response> apiCall = http.get(_adviceURI); 

    //Define what will happen once it's resolved
    return apiCall.then((response) => Wisdom.fromResponse(response)); 
  }
}
```

*Codesnippt 11: Wisgen API Repository (Futures) [(Faust 2019)](https://github.com/Fasust/wisgen)*

As you can see, you simply call *get()* on the HTTP module and give it the URL it should request. The get() methode returns a Future. A Future object is a reference to an event that will take place at some point in the *future*. We can give it a callback function with *then()*, that will execute once that event is resolved. The callback we define will get access to the result of the Future IE it’s type: `Future<Type>`. So here, the Future object *“apiCall”* is a reference to when the API call will be resolved. Once the call is complete, *then()* will be called and we get access to the *http.Response*. We tell the future to transform the Response into a wisdom object and return the result, by adding this instruction as a callback to *then()* (Google LLC 2019c, 2019b). We can also handle errors with the *catchError()* function:

``` dart
class Api {
  //Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

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

If you have ever worked with Promises or Futures before, you know that this can get really ugly really quickly: callbacks nested in callbacks nested in callbacks. Luckily Dart supports the *async & await* keywords [(Dart Team 2018)](https://dart.dev/codelabs/async-await), which give us the ability to structure our asynchrones code the same way we would if it was synchronous. Let’s take the same example as in
Snippet 11:

``` dart
class Api {
  //Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() async {
    http.Response response = await http.get(_adviceURI);
    return Wisdom.fromResponse(response);
  }
}
```

*Codesnippt 13: Wisgen API Repository (Async) [(Faust 2019)](https://github.com/Fasust/wisgen)*

We can use the *await* keyword to tell Flutter to wait at on specific point until a Future is resolved. In this example Flutter waits until the *http.Response* has arrived and then proceeds to transform it into a Wisdom. If we want to use the await keyword in a function, we have to mark the function as *async*. This forces the return type to be a Future. This makes sense, because if we wait during the function, the function will never return instantly, thus it **has** to return a Future [(Google LLC 2019e)](https://www.youtube.com/watch?v=SmTCmDMi4BY). Error handling in async function can be done with try / catch:

``` dart
class Api {
  //Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

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

## Streams

Streams [(Dart Team 2019b)](https://dart.dev/tutorials/language/streams) are one of the core technologies behind reactive programming (Boelens 2018). And we’ll use them heavily in the chapter [Architecting a Flutter app](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App). But what exactly are *streams*? As Andrew Brogdon put’s it in one of Googles official Dart tutorials, Streams are to Future what Iterables are to synchronous data types [(Google LLC 2019d)](https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17&t=345s). You can think of streams as one continuos flow of data. Data can be put into the stream, other parties can subscribe/listen to a given stream and be notified once a new peace of data enters the stream.

![Data Stream](https://github.com/Fasust/flutter-guide/wiki//images/stream.PNG)

*Figure 10: Data Stream*

Let’s have a look at an example: In Wisgen, our wisdoms are delivered to the Interface via a stream. When ever we run out of wisdoms to display, a request is send to a class that fetches new wisdoms form our API and publishes them in a stream. Once those new wisdoms come in, the UI gets notified and receives them. This approach is called *BLoC Pattern* and I will explain it’s details in the chapter [Architecting a Flutter app](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App). For now, this is a simplified version of how that could look like:

``` dart
class WisdomBloc {
  final Api _api = new Api();
  List<Wisdom> _oldWisdom = new List();

  //Stream
  final StreamController _streamController = StreamController<List<Wisdom>>.broadcast(); 
  StreamSink<List<Wisdom>> get _wisdomSink => _streamController.sink; //Data In
  Stream<List<Wisdom>> get wisdomStream => _streamController.stream; //Data out

  ///Called from UI to tell the BLoC to put more data into the stream
  publishMoreWisdom() async {
    List<Wisdom> fetchedWisdoms = await _api.fetch(20);

    //Appending the new Wisdoms to the current state
    List<Wisdom> newWisdom = _oldWisdom + fetchedWisdoms;

    _wisdomSink.add(newWisdom); //publish to stream
    _oldWisdom = newWisdom;
  }

  ///Called when UI is disposed
  dispose() {
    _streamController.close();
  }
}
```

*Codesnippt 15: Simplified Wisgen WisdomBLoC [(Faust 2019)](https://github.com/Fasust/wisgen)*

We create a stream builder in the beginning and expose the stream itself to enable the UI to subscribe to it. We also open up a private sink, so we can easily add thinks to the stream. When ever the *publishMoreWisdom()* function is called, the BLoC request more wisdom from the API, waits until it is fetched and the publishes it to the stream. Let’s look at the UI side of thing. This is a simplified version of the WisdomFeed in Wisgen:

``` dart
class WisdomFeedState extends State<WisdomFeed> {

  WisdomBloc _wisdomBloc;

  //We Tell the WisdomBLoC to fetch more data based on how far we have scrolled down
  //the list. That is why we need this Controller
  final _scrollController = ScrollController();
  static const _scrollThreshold = 200.0;

  @override
  void initState() {
    _wisdomBloc = new WisdomBloc();    
    _wisdomBloc.publishMoreWisdom(); //Dispatch Initial Events

    _scrollController.addListener(_onScroll);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: StreamBuilder(
        stream: _wisdomBloc.wisdomStream,
        builder: (context, AsyncSnapshot<List<Wisdom>> snapshot) {
          //show Error message
          if (snapshot.hasError) return _error(); 
          //loading animation
          if (snapshot.connectionState == ConnectionState.waiting) return _loading(context); 
          //create listView of wisdoms
          else return _listView(context, snapshot.data); 
        },
      ),
    );
  }

  Widget _listView(BuildContext context, List<Wisdom> wisdoms) {
    return ListView.builder(
      itemBuilder: (BuildContext context, int index) {
        return index >= wisdoms.length
            ? LoadingCard()
            : WisdomCard(wisdom: wisdoms[index]);
      },
      itemCount: wisdoms.length + 1,
      controller: _scrollController,
    );
  }

  @override
  void dispose() {
    _wisdomBloc.dispose();
    _scrollController.dispose();
    super.dispose();
  }

  ///Dispatching fetch events to the BLoC when we reach the end of the List
  void _onScroll() {
    final maxScroll = _scrollController.position.maxScrollExtent;
    final currentScroll = _scrollController.position.pixels;
    if (maxScroll - currentScroll <= _scrollThreshold) {
      _wisdomBloc.publishMoreWisdom();
    }
  }
  ...
}
```

*Codesnippt 16: Simplified Wisgen WisdomFeed with StreamBuilder [(Faust 2019)](https://github.com/Fasust/wisgen)*

Alright, let’s go through this step by step. First we initialize our WisdomBloc in the *initSate()* methode. This is also where we set up a ScrollController that we can use to determine how far down the list we have scrolled. I wont go into the details here, but the controller enables us to call *publishMoreWisdom()* on the WisdomBloc when ever we are near the end ouf our list. This way we get infinite scrolling. In the *build()* methode, we use Flutters StreamBuilder to link our UI to our stream. We give it our stream and it provides a builder method. This builder has a snapshot containing the current state of the stream. We can use the snapshot to determine when the UI needs to display a loading animation, an error message or the actual list. When we receive the actual list of wisdoms from our stream through the snapshot, we continue to the \*\_listView()\* methode. Here we just use the list of wisdoms to create a ListView with WisdomCards. You might have wondered why we stream a List of wisdoms and not just individual wisdoms. This ListView is the reason. If we where streaming individual Wisdoms we would need to combine them into a list here. Streaming a compleat list is also recommended by the Flutter team for this usecase. Finally, once the app is closed down, the *dispose()* methode is called and we dispose our stream and ScrollController.

### yield

  - yield example (Wisdom BLoC)
  - mainpultion options (brief)

## Side Note on Communication with the Web

I just wanted to end this chapter with showing you how the API Repository of Wisgen [(Faust 2019)](https://github.com/Fasust/wisgen) actually looks like and give some input of why it looks the way it does:

``` dart
import 'dart:convert';
import 'dart:math';

import 'package:flutter/src/widgets/framework.dart';
import 'package:wisgen/models/advice_slips.dart';
import 'package:wisgen/models/wisdom.dart';
import 'package:wisgen/repositories/repository.dart';
import 'package:http/http.dart' as http;

///Repository that cashes data it fetches from an API and
///then Provides a given amount of random entries.
class Api implements Repository<Wisdom> {
  ///Advice SLip API Query that requests all (~213) Text Entries from the API.
  ///We fetch all entries ad once and cash them locally to minimize network traffic.
  ///The Advice Slip API also does not provide the option to request a 
  ///selected amount of entries.
  ///That's why I think this is the best approach.

  ///Delivers all entries of the AdviceSlip API
  static const _adviceURI = 'https://api.adviceslip.com/advice/search/%20';
  List<Wisdom> _cash;
  final Random _random = new Random();

  @override
  Future<List<Wisdom>> fetch(int amount, BuildContext context) async {
    //if the Cash is empty, request data from the API
    if (_cash == null) _cash = await _loadData();

    //return requested amount of random Wisdoms
    List<Wisdom> res = new List();
    for (int i = 0; i < amount; i++) {
      res.add(_cash[_random.nextInt(_cash.length)]);
    }
    return res;
  }

  ///I changed this function for the Snippets in the Guide
  ///Fetches Data from API and coverts it to Wisdoms
  Future<List<Wisdom>> _loadData() async {
    http.Response response = await http.get(_adviceURI);
    AdviceSlips adviceSlips = AdviceSlips.fromJson(json.decode(response.body));

    List<Wisdom> wisdoms = new List();
    adviceSlips.slips.forEach((slip) {
      wisdoms.add(slip.toWisdom());
    });

    return wisdoms;
  }
}
```

*Codesnippt XXX: Actual Wisgen API Repository [(Faust 2019)](https://github.com/Fasust/wisgen)*

The AdviceSlips class, is generated with a JSON to Dart converter [(Lecuona 2019)](https://javiercbk.github.io/json_to_dart/). The generated class has a fromJson function that makes it easy to populate it’s data fields with the JSON response. I used this class instead of implementing a method in the Wisdom class, because I did not want a direct dependency from my entity class to the AdviceSlip JSON structure. This is the generated class, you don’t need to read it all, I just want to give you an idea of how it looks like:

``` dart
class AdviceSlips {
  String totalResults;
  String query;
  List<Slips> slips;

  AdviceSlips({this.totalResults, this.query, this.slips});

  AdviceSlips.fromJson(Map<String, dynamic> json) {
    totalResults = json['total_results'];
    query = json['query'];
    if (json['slips'] != null) {
      slips = new List<Slips>();
      json['slips'].forEach((v) {
        slips.add(new Slips.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['total_results'] = this.totalResults;
    data['query'] = this.query;
    if (this.slips != null) {
      data['slips'] = this.slips.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Slips {
  String advice;
  String slipId;

  Slips({this.advice, this.slipId});

  Slips.fromJson(Map<String, dynamic> json) {
    advice = json['advice'];
    slipId = json['slip_id'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['advice'] = this.advice;
    data['slip_id'] = this.slipId;
    return data;
  }

  //I wrote this function myself to make it easy to cast 
  //slips into my own Wisdom data structure.
  Wisdom toWisdom() {
    return new Wisdom(
      id: int.parse(slipId),
      text: advice,
      type: "Advice Slip",
    );
  }
}
```

*Codesnippt XXX: Wisgen AdviceSlips Class [(Faust 2019)](https://github.com/Fasust/wisgen)*

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App">Next Chapter: Architecting a Flutter App ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>