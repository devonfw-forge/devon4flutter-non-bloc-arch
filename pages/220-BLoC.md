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

The BLoC Pattern is a State Management solution originally designed by Paolo Soares in 2018 [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE). Its original purpose was to enable code sharing between Flutter [\[1\]](%5B@flutterdevteamFlutterFramework2018%5D) and Angular Dart [\[64\]](https://angulardart.dev/) applications. Soares was working on applications in both frameworks at the time and he wanted a pattern that enabled him to hook up the same business logic to both Flutter and Angular apps. His idea was to remove business logic from the UI as much as possible and extract it into its own classes, into BLoCs (Business Logic Components). The UI should only send events to a BLoC and display the interface based on the State of a BLoC. Soares defined, that UI and BLoCs should only communicate through streams [\[37\]](https://dart.dev/tutorials/language/streams). This way the developer would not need to worry about manually telling the UI to redraw. The UI can simply subscribe to a stream of State [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) emitted by a BLoC and change based on the incoming State \[7\], \[44\], \[48\], \[62\].

| 📙 | BLoC | Business Logic Component [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE) |
| - | ---- | :---------------------------------------------------------------------------- |

| 🕐 | TLDR | The UI should be kept free of business logic. The UI Only publishes *Events* to a BLoC and subscribes to a stream of *State* emitted by a BLoC |
| - | ---- | :--------------------------------------------------------------------------------------------------------------------------------------------- |

![Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/bloc-architecture.png)

*Figure XXX: Bloc turning input events to a stream of State [\[62\]](https://www.youtube.com/watch?v=RS36gBEp8OI)*

## Advantages of BLoC

That’s all well and good, but why should you care? An application that follows the rules defined by the BLoC pattern will…

1.  have all its business logic in one place
2.  have business logic that functions independently of the interface
3.  have UI that can be changed without affecting the business Logic
4.  have business logic that is easily testable
5.  rely on few rebuilds, as the UI only rebuilds when the State related to that UI changes

\[7\], \[44\], \[55\], \[56\]

## Rules of the BLoC Pattern

To gain those promised advantages, you will have to follow these 8 rules Soares defined for the BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE):

#### Rules for the BLoCs

1.  Input/Outputs are simple **Sinks/Streams**
2.  All **dependencies** must be **injectable** and platform agnostic
3.  **No platform branching**
      - No `if(IOS) then doThis()`
4.  The actual implementation can be whatever you want if you follow 1-3

#### Rules for UI Classes

1.  Each *“Complex Enough”* Widget has a related BLoC
      - You will have to define what *“Complex Enough”* means for your app.
2.  Widgets **do not format the inputs** they send to the BLoC
      - Because formating is Business Logic
3.  Widgets should display the BLoCs **State/output with as little formatting as possible**
      - Sometimes a little formatting is inevitable, put things like currency formating is business logic and should be done in the BLoC
4.  If you do have **platform branching**, It should be dependent on **a single BLoC bool State/output**

![Bloc Sink and Stream](https://github.com/Fasust/flutter-guide/wiki//images/bloc-sink-stream.png)

*Figure XXX: How a BLoC looks like [\[44\]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/)*

## Implementation

Alright, Now that you know what the BLoC pattern is, let’s have a look at how it looks in practice. You will see some strong similarities to the implementation of Redux [\[57\]](https://redux.js.org/) here. That is just because the two patterns are very similar in general. I am using the BLoC package [\[36\]](https://felangel.github.io/bloc/#/) for Flutter by Felix Angelov, as it removes a lot of the boilerplate code we would have to write if we would implement our own BLoCs from scratch. I am going to use the Example of *App State* as I did in the [previous chapter](https://github.com/Fasust/flutter-guide/wiki/210-State-Management-Alternatives): The favorite list in Wisgen [\[11\]](https://github.com/Fasust/wisgen). First, let’s have a look at how the Bloc pattern will interact with Wisgen on a more abstract scale:

![Bloc and Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-bloc.PNG)

*Figure XXX: Bloc and Wisgen Widget Tree [\[11\]](https://github.com/Fasust/wisgen)*

Let’s have a look at the events that can be sent to the BLoC by the UI. Again, this is very similar to the *actions* in our Redux implementation:

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

*Code Snippet XXX: Favorite Event in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

Now Let’s take a look at the most interesting part of an implementation of the BLoC patter, the BLoC class itself. We extend the BLoC class provided by the Flutter BLoC package. It takes in the type of the *events* that will be sent to the BLoC and the type of the *State* that should be emitted by the BLoC `Bloc<Event, State>`:

``` dart
///The FavoriteBLoC is Responsible for Keeping track of the
///Favorite List. It receives Events to Add and remove Favorite
///Wisdoms and Broadcasts the Complete List of Favorites.
class FavoriteBloc extends Bloc<FavoriteEvent, List<Wisdom>> {

  @override
  List<Wisdom> get initialState => List<Wisdom>();

  ///Takes in each event that is send to the BLoC and emits new State
  ///based on that event.
  @override
  Stream<List<Wisdom>> mapEventToState(FavoriteEvent event) async* {
    List<Wisdom> newFavorites = new List()..addAll(currentState);

    if (event is AddFavoriteEvent) newFavorites.add(event.favorite);
    if (event is RemoveFavoriteEvent) newFavorites.remove(event.favorite);

    yield newFavorites;
  }
}
```

*Code Snippet XXX: Favorite BLoC in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

As I mentioned before, the BLoC package for Flutter uses the Provider package [\[50\]](https://pub.dev/packages/provider). This means that we can provide our BLoC to the rest of our Widget Tree in the same way we would if just used Provider for State Management. By the rule of *“lifting State up”* we have to place the favorite BLoC at the lowest common ancestor of all Widgets that need access to it. So in our case at *MaterialApp*:

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

*Code Snippet XXX: Providing a BLoC Globally in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

Lastly, we can dispatch events and subscribe to a BLoC. This is the favorite button in Wisgen. It changes shape and color based on the State emitted by the FavoriteBLoC and it dispatches events to the BLoC to add and remove favorites. The *wisdom* object is the wisdom displayed on the Card Widget.

``` dart
...
Expanded(
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
      padding: EdgeInsets.only(right: _smallPadding),
    ),
  ),
)
...
```

*Code Snippet XXX: Accessing a BLoC in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

## Layered Architecure

Now that we understand how to implement the BLoC pattern, lets’ have a look at how we can use it to achieve a clean three-layered architecture for your application. The BLoC Pattern already forces us to keep our UI and our business logic separate. This way we end up with a UI-Layer and a Business-Logic Layer. Lastly, we want to keep our BLoCs plattform independant. So we extract any logic related to external services from the BLoC and put it into its own layer [\[63\]](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1). This would be things like a *database connector* or a class that handels communication with an API. To fulfill rule 2 of the BLoC Pattern, We can’t have our BLoCs directly depend on our external service connectors. We have to create plattform agnostic *interfaces*/*boundary object* and make our external service connectors implement those [\[65\]](https://www.youtube.com/watch?v=o_TH-Y78tt4). Then our BLoCs can depend on the plattform agnostic interfaces instead and the actual dependency can be injected. This way we end up with a clean three-layered architecture with one-way dependencies:

![Bloc Architecture with Layers](https://github.com/Fasust/flutter-guide/wiki//images/bloc-my-layers.png)

*Figure XXX: Three-Layered BLoC Architecture*

## Architecture in Practice

![Wisgen Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dependencies.png)

*Figure XXX: Wisgen Bloc Architecture [\[11\]](https://github.com/Fasust/wisgen)*

![Wisgen Bloc Architecture Dataflow](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dataflow.png)

*Figure XXX: Wisgen Bloc Architecture Dataflow [\[11\]](https://github.com/Fasust/wisgen)*

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/300-Testing">Next Chapter: Testing ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>