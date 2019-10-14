[intro]: https://github.com/Fasust/flutter-guide/wiki
[framework]: https://github.com/Fasust/flutter-guide/wiki/100-The-Flutter-Framework
[under-hood]: https://github.com/Fasust/flutter-guide/wiki/110-Under-the-Hood
[declarative]: https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively
[tree]: https://github.com/Fasust/flutter-guide/wiki/130-The-Widget-Tree
[async]: https://github.com/Fasust/flutter-guide/wiki/140-Asynchronous-Flutter
[architecture]: https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App
[statemng]: https://github.com/Fasust/flutter-guide/wiki/210-State-Management-Alternatives
[bloc]: https://github.com/Fasust/flutter-guide/wiki/220-BLoC
[test]: https://github.com/Fasust/flutter-guide/wiki/300-Testing
[conventions]: https://github.com/Fasust/flutter-guide/wiki/400-Conventions
[conclusion]: https://github.com/Fasust/flutter-guide/wiki/500-Conclusion
[refs]: https://github.com/Fasust/flutter-guide/wiki/600-References

# 140-Asynchronous-Flutter
## Introduction
Asynchronous Programming is an essential part of any modern application. There will always be network calls, user input or any number of other unpredictable things that your app has to wait for. Luckily Dart [[@dartteamDartProgrammingLanguage2019]](https://dart.dev/) and Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) have a very good integration of Asynchronous Programming. This chapter will teach you the basics of Futures, async/await [[@dartteamDartProgrammingLanguage2019]](https://dart.dev/) and Streams [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams). Throughout this chapter, I will be using the _HTTP package_ [[@dartteamHttpDartPackage2019]](https://pub.dev/packages/http) to make network requests. Communication with the web is one of the most common use-cases for Asynchronous Programming, so I thought it would only be fitting.

## Futures
Futures [[@dartteamDartProgrammingLanguage2019]](https://dart.dev/) are the most basic way of dealing with asynchronous code in Flutter. If you have ever worked with JavaScripts [[@ecmaJavaScriptECMAStandard1997]](https://www.ecma-international.org/publications/standards/Ecma-262.htm) Promises before, they are basically the exact same thing. Here is a small example: This is a simplified version of the Wisgen _ApiSupplier_ class. It can make requests to the AdviceSlip API [[@kissAdviceSlipAPI2019]](https://api.adviceslip.com/) to fetch some new advice texts.

```dart
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
_Code Snippet 11: Wisgen ApiSupplier class (Futures) [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

As you can see, you simply call _get()_ on the HTTP module and give it the URL it should request. The get() method returns a Future. A Future object is a reference to an event that will take place at some point in the _future_. We can give it a callback function with _then()_, that will execute once that event is resolved. The callback we define will get access to the result of the Future IE it's type: `Future<Type>`. So here, the Future object _"apiCall"_ is a reference to when the API call will be resolved. Once the call is complete, _then()_ will be called and we get access to the _http.Response_. We tell the future to transform the Response into a wisdom object and return the result, by adding this instruction as a callback to _then()_ [@googlellcDartFutures2019; @googlellcIsolatesEventLoops2019]. We can also handle errors with the _catchError()_ function:

```dart
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
_Code Snippet 12: Wisgen ApiSupplier Class (Futures with Error) [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

### async & await
If you have ever worked with Promises or Futures before, you know that this can get really ugly really quickly: callbacks nested in callbacks. Luckily Dart supports the _async & await_ keywords [[@dartteamAsynchronousProgrammingDart2018]](https://dart.dev/codelabs/async-await), which give us the ability to structure our asynchronous  code the same way we would if it was synchronous. Let's take the same example as in 
Snippet 11:

```dart
class ApiSupplier {
  ///Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() async {
    http.Response response = await http.get(_adviceURI);
    return Wisdom.fromResponse(response);
  }
}
```
_Code Snippet 13: Wisgen ApiSupplier Class (Async) [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

We can use the _await_ keyword to tell Flutter to wait at on specific point until a Future is resolved. In this example, Flutter waits until the _http.Response_ has arrived and then proceeds to transform it into a Wisdom. If we want to use the await keyword in a function, we have to mark the function as _async_. This forces the return type to be a Future. This makes sense because if we wait during the function, the function will never return instantly, thus it **has** to return a Future [[@googlellcAsyncAwait2019]](https://www.youtube.com/watch?v=SmTCmDMi4BY). Error handling in async function can be done with _try/catch_:

```dart
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
_Code Snippet 14: Wisgen ApiSupplier Class (Async with Error) [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

## Streams
Streams [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams) are one of the core technologies behind reactive programming [[@boelensFlutterReactiveProgramming2018]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/). And we'll use them heavily in the chapter [Architecting a Flutter app][architecture]. But what exactly are _streams_? As Andrew Brogdon put's it in one of Google's official Dart tutorials, Streams are to Future what Iterables are to synchronous data types [[@googlellcDartStreams2019]](https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17&t=345s). You can think of streams as one continuous flow of data. Data can be put into the stream, other parties can subscribe/listen to a given stream and be notified once a new piece of data enters the stream.

![Data Stream](https://github.com/Fasust/flutter-guide/wiki//images/stream.PNG)

_Figure 10: Data Stream_

Okay, but how does it look in Dart code? First, we initialize a SteamBuilder [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams) to generate a new stream. The StreamBuilder gives us access to a _sink_, that we can use to put data into the stream and the actual _stream_, which we can use to read data from the stream:

```dart
main(List<String> arguments) {
  StreamController<int> _controller = StreamController();
  for(int i = 0; i < 5 ; i++){
    _controller.sink.add(i);
  }

  _controller.stream.listen((i) => print(i));

  _controller.close(); //don't forget to close the stream once you are done
}
```
_Code Snippet 15: Stream of Ints_

```bash
0
1
2
3
4
```
_Code Snippet 16: Stream of Ints Output_

Important Side Note: 

| ⚠   | Streams are single subscription by default. So if you want multiple subscribers you need to add `StreamController streamController = new StreamController.broadcast();` |
| --- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------- |


Let's have a look at a more complex example: In Wisgen, our wisdoms are delivered to the Interface via a stream. Whenever we run out of wisdoms to display, a request is sent to a class that fetches new wisdoms form our API [[@kissAdviceSlipAPI2019]](https://api.adviceslip.com/) and publishes them in a stream. Once those new wisdoms come in, the UI gets notified and receives them. This approach is called _BLoC Pattern_ [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE) and I will explain it in detail in the chapter [Architecting a Flutter app][architecture]. For now, this is a simplified version of how that could look like:

```dart
class WisdomBloc {
  final ApiSupplier _api = new Api();
  List<Wisdom> _oldWisdoms = new List();

  //Stream
  final StreamController _streamController = StreamController<List<Wisdom>>; 
  StreamSink<List<Wisdom>> get _wisdomSink => _streamController.sink; //Data In
  Stream<List<Wisdom>> get wisdomStream => _streamController.stream; //Data out

  ///Called from UI to tell the BLoC to put more data into the stream
  publishMoreWisdom() async {
    List<Wisdom> fetchedWisdoms = await _api.fetch(20);

    //Appending the new Wisdoms to the current State
    List<Wisdom> newWisdoms = _oldWisdoms + fetchedWisdoms;

    _wisdomSink.add(newWisdoms); //publish to stream
    _oldWisdoms = newWisdoms;
  }

  ///Called when UI is disposed
  dispose() {
    _streamController.close();
  }
}
```
_Code Snippet 17: Simplified Wisgen WisdomBLoC [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

We create a stream builder in the beginning and expose the stream itself to enable the UI to subscribe to it. We also open up a private sink, so we can easily add thinks to the stream. Whenever the _publishMoreWisdom()_ function is called, the BLoC request more wisdom from the API waits until they are fetched and then publishes them to the stream. Let's look at the UI side of things. This is a simplified version of the WisdomFeed in Wisgen:

```dart
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
          if (snapshot.hasError) return ErrorText(state.exception); 
          //loading animation
          if (snapshot.connectionState == ConnectionState.waiting) return LoadingSpinner(); 
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
_Code Snippet 18: Simplified Wisgen WisdomFeed with StreamBuilder [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Alright, let's go through this step by step. First, we initialize our WisdomBloc in the _initSate()_ method. This is also where we set up a ScrollController [[@flutterdevteamScrollControllerClass2018]](https://api.flutter.dev/flutter/widgets/ScrollController-class.html) that we can use to determine how far down the list we have scrolled [[@angelovFlutterInfiniteList2019]](https://felangel.github.io/bloc/#/flutterinfinitelisttutorial). I won't go into the details here, but the controller enables us to call _publishMoreWisdom()_ on the WisdomBloc whenever we are near the end of our list. This way we get infinite scrolling. In the _build()_ method, we use Flutter's StreamBuilder [[@flutterdevteamStreamBuilderClass2018]](https://api.flutter.dev/flutter/widgets/StreamBuilder-class.html) to link our UI to our stream. We give it our stream and it provides a builder method. This builder has a snapshot containing the current State of the stream. We can use the snapshot to determine when the UI needs to display a loading animation, an error message or the actual list. When we receive the actual list of wisdoms from our stream through the snapshot, we continue to the _listView()_ method. Here we just use the list of wisdoms to create a ListView with WisdomCards. You might have wondered why we stream a List of wisdoms and not just individual wisdoms. This ListView is the reason. If we where streaming individual Wisdoms we would need to combine them into a list here. Streaming a complete list is also recommended by the Flutter team for this use-case [[@sullivanTechnicalDebtStreams2018]](https://www.youtube.com/watch?v=fahC3ky_zW0). Finally, once the app is closed down, the _dispose()_ method is called and we dispose of our stream and ScrollController.

![Streaming Wisdom from BLoC to WisdomFeed](https://github.com/Fasust/flutter-guide/wiki//images/wisdomBloc-stream.PNG)

_Figure 11: Streaming Wisdom from BLoC to WisdomFeed [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

### async* & yield
Streams have two keywords that are very similar to the _async & await_ of Futures: _async* & yield_ [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams). If we mark a function as async* the return type **has** to be a stream. In an async* function we get access to the async keyword (which we already know) and the yield keyword, which is very similar to a return, only that yield does not terminate the function but instead adds a value to the stream. This is what an implementation of the WisdomBloc from snippet 17 could look like when using async*:

```dart
Stream<List<Wisdom>> streamWisdoms() async* {
  List<Wisdom> fetchedWisdoms = await _api.fetch(20);

  //Appending the new Wisdoms to the current State
  List<Wisdom> newWisdoms = _oldWisdoms + fetchedWisdoms;

  yield newWisdoms; //publish to stream
  _oldWisdoms = newWisdoms;
}
```
_Code Snippet 19: Simplified Wisgen WisdomBLoC with async* [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

This marks the end of my introduction to streams. It can be a challenging topic wrap your head around at first so if you still feel like you want to learn more I can highly recommend this article by Didier Boelens [[@boelensFlutterReactiveProgramming2018]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/) or this 8-minute tutorial video by the Flutter Team [[@googlellcDartStreams2019]](https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17&t)

## Side Note on Communication with the Web
I just wanted to end this chapter by showing you how the ApiSupplier class of Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen) actually looks like and give some input of why it looks the way it does:

```dart
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
///[Wisdom]s Supplies do not have an Image URL
class ApiSupplier implements Supplier<List<Wisdom>> {
  ///Advice SLip API Query that requests all (~213) Text Entries from the API.
  //////We fetch all entries at once and cash them locally to minimize network traffic.
  ///The Advice Slip API also does not provide the option to request a selected amount of entries.
  ///That's why I think this is the best approach.
  static const _adviceURI = 'https://api.adviceslip.com/advice/search/%20';
  List<Wisdom> _cash;
  final Random _random = Random();

  @override
  Future<List<Wisdom>> fetch(int amount, BuildContext context) async {
    //if the Cash is empty, request data from the API
    if (_cash == null) _cash = await _loadData();

    List<Wisdom> res = List();
    for (int i = 0; i < amount; i++) {
      res.add(_cash[_random.nextInt(_cash.length)]);
    }
    return res;
  }

  ///Fetches Data from API and coverts it to Wisdoms
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
_Code Snippet 20: Actual Wisgen API Class [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

The _AdviceSlips_ class is generated with a JSON to Dart converter [[@lecuonaJSONDartConverter2019]](https://javiercbk.github.io/json_to_dart/). The generated class has a fromJson function that makes it easy to populate it's data fields with the JSON response. I used this class instead of implementing a method in the _Wisdom_ class because I did not want a direct dependency from my entity class to the AdviceSlip JSON structure. This is the generated class, you don't need to read it all, I just want to give you an idea of how it looks like:

```dart
import 'package:wisgen/models/wisdom.dart';

///Generated Class to Handle JSON Input from AdviceSlip API.
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

///Generated Class to Handle JSON Input from AdviceSlip API.
///I used this tool: https://javiercbk.github.io/json_to_dart/.
///A Slip can be converted directly into a Wisdom.
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

  Wisdom toWisdom() {
    return Wisdom(
      id: int.parse(slipId),
      text: advice,
      type: "Advice Slip",
    );
  }
}
```
_Code Snippet 21: Wisgen AdviceSlips Class [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

