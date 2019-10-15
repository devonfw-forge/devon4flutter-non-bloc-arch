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

# 220-BLoC

## Introduction
BLoC is a State Management solution that implements an architectural pattern originally designed by Paolo Soares in 2018 [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE). Its original purpose was to enable code sharing between Flutter [[@flutterdevteamFlutterFramework2018]]([@flutterdevteamFlutterFramework2018]) and Angular Dart [[@googlellcAngularDart2018]](https://angulardart.dev/) applications. This chapter will give you an in-depth understanding of what the BLoC Pattern is and how it works. You will learn how to implement it using the BLoC Package [[@angelovBlocLibraryDart2019]](https://felangel.github.io/bloc/#/) by Felix Angelov. And Finally, you will learn how to use the BLoC Pattern to achieve a scalable four-layered architecture for your application.

## Intro to BLoC
When Soares designed the BLoC Pattern, he was working on applications in both Flutter and Angular Dart. He wanted a pattern that enabled him to hook up the same business logic to both Flutter and Angular Dart apps. His idea was to remove business logic from the UI as much as possible and extract it into its own classes, into BLoCs (Business Logic Components). The UI should only send events to BLoCs and display the interface based on the State of the BLoCs. Soares defined, that UI and BLoCs should only communicate through streams [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams). This way the developer would not need to worry about manually telling the UI to redraw. The UI can simply subscribe to a stream of State [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) emitted by a BLoC and change based on the incoming State [@sullivanBuildReactiveMobile2018; @sullivanTechnicalDebtStreams2018; @soaresFlutterAngularDartCode2018; @boelensFlutterReactiveProgramming2018].

| 📙  | BLoC | Business Logic Component [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE) |
| --- | ---- | :---------------------------------------------------------------------------------------------------------- |

| 🕐  | TLDR | The UI should be kept free of business logic. The UI Only publishes _Events_ to a BLoC and subscribes to a stream of _State_ emitted by a BLoC |
| --- | ---- | :--------------------------------------------------------------------------------------------------------------------------------------------- |

![Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/bloc-architecture.png)

_Figure 17: Bloc turning input events to a stream of State [[@sullivanBuildReactiveMobile2018]](https://www.youtube.com/watch?v=RS36gBEp8OI)_

## Advantages of BLoC
That's all well and good, but why should you care? An application that follows the rules defined by the BLoC pattern will...

1. have all its business logic in one place 
2. have business logic that functions independently of the UI
3. have UI that can be changed without affecting the business Logic
4. have business logic that is easily testable
5. rely on few rebuilds, as the UI only rebuilds when the State related to that UI changes

[@boelensFlutterBLoCScopedModel2019; @savjolovsFlutterAppArchitecture2019; @soaresFlutterAngularDartCode2018; @boelensFlutterReactiveProgramming2018]

## Rules of the BLoC Pattern
To gain those promised advantages, you will have to follow these 8 rules Soares defined for the BLoC Pattern [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE):

#### Rules for the BLoCs

   1. Input/Outputs are simple **Sinks/Streams**
   2. All **dependencies** must be **injectable** and platform agnostic
   3. **No platform branching**
      - No `if(IOS) then doThis()`
   4. The actual implementation can be whatever you want if you follow 1-3

#### Rules for UI Classes

  1. Each _"Complex Enough"_ Widget has a related BLoC
     - You will have to define what _"Complex Enough"_ means for your app.
  2. Widgets **do not format the inputs** they send to the BLoC
     - Because formating is Business Logic
  3. Widgets should display the BLoCs **State/output with as little formatting as possible**
     - Sometimes a little formatting is inevitable, but things like currency formating is business logic and should be done in the BLoC 
  4. If you do have **platform branching**, It should be dependent on **a single BLoC bool State/output**

![Bloc Sink and Stream](https://github.com/Fasust/flutter-guide/wiki//images/bloc-sink-stream.png)

_Figure 18: How a BLoC looks like [[@boelensFlutterReactiveProgramming2018]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/)_

## Implementation
Alright, Now that you know what the BLoC pattern is, let's have a look at how it looks in practice. You will see some strong similarities to the implementation of Redux [[@abramovRedux2015]](https://redux.js.org/) here. That is just because the two patterns are very similar in general. I am using the BLoC package [[@angelovBlocLibraryDart2019]](https://felangel.github.io/bloc/#/) for Flutter by Felix Angelov, as it removes a lot of the boilerplate code we would have to write if we would implement our own BLoCs from scratch. I am going to use the same example of _App State_ as I did in the [previous chapter][statemng]: The favorite list in Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen). First, let's have a look at how the Bloc Pattern will interact with Wisgen on a more abstract scale:

![Bloc and Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-bloc.PNG)

_Figure 19: Bloc and Wisgen Widget Tree [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

These are the events that can be sent to the BLoC by the UI. Again, this is very similar to the _actions_ in our Redux implementation:

```dart
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
_Code Snippet 29: Favorite Events in Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Now the arguably most interesting part of an implementation of the BLoC patter, the BLoC class itself. We extend the BLoC class provided by the Flutter BLoC package. It takes in the type of the _events_ that will be sent to the BLoC and the type of the _State_ that should be emitted by the BLoC `Bloc<Event, State>`:

```dart
///Responsible for keeping track of the Favorite List. 
///
///Receives [FavoriteEvent] to add/remove favorite [Wisdom] objects 
///from its list.
///Broadcasts complete list of favorites every time it changes.
class FavoriteBloc extends Bloc<FavoriteEvent, List<Wisdom>> {
  @override
  List<Wisdom> get initialState => List<Wisdom>();

  @override
  Stream<List<Wisdom>> mapEventToState(FavoriteEvent event) async* {
    List<Wisdom> newFavorites = List()..addAll(currentState);

    if (event is FavoriteEventAdd) newFavorites.add(event.favorite);
    if (event is FavoriteEventRemove) newFavorites.remove(event.favorite);

    yield newFavorites;
  }
}
```
_Code Snippet 30: Favorite BLoC in Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

As I mentioned before, the BLoC package for Flutter uses the Provider package [[@rousseletProviderFlutterPackage2018]](https://pub.dev/packages/provider). This means that we can provide our BLoC to the rest of our Widget Tree in the same way we learned in the chapter [State Management Alternatives][statemng]. By the rule of _"lifting State up"_ we have to place the favorite BLoC at the lowest common ancestor of all Widgets that need access to it. So in our case at _MaterialApp_:

```dart
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
_Code Snippet 31: Providing a BLoC Globally in Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Now we can dispatch events and subscribe to a BLoC. This is the favorite button in Wisgen. It changes shape and color based on the State emitted by the FavoriteBLoC and it dispatches events to the BLoC to add and remove favorites. The _wisdom_ object is the wisdom displayed on the Card Widget.

```dart
...
@override
Widget build(BuildContext context) {
  return Expanded(
    flex: 1,
    //This is where we Subscribe to the FavoriteBLoC
    child: BlocBuilder<FavoriteBloc, List<Wisdom>>(
      builder: (context, favorites) => IconButton(
        //Display Icon Button depending on current State
        //Re-Build when favorite list changes
        icon: Icon(favorites.contains(wisdom)
            ? Icons.favorite
            : Icons.favorite_border),
        color: favorites.contains(wisdom) 
            ? Colors.red 
            : Colors.grey,
        onPressed: () {
          //Grab FavoriteBloc though Buildcontext
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
_Code Snippet 32: Accessing a BLoC in Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

## Layered Architecure
Now that we understand how to implement the BLoC pattern [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE), lets' have a look at how we can use it to achieve a four-layered architecture with one way dependencies [[@suriArchitectYourFlutter2019]](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1):

<img src="https://github.com/Fasust/flutter-guide/wiki//images/bloc-my-layers.png" height="500" alt="Bloc Architecture with Layers">

_Figure 20: Four-Layered BLoC Architecture_

### UI Layer
This is the layer that our user directly interacts with. It is the Widget Tree of our Application, all Widgets of our app sit here. We need to keep this layer as _stupid_ as possible, No business logic and only minor formating.

### Business Logic Layer
This is where all our BLoCs reside. All our business logic sits in this layer. The communication between this layer and the _UI Layer_ should be limited to sinks and streams:

![Widget BLoC Communication](https://github.com/Fasust/flutter-guide/wiki//images/widget-bloc-communication.PNG)

_Figure 21: Widget BLoC Communication_

For this Layer, all plattform specific dependencies should be injectable. To achieve this, the Flutter community [@suriArchitectYourFlutter2019; @eganFlutterArchitectureSamples2017; @angelovBlocLibraryDart2019; @bizzottoWidgetAsyncBlocServicePracticalArchitecture2019] mostly uses the _Repository Patter_ [[@garlanIntroductionSoftwareArchitecture1994]](https://dl.acm.org/citation.cfm?id=865128) or as _"Uncle Bob"_ would say: _Boundary Objects_ [[@martinPrinciplesCleanArchitecture2015]](https://www.youtube.com/watch?v=o_TH-Y78tt4). Even if this pattern is not an explicit part of BLoC, I personally think it is a very clean solution. Instead of having BLoCs directly depend on plattform specific interfaces, we create simple _Repository_ interfaces for the BLoCs to depend on:

```dart
///Generic Repository that fetches a given amount of T
abstract class Supplier<T>{
  Future<T> fetch(int amount, BuildContext context);
}
```
_Code Snippet 33: Wisgen Plattform Agnostic Repository [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

The actual implementation of the _Repository_ can then be injected into the BLoC.

### Repository Layer
This Layer consist of plattform agnostic interfaces. Things like _Data Base_ or _Service_.

### Data Layer
These are the actual implementations of our _Repositories_. Platform specific things like a Database connector or a class to make API calls.

## Architecture in Practice 
To give you a better understanding of how this architecture works in practice, I will walk you through how Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen) is build using the BLoC Pattern and a Four-layered architecture.

![Wisgen Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/wisgen_depencies.PNG)

_Figure 22: Wisgen Architecture with Dependencies [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

In the UI Layer, we have all the Widgets that make up Wisgen. Three of those actually consume State from the BLoC Layer, so those are the only ones I put in figure 22. The _Wisdom Feed_ displays an infinite list of wisdoms. Whenever the user scrolls close to the bottom of the list, the Wisdom Feed sends a _Request-Event_ to the Wisdom BLoC [[@angelovFlutterInfiniteList2019]](https://felangel.github.io/bloc/#/flutterinfinitelisttutorial). This event causes the _Wisdom BLoC_ to fetch more data from its Repository. You can see the _Repository_ interface in snippet 33. This way the Wisdom BLoC just knows it can fetch some data with its Repository and it does not care where the data comes from or how the data is fetched. In our case, the Repository could be implemented to either load some wisdoms from a local list or fetch some wisdoms from an API. I already covered the implementation of the API Repository class in the chapter [Asynchronous Flutter][async] if you want to remind yourself again. When the Wisdom BLoC receives a response from it's Repository, it publishes the new wisdoms to its Stream [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams) and all listening Widgets will be notified. 

![Wisgen Bloc Architecture Dataflow](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dataflow.png)

_Figure 23: Wisgen Dataflow [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

I already covered how the favorite list works in detail in this chapter, so I won't go over it again. The _Storage BLoC_ keeps a persistant copy of the favorite list on the device. It recievce a _Load-Event_ once on start-up, loads the old favorite list from its _Storage_, and adds it to the _Favortie BLoC_ though _Add-Events_. It also listens to the _Favorite BLoC_ and updates the persistant copy of the favorite list every time the _Favorite Bloc_ emits a new State:

```dart
///Gives access to the 2 events the [StorageBloc] can receive.
///
///It is an enum, because the 2 events both don't need to carry additional data.
///[StorageEvent.load] tells the [StorageBloc] to load the 
///favorite list from it [Storage].
///[StorageEvent.wipe] tells the [StorageBloc] to wipe 
///any favorites on the [Storage].
enum StorageEvent { load, wipe }

///Responsible for keeping a persistent copy of the favorite list 
///on it's [Storage].
///
///It is injected with a [FavoriteBLoC] on Creation.
///It subscribes to the [FavoriteBLoC] and writes the favorite list to a 
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
_Code Snippet 34: Wisgen Storage BLoC [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

_Storage_ is also a plattform agnostic interface and it looks like this:

```dart
///Generic Repository for read/write Storage device
abstract class Storage<T>{
  Future<T> load();
  save(T data);

  ///Wipe the Storage Medium
  wipeStorage();
}
```
_Code Snippet 35: Wisgen Plattform Agnostic Interface Storage [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

In Wisgen, I built an implementaion of _Storage_ that communicates with Androids Shared Preferences [[@googlellcSharedPreferences2011]](https://developer.android.com/reference/android/content/SharedPreferences) and saves the favorite list as a JSON:

```dart
///[Storage] that gives access to Androids Shared Preferences 
///(a small, local, persistent key value store).
class SharedPreferenceStorage implements Storage<List<Wisdom>> {
  ///Key is used to access store
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
_Code Snippet 36: Wisgen Plattform Agnostic Interface Storage [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_
