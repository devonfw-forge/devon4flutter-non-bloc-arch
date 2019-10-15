Page Table of Contents
- [Introduction](#introduction)
- [Intro to BLoC](#intro-to-bloc)
- [Advantages of BLoC](#advantages-of-bloc)
- [Rules of the BLoC Pattern](#rules-of-the-bloc-pattern)
    - [Rules for the BLoCs](#rules-for-the-blocs)
    - [Rules for UI Classes](#rules-for-ui-classes)
- [Implementation](#implementation)
- [Layered Architecure](#layered-architecure)
  - [UI Layer](#ui-layer)
  - [Business Logic Layer](#business-logic-layer)
  - [Repository Layer](#repository-layer)
  - [Data Layer](#data-layer)
- [Architecture in Practice](#architecture-in-practice)

## Introduction

BLoC is a State Management solution that implements an architectural pattern. It was designed by Paolo Soares in 2018 [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE). Its original purpose was to enable code sharing between Flutter [\[1\]](%5B@flutterdevteamFlutterFramework2018%5D) and Angular Dart [\[72\]](https://angulardart.dev/) applications. This chapter will give you an in-depth understanding of what the BLoC Pattern is and how it works. You will learn how to implement it using the BLoC Package [\[39\]](https://felangel.github.io/bloc/#/) by Felix Angelov. And Finally, you will learn how to use the BLoC Pattern to achieve a scalable four-layered architecture for your application.

## Intro to BLoC

When Soares designed the BLoC Pattern, he was working on applications in both Flutter and Angular Dart. He wanted a pattern that enabled him to hook up the same business logic to both Flutter and Angular Dart apps. His idea was to remove business logic from the UI as much as possible and extract it into its own classes, into BLoCs (Business Logic Components). The UI should only send *Events* to BLoCs and display the UI based on the *State* of the BLoCs. Soares defined, that UI and BLoCs should only communicate through Streams [\[40\]](https://dart.dev/tutorials/language/streams). This way the developer would not need to worry about manually telling the UI to redraw. The UI can simply subscribe to a Stream of State [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) emitted by a BLoC and change based on the incoming State \[7\], \[48\], \[53\], \[70\].

| 📙 | BLoC | Business Logic Component [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE) |
| - | ---- | :---------------------------------------------------------------------------- |

| 🕐 | TLDR | The UI should be kept free of business logic. The UI Only publishes *Events* to a BLoC and subscribes to a stream of *State* emitted by a BLoC |
| - | ---- | :--------------------------------------------------------------------------------------------------------------------------------------------- |

![Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/bloc-architecture.png)

*Figure 17: Bloc turning input events to a stream of State [\[70\]](https://www.youtube.com/watch?v=RS36gBEp8OI)*

## Advantages of BLoC

That’s all well and good, but why should you care? An application that follows the rules defined by the BLoC pattern will…

1.  have all its business logic in one place.
2.  have business logic that functions independently of the UI.
3.  have a UI that can be changed without affecting the business Logic.
4.  have business logic that is easily testable.
5.  rely on few rebuilds, as the UI only rebuilds when the State related to that UI changes.

\[7\], \[48\], \[63\], \[64\]

## Rules of the BLoC Pattern

To gain those promised advantages, you will have to follow these 8 rules Soares defined for the BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE):

#### Rules for the BLoCs

1.  Input/Outputs are simple **Sinks/Streams**
2.  All **dependencies** must be **injectable** and platform agnostic
3.  **No platform branching**
      - No `if(IOS) then doThis()`
4.  The actual implementation can be whatever you want if you follow 1-3

#### Rules for UI Classes

1.  Each *“Complex Enough”* Widget has a related BLoC.
      - You will have to define what *“Complex Enough”* means for your app.
2.  Widgets **do not format the inputs** they send to the BLoC.
      - Because formating is Business Logic.
3.  Widgets should display the BLoCs **State/output with as little formatting as possible**.
      - Sometimes a little formatting is inevitable, but things like currency formating is business logic and should be done in the BLoC.
4.  If you do have **platform branching**, It should be dependent on **a single BLoC bool State/output** emitted by a BLoC.

![Bloc Sink and Stream](https://github.com/Fasust/flutter-guide/wiki//images/bloc-sink-stream.png)

*Figure 18: How a BLoC looks like [\[48\]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/)*

## Implementation

Alright, Now that you know what the BLoC pattern is, let’s have a look at how it looks in practice. You will see some strong similarities to the implementation of Redux [\[65\]](https://redux.js.org/) here. That is just because the two patterns are very similar in general. I am using the BLoC package [\[39\]](https://felangel.github.io/bloc/#/) for Flutter by Felix Angelov, as it removes a lot of the boilerplate code we would have to write if we would implement our own BLoCs from scratch. I am going to use the same example of *App State* as I did in the [previous chapter](https://github.com/Fasust/flutter-guide/wiki/210-State-Management-Alternatives): The favorite list in Wisgen [\[11\]](https://github.com/Fasust/wisgen). First, let’s have a look at how the Bloc Pattern will interact with Wisgen on a more abstract scale:

![Bloc and Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-bloc.PNG)

*Figure 19: Bloc and Wisgen Widget Tree [\[11\]](https://github.com/Fasust/wisgen)*

These are the Events that can be sent to the BLoC by the UI. Again, this is very similar to the *Actions* in our Redux implementation:

``` dart
@immutable
abstract class FavoriteEvent {
  final Wisdom _favorite;
  get favorite => _favorite;

  FavoriteEvent(this._favorite);
}

///Adds a given [Wisdom] to the [FavoriteBloc] when dispatched
class FavoriteEventAdd extends FavoriteEvent {
  FavoriteEventAdd(Wisdom favorite) : super(favorite);
}

///Removes a given [Wisdom] from the [FavoriteBloc] when dispatched
class FavoriteEventRemove extends FavoriteEvent {
  FavoriteEventRemove(Wisdom favorite) : super(favorite);
}
```

*Code Snippet 29: Favorite Events in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

Now the arguably most interesting part of an implementation of the BLoC Patter, the BLoC class itself. We extend the BLoC class provided by the Flutter BLoC package. It takes in the type of the *Events* that will be sent to the BLoC and the type of the *State* that will be emitted by the BLoC `Bloc<Event, State>`:

``` dart
///Responsible for keeping track of the Favorite List. 
///
///Receives [FavoriteEvent] to add/remove favorite [Wisdom] objects 
///from its list.
///Broadcasts complete list of favorites every time it changes.
class FavoriteBloc extends Bloc<FavoriteEvent, List<Wisdom>> {
  @override
  List<Wisdom> get initialState => List<Wisdom>();

  //Called when the BLoC receives an Event
  @override
  Stream<List<Wisdom>> mapEventToState(FavoriteEvent event) async* {
    List<Wisdom> newFavorites = List()..addAll(currentState);

    //Determine how to manipulate the State based on the Event
    if (event is FavoriteEventAdd) newFavorites.add(event.favorite);
    if (event is FavoriteEventRemove) newFavorites.remove(event.favorite);

    //Emit new State
    yield newFavorites;
  }
}
```

*Code Snippet 30: Favorite BLoC in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

As I mentioned before, the BLoC package for Flutter uses the Provider package [\[58\]](https://pub.dev/packages/provider). This means that we can provide our BLoC to the rest of our Widget Tree in the same way we learned in the chapter [State Management Alternatives](https://github.com/Fasust/flutter-guide/wiki/210-State-Management-Alternatives). By the rule of *“lifting State up”* we have to place the favorite BLoC at the lowest common ancestor of all Widgets that need access to it. So in our case at *MaterialApp*:

``` dart
void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    //Globally Providing the Favorite BLoC as it is needed on multiple pages
    return BlocProvider(
      builder: (BuildContext context) => FavoriteBloc(),
      child: MaterialApp(home: WisdomFeed()),
    );
  }
}
```

*Code Snippet 31: Providing a BLoC Globally in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

Now we can access the BLoC from all descendance of the *BlocProvider* Widget. This is the favorite button in Wisgen. It changes shape and color based on the State emitted by the FavoriteBLoC and it dispatches events to the BLoC to add and remove favorites. The *wisdom* object is the wisdom displayed on the Card Widget.

``` dart
...
@override
Widget build(BuildContext context) {
  return Expanded(
    flex: 1,
    //This is where we Subscribe to the FavoriteBLoC
    child: BlocBuilder<FavoriteBloc, List<Wisdom>>(
      builder: (context, favorites) => IconButton(
        //Display Icon Button depending on current State
        //Re-build when favorite list changes
        icon: Icon(favorites.contains(wisdom)
            ? Icons.favorite
            : Icons.favorite_border),
        color: favorites.contains(wisdom) 
            ? Colors.red 
            : Colors.grey,
        onPressed: () {
          //Grab FavoriteBloc through BuildContext
          FavoriteBloc favoriteBloc = BlocProvider.of<FavoriteBloc>(context);
          
          //Add/remove Wisdom to/from Favorites (dispatch events)
          if (favorites.contains(wisdom)) favoriteBloc.dispatch(RemoveFavoriteEvent(wisdom));
          else favoriteBloc.dispatch(AddFavoriteEvent(wisdom));  
        },
      ),
    ),
  )
}
...
```

*Code Snippet 32: Accessing a BLoC in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

## Layered Architecure

Now that we understand how to implement the BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE), lets’ have a look at how we can use it to achieve a four-layered architecture with one way dependencies [\[71\]](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1):

<img src="https://github.com/Fasust/flutter-guide/wiki//images/bloc-my-layers.png" height="500" alt="Bloc Architecture with Layers">

*Figure 20: Four-Layered BLoC Architecture*

### UI Layer

This is the layer that our user directly interacts with. It is the Widget Tree of our Application, all Widgets of our app sit here. We need to keep this layer as *stupid* as possible, No business logic and only minor formating.

### Business Logic Layer

This is where all our BLoCs reside. All our business logic sits in this layer. The communication between this layer and the *UI Layer* should be limited to sinks and streams:

![Widget BLoC Communication](https://github.com/Fasust/flutter-guide/wiki//images/widget-bloc-communication.PNG)

*Figure 21: Widget BLoC Communication*

For this Layer, all platform-specific dependencies should be injectable. To achieve this, the Flutter community \[39\], \[57\], \[71\], \[73\] mostly uses the *Repository Patter* [\[74\]](https://dl.acm.org/citation.cfm?id=865128) or as *“Uncle Bob”* would say: *Boundary Objects* [\[75\]](https://www.youtube.com/watch?v=o_TH-Y78tt4). Even if this pattern is not an explicit part of BLoC, I personally think it is a very clean solution. Instead of having BLoCs directly depend on platform-specific interfaces, we create *Repository* interfaces for the BLoCs to depend on:

``` dart
///Generic Repository that fetches a given amount of T
abstract class Supplier<T>{
  Future<T> fetch(int amount, BuildContext context);
}
```

*Code Snippet 33: Wisgen platform-agnostic Repository [\[11\]](https://github.com/Fasust/wisgen)*

The actual implementation of the *Repository* can then be injected into the BLoC.

### Repository Layer

This Layer consist of platform-agnostic interfaces. Things like *Data Base*, *Service*, and *Supplier* (Snippet 33).

### Data Layer

These are the actual implementations of our *Repositories*. Platform-specific things like a Database connector or a class that makes API calls.

## Architecture in Practice

To give you a better understanding of how this architecture works in practice, I will walk you through how Wisgen [\[11\]](https://github.com/Fasust/wisgen) is build using the BLoC Pattern and a four-layered architecture.

![Wisgen Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/wisgen_depencies.PNG)

*Figure 22: Wisgen Architecture with Dependencies [\[11\]](https://github.com/Fasust/wisgen)*

In the UI Layer, we have all the Widgets that make up Wisgen. Three of those actually consume State from the BLoC Layer, so those are the only ones I put in figure 22. The *Wisdom Feed* displays an infinite list of wisdoms. Whenever the user scrolls close to the bottom of the list, the Wisdom Feed sends a *Request-Event* to the Wisdom BLoC [\[51\]](https://felangel.github.io/bloc/#/flutterinfinitelisttutorial). This event causes the *Wisdom BLoC* to fetch more data from its *Repository*. You can see the *Repository* interface in snippet 33. This way the Wisdom BLoC just knows it can fetch some data with its Repository and it does not care where the data comes from or how the data is fetched. In our case, the Repository could be implemented to either load some wisdoms from a local list or fetch some wisdoms from an API. I already covered the implementation of the API Repository class in the chapter [Asynchronous Flutter](https://github.com/Fasust/flutter-guide/wiki/140-Asynchronous-Flutter) if you want to remind yourself again. When the Wisdom BLoC receives a response from it’s Repository, it publishes the new wisdoms to its Stream [\[40\]](https://dart.dev/tutorials/language/streams) and all listening Widgets will be notified.

![Wisgen Bloc Architecture Dataflow](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dataflow.png)

*Figure 23: Wisgen Dataflow [\[11\]](https://github.com/Fasust/wisgen)*

I already covered how the favorite list works in detail in this chapter, so I won’t go over it again. The *Storage BLoC* keeps a persistent copy of the favorite list on the device. It receives a *Load-Event* once on start-up, loads the old favorite list from its *Storage*, and adds it to the *Favorite BLoC* though *Add-Events*. It also listens to the *Favorite BLoC* and updates the persistent copy of the favorite list every time the *Favorite Bloc* emits a new State:

``` dart
///Gives access to the 2 events the [StorageBloc] can receive.
///
///It is an enum, because the 2 events both don't need to carry additional data.
///[StorageEvent.load] tells the [StorageBloc] to load the 
///favorite list from its [Storage].
///[StorageEvent.wipe] tells the [StorageBloc] to wipe 
///any favorites on its [Storage].
enum StorageEvent { load, wipe }

///Responsible for keeping a persistent copy of the favorite list 
///on its [Storage].
///
///It is injected with a [FavoriteBLoC] on creation.
///It subscribes to the [FavoriteBLoC] and writes the favorite list to 
///its [Storage] device every time a new State is emitted by the [FavoriteBLoC].
///When the [StorageBLoC] receives a StorageEvent.load, it loads a list of [Wisdom]s 
///from a its [Storage] device and pipes it into the [FavoriteBLoC] though [FavoriteEventAdd]s
///(This usually happens once on Start-up).
///It's State is [dynamic] because it never needs to emit it.
class StorageBloc extends Bloc<StorageEvent, dynamic> {
  Storage _storage = SharedPreferenceStorage();
  FavoriteBloc _observedBloc;

  StorageBloc(this._observedBloc) {
    //Subscribe to BLoC
    _observedBloc.state.listen((state) async {
      await _storage.save(state);
    });
  }

  @override
  dynamic get initialState => dynamic;

  @override
  Stream<dynamic> mapEventToState(StorageEvent event) async* {
    if (event == StorageEvent.load) await _load();
    if (event == StorageEvent.wipe) _storage.wipeStorage();
  }

  _load() async {
    List<Wisdom> loaded = await _storage.load();

    if (loaded == null || loaded.isEmpty) return;

    loaded.forEach((f) {
      _observedBloc.dispatch(FavoriteEventAdd(f));
    });
  }

  //Injection ---
  set storage(Storage storage) => _storage = storage;
  set observedBloc(FavoriteBloc observedBloc) => _observedBloc = observedBloc;
}
```

*Code Snippet 34: Wisgen Storage BLoC [\[11\]](https://github.com/Fasust/wisgen)*

*Storage* is also a platform-agnostic interface and it looks like this:

``` dart
///Generic Repository for read/write Storage device
abstract class Storage<T>{
  Future<T> load();
  save(T data);

  ///Wipe the Storage Medium of all T
  wipeStorage();
}
```

*Code Snippet 35: Wisgen platform-agnostic Repository: Storage [\[11\]](https://github.com/Fasust/wisgen)*

In Wisgen, I built an implementation of *Storage* that communicates with Androids Shared Preferences [\[76\]](https://developer.android.com/reference/android/content/SharedPreferences) and saves the favorite list as a JSON:

``` dart
///[Storage] that gives access to Androids Shared Preferences 
///(a small, local, persistent key value store).
class SharedPreferenceStorage implements Storage<List<Wisdom>> {
  ///Key is used to access Store
  static const String _sharedPrefKey = "wisgen_storage";

  @override
  Future<List<Wisdom>> load() async {
    final prefs = await SharedPreferences.getInstance();
    List<String> strings = prefs.getStringList(_sharedPrefKey);

    if (strings == null || strings.isEmpty) return null;

    //Decode all JSON Strings we fetched from the Preferences and add them to the Result
    List<Wisdom> wisdoms = List();
    strings.forEach((s) {
      Wisdom w = Wisdom.fromJson(jsonDecode(s));

      wisdoms.add(w);
    });
    return wisdoms;
  }

  @override
  save(List<Wisdom> data) async {
    if (data == null || data.isEmpty) return;

    final prefs = await SharedPreferences.getInstance();

    //Encode data to JSON Strings
    List<String> strings = List();
    data.forEach((wisdom) {
      strings.add(json.encode(wisdom.toJson()));
    });

    //Overwrite Preferences with new List
    prefs.setStringList(_sharedPrefKey, strings);
  }

  @override
  wipeStorage() async {
    final prefs = await SharedPreferences.getInstance();
    prefs.remove(_sharedPrefKey);
  }
}
```

*Code Snippet 36: Wisgen’s Storage implementation for SharedPreferences [\[11\]](https://github.com/Fasust/wisgen)*

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/300-Testing">Next Chapter: Testing ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>