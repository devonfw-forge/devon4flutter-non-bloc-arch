Page Table of Contents
- [Introduction](#introduction)
- [Futures](#futures)
  - [async & await](#async--await)
- [Streams](#streams)
  - [async\* & yield](#async--yield)
- [Side Note on Communication with the Web](#side-note-on-communication-with-the-web)

## Introduction

Asynchronous Programming is an essential part of any modern application. There will always be network calls, user inputs or any number of other unpredictable events that an app has to wait for. Luckily Dart [\[3\]](https://dart.dev/) and Flutter [\[1\]](https://flutter.dev/) have a very good integration of Asynchronous Programming. This chapter will teach you the basics of Futures [\[3\]](https://dart.dev/) and Streams [\[40\]](https://dart.dev/tutorials/language/streams). The latter of these two will be especially important moving forward, as it one of the fundamental technologies used by the BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE). Throughout this chapter, I will be using the *HTTP package* [\[41\]](https://pub.dev/packages/http) to make network requests. Communication with the Web is one of the most common use-cases for Asynchronous Programming, so I thought it would only be fitting.

## Futures

Futures [\[3\]](https://dart.dev/) are the most basic way of dealing with asynchronous code in Flutter. If you have ever worked with JavaScript’s Promises [\[42\]](https://developer.mozilla.org/de/docs/Web/JavaScript/Reference/Global_Objects/Promise) before, they are basically the exact same thing. Here is a small example: This is a simplified version of the Wisgen *ApiSupplier* class. It can make requests to the AdviceSlip API [\[43\]](https://api.adviceslip.com/) to fetch some new advice texts.

``` dart
import 'package:http/http.dart' as http;

class ApiSupplier {
  ///Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() {
    //Define the Future and what the result will look like
    Future<http.Response> apiCall = http.get(_adviceURI); 

    //Define what will happen once it's resolved
    return apiCall.then((response) => Wisdom.fromResponse(response)); 
  }
}
```

*Code Snippet 11: Simplified Wisgen ApiSupplier (Futures) [\[11\]](https://github.com/Fasust/wisgen)*

We call *get()* on the HTTP module and give it the URL it should request. The *get()* method returns a Future. A Future object is a reference to an event that will take place at some point in the *future*. We can give it a callback function with *then()*, that will execute once that event is resolved. The callback we define will get access to the result of the Future IE it’s type: `Future<Type>`. So here, `Future<http.Response> apiCall` is a reference to when the API call will be resolved. Once the call is complete, *then()* will be called and we get access to the *http.Response*. We tell the Future to transform the Response into a wisdom object and return the result, by adding this instruction as a callback to *then()* \[44\], \[45\]. We can also handle errors with the *catchError()* function:

``` dart
import 'package:http/http.dart' as http;

class ApiSupplier {
  ///Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() {
    Future<http.Response> apiCall = http.get(_adviceURI);
    return apiCall
      .then((response) => Wisdom.fromResponse(response))
      .catchError((exception) => Wisdom.Empty);
  }
}
```

*Code Snippet 12: Simplified Wisgen ApiSupplier (Futures with Error) [\[11\]](https://github.com/Fasust/wisgen)*

### async & await

If you have ever worked with Promises or Futures before, you know that this can get really ugly really quickly: callbacks nested in callbacks. Luckily Dart supports the *async & await* keywords [\[46\]](https://dart.dev/codelabs/async-await), which give us the ability to structure our asynchronous code the same way we would if it was synchronous. Let’s take the same example as in snippet 11:

``` dart
import 'package:http/http.dart' as http;

class ApiSupplier {
  ///Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() async {
    http.Response response = await http.get(_adviceURI);
    return Wisdom.fromResponse(response);
  }
}
```

*Code Snippet 13: Simplified Wisgen ApiSupplier (Async & Await) [\[11\]](https://github.com/Fasust/wisgen)*

We can use the *await* keyword to tell Flutter to wait at on specific point until a Future is resolved. In this example, Flutter waits until the *http.Response* has arrived and then proceeds to transform it into a wisdom. If we want to use the *await* keyword in a function, we have to mark the function as *async*. This forces the return type of the function to be a Future, because if we wait during the function, the function can not return instantly, thus it **has** to return a Future [\[47\]](https://www.youtube.com/watch?v=SmTCmDMi4BY). Error handling in async function can be done with *try/catch*:

``` dart
import 'package:http/http.dart' as http;

class ApiSupplier {
  ///Delivers 1 random advice as JSON
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

*Code Snippet 14: Simplified Wisgen ApiSupplier (Async & Await with Error) [\[11\]](https://github.com/Fasust/wisgen)*

## Streams

Streams [\[40\]](https://dart.dev/tutorials/language/streams) are one of the core technologies behind reactive programming [\[48\]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/). And we’ll use them heavily in the chapter [Architecting a Flutter app](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App). But what exactly are *Streams*? As Andrew Brogdon put’s it in one of Google’s official Dart tutorials, Streams are to Future what Iterables are to synchronous data types [\[49\]](https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17&t=345s). You can think of Streams as one continuous flow of data. Data can be put into the Stream, other parties can subscribe/listen to a given Stream and be notified once a new piece of data enters the Stream.

![Data Stream](https://github.com/Fasust/flutter-guide/wiki//images/stream.PNG)

*Figure 10: Data Stream*

Let’s have a look at how we can implement Streams in Dart. First, we initialize a *StreamController* [\[40\]](https://dart.dev/tutorials/language/streams) to generate a new Stream. The StreamController gives us access to a *Sink*, that we can use to put data into the Stream and the actual *Stream* object, which we can use to read data from the Stream:

``` dart
main(List<String> arguments) {

  StreamController<int> _controller = StreamController();

  for(int i = 0; i < 5 ; i++){
    _controller.sink.add(i);
  }

  _controller.stream.listen((i) => print(i));

  _controller.close(); //don't forget to close the stream once you are done
}
```

*Code Snippet 15: Stream of Ints*

``` bash
0
1
2
3
4
```

*Code Snippet 16: Stream of Ints output*

Important Side Note:

| ⚠ | Streams in Dart are single subscription by default. So if you want multiple subscribers you need to initialize it like this: `StreamController streamController = new StreamController.broadcast();` |
| - | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

Let’s have a look at a more complex example. In Wisgen, our wisdoms are delivered to the UI via a Stream. Whenever we run out of wisdoms to display, a request is sent to a class that fetches new wisdom form our API [\[43\]](https://api.adviceslip.com/) and publishes them in a stream. Once the API response comes in, the UI gets notified and receives the new list of wisdoms. This approach is part of the *BLoC Pattern* [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE) and I will explain it in detail in the chapter [Architecting a Flutter app](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App). For now, this is a simplified version of the class that is responsible for streaming wisdom:

``` dart
class WisdomBloc {
  final ApiSupplier _api = new Api();
  List<Wisdom> _oldWisdoms = new List();

  //Stream
  final StreamController _streamController = StreamController<List<Wisdom>>; 
  StreamSink<List<Wisdom>> get _wisdomSink => _streamController.sink; //Data In
  Stream<List<Wisdom>> get wisdomStream => _streamController.stream; //Data out

  ///Called from UI to tell the BLoC to put more wisdom into the Stream
  publishMoreWisdom() async {

    //Fetch a wisdom form the API (Snippet 14)
    Wisdom fetchedWisdom = await _api.fetch();
    

    //Appending the new wisdoms to the current State
    List<Wisdom> newWisdoms = _oldWisdoms..add(fetchedWisdom);

    _wisdomSink.add(newWisdoms); //publish to stream
    _oldWisdoms = newWisdoms;
  }

  ///Called when UI is disposed
  dispose() => _streamController.close();
}
```

*Code Snippet 17: Simplified Wisgen WisdomBLoC [\[11\]](https://github.com/Fasust/wisgen)*

First, we create a *StreamController* and expose the Stream itself to enable the UI to subscribe to it. We also open up a private Sink, so we can easily add new wisdoms to the Stream. Whenever the *publishMoreWisdom()* function is called, the BLoC request a new wisdom from the API, waits until it is fetched, and then publishes it to the Stream. Let’s look at the UI side of things. This is a simplified version of the WisdomFeed in Wisgen:

``` dart
class WisdomFeedState extends State<WisdomFeed> {

  WisdomBloc _wisdomBloc;

  ///We Tell the WisdomBLoC to fetch more data based on how far we have scrolled down
  ///the list. That is why we need this Controller
  final _scrollController = ScrollController();
  static const _scrollThreshold = 200.0;

  @override
  void initState() {
    _wisdomBloc = new WisdomBloc();    
    _wisdomBloc.publishMoreWisdom(); //Dispatch initial event

    _scrollController.addListener(_onScroll);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: StreamBuilder(
        stream: _wisdomBloc.wisdomStream,
        builder: (context, AsyncSnapshot<List<Wisdom>> snapshot) {
          //Show Error message
          if (snapshot.hasError) return ErrorText(state.exception); 
          //Show loading animation
          if (snapshot.connectionState == ConnectionState.waiting) return LoadingSpinner(); 
          //Create ListView of wisdoms
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

  ///Dispatching fetch events to the BLoC when we reach the end of the list
  void _onScroll() {
    final maxScroll = _scrollController.position.maxScrollExtent;
    final currentScroll = _scrollController.position.pixels;
    if (maxScroll - currentScroll <= _scrollThreshold) {
      _wisdomBloc.publishMoreWisdom(); //Publish more wisdoms
    }
  }
  ...
}
```

*Code Snippet 18: Simplified Wisgen WisdomFeed with StreamBuilder [\[11\]](https://github.com/Fasust/wisgen)*

Alright, let’s go through this step by step:

First, we initialize our WisdomBloc in the *initSate()* method. This is also where we set up a ScrollController [\[50\]](https://api.flutter.dev/flutter/widgets/ScrollController-class.html) that we can use to determine how far down the list we have scrolled [\[51\]](https://felangel.github.io/bloc/#/flutterinfinitelisttutorial). I won’t go into the details here, but the controller enables us to call *publishMoreWisdom()* on the WisdomBloc whenever we are near the end of our list. This way we get infinite scrolling.

In the *build()* method, we use Flutter’s *StreamBuilder* Widget [\[52\]](https://api.flutter.dev/flutter/widgets/StreamBuilder-class.html) to link our UI to our Stream. We give it our Stream and it provides a builder method. This builder method receives a Snapshot containing the current State of the Stream whenever the State of the Stream changes IE when new wisdom is added. We can use the Snapshot to determine when the UI needs to display a loading animation, an error message or the actual list. When we receive new wisdoms from our Stream through the Snapshot, we continue to the *listView()* method.

Here we use the list of wisdoms to create a ListView containing cards that display wisdoms. You might have wondered why we Stream a list of wisdoms and not just individual wisdoms. This ListView is the reason. If we where streaming individual wisdoms we would need to combine them into a list here. Streaming a complete list when using a StreamBuilder to create infinite scrolling is also the recommended approach by the Flutter Team [\[53\]](https://www.youtube.com/watch?v=fahC3ky_zW0).

Finally, once the app is closed down, the *dispose()* method is called and we dispose of our Stream and ScrollController.

![Streaming Wisdom from BLoC to WisdomFeed](https://github.com/Fasust/flutter-guide/wiki//images/wisdomBloc-stream.PNG)

*Figure 11: Streaming Wisdom from BLoC to WisdomFeed [\[11\]](https://github.com/Fasust/wisgen)*

### async\* & yield

Streams have two keywords that are very similar to the *async & await* of Futures: *async\* & yield* [\[40\]](https://dart.dev/tutorials/language/streams). If we mark a function as *async\** the return type **has** to be a Stream. In an async\* function we get access to the async keyword (which we already know) and the yield keyword, which is very similar to a return, only that yield does not terminate the function but instead adds a value to the Stream. This is what an implementation of the WisdomBloc from snippet 17 could look like with async\*:

``` dart
Stream<List<Wisdom>> streamWisdoms() async* {
  Wisdom fetchedWisdom = await _api.fetch();

  //Appending the new wisdom to the current State
  List<Wisdom> newWisdoms = _oldWisdoms..add(fetchedWisdom);

  yield newWisdoms; //Publish to Stream

  _oldWisdoms = newWisdoms;
}
```

*Code Snippet 19: Simplified Wisgen WisdomBLoC with async\* [\[11\]](https://github.com/Fasust/wisgen)*

This marks the end of my introduction to Streams. It can be a challenging topic wrap your head around at first so if you still feel like you want to learn more I can highly recommend this article by Didier Boelens [\[48\]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/) or this 8-minute tutorial video by the Flutter Team [\[49\]](https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17&t)

## Side Note on Communication with the Web

I just wanted to end this chapter by showing you how the ApiSupplier class of Wisgen [\[11\]](https://github.com/Fasust/wisgen) actually looks like and give some input of why it looks the way it does:

``` dart
import 'dart:convert';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:wisgen/models/advice_slips.dart';
import 'package:wisgen/models/wisdom.dart';
import 'package:wisgen/repositories/supplier.dart';
import 'package:http/http.dart' as http;

///[Supplier] that cashes [Wisdom]s it fetches from an API and
///then provides a given amount of random entries.
///
///[Wisdom]s Supplied do not have an Image URL
class ApiSupplier implements Supplier<List<Wisdom>> {
  ///Advice SLip API Query that requests all (~213) text entries from the API.
  ///We fetch all entries at once and cash them locally to minimize network traffic.
  ///The Advice Slip API also does not provide the option to request a selected amount of entries.
  ///That's why I think this is the best approach.
  static const _adviceURI = 'https://api.adviceslip.com/advice/search/%20';
  List<Wisdom> _cash;
  final Random _random = Random();

  @override
  Future<List<Wisdom>> fetch(int amount, BuildContext context) async {
    //if the cash is empty, request data from the API
    if (_cash == null) _cash = await _loadData();

    List<Wisdom> res = List();
    for (int i = 0; i < amount; i++) {
      res.add(_cash[_random.nextInt(_cash.length)]);
    }
    return res;
  }

  ///Fetches Data from API and coverts it to wisdoms
  Future<List<Wisdom>> _loadData() async {
    http.Response response = await http.get(_adviceURI);
    AdviceSlips adviceSlips = AdviceSlips.fromJson(json.decode(response.body));

    List<Wisdom> wisdoms = List();
    adviceSlips.slips.forEach((slip) {
      wisdoms.add(slip.toWisdom());
    });

    return wisdoms;
  }
}
```

*Code Snippet 20: Actual Wisgen ApiSupplier [\[11\]](https://github.com/Fasust/wisgen)*

The *AdviceSlips* class was generated with a JSON to Dart converter [\[54\]](https://javiercbk.github.io/json_to_dart/). The generated class has a *fromJson()* function that makes it easy to populate it’s data fields with the JSON response. I used this class instead of implementing a method in the *Wisdom* class because I did not want a direct dependency from my entity class to the AdviceSlip JSON structure. This is the generated class, you don’t need to read it all, I just want to give you an idea of how it looks like:

``` dart
import 'package:wisgen/models/wisdom.dart';

///Generated class to handle JSON input from AdviceSlip API.
///I used this tool: https://javiercbk.github.io/json_to_dart/.
class AdviceSlips {
  String totalResults;
  String query;
  List<Slips> slips;

  AdviceSlips({this.totalResults, this.query, this.slips});

  AdviceSlips.fromJson(Map<String, dynamic> json) {
    totalResults = json['total_results'];
    query = json['query'];
    if (json['slips'] != null) {
      slips = List<Slips>();
      json['slips'].forEach((v) {
        slips.add(Slips.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['total_results'] = this.totalResults;
    data['query'] = this.query;
    if (this.slips != null) {
      data['slips'] = this.slips.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

///Generated class to handle JSON input from AdviceSlip API.
///I used this tool: https://javiercbk.github.io/json_to_dart/.
///A Slip can be converted directly into a Wisdom object (this is a function I added myself)
class Slips {
  String advice;
  String slipId;

  Slips({this.advice, this.slipId});

  Slips.fromJson(Map<String, dynamic> json) {
    advice = json['advice'];
    slipId = json['slip_id'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['advice'] = this.advice;
    data['slip_id'] = this.slipId;
    return data;
  }

  ///Not generated
  Wisdom toWisdom() {
    return Wisdom(
      id: int.parse(slipId),
      text: advice,
      type: "Advice Slip",
    );
  }
}
```

*Code Snippet 21: Wisgen AdviceSlips [\[11\]](https://github.com/Fasust/wisgen)*

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App">Next Chapter: Architecting a Flutter App ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>