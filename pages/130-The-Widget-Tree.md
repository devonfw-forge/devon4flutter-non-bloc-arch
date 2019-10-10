Page Table of Contents
- [Introduction](#introduction)
- [Widgets in General](#widgets-in-general)
  - [The Widget Tree](#the-widget-tree)
  - [Buildcontext](#buildcontext)
- [The three types of Widgets](#the-three-types-of-widgets)
  - [Stateless Widgets](#stateless-widgets)
  - [Stateful Widgets](#stateful-widgets)
  - [When to use Stateless & When to use Stateful](#when-to-use-stateless--when-to-use-stateful)
  - [Inherited Widgets](#inherited-widgets)

## Introduction

This section will give you a better understanding of how programming in Flutter [\[1\]](https://flutter.dev/) actually works. You will learn what Widgets [\[27\]](https://flutter.dev/docs/development/ui/widgets-intro) are, what types of Widgets Flutter has and lastly what exactly the *Widget Tree* is.

## Widgets in General

One sentence you can simply not avoid when researching Flutter is:

> “In Flutter, everything is a Widget.” [\[27\]](https://flutter.dev/docs/development/ui/widgets-intro)

But that is not really helpful, is it? Personally, I like Didier Boelens definition of Flutter Widgets better:

| 📙 | Widget | A visual component (or a component that interacts with the visual aspect of an application) [\[28\]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956) |
| - | ------ | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |

Let’s have look at an example, this app displays an endless feed of Wisdoms combined with vaguely thought-provoking stock images:

![Wisgen Widgets](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-widgets.png)

*Figure 8: Wisgen Widgets [\[11\]](https://github.com/Fasust/wisgen)*

As you can see, all UI-Components of the app are Widgets. From high-level stuff like the App-Bar and the ListView down to to the granular things like texts and buttons (I did not highlight every Widget on the screen to keep the figure from becoming overcrowded). In code, the build method of a card Widget would look something like this:

``` dart
@override
Widget build(BuildContext context) {
  return Card(
    shape: RoundedRectangleBorder( //Declare corners to be rounded
      borderRadius: BorderRadius.circular(7),
    ),
    elevation: 2, //Declare shadow drop
    child: Column( //The child of the card should be displayed in a column Widget
      children: <Widget>[ //The card contains an image and some content
        _image(context),
        _content(context),
      ],
    ),
  );
}
```

*Code Snippet 5: Wisgen Card Widget [\[11\]](https://github.com/Fasust/wisgen)*

The functions \_image() generates a Widget that contains the stock image. The function \_content() generates a Widget that displays the wisdom text and the buttons on the card.
Another important thing to note is that:

| ⚠ | Widgets in Flutter are always immutable [\[27\]](https://flutter.dev/docs/development/ui/widgets-intro) |
| - | :------------------------------------------------------------------------------------------------------ |

The build method of any given Widget can be called multiple times a second. And how often it is called exactly is never under your control, it is controlled by the Flutter Framework [\[29\]](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html). To make this rapid rebuilding of Widgets efficient, Flutter forces us developers to keep the build methods lightweight by making all Widgets immutable. This means that all variables in a Widget have to be declared as *final*. Which means they are initialized once and can not change over time.
But your app never consists out of exclusively immutable parts, does it? Variables need to change, data needs to be fetched and stored. Almost any app needs some sort of mutable data. As mentioned in the [previous chapter](https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively), in Flutter such data is called *State* [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). No worries, how Flutter handles mutable State will be covered in the section [Stateful Widgets](#stateful-widgets) down below, so just keep on reading.

### The Widget Tree

When working with Flutter, you will inevitably stumble over the term *Widget Tree*, but what exactly does it mean? A UI in Flutter is nothing more than a tree of nested Widgets. Let’s have a look at the Widget Tree for our example from Figure 8. Note the card Widgets on the right-hand side of the diagram. You can see how the code from snippet 5 translates to Widgets in the Widget Tree.

![Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-widget-tree.PNG)

*Figure 9: Wisgen Widget Tree [\[11\]](https://github.com/Fasust/wisgen)*

### Buildcontext

If you have previously built an App with Flutter, you have definitely encountered *BuildContext* [\[30\]](https://api.flutter.dev/flutter/widgets/BuildContext-class.html). It is passed in as a variable in every Widget build method in Flutter. But what exactly is *BuildContext*?

| 📙 | BuildContext | A reference to the location of a Widget within the tree structure of all the Widgets that have been built [\[28\]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956) |
| - | ------------ | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

The BuildContext contains information about each *ancestor* leading down to the Widget that the context belongs to. So it is an easy way for a Widget to access all its ancestors in the Widget Tree. Accessing a Widgets *descendants* through the BuildContext is possible, but not advised and inefficient. So in short: For a Widget at the bottom of the tree, it is very easy to get information from Widgets at the top of the tree but **not** vice-versa [\[28\]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956). For example, the image Widget from Figure 9 could access its ancestor card Widget like this:

``` dart
Widget build(BuildContext context) {

  //going up the Widget Tree: 
  //(Image [me]) -> (Column) -> (Card) [!] first match, so this one is returned
  Card ancestorCard = Card.of(context); 

  return CachedNetworkImage(
    ...
  );
}
```

*Code Snippet 6: Hypothetical Wisgen Image Widget [\[11\]](https://github.com/Fasust/wisgen)*

Alright, but what does that mean for me as a Flutter developer? It is important to understand how data in Flutter flows through the Widget Tree: **Downwards**. You want to place information that is required by multiple Widgets above them in the tree, so they can both easily access it through their BuildContext. Keep this in mind, for now, I will explain this in more detail in the chapter [Architecting a Flutter App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App).

## The three types of Widgets

There are three types of Widgets in the Flutter framework. I will now showcase their differences, their lifecycles, and their respective use-cases.

### Stateless Widgets

This is the most basic of the three and likely the one you’ll use the most when developing an app with Flutter. Stateless Widgets [\[29\]](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html) are initialized once with a set of parameters and those parameters will never change from there on out. Let’s have a look at an example. This is the class of the card Widget from figure 8:

``` dart
class WisdomCard extends StatelessWidget {
  static const double _smallPadding = 4;
  static const double _largePadding = 8;
  static const double _imageHeight = 300;
  static const double _cardElevation = 2;
  static const double _cardBorderRadius = 7;

  final Wisdom wisdom;

  WisdomCard({Key key, this.wisdom}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    ...
  }
}
```

*Code Snippet 7: Wisgen Card Widget Class [\[11\]](https://github.com/Fasust/wisgen)*

As you can see, it has some const values for styling, a wisdom object that is passed into the constructor and a build method. The wisdom object contains the wisdom text and the hyperlink for the stock image.

One thing I want to point out here is that even if all fields are final in a StatelessWidget, it can still change to a degree. A ListView Widget is also a Stateless for example. It has a final reference to a list. Things can be added or removed from that list without the reference in the ListView Widget changing. So the ListView remains immutable and Stateless while the things it displays can change [\[31\]](https://www.youtube.com/watch?v=wE7khGHVkYY).

The Lifecycle of Stateless Widgets is very straight forward [\[28\]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956):

``` dart
class MyWidget extends StatelessWidget {

  //Called first
  //Use for initialization if needed
  MyClass({ Key key }) : super(key: key);

  //Called multiple times a second
  //Keep lightweight
  //This is where the actual UI is build
  @override
  Widget build(BuildContext context) {
    ...
  }
}
```

*Code Snippet 8: Stateless Widget Lifecycle*

### Stateful Widgets

I have explained what State is in the Chapter [Thinking Declaratively](https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively). But just as a reminder:

| 📙 | State | Any data that can change over time [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) |
| - | ----- | :------------------------------------------------------------------------------------------------------------ |

A Stateful Widget [\[32\]](https://api.flutter.dev/flutter/widgets/StatefulWidget-class.html) always consists of two parts: An immutable Widget and a mutable State. The immutable Widget’s responsibility is to hold onto that State, the State itself has the mutable data and builds the actual Widget [\[33\]](https://www.youtube.com/watch?v=AqCMFXEmf3w). Let’s have a look at an example. This is a simplified version of the WisdomFeed from Figure 8. The *WisdomBloc* is responsible for generating and cashing wisdoms that are then displayed in the Feed. More on that in the chapter [Architecting a Flutter App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App).

``` dart
//Immutable Widget
class WisdomFeed extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => WisdomFeedState();
}

//Mutable State
class WisdomFeedState extends State<WisdomFeed>{
  WisdomBloc _wisdomBloc; //mutable/not final (!)

  @override
  Widget build(BuildContext context) {
    //this is where the actual Widget is build
    ...
  }
}
```

*Code Snippet 9: Wisgen WisdomFeed [\[11\]](https://github.com/Fasust/wisgen)*

If you are anything like me, you will ask yourself: “why is this split into 2 parts? The StatefulWidget is not really doing anything.” Well, The Flutter Team wants to keep Widgets **always** immutable. The only way to keep this statement universally true is to have the StatefulWidget hold onto the State but not actually be the State \[33\], \[34\].

State objects have a long lifespan in Flutter. This means that they will stick around during rebuilds or even if the Widget that they are linked to gets replaced [\[33\]](https://www.youtube.com/watch?v=AqCMFXEmf3w). So in this example, no matter how often the WisdomFeed gets rebuild and no matter if the user switches pages, the cashed list of wisdoms (WisdomBloc) will stay the same until the app is shut down.

The Lifecycle of State Objects/StatefulWidgets is a little bit more complex, here is a boiled-down version of it with all the methods you’ll need for this guide. You can read the full Lifecycle here: Lifecycle of StatefulWidgets [\[34\]](https://flutterbyexample.com//stateful-widget-lifecycle).

``` dart
class MyWidget extends StatefulWidget {

  //Called Immediately when first building the StatefulWidget
  @override
  State<StatefulWidget> createState() => MySate();
}

class MySate extends State<MyWidget>{
  
  //Called after constructor
  //Called exactly once
  //Use this to subscribe to streams or for any initialization
  @override
  initState(){...}

  //Called multiple times a second
  //Keep lightweight
  //This is where the actual UI is build
  @override
  Widget build(BuildContext context){...}

  //Called once before the State is disposed(app shut down)
  //Use this for your clean up and to unsubscribe from streams
  @override
  dispose(){...}
}
```

*Code Snippet 10: State Objects/StatefulWidgets Lifecycle*

### When to use Stateless & When to use Stateful

Keep in mind, to improve performance, you always want to rely on as few Stateful Widgets as possible.
But there are essentially two reasons to choose a Stateful Widget over a Stateless one:

1.  The Widget needs to hold any kind of data that has to change during its lifetime.
2.  The Widget needs to dispose of anything or clean up after itself at the end of its lifetime.

### Inherited Widgets

I will not go in detail on Inherited Widgets [\[35\]](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html) here. When using the BLoC library [\[36\]](https://felangel.github.io/bloc/#/), which I will teach you in the chapter [Architecting a Flutter-App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App), you will most likely never create an Inherited Widgets yourself. But in short: They are a way to expose data from the top of the Widget Tree to all their descendants. And they are used as the underlying technology of the BLoC library.

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/140-Asynchronous-Flutter">Next Chapter: Asynchronous Flutter ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>