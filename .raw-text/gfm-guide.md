# 000-Introduction

## The Goal of this Guide

This guide aims to bridge the gap between the absolute Flutter [(Flutter Dev Team 2018g)](https://flutter.dev/) basics and clean, structured Flutter development. It should bring you from the basics of knowing how to build an app with Flutter to an understanding of how to do it *properly*. Or at least show you one possible way to make large scale Flutter projects clean and manageable.

## Who is this Guide for?

For people with a basic knowledge of the Flutter Framework. I recommend following this tutorial by the Flutter team [(Flutter Dev Team 2018h)](https://flutter.dev/docs/get-started/codelab). It will walk you through developing your first flutter application. You should also have a basic understanding of the Dart programming language [(Dart Team 2019a)](https://dart.dev/). No worries, it is very similar to Java [(Oracle 1996)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), Kotlin [(Jet Brains 2017)](https://kotlinlang.org/) and JavaScript [(ECMA 1997)](https://www.ecma-international.org/publications/standards/Ecma-262.htm). So if you know 1 or 2 of those languages you should be fine.

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

# 100-The-Flutter-Framework

## Introduction

This Chapter will give you a basic understanding of how the Flutter Framework [(Flutter Dev Team 2018g)](https://flutter.dev/) works as a whole. I will showcase the difference of Flutter to other Cross-Platform approaches and how Flutter works *under the hood*. You will also be introduced to the concepts of *state* [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt) and flutters way of rendering an app as a tree of *widgets*. In addition to this, You will gain an understanding of how Flutter Handels Asynchronous Programming. And Lastly, you will learn how to communicate with the Web within the Flutter Framework.

## Contents of the Chapter

  - [Under The Hood](https://github.com/Fasust/flutter-guide/wiki/110-Under-the-Hood)
  - [Thinking Declaratively](https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively)
  - [The Widget Tree](https://github.com/Fasust/flutter-guide/wiki/130-The-Widget-Tree)
  - [Asynchronous Flutter](https://github.com/Fasust/flutter-guide/wiki/140-Asynchronous-Flutter)

# 110-Under-The-Hood

## Introduction

Flutter [(Flutter Dev Team 2018g)](https://flutter.dev/) is a framework for cross-platform native development. But what exactly does that mean? It means that it promises Native App performance while still compiling apps for multiple platforms from a single codebase. The best way to understand how flutter achieves this, is to compare it to other mobile development approaches.

### Full Native Approach

![Native app rendering](https://github.com/Fasust/flutter-guide/wiki//images/native-rendering.png)

*Figure 1: Native app rendering [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

The classic way to build a mobile app, would be to write native code for each platform you want to support. I.E. One for IOS [(Apple 2010)](https://developer.apple.com/ios/), one for Android [(Google LLC 2008)](https://developer.android.com/) and so on. In this approach your app will be written in a platform specific language and render through platform specific widgets and a platform specific engine. During the development you have direct access to platform specific services and sensors (Google LLC 2019a; Stoll 2018; Leler 2017). But you will have to build the same app multiple times, which effectively doubles your workload.

### Embedded WebApp Approach

![Embedded Web App rendering](https://github.com/Fasust/flutter-guide/wiki//images/webview-rendering.png)

*Figure 2: Embedded WebApp rendering [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Embedded WebApps where the first approach to cross-platform development. You would simply build your application with HTML, CSS and JavaScript and then have it render through a native WebView(Google LLC 2019a; Leler 2017). The Problem here is, that developers are limited to the web technology stack and that communication between the app and native services would always have to run through a *bridge* [(Stoll 2018)](https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a).

#### Bridges

Bridges connect components with one another. These components can be build in the same or different programming languages [(Adinugroho, Reina, and Gautama 2015)](http://www.sciencedirect.com/science/article/pii/S1877050915020979).

### Reactive View Approach

![Reactive app rendering](https://github.com/Fasust/flutter-guide/wiki//images/reactive-rendering.png)

*Figure 3: Reactive app rendering [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Apps build with reactive frameworks (like React Native [(Facebook 2015)](https://facebook.github.io/react-native/)) are mostly written in a platform independent language like JavaScript [(ECMA 1997)](https://www.ecma-international.org/publications/standards/Ecma-262.htm). The JavaScript code then sends information on how UI components should be displayed to the native environment. This communication always runs through a *bridge*. So we end up with native widgets that are controller through JavaScript. The main problem here, is that the communication through the *bridge* is a bottleneck which can lead to performance issus (Google LLC 2019a; Stoll 2018; Leler 2017; Kol 2017).

### Flutters Approach

![Flutter app rendering](https://github.com/Fasust/flutter-guide/wiki//images/flutter-rendering.png)

*Figure 4: Flutter app rendering [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Flutters approach is to move the entire rendering process into the app. The rendering runs through Flutters own engine and uses flutters own widgets. It only needs a canvas to display the rendered frames on and system events/input it can then forward to your app. The framework also provides a way to access services and sensors through platform independent interfaces. This way the *bridging* between the app and the native environment is kept to a minimum which removes that bottleneck (Google LLC 2019a; Stoll 2018; Leler 2017).

You might think that keeping an entire rendering engine inside your app would lead to huge APKs, but as of 2019 the compressed framework is only 4.3MB [(Flutter Dev Team 2019a)](https://flutter.dev/docs/resources/faq).

![Flutter Framework Architecture](https://github.com/Fasust/flutter-guide/wiki//images/flutter-architecture.png)

*Figure 5: Flutter Framework Architecture [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

| 🕐 TLDR | Flutter uses it’s own engine instead of using the native one. The native environment only renders the finished frames. |
| ------ | :--------------------------------------------------------------------------------------------------------------------- |

## Flutter Compiler

One additional advantage of Flutter, is that is comes with two different compilers. A JIT-Compiler (Just in time) and a AOT-Compiler (Ahead of Time). The following table will showcase the advantage of each:

| Compiler      | What is does                                                                                                                                                                                                                                                                        | When it’s used     |
| :------------ | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :----------------- |
| Just in Time  | Only re-compiles files that have changed. Preserves App State [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt) during rebuilds. Enables *Hot Reload* [(Flutter Dev Team 2019d)](https://flutter.dev/docs/development/tools/hot-reload). | During Development |
| Ahead of Time | Compiles all dependencies ahead of time. The output app is faster.                                                                                                                                                                                                                  | For Release        |

*Table 1: Flutters 2 Compliers (Moore and Nystrom 2019; Google LLC 2019a)*

## Hot Reload

*Hot Reload* [(Flutter Dev Team 2019d)](https://flutter.dev/docs/development/tools/hot-reload) is a feature web developers are already very familiar with. It essentially means, that your changes in the code are displayed in the running application near instantaneously. Thanks to Flutters JIT Complier, it is also able to provide this feature.

![Hot Reload](https://github.com/Fasust/flutter-guide/wiki//images/hot-reload.gif)

*Figure 6: Hot Reload [(Flutter Dev Team 2019d)](https://flutter.dev/docs/development/tools/hot-reload)*

# 120-Thinking-Declaratively

## Introduction

If you come from the native mobile world and *imperative* frameworks like IOS [(Apple 2010)](https://developer.apple.com/ios/) and Android [(Google LLC 2008)](https://developer.android.com/), developing with Flutter [(Flutter Dev Team 2018g)](https://flutter.dev/) can take while to get used to. Flutter, other then those frameworks mentioned above, is a *declarative* Framework. This section will teach you how to think about developing apps declaratively and one of the most important concepts of Flutter: *state* [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt).

## Declarative Programming vs Imperative Programming

But what exactly is the difference between *declarative* and *imperative*? I will try to explain this using a metaphor: For a second, let’s think of programming as *talking* to the underlying framework. In this context, an imperative approach is telling the framework **exactly** what you want it to do. “Imperium” (Latin) means “to command”. A declarative approach, on the other hand, would be describing to the framework what kind of result you want to get and letting the framework decide on how to achieve that result. “Declaro” (Latin) means “to explain” (Flutter Dev Team 2018g, 2019b, 2019e; Bezerra 2018). Let’s look at an example:

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

![UI = f(State)](https://github.com/Fasust/flutter-guide/wiki//images/ui-equals-function-of-state.png)

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

# 130-The-Widget-Tree

## Introduction

This section will give you a better understanding of how programming in Flutter [(Flutter Dev Team 2018g)](https://flutter.dev/) actually works. You will learn what Widgets [(Flutter Dev Team 2019c)](https://flutter.dev/docs/development/ui/widgets-intro) are, what types of Widgets Flutter has and lastly what exactly the *Widget Tree* is.

## Widgets in General

One sentence you can simply not avoid when researching Flutter is:

> “In Flutter, everything is a Widget.” [(Flutter Dev Team 2019c)](https://flutter.dev/docs/development/ui/widgets-intro)

But that is not really helpful, is it? Personally, I like Didier Boelens definition of Flutter Widgets better:

> “Think of a Widget as a visual component (or a component that interacts with the visual aspect of an application).” [(Boelens 2018b)](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956)

Let’s have look at an example, this app displays an endless feed of Wisdoms combined with vaguely thought provoking stock images:

![Wisgen Widgets](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-widgets.png)

*Figure 8: Wisgen Widgets [(Faust 2019)](https://github.com/Fasust/wisgen)*

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

The build method of any given Widget can be called multiple times a second. And how often it is called exactly is never under your control, it is controlled by the Flutter Framework [(Flutter Dev Team 2018e)](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html). To make this rapid rebuilding of Widgets efficient, Flutter forces us developers to keep the build methods light weight by making all Widgets immutable. This means that all variables in a Widget have to be declared as *final*. Which means they are initialized once and can not change over time.
But your app never consists out of exclusively immutable parts, does it? Variables need to change, data needs to be fetched and stored. Almost any app needs some sort of mutable data. As mentioned in the [previous chapter](https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively), in Flutter such data is called *state* [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt). No worries, how Flutter handles mutable state will be covered in the section [Stateful Widgets](#stateful-widgets) down below, so just keep on reading.

### The Widget Tree

When working with Flutter, you will inevitably stumble over the term *Widget Tree*, but what exactly does it mean? A UI in flutter is nothing more then a tree of nested Widgets. Let’s have a look at the Widget Tree for our example from Figure 8. Note the card Widgets on the right hand side of the diagram. You can see how the code from Codesnippt 5 translates to Widgets in the Widget Tree.

![Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-widget-tree.PNG)

*Figure 9: Wisgen Widget Tree [(Faust 2019)](https://github.com/Fasust/wisgen)*

### Buildcontext

If you have previously build an App with Flutter, you have definitely encountered *BuildContext* [(Flutter Dev Team 2018a)](https://api.flutter.dev/flutter/widgets/BuildContext-class.html). It is passed in as a variable in every Widget build methode in Flutter. But what exactly is *BuildContext*? As Didier Boelens puts it:

> “A BuildContext is nothing else but a reference to the location of a Widget within the tree structure of all the Widgets which are built.” [(Boelens 2018b)](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956)

The BuildContext contain information about each *ancestor* leading down to the Widget that the context belongs to. So it is an easy way for a Widget to access all its ancestors in the Widget tree. Accessing a Widgets *descendance* through the BuildContext is possible, but not advised and inefficient. So in short: For a Widget at the bottom of the tree, it is very easy to get information from Widgets at the top of the tree but **not** visversa [(Boelens 2018b)](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956). For example, the image Widget from Figure 9 could access it’s ancestor card Widget like this:

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

This is the most basic of the three an likely the one you’ll use the most when developing an app with Flutter. Stateless Widgets [(Flutter Dev Team 2018e)](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html) are initialized once with a set of parameters and those parameters will never change from there on out. Let’s have a look at an example. This is the class of the card Widget from figure 8:

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

As you can see, it has some const values for styling, a wisdom object that is passed into the constructor and a build methode. The wsidom object contains the wisdom text and the hyperlink for the stock image.

One thing I want to point out here is that even if all fields are final in a StatelessWidget, it can still change to a degree. A ListView Widget is also a Stateless for example. It has a final reference to a list. Things can be added or removed from that list without the reference in the ListView Widget changing. So the ListView remains immutable and Stateless while the things it displays are able to change [(Google LLC 2018b)](https://www.youtube.com/watch?v=wE7khGHVkYY).

The Lifecycle of Stateless Widgets is very straight forward [(Boelens 2018b)](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956):

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

A Stateful Widget [(Flutter Dev Team 2018d)](https://api.flutter.dev/flutter/widgets/StatefulWidget-class.html) always consist of two parts: An immutable Widget and a mutable state. The immutable Widgets responsibility is to hold onto that state, the state itself has the mutable data and builds the actual Widget [(Google LLC 2018a)](https://www.youtube.com/watch?v=AqCMFXEmf3w). Let’s have a look at an example. This is a simplified version of the WisdomFeed from Figure 8. The *WisdomBloc* is responsible for generating and cashing wisdoms that are then displayed in the Feed. More on that in the chapter [Architecting a Flutter App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App).

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

If you are anything like me, you will ask yourself: “why is this split into 2 parts? The StatefulWidget is not really doing anything.” Well, The Flutter Team wants to keep Widgets **always** immutable. The only way to keep this statement universally true, is to have the StatefulWidget hold onto the State but not actually be the State (Google LLC 2018a; Windmill and Contributors 2019).

State objects have a long lifespan in Flutter. This means that they will stick around during rebuilds or even if the widget that they are linked to gets replaced [(Google LLC 2018a)](https://www.youtube.com/watch?v=AqCMFXEmf3w). So in this example, no matter how often the WisdomFeed gets rebuild and no matter if the user switches pages, the cashed list of wisdoms (WisdomBloc) will stay the same until the app is shut down.

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

1.  The Widget needs to hold any kind of data that has to change during its lifetime.
2.  The Widget needs to dispose of anything or cleanup after it self at the end of it’s lifetime.

### Inherited Widgets

I will not go in detail on Inherited Widgets [(Flutter Dev Team 2018b)](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html) here. When using the BLoC library [(Angelov and Contributors 2019)](https://felangel.github.io/bloc/#/), which I will teach you in the chatper [Architecting a Flutter-App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App), you will most likely never create an Inherited Widgets yourself. But in short: They are a way to expose data from the top if the Widget Tree to all their descendance. And they are used as the underlying technologie of the BLoC library.

# 140-Asynchronous-Flutter

## Introduction

Asynchronous Programming is an essential part of any modern application. There will always be network calls, user input or any number of other unpredictable things that your app has to wait for. Luckily Dart [(Dart Team 2019a)](https://dart.dev/) and Flutter [(Flutter Dev Team 2018g)](https://flutter.dev/) have a very good integration of Asynchronous Programming. This chapter will teach you the basics of Futures, async/await [(Dart Team 2019a)](https://dart.dev/) and Streams [(Dart Team 2019b)](https://dart.dev/tutorials/language/streams). Throughout this chapter I will be using the *http* package [(Dart Team 2019c)](https://pub.dev/packages/http) to make network requests. Communication with the web is one of the most common usecases for Asynchronous Programming, so I though it would only be fitting.

## Futures

Futures [(Dart Team 2019a)](https://dart.dev/) are the most basic way of dealing with asynchronous code in Flutter. If you have ever worked with JavaScripts [(ECMA 1997)](https://www.ecma-international.org/publications/standards/Ecma-262.htm) Promises before, they are basically the exact same thing. Here is a small example: This is a simplified version is Wisgens Api Repository. It can make requests to the AdviceSlip API [(Kiss 2019)](https://api.adviceslip.com/) to fetch some new advice texts.

``` dart
class Api {
  //Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() {
    //Define the Future and what the result will look like
    Future<http.Response> apiCall = http.get(_adviceURI); 

    //Define what will happen once it's resolved
    return apiCall.then((response) => Wisdom.fromResponse(response)); 
  }
}
```

*Codesnippt 11: Wisgen API Repository (Futures) [(Faust 2019)](https://github.com/Fasust/wisgen)*

As you can see, you simply call *get()* on the HTTP module and give it the URL it should request. The get() methode returns a Future. A Future object is a reference to an event that will take place at some point in the *future*. We can give it a callback function with *then()*, that will execute once that event is resolved. The callback we define will get access to the result of the Future IE it’s type: `Future<Type>`. So here, the Future object *“apiCall”* is a reference to when the API call will be resolved. Once the call is complete, *then()* will be called and we get access to the *http.Response*. We tell the future to transform the Response into a wisdom object and return the result, by adding this instruction as a callback to *then()* (Google LLC 2019c, 2019b). We can also handle errors with the *catchError()* function:

``` dart
class Api {
  //Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() {
    Future<http.Response> apiCall = http.get(_adviceURI);
    return apiCall
      .then((response) => Wisdom.fromResponse(response))
      .catchError((exception) => Wisdom.Empty);
  }
}
```

*Codesnippt 12: Wisgen API Repository (Futures with Error) [(Faust 2019)](https://github.com/Fasust/wisgen)*

### Async & Await

If you have ever worked with Promises or Futures before, you know that this can get really ugly really quickly: callbacks nested in callbacks nested in callbacks. Luckily Dart supports the *async & await* keywords [(Dart Team 2018)](https://dart.dev/codelabs/async-await), which give us the ability to structure our asynchrones code the same way we would if it was synchronous. Let’s take the same example as in
Snippet 11:

``` dart
class Api {
  //Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() async {
    http.Response response = await http.get(_adviceURI);
    return Wisdom.fromResponse(response);
  }
}
```

*Codesnippt 13: Wisgen API Repository (Async) [(Faust 2019)](https://github.com/Fasust/wisgen)*

We can use the *await* keyword to tell Flutter to wait at on specific point until a Future is resolved. In this example Flutter waits until the *http.Response* has arrived and then proceeds to transform it into a Wisdom. If we want to use the await keyword in a function, we have to mark the function as *async*. This forces the return type to be a Future. This makes sense, because if we wait during the function, the function will never return instantly, thus it **has** to return a Future [(Google LLC 2019e)](https://www.youtube.com/watch?v=SmTCmDMi4BY). Error handling in async function can be done with try / catch:

``` dart
class Api {
  //Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() async {
    try {
      http.Response response = await http.get(_adviceURI);
      return Wisdom.fromResponse(response);
    } catch (exception) {
      return Wisdom.Empty;
    }
  }
}
```

*Codesnippt 14: Wisgen API Repository (Async with Error) [(Faust 2019)](https://github.com/Fasust/wisgen)*

## Streams

Streams [(Dart Team 2019b)](https://dart.dev/tutorials/language/streams) are one of the core technologies behind reactive programming [(Boelens 2018a)](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/). And we’ll use them heavily in the chapter [Architecting a Flutter app](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App). But what exactly are *streams*? As Andrew Brogdon put’s it in one of Googles official Dart tutorials, Streams are to Future what Iterables are to synchronous data types [(Google LLC 2019d)](https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17&t=345s). You can think of streams as one continuos flow of data. Data can be put into the stream, other parties can subscribe/listen to a given stream and be notified once a new peace of data enters the stream.

![Data Stream](https://github.com/Fasust/flutter-guide/wiki//images/stream.PNG)

*Figure 10: Data Stream*

Okay, but how does it look in Dart code? Fist we initialize a SteamBuilder [(Dart Team 2019b)](https://dart.dev/tutorials/language/streams) to generate a new stream. The StreamBuilder gives us access to a *sink*, that we can use to put data into the stream and the actual *stream*, which we can use to read data from the stream:

``` dart
main(List<String> arguments) {
  StreamController<int> _controller = StreamController();
  for(int i = 0; i < 5 ; i++){
    _controller.sink.add(i);
  }

  _controller.stream.listen((i) => print(i));

  _controller.close(); //don't forget to close the stream once you are done
}
```

*Codesnippt 15: Stream of Ints*

``` bash
0
1
2
3
4
```

*Codesnippt 16: Stream of Ints Output*

Important Siedenote:

| ⚠ | Streams are single subscription by default. So if you want multiple subscribers you need to add `StreamController streamController = new StreamController.broadcast();` |
| - | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

Let’s have a look at a more complex example: In Wisgen, our wisdoms are delivered to the Interface via a stream. When ever we run out of wisdoms to display, a request is send to a class that fetches new wisdoms form our API [(Kiss 2019)](https://api.adviceslip.com/) and publishes them in a stream. Once those new wisdoms come in, the UI gets notified and receives them. This approach is called *BLoC Pattern* [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE) and I will explain it’s details in the chapter [Architecting a Flutter app](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App). For now, this is a simplified version of how that could look like:

``` dart
class WisdomBloc {
  final Api _api = new Api();
  List<Wisdom> _oldWisdoms = new List();

  //Stream
  final StreamController _streamController = StreamController<List<Wisdom>>; 
  StreamSink<List<Wisdom>> get _wisdomSink => _streamController.sink; //Data In
  Stream<List<Wisdom>> get wisdomStream => _streamController.stream; //Data out

  ///Called from UI to tell the BLoC to put more data into the stream
  publishMoreWisdom() async {
    List<Wisdom> fetchedWisdoms = await _api.fetch(20);

    //Appending the new Wisdoms to the current state
    List<Wisdom> newWisdoms = _oldWisdoms + fetchedWisdoms;

    _wisdomSink.add(newWisdoms); //publish to stream
    _oldWisdoms = newWisdoms;
  }

  ///Called when UI is disposed
  dispose() {
    _streamController.close();
  }
}
```

*Codesnippt 17: Simplified Wisgen WisdomBLoC [(Faust 2019)](https://github.com/Fasust/wisgen)*

We create a stream builder in the beginning and expose the stream itself to enable the UI to subscribe to it. We also open up a private sink, so we can easily add thinks to the stream. When ever the *publishMoreWisdom()* function is called, the BLoC request more wisdom from the API, waits until they are fetched and then publishes them to the stream. Let’s look at the UI side of thing. This is a simplified version of the WisdomFeed in Wisgen:

``` dart
class WisdomFeedState extends State<WisdomFeed> {

  WisdomBloc _wisdomBloc;

  //We Tell the WisdomBLoC to fetch more data based on how far we have scrolled down
  //the list. That is why we need this Controller
  final _scrollController = ScrollController();
  static const _scrollThreshold = 200.0;

  @override
  void initState() {
    _wisdomBloc = new WisdomBloc();    
    _wisdomBloc.publishMoreWisdom(); //Dispatch Initial Events

    _scrollController.addListener(_onScroll);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: StreamBuilder(
        stream: _wisdomBloc.wisdomStream,
        builder: (context, AsyncSnapshot<List<Wisdom>> snapshot) {
          //show Error message
          if (snapshot.hasError) return _error(); 
          //loading animation
          if (snapshot.connectionState == ConnectionState.waiting) return _loading(context); 
          //create listView of wisdoms
          else return _listView(context, snapshot.data); 
        },
      ),
    );
  }

  Widget _listView(BuildContext context, List<Wisdom> wisdoms) {
    return ListView.builder(
      itemBuilder: (BuildContext context, int index) {
        return index >= wisdoms.length
            ? LoadingCard()
            : WisdomCard(wisdom: wisdoms[index]);
      },
      itemCount: wisdoms.length + 1,
      controller: _scrollController,
    );
  }

  @override
  void dispose() {
    _wisdomBloc.dispose();
    _scrollController.dispose();
    super.dispose();
  }

  ///Dispatching fetch events to the BLoC when we reach the end of the List
  void _onScroll() {
    final maxScroll = _scrollController.position.maxScrollExtent;
    final currentScroll = _scrollController.position.pixels;
    if (maxScroll - currentScroll <= _scrollThreshold) {
      _wisdomBloc.publishMoreWisdom();
    }
  }
  ...
}
```

*Codesnippt 18: Simplified Wisgen WisdomFeed with StreamBuilder [(Faust 2019)](https://github.com/Fasust/wisgen)*

Alright, let’s go through this step by step. First we initialize our WisdomBloc in the *initSate()* methode. This is also where we set up a ScrollController [(Flutter Dev Team 2018c)](https://api.flutter.dev/flutter/widgets/ScrollController-class.html) that we can use to determine how far down the list we have scrolled. I wont go into the details here, but the controller enables us to call *publishMoreWisdom()* on the WisdomBloc when ever we are near the end ouf our list. This way we get infinite scrolling. In the *build()* methode, we use Flutters StreamBuilder [(Flutter Dev Team 2018f)](https://api.flutter.dev/flutter/widgets/StreamBuilder-class.html) to link our UI to our stream. We give it our stream and it provides a builder method. This builder has a snapshot containing the current state of the stream. We can use the snapshot to determine when the UI needs to display a loading animation, an error message or the actual list. When we receive the actual list of wisdoms from our stream through the snapshot, we continue to the *listView()* methode. Here we just use the list of wisdoms to create a ListView with WisdomCards. You might have wondered why we stream a List of wisdoms and not just individual wisdoms. This ListView is the reason. If we where streaming individual Wisdoms we would need to combine them into a list here. Streaming a complete list is also recommended by the Flutter team for this usecase [(Sullivan and Hracek 2018a)](https://www.youtube.com/watch?v=fahC3ky_zW0). Finally, once the app is closed down, the *dispose()* methode is called and we dispose our stream and ScrollController.

![Streaming Wisdom from BLoC to WisdomFeed](https://github.com/Fasust/flutter-guide/wiki//images/wisdomBloc-stream.PNG)

*Figure 11: Streaming Wisdom from BLoC to WisdomFeed [(Faust 2019)](https://github.com/Fasust/wisgen)*

### Async\* & yield

Streams have a two keywords that are very similar to the *async & await* of Futures: *async\* & yield* [(Dart Team 2019b)](https://dart.dev/tutorials/language/streams). If we mark a function as async\* the return type **has** to be a stream. In an async\* function we get access to the async keyword (which we already know) and the yield keyword, which is very similar to a return, only that yield does not terminate the function, but instead adds a value to the stream. This is what an implementation of the WisdomBloc from snippet 17 could look like when using async\*:

``` dart
Stream<List<Wisdom>> streamWisdoms() async* {
  List<Wisdom> fetchedWisdoms = await _api.fetch(20);

  //Appending the new Wisdoms to the current state
  List<Wisdom> newWisdoms = _oldWisdoms + fetchedWisdoms;

  yield newWisdoms; //publish to stream
  _oldWisdoms = newWisdoms;
}
```

*Codesnippt 19: Simplified Wisgen WisdomBLoC with async\* [(Faust 2019)](https://github.com/Fasust/wisgen)*

This marks the end of my introduction to streams. It can be a challenging topic wrap your head around at first so if you still fell like you want to learn more I can highly recommend this article by Didier Boelens [(Boelens 2018a)](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/) or this 8 minute tutorial video by the Flutter Team [(Google LLC 2019d)](https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17&t)

## Side Note on Communication with the Web

I just wanted to end this chapter with showing you how the API Repository of Wisgen [(Faust 2019)](https://github.com/Fasust/wisgen) actually looks like and give some input of why it looks the way it does:

``` dart
import 'dart:convert';
import 'dart:math';

import 'package:flutter/src/widgets/framework.dart';
import 'package:wisgen/models/advice_slips.dart';
import 'package:wisgen/models/wisdom.dart';
import 'package:wisgen/repositories/repository.dart';
import 'package:http/http.dart' as http;

///Repository that cashes data it fetches from an API and
///then Provides a given amount of random entries.
class Api implements Repository<Wisdom> {
  ///Advice SLip API Query that requests all (~213) Text Entries from the API.
  ///We fetch all entries at once and cash them locally to minimize network traffic.
  ///The Advice Slip API also does not provide the option to request a 
  ///selected amount of entries.
  ///That's why I think this is the best approach.
  static const _adviceURI = 'https://api.adviceslip.com/advice/search/%20';

  List<Wisdom> _cash;
  final Random _random = new Random();

  @override
  Future<List<Wisdom>> fetch(int amount) async {
    //if the Cash is empty, request data from the API
    if (_cash == null) _cash = await _loadData();

    //return requested amount of random Wisdoms
    List<Wisdom> res = new List();
    for (int i = 0; i < amount; i++) {
      res.add(_cash[_random.nextInt(_cash.length)]);
    }
    return res;
  }

  ///I changed this function for the Snippets in the Guide
  ///Fetches Data from API and coverts it to Wisdoms
  Future<List<Wisdom>> _loadData() async {
    http.Response response = await http.get(_adviceURI);
    AdviceSlips adviceSlips = AdviceSlips.fromJson(json.decode(response.body));

    List<Wisdom> wisdoms = new List();
    adviceSlips.slips.forEach((slip) {
      wisdoms.add(slip.toWisdom());
    });

    return wisdoms;
  }
}
```

*Codesnippt 20: Actual Wisgen API Repository [(Faust 2019)](https://github.com/Fasust/wisgen)*

The AdviceSlips class, is generated with a JSON to Dart converter [(Lecuona 2019)](https://javiercbk.github.io/json_to_dart/). The generated class has a fromJson function that makes it easy to populate it’s data fields with the JSON response. I used this class instead of implementing a method in the Wisdom class, because I did not want a direct dependency from my entity class to the AdviceSlip JSON structure. This is the generated class, you don’t need to read it all, I just want to give you an idea of how it looks like:

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

*Codesnippt 21: Wisgen AdviceSlips Class [(Faust 2019)](https://github.com/Fasust/wisgen)*

# 200-Architecting-a-Flutter-App

## Introduction

The Most central topic of architecting a Flutter [(Flutter Dev Team 2018g)](https://flutter.dev/) app is *Statemanagement* [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt). **Where** does my State sit, **who** need access to it and **how** do parts of the app access it? This chapter aims to answer those questions. You will learn about the two types of state, you will be introduced to the most three most popular statemanagement solutions and you will learn one of those statemanagement solutions (BLoC [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE)) in detail. You will also learn how to use the BLoC statemanagement solution in a clean and scalable 3-Layered architecture.

## Statemanagement vs Architecture

I want to differentiate these two terms. Within the Flutter community *Statemanagement* and *Architecture* are often used synonymously, but I think we should be careful to do so. Statemanagement is a set of tools or a pattern with which we can manage the State within our app. Architecture on the other hand, is the over arching structure of our app. A set of rules that our app conforms to. Any architecture for a Flutter application will have some sort of statemanagement, but statemanagement is not an architecture by it self. I just want you to keep this in mind for the following chapters.

## Types of State

The Flutter documentaion [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt) differentiates between two types of State: *Ephemeral State* & *App State*.
Ephemeral State is State that is only required in one location IE inside of one Widget. Examples would be: scroll position in a list, highlighting of selected elements or the color change of a pressed button. This is the type of state that we don’t need to worry about that much or in other word, there is no need for a fancy Statemanagement solution for Ephemeral State. We can simply use a Stateful Widget with some variables and manage Ephemeral State that way [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt). The more interesting type of State is App State. This is information that is required in multiple locations / by multiple Widgets. Examples would be: User data, a list of favorites or a shopping car. App State management is going to be the focus of this chapter.

![Ephemeral State vs App State Dession Tree](https://github.com/Fasust/flutter-guide/wiki//images/ephemeral-vs-app-state.png)

*Figure XXX: Ephemeral State vs App State Dession Tree [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt)*

## Contents of this Chapter

  - [Statemanagement Solutions](https://github.com/Fasust/flutter-guide/wiki/210-Statemanagement-Solutions)
  - [BLoC](https://github.com/Fasust/flutter-guide/wiki/220-BLoC)
  - [BLoC in Practice](https://github.com/Fasust/flutter-guide/wiki/230-BLoC-In-Practice)

# 210-Statemanagement-Solutions

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

# 220-BLoC

## Introduction

  - UI only publishes and subscribes
  - NO BL in the UI
  - Keep it stupid so you don’t need to test it
  - All BL should be in BLoC
      - Buisnees Logic Objecs

![Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/bloc-architecture.png)

*Figure XXX: Bloc Architecture [(Sullivan and Hracek 2018b)](https://www.youtube.com/watch?v=RS36gBEp8OI)*

  - **4 Rules for BLoCs**
      - Only Sinks In & Streams out
      - Dependencies Injectable
      - No Platform Branching
      - Implementation can be whatever you want
  - **4 Rules for UI Classes**
      - “Complex Enough” views have a BLoC
      - Components do not format the inputs they send to the BLoC
      - Output are formated as little as possible
      - If you do have Platform Branching, It should be dependent on a single BLoC bool output

![Bloc Sink and Stream](https://github.com/Fasust/flutter-guide/wiki//images/bloc-sink-stream.png)

*Figure XXX: Bloc Sink and Stream [(Boelens 2018a)](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/)*

  - **Build Interface code how you want it to look like -\> then make it work**

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

# 230-BLoC-In-Practice

## Introduction

## BLoC in Wisgen

![Wisgen Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dependencies.png)

*Figure XXX: Wisgen Bloc Architecture [(Faust 2019)](https://github.com/Fasust/wisgen)*

![Wisgen Bloc Architecture Dataflow](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dataflow.png)

*Figure XXX: Wisgen Bloc Architecture Dataflow [(Faust 2019)](https://github.com/Fasust/wisgen)*

# 300-Testing

## Unit Tests in Dart using the BLoC Pattern

# 400-Conventions

## Mastering the Widget Tree

## Naming conventions

## File structure with BLoC

# 500-Conclusion

## My Opinion of Flutter

## Should you use it?

# 600-References

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

<div id="ref-boelensFlutterReactiveProgramming2018">

Boelens, Didier. 2018a. “Flutter - Reactive Programming - Streams - BLoC.” Guide. Didier Boelens. 2018. <https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/>.

</div>

<div id="ref-boelensWidgetStateBuildContext2018">

———. 2018b. “Widget — State — BuildContext — InheritedWidget.” Blog. Medium. 2018. <https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956>.

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

<div id="ref-flutterdevteamScrollControllerClass2018">

———. 2018c. “ScrollController Class.” Documentation. 2018. <https://api.flutter.dev/flutter/widgets/ScrollController-class.html>.

</div>

<div id="ref-flutterdevteamStatefulWidgetClass2018">

———. 2018d. “StatefulWidget Class.” Documentation. 2018. <https://api.flutter.dev/flutter/widgets/StatefulWidget-class.html>.

</div>

<div id="ref-flutterdevteamStatelessWidgetClass2018">

———. 2018e. “StatelessWidget Class.” Documentation. 2018. <https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html>.

</div>

<div id="ref-flutterdevteamStreamBuilderClass2018">

———. 2018f. “StreamBuilder Class.” Documentation. 2018. <https://api.flutter.dev/flutter/widgets/StreamBuilder-class.html>.

</div>

<div id="ref-flutterdevteamFlutterFramework2018">

———. 2018g. *The Flutter Framework* (version 1.9). Google LLC. <https://flutter.dev/>.

</div>

<div id="ref-flutterdevteamWriteYourFirst2018">

———. 2018h. “Write Your First Flutter App.” Guide. 2018. <https://flutter.dev/docs/get-started/codelab>.

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

———, dir. 2018a. *How Stateful Widgets Are Used Best*. Vol. Ep. 2. Flutter Widgets 101. <https://www.youtube.com/watch?v=AqCMFXEmf3w>.

</div>

<div id="ref-googlellcHowCreateStateless2018">

———, dir. 2018b. *How to Create Stateless Widgets*. Vol. Ep. 1. Flutter Widgets 101. <https://www.youtube.com/watch?v=wE7khGHVkYY>.

</div>

<div id="ref-googlellcHowFlutterDifferent2019">

———, dir. 2019a. *How Is Flutter Different for App Development*. Google Developers Official Youtube Channel. <https://www.youtube.com/watch?v=l-YO9CmaSUM&feature=youtu.be>.

</div>

<div id="ref-googlellcIsolatesEventLoops2019">

———, dir. 2019b. *Isolates and Event Loops*. Vol. Ep. 1. Flutter in Focus. <https://www.youtube.com/watch?v=vl_AaCgudcY>.

</div>

<div id="ref-googlellcDartFutures2019">

———, dir. 2019c. *Dart Futures*. Vol. Ep. 2. Flutter in Focus. <https://www.youtube.com/watch?v=OTS-ap9_aXc>.

</div>

<div id="ref-googlellcDartStreams2019">

———, dir. 2019d. *Dart Streams*. Vol. Ep. 3. Flutter in Focus. <https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17>.

</div>

<div id="ref-googlellcAsyncAwait2019">

———, dir. 2019e. *Async/Await*. Vol. Ep. 4. Flutter in Focus. <https://www.youtube.com/watch?v=SmTCmDMi4BY>.

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

<div id="ref-mooreDartProductiveFast2019">

Moore, Kevin, and Bob Nystrom. 2019. “Dart: Productive, Fast, Multi-Platform - Pick 3.” Conference Talk presented at the Google I/O’19, Mountain View, CA, May 9. <https://www.youtube.com/watch?v=J5DQRPRBiFI>.

</div>

<div id="ref-oracleJavaJDK1996">

Oracle. 1996. *Java JDK* (version 8). Oracle. <https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>.

</div>

<div id="ref-rousseletProviderFlutterPackage2018">

Rousselet, Remi, and Flutter Dev Team. 2018. “Provider | Flutter Package.” Documentation. Dart Packages. 2018. <https://pub.dev/packages/provider>.

</div>

<div id="ref-soaresFlutterAngularDartCode2018">

Soares, Paolo. 2018. “Flutter / AngularDart – Code Sharing, Better Together.” Conference Talk presented at the DartConf 2018, Google Campus, LA, January 25. <https://www.youtube.com/watch?v=PLHln7wHgPE>.

</div>

<div id="ref-stollPlainEnglishWhat2018">

Stoll, Scott. 2018. “In Plain English: So What the Heck Is Flutter and Why Is It a Big Deal?” Blog. Medium. 2018. <https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a>.

</div>

<div id="ref-sullivanTechnicalDebtStreams2018">

Sullivan, Matt, and Filip Hracek, dirs. 2018a. *Technical Debt and Streams/BLoC*. Vol. 4. The Boring Flutter Development Show. <https://www.youtube.com/watch?v=fahC3ky_zW0>.

</div>

<div id="ref-sullivanBuildReactiveMobile2018">

———. 2018b. “Build Reactive Mobile Apps with Flutter.” Conference Talk presented at the Google I/O ’18, Mountain View, CA, May 10. <https://www.youtube.com/watch?v=RS36gBEp8OI>.

</div>

<div id="ref-sullivanPragmaticStateManagement2019">

———. 2019. “Pragmatic State Management in Flutter.” Conference Talk presented at the Google I/O’19, Mountain View, CA, May 9. <https://www.youtube.com/watch?v=d_m5csmrf7I>.

</div>

<div id="ref-suriArchitectYourFlutter2019">

Suri, Sagar. 2019. “Architect Your Flutter Project Using BLOC Pattern.” Guide. Medium. 2019. <https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1>.

</div>

<div id="ref-technicaluniversitycologneTechnicalUniversityCologne2019">

Technical University Cologne. 2019. “Technical University Cologne.” Home Page. 2019. <https://www.th-koeln.de/en/homepage_26.php>.

</div>

<div id="ref-windmillStatefulWidgetLifecycle2019">

Windmill, Eric, and Contributors. 2019. “Stateful Widget Lifecycle.” Blog. Flutterbyexample. 2019. <https://flutterbyexample.com//stateful-widget-lifecycle>.

</div>

</div>
