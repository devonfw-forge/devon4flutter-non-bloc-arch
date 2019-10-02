# 000 Introduction

## The Goal of this Guide

This guide aims to bridge the gap between the absolute Flutter [(Flutter Dev Team 2018e)](https://flutter.dev/) basics and clean, structured Flutter development. It should bring you from the basics of knowing how to build an app with Flutter to an understanding of how to do it *properly*. Or at least show you one possible way to make large scale Flutter projects clean and manageable.

## Who is this Guide for?

For people with a basic knowledge of the Flutter Framework. I recommend following this tutorial by the Flutter team [(Flutter Dev Team 2018f)](https://flutter.dev/docs/get-started/codelab). It will walk you through developing your first flutter application. You should also have a basic understanding of the Dart programming language [(Dart Team 2019a)](https://dart.dev/). No worries, it is very similar to Java [(Oracle 1996)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), Kotlin [(Jet Brains 2017)](https://kotlinlang.org/) and JavaScript [(ECMA 1997)](https://www.ecma-international.org/publications/standards/Ecma-262.htm). So if you know 1 or 2 of those languages you should be fine. Lastly, for the Architecture chapter, you should have a basic understanding of Data Streams [(Dart Team 2019b)](https://dart.dev/tutorials/language/streams).

## Topics that will be covered

  - A brief introduction to the Flutter Framework in general. How it works *under the hood* and its underlying structure.
  - One possible architecture for your Flutter app and how to implement it (BLoC [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE))
  - How to test your app
  - Some conventions and best practices for Dart, BLoC and the Flutter Framework
  - My personal opinion of the framework

## Creation Context

This guide was written by a student in the Bachelor of Science Program “Computer Science and Media Technology” at Technical University Cologne [(Technical University Cologne 2019)](https://www.th-koeln.de/en/homepage_26.php), and it was created for one of the modules in that Bachelor. In addition to this, the guide was written in collaboration with Capgemini Cologne [(Capgemini 2019)](https://www.capgemini.com/us-en/). Capgemini released a guide on building an application in Angular [(Ambuludi, Linares, and Contributors 2019)](https://github.com/devonfw/devon4ng) in May of 2019, this guide is meant to be the the flutter version of that.

## Structure

The guide is designed to be read in order, from Chapter 0 (this one) to Chapter 5. Code examples throughout the chapters will mainly be taken from Wisgen [(Faust 2019)](https://github.com/Fasust/wisgen), an example Flutter Application that was specifically built for the purposes of this guide.

## My Sources

I am basing this guide on a combination of conference talks, blog articles by respected Flutter developers, official documentaions, scientific papers that cover cross-platform mobile development in gerneral and many other sources. All sources used in the guide are listed in the chapter [*References*](https://github.com/Fasust/flutter-guide/wiki/600-References). To better understand all the theory, I also developed the Wisgen app [(Faust 2019)](https://github.com/Fasust/wisgen) using the Flutter Framework and the BLoC Pattern [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE).

# 100 The Flutter Framework

## Introduction

This Chapter will give you a basic understanding of how the Flutter Framework [(Flutter Dev Team 2018e)](https://flutter.dev/) works as a whole. I will showcase the difference of Flutter to other Cross-Platform approaches and how Flutter works *under the hood*. You will also be introduced to the concepts of *state* [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt) and flutters way of rendering an app as a tree of *widgets*. In addition to this, You will gain an understanding of how Flutter Handels Asynchronous Programming. And Lastly, you will learn how to communicate with the Web within the Flutter Framework.

## Contents of the Chapter

  - [Under The Hood](https://github.com/Fasust/flutter-guide/wiki/110-Under-the-Hood)
  - [Thinking Declaratively](https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively)
  - [The Widget Tree](https://github.com/Fasust/flutter-guide/wiki/130-The-Widget-Tree)
  - [Asynchronous Flutter](https://github.com/Fasust/flutter-guide/wiki/140-Asynchronous-Flutter)
  - [Communication with the Web](https://github.com/Fasust/flutter-guide/wiki/150-Communication-with-the-Web)

# 110 Under The Hood

## Introduction

Flutter [(Flutter Dev Team 2018e)](https://flutter.dev/) is a framework for cross-platform native development. But what exactly does that mean? It means that it promises Native App performance while still compiling apps for multiple platforms from a single codebase. The best way to understand how flutter achieves this, is to compare it to other mobile development approaches.

### Full Native Approach

![Native app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/native-rendering.png)

*Figure 1: Native app rendering [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

The classic way to build a mobile app, would be to write native code for each platform you want to support. I.E. One for IOS [(Apple 2010)](https://developer.apple.com/ios/), one for Android [(Google LLC 2008)](https://developer.android.com/) and so on. In this approach your app will be written in a platform specific language and render through platform specific widgets and a platform specific engine. During the development you have direct access to platform specific services and sensors (Google LLC 2019; Stoll 2018; Leler 2017). But you will have to build the same app multiple times, which effectively doubles your workload.

### Embedded WebApp Approach

![Embedded Web App rendering](https://github.com/Fasust/flutter-guide/wiki//.images/webview-rendering.png)

*Figure 2: Embedded WebApp rendering [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Embedded WebApps where the first approach to cross-platform development. You would simply build your application with HTML, CSS and JavaScript and then have it render through a native WebView(Google LLC 2019; Leler 2017). The Problem here is, that developers are limited to the web technology stack and that communication between the app and native services would always have to run through a *bridge* (Stoll 2018).

#### Bridges

Bridges connect components with one another. These components can be build in the same or different programming languages (Adinugroho, Reina, and Gautama 2015).

### Reactive View Approach

![Reactive app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/reactive-rendering.png)

*Figure 3: Reactive app rendering [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Apps build with reactive frameworks (like React Native [(Facebook 2015)](https://facebook.github.io/react-native/)) are mostly written in a platform independent language like JavaScript [(ECMA 1997)](https://www.ecma-international.org/publications/standards/Ecma-262.htm). The JavaScript code then sends information on how UI components should be displayed to the native environment. This communication always runs through a *bridge*. So we end up with native widgets that are controller through JavaScript. The main problem here, is that the communication through the *bridge* is a bottleneck which can lead to performance issus (Google LLC 2019; Stoll 2018; Leler 2017; Kol 2017).

### Flutters Approach

![Flutter app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/flutter-rendering.png)

*Figure 4: Flutter app rendering [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Flutters approach is to move the entire rendering process into the app. The rendering runs through Flutters own engine and uses flutters own widgets. It only needs a canvas to display the rendered frames on and system events/input it can then forward to your app. The framework also provides a way to access services and sensors through platform independent interfaces. This way the *bridging* between the app and the native environment is kept to a minimum which removes that bottleneck (Google LLC 2019; Stoll 2018; Leler 2017).

You might think that keeping an entire rendering engine inside your app would lead to huge APKs, but as of 2019 the compressed framework is only 4.3MB [(Flutter Dev Team 2019a)](https://flutter.dev/docs/resources/faq).

![Flutter Framework Architecture](https://github.com/Fasust/flutter-guide/wiki//.images/flutter-architecture.png)

*Figure 5: Flutter Framework Architecture [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

| 🕐 TLDR | Flutter uses it’s own engine instead of using the native one. The native environment only renders the finished frames. |
| ------ | :--------------------------------------------------------------------------------------------------------------------- |

## Flutter Compiler

One additional advantage of Flutter, is that is comes with two different compilers. A JIT-Compiler (Just in time) and a AOT-Compiler (Ahead of Time). The following table will showcase the advantage of each:

| Compiler      | What is does                                                                                                                                                                                                                                                                        | When it’s used     |
| :------------ | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :----------------- |
| Just in Time  | Only re-compiles files that have changed. Preserves App State [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt) during rebuilds. Enables *Hot Reload* [(Flutter Dev Team 2019d)](https://flutter.dev/docs/development/tools/hot-reload). | During Development |
| Ahead of Time | Compiles all dependencies ahead of time. The output app is faster.                                                                                                                                                                                                                  | For Release        |

*Table 1: Flutters 2 Compliers (Moore and Nystrom 2019; Google LLC 2019)*

## Hot Reload

*Hot Reload* [(Flutter Dev Team 2019d)](https://flutter.dev/docs/development/tools/hot-reload) is a feature web developers are already very familiar with. It essentially means, that your changes in the code are displayed in the running application near instantaneously. Thanks to Flutters JIT Complier, it is also able to provide this feature.

![Hot Reload](https://github.com/Fasust/flutter-guide/wiki//.images/hot-reload.gif)

*Figure 6: Hot Reload [(Flutter Dev Team 2019d)](https://flutter.dev/docs/development/tools/hot-reload)*

# 120 Thinking Declaratively

## Introduction

If you come from the native mobile world and *imperative* frameworks like IOS [(Apple 2010)](https://developer.apple.com/ios/) and Android [(Google LLC 2008)](https://developer.android.com/), developing with Flutter [(Flutter Dev Team 2018e)](https://flutter.dev/) can take while to get used to. Flutter, other then those frameworks mentioned above, is a *declarative* Framework. This section will teach you how to think about developing apps declaratively and one of the most important concepts of Flutter: *state* [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt).

## Declarative Programming vs Imperative Programming

But what exactly is the difference between *declarative* and *imperative*? I will try to explain this using a metaphor: For a second, let’s think of programming as *talking* to the underlying framework. In this context, an imperative approach is telling the framework **exactly** what you want it to do. “Imperium” (Latin) means “to command”. A declarative approach, on the other hand, would be describing to the framework what kind of result you want to get and letting the framework decide on how to achieve that result. “Declaro” (Latin) means “to explain” (Flutter Dev Team 2018e, 2019b, 2019e; Bezerra 2018). Let’s look at an example:

``` dart
List numbers = [1,2,3,4,5]
for(int i = 0; i < numbers.length; i++){
    if(numbers[i] > 3 ) print(numbers[i]);     
}
```

*Codesnippt 1: Number List (Imperative)*

Here we want to print every entry in the list that is bigger then 3. We explicitly tell the framework to go through the List one by one and check each value. In the declarative version, we simply state how our result should look like, but not how to reach it:

``` dart
List numbers = [1,2,3,4,5]
print(numbers.where((num) => num > 3));
```

*Codesnippt 2: Number List (Declarative)*

One important thing to note here is, that the difference between imperative and declarative is not black and white. One style might bleed over into the other. Prof. David Brailsford from the University of Nottingham argues that as soon as you start using libraries for your imperative projects, they become a tiny bit mor declarative. This is because you are then using functions that *describe* what they do and you no longer care how they do it [(Computerphile 2016)](https://www.youtube.com/watch?v=4A2mWqLUpzw).

| 🕐 TLDR | Imperative Programming is telling the framework **exactly** what you want it to do. Declarative Programming is describing to the framework what kind of result you want to get and letting the framework decide on how to achieve that result. |
| ------ | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

## Declarative Programming in Flutter

Okay, now that we understand what declarative means, let’s take a look at Flutter specifically. This is a quote from Flutters official documentation:

> “Flutter is declarative. This means that Flutter builds its user interface to reflect the current state of your app” [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)

![UI = f(State)](https://github.com/Fasust/flutter-guide/wiki//.images/ui-equals-function-of-state.png)

*Figure 7: UI = f(State) [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)*

This means that you never imperatively or explicitly call an UI element to change it. You rather *declare* that the UI should look a certain way, given a certain *state* [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt). But what exactly is *state*?

| ⚠ | State in Flutter is any data that can change over time |
| - | :----------------------------------------------------- |

Typical State examples: User Data, the position of a scroll bar, a favorite List

Let’s have a look at a classic UI problem and how we would solve it imperatively in Android and compare it to Flutters declarative approach. let’s say we want to build a button that changes it’s color to red when it is pressed. In Android we find the button by it’s ID, attach a listener and tell that listener to change the background color when the button is pressed:

``` java
Button button = findViewById(R.id.button_id);
button.setBackground(blue);
boolean pressed = false;

button.setOnClickListener(new View.OnClickListener() { 
    @Override
    public void onClick(View view) 
    { 
        button.setBackground(pressed ? red : blue);
    } 
}); 
```

*Codesnippt 3: Red button in Android (Imperative)*

In Flutter on the other hand, we never call the UI element directly, we instead declare that the button background should be red or blue depending on the App-Sate (here the bool “pressed”). We then declare that the onPressed function should update the app state and re-build the button:

``` dart
bool pressed = false; //State

@override
Widget build(BuildContext context) {
    return FlatButton(
        color: pressed ? Colors.red : Colors.blue,
        onPressed: () {
            setState(){ // trigger re-build of the button
                pressed = !pressed;
            } 
        }
    );
}
```

*Codesnippt 4: Red button in Flutter (Declarative)*

## Efficiency of Re-Builds

Is it not very inefficient to re-render the entire Widget every time we change the state? That was the first questions I had when learning about this topic. But I was pleased to learn, that Flutter uses something called “RenderObjects” to improve performance similar to Reacts [(Facebook 2015)](https://facebook.github.io/react-native/) virtual DOM.

> “RenderObjects persist between frames and Flutter’s lightweight Widgets tell the framework to mutate the RenderObjects between states. The Flutter framework handles the rest.” [(Flutter Dev Team 2019e)](https://flutter.dev/docs/get-started/flutter-for/declarative)

# 130 The Widget Tree

## Introduction

This section will give you a better understanding of how programming in Flutter [(Flutter Dev Team 2018e)](https://flutter.dev/) actually works. You will learn what Widgets [(Flutter Dev Team 2019c)](https://flutter.dev/docs/development/ui/widgets-intro) are, what types of Widgets Flutter has and lastly what exactly the *Widget Tree* is.

## Widgets in General

One sentence you can simply not avoid when researching Flutter is:

> “In Flutter, everything is a Widget.” [(Flutter Dev Team 2019c)](https://flutter.dev/docs/development/ui/widgets-intro)

But that is not really helpful, is it? Personally, I like Didier Boelens definition of Flutter Widgets better:

> “Think of a Widget as a visual component (or a component that interacts with the visual aspect of an application).” [(Boelens n.d.)](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956)

Let’s have look at an example, this app displays an endless feed of Wisdoms combined with vaguely thought provoking stock images:

![Wisgen Widgets](https://github.com/Fasust/flutter-guide/wiki//.images/wisgen-widgets.png)

*Figure 1: Wisgen Widgets [(Faust 2019)](https://github.com/Fasust/wisgen)*

As you can see, all UI-Components of the app are Widgets. From high level stuff like the App-Bar and the ListView down to to the granular things like texts and buttons (I did not highlight every Widget on screen to keep the figure from becoming over crowded). In code, the build method of a card Widget would look something like this:

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

*Codesnippt 5: Wisgen Card Widget [(Faust 2019)](https://github.com/Fasust/wisgen)*

The functions \_image() generates a Widget that contains the stock image. The function \_content() generate a Widget that displays the wisdom text and the buttons on the card.
Another important thing to note is that:

| ⚠ | Widgets in Flutter are always immutable [(Flutter Dev Team 2019c)](https://flutter.dev/docs/development/ui/widgets-intro) |
| - | :------------------------------------------------------------------------------------------------------------------------ |

The build method of any given Widget can be called multiple times a second. And how often it is called exactly is never under your control, it is controlled by the Flutter Framework [(Flutter Dev Team 2018d)](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html). To make this rapid rebuilding of Widgets efficient, Flutter forces us developers to keep the build methods light weight by making all Widgets immutable. This means that all variables in a Widget have to be declared as *final*. Which means they are initialized once and can not change over time.
But your app never consists out of exclusively immutable parts, does it? Variables need to change, data needs to be fetched and stored. Almost any app needs some sort of mutable data. As mentioned in the [previous chapter](https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively), in Flutter such data is called *state* [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt). No worries, how Flutter handles mutable state will be covered in the section [Stateful Widgets](#stateful-widgets) down below, so just keep on reading.

### The Widget Tree

When working with Flutter, you will inevitably stumble over the term *Widget Tree*, but what exactly does it mean? A UI in flutter is nothing more then a tree of nested Widgets. Let’s have a look at the Widget Tree for our example from Figure 1. Note the card Widgets on the right hand side of the diagram. You can see how the code from Codesnippt 5 translates to Widgets in the Widget Tree.

![Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//.images/wisgen-widget-tree.PNG)

*Figure 2: Wisgen Widget Tree [(Faust 2019)](https://github.com/Fasust/wisgen)*

### Buildcontext

If you have previously build an App with Flutter, you have definitely encountered *BuildContext* [(Flutter Dev Team 2018a)](https://api.flutter.dev/flutter/widgets/BuildContext-class.html). It is passed in as a variable in every Widget build methode in Flutter. But what exactly is *BuildContext*? As Didier Boelens puts it:

> “A BuildContext is nothing else but a reference to the location of a Widget within the tree structure of all the Widgets which are built.” [(Boelens n.d.)](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956)

The BuildContext contain information about each *ancestor* leading down to the Widget that the context belongs to. So it is an easy way for a Widget to access all its ancestors in the Widget tree. Accessing a Widgets *descendance* through the BuildContext is possible, but not advised and inefficient. So in short: For a Widget at the bottom of the tree, it is very easy to get information from Widgets at the top of the tree but **not** visversa [(Boelens n.d.)](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956). For example, the image Widget from Figure 2 could access it’s ancestor card Widget like this:

``` dart
Widget build(BuildContext context) {

  //going up the Widget tree: 
  //(Image [me]) -> (Column) -> (Card) [!] first match, so this one is returned
  Card ancestorCard = Card.of(context); 

  return CachedNetworkImage(
    ...
  );
}
```

*Codesnippt 6: Hypothetical Wisgen Image Widget [(Faust 2019)](https://github.com/Fasust/wisgen)*

Alright, but what does that mean for me as a Flutter developer? It is important to understand how data in Flutter flows through the Widget Tree: **Downwards**. You want to place information that is required by multiple Widgets above them in the tree, so they can both easily access it through their BuildContext. Keep this in mind for now, I will explain this in more detail in the chapter [Architecting a Flutter App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App).

## The three types of Widgets

There are 3 types of Widgets in the Flutter framework. I will now showcase there differences, there lifecycles and their respective usecases.

### Stateless Widgets

This is the most basic of the Three an likely the one you’ll use the most when developing an app with Flutter. Stateless Widgets [(Flutter Dev Team 2018d)](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html) are initialized once with a set of parameters and those parameters will never change from there on out. Let’s have a look at an example. This is the class of the card Widget from figure 1:

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

*Codesnippt 7: Wisgen Card Widget Class [(Faust 2019)](https://github.com/Fasust/wisgen)*

As you can see, it has some const values for styling, a Wisdom object that is passed into the constructor and a build methode. The Wisdom object contains the wisdom text and the hyperlink for the stock image.

One thing I want to point out here is that even if all fields are final in a StatelessWidget, it can still change to a degree. A ListView Widget is also a Stateless for example. It has a final reference to a list. Things can be added or removed from that list without the reference in the ListView Widget changing. So the ListView remains immutable and Stateless while the things it displays are able to change (LLC 2018).

The Lifecycle of Stateless Widgets is very straight forward (Boelens n.d.):

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

*Codesnippt 8: Stateless Widget Lifecycle*

### Stateful Widgets

I have explained what State is in the Chapter [Thinking Declaratively](https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively). But just as a reminder:

| ⚠ | State in Flutter is any data that can change over time |
| - | :----------------------------------------------------- |

A Stateful Widget [(Flutter Dev Team 2018c)](https://api.flutter.dev/flutter/widgets/StatefulWidget-class.html) always consist of two parts: An immutable Widget and a mutable state. The immutable Widgets responsibility is to hold onto that state, the state itself has the mutable data and builds the actual Widget [(Google LLC 2018)](https://www.youtube.com/watch?v=AqCMFXEmf3w). Let’s have a look at an example. This is a simplified version of the WisdomFeed from Figure 1. The *WisdomBloc* is responsible for generating and cashing wisdoms that are then displayed in the Feed. More on that in the chapter [Architecting a Flutter App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App).

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

*Codesnippt 9: Wisgen WisdomFeed [(Faust 2019)](https://github.com/Fasust/wisgen)*

If you are anything like me, you will ask yourself: “why is this split into 2 parts? The StatefulWidget is not really doing anything.” Well, The Flutter Team wants to keep Widgets **always** immutable. The only way to keep this statement universally true, is to have the StatefulWidget hold onto the State but not actually be the State (Google LLC 2018; Windmill and Contributors 2019).

State objects have a long lifespan in Flutter. This means that they will stick around during rebuilds or even if the widget that they are linked to gets replaced [(Google LLC 2018)](https://www.youtube.com/watch?v=AqCMFXEmf3w). So in this example, no matter how often the WisdomFeed gets rebuild and no matter if the user switches pages, the cashed list of wisdoms (WisdomBloc) will stay the same until the app is shut down.

The Lifecycle of State Objects/StatefulWidgets is a little bit more complex, here is a boiled down version of it with all the methods you’ll need for this guide. You can read the full Lifecycle here: Lifecycle of StatefulWidgets [(Windmill and Contributors 2019)](https://flutterbyexample.com//stateful-widget-lifecycle).

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

  //Called once before the State is disposed (app shut down)
  //Use this for clean up and to unsubscribe from streams
  @override
  dispose(){...}
}
```

*Codesnippt 10: State Objects/StatefulWidgets Lifecycle*

### When to use Stateless & When to use Stateful

Keep in mind, to improve performance, you always want to rely on as few Stateful widgets as possible.
But There is essentially two reasons to choose a Stateful Widget over a Stateless one:
1\. The Widget needs to hold any kind of data that has to change during its lifetime.
2\. The Widget needs to dispose of anything or cleanup after it self at the end of it’s lifetime.

### Inherited Widgets

I will not go in detail on Inherited Widgets [(Flutter Dev Team 2018b)](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html) here. When using the BLoC library [(Angelov and Contributors 2019)](https://felangel.github.io/bloc/#/), which I will teach you in the chatper [Architecting a Flutter-App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App), you will most likely never create an Inherited Widgets yourself. But in short: They are a way to expose data from the top if the Widget Tree to all their descendance. And they are used as the underlying technologie of the BLoC library.

# 140 Asynchronous Flutter

# 150 Communication with the Web

## Introduction

In this chapter I will briefly show you how to communicate with the Web in Flutter [(Flutter Dev Team 2018e)](https://flutter.dev/). I think most large scale application are dependant on the Web in one way or another, so it felt important to cover this topic.

## The HTTP Package

Communicating with the Web is very easy in Flutter. The Dart Team maintains an external package called *http* [(Dart Team 2019c)](https://pub.dev/packages/http) which takes care of most of the work for you. Dart [(Dart Team 2019a)](https://dart.dev/) also offers very good integration of asynchrones programming [(Dart Team 2018)](https://dart.dev/codelabs/async-await), which I covered in the [last chapter](https://github.com/Fasust/flutter-guide/wiki/140-Asynchronous-Flutter). Let’s look at an example, this is a simplified version is Wisgens Api Repository. It can make a request the AdviceSlip API [(Kiss 2019)](https://api.adviceslip.com/) to fetch some new advice texts.

``` dart
import 'package:http/http.dart' as http;

class Api implements Repository<Wisdom> {
  static const _adviceURI = 'https://api.adviceslip.com/advice/search/%20';

  Future<List<Wisdom>> loadData() async {
    http.Response response = await http.get(_adviceURI); //API Request

    AdviceSlips adviceSlips = AdviceSlips.fromJson(json.decode(response.body));

    List<Wisdom> wisdoms = new List();
    adviceSlips.slips.forEach((slip) {
      wisdoms.add(slip.toWisdom());
    });

    return wisdoms;
  }
}
```

*Codesnippt 11: Wisgen API Repository [(Faust 2019)](https://github.com/Fasust/wisgen)*

As you can see, you simply call *get()* on the HTTP module and give it the URL it should request. This is an asynchronous call, so you can use the *await* keyword to wait till the request is complete. Once the request is finished, you can read out headers and the body from the http.Response object.

The AdviceSlips class, is generated with a JSON to Dart converter [(Lecuona 2019)](https://javiercbk.github.io/json_to_dart/). The generated class has a fromJson function that makes it easy to populate it’s data fields with the JSON response. This is the generated class, you don’t need to read it all, I just want to give you an idea of how it looks like:

``` dart
class AdviceSlips {
  String totalResults;
  String query;
  List<Slips> slips;

  AdviceSlips({this.totalResults, this.query, this.slips});

  AdviceSlips.fromJson(Map<String, dynamic> json) {
    totalResults = json['total_results'];
    query = json['query'];
    if (json['slips'] != null) {
      slips = new List<Slips>();
      json['slips'].forEach((v) {
        slips.add(new Slips.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['total_results'] = this.totalResults;
    data['query'] = this.query;
    if (this.slips != null) {
      data['slips'] = this.slips.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Slips {
  String advice;
  String slipId;

  Slips({this.advice, this.slipId});

  Slips.fromJson(Map<String, dynamic> json) {
    advice = json['advice'];
    slipId = json['slip_id'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['advice'] = this.advice;
    data['slip_id'] = this.slipId;
    return data;
  }

  //I wrote this function myself to make it easy to cast 
  //slips into my own Wisdom data structure.
  Wisdom toWisdom() {
    return new Wisdom(
      id: int.parse(slipId),
      text: advice,
      type: "Advice Slip",
    );
  }
}
```

*Codesnippt 12: [Wisgen AdviceSlips Class (Faust 2019)](https://github.com/Fasust/wisgen)*

# 200 Architecting a Flutter App

# 300 Testing

# 400 Conventions

# 500 Conclusion

# 600 References

<div id="refs" class="references">

<div id="ref-adinugrohoReviewMultiplatformMobile2015">

Adinugroho, Timothy Yudi, Reina, and Josef Bernadi Gautama. 2015. “Review of Multi-Platform Mobile Application Development Using WebView: Learning Management System on Mobile Platform.” *Procedia Computer Science*, International Conference on Computer Science and Computational Intelligence (ICCSCI 2015), 59 (January): 291–97. <https://doi.org/10.1016/j.procs.2015.07.568>.

</div>

<div id="ref-ambuludiCapgeminiAngularGuide2019">

Ambuludi, Juan Andrés, Santos Jiménez Linares, and Contributors. 2019. “Capgemini Angular Guide.” Guide. GitHub. 2019. <https://github.com/devonfw/devon4ng>.

</div>

<div id="ref-angelovBlocLibraryDart2019">

Angelov, Felix, and Contributors. 2019. “Bloc Library for Dart.” Computer software Library Documentation. 2019. <https://felangel.github.io/bloc/#/>.

</div>

<div id="ref-appleIOSSDK2010">

Apple. 2010. *iOS SDK* (version 13). Apple. <https://developer.apple.com/ios/>.

</div>

<div id="ref-bezerraDeclarativeProgramming2018">

Bezerra, Josimar, dir. 2018. *Declarative Programming*. Fun Fun Function. <https://www.youtube.com/watch?v=yGh0bjzj4IQ&t=632s>.

</div>

<div id="ref-boelensWidgetStateBuildContext2018">

Boelens, Didier. n.d. “Widget — State — BuildContext — InheritedWidget.” Blog. Medium. Accessed September 23, 2019. <https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956>.

</div>

<div id="ref-capgeminiCapgeminiHomePage2019">

Capgemini. 2019. “Capgemini - Home Page.” Home Page. 2019. <https://www.capgemini.com/us-en/>.

</div>

<div id="ref-computerphileHTMLProgrammingLanguage2016">

Computerphile, dir. 2016. *HTML IS a Programming Language (Imperative Vs Declarative)*. University of Nottingham. <https://www.youtube.com/watch?v=4A2mWqLUpzw>.

</div>

<div id="ref-dartteamAsynchronousProgrammingDart2018">

Dart Team. 2018. “Asynchronous Programming in Dart.” Documentation. 2018. <https://dart.dev/codelabs/async-await>.

</div>

<div id="ref-dartteamDartProgrammingLanguage2019">

———. 2019a. “Dart Programming Language.” Documentation. 2019. <https://dart.dev/>.

</div>

<div id="ref-dartteamDartStreams2019">

———. 2019b. “Dart Streams.” Documentation. 2019. <https://dart.dev/tutorials/language/streams>.

</div>

<div id="ref-dartteamHttpDartPackage2019">

———. 2019c. “Http | Dart Package.” Dart Packages. 2019. <https://pub.dev/packages/http>.

</div>

<div id="ref-ecmaJavaScriptECMAStandard1997">

ECMA. 1997. *JavaScript ECMA Standard* (version 10). ECMA. <https://www.ecma-international.org/publications/standards/Ecma-262.htm>.

</div>

<div id="ref-facebookReactNativeFramework2015">

Facebook. 2015. *React Native Framework*. Facebook. <https://facebook.github.io/react-native/>.

</div>

<div id="ref-faustWisgen2019">

Faust, Sebastian. 2019. *Wisgen*. Germany. <https://github.com/Fasust/wisgen>.

</div>

<div id="ref-flutterdevteamBuildContextClass2018">

Flutter Dev Team. 2018a. “BuildContext Class.” Documentation. 2018. <https://api.flutter.dev/flutter/widgets/BuildContext-class.html>.

</div>

<div id="ref-flutterdevteamInheritedWidgetClass2018">

———. 2018b. “InheritedWidget Class.” Documentation. 2018. <https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html>.

</div>

<div id="ref-flutterdevteamStatefulWidgetClass2018">

———. 2018c. “StatefulWidget Class.” Documentation. 2018. <https://api.flutter.dev/flutter/widgets/StatefulWidget-class.html>.

</div>

<div id="ref-flutterdevteamStatelessWidgetClass2018">

———. 2018d. “StatelessWidget Class.” Documentation. 2018. <https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html>.

</div>

<div id="ref-flutterdevteamFlutterFramework2018">

———. 2018e. *The Flutter Framework* (version 1.9). Google LLC. <https://flutter.dev/>.

</div>

<div id="ref-flutterdevteamWriteYourFirst2018">

———. 2018f. “Write Your First Flutter App.” Guide. 2018. <https://flutter.dev/docs/get-started/codelab>.

</div>

<div id="ref-flutterdevteamFAQFlutter2019">

———. 2019a. “FAQ - Flutter.” FAQ. 2019. <https://flutter.dev/docs/resources/faq>.

</div>

<div id="ref-flutterdevteamFlutterState2019">

———. 2019b. “Flutter State.” Documentation. 2019. <https://flutter.dev/docs/development/data-and-backend/state-mgmt>.

</div>

<div id="ref-flutterdevteamFlutterWidgets2019">

———. 2019c. “Flutter Widgets.” Documentation. 2019. <https://flutter.dev/docs/development/ui/widgets-intro>.

</div>

<div id="ref-flutterdevteamHotReloadFlutter2019">

———. 2019d. “Hot Reload - Flutter.” Documentation. 2019. <https://flutter.dev/docs/development/tools/hot-reload>.

</div>

<div id="ref-flutterdevteamIntroductionDeclarativeUI2019">

———. 2019e. “Introduction to Declarative UI.” Documentation. 2019. <https://flutter.dev/docs/get-started/flutter-for/declarative>.

</div>

<div id="ref-googlellcAndroidSDK2008">

Google LLC. 2008. *Android SDK* (version 10). Google LLC. <https://developer.android.com/>.

</div>

<div id="ref-googlellcHowStatefulWidgets2018">

———, dir. 2018. *How Stateful Widgets Are Used Best*. Vol. Ep. 2. Flutter Widgets 101. <https://www.youtube.com/watch?v=AqCMFXEmf3w>.

</div>

<div id="ref-googlellcHowFlutterDifferent2019">

———, dir. 2019. *How Is Flutter Different for App Development*. Google Developers Official Youtube Channel. <https://www.youtube.com/watch?v=l-YO9CmaSUM&feature=youtu.be>.

</div>

<div id="ref-jetbrainsKotlinSDK2017">

Jet Brains. 2017. *Kotlin SDK* (version 1.3). Jet Brains. <https://kotlinlang.org/>.

</div>

<div id="ref-kissAdviceSlipAPI2019">

Kiss, Tom. 2019. “Advice Slip API.” Documentation. 2019. <https://api.adviceslip.com/>.

</div>

<div id="ref-kolPerformanceLimitationsReact2017">

Kol, Tal. 2017. “Performance Limitations of React Native and How to Overcome Them.” Conference Talk presented at the React Amsterdam, Amsterdam. <https://www.youtube.com/watch?v=psZLAHQXRsI>.

</div>

<div id="ref-lecuonaJSONDartConverter2019">

Lecuona, Javier. 2019. *JSON to Dart Converter* (version 1). Buenos Aires, Argentina. <https://javiercbk.github.io/json_to_dart/>.

</div>

<div id="ref-lelerWhatRevolutionaryFlutter2017">

Leler, Wm. 2017. “What’s Revolutionary About Flutter.” Blog. Hackernoon. 2017. <https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514>.

</div>

<div id="ref-googlellcHowCreateStateless2018">

LLC, Google, dir. 2018. *How to Create Stateless Widgets*. Vol. Ep. 1. Flutter Widgets 101. <https://www.youtube.com/watch?v=wE7khGHVkYY>.

</div>

<div id="ref-mooreDartProductiveFast2019">

Moore, Kevin, and Bob Nystrom. 2019. “Dart: Productive, Fast, Multi-Platform - Pick 3.” Conference Talk presented at the Google I/O’19, Mountain View, CA, May 9. <https://www.youtube.com/watch?v=J5DQRPRBiFI>.

</div>

<div id="ref-oracleJavaJDK1996">

Oracle. 1996. *Java JDK* (version 8). Oracle. <https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>.

</div>

<div id="ref-soaresFlutterAngularDartCode2018">

Soares, Paolo. 2018. “Flutter / AngularDart – Code Sharing, Better Together.” Conference Talk presented at the DartConf 2018, Google Campus, LA, January 25. <https://www.youtube.com/watch?v=PLHln7wHgPE>.

</div>

<div id="ref-stollPlainEnglishWhat2018">

Stoll, Scott. 2018. “In Plain English: So What the Heck Is Flutter and Why Is It a Big Deal?” Blog. Medium. 2018. <https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a>.

</div>

<div id="ref-technicaluniversitycologneTechnicalUniversityCologne2019">

Technical University Cologne. 2019. “Technical University Cologne.” Home Page. 2019. <https://www.th-koeln.de/en/homepage_26.php>.

</div>

<div id="ref-windmillStatefulWidgetLifecycle2019">

Windmill, Eric, and Contributors. 2019. “Stateful Widget Lifecycle.” Blog. Flutterbyexample. 2019. <https://flutterbyexample.com//stateful-widget-lifecycle>.

</div>

</div>
