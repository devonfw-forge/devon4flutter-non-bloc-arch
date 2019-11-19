Page Table of Contents
- [Introduction](#introduction)
- [Example App State](#example-app-state)
- [Provider Package](#provider-package)
  - [Why I Decided Against it](#why-i-decided-against-it)
- [Redux](#redux)
  - [Why I Decided Against it](#why-i-decided-against-it-1)

## Introduction

Other than many mobile development frameworks, Flutter [\[1\]](https://flutter.dev/) does not impose any kind of architecture or State Management Solution on its developers. This open-ended approach has lead to multiple State Management Solution and a hand full of architectural approaches spawning from the community [\[57\]](https://fluttersamples.com/). Some of these approaches have even been endorsed by the Flutter Team itself [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). I decided to focus on the BLoC pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE) for this guide. But I do want to showcase some alternatives and explain why exactly I ended up choosing BLoC.

## Example App State

I will showcase the State Management Solutions using one example of *App State* from the Wisgen App [\[11\]](https://github.com/Fasust/wisgen). Wisgen gives the user the option to add wisdoms to a favorite list. This list/State is needed by two parties:

1.  The ListView on the favorite page, so it can display all favorites.
2.  The button on every wisdom card so it can add a new favorites to the list and show if a given wisdom is a favorite.

![Wisgen favorites list and favorite in feed](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen-fav-mock.png)

*Figure 13: Wisgen favorites list and favorite in feed [\[11\]](https://github.com/Fasust/wisgen)*

Whenever the favorite button on any card is pressed, several Widgets [\[30\]](https://flutter.dev/docs/development/ui/widgets-intro) have to update. This is a simplified version of the Wisgen Widget Tree. The red highlights show the Widgets that need access to the favorite list, the heart shows a possible location from where a new favorite could be added.

![Wisgen favorites WidgetTree](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen-pagetree-fav.PNG)

*Figure 14: Wisgen favorites WidgetTree [\[11\]](https://github.com/Fasust/wisgen)*

## Provider Package

The Provider Package [\[58\]](https://pub.dev/packages/provider) is a State Management Solution for Flutter published by Remi Rousselet in 2018. It has since then been endorsed by the Flutter Team on multiple occasions [\[59\]](https://www.youtube.com/watch?v=HrBiNHEqSYU), [\[60\]](https://www.youtube.com/watch?v=d_m5csmrf7I) and Rousselet and the Flutter Team are now devolving it in cooperation. The package is basically a prettier interface for Inherited Widgets [\[38\]](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html). You can use Provider to expose State from a Widget at the top of the tree to any number of Widgets below it in the tree.

As a quick reminder: Data in Flutter always flows **downwards**. If you want to access data from multiple locations within your Widget Tree, you have to place it at one of their common ancestors so they can both access it through their BuildContexts [\[34\]](https://api.flutter.dev/flutter/widgets/BuildContext-class.html). This practice is called *“lifting State up”* and it is very common within declarative frameworks [\[61\]](https://www.youtube.com/watch?v=zKXz3pUkw9A).

| 📙 | Lifting State up | Placing State at the lowest common ancestor of all Widgets that need access to it [\[61\]](https://www.youtube.com/watch?v=zKXz3pUkw9A) |
| - | ---------------- | :-------------------------------------------------------------------------------------------------------------------------------------- |

The Provider Package is an easy way for us to lift State up. Let’s look at our example from figure 14: The first common ancestor of all Widgets in need of the favorite list is *MaterialApp*. So we will need to lift the State up to the MaterialApp Widget and then have our other Widgets access it from there:

![Wisgen WidgetTree favorites with Provider](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen-pagetree-provider.PNG)

*Figure 15: Wisgen WidgetTree favorites with Provider [\[11\]](https://github.com/Fasust/wisgen)*

To minimize re-builds the Provider Package uses ChangeNotifiers [\[62\]](https://api.flutter.dev/flutter/foundation/ChangeNotifier-class.html). This way Widgets can subscribe/listen to the provided Sate and get notified whenever it changes. This is how an implementation of Wisgen’s favorite list would look like using Provider. “*Favorites*” is the class we will use to provide our favorite list globally. The *notifyListeners()* function will trigger rebuilds on all Widgets that listen to it.

``` dart
class Favorites with ChangeNotifier{
  //State
  final List<Wisdom> _wisdoms = new List(); 

  add(Wisdom w){
    _wisdoms.add(w);
    notifyListeners(); //Re-Build all Listeners
  }

  remove(Wisdom w){
    _wisdoms.remove(w);
    notifyListeners(); //Re-Build all Listeners
  }

  bool contains(Wisdom w) => _wisdoms.contains(w);

}
```

*Code Snippet 22: Hypothetical Favorites class that would be exposed through the Provider Package [\[11\]](https://github.com/Fasust/wisgen)*

Now we can expose our *Favorite* class above *MaterialApp* in the WidgetTree using the *ChangeNotifierProvider* Widget:

``` dart
void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    //Providing Favorite class globally
    return ChangeNotifierProvider(
      builder: (_) => Favorites(),
      child: MaterialApp(home: WisdomFeed()),
    );
  }
}
```

*Code Snippet 23: Providing Favorite class globally [\[11\]](https://github.com/Fasust/wisgen)*

Now we can consume the Favorite class from any of the dependance of the ChangeNotifierProvider Widget. Let’s look at the favorite button as an example. We use the *Consumer Widget* to get access to the favorite list and everything below the Consumer Widget will be rebuild when the favorites list changes. The *wisdom* object is the wisdom displayed on the Card Widget.

``` dart
...
@override
Widget build(BuildContext context) {
  return Expanded(
    flex: 1,
    child: Consumer<Favorites>( //Consuming global instance of Favorite class
      builder: (context, favorites, child) => IconButton(
        //Display Icon Button depending on current State
        icon: Icon(favorites.contains(wisdom)
            ? Icons.favorite
            : Icons.favorite_border),
        color: favorites.contains(wisdom) 
            ? Colors.red 
            : Colors.grey,
        onPressed: () {
          //Add/remove wisdom to/from Favorite class
          if (favorites.contains(wisdom)) favorites.remove(wisdom);
          else favorites.add(wisdom);
        },
      ),
    ),
  )
}
...
```

*Code Snippet 24: Consuming Provider in favorite button [\[11\]](https://github.com/Fasust/wisgen)*

### Why I Decided Against it

All in all, Provider is a great and easy solution to distribute State in a small Flutter application. But it is just that, a State Management Solution and not an architecture [\[59\]](https://www.youtube.com/watch?v=HrBiNHEqSYU), [\[60\]](https://www.youtube.com/watch?v=d_m5csmrf7I), [\[63\]](https://www.didierboelens.com/2019/04/bloc---scopedmodel---redux---comparison/), [\[64\]](https://medium.com/flutter-community/flutter-app-architecture-101-vanilla-scoped-model-bloc-7eff7b2baf7e). Just the Provider package alone with no pattern to follow or an architecture to obey will not lead to a clean and manageable application. But no worries, I did not teach you about the package for nothing. Because Provider is such an efficient and easy way to distribute State, the BLoC package [\[39\]](https://felangel.github.io/bloc/#/) uses it as an underlying technology for their approach.

## Redux

Redux [\[65\]](https://redux.js.org/) is State Management Solution with an associated architectural pattern. It was originally built for React [\[22\]](https://facebook.github.io/react-native/) in 2015 by Dan Abramov. It was late ported to Flutter by Brian Egan in 2017 [\[66\]](https://pub.dev/packages/flutter_redux). Redux uses a *Store* as one central location for all business logic. This Store is put at the very top of the Widget Tree and then globally provided to all Widgets using an Inherited Widget. We extract as much logic from the UI as possible. It should only send actions to the Store (such as user inputs) and display the UI dependant on the current State of the Store. The Store has *Reducer* functions, that take in the previous State and an *Action* and return a new State. [\[61\]](https://www.youtube.com/watch?v=zKXz3pUkw9A), [\[63\]](https://www.didierboelens.com/2019/04/bloc---scopedmodel---redux---comparison/), [\[67\]](https://www.youtube.com/watch?v=n_5JULTrstU&feature=youtu.be) So in Wisgen, the Dataflow would look something like this:

![Wisgen Redux dataflow](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen-redux.PNG)

*Figure 16: Wisgen Redux dataflow [\[11\]](https://github.com/Fasust/wisgen)*

Let’s have a quick look at how an implementation of Redux would look like in Wisgen.
Our possible *Actions* are:

1.  Adding a new wisdom to our favorites.
2.  Removing a wisdom from our favorites.

So this is what our *Action* classes would look like:

``` dart
@immutable
abstract class FavoriteAction {
  //Wisdom related to Action
  final Wisdom _favorite;
  get favorite => _favorite;

  FavoriteAction(this._favorite);
}

class AddFavoriteAction extends FavoriteAction {
  AddFavoriteAction(Wisdom favorite) : super(favorite);
}

class RemoveFavoriteAction extends FavoriteAction {
  RemoveFavoriteAction(Wisdom favorite) : super(favorite);
}
```

*Code Snippet 25: Hypothetical Wisgen Redux Actions [\[11\]](https://github.com/Fasust/wisgen)*

This what the Reducer function would look like:

``` dart
// take in old State and Action
List<Wisdom> favoriteReducer(List<Wisdom> state, FavoriteAction action) {
  //Determine how to change the State
  if (action is AddFavoriteAction) state.add(action.favorite);
  if (action is RemoveFavoriteAction) state.remove(action.favorite);

  //Return new State
  return state;
}
```

*Code Snippet 26: Hypothetical Wisgen Redux Reducer [\[11\]](https://github.com/Fasust/wisgen)*

And this is how we would make the Store globally available:

``` dart
void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    //Create new Store from Reducer function
    favoriteStore = new Store<List<Wisdom>>(
      reducer: favoriteReducer, 
      initialState: new List(),
    );

    //Provide Store globally
    return StoreProvider<List<Wisdom>>((
      store: favoriteStore,
      child: MaterialApp(home: WisdomFeed()),
    );
  }

  //Snippet 26
  List<Wisdom> favoriteReducer(List<Wisdom> state, FavoriteAction action) {...}
}
```

*Code Snippet 27: Providing Redux Store globally in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

Now the favorite button from snippet 24 would be implemented like this:

``` dart
...
@override
Widget build(BuildContext context) {
  return Expanded(
    flex: 1,
    child: StoreConnector( //Consume Store
      converter: (store) => store.state, //No need for conversion, just need current State
      builder: (context, favorites) => IconButton(
        //Display Icon Button depending on current State
        icon: Icon(favorites.contains(wisdom)
            ? Icons.favorite
            : Icons.favorite_border),
        color: favorites.contains(wisdom) 
            ? Colors.red 
            : Colors.grey,
        onPressed: () {
          //Add/remove wisdom to/from favorites
          if (favorites.contains(wisdom)) store.dispatch(AddFavoriteAction(wisdom));
          else store.dispatch(RemoveFavoriteAction(wisdom));
        },
      ),
    ),
  )
}
...
```

*Code Snippet 28: Consuming Redux Store in favorite button [\[11\]](https://github.com/Fasust/wisgen)*

### Why I Decided Against it

I went back and forth on this decision a lot. Redux is a great State Management Solution with some clear guidelines on how to integrate it into a Reactive application [\[68\]](https://redux.js.org/introduction/three-principles). It also enables the implementation of a clean three-layered architecture (View - Store - Data) [\[61\]](https://www.youtube.com/watch?v=zKXz3pUkw9A). Didier Boelens recommends to just stick to a Redux architecture if you are already familiar with its approach from other cross-platform development frameworks like React [\[22\]](https://facebook.github.io/react-native/) and Angular [\[69\]](https://angular.io/) and I very much agree with this advice [\[63\]](https://www.didierboelens.com/2019/04/bloc---scopedmodel---redux---comparison/). I have previously never worked with Redux and I decided to use BLoC over Redux because:

1.  It was publicly endorsed by the Flutter Team on multiple occasions [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE), [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt), [\[53\]](https://www.youtube.com/watch?v=fahC3ky_zW0), [\[59\]](https://www.youtube.com/watch?v=HrBiNHEqSYU), [\[70\]](https://www.youtube.com/watch?v=RS36gBEp8OI)
2.  It also has clear architectural rules [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE)
3.  It also enables the implementation of a layered architecture [\[71\]](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1)
4.  It was developed by one of Flutter’s Engineers [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE)
5.  It does not end up with one giant Store for the business logic. Instead, we spread the business logic out into multiple BLoCs with separate responsibilities [\[63\]](https://www.didierboelens.com/2019/04/bloc---scopedmodel---redux---comparison/)

<p align="right"><a href="https://github.com/devonfw-forge/devonfw4flutter/wiki/220-BLoC">Next Chapter: BLoC ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>