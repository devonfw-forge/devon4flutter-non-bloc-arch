Page Table of Contents
- [Introduction](#introduction)
- [Example App State](#example-app-state)
- [Provider Package](#provider-package)
  - [Why I decided against it](#why-i-decided-against-it)
- [Redux](#redux)
  - [Why I decided against it](#why-i-decided-against-it-1)

## Introduction

Other then many mobile development frameworks, Flutter [(Flutter Dev Team 2018h)](https://flutter.dev/) does not impose any kind of architecture or State Management solution on it’s developers. This open ended approach has lead to multiple State Management solution and a hand full of architectural approaches spawning from the community. Some of these approaches have even been indorsed by the Flutter Team itself [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt). I decided to focus on the BLoC pattern [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE) for this Guide. But I do want to showcase some alternatives and explain why exactly I ended up choosing BLoC.

## Example App State

I will showcase the State Management solutions using one example of *App State* from the Wisgen App [(Faust 2019)](https://github.com/Fasust/wisgen). We have a list of favorite wisdoms in the Wisgen App. This State is needed by 2 parties:

1.  The ListView on the favorite page, so it can display all favorites
2.  The button on every wisdom card so it can add a new favorite to the list and show if a given wisdom is a favorite.

![Wisgen WidgetTree Favorites](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-fav-mock.png)

*Figure 13: Wisgen Favorites [(Faust 2019)](https://github.com/Fasust/wisgen)*

So when ever the favorite button on any card is pressed, a number of widgets [(Flutter Dev Team 2019c)](https://flutter.dev/docs/development/ui/widgets-intro) have to update. This is a simplified version of the Wisgen Widget Tree, the red highlights show the Widgets that need access to the favorite list, the heart shows a possible location from where a new favorite could be added.

![Wisgen WidgetTree Favorites](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-fav.PNG)

*Figure 14: Wisgen WidgetTree Favorites [(Faust 2019)](https://github.com/Fasust/wisgen)*

## Provider Package

The Provider Package [(Rousselet and Flutter Dev Team 2018)](https://pub.dev/packages/provider) is an open source package for Flutter developed by Remi Rousselet in 2018. It has since then been endorsed by the Flutter Team on multiple occasions (Hracek and Sullivan 2019; Sullivan and Hracek 2019) and they are now devolving it in cooperation. The package is basically a prettier interface to interact with Inherited Widgets [(Flutter Dev Team 2018c)](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html) and expose state from a Widget at the top of the tree to a Widget at the bottom.

As a quick reminder: Data in Flutter always flows **downwards**. If you want to access data from multiple locations within your widget tree, you have to place it at one of there common ancestors so they can both access it through their build contexts. This practice is called *“lifting state up”* and it a common practice within declarative frameworks [(Egan 2018)](https://www.youtube.com/watch?v=zKXz3pUkw9A).

| Lifting state up | Placing State at the lowest common ancestor of all Widgets that need access to it |
| :--------------- | :-------------------------------------------------------------------------------- |

The Provider Package is an easy way for us to lift state up. Let’s look at our example form figure 14: The first common ancestor of all Widgets in need of the favorite list is *MaterialApp*. So we will need to lift the state up to the MaterialApp and then have our widgets access it from there:

![Wisgen WidgetTree Favorites with Provider](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-provider.PNG)

*Figure 15: Wisgen WidgetTree Favorites with Provider [(Faust 2019)](https://github.com/Fasust/wisgen)*

To minimize re-builds the Provider Package uses ChangeNotifiers [(Flutter Dev Team 2018b)](https://api.flutter.dev/flutter/foundation/ChangeNotifier-class.html). This way Widgets can subscribe/listen to the Sate and get notified whenever the State changes. This is how an implementation of Wisgens favorite list would look like using Provider: *Favorites* is the class we will use to provide our favorite list globally. The *notifyListeners()* function will trigger rebuilds on all Widgets that listen to it.

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

  bool contains(Wisdom w){
    return _wisdoms.contains(w);
  }
}
```

*Code Snippet 22: Favorites Class that will be exposed through Provider Package [(Faust 2019)](https://github.com/Fasust/wisgen)*

Here we expose our Favorite class globally above *MaterialApp* in the WidgetTree using the *ChangeNotifierProvider* Widget:

``` dart
void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    //Providing Favorites Globally
    return ChangeNotifierProvider(
      builder: (_) => Favorites(),
      child: MaterialApp(home: WisdomFeed()),
    );
  }
}
```

*Code Snippet 23: Providing Favorites Globally [(Faust 2019)](https://github.com/Fasust/wisgen)*

This is how listening to the Favorite class looks like. We use the *Consumer Widget* to get access to the favorite list and everything below the Consumer Widget will be rebuild when the favorites list changes.

``` dart
...
Expanded(
  flex: 1,
  child: Consumer<Favorites>( //Consuming Global instance of Favorites
    builder: (context, favorites, child) => IconButton(
      //Display Icon Button depending on current State
      icon: Icon(favorites.contains(wisdom)
          ? Icons.favorite
          : Icons.favorite_border),
      color: favorites.contains(wisdom) 
          ? Colors.red 
          : Colors.grey,
      onPressed: () {
        //Add/remove Wisdom to/from Favorites
        if (favorites.contains(wisdom)) favorites.remove(wisdom);
        else favorites.add(wisdom);
      },
    ),
  ),
)
...
```

*Code Snippet 24: Consuming Provider in Favorite Button of Wisdom Card [(Faust 2019)](https://github.com/Fasust/wisgen)*

### Why I decided against it

All in all Provider is a great and easy solution to distribute State in a small Flutter applications. But it is just that, a State Managment solution and not an architecture (Hracek and Sullivan 2019; Boelens 2019; Savjolovs 2019; Sullivan and Hracek 2019). Just the provider package alone with no pattern to follow or an architecture to obey will not lead to a clean and manageable application. But no worries, I did not teach you about the package for nothing. Because provider is such an efficient and easy way to distribute state, the BLoC package [(Angelov and Contributors 2019)](https://felangel.github.io/bloc/#/) uses it as an underlying technologie for their approach.

## Redux

Redux [(Abramov 2015)](https://redux.js.org/) is an Architectual Pattern with a State Management solution. It was originally build for React [(Facebook 2015)](https://facebook.github.io/react-native/) in 2015 by Dan Abramov. It was late ported to Flutter by Brian Egan in 2017 [(Egan 2017)](https://pub.dev/packages/flutter_redux). In Redux, we use a *Store* as one central location for all our Business Logic. This Store is put at the very top of our Widget Tree and then globally provided to all widgets using an Inherited Widget. We extract as much logic from the UI as possible. It should only send actions to the store (such as user input) and display the interface dependant on the current State of the Store. The Store has *reducer* functions, that take in the previous State and an *action* and return a new state. (Boelens 2019; Doughtie 2017; Egan 2018) So in Wisgen the Dataflow would look something like this:

![Wisgen Favorite List with Redux](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-redux.PNG)

*Figure 16: Wisgen Favorite List with Redux [(Faust 2019)](https://github.com/Fasust/wisgen)*

Our possible *actions* are adding a new wisdom and removing a wisdom. So this is what our Action classes would look like:

``` dart
@immutable
abstract class FavoriteAction {
  //Wisdom related to action
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

*Code Snippet 25: Wisgen Redux Actions [(Faust 2019)](https://github.com/Fasust/wisgen)*

This what the reducer function would look like:

``` dart
List<Wisdom> favoriteReducer(List<Wisdom> state, FavoriteAction action) {
  if (action is AddFavoriteAction) state.add(action.favorite);
  if (action is RemoveFavoriteAction) state.remove(action.favorite);
  return state;
}
```

*Code Snippet 26: Wisgen Redux Reducer [(Faust 2019)](https://github.com/Fasust/wisgen)*

And this is how you would make the Store globally available:

``` dart
void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    //Create new Store from reducer function
    favoriteStore = new Store<List<Wisdom>>(favoriteReducer, initialState: new List());

    //Provide Store globally
    return StoreProvider<List<Wisdom>>((
      store: favoriteStore,
      child: MaterialApp(home: WisdomFeed()),
    );
  }
}
```

*Code Snippet 27: Providing Redux Store globally in Wisgen [(Faust 2019)](https://github.com/Fasust/wisgen)*

Now the Favorite button from snippet 24 would be implemented like this:

``` dart
...
Expanded(
  flex: 1,
  child: StoreConnector( //Consume Store
    converter: (store) => store.state, //No need for conversion, just need current state
    builder: (context, favorites) => IconButton(
      //Display Icon Button depending on current State
      icon: Icon(favorites.contains(wisdom)
          ? Icons.favorite
          : Icons.favorite_border),
      color: favorites.contains(wisdom) 
          ? Colors.red 
          : Colors.grey,
      onPressed: () {
        //Add/remove Wisdom to/from Favorites
        if (favorites.contains(wisdom)) store.dispatch(AddFavoriteAction(wisdom));
        else store.dispatch(RemoveFavoriteAction(wisdom));
      },
    ),
  ),
)
...
```

*Code Snippet 28: Consuming Redux Store in Favorite Button of Wisdom Card [(Faust 2019)](https://github.com/Fasust/wisgen)*

### Why I decided against it

I went back and forth on this decision a lot. Redux is a great State Management solution with some clear guidlines on how to intergrate it into a Flutter application. It also enables the implementation of a clean three layered architecture (View - Store - Data) [(Egan 2018)](https://www.youtube.com/watch?v=zKXz3pUkw9A). Didier Boelens recommends to just stick to a Redux architecture if you are already familiar with it’s approach from other cross-plattform development frameworks like React [(Facebook 2015)](https://facebook.github.io/react-native/) and Angular [(Google LLC 2016)](https://angular.io/) and I very much agree with this advice [(Boelens 2019)](https://www.didierboelens.com/2019/04/bloc---scopedmodel---redux---comparison/). I have previously never worked with Redux and I decided to use BLoC over Redux because:

1.  It was publicly endorsed by the Flutter Team on multiple occasions (Sullivan and Hracek 2018b, 2018a; Hracek and Sullivan 2019; Soares 2018; Flutter Dev Team 2019b)
2.  It also has clear architectural rules [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE)
3.  It also enables the implementation of a clean three layered architecture [(Suri 2019)](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1)
4.  It was developed by one of Flutters Engineers [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE)
5.  We don’t end up with one giant store for the business logic out with multiple blocs with separate responsibilities [(Boelens 2019)](https://www.didierboelens.com/2019/04/bloc---scopedmodel---redux---comparison/)

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/220-BLoC">Next Chapter: BLoC ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>