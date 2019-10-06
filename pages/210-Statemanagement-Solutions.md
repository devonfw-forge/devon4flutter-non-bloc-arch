Page Table of Contents
- [Introduction](#introduction)
- [Example](#example)
- [Provider](#provider)
- [Redux](#redux)
- [Bloc](#bloc)

## Introduction

Other then many mobile development frameworks, Flutter [(Flutter Dev Team 2018g)](https://flutter.dev/) does not impose any kind of architecture or statemanagement solution on it’s developers. This open ended approach has lead to multiple statemanagement solution and a hand full of architectural approaches spawning from the community. Some of these approaches have even been indorsed by the Flutter Team itself [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt). I will now showcase the three most popular statemanagement solution briefly to explain why I ended up choosing the BLoC Pattern [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE) in combination with a layered architecture for this guide.

## Example

I will showcase the statemanagement solutions using one example of *App State* from the Wisgen App [(Faust 2019)](https://github.com/Fasust/wisgen). We have a list of favorite wisdoms in the Wisgen App. This State is needed by 2 parties:

1.  The ListView on the favorite page, so it can display all favorites
2.  The button on every wisdom card so it can add a new favorite to the list and show if a given wisdom is a favorite.

![Wisgen WidgetTree Favorites](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-fav-mock.png)

*Figure XXX: Wisgen Favorites [(Faust 2019)](https://github.com/Fasust/wisgen)*

So when ever the favorite button on any card is pressed, a number of widgets have to update. This a simplified version of the Wisgen WidgetTree, the red highlights show the widgets that need access to the favorite list, the heart shows a possible location from where a new favorite could be added.

![Wisgen WidgetTree Favorites](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-fav.PNG)

*Figure XXX: Wisgen WidgetTree Favorites [(Faust 2019)](https://github.com/Fasust/wisgen)*

## Provider

  - One Approach advicate by Google
  - Uising a package to hide Inherted widgets behind a nice interface
  - Access through context
  - used by google internally
  - Simple but not really an architecture

The Provider Package [(Rousselet and Flutter Dev Team 2018)](https://pub.dev/packages/provider) is an open source package for Flutter developed by Remi Rousselet in 2018. It has since then been endorsed by the Flutter Team on multiple achsions (<span class="citeproc-not-found" data-reference-id="sullivanPragmaticStateManagement">**???**</span>; Sullivan and Hracek 2019) and they are now devolving it in cooperation. The package is basically a prettier interface to interact with inherited widgets [(Flutter Dev Team 2018b)](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html) and expose state from a widget at the top of the widget tree to a widget at the bottom. As a quick reminder: Data in Flutter always flows **downwards**. If you want to access data from multiple locations withing your widget tree, you have to place it above/at one of there common ancestors so they can both access it through their build contexts. This practice is called *lifting state up* and it a common practice within declarative frameworks. The Provider Package is an easy way for us to lift state up. Let’s look at our example form figure XXX: The first common ancestor of all widgets in need of the favorite list is *MaterialApp*. So we will need to lift the state up to the MaterialApp and then have our widgets access it from there:

![Wisgen WidgetTree Favorites with Provider](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-provider.PNG)

*Figure XXX: Wisgen WidgetTree Favorites with Provider [(Faust 2019)](https://github.com/Fasust/wisgen)*

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