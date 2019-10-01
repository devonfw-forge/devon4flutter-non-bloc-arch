Page Table of Contents
- [Introduction](#introduction)
- [Widgets in General](#widgets-in-general)
  - [The Widget Tree](#the-widget-tree)
  - [Buildcontext](#buildcontext)
- [The three types of Widgets](#the-three-types-of-widgets)
  - [Stateless Widgets](#stateless-widgets)
  - [Stateful Widgets](#stateful-widgets)
  - [When to use Stateless & When to use Statefull](#when-to-use-stateless--when-to-use-statefull)
  - [Inherited Widgets](#inherited-widgets)
- [How to access State](#how-to-access-state)
- [References](#references)

## Introduction
This section will give you a better understanding of how programming in [Flutter (Flutter Dev Team 2018)](https://flutter.dev/) actually works. You will learn what Widgets are, what types of Widgets Flutter has and lastly what exactly the [_Widget Tree_ (Flutter Dev Team 2019)](https://flutter.dev/docs/development/ui/widgets-intro) is.

## Widgets in General
One sentence you can simply not avoid when researching Flutter is:
> "In Flutter, everything is a Widget."

But that is not really helpful, is it? Personally, I like Didier Boelens definition of Flutter Widgets better:
> Think of a Widget as a visual component (or a component that interacts with the visual aspect of an application).

Let's have look at an example, this app displays an endless feed of Wisdoms combined with vaguely thought provoking stock images:

![Wisgen Widgets](https://github.com/Fasust/flutter-guide/wiki//.images/wisgen-widgets.png)

_Figure 1: [Wisgen Widgets (Faust 2019)](https://github.com/Fasust/wisgen)_

As you can see, all UI-Components of the app are Widgets. From high level stuff like the App-Bar and the ListView down to to the granular things like texts and buttons (I did not highlight every Widget on screen to keep the figure from becoming over crowded). In code, the build method of a card Widget would look something like this:

```dart
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
_Codesnippt 1: [Wisgen Card Widget (Faust 2019)](https://github.com/Fasust/wisgen)_

The functions _image() generates a Widget that contains the stock image. The function _content() generate a Widget that displays the wisdom text and the buttons on the card. 
Another important thing to note is that:

| ⚠   | Widgets in Flutter are always immutable |
| --- | :-------------------------------------- |

The build method of any given Widget can be called multiple times a second. And how often it is called exactly is never under your control, it is controlled by the Flutter Framework. To make this rapid rebuilding of Widgets efficient, Flutter forces us developers to keep the build methods light weight by making all Widgets immutable. This means that all variables in a Widget have to be declared as _final_. Which means they are initialized once and can not change over time. 
But your app never consists out of exclusively immutable parts, does it? Variables need to change, data needs to be fetched and stored. Almost any app needs some sort of mutable data. As mentioned in the previous chapter, in Flutter such data is called _state_. No worries, how Flutter handles mutable _state_ will be covered in the section [Stateful Widgets](#stateful-widgets) down below, so just keep on reading.

### The Widget Tree
When working with Flutter, you will inevitably stumble over the term _Widget Tree_, but what exactly does it mean? A UI in flutter is nothing more then a tree of nested Widgets. Let's have a look at the Widget Tree for our example from Figure 1. Note the card Widgets on the right hand side of the diagram. You can see how the code from Snippet 1 translates to Widgets in the Widget Tree.

![Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//.images/wisgen-widget-tree.PNG)

_Figure 2: [Wisgen Widget Tree (Faust 2019)](https://github.com/Fasust/wisgen)_

### Buildcontext
If you have previously build an App with Flutter, you have definitely encountered _BuildContext_. It is passed in as a variable in every Widget build methode in Flutter. But what exactly is _BuildContext_? As Didier Boelens puts it:
> "A BuildContext is nothing else but a reference to the location of a Widget within the tree structure of all the Widgets which are built."

The BuildContext contain information about each *ancestor* leading down to the Widget that the context belongs to. So it is an easy way for a Widget to access all its ancestors in the Widget tree. Accessing a Widgets *descendance* through the BuildContext is possible, but not advised and inefficient. So in short: For a Widget at the bottom of the tree, it is very easy to get information from Widgets at the top of the tree but **not** visversa. For example, the image Widget from Figure 2 could access it's ancestor card Widget like this:
```dart
Widget build(BuildContext context) {

  //going up the Widget tree: 
  //(Image [me]) -> (Column) -> (Card) [!] first match, so this one is returned
  Card ancestorCard = Card.of(context); 

  return CachedNetworkImage(
    ...
  );
}
```
_Codesnippt 2: [Hypothetical Wisgen Image Widget (Faust 2019)](https://github.com/Fasust/wisgen)_

Alright, but what does that mean for me as a Flutter developer? It is important to understand how data in Flutter flows through the Widget Tree: **Downwards**. You want to place information that is required by multiple Widgets above them in the tree, so they can both easily access it through their BuildContext. Keep this in mind for now, I will explain this in more detail in the chapter [Architekting a Flutter App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App).

## The three types of Widgets
There are 3 types of Widgets in the Flutter framework. I will now showcase there differences, there lifecycles and their respective usecases.

### Stateless Widgets
This is the most basic of the Three an likely the one you'll use the most when developing an app with Flutter. Stateless Widgets are initialized once with a set of parameters and those parameters will never change from there on out. Let's have a look at an example. This is the class of the card Widget from figure 1:

```dart
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
_Codesnippt 3: [Wisgen Card Widget Class (Faust 2019)](https://github.com/Fasust/wisgen)_

As you can see, it has some const values for styling, a Wisdom object that is passed into the constructor and a build methode. The Wisdom object contains the wisdom text and the hyperlink for the stock image.

One thing I want to point out here is that even if all fields are final in a StatelessWidget, it can still change to a degree. A ListView Widget is also a Stateless for example. It has a final reference to a list. Things can be added or removed from that list without the reference in the ListView Widget changing. So the ListView remains immutable and Stateless while the things it displays are able to change.

The Lifecycle of Stateless Widgets is very straight forward:

```dart
class MyClass extends StatelessWidget {

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
_Codesnippt 4: Stateless Widget Lifecycle_

### Stateful Widgets
I have explained what State is in the Chapter [120 Thinking Declaratively](https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively#declarative-programming-in-flutter). But just as a reminder:

| ⚠   | State in Flutter is any data that can change over time |
| --- | :----------------------------------------------------- |

A Stateful Widget always consist of two parts: An immutable Widget and a mutable state. The immutable Widgets responsibility is to hold onto that state, the state itself has the mutable data and builds the actual Widget. Let's have a look at an example. This is a simplified version of the WisdomFeed from Figure 1:

```dart
//Immutable Widget
class WisdomFeed extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => WisdomFeedState();
}

//Mutable State
class WisdomFeedState extends State<WisdomFeed>{
  WisdomBloc _wisdomBloc; //not final (!)

  @override
  Widget build(BuildContext context) {
    //this is where the actual Widget is build
    ...
  }
}
```
_Codesnippt 5: [Wisgen WisdomFeed (Faust 2019)](https://github.com/Fasust/wisgen)_

- what is state 
  - mutable
  - "n properties that change over time"
  - long life span
  - sticks around
- why 2 party
- Lifecycle
### When to use Stateless & When to use Statefull
### Inherited Widgets
I will not go in detail on Inherited Widgets here. When using the BLoC pattern, which I will teach you in the next chapter, you will most likely never create an Inherited Widgets yourself. But in short: They are a way to expose data from the top if the Widget Tree to all there descendance. And they are used as the underlying technologie of the BLoC library.

## How to access State
- Lifting up
- Inherited Widget
- 2 types of state
  - local and global
  - more in architecture

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/140-Communication-with-the-Web">Next Chapter: Communication with the Web ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>

---
## References 