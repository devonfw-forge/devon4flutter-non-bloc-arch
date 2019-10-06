Page Table of Contents
- [Introduction](#introduction)
- [Example App State](#example-app-state)
- [Provider Package](#provider-package)
  - [Why I decided against it](#why-i-decided-against-it)
- [Redux](#redux)
- [Bloc](#bloc)

## Introduction

Other then many mobile development frameworks, Flutter [(Flutter Dev Team 2018h)](https://flutter.dev/) does not impose any kind of architecture or statemanagement solution on it’s developers. This open ended approach has lead to multiple statemanagement solution and a hand full of architectural approaches spawning from the community. Some of these approaches have even been indorsed by the Flutter Team itself [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt). I will now showcase the three most popular statemanagement solution briefly to explain why I ended up choosing the BLoC Pattern [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE) in combination with a layered architecture for this guide.

## Example App State

I will showcase the statemanagement solutions using one example of *App State* from the Wisgen App [(Faust 2019)](https://github.com/Fasust/wisgen). We have a list of favorite wisdoms in the Wisgen App. This State is needed by 2 parties:

1.  The ListView on the favorite page, so it can display all favorites
2.  The button on every wisdom card so it can add a new favorite to the list and show if a given wisdom is a favorite.

![Wisgen WidgetTree Favorites](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-fav-mock.png)

*Figure XXX: Wisgen Favorites [(Faust 2019)](https://github.com/Fasust/wisgen)*

So when ever the favorite button on any card is pressed, a number of widgets [(Flutter Dev Team 2019c)](https://flutter.dev/docs/development/ui/widgets-intro) have to update. This a simplified version of the Wisgen WidgetTree, the red highlights show the widgets that need access to the favorite list, the heart shows a possible location from where a new favorite could be added.

![Wisgen WidgetTree Favorites](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-fav.PNG)

*Figure XXX: Wisgen WidgetTree Favorites [(Faust 2019)](https://github.com/Fasust/wisgen)*

## Provider Package

The Provider Package [(Rousselet and Flutter Dev Team 2018)](https://pub.dev/packages/provider) is an open source package for Flutter developed by Remi Rousselet in 2018. It has since then been endorsed by the Flutter Team on multiple achsions (Hracek and Sullivan 2019; Sullivan and Hracek 2019) and they are now devolving it in cooperation. The package is basically a prettier interface to interact with Inherited Widgets [(Flutter Dev Team 2018c)](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html) and expose state from a Widget at the top of the tree to a Widget at the bottom.

As a quick reminder: Data in Flutter always flows **downwards**. If you want to access data from multiple locations withing your widget tree, you have to place it at one of there common ancestors so they can both access it through their build contexts. This practice is called *lifting state up* and it a common practice within declarative frameworks [(Egan 2018)](https://www.youtube.com/watch?v=zKXz3pUkw9A).

The Provider Package is an easy way for us to lift state up. Let’s look at our example form figure XXX: The first common ancestor of all widgets in need of the favorite list is *MaterialApp*. So we will need to lift the state up to the MaterialApp and then have our widgets access it from there:

![Wisgen WidgetTree Favorites with Provider](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-provider.PNG)

*Figure XXX: Wisgen WidgetTree Favorites with Provider [(Faust 2019)](https://github.com/Fasust/wisgen)*

To minimize re-builds the Provider Package uses ChangeNotifiers [(Flutter Dev Team 2018b)](https://api.flutter.dev/flutter/foundation/ChangeNotifier-class.html). This way Widgets can subscribe/listen to the Sate and get notified whenever the state changes. This is how an implementation of Wisgens favorite list would look like using Provider: *Favorites* is the class we will use to provide our favorite list globally. The *notifyListeners()* function will trigger rebuilds on all Widgets that listen to it.

``` dart
class Favorites with ChangeNotifier{
  final List<Wisdom> _wisdoms = new List();

  add(Wisdom w){
    _wisdoms.add(w);
    notifyListeners();
  }

  remove(Wisdom w){
    _wisdoms.remove(w);
    notifyListeners();
  }

  bool contains(Wisdom w){
    return _wisdoms.contains(w);
  }
}
```

*Codesnippt XXX: Favorites Class that will be exposed through Provider Package [(Faust 2019)](https://github.com/Fasust/wisgen)*

Here expose our Favorite class globally above *MaterialApp* in the WidgetTree:

``` dart
void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      builder: (_) => Favorites(),
      child: MaterialApp(home: WisdomFeed()),
    );
  }
}
```

*Codesnippt XXX: Providing Favorites Globally [(Faust 2019)](https://github.com/Fasust/wisgen)*

This is how listening to the Favorite class looks like. We use the *Consumer Widget* to get access to the favorite list and everything below the Consumer Widget will be rebuild when the favorites list changes.

``` dart
...
Expanded(
  flex: 1,
  child: Consumer<Favorites>(
    builder: (context, favorites, child) => IconButton(
      icon: Icon(favorites.contains(wisdom)
          ? Icons.favorite
          : Icons.favorite_border),
      color: favorites.contains(wisdom) ? Colors.red : Colors.grey,
      onPressed: () {
        if (favorites.contains(wisdom)) favorites.remove(wisdom);
        else favorites.add(wisdom);
      },
    ),
  ),
)
...
```

*Codesnippt XXX: Consuming Provider in Favorite Button of Wisdom Card [(Faust 2019)](https://github.com/Fasust/wisgen)*

### Why I decided against it

All in all Provider is a great and easy solution to distribute State in a small Flutter applications. But it not an architecture (Hracek and Sullivan 2019; Boelens 2019; Savjolovs n.d.; Sullivan and Hracek 2019). Just the provider package alone with no pattern to follow or an architecture to obey will not lead to a clean and manageable application. But no worries, I did not teach you about the package for nothing. Because provider is such an efficient and easy way to distribute state, the BLoC package [(Angelov and Contributors 2019)](https://felangel.github.io/bloc/#/) uses it as an underlying technologie for their approach.

## Redux

  - Port from React
  - Good approach if you are already familiar
  - Uses a store for BL
  - Not that easy to understand

## Bloc

  - Goal:
      - Extract the Logic into a class that can be calls from 2 different independent interfaces (AngularDart and Flutter)
  - Streams
  - build by google engniers
  - used by google internally
  - Google went bach and forth on this as well.
  - Why BLoC …
      - Produces nice layered architecture
          - Makes sense for big applications
      - Specifically build for this
      - Used by the people who build the framework
      - \-\> Not better or worse then Redux, but thats why I choose BLoC

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/220-BLoC">Next Chapter: BLoC ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>