Page Table of Contents
- [Introduction](#introduction)
- [Advantages of BLoC](#advantages-of-bloc)
- [Rules of the BLoC Pattern](#rules-of-the-bloc-pattern)
    - [Rules for the BLoCs](#rules-for-the-blocs)
    - [Rules for UI Classes](#rules-for-ui-classes)
- [Implementation](#implementation)
- [Layered Architecure](#layered-architecure)
- [Architecture in Practice](#architecture-in-practice)

## Introduction

The BLoC Pattern is a State Management solution originally designed by Paolo Soares in 2018 [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE). It’s original purpose was to enable code sharing between Flutter [(Flutter Dev Team 2018h)](%5B@flutterdevteamFlutterFramework2018%5D) and Angular Dart [(Google LLC 2018a)](https://angulardart.dev/) applications. Soares was working on applications in both frameworks at the time and he wanted a pattern that enabled him to hook up the same business logic to both Flutter and Angular apps. His idea was to remove business logic from the UI as much as possible and extract it into it’s own classes, into BLoCs (Business Logic Components). The UI should only send events to a BLoC, and display the interface based on the state of a BLoC. Soares defined, that UI and BLoCs should only communicate through streams [(Dart Team 2019b)](https://dart.dev/tutorials/language/streams). This way the developer would not need to worry about manually telling the UI to redraw. The UI can simply subscribe to a stream of State [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt) emitted by a BLoC and change based on the incoming state (Sullivan and Hracek 2018b, 2018a; Soares 2018; Boelens 2018a).

| BLoC | Business Logic Component |
| ---- | :----------------------- |

| TLDR | The UI should be kept free of business logic. The UI Only publishes *Events* to a BLoC and subscribes to a stream of *State* emitted by a BLoC |
| ---- | :--------------------------------------------------------------------------------------------------------------------------------------------- |

![Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/bloc-architecture.png)

*Figure XXX: Bloc turning input events to a stream of State [(Sullivan and Hracek 2018b)](https://www.youtube.com/watch?v=RS36gBEp8OI)*

## Advantages of BLoC

That’s all well and good, but why should you care? An application that follows the rules defined by the BLoC pattern will..

1.  have all it’s business logic in one place
2.  have business logic that is functions independently of the interface
3.  have UI that can be changed without affecting the business Logic
4.  have business logic that easily testable
5.  rely on few rebuilds, as the UI only rebuilds when the state related to that UI changes
6.  have all it’s dependencies injectable

(Boelens 2019, 2018a; Savjolovs 2019; Soares 2018)

## Rules of the BLoC Pattern

To gain those promised advanteges, you will have to follow these 8 rules Soares defined for the BLoC Pattern [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE):

#### Rules for the BLoCs

1.  Input/Outputs are simple **Sinks/Streams**
2.  All **dependencies** must be **injectable** and plattform agnostic
3.  **No platform branching**
      - No `if(IOS) then doThis()`
4.  The actual implementation can be whatever you want if you follow 1-3

#### Rules for UI Classes

1.  Each *“Complex Enough”* Widget has a related BLoC
      - You will have to define what *“Complex Enough”* means for your app.
2.  Widgets **do not format the inputs** they send to the BLoC
      - Because formating is Business Logic
3.  Widgets should display the BLoCs **State/output with as little formating as possible**
      - Sometime a little formatting is inevitable, put things like currency formating is business logic and should be done in the BLoC
4.  If you do have **platform branching**, It should be dependent on **a single BLoC bool State/output**

![Bloc Sink and Stream](https://github.com/Fasust/flutter-guide/wiki//images/bloc-sink-stream.png)

*Figure XXX: How a BLoC looks like [(Boelens 2018a)](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/)*

## Implementation

  - **Build Interface code how you want it to look like -\> then make it work**
  - Wisgen Exampels

Alright, Now that you know what the BLoC pattern is, let’s have a look at how it looks in practice. You will see some strong similarity to the implementation of Redux [(Abramov 2015)](https://redux.js.org/) here. That is just because the two patterns are very similar in gerneral. I am using the BLoC package [(Angelov and Contributors 2019)](https://felangel.github.io/bloc/#/) for Flutter by Felix Angelov, as it removes a lot of the boilerplate code we would have to write if we would implement our own BLoCs from scratch. I am going to use the Example of *App State* as I did in the [previous chapter](https://github.com/Fasust/flutter-guide/wiki/210-State-Management-Alternatives): The favorite list in Wisgen [(Faust 2019)](https://github.com/Fasust/wisgen). First, let’s have a look at how the Bloc pattern will interact with Wisgen on a more abstract scale:

![Bloc and Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-bloc.PNG)

*Figure XXX: Bloc and Wisgen Widget Tree [(Faust 2019)](https://github.com/Fasust/wisgen)*

First, let’s have a look at the events that can be send to the BLoC by the UI. Again, this is very similar to the *actions* in our Redux implementation:

``` dart
///The Favorite BLoC can handle 2 types of Events: Add and Remove.
///These events add and remove Wisdoms from the Favorite List respectively.
@immutable
abstract class FavoriteEvent {
  final Wisdom _favorite;
  get favorite => _favorite;

  FavoriteEvent(this._favorite);
}

class AddFavoriteEvent extends FavoriteEvent {
  AddFavoriteEvent(Wisdom favorite) : super(favorite);
}

class RemoveFavoriteEvent extends FavoriteEvent {
  RemoveFavoriteEvent(Wisdom favorite) : super(favorite);
}
```

*Code Snippet XXX: Favorite Event in Wisgen [(Faust 2019)](https://github.com/Fasust/wisgen)*

Now Let’s take a look at the most interesting part of an implementation of the BLoC patter, the BLoC class itself. We extend the BLoC class provided by the Flutter BLoC package. It take in the type of the *events* that will be send to the BLoC and the type of the *State* that should be emitted from the BLoC `Bloc<Event,State>`:

``` dart
///The FavoriteBLoC is Responsible for Keeping track of the
///Favorite List. It receives Events to Add and remove Favorite
///Wisdoms and Broadcasts the Complete List of Favorites.
class FavoriteBloc extends Bloc<FavoriteEvent, List<Wisdom>> {

  @override
  List<Wisdom> get initialState => List<Wisdom>();

  ///Takes in each event that is send to the BLoC emits a new State based on
  ///that event.
  @override
  Stream<List<Wisdom>> mapEventToState(FavoriteEvent event) async* {
    List<Wisdom> newFavorites = new List()..addAll(currentState);

    if (event is AddFavoriteEvent) newFavorites.add(event.favorite);
    if (event is RemoveFavoriteEvent) newFavorites.remove(event.favorite);

    yield newFavorites;
  }
}
```

*Code Snippet XXX: Favorite BLoC in Wisgen [(Faust 2019)](https://github.com/Fasust/wisgen)*

## Layered Architecure

  - Pros
      - Change BL more easily
      - Change UI without impacting BL
      - Easily Test BL
  - Layered Architecture out of BLoCs
      - Like Uncle Bob says
      - Nice indented Layers
      - use Boundary classes IE interfaces to keep data layer seperat from Buisness Layer

![Bloc Architecture with Layers](https://github.com/Fasust/flutter-guide/wiki//images/bloc-layers.png)

*Figure XXX: Bloc Architecture with Layers [(Suri 2019)](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1)*

## Architecture in Practice

![Wisgen Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dependencies.png)

*Figure XXX: Wisgen Bloc Architecture [(Faust 2019)](https://github.com/Fasust/wisgen)*

![Wisgen Bloc Architecture Dataflow](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dataflow.png)

*Figure XXX: Wisgen Bloc Architecture Dataflow [(Faust 2019)](https://github.com/Fasust/wisgen)*

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/300-Testing">Next Chapter: Testing ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>