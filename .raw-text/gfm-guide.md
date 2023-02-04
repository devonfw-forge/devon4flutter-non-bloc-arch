# 000-Introduction

## The Goal of this Guide

This guide aims to bridge the gap between the absolute Flutter [\[1\]](https://flutter.dev/) basics and clean, structured Flutter development. It should bring you from the basics of knowing how to build an app with Flutter to an understanding of how to do it *properly*. Or at least show you one possible way to make large scale Flutter projects clean and manageable.

## Who is this Guide for?

For people with a basic knowledge of the Flutter Framework. I recommend following this tutorial by the Flutter team [\[2\]](https://flutter.dev/docs/get-started/codelab). It will walk you through developing your first Flutter application. You should also have a basic understanding of the Dart programming language [\[3\]](https://dart.dev/). No worries, it is very similar to Java [\[4\]](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), Kotlin [\[5\]](https://kotlinlang.org/) and JavaScript [\[6\]](https://www.ecma-international.org/publications/standards/Ecma-262.htm). So if you know 1 or 2 of those languages you should be fine.

## Topics that will be Covered

  - A brief introduction to the [Flutter Framework](https://github.com/devonfw-forge/devonfw4flutter/wiki/100-The-Flutter-Framework) in general:
      - How the [underlying technology](https://github.com/devonfw-forge/devonfw4flutter/wiki/110-Under-the-Hood) works,
      - how it’s [programming style](https://github.com/devonfw-forge/devonfw4flutter/wiki/120-Thinking-Declaratively) is little different from other frameworks,
      - how Flutter apps are [structured](https://github.com/devonfw-forge/devonfw4flutter/wiki/130-The-Widget-Tree) on an abstract level and,
      - how [asynchrony](https://github.com/devonfw-forge/devonfw4flutter/wiki/140-Asynchronous-Flutter) and communication with the web can be implemented.
  - A showcase of possible [architectural styles](https://github.com/devonfw-forge/devonfw4flutter/wiki/210-State-Management-Alternatives) you can use to build your app and
      - an [in-depth guide](https://github.com/devonfw-forge/devonfw4flutter/wiki/220-BLoC) on one of those possibilities (BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE)).
  - How to [test](https://github.com/devonfw-forge/devonfw4flutter/wiki/300-Testing) your app.
  - Some [conventions and best practices](https://github.com/devonfw-forge/devonfw4flutter/wiki/400-Conventions) for Dart, and the Flutter Framework in general.
  - My personal [evaluation of the framework](https://github.com/devonfw-forge/devonfw4flutter/wiki/500-Conclusion).

## Creation Context

This guide was written by a student in the Bachelor of Science Program “Computer Science and Media Technology” at Technical University Cologne [\[8\]](https://www.th-koeln.de/en/homepage_26.php), and it was created for one of the modules in that Bachelor. In addition to this, the guide was written in collaboration with DevonFw [\[9\]](https://devonfw.com/index.html). DevonFw released a guide on building an application with Angular [\[10\]](https://github.com/devonfw/devon4ng) in May of 2019, this guide is meant to be the Flutter version of that.

## Structure

The guide is designed to be read in order, from chapter 0 (this one) to chapter 5. Code examples throughout the chapters will mainly be taken from Wisgen [\[11\]](https://github.com/Fasust/wisgen), an example Flutter application that was specifically built for the purposes of this guide. If you want to search for any specific terms in the guide, you can use [this page](https://github.com/devonfw-forge/devonfw4flutter/wiki/gfm-guide). It is all chapters of the guide combined into one page. There is going to be a few common symbols throughout the guide, this is what they stand for:

| Symbol | Meaning                  |
| :----: | :----------------------- |
|   📙    | Definition               |
|   🕐    | Shortened version (TLDR) |
|   ⚠    | Important                |

## My Sources

I am basing this guide on a combination of conference talks, blog articles by respected Flutter developers, the official documentation, scientific papers that cover cross-platform mobile development in general and many other sources. All sources used in the guide are listed in chapter [*6 References*](https://github.com/devonfw-forge/devonfw4flutter/wiki/600-References). To put that theoretical knowledge into practice, I built the Wisgen application [\[11\]](https://github.com/Fasust/wisgen) using the Flutter Framework, the BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE), and a four-layered architecture.

## Creation Process

If you are interested in how this guide was created, how Wisgen was built, how a bridge between a citation software and Markdown was realized, or any other details about the creation process, check out the [Meta-Documentation](https://github.com/devonfw-forge/devonfw4flutter/blob/master/Meta-Documentation.pdf).

# 100-The-Flutter-Framework

## Introduction

This chapter will give you a basic understanding of how the Flutter Framework [\[1\]](https://flutter.dev/) works as a whole. I will showcase the difference of Flutter to other cross-platform approaches and how Flutter works [*under the hood*](https://github.com/devonfw-forge/devonfw4flutter/wiki/110-Under-the-Hood). You will also be introduced to the concepts of [*State*](https://github.com/devonfw-forge/devonfw4flutter/wiki/120-Thinking-Declaratively) [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) and Flutter’s way of rendering an app as a [tree of *Widgets*](https://github.com/devonfw-forge/devonfw4flutter/wiki/130-The-Widget-Tree) [\[13\]](https://api.flutter.dev/flutter/widgets/StatefulWidget-class.html). Lastly, you will gain an understanding of how Flutter handles [Asynchronous Programming](https://github.com/devonfw-forge/devonfw4flutter/wiki/140-Asynchronous-Flutter) and communication with the Web.

## Contents of the Chapter

  - [Under The Hood](https://github.com/devonfw-forge/devonfw4flutter/wiki/110-Under-the-Hood)
  - [Thinking Declaratively](https://github.com/devonfw-forge/devonfw4flutter/wiki/120-Thinking-Declaratively)
  - [The Widget Tree](https://github.com/devonfw-forge/devonfw4flutter/wiki/130-The-Widget-Tree)
  - [Asynchronous Flutter](https://github.com/devonfw-forge/devonfw4flutter/wiki/140-Asynchronous-Flutter)

# 110-Under-The-Hood

## Introduction

Flutter [\[1\]](https://flutter.dev/) is a framework for cross-platform native development. That means that it promises native app performance while still compiling apps for multiple platforms from a single codebase. The best way to understand how Flutter achieves this is to compare it to other mobile development approaches. This chapter will showcase how three of the most popular cross-platform approaches function and then compare those technics to the one Flutter uses. Lastly, I will highlight some of the unique features that Flutters approach enables.

### Full Native Approach

![Native app rendering](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/native-rendering.png)

*Figure 1: Native app rendering [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

The classic way to build a mobile app would be to write native code for each platform you want to support. I.E. One for IOS [\[15\]](https://developer.apple.com/ios/), one for Android [\[16\]](https://developer.android.com/) and so on. In this approach, your app will be written in a platform-specific language and render through platform-specific Widgets and a platform-specific engine. During the development, you have direct access to platform-specific services and sensors \[14\], \[17\], \[18\]. But you will have to build the same app multiple times, which multiplies your workload by however many platforms you want to support.

### Embedded WebApp Approach

![Embedded WebApp rendering](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/webview-rendering.png)

*Figure 2: Embedded WebApp rendering [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Embedded WebApps where the first approach to cross-platform development. For this approach, you build your application with HTML [\[19\]](https://www.w3.org/html/), CSS [\[20\]](https://www.w3.org/Style/CSS/Overview.de.html), and JavaScript [\[6\]](https://www.ecma-international.org/publications/standards/Ecma-262.htm) and then render it through a native WebView \[14\], \[17\]. The problem here is, that developers are limited to the web technology stack and that communication between the app and native services always has to run through a *bridge* [\[18\]](https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a).

#### Bridges

Bridges connect components with one another. These components can be built in the same or different programming languages [\[21\]](http://www.sciencedirect.com/science/article/pii/S1877050915020979).

### Reactive View Approach

![Reactive app rendering](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/reactive-rendering.png)

*Figure 3: Reactive app rendering [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Apps build with reactive frameworks (like React Native [\[22\]](https://facebook.github.io/react-native/)) are mostly written in a platform-independent language like JavaScript [\[6\]](https://www.ecma-international.org/publications/standards/Ecma-262.htm). The JavaScript code then sends information on how UI components should be displayed to the native environment. This communication always runs through a *bridge*. So we end up with native Widgets that are controller through JavaScript. The main problem here is that the communication through the *bridge* can be a bottleneck that can lead to performance issues \[14\], \[17\], \[18\], \[23\].

### Flutter’s Approach

![Flutter app rendering](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/flutter-rendering.png)

*Figure 4: Flutter app rendering [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Flutter’s approach is to move the entire rendering process into the app. The rendering runs through Flutter’s own engine and uses Flutter’s own Widgets. Flutter only needs a canvas to display the rendered frames on. Any user inputs on the canvas or system events happening on the device are forwarded to the Flutter app. This limits the *bridging* between the app and native environment to frames and events which removes that potential bottleneck \[14\], \[17\], \[18\].

You might think that keeping an entire rendering engine inside an app would lead to huge APKs, but as of 2019, the compressed framework is only 4.3MB [\[24\]](https://flutter.dev/docs/resources/faq).

![Flutter Framework architecture](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/flutter-architecture.png)

*Figure 5: Flutter Framework architecture [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

| 🕐 | TLDR | Flutter uses its own engine instead of using the native one. The native environment only renders the finished frames. |
| - | ---- | :-------------------------------------------------------------------------------------------------------------------- |

## Flutter Compiler

One additional advantage of Flutter is that it comes with two different compilers. A JIT-Compiler (just in time) and an AOT-Compiler (ahead of time). The following table will showcase the advantage of each:

| Compiler      | What is does                                                                                                                                                                                                                                      | When it’s used     |
| :------------ | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | :----------------- |
| Just in Time  | Only re-compiles files that have changed. Preserves *App State* [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) during rebuilds. Enables *Hot Reload* [\[25\]](https://flutter.dev/docs/development/tools/hot-reload). | During Development |
| Ahead of Time | Compiles all dependencies ahead of time. The output app is faster.                                                                                                                                                                                | For Release        |

*Table 1: Flutter’s 2 Compilers \[17\], \[26\]*

## Hot Reload

*Hot Reload* [\[25\]](https://flutter.dev/docs/development/tools/hot-reload) is a feature that Web developers are already very familiar with. It essentially means that changes in the code are displayed in the running application near instantaneously. Thanks to its JIT Compiler, The Flutter Framework is also able to provide this feature.

![Hot Reload](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/hot-reload.gif)

*Figure 6: Hot Reload [\[25\]](https://flutter.dev/docs/development/tools/hot-reload)*

# 120-Thinking-Declaratively

## Introduction

If you are coming from the native mobile world and *imperative* frameworks like IOS [\[15\]](https://developer.apple.com/ios/) and Android [\[16\]](https://developer.android.com/), developing with Flutter [\[1\]](https://flutter.dev/) can take a while to get used to. Flutter, other then those frameworks mentioned above, is *declarative*. This section will teach you how to think about developing apps declaratively and one of the most important concepts of Flutter: *State* [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt).

## Declarative Programming vs Imperative Programming

I will explain the difference between *declarative* and *imperative* using a metaphor: For a second, let’s think of programming as *talking* to the underlying framework. In this context, an imperative approach is telling the framework **exactly** what you want it to do. “Imperium” (Latin) means “to command”. A declarative approach, on the other hand, would be describing to the framework what kind of result you want to get and then letting the framework decide on how to achieve that result. “Declaro” (Latin) means “to explain” \[1\], \[12\], \[27\], \[28\]. Let’s look at a code example:

``` dart
List numbers = [1,2,3,4,5];
for(int i = 0; i < numbers.length; i++){
    if(numbers[i] > 3 ) print(numbers[i]);     
}
```

*Code Snippet 1: Searching through a list (Imperative)*

Here we want to print every entry in the list that is bigger than 3. We explicitly tell the framework to go through the list one by one and check each value.
In the declarative version, we simply state how our result should look like, but not how to reach it:

``` dart
List numbers = [1,2,3,4,5];
print(numbers.where((num) => num > 3));
```

*Code Snippet 2: Searching through a list (Declarative)*

One important thing to note here is, that the difference between imperative and declarative is not black and white. One style might bleed over into the other. Prof. David Brailsford from the University of Nottingham argues that as soon as you start using libraries for your imperative projects, they become a tiny bit more declarative. This is because you are then using functions that *describe* what they do and you no longer care how they do it [\[29\]](https://www.youtube.com/watch?v=4A2mWqLUpzw).

| 🕐 | TLDR | Imperative Programming is telling the framework **exactly** what you want it to do. Declarative Programming is describing to the framework what kind of result you want to get and then letting the framework decide on how to achieve that result. |
| - | ---- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

## Declarative Programming in Flutter

Okay, now that we understand what Declarative Programming is, let’s take a look at Flutter specifically. This is a quote from Flutter’s official documentation:

> “Flutter is declarative. This means that Flutter builds its user interface to reflect the current State of your app” [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)

![UI = f(State)](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/ui-equals-function-of-state.png)

*Figure 7: UI = f(State) [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)*

In Flutter, you never imperatively or explicitly call a UI element to change it. You rather *declare* that the UI should look a certain way, given a certain *State* [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). But what exactly is *State*?

| 📙 | State | Any data that can change over time [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) |
| - | ----- | :------------------------------------------------------------------------------------------------------------ |

Typical State examples are user data, a user’s scroll position within a list, a favorite list.

Let’s have a look at a classic UI problem and how we would solve it imperatively in Android and compare it to Flutter’s declarative approach. let’s say we want to build a button that changes its color to red when it is pressed. In Android we find the button by its ID, attach a listener, and tell that listener to change the background color when the button is pressed:

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

*Code Snippet 3: Red button in Android (Imperative)*

In Flutter, on the other hand, we never call the UI element directly, we instead declare that the button background should be red or blue depending on the App-State (here the bool “pressed”). We then declare that the *onPressed()* function should update the App State and rebuild the button:

``` dart
bool pressed = false; //State

@override
Widget build(BuildContext context) {
    return FlatButton(
        color: pressed ? Colors.red : Colors.blue,
        onPressed: () {
            setState(){ // trigger rebuild of the button
                pressed = !pressed;
            } 
        }
    );
}
```

*Code Snippet 4: Red button in Flutter (Declarative)*

## Efficiency of Re-Builds

Is it not very inefficient to re-render the entire Widget every time we change the State? That was the first question I had when learning about this topic. But I was pleased to learn, that Flutter uses something called “RenderObjects” to improve performance similar to Reacts [\[22\]](https://facebook.github.io/react-native/) virtual DOM.

> “RenderObjects persist between frames and Flutter’s lightweight Widgets tell the framework to mutate the RenderObjects between States. The Flutter framework handles the rest.” [\[27\]](https://flutter.dev/docs/get-started/flutter-for/declarative)

# 130-The-Widget-Tree

## Introduction

This section will give you a better understanding of how programming in Flutter [\[1\]](https://flutter.dev/) actually works. You will learn what Widgets [\[30\]](https://flutter.dev/docs/development/ui/widgets-intro) are, what types of Widgets Flutter has and lastly what exactly the *Widget Tree* is.

## Widgets in General

One sentence that is impossible to avoid when researching Flutter is:

> “In Flutter, everything is a Widget.” [\[30\]](https://flutter.dev/docs/development/ui/widgets-intro)

But that is not really helpful, is it? Personally, I like Didier Boelens definition of Flutter Widgets better:

| 📙 | Widget | A visual component (or a component that interacts with the visual aspect of an application) [\[31\]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956) |
| - | ------ | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |

Let’s have look at an example, this is the Wisgen app [\[11\]](https://github.com/Fasust/wisgen), it displays an endless feed of wisdoms combined with vaguely thought-provoking stock images:

![Wisgen Widgets](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen-widgets.png)

*Figure 8: Wisgen Widgets [\[11\]](https://github.com/Fasust/wisgen)*

All UI-Components of the app are Widgets. From high-level stuff like the AppBar and the ListView down to to the granular things like texts and buttons (I did not highlight every Widget on the screen to keep the figure from becoming overcrowded). In code, the build method of a card Widget would look something like this:

``` dart
@override
Widget build(BuildContext context) {
  return Card(
    shape: RoundedRectangleBorder( //Declare corners to be rounded
      borderRadius: BorderRadius.circular(7),
    ),
    elevation: 2, //Declare shadow drop
    child: Column( //The child of the card are displayed in a column Widget
      children: <Widget>[
          _Image(_wisdom.imgUrl), //First child
          _Content(_wisdom), //Second Child
        ],
    ),
  );
}
```

*Code Snippet 5: Simplified Wisgen card Widget build method [\[11\]](https://github.com/Fasust/wisgen)*

The *\_Image* class generates a Widget that contains the stock image. The *\_Content* class generates a Widget that displays the wisdom text and the buttons on the card.
An important thing to note is that:

| ⚠ | Widgets in Flutter are always immutable [\[30\]](https://flutter.dev/docs/development/ui/widgets-intro) |
| - | :------------------------------------------------------------------------------------------------------ |

The build method of any given Widget can be called multiple times a second. And how often it is called exactly is never under your control, it is controlled by the Flutter Framework [\[32\]](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html). To make this rapid rebuilding of Widgets efficient, Flutter forces us developers to keep the build methods lightweight by making all Widgets immutable [\[33\]](https://flutter.dev/docs/testing/best-practices). This means that all variables in a Widget have to be declared as *final*. They are initialized once and can not change over time.
But your app never consists out of exclusively immutable parts, does it? Variables need to change, data needs to be fetched and stored. Almost any app needs some sort of mutable data. As mentioned in the [previous chapter](https://github.com/devonfw-forge/devonfw4flutter/wiki/120-Thinking-Declaratively), in Flutter such data is called *State* [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). No worries, how Flutter handles mutable State will be covered in the section [Stateful Widgets](#stateful-widgets) down below, so just keep on reading.

### The Widget Tree

When working with Flutter, you will inevitably stumble over the term *Widget Tree*, but what is a Widget Tree? A UI in Flutter is nothing more than a tree of nested Widgets. Let’s have a look at the Widget Tree of our example from figure 8. Note the card Widgets on the right-hand side of the diagram. There you can see how the code from snippet 5 translates to Widgets in the Widget Tree.

![Wisgen Widget Tree](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen-widget-tree.PNG)

*Figure 9: Wisgen Widget Tree [\[11\]](https://github.com/Fasust/wisgen)*

### Buildcontext

If you have previously built an app with Flutter, you have definitely encountered *BuildContext* [\[34\]](https://api.flutter.dev/flutter/widgets/BuildContext-class.html). It is passed in as a variable in every Widget build method in Flutter.

| 📙 | BuildContext | A reference to the location of a Widget within the tree structure of all the Widgets that have been built [\[31\]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956) |
| - | ------------ | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

The BuildContext contains information about each *ancestor* leading down to the Widget that the BuildContext belongs to. It is an easy way for a Widget to access all its ancestors in the Widget Tree. Accessing a Widgets *descendants* through the BuildContext is possible, but not advised and inefficient. So in short: It s very easy for a Widget at the bottom of the tree to get information from Widgets at the top of the tree but **not** vice-versa [\[31\]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956). For example, the image Widget from figure 9 could access its ancestor card Widget like this:

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

*Code Snippet 6: Hypothetical Wisgen image Widget [\[11\]](https://github.com/Fasust/wisgen)*

Alright, but what does that mean for me as a Flutter developer? It is important to understand how data in Flutter flows through the Widget Tree: **Downwards**. You want to place information that is required by multiple Widgets above them in the tree, so they can both easily access it through their BuildContext. Keep this in mind, for now, I will explain this in more detail in the chapter [Architecting a Flutter App](https://github.com/devonfw-forge/devonfw4flutter/wiki/200-Architecting-a-Flutter-App).

## The Three Types of Widgets

There are three types of Widgets in the Flutter Framework. I will now showcase their differences, their lifecycles, and their respective use-cases.

### Stateless Widgets

This is the most basic of the three and likely the one you’ll use the most when developing an app with Flutter. Stateless Widgets [\[32\]](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html) are initialized once with a set of parameters and those parameters will never change from there on out. Let’s have a look at an example. This is a simplified version of the card Widget from figure 8:

``` dart
class WisdomCard extends StatelessWidget {
  static const double _cardElevation = 2;
  static const double _cardBorderRadius = 7;

  final Wisdom _wisdom;

  WisdomCard(this._wisdom);

  @override
  Widget build(BuildContext context) {...}
  ...
}
```

*Code Snippet 7: Simplified Wisgen card Widget class [\[11\]](https://github.com/Fasust/wisgen)*

As you can see, it has some constant values for styling, a wisdom object that is passed into the constructor and a build method. The wisdom object contains the wisdom text and the hyperlink to the stock image.

One thing I want to point out here is that even if all fields are final in a StatelessWidget, it can still change to a degree. A ListView Widget is also a Stateless for example. It has a final reference to a list. Things can be added or removed from that list without the reference in the ListView Widget changing. So the ListView remains immutable and Stateless while the things it displays can change [\[35\]](https://www.youtube.com/watch?v=wE7khGHVkYY).

The Lifecycle of Stateless Widgets is very straight forward [\[31\]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956):

``` dart
class MyWidget extends StatelessWidget {

  ///Called first
  ///
  ///Use for initialization if needed
  MyClass({ Key key }) : super(key: key);

  ///Called multiple times a second
  ///
  ///Keep lightweight (!)
  ///This is where the actual UI is build
  @override
  Widget build(BuildContext context) {
    ...
  }
}
```

*Code Snippet 8: Stateless Widget Lifecycle*

### Stateful Widgets

I explained what State is in the Chapter [Thinking Declaratively](https://github.com/devonfw-forge/devonfw4flutter/wiki/120-Thinking-Declaratively). But just as a reminder:

| 📙 | State | Any data that can change over time [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) |
| - | ----- | :------------------------------------------------------------------------------------------------------------ |

A Stateful Widget [\[13\]](https://api.flutter.dev/flutter/widgets/StatefulWidget-class.html) always consists of two parts: An immutable Widget and a mutable State. The immutable Widget’s responsibility is to hold onto that State, the State itself has the mutable data and builds the actual UI element [\[36\]](https://www.youtube.com/watch?v=AqCMFXEmf3w). Let’s have a look at an example. This is a simplified version of the WisdomFeed from Figure 8. The *WisdomBloc* is responsible for generating and caching wisdoms that are then displayed in the Feed. More on that in the chapter [Architecting a Flutter App](https://github.com/devonfw-forge/devonfw4flutter/wiki/200-Architecting-a-Flutter-App).

``` dart
///Immutable Widget
class WisdomFeed extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => WisdomFeedState();
}

///Mutable State
class WisdomFeedState extends State<WisdomFeed>{
  WisdomBloc _wisdomBloc; //Mutable / Not final (!)

  @override
  Widget build(BuildContext context) {
    //This is where the actual UI element/Widget is build
    ...
  }
}
```

*Code Snippet 9: Simplified Wisgen WisdomFeed [\[11\]](https://github.com/Fasust/wisgen)*

If you are anything like me, you will ask yourself: “why is this split into 2 parts? The StatefulWidget is not really doing anything.” Well, The Flutter Team wants to keep Widgets **always** immutable. The only way to keep this statement universally true is to have the StatefulWidget hold onto the State but not actually be the State \[36\], \[37\].

State objects have a long lifespan in Flutter. They will stick around during rebuilds or even if the Widget that they are linked to gets replaced [\[36\]](https://www.youtube.com/watch?v=AqCMFXEmf3w). So in this example, no matter how often the WisdomFeed gets rebuild and no matter if the user switches pages, the cached list of wisdoms (WisdomBloc) will stay the same until the app is shut down.

The Lifecycle of State Objects/StatefulWidgets is a little bit more complex. I will only showcase a boiled-down version here. It contains all the methods you’ll need for this guide. You can read the full Lifecycle here: Lifecycle of StatefulWidgets [\[37\]](https://flutterbyexample.com//stateful-widget-lifecycle).

``` dart
class MyWidget extends StatefulWidget {

  ///Called immediately when first building the StatefulWidget
  @override
  State<StatefulWidget> createState() => MySate();
}

class MyState extends State<MyWidget>{
  
  ///Called after constructor
  ///
  ///Called exactly once
  ///Use this to subscribe to Streams or for any initialization
  @override
  initState(){...}

  ///Called multiple times a second
  ///
  ///Keep lightweight (!)
  ///This is where the actual UI element/Widget is build
  @override
  Widget build(BuildContext context){...}

  ///Called once before the State is disposed (app shut down)
  ///
  ///Use this for your clean up and to unsubscribe from Streams
  @override
  dispose(){...}
}
```

*Code Snippet 10: State Objects/StatefulWidgets Lifecycle*

### When to use Stateless & When to use Stateful

Keep in mind, to improve performance, you always want to rely on as few Stateful Widgets as possible. There are essentially two reasons to choose a Stateful Widget over a Stateless one:

1.  The Widget needs to hold any kind of data that has to change during its lifetime.
2.  The Widget needs to dispose of anything or clean up after itself at the end of its lifetime.

### Inherited Widgets

I will not go in detail on Inherited Widgets [\[38\]](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html) here. When using the BLoC library [\[39\]](https://felangel.github.io/bloc/#/), which I will teach you in the chapter [Architecting a Flutter-App](https://github.com/devonfw-forge/devonfw4flutter/wiki/200-Architecting-a-Flutter-App), you will most likely never create an Inherited Widgets yourself. But in short: They are a way to expose data from the top of the Widget Tree to all their descendants. And they are used as the underlying technology of the BLoC library.

# 140-Asynchronous-Flutter

## Introduction

Asynchronous Programming is an essential part of any modern application. There will always be network calls, user inputs or any number of other unpredictable events that an app has to wait for. Luckily Dart [\[3\]](https://dart.dev/) and Flutter [\[1\]](https://flutter.dev/) have a very good integration of Asynchronous Programming. This chapter will teach you the basics of Futures [\[3\]](https://dart.dev/) and Streams [\[40\]](https://dart.dev/tutorials/language/streams). The latter of these two will be especially important moving forward, as it one of the fundamental technologies used by the BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE). Throughout this chapter, I will be using the *HTTP package* [\[41\]](https://pub.dev/packages/http) to make network requests. Communication with the Web is one of the most common use-cases for Asynchronous Programming, so I thought it would only be fitting.

## Futures

Futures [\[3\]](https://dart.dev/) are the most basic way of dealing with asynchronous code in Flutter. If you have ever worked with JavaScript’s Promises [\[42\]](https://developer.mozilla.org/de/docs/Web/JavaScript/Reference/Global_Objects/Promise) before, they are basically the exact same thing. Here is a small example: This is a simplified version of the Wisgen *ApiSupplier* class. It can make requests to the AdviceSlip API [\[43\]](https://api.adviceslip.com/) to fetch some new advice texts.

``` dart
import 'package:http/http.dart' as http;

class ApiSupplier {
  ///Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() {
    //Define the Future and what the result will look like
    Future<http.Response> apiCall = http.get(_adviceURI); 

    //Define what will happen once it's resolved
    return apiCall.then((response) => Wisdom.fromResponse(response)); 
  }
}
```

*Code Snippet 11: Simplified Wisgen ApiSupplier (Futures) [\[11\]](https://github.com/Fasust/wisgen)*

We call *get()* on the HTTP module and give it the URL it should request. The *get()* method returns a Future. A Future object is a reference to an event that will take place at some point in the *future*. We can give it a callback function with *then()*, that will execute once that event is resolved. The callback we define will get access to the result of the Future IE it’s type: `Future<Type>`. So here, `Future<http.Response> apiCall` is a reference to when the API call will be resolved. Once the call is complete, *then()* will be called and we get access to the *http.Response*. We tell the Future to transform the Response into a wisdom object and return the result, by adding this instruction as a callback to *then()* \[44\], \[45\]. We can also handle errors with the *catchError()* function:

``` dart
import 'package:http/http.dart' as http;

class ApiSupplier {
  ///Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() {
    Future<http.Response> apiCall = http.get(_adviceURI);
    return apiCall
      .then((response) => Wisdom.fromResponse(response))
      .catchError((exception) => Wisdom.Empty);
  }
}
```

*Code Snippet 12: Simplified Wisgen ApiSupplier (Futures with Error) [\[11\]](https://github.com/Fasust/wisgen)*

### Async & Await

If you have ever worked with Promises or Futures before, you know that this can get really ugly really quickly: callbacks nested in callbacks. Luckily Dart supports the *async & await* keywords [\[46\]](https://dart.dev/codelabs/async-await), which give us the ability to structure our asynchronous code the same way we would if it was synchronous. Let’s take the same example as in snippet 11:

``` dart
import 'package:http/http.dart' as http;

class ApiSupplier {
  ///Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() async {
    http.Response response = await http.get(_adviceURI);
    return Wisdom.fromResponse(response);
  }
}
```

*Code Snippet 13: Simplified Wisgen ApiSupplier (Async & Await) [\[11\]](https://github.com/Fasust/wisgen)*

We can use the *await* keyword to tell Flutter to wait at on specific point until a Future is resolved. In this example, Flutter waits until the *http.Response* has arrived and then proceeds to transform it into a wisdom. If we want to use the *await* keyword in a function, we have to mark the function as *async*. This forces the return type of the function to be a Future because if we wait during the function, the function can not return instantly, thus it **has** to return a Future [\[47\]](https://www.youtube.com/watch?v=SmTCmDMi4BY). Error handling in async function can be done with *try/catch*:

``` dart
import 'package:http/http.dart' as http;

class ApiSupplier {
  ///Delivers 1 random advice as JSON
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

*Code Snippet 14: Simplified Wisgen ApiSupplier (Async & Await with Error) [\[11\]](https://github.com/Fasust/wisgen)*

## Streams

Streams [\[40\]](https://dart.dev/tutorials/language/streams) are one of the core technologies behind reactive programming [\[48\]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/). And we’ll use them heavily in the chapter [Architecting a Flutter app](https://github.com/devonfw-forge/devonfw4flutter/wiki/200-Architecting-a-Flutter-App). But what exactly are *Streams*? As Andrew Brogdon put’s it in one of Google’s official Dart tutorials, Streams are to Future what Iterables are to synchronous data types [\[49\]](https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17&t=345s). You can think of Streams as one continuous flow of data. Data can be put into the Stream, other parties can subscribe/listen to a given Stream and be notified once a new piece of data enters the Stream.

![Data Stream](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/stream.PNG)

*Figure 10: Data Stream*

Let’s have a look at how we can implement Streams in Dart. First, we initialize a *StreamController* [\[40\]](https://dart.dev/tutorials/language/streams) to generate a new Stream. The StreamController gives us access to a *Sink*, that we can use to put data into the Stream and the actual *Stream* object, which we can use to read data from the Stream:

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

*Code Snippet 15: Stream of Ints*

``` bash
0
1
2
3
4
```

*Code Snippet 16: Stream of Ints output*

Important Side Note:

| ⚠ | Streams in Dart are single subscription by default. So if you want multiple subscribers you need to initialize it like this: `StreamController streamController = new StreamController.broadcast();` |
| - | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

Let’s have a look at a more complex example. In Wisgen, our wisdoms are delivered to the UI via a Stream. Whenever we run out of wisdoms to display, a request is sent to a class that fetches new wisdom form our API [\[43\]](https://api.adviceslip.com/) and publishes them in a stream. Once the API response comes in, the UI gets notified and receives the new list of wisdoms. This approach is part of the *BLoC Pattern* [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE) and I will explain it in detail in the chapter [Architecting a Flutter app](https://github.com/devonfw-forge/devonfw4flutter/wiki/200-Architecting-a-Flutter-App). For now, this is a simplified version of the class that is responsible for streaming wisdom:

``` dart
class WisdomBloc {
  final ApiSupplier _api = new Api();
  List<Wisdom> _oldWisdoms = new List();

  //Stream
  final StreamController _streamController = StreamController<List<Wisdom>>; 
  StreamSink<List<Wisdom>> get _wisdomSink => _streamController.sink; //Data In
  Stream<List<Wisdom>> get wisdomStream => _streamController.stream; //Data out

  ///Called from UI to tell the BLoC to put more wisdom into the Stream
  publishMoreWisdom() async {

    //Fetch a wisdom form the API (Snippet 14)
    Wisdom fetchedWisdom = await _api.fetch();
    

    //Appending the new wisdoms to the current State
    List<Wisdom> newWisdoms = _oldWisdoms..add(fetchedWisdom);

    _wisdomSink.add(newWisdoms); //publish to stream
    _oldWisdoms = newWisdoms;
  }

  ///Called when UI is disposed
  dispose() => _streamController.close();
}
```

*Code Snippet 17: Simplified Wisgen WisdomBLoC [\[11\]](https://github.com/Fasust/wisgen)*

First, we create a *StreamController* and expose the Stream itself to enable the UI to subscribe to it. We also open up a private Sink, so we can easily add new wisdoms to the Stream. Whenever the *publishMoreWisdom()* function is called, the BLoC request a new wisdom from the API, waits until it is fetched, and then publishes it to the Stream. Let’s look at the UI side of things. This is a simplified version of the WisdomFeed in Wisgen:

``` dart
class WisdomFeedState extends State<WisdomFeed> {

  WisdomBloc _wisdomBloc;

  ///We Tell the WisdomBLoC to fetch more data based on how far we have scrolled down
  ///the list. That is why we need this Controller
  final _scrollController = ScrollController();
  static const _scrollThreshold = 200.0;

  @override
  void initState() {
    _wisdomBloc = new WisdomBloc();    
    _wisdomBloc.publishMoreWisdom(); //Dispatch initial event

    _scrollController.addListener(_onScroll);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: StreamBuilder(
        stream: _wisdomBloc.wisdomStream,
        builder: (context, AsyncSnapshot<List<Wisdom>> snapshot) {
          //Show Error message
          if (snapshot.hasError) return ErrorText(state.exception); 
          //Show loading animation
          if (snapshot.connectionState == ConnectionState.waiting) return LoadingSpinner(); 
          //Create ListView of wisdoms
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

  ///Dispatching fetch events to the BLoC when we reach the end of the list
  void _onScroll() {
    final maxScroll = _scrollController.position.maxScrollExtent;
    final currentScroll = _scrollController.position.pixels;
    if (maxScroll - currentScroll <= _scrollThreshold) {
      _wisdomBloc.publishMoreWisdom(); //Publish more wisdoms
    }
  }
  ...
}
```

*Code Snippet 18: Simplified Wisgen WisdomFeed with StreamBuilder [\[11\]](https://github.com/Fasust/wisgen)*

Alright, let’s go through this step by step:

First, we initialize our WisdomBloc in the *initSate()* method. This is also where we set up a ScrollController [\[50\]](https://api.flutter.dev/flutter/widgets/ScrollController-class.html) that we can use to determine how far down the list we have scrolled [\[51\]](https://felangel.github.io/bloc/#/flutterinfinitelisttutorial). I won’t go into the details here, but the controller enables us to call *publishMoreWisdom()* on the WisdomBloc whenever we are near the end of our list. This way we get infinite scrolling.

In the *build()* method, we use Flutter’s *StreamBuilder* Widget [\[52\]](https://api.flutter.dev/flutter/widgets/StreamBuilder-class.html) to link our UI to our Stream. We give it our Stream and it provides a builder method. This builder method receives a Snapshot containing the current State of the Stream whenever the State of the Stream changes IE when new wisdom is added. We can use the Snapshot to determine when the UI needs to display a loading animation, an error message or the actual list. When we receive new wisdoms from our Stream through the Snapshot, we continue to the *listView()* method.

Here we use the list of wisdoms to create a ListView containing cards that display wisdoms. You might have wondered why we Stream a list of wisdoms and not just individual wisdoms. This ListView is the reason. If we where streaming individual wisdoms we would need to combine them into a list here. Streaming a complete list when using a StreamBuilder to create infinite scrolling is also the recommended approach by the Flutter Team [\[53\]](https://www.youtube.com/watch?v=fahC3ky_zW0).

Finally, once the app is closed down, the *dispose()* method is called and we dispose of our Stream and ScrollController.

![Streaming Wisdom from BLoC to WisdomFeed](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisdomBloc-stream.PNG)

*Figure 11: Streaming Wisdom from BLoC to WisdomFeed [\[11\]](https://github.com/Fasust/wisgen)*

### Async\* & Yield

Streams have two keywords that are very similar to the *async & await* of Futures: *async\* & yield* [\[40\]](https://dart.dev/tutorials/language/streams). If we mark a function as *async\** the return type **has** to be a Stream. In an async\* function we get access to the async keyword (which we already know) and the yield keyword, which is very similar to a return, only that yield does not terminate the function but instead adds a value to the Stream. This is what an implementation of the WisdomBloc from snippet 17 could look like with async\*:

``` dart
Stream<List<Wisdom>> streamWisdoms() async* {
  Wisdom fetchedWisdom = await _api.fetch();

  //Appending the new wisdom to the current State
  List<Wisdom> newWisdoms = _oldWisdoms..add(fetchedWisdom);

  yield newWisdoms; //Publish to Stream

  _oldWisdoms = newWisdoms;
}
```

*Code Snippet 19: Simplified Wisgen WisdomBLoC with async\* [\[11\]](https://github.com/Fasust/wisgen)*

This marks the end of my introduction to Streams. It can be a challenging topic wrap your head around at first so if you still feel like you want to learn more I can highly recommend this article by Didier Boelens [\[48\]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/) or this 8-minute tutorial video by the Flutter Team [\[49\]](https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17&t)

## Side Note on Communication with the Web

I just wanted to end this chapter by showing you how the ApiSupplier class of Wisgen [\[11\]](https://github.com/Fasust/wisgen) actually looks like and give some input of why it looks the way it does:

``` dart
import 'dart:convert';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:wisgen/models/advice_slips.dart';
import 'package:wisgen/models/wisdom.dart';
import 'package:wisgen/repositories/supplier.dart';
import 'package:http/http.dart' as http;

///[Supplier] that caches [Wisdom]s it fetches from an API and
///then provides a given amount of random entries.
///
///[Wisdom]s Supplied do not have an Image URL
class ApiSupplier implements Supplier<List<Wisdom>> {
  ///Advice SLip API Query that requests all (~213) text entries from the API.
  ///We fetch all entries at once and cache them locally to minimize network traffic.
  ///The Advice Slip API also does not provide the option to request a selected amount of entries.
  ///That's why I think this is the best approach.
  static const _adviceURI = 'https://api.adviceslip.com/advice/search/%20';
  List<Wisdom> _cache;
  final Random _random = Random();

  @override
  Future<List<Wisdom>> fetch(int amount, BuildContext context) async {
    //if the cache is empty, request data from the API
    if (_cache == null) _cache = await _loadData();

    List<Wisdom> res = List();
    for (int i = 0; i < amount; i++) {
      res.add(_cache[_random.nextInt(_cache.length)]);
    }
    return res;
  }

  ///Fetches Data from API and coverts it to wisdoms
  Future<List<Wisdom>> _loadData() async {
    http.Response response = await http.get(_adviceURI);
    AdviceSlips adviceSlips = AdviceSlips.fromJson(json.decode(response.body));

    List<Wisdom> wisdoms = List();
    adviceSlips.slips.forEach((slip) {
      wisdoms.add(slip.toWisdom());
    });

    return wisdoms;
  }
}
```

*Code Snippet 20: Actual Wisgen ApiSupplier [\[11\]](https://github.com/Fasust/wisgen)*

The *AdviceSlips* class was generated with a JSON to Dart converter [\[54\]](https://javiercbk.github.io/json_to_dart/). The generated class has a *fromJson()* function that makes it easy to populate it’s data fields with the JSON response. I used this class instead of implementing a method in the *Wisdom* class because I did not want a direct dependency from my entity class to the AdviceSlip JSON structure. This is the generated class, you don’t need to read it all, I just want to give you an idea of how it looks like:

``` dart
import 'package:wisgen/models/wisdom.dart';

///Generated class to handle JSON input from AdviceSlip API.
///I used this tool: https://javiercbk.github.io/json_to_dart/.
class AdviceSlips {
  String totalResults;
  String query;
  List<Slips> slips;

  AdviceSlips({this.totalResults, this.query, this.slips});

  AdviceSlips.fromJson(Map<String, dynamic> json) {
    totalResults = json['total_results'];
    query = json['query'];
    if (json['slips'] != null) {
      slips = List<Slips>();
      json['slips'].forEach((v) {
        slips.add(Slips.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['total_results'] = this.totalResults;
    data['query'] = this.query;
    if (this.slips != null) {
      data['slips'] = this.slips.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

///Generated class to handle JSON input from AdviceSlip API.
///I used this tool: https://javiercbk.github.io/json_to_dart/.
///A Slip can be converted directly into a Wisdom object (this is a function I added myself)
class Slips {
  String advice;
  String slipId;

  Slips({this.advice, this.slipId});

  Slips.fromJson(Map<String, dynamic> json) {
    advice = json['advice'];
    slipId = json['slip_id'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['advice'] = this.advice;
    data['slip_id'] = this.slipId;
    return data;
  }

  ///Not generated
  Wisdom toWisdom() {
    return Wisdom(
      id: int.parse(slipId),
      text: advice,
      type: "Advice Slip",
    );
  }
}
```

*Code Snippet 21: Wisgen AdviceSlips [\[11\]](https://github.com/Fasust/wisgen)*

# 200-Architecting-a-Flutter-App

## Introduction

The Most central topic of architecting a Flutter app [\[1\]](https://flutter.dev/) is *State Management* [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). **Where** does my State sit, **who** needs access to it, and **how** do they access it? This chapter aims to answer those questions. You will learn about the two types of State, you will be introduced to the three most popular [State Management Solutions](https://github.com/devonfw-forge/devonfw4flutter/wiki/210-State-Management-Alternatives) and you will learn one of those State Management Solutions ([BLoC](https://github.com/devonfw-forge/devonfw4flutter/wiki/220-BLoC) [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE)) in detail. You will also learn how to use the BLoC State Management Solution in a clean and scalable [four-layered architecture](https://github.com/devonfw-forge/devonfw4flutter/wiki/220-BLoC).

## State Management vs Architecture

I want to differentiate these two terms. Within the Flutter community, *State Management* and *Architecture* are often used synonymously, but I think we should be careful to do so. State Management is, as the name indicates, managing the State of an application. This can be done with the help of a technology or a framework \[12\], \[55\]. Architecture, on the other hand, is the overarching structure of an app. A set of rules that an app conforms to [\[56\]](http://www.iso.org/cms/render/live/en/sites/isoorg/contents/data/standard/05/05/50508.html). Any architecture for a Flutter application will have some sort of State Management, but State Management is not an architecture by itself. I just want you to keep this in mind for the following chapters.

## Types of State

The Flutter documentation [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) differentiates between two types of State: *Ephemeral State* & *App State*.
Ephemeral State is State that is only required in one location IE inside of one Widget. Examples would be: scroll position in a list, highlighting of selected elements or the color change of a pressed button. This is the type of State that we don’t need to worry about that much or in other words, there is no need for a fancy State Management Solution for Ephemeral State. We can simply use a Stateful Widget with some variables and manage Ephemeral State that way [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). The more interesting type of State is App State. This is information that is required in multiple locations/by multiple Widgets. Examples would be user data, a list of favorites or a shopping cart. App State management is going to be the focus of this chapter.

![Ephemeral State vs App State decision tree](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/ephemeral-vs-app-state.png)

*Figure 12: Ephemeral State vs App State decision tree [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt)*

## Contents of this Chapter

  - [State Management Alternatives](https://github.com/devonfw-forge/devonfw4flutter/wiki/210-State-Management-Alternatives)
  - [BLoC](https://github.com/devonfw-forge/devonfw4flutter/wiki/220-BLoC)

# 210-State-Management-Alternatives

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

The Provider Package [\[58\]](https://pub.dev/packages/provider) is a State Management Solution for Flutter published by Remi Rousselet in 2018. It has since then been endorsed by the Flutter Team on multiple occasions \[59\], \[60\] and Rousselet and the Flutter Team are now devolving it in cooperation. The package is basically a prettier interface for Inherited Widgets [\[38\]](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html). You can use Provider to expose State from a Widget at the top of the tree to any number of Widgets below it in the tree.

As a quick reminder: Data in Flutter always flows **downwards**. If you want to access data from multiple locations within your Widget Tree, you have to place it at one of their common ancestors so they can both access it through their BuildContexts [\[34\]](https://api.flutter.dev/flutter/widgets/BuildContext-class.html). This practice is called *“lifting State up”* and it is very common within declarative frameworks [\[61\]](https://www.youtube.com/watch?v=zKXz3pUkw9A).

| 📙 | Lifting State up | Placing State at the lowest common ancestor of all Widgets that need access to it [\[61\]](https://www.youtube.com/watch?v=zKXz3pUkw9A) |
| - | ---------------- | :-------------------------------------------------------------------------------------------------------------------------------------- |

The Provider Package is an easy way for us to lift State up. Let’s look at our example from figure 14: The first common ancestor of all Widgets in need of the favorite list is *MaterialApp*. So we will need to lift the State up to the MaterialApp Widget and then have our other Widgets access it from there:

![Wisgen WidgetTree favorites with Provider](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen-pagetree-provider.PNG)

*Figure 15: Wisgen WidgetTree favorites with Provider [\[11\]](https://github.com/Fasust/wisgen)*

To minimize re-builds the Provider Package uses ChangeNotifiers [\[62\]](https://api.flutter.dev/flutter/foundation/ChangeNotifier-class.html). This way Widgets can subscribe/listen to the provided State and get notified whenever it changes. This is how an implementation of Wisgen’s favorite list would look like using Provider. “*Favorites*” is the class we will use to provide our favorite list globally. The *notifyListeners()* function will trigger rebuilds on all Widgets that listen to it.

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

All in all, Provider is a great and easy solution to distribute State in a small Flutter application. But it is just that, a State Management Solution and not an architecture \[59\], \[60\], \[63\], \[64\]. Just the Provider package alone with no pattern to follow or an architecture to obey will not lead to a clean and manageable application. But no worries, I did not teach you about the package for nothing. Because Provider is such an efficient and easy way to distribute State, the BLoC package [\[39\]](https://felangel.github.io/bloc/#/) uses it as an underlying technology for their approach.

## Redux

Redux [\[65\]](https://redux.js.org/) is State Management Solution with an associated architectural pattern. It was originally built for React [\[22\]](https://facebook.github.io/react-native/) in 2015 by Dan Abramov. It was late ported to Flutter by Brian Egan in 2017 [\[66\]](https://pub.dev/packages/flutter_redux). Redux uses a *Store* as one central location for all business logic. This Store is put at the very top of the Widget Tree and then globally provided to all Widgets using an Inherited Widget. We extract as much logic from the UI as possible. It should only send actions to the Store (such as user inputs) and display the UI dependant on the current State of the Store. The Store has *Reducer* functions, that take in the previous State and an *Action* and return a new State. \[61\], \[63\], \[67\] So in Wisgen, the Dataflow would look something like this:

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

1.  It was publicly endorsed by the Flutter Team on multiple occasions \[7\], \[12\], \[53\], \[59\], \[70\]
2.  It also has clear architectural rules [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE)
3.  It also enables the implementation of a layered architecture [\[71\]](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1)
4.  It was developed by one of Flutter’s Engineers [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE)
5.  It does not end up with one giant Store for the business logic. Instead, we spread the business logic out into multiple BLoCs with separate responsibilities [\[63\]](https://www.didierboelens.com/2019/04/bloc---scopedmodel---redux---comparison/)

# 220-BLoC

## Introduction

BLoC is an architectural pattern that functions as a State Management Solution. It was designed by Paolo Soares in 2018 [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE). Its original purpose was to enable code sharing between Flutter [\[1\]](%5B@flutterdevteamFlutterFramework2018%5D) and Angular Dart [\[72\]](https://angulardart.dev/) applications. This chapter will give you an in-depth understanding of what the BLoC Pattern is and how it works. You will learn how to implement it using the BLoC Package [\[39\]](https://felangel.github.io/bloc/#/) by Felix Angelov. And Finally, you will learn how to use the BLoC Pattern to achieve a scalable four-layered architecture for your application.

## Intro to BLoC

When Soares designed the BLoC Pattern, he was working on applications in both Flutter and Angular Dart. He wanted a pattern that enabled him to hook up the same business logic to both Flutter and Angular Dart apps. His idea was to remove business logic from the UI as much as possible and extract it into its own classes, into BLoCs (Business Logic Components). The UI should only send *Events* to BLoCs and display the UI based on the *State* of the BLoCs. Soares defined, that UI and BLoCs should only communicate through Streams [\[40\]](https://dart.dev/tutorials/language/streams). This way the developer would not need to worry about manually telling the UI to redraw. The UI can simply subscribe to a Stream of State [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) emitted by a BLoC and change based on the incoming State \[7\], \[48\], \[53\], \[70\].

| 📙 | BLoC | Business Logic Component [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE) |
| - | ---- | :---------------------------------------------------------------------------- |

| 🕐 | TLDR | The UI should be kept free of business logic. The UI only publishes *Events* to a BLoC and subscribes to a Stream of *State* emitted by a BLoC |
| - | ---- | :--------------------------------------------------------------------------------------------------------------------------------------------- |

![Bloc turning input Events to a Stream of State](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/bloc-architecture.png)

*Figure 17: Bloc turning input Events to a Stream of State [\[70\]](https://www.youtube.com/watch?v=RS36gBEp8OI)*

## Advantages of BLoC

That’s all well and good, but why should you care? An application that follows the rules defined by the BLoC pattern will…

1.  have all its business logic in one place.
2.  have business logic that functions independently of the UI.
3.  have a UI that can be changed without affecting the business Logic.
4.  have easily testable business logic.
5.  rely on few rebuilds, as the UI only rebuilds when the State related to that UI changes.
6.  have an App-State with very predictable transitions as the pattern enforces a single way for State to change throughout the entire application.

\[7\], \[39\], \[48\], \[63\], \[64\]

## Rules of the BLoC Pattern

To gain those promised advantages, you will have to follow these 8 rules Soares defined for the BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE):

#### Rules for the BLoCs

1.  Input/Outputs are simple **Sinks/Streams** only.
2.  All **dependencies** must be **injectable** and platform agnostic.
3.  **No platform branching**.
      - No `if(IOS) then doThis()`.
4.  The actual implementation can be whatever you want if you follow 1-3.

#### Rules for UI Classes

1.  Each *“Complex Enough”* Widget has a related BLoC.
      - You will have to define what *“Complex Enough”* means for your app.
2.  Widgets **do not format the inputs** they send to the BLoC.
      - Because formating is business logic.
3.  Widgets should display the BLoCs **State/output with as little formatting as possible**.
      - Sometimes a little formatting is inevitable, but more complex things like currency formating is business logic and should be done in a BLoC.
4.  If you do have **platform branching**, It should be dependent on **a single bool State/output** emitted by a BLoC.

![What a BLoC looks like](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/bloc-sink-stream.png)

*Figure 18: What a BLoC looks like [\[48\]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/)*

## Implementation

Alright, Now that you know what the BLoC Pattern is, let’s have a look at how it looks in practice. I am using the BLoC package [\[39\]](https://felangel.github.io/bloc/#/) for Flutter by Felix Angelov, as it removes a lot of the boilerplate code we would have to write if we would implement our own BLoCs from scratch and because it was publicly endorsed by the Flutter Team [\[73\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt/options#bloc--rx). I am going to use the same example of *App State* as I did in the [previous chapter](https://github.com/devonfw-forge/devonfw4flutter/wiki/210-State-Management-Alternatives): The favorite list in Wisgen [\[11\]](https://github.com/Fasust/wisgen). First, let’s have a look at how the Bloc Pattern will interact with Wisgen on a more abstract scale:

![Bloc and Wisgen Widget Tree](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen-pagetree-bloc.PNG)

*Figure 19: Bloc and Wisgen Widget Tree [\[11\]](https://github.com/Fasust/wisgen)*

These are the “*Events*” that can be sent to the BLoC by the UI. It is a common practice to use inheriting classes for *Events*. This way we can communicate intent through the name of the class and add some data for the *Event* to carry in its members. This approach of implementing *Events* is very similar to Redux’s *Actions* [\[65\]](https://redux.js.org/).

``` dart
@immutable
abstract class FavoriteEvent {
  final Wisdom _favorite;
  get favorite => _favorite;

  FavoriteEvent(this._favorite);
}

///Adds a given [Wisdom] to the [FavoriteBloc] when dispatched
class FavoriteEventAdd extends FavoriteEvent {
  FavoriteEventAdd(Wisdom favorite) : super(favorite);
}

///Removes a given [Wisdom] from the [FavoriteBloc] when dispatched
class FavoriteEventRemove extends FavoriteEvent {
  FavoriteEventRemove(Wisdom favorite) : super(favorite);
}
```

*Code Snippet 29: Favorite Events in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

Now the arguably most interesting part of an implementation of the BLoC Pattern, the BLoC class itself. We extend the BLoC class provided by the Flutter BLoC package. It takes in the type of the *Events* that will be sent to the BLoC and the type of the *State* that will be emitted by the BLoC `Bloc<Event, State>`:

``` dart
///Responsible for keeping track of the Favorite list. 
///
///Receives [FavoriteEvent] to add/remove favorite [Wisdom] objects 
///from its list.
///Broadcasts complete list of favorites every time it changes.
class FavoriteBloc extends Bloc<FavoriteEvent, List<Wisdom>> {
  @override
  List<Wisdom> get initialState => List<Wisdom>();

  //Called when the BLoC receives an Event
  @override
  Stream<List<Wisdom>> mapEventToState(FavoriteEvent event) async* {
    List<Wisdom> newFavorites = List()..addAll(currentState);

    //Determine how to manipulate the State based on the Event
    if (event is FavoriteEventAdd) newFavorites.add(event.favorite);
    if (event is FavoriteEventRemove) newFavorites.remove(event.favorite);

    //Emit new State
    yield newFavorites;
  }
}
```

*Code Snippet 30: Favorite BLoC in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

As I mentioned before, the BLoC package for Flutter uses the Provider package [\[58\]](https://pub.dev/packages/provider). This means that we can provide our BLoC to the rest of our Widget Tree in the same way we learned in the chapter [State Management Alternatives](https://github.com/devonfw-forge/devonfw4flutter/wiki/210-State-Management-Alternatives). By the rule of *“lifting State up”*, we have to place the favorite BLoC at the lowest common ancestor of all Widgets that need access to it. So in our case at *MaterialApp*:

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

*Code Snippet 31: Providing a BLoC Globally in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

Now we can access the BLoC from all descendants of the *BlocProvider* Widget. This is the favorite button in Wisgen. It changes shape and color based on the State emitted by the FavoriteBLoC and it dispatches Events to the BLoC to add and remove favorites. The *wisdom* object is the wisdom displayed on the Card Widget.

``` dart
...
@override
Widget build(BuildContext context) {
  return Expanded(
    flex: 1,
    //This is where we subscribe to the FavoriteBLoC
    child: BlocBuilder<FavoriteBloc, List<Wisdom>>(
      builder: (context, favorites) => IconButton(
        //Display Icon Button depending on current State
        //Re-build when favorite list changes
        icon: Icon(favorites.contains(wisdom)
            ? Icons.favorite
            : Icons.favorite_border),
        color: favorites.contains(wisdom) 
            ? Colors.red 
            : Colors.grey,
        onPressed: () {
          //Grab FavoriteBloc through BuildContext
          FavoriteBloc favoriteBloc = BlocProvider.of<FavoriteBloc>(context);
          
          //Add/remove Wisdom to/from favorites (dispatch events)
          if (favorites.contains(wisdom)) favoriteBloc.dispatch(FavoriteEventRemove(wisdom));
          else favoriteBloc.dispatch(FavoriteEventAdd(wisdom));  
        },
      ),
    ),
  )
}
...
```

*Code Snippet 32: Accessing a BLoC in Wisgen [\[11\]](https://github.com/Fasust/wisgen)*

## Layered Architecture

Now that we understand how to implement the BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE), lets’ have a look at how we can use it to achieve a four-layered architecture with one-way dependencies [\[71\]](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1):

<img src="https://github.com/devonfw-forge/devonfw4flutter/wiki//images/bloc-my-layers.png" height="500" alt="Four-Layered BLoC architecture">

*Figure 20: Four-Layered BLoC architecture*

### UI Layer

This is the layer that our user directly interacts with. It is the Widget Tree of our application, all Widgets of our app sit here. We need to keep this layer as *stupid* as possible, No business logic and only minor formating.

### Business Logic Layer

This is where all our BLoCs reside. All our business logic sits in this layer. The communication between this layer and the *UI Layer* should be limited to *Sinks* and *Streams*:

![Widget BLoC communication](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/widget-bloc-communication.PNG)

*Figure 21: Widget BLoC communication*

For this Layer, all platform-specific dependencies should be injectable. To achieve this, the Flutter community \[39\], \[57\], \[71\], \[74\] mostly uses the *Repository Pattern* [\[75\]](https://dl.acm.org/citation.cfm?id=865128) or as *“Uncle Bob”* would say: *Boundary Objects* [\[76\]](https://www.youtube.com/watch?v=o_TH-Y78tt4). Even if this pattern is not an explicit part of BLoC, I personally think it is a very clean solution. Instead of having BLoCs directly depend on platform-specific interfaces, we create *Repository* interfaces for the BLoCs to depend on:

``` dart
///Generic Repository that fetches a given amount of T
abstract class Supplier<T>{
  Future<T> fetch(int amount, BuildContext context);
}
```

*Code Snippet 33: Wisgen platform-agnostic Repository [\[11\]](https://github.com/Fasust/wisgen)*

The actual implementation of the *Repository* can then be injected into the BLoC.

### Repository Layer

This Layer consist of platform-agnostic interfaces. Things like *Data Base*, *Service*, and *Supplier* (Snippet 33).

### Data Layer

These are the actual implementations of our *Repositories*. Platform-specific things like a Database connector or a class that makes API calls.

## Architecture in Practice

To give you a better understanding of how this architecture works in practice, I will walk you through how Wisgen [\[11\]](https://github.com/Fasust/wisgen) is built using the BLoC Pattern and a four-layered architecture.

![Wisgen architecture with dependencies](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen_depencies.PNG)

*Figure 22: Wisgen architecture with dependencies [\[11\]](https://github.com/Fasust/wisgen)*

In the UI Layer, we have all the Widgets that make up Wisgen. Three of those actually consume State from the BLoC Layer, so those are the only ones I put in figure 22. The *Wisdom Feed* displays an infinite list of wisdoms. Whenever the user scrolls close to the bottom of the list, the Wisdom Feed sends a *Request-Event* to the Wisdom BLoC [\[51\]](https://felangel.github.io/bloc/#/flutterinfinitelisttutorial). This Event causes the *Wisdom BLoC* to fetch more data from its *Repository*. You can see the *Repository* interface in snippet 33. This way the Wisdom BLoC just knows it can fetch some data with its Repository and it does not care where the data comes from or how the data is fetched. In our case, the Repository could be implemented to either load some wisdoms from a local list or fetch some wisdoms from an API. I already covered the implementation of the API Repository class in the chapter [Asynchronous Flutter](https://github.com/devonfw-forge/devonfw4flutter/wiki/140-Asynchronous-Flutter) if you want to remind yourself again. When the Wisdom BLoC receives a response from its Repository, it publishes the new wisdoms to its Stream [\[40\]](https://dart.dev/tutorials/language/streams) and all listening Widgets will be notified.

![Wisgen dataflow](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen-dataflow.png)

*Figure 23: Wisgen dataflow [\[11\]](https://github.com/Fasust/wisgen)*

I already covered how the favorite list works in detail in this chapter, so I won’t go over it again. The *Storage BLoC* keeps a persistent copy of the favorite list on the device. It receives a *Load-Event* once on start-up, loads the old favorite list from its *Storage*, and adds it to the *Favorite BLoC* though *Add-Events*. It also listens to the *Favorite BLoC* and updates the persistent copy of the favorite list every time the *Favorite Bloc* emits a new State:

``` dart
///Gives access to the 2 Events the [StorageBloc] can receive.
///
///It is an enum because the 2 Events both don't need to carry additional data.
///[StorageEvent.load] tells the [StorageBloc] to load the 
///favorite list from its [Storage].
///[StorageEvent.wipe] tells the [StorageBloc] to wipe 
///any favorites on its [Storage].
enum StorageEvent { load, wipe }

///Responsible for keeping a persistent copy of the favorite list on its [Storage].
///
///It is injected with a [FavoriteBLoC] on creation.
///It subscribes to the [FavoriteBLoC] and writes the favorite list to 
///its [Storage] device every time a new State is emitted by the [FavoriteBLoC].
///When the [StorageBLoC] receives a [StorageEvent.load], it loads a list of [Wisdom]s 
///from its [Storage] device and pipes it into the [FavoriteBLoC] though [FavoriteEventAdd]s
///(This usually happens once on start-up).
///Its State is [dynamic] because it never needs to emit it.
class StorageBloc extends Bloc<StorageEvent, dynamic> {
  Storage _storage = SharedPreferenceStorage();
  FavoriteBloc _observedBloc;

  StorageBloc(this._observedBloc) {
    //Subscribe to BLoC
    _observedBloc.state.listen((state) async {
      await _storage.save(state);
    });
  }

  @override
  dynamic get initialState => dynamic;

  ///Called every time a new [StorageEvent] comes in.
  @override
  Stream<dynamic> mapEventToState(StorageEvent event) async* {
    if (event == StorageEvent.load) await _load();
    if (event == StorageEvent.wipe) _storage.wipeStorage();
  }

  ///Load the old favorite list form [Storgae].
  _load() async {
    List<Wisdom> loaded = await _storage.load();

    if (loaded == null || loaded.isEmpty) return;

    loaded.forEach((f) {
      _observedBloc.dispatch(FavoriteEventAdd(f));
    });
  }

  //Injection ---
  set storage(Storage storage) => _storage = storage;
  set observedBloc(FavoriteBloc observedBloc) => _observedBloc = observedBloc;
}
```

*Code Snippet 34: Wisgen Storage BLoC [\[11\]](https://github.com/Fasust/wisgen)*

*Storage* is also a platform-agnostic interface and it looks like this:

``` dart
///Generic Repository for read/write Storage device.
abstract class Storage<T>{
  Future<T> load();
  save(T data);

  ///Wipe the Storage Medium of all T.
  wipeStorage();
}
```

*Code Snippet 35: Wisgen platform-agnostic Repository: Storage [\[11\]](https://github.com/Fasust/wisgen)*

In Wisgen, I built an implementation of *Storage* that communicates with Androids Shared Preferences [\[77\]](https://developer.android.com/reference/android/content/SharedPreferences) and saves the favorite list as a JSON:

``` dart
///[Storage] that gives access to Androids Shared Preferences 
///(a small, local, persistent key-value-store).
class SharedPreferenceStorage implements Storage<List<Wisdom>> {
  ///Key is used to access store.
  static const String _sharedPrefKey = "wisgen_storage";

  @override
  Future<List<Wisdom>> load() async {
    final prefs = await SharedPreferences.getInstance();
    List<String> strings = prefs.getStringList(_sharedPrefKey);

    if (strings == null || strings.isEmpty) return null;

    //Decode all JSON strings we fetched from the
    //Preferences and add them to the result.
    List<Wisdom> wisdoms = List();
    strings.forEach((s) {
      Wisdom w = Wisdom.fromJson(jsonDecode(s));

      wisdoms.add(w);
    });
    return wisdoms;
  }

  @override
  save(List<Wisdom> data) async {
    if (data == null || data.isEmpty) return;

    final prefs = await SharedPreferences.getInstance();

    //Encode data to JSON Strings
    List<String> strings = List();
    data.forEach((wisdom) {
      strings.add(json.encode(wisdom.toJson()));
    });

    //Overwrite Preferences with new List
    prefs.setStringList(_sharedPrefKey, strings);
  }

  @override
  wipeStorage() async {
    final prefs = await SharedPreferences.getInstance();
    prefs.remove(_sharedPrefKey);
  }
}
```

*Code Snippet 36: Wisgen’s Storage implementation for SharedPreferences [\[11\]](https://github.com/Fasust/wisgen)*

# 300-Testing

## Introduction

Testing has become an essential part of developing any large scale application and there is strong evidence that writing tests leads to a higher code quality [\[78\]](http://doi.acm.org/10.1145/952532.952753). This chapter aims to give you a brief introduction to how testing in Flutter [\[1\]](https://flutter.dev/) works and more specifically, how to test an app that implements the BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE).

## Types of Tests in Flutter

Flutters official test library [\[79\]](https://pub.dev/packages/test) differentiates between three types of tests:

#### Unit Tests

Unit Tests can be run very quickly. They can test any function of your app, that does not require the rendering of a Widget [\[80\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). Their main use-case is to test business logic or in our case: BLoCs [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE).

#### Widget Tests

Widget Tests are used to test small Widget Sub-Trees of your application. They run relatively quickly and can test the behavior of a given UI [\[80\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

#### Integration Test (Driver Tests)

Integration/Driver Tests run your entire application in a virtual machine or on a physical device. They can test user-journeys and complete use-cases. They are very slow and *“prone to braking”*[\[80\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

![Flutter test comparison](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/test-comp.PNG)

*Figure 24: Flutter test comparison [\[81\]](https://flutter.dev/docs/testing)*

## Writing Unit Tests

I will focus on *Unit Tests* for this guide. The Flutter Team recommends that the majority of Flutter tests should be Unit Test \[80\], \[81\]. The fact that they are quick to write and quick to execute makes up for their relatively low *confidence*. In addition to this, because we are using the BLoC Pattern, our UI shouldn’t contain that much testable code anyways. Or to paraphrase the BLoC pattern creator: We keep our UI so *stupid* we don’t need to test it [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE). First, we have to import the test library [\[79\]](https://pub.dev/packages/test) and the *mockito* package [\[82\]](https://pub.dev/packages/mockito) in our *pubspec.yaml*:

``` yaml
dev_dependencies:
  mockito: ^4.1.1
  flutter_test:
    sdk: flutter
```

*Code Snippet 37: Pubspec.yaml Test Imports*

*flutter\_test* offers the core testing capabilities of Flutter. *mockito* is used to mock up dependencies. All our tests should sit in a directory named *“test”* on the root level of our app directory. If we want to place them somewhere else, we have to specify their location every time we want to run them.

![Wisgen test directory](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen-test-dir.PNG)

*Figure 25: Wisgen test directory [\[11\]](https://github.com/Fasust/wisgen)*

| ⚠ | All test files have to end with the postfix "\_test.dart" to be recognized by the framework [\[80\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). |
| - | :-------------------------------------------------------------------------------------------------------------------------------------------------- |

Now we can start writing our tests. For this example, I will test the favorite BLoC of Wisgen [\[11\]](https://github.com/Fasust/wisgen):

``` dart
void main() {

  ///Related test are grouped together 
  ///to get a more readable output 
  group('Favorite Bloc', () {
    FavoriteBloc favoriteBloc;

    setUp((){
      //Run before each test
      favoriteBloc = new FavoriteBloc();
    });

    tearDown((){
      //Run after each test
      favoriteBloc.dispose();
    });
    
    test('Initial State is an empty list', () {
      expect(favoriteBloc.currentState, List());
    });

    ...
  });
}
```

*Code Snippet 38: Wisgen Favorite BLoC Tests 1 [\[11\]](https://github.com/Fasust/wisgen)*

We can use the *group()* function to group related tests together. This way the output of our tests is more neatly formated [\[80\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). *setUp()* is called once before every test, so it is perfect for initializing our BLoC [\[83\]](https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86). *tearDown()* is called after every test, so we can use it to dispose of our BLoC. The *test()* function takes in a name and a callback with the actual test. In our test, we check if the State of the favorite BloC after initialization is an empty list. *expect()* takes in the actual value and the value that is expected: `expect(actual, matcher)`. We can run all our tests using the command `flutter test`.

### Testing Streams

Now a more relevant topic when working with the BLoC Pattern, the testing of Streams [\[40\]](https://dart.dev/tutorials/language/streams):

``` dart
void main() {

  group('Favorite Bloc', () {
    FavoriteBloc favoriteBloc;

    setUp((){...}); //Snippet 38

    tearDown((){...}); //Snippet 38
    
    
    test('Initial State is an empty list', () {...}); //Snippet 38

    test('Stream many Events and see if the State is emitted in the correct order', () {
      //Set Up
      Wisdom wisdom1 = Wisdom(id: 1, text: "Back up your pictures", type: "tech");
      Wisdom wisdom2 = Wisdom(id: 2, text: "Wash your ears", type: "Mum's Advice");
      Wisdom wisdom3 = Wisdom(id: 3, text: "Travel while you're young", type: "Grandma's Advice");

      //Testing
      favoriteBloc.dispatch(FavoriteEventAdd(wisdom1));
      favoriteBloc.dispatch(FavoriteEventAdd(wisdom2));
      favoriteBloc.dispatch(FavoriteEventRemove(wisdom1));
      favoriteBloc.dispatch(FavoriteEventAdd(wisdom3));

      //Result
      expect( 
          favoriteBloc.state,
          emitsInOrder([
            List(), //BLoC Library BLoCs emit their initial State on creation.
            List()..add(wisdom1),
            List()..add(wisdom1)..add(wisdom2),
            List()..add(wisdom2),
            List()..add(wisdom2)..add(wisdom3)
          ]));
    });
  });
}
```

*Code Snippet 39: Wisgen Favorite BLoC Tests 2 [\[11\]](https://github.com/Fasust/wisgen)*

In this test, we create three wisdoms and add/remove them from the favorite BLoC by sending the corresponding Events. We use the *emitsInOrder()* *matcher* to tell the framework that we are working with a Stream and looking for a specific set of Events to be emitted in order [\[83\]](https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86). The Flutters test framework also offers many other Stream matchers besides *emitsInOrder()* [\[84\]](https://pub.dev/packages/test#asynchronous-tests):

  - *emits()* matches a single data Event.
  - *emitsError()* matches a single error Event.
  - *emitsDone* matches a single done Event.
  - *emitsAnyOf()* consumes events matching one (or more) of several possible matchers.
  - *emitsInAnyOrder()* works like emitsInOrder(), but it allows the matchers to match in any order.
  - *neverEmits()* matches a Stream that finishes without matching an inner matcher.
  - And more [\[84\]](https://pub.dev/packages/test#asynchronous-tests)

### Mockito

As mentioned before, *Mockito* [\[82\]](https://pub.dev/packages/mockito) can be used to mock dependencies. The BLoC Pattern forces us to make all platform-specific dependencies of our BLoCs injectable [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE). This comes in very handy when testing BLoCs. For example, the wisdom BLoC of Wisgen fetches data from a given Repository. Instead of testing the Wisdom BLoC in combination with its Repository, we can inject a mock Repository into the BLoC. This way we can test one bit of logic at a time. In this example, we use *Mockito* to test if our wisdom BLoC emits new wisdoms after receiving a fetch event:

``` dart
//Creating Mocks using Mockito
class MockRepository extends Mock implements Supplier<Wisdom> {}
class MockBuildContext extends Mock implements BuildContext {}

void main() {
  group('Wisdom Bloc', () {
    WisdomBloc wisdomBloc;
    MockRepository mockRepository;
    MockBuildContext mockBuildContext;

    setUp(() {
      wisdomBloc = WisdomBloc();
      mockRepository = MockRepository();
      mockBuildContext = MockBuildContext();
      //Inject Mock
      wisdomBloc.repository = mockRepository;
    });

    tearDown(() {
      //Run after each test
      wisdomBloc.dispose();
    });

    test('Send Fetch Event and see if it emits correct wisdom', () {
      //Set Up ---
      List<Wisdom> fetchedWisdom = [
        Wisdom(id: 1, text: "Back up your Pictures", type: "tech"),
        Wisdom(id: 2, text: "Wash your ears", type: "Mum's Advice"),
        Wisdom(id: 3, text: "Travel while you're young", type: "Grandma's Advice")
      ];
      
      //Telling the Mock Repo how to behave
      when(mockRepository.fetch(20, mockBuildContext))
        .thenAnswer((_) async => fetchedWisdom);

      List expectedStates = [
        IdleWisdomState(new List()), //BLoC Library BLoCs emit their initial State on creation
        IdleWisdomState(fetchedWisdom)
      ];
    
      //Testing ---
      wisdomBloc.dispatch(FetchEvent(mockBuildContext));

      //Result ---
      expect(wisdomBloc.state, emitsInOrder(expectedStates));
    });
  });
}
```

*Code Snippet 40: Wisgen Wisdom BLoC Tests with Mockito [\[11\]](https://github.com/Fasust/wisgen)*

First, we create our Mock classes. For this test, we need a mock *Supplier-Repository* and a mock *BuildContext* [\[34\]](https://api.flutter.dev/flutter/widgets/BuildContext-class.html). In the *setUp()* function, we initialize our BLoC and our mocks and inject the mock Repository into our BLoC. In the *test()* function, we tell our mock Repository to send a list of three wisdoms when its *fetch()* function is called. Now we can send a fetch event to the BLoC, and check if it emits the correct states in order.

## Equality in Dart

| ⚠ | By default, all comparisons in Dart work based on references and not base on values \[83\], \[85\] |
| - | :------------------------------------------------------------------------------------------------- |

``` dart
Wisdom wisdom1 = Wisdom(id: 1, text: "Back up your Pictures", type: "tech");
Wisdom wisdom2 = Wisdom(id: 1, text: "Back up your Pictures", type: "tech");

print(wisdom1 == wisdom2); //false
```

*Code Snippet 41: Equality in Flutter*

This can be an easy thing to trip over during testing, especially when comparing States emitted by BLoCs. Luckily, Felix Angelov released the *Equatable* package in 2019 [\[85\]](https://pub.dev/packages/equatable#-example-tab-). It’s an easy way to overwrite how class equality is handled. If we make a class extend the *Equatable* class, we can set the properties it is compared by. We do this by overwriting its *props* attribute. This is used in Wisgen to make the States of the wisdom BLoC compare based on the wisdom they carry:

``` dart
@immutable
abstract class WisdomState extends Equatable {}

///Broadcasted from [WisdomBloc] on network error.
class WisdomStateError extends WisdomState {
  final Exception exception;
  WisdomStateError(this.exception);

  @override
  List<Object> get props => [exception]; //compare based on exception.
}

///Gives access to current list of [Wisdom]s in the [WisdomBloc].
///
///When the BLoC receives a [WisdomEventFetch] during this State, 
///it fetches more [Wisdom] from it [Supplier]. 
///When done it emits a new [IdleSate] with more [Wisdom].
class WisdomStateIdle extends WisdomState {
  final List<Wisdom> wisdoms;
  WisdomStateIdle(this.wisdoms);

  @override
  List<Object> get props => wisdoms; //compare based on wisdoms.
}
```

*Code Snippet 42: Wisgen Wisdom States with Equatable [\[11\]](https://github.com/Fasust/wisgen)*

If we wouldn’t use Equatable, the test form snippet 40 could not functions properly, as two states carrying the same wisdom would still be considered different by the test framework.

| 🕐 | TLDR | If you don’t want your classes to be compared base on their reference, use the Equatable package [\[85\]](https://pub.dev/packages/equatable#-example-tab-) |
| - | ---- | :---------------------------------------------------------------------------------------------------------------------------------------------------------- |

# 400-Conventions

## Introduction

I want to start this chapter with a great quote from Dart’s official style guide:

> “A surprisingly important part of good code is good style. Consistent naming, ordering, and formatting helps code that is the same look the same.” [\[86\]](https://dart.dev/guides/language/effective-dart)

This chapter will teach you some of the current best practices, conventions, and tips when writing Dart code [\[3\]](https://dart.dev/) and Flutter applications [\[1\]](https://flutter.dev/) in general. That being said, the Dart team has published its own comprehensive guide [\[86\]](https://dart.dev/guides/language/effective-dart) on writing effective Dart. I will be highlighting some of the information in that guide here, but I will mainly be focusing on the aspects that are unique to Dart and might be a bit counter-intuitive when coming from other languages or frameworks.

## Naming Conventions

There are three types of naming schemes in Dart. The following table is a summary of when to use which of those schemes:

| Naming Scheme                  | When to use                                                                                               |
| :----------------------------- | :-------------------------------------------------------------------------------------------------------- |
| *lowercase\_with\_underscores* | libraries, packages, directories, source files, and import prefixes: `import 'package:js/js.dart' as js;` |
| *UpperCamelCase*               | classes, enums, type definitions, and type parameters                                                     |
| *lowerCamelCase*               | anything else: Class members, top-level definitions, variables, parameters, **constants**                 |

*Table 2: Naming Convention [\[86\]](https://dart.dev/guides/language/effective-dart)*

There are two things I want to highlight about constant values in Dart:

Firstly, the Dart style guide discourages the use of all uppercase or *SCREENING\_CAPS*. In most other languages all uppercase is used for constant values. The Dart team argues that during development you often end up changing constant variables to no longer be constant. Using all uppercase for constant values thus leads to a lot of renaming. So the convention in Dart is to use the same scheme for every variable.

Secondly, the official style guide forbids the use of prefixes like “k” for constants or any other variation of Hungarian Notation [\[87\]](https://en.wikipedia.org/w/index.php?title=Hungarian_notation&oldid=903388598). They argue that we are now able to see the type, scope, mutability, and other properties of our variables through our IDEs and/or frameworks, and we no longer need to embed such information into the names of our variables. It is interesting to note that the official Flutter repository uses and encourages the use of a “k” prefix for constants in their style guide [\[88\]](https://github.com/flutter/flutter/wiki/Style-guide-for-Flutter-repo). So I would argue that either approach is fine as long as you are consistent.

A few additional things to note about naming conventions in Dart [\[86\]](https://dart.dev/guides/language/effective-dart):

  - A leading "\_" is reserved to define a private scope, so you can’t use it for other purposes than that.
  - Only capitalize the first letter of an abbreviation For Example: `ApiSupplier`
  - Whenever naming anything, ask your self: “Does each word in that name tell me something critical or prevent a name collision?”, If not, shorten it.
  - The last word in class- or variable-name should always be the most descriptive of what it is: `PageCount & DataSink` are better then `NumberOfPage & DataIn`

## Doc Comments

In the snippets up until now, you might have noticed the use of `///` for comments above classes, functions, and members. In Dart triple-dash or “Doc Comments” are a replacement for the classical `/** ... */` block comment from other languages. The Dart team argues, that Doc Comments don’t take up two additional lines when using them as a block comment:

``` dart
/**
* Holds one pice of supreme [Wisdom]
*
* [Wisdom.id] is only unique in the scope of its [Wisdom.type].
*/
class Wisdom {...}
```

*Code Snippet 43: Classic block comment (5 lines)*

``` dart
///Holds one pice of supreme [Wisdom]
///
///[Wisdom.id] is only unique in the scope of its [Wisdom.type].
class Wisdom {...}
```

*Code Snippet 44: Triple-Dash block comment (3 lines)*

Whether you agree with that reasoning or not. You should definitely use them because they can be used to auto-generate a documentation for your project with the Dartdoc tool [\[89\]](https://github.com/dart-lang/dartdoc) and they are shown as tooltips in your IDE:

![Wisgen wisdom tooltip](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisdom-tool-tip.png)

*Figure 26: Wisgen wisdom tooltip [\[11\]](https://github.com/Fasust/wisgen)*

So in short: Use `//` for inline comments, or to explain some specific code within a function. Use `///` to document the top-level behavior of classes, variables, and function.
Some additional things to note about Doc Comments in Dart are [\[86\]](https://dart.dev/guides/language/effective-dart):

  - They should always start with a one sentence description of what the commented thing **dose**. Preferably starting with a present tense, verbs in third person like *supplies*, *holds* or *models*.
  - That initial line should be followed by one empty line to make it stand out.
  - Highlight relevant classes, functions or members by surrounding them with *\[…\]*.
      - They will be linked in the auto-generated docs
  - Markdown [\[90\]](https://daringfireball.net/projects/markdown/) is supported for Doc Comments, so consider adding code snippets as examples.
  - Don’t document information that is already obvious by class name and parameter:

<!-- end list -->

``` dart
///Adds the [int] values of [Adder.a] and [Adder.b] together.
Adder {
  int a;
  int b;
  Adder(this.a, this.b);
  ...
}
```

*Code Snippet 45: Redundant Doc Comment*

## Bracket Hell

One thing you might have already encountered when building an app with Flutter is how easily you end up with a very deeply needed build method that might look a little something like this:

``` dart
...
                          },
                        ),
                      );
                    }),
                  );
                }),
              ),
            );
          },
        ),
      ),
    );
  }
}
...
```

*Code Snippet 46: Flutter Gallery App [\[91\]](https://github.com/flutter/flutter/blob/master/examples/flutter_gallery/lib/gallery/home.dart)*

This phenomenon is known as “Bracket Hell” in the Flutter community \[92\]–\[94\]. And to a degree, this is just what Flutter code looks like. Snippet 46 is from one of Flutter’s official example projects. But we can still try to minimize the problem if we …

| ⚠ | Extract any *distinct enough* Widget into its own class [\[92\]](https://iirokrankka.com/2018/06/18/putting-build-methods-on-a-diet/) |
| - | :------------------------------------------------------------------------------------------------------------------------------------ |

and

| ⚠ | Extract any *callback* into its own function [\[92\]](https://iirokrankka.com/2018/06/18/putting-build-methods-on-a-diet/) |
| - | :------------------------------------------------------------------------------------------------------------------------- |

Let’s look at an example. This is what the *WisdomCard* in Wisgen would look like with one build method. You don’t need to read it all, just look at the top-level *form* of the code:

``` dart
///Displays a given [Wisdom].
///
///Images are loaded from the given [Wisdom.imgUrl] once and then cached.
///All [Wisdom]s displayed in a [WisdomCard] *have* to contain an imgUrl.
///The like button subscribes to the global [FavoriteBLoC] to change its appearance
///based on on the [FavoriteBLoC]s current State.
///The button also publishes [FavoriteEventAdd]/[FavoriteEventRemove] to 
///the [FavoriteBLoC] when it is pressed.
class WisdomCard extends StatelessWidget {
  static const double _cardElevation = 2;
  static const double _cardBorderRadius = 7;
  static const double _imageHeight = 300;
  static const double _smallPadding = 4;
  static const double _largePadding = 8;

  final Wisdom _wisdom;

  WisdomCard(this._wisdom);

  @override
  Widget build(BuildContext context) {
    return Card(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(_cardBorderRadius),
      ),
      clipBehavior: Clip.antiAlias,
      elevation: _cardElevation,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          CachedNetworkImage(
              imageUrl: _wisdom.imgUrl,
              fit: BoxFit.cover,
              height: _imageHeight,
              errorWidget: (context, url, error) => Container(
                    child: Icon(Icons.error),
                    height: _imageHeight,
                  ),
              placeholder: (context, url) => Container(
                    alignment: Alignment(0.0, 0.0),
                    height: _imageHeight,
                    child: LoadingSpinner(),
                  )),
          Container(
            padding: EdgeInsets.only(top: _largePadding, bottom: _largePadding),
            child: Row(
              children: <Widget>[
                Expanded(
                    flex: 5,
                    child: ListTile(
                      title: Text(_wisdom.text),
                      subtitle: Container(
                          padding: EdgeInsets.only(top: _smallPadding),
                          child: Text(_wisdom.type + ' #' + '${_wisdom.id}',
                              textAlign: TextAlign.left)),
                    )),
                Expanded(
                  flex: 1,
                  child: IconButton(
                    icon: Icon(Icons.share),
                    color: Colors.grey,
                    onPressed: () {
                      Share.share(_wisdom.shareAsString());
                    },
                  ),
                ),
                Expanded(
                  flex: 1,
                  //This is where we Subscribe to the FavoriteBLoC
                  child: BlocBuilder<FavoriteBloc, List<Wisdom>>(
                    builder: (context, favorites) => IconButton(
                      icon: Icon(favorites.contains(_wisdom)
                          ? Icons.favorite
                          : Icons.favorite_border),
                      color: favorites.contains(_wisdom)
                          ? Colors.red
                          : Colors.grey,
                      onPressed: () {
                        final FavoriteBloc favoriteBloc =
                            BlocProvider.of<FavoriteBloc>(context);

                        if (favorites.contains(_wisdom)) {
                          favoriteBloc.dispatch(FavoriteEventRemove(_wisdom));
                        } else {
                          favoriteBloc.dispatch(FavoriteEventAdd(_wisdom));
                        }
                      },
                      padding: EdgeInsets.only(right: _smallPadding),
                    ),
                  ),
                )
              ],
            ),
          ),
        ],
      ),
    );
  }
}
```

*Code Snippet 47: Wisgen WisdomCard in one Widget [\[11\]](https://github.com/Fasust/wisgen)*

<img src="https://github.com/devonfw-forge/devonfw4flutter/wiki//images/wisgen-card.png" height="350" alt="Wisgen Wisdom Card">

*Figure 27: Wisgen Wisdom Card [\[11\]](https://github.com/Fasust/wisgen)*

And this is what it looks like if we extract the callback function and split the Widget [\[32\]](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html) into Card, Image, Content and LikeButton:

``` dart
///Displays a given [Wisdom].
///
///Images are loaded from the given [Wisdom.imgUrl] once and then cached.
///All [Wisdom]s displayed in a [WisdomCard] *have* to contain an imgUrl.
///The like button subscribes to the global [FavoriteBLoC] to change its appearance
///based on on the [FavoriteBLoC]s current State.
///The button also publishes [FavoriteEventAdd]/[FavoriteEventRemove] to 
///the [FavoriteBLoC] when it is pressed.
class WisdomCard extends StatelessWidget {
  static const double _cardElevation = 2;
  static const double _cardBorderRadius = 7;

  final Wisdom _wisdom;

  WisdomCard(this._wisdom);

  @override
  Widget build(BuildContext context) {
    return Card(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(_cardBorderRadius),
      ),
      clipBehavior: Clip.antiAlias,
      elevation: _cardElevation,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          _Image(_wisdom.imgUrl),
          _Content(_wisdom),
        ],
      ),
    );
  }
}

///[CachedNetworkImage] with formating and loading animation.
class _Image extends StatelessWidget {
  static const double _imageHeight = 300;
  const _Image(this._url);

  final String _url;

  @override
  Widget build(BuildContext context) {
    return CachedNetworkImage(
      imageUrl: _url,
      fit: BoxFit.cover,
      height: _imageHeight,
      errorWidget: (context, url, error) => Container(
        child: Icon(Icons.error),
        height: _imageHeight,
      ),
      placeholder: (context, url) => Container(
          alignment: Alignment(0.0, 0.0),
          height: _imageHeight,
          child: LoadingSpinner(),
      )
    );
  }
}

///Displays [Wisdom.text], [Wisdom.type], [Wisdom.id] and a [_LikeButton].
class _Content extends StatelessWidget {
  static const double _smallPadding = 4;
  static const double _largePadding = 8;
  final Wisdom _wisdom;

  const _Content(this._wisdom);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(top: _largePadding, bottom: _largePadding),
      child: Row(
        children: <Widget>[
          Expanded(
              flex: 5,
              child: ListTile(
                title: Text(_wisdom.text),
                subtitle: Container(
                    padding: EdgeInsets.only(top: _smallPadding),
                    child: Text(_wisdom.type + ' #' + '${_wisdom.id}',
                        textAlign: TextAlign.left)),
              )),
          Expanded(
            flex: 1,
            child: IconButton(
              icon: Icon(Icons.share),
              color: Colors.grey,
              onPressed: () {
                Share.share(_wisdom.shareAsString());
              },
            ),
          ),
          _LikeButton(wisdom: _wisdom, smallPadding: _smallPadding)
        ],
      ),
    );
  }

}

///Displays if a given [Wisdom] is a favorite and gives the option 
///to change that fact.
class _LikeButton extends StatelessWidget {
  const _LikeButton({
    Key key,
    @required Wisdom wisdom,
    @required double smallPadding,
  }) : _wisdom = wisdom, _smallPadding = smallPadding, super(key: key);

  final Wisdom _wisdom;
  final double _smallPadding;

  @override
  Widget build(BuildContext context) {
    return Expanded(
      flex: 1,
      //This is where we subscribe to the FavoriteBLoC
      child: BlocBuilder<FavoriteBloc, List<Wisdom>>(
        builder: (context, favorites) => IconButton(
          icon: Icon(favorites.contains(_wisdom)
              ? Icons.favorite
              : Icons.favorite_border),
          color: favorites.contains(_wisdom) ? Colors.red : Colors.grey,
          onPressed: () {_onLike(context,favorites);},
          padding: EdgeInsets.only(right: _smallPadding),
        ),
      ),
    );
  }

  ///Figures out if a [Wisdom] is already liked or not and 
  ///then sends the corresponding Event to the [FavoriteBloc].
  void _onLike(BuildContext context, List<Wisdom> favorites) {
    final FavoriteBloc favoriteBloc = BlocProvider.of<FavoriteBloc>(context);

    if (favorites.contains(_wisdom)) {
      favoriteBloc.dispatch(FavoriteEventRemove(_wisdom));
    } else {
      favoriteBloc.dispatch(FavoriteEventAdd(_wisdom));
    }
  }
}
```

*Code Snippet 48: Wisgen Wisdom Card in four Widgets and with an extracted callback [\[11\]](https://github.com/Fasust/wisgen)*

As you can see, splitting your code into multiple smaller Widgets does lead to a lot more boilerplate. But it has both readability and performance advantages \[33\], \[92\]. Extracting Widgets into private functions removes the boilerplate, but also has no performance advantages.

## Directory Structure

As of the writing of this guide, there is not really any agreement or best practice regarding directory structure in the Flutter community. The closest thing I could find was a popular Blog post by Sagar Suri on Medium in 2019 [\[71\]](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1). My recommendation would be very close to his. One directory per layer plus one directory for *model* classes, which are domain-specific entities like *user* or *wisdom*:

    lib
    |
    ├── blocs
    ├── data
    ├── models
    ├── repositories
    ├── ui
    |   ├── pages
    |   |   ├── home_page.dart
    |   |   └── ...
    |   └── widgets
    |       ├── image_card.dart
    |       └── ...
    └── main.dart

*Figure 28: Possible Project Directory Structure*

I would also recommend splitting up the UI directory into pages and widgets. This way you have the highest level of your interface in one place. Suri combines the repository- and data-directory into one, this also a perfectly valid option.

# 500-Conclusion

## Introduction

So, you’ve made it. This is the final chapter of the guide. I will use this chapter to reflect on the guide, evaluate its strengths and weaknesses, and highlight how I and this project will move foreword from here on out. I will also take this opportunity to share my personal opinion of the current state of the Flutter Framework [\[1\]](https://flutter.dev/). That being said, the guide has already been quite opinionated up until now.

## Evaluation of the Guide

I am pretty happy with how the guide turned out. I managed to find a diverse range of sources for most of the topics I wanted to cover. I kept close to the *informal*, “*from developer for developer*”-style of the original Angular guide [\[10\]](https://github.com/devonfw/devon4ng) and I think it reads pretty well. The guide did end up being a bit longer then I planned (\~50 A4 pages without figures and references). If I would write it again, I would choose a more narrow and clear scope for what the guide should include. For example, I might have excluded or shortened chapter [1 The Flutter Framework](https://github.com/devonfw-forge/devonfw4flutter/wiki/100-The-Flutter-Framework) and instead focused even more on chapter [2 Architecting a Flutter App](https://github.com/devonfw-forge/devonfw4flutter/wiki/200-Architecting-a-Flutter-App). I would have also liked to include more scientific sources on Flutter. But as it is still such a new Framework, next to no scientific research has so far been conducted on it. As of the writing of this guide, there has not been a single publication on the Flutter Framework through IEEE [\[95\]](https://ieeexplore.ieee.org/Xplore/home.jsp) or ACM [\[96\]](https://dl.acm.org/).

All in all, I think I fulfilled the goal I set myself when starting this guide. To bridge the gap between the basics of Flutter and clean, structured Flutter development. The result is a resource I would have liked to have when bridging that gap myself.

## Future of the Guide and Next Steps

This guide was commissioned by Capgemini Cologne [\[97\]](https://www.capgemini.com/us-en/) as part of their DevonFw [\[9\]](https://devonfw.com/index.html) open-source initaive. It is supposed to aid Capgemini’s mobile developers when building large-scale application using the Flutter Framework. It will also be the basis for the Bachelor Thesis I am starting this November. In my Thesis, I will create and document a large scale Flutter application for DevonFw. It is supposed to be an example project that other developers can use as a guideline for creating their own large scale Flutter applications. More specifically, DevonFw has used the “My Thai Star” app [\[98\]](https://github.com/devonfw/my-thai-star) as a reference project for Angular [\[69\]](https://angular.io/). I will create a Flutter version of it.

### Suggestion for Future Works

One thing I and peers in the mobile development community [\[99\]](http://doi.acm.org/10.1145/3241739) would like to see is more scientific research on the Flutter Framework: Lagre scale benchmarking, usability tests, scalability tests, architecture evaluation and so on.

## My Thoughts on Flutter

I am guessing you could already pick up on my position regarding Flutter in the previous chapters. I am a Fan. The applications it produces run remarkably smooth and performance is usually one of the main drawbacks of cross-platform frameworks \[100\], \[101\]. The trade-off used to be *less development cost* for *less performance*. With Flutter this is no longer the case.

I was originally not a fan of how State Management [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) in Flutter is handled. I am coming from native Android [\[16\]](https://developer.android.com/) development and the switch from imperative to declarative thinking took a little while for me. I now understand that the design of Flutter has one central goal: Maximize performance [\[102\]](https://flutter.dev/docs/resources/technical-overview). And the way that State is handled is a direct consequence of that goal. State Management also becomes a lot less of an issue once you start following a clearly defined architectural style. I had to build a few iterations of Wisgen [\[11\]](https://github.com/Fasust/wisgen) to figure this out for myself. But once I implemented it with BLoC [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE), I began to understand how Flutter could work in a large scale application.

Another great thing I noticed during the writing of this guide is how open the Flutter Team is towards the community. The Framework is completely open-source and many features have been implemented by developers outside of Google. The Flutter team is also very active on social media, with its own weekly podcast and Youtube channel [\[103\]](https://www.youtube.com/channel/UCwXdFgeE9KYzlDdR7TG9cMw).

One thing I don’t like about Flutter is how easy it is to write ugly code. I already touched on this topic in [the last chapter](https://github.com/devonfw-forge/devonfw4flutter/wiki/400-Conventions). Having deeply nested code is, to a degree, unavoidable in Flutter. Splitting Widgets into smaller parts helps but adds a lot of boilerplate. I hope there will be a better solution to mitigate this problem in the future.

Another thing I am not a fan of is dependency injection in Flutter. At the moment the only solution is to inject dependencies from the UI into the BLoC. This created an ugly dependency between UI- and Data-Layer. But Google is currently working on a solution for this, so the problem is known and will be addressed [\[104\]](https://github.com/google/inject.dart).

All that being said, I won’t be going back to native app development any time soon.

# 600-References

<div id="refs" class="references">

<div id="ref-flutterdevteamFlutterFramework2018">

\[1\] Flutter Dev Team, *The Flutter Framework*. Google LLC, 2018 \[Online\]. Available: <https://flutter.dev/>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-flutterdevteamWriteYourFirst2018">

\[2\] Flutter Dev Team, “Write your first Flutter app,” 2018. \[Online\]. Available: <https://flutter.dev/docs/get-started/codelab>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-dartteamDartProgrammingLanguage2019">

\[3\] Dart Team, “Dart programming language,” 2019. \[Online\]. Available: <https://dart.dev/>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-oracleJavaJDK1996">

\[4\] Oracle, *Java JDK*. Oracle, 1996 \[Online\]. Available: <https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>. \[Accessed: 26-Sep-2019\]

</div>

<div id="ref-jetbrainsKotlinSDK2017">

\[5\] Jet Brains, *Kotlin SDK*. Jet Brains, 2017 \[Online\]. Available: <https://kotlinlang.org/>. \[Accessed: 26-Sep-2019\]

</div>

<div id="ref-ecmaJavaScriptECMAStandard1997">

\[6\] ECMA, *JavaScript ECMA Standard*. ECMA, 1997 \[Online\]. Available: <https://www.ecma-international.org/publications/standards/Ecma-262.htm>. \[Accessed: 26-Sep-2019\]

</div>

<div id="ref-soaresFlutterAngularDartCode2018">

\[7\] P. Soares, “Flutter / AngularDart – Code sharing, better together,” 25-Jan-2018 \[Online\]. Available: <https://www.youtube.com/watch?v=PLHln7wHgPE>. \[Accessed: 12-Sep-2019\]

</div>

<div id="ref-technicaluniversitycologneTechnicalUniversityCologne2019">

\[8\] Technical University Cologne, “Technical University Cologne,” 2019. \[Online\]. Available: <https://www.th-koeln.de/en/homepage_26.php>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-capgeminiDevonfw2019">

\[9\] Capgemini, “Devonfw,” 2019. \[Online\]. Available: <https://devonfw.com/index.html>. \[Accessed: 13-Nov-2019\]

</div>

<div id="ref-ambuludiDevonFwAngularGuide2019">

\[10\] J. A. Ambuludi, S. J. Linares, and Contributors, “DevonFw Angular Guide,” *GitHub*, 2019. \[Online\]. Available: <https://github.com/devonfw/devon4ng>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-faustWisgen2019">

\[11\] S. Faust, *Wisgen*. Germany, 2019 \[Online\]. Available: <https://github.com/Fasust/wisgen>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-flutterdevteamFlutterState2019">

\[12\] Flutter Dev Team, “Flutter State,” 2019. \[Online\]. Available: <https://flutter.dev/docs/development/data-and-backend/state-mgmt>. \[Accessed: 22-Sep-2019\]

</div>

<div id="ref-flutterdevteamStatefulWidgetClass2018">

\[13\] Flutter Dev Team, “StatefulWidget class,” 2018. \[Online\]. Available: <https://api.flutter.dev/flutter/widgets/StatefulWidget-class.html>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-lelerWhatRevolutionaryFlutter2017">

\[14\] W. Leler, “What’s Revolutionary about Flutter,” *hackernoon*, 2017. \[Online\]. Available: <https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514>. \[Accessed: 22-Sep-2019\]

</div>

<div id="ref-appleIOSSDK2010">

\[15\] Apple, *iOS SDK*. Apple, 2010 \[Online\]. Available: <https://developer.apple.com/ios/>. \[Accessed: 25-Sep-2019\]

</div>

<div id="ref-googlellcAndroidSDK2008">

\[16\] Google LLC, *Android SDK*. Google LLC, 2008 \[Online\]. Available: <https://developer.android.com/>. \[Accessed: 25-Sep-2019\]

</div>

<div id="ref-googlellcHowFlutterDifferent2019">

\[17\] *How is Flutter different for app development*. Google Developers Official Youtube Channel, 2019 \[Online\]. Available: <https://www.youtube.com/watch?v=l-YO9CmaSUM&feature=youtu.be>. \[Accessed: 19-Sep-2019\]

</div>

<div id="ref-stollPlainEnglishWhat2018">

\[18\] S. Stoll, “In plain English: So what the heck is Flutter and why is it a big deal?” *Medium*, 2018. \[Online\]. Available: <https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a>. \[Accessed: 22-Sep-2019\]

</div>

<div id="ref-worldwidewebconsortiumw3cHTML1994">

\[19\] World Wide Web Consortium (W3C), “HTML,” 1994. \[Online\]. Available: <https://www.w3.org/html/>. \[Accessed: 15-Oct-2019\]

</div>

<div id="ref-worldwidewebconsortiumw3cCascadingStyleSheets1994">

\[20\] World Wide Web Consortium (W3C), “Cascading Style Sheets,” 1994. \[Online\]. Available: <https://www.w3.org/Style/CSS/Overview.de.html>. \[Accessed: 15-Oct-2019\]

</div>

<div id="ref-adinugrohoReviewMultiplatformMobile2015">

\[21\] T. Y. Adinugroho, Reina, and J. B. Gautama, “Review of Multi-platform Mobile Application Development Using WebView: Learning Management System on Mobile Platform,” *Procedia Computer Science*, vol. 59, pp. 291–297, Jan. 2015 \[Online\]. Available: <http://www.sciencedirect.com/science/article/pii/S1877050915020979>. \[Accessed: 25-Sep-2019\]

</div>

<div id="ref-facebookReactNativeFramework2015">

\[22\] Facebook, *React Native Framework*. Facebook, 2015 \[Online\]. Available: <https://facebook.github.io/react-native/>. \[Accessed: 22-Sep-2019\]

</div>

<div id="ref-kolPerformanceLimitationsReact2017">

\[23\] T. Kol, “Performance Limitations of React Native and How to Overcome Them,” 2017 \[Online\]. Available: <https://www.youtube.com/watch?v=psZLAHQXRsI>. \[Accessed: 22-Sep-2019\]

</div>

<div id="ref-flutterdevteamFAQFlutter2019">

\[24\] Flutter Dev Team, “FAQ - Flutter,” 2019. \[Online\]. Available: <https://flutter.dev/docs/resources/faq>. \[Accessed: 22-Sep-2019\]

</div>

<div id="ref-flutterdevteamHotReloadFlutter2019">

\[25\] Flutter Dev Team, “Hot reload - Flutter,” 2019. \[Online\]. Available: <https://flutter.dev/docs/development/tools/hot-reload>. \[Accessed: 22-Sep-2019\]

</div>

<div id="ref-mooreDartProductiveFast2019">

\[26\] K. Moore and B. Nystrom, “Dart: Productive, Fast, Multi-Platform - Pick 3,” 09-May-2019 \[Online\]. Available: <https://www.youtube.com/watch?v=J5DQRPRBiFI>. \[Accessed: 04-Sep-2019\]

</div>

<div id="ref-flutterdevteamIntroductionDeclarativeUI2019">

\[27\] Flutter Dev Team, “Introduction to declarative UI,” 2019. \[Online\]. Available: <https://flutter.dev/docs/get-started/flutter-for/declarative>. \[Accessed: 24-Sep-2019\]

</div>

<div id="ref-bezerraDeclarativeProgramming2018">

\[28\] *Declarative programming*. 2018 \[Online\]. Available: <https://www.youtube.com/watch?v=yGh0bjzj4IQ&t=632s>. \[Accessed: 25-Sep-2019\]

</div>

<div id="ref-computerphileHTMLProgrammingLanguage2016">

\[29\] *HTML IS a Programming Language (Imperative vs Declarative)*. University of Nottingham, 2016 \[Online\]. Available: <https://www.youtube.com/watch?v=4A2mWqLUpzw>. \[Accessed: 25-Sep-2019\]

</div>

<div id="ref-flutterdevteamFlutterWidgets2019">

\[30\] Flutter Dev Team, “Flutter Widgets,” 2019. \[Online\]. Available: <https://flutter.dev/docs/development/ui/widgets-intro>. \[Accessed: 25-Sep-2019\]

</div>

<div id="ref-boelensWidgetStateBuildContext2018">

\[31\] D. Boelens, “Widget — State — BuildContext — InheritedWidget,” *Medium*, 2018. \[Online\]. Available: <https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956>. \[Accessed: 23-Sep-2019\]

</div>

<div id="ref-flutterdevteamStatelessWidgetClass2018">

\[32\] Flutter Dev Team, “StatelessWidget class,” 2018. \[Online\]. Available: <https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-dartteamPerformanceBestPractices2018">

\[33\] Dart Team, “Performance best practices,” 2018. \[Online\]. Available: <https://flutter.dev/docs/testing/best-practices>. \[Accessed: 11-Oct-2019\]

</div>

<div id="ref-flutterdevteamBuildContextClass2018">

\[34\] Flutter Dev Team, “BuildContext class,” 2018. \[Online\]. Available: <https://api.flutter.dev/flutter/widgets/BuildContext-class.html>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-googlellcHowCreateStateless2018">

\[35\] *How to Create Stateless Widgets*, vol. Ep. 1. 2018 \[Online\]. Available: <https://www.youtube.com/watch?v=wE7khGHVkYY>. \[Accessed: 23-Sep-2019\]

</div>

<div id="ref-googlellcHowStatefulWidgets2018">

\[36\] *How Stateful Widgets Are Used Best*, vol. Ep. 2. 2018 \[Online\]. Available: <https://www.youtube.com/watch?v=AqCMFXEmf3w>. \[Accessed: 23-Sep-2019\]

</div>

<div id="ref-windmillStatefulWidgetLifecycle2019">

\[37\] E. Windmill and Contributors, “Stateful Widget Lifecycle,” *flutterbyexample*, 2019. \[Online\]. Available: <https://flutterbyexample.com//stateful-widget-lifecycle>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-flutterdevteamInheritedWidgetClass2018">

\[38\] Flutter Dev Team, “InheritedWidget class,” 2018. \[Online\]. Available: <https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-angelovBlocLibraryDart2019">

\[39\] F. Angelov and Contributors, “Bloc library for Dart,” 2019. \[Online\]. Available: <https://felangel.github.io/bloc/#/>. \[Accessed: 12-Sep-2019\]

</div>

<div id="ref-dartteamDartStreams2019">

\[40\] Dart Team, “Dart Streams,” 2019. \[Online\]. Available: <https://dart.dev/tutorials/language/streams>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-dartteamHttpDartPackage2019">

\[41\] Dart Team, “Http | Dart Package,” *Dart packages*, 2019. \[Online\]. Available: <https://pub.dev/packages/http>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-mozillaJavaScriptPromise2016">

\[42\] Mozilla and Contributors, “JavaScript Promise,” *MDN-Web-Dokumentation*, 2016. \[Online\]. Available: <https://developer.mozilla.org/de/docs/Web/JavaScript/Reference/Global_Objects/Promise>. \[Accessed: 15-Oct-2019\]

</div>

<div id="ref-kissAdviceSlipAPI2019">

\[43\] T. Kiss, “Advice Slip API,” 2019. \[Online\]. Available: <https://api.adviceslip.com/>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-googlellcDartFutures2019">

\[44\] *Dart Futures*, vol. Ep. 2. 2019 \[Online\]. Available: <https://www.youtube.com/watch?v=OTS-ap9_aXc>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-googlellcIsolatesEventLoops2019">

\[45\] *Isolates and Event Loops*, vol. Ep. 1. 2019 \[Online\]. Available: <https://www.youtube.com/watch?v=vl_AaCgudcY>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-dartteamAsynchronousProgrammingDart2018">

\[46\] Dart Team, “Asynchronous programming in Dart,” 2018. \[Online\]. Available: <https://dart.dev/codelabs/async-await>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-googlellcAsyncAwait2019">

\[47\] *Async/Await*, vol. Ep. 4. 2019 \[Online\]. Available: <https://www.youtube.com/watch?v=SmTCmDMi4BY>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-boelensFlutterReactiveProgramming2018">

\[48\] D. Boelens, “Flutter - Reactive Programming - Streams - BLoC,” *Didier Boelens*, 2018. \[Online\]. Available: <https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/>. \[Accessed: 12-Sep-2019\]

</div>

<div id="ref-googlellcDartStreams2019">

\[49\] *Dart Streams*, vol. Ep. 3. 2019 \[Online\]. Available: <https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-flutterdevteamScrollControllerClass2018">

\[50\] Flutter Dev Team, “ScrollController class,” 2018. \[Online\]. Available: <https://api.flutter.dev/flutter/widgets/ScrollController-class.html>. \[Accessed: 03-Oct-2019\]

</div>

<div id="ref-angelovFlutterInfiniteList2019">

\[51\] F. Angelov and Contributors, “Flutter Infinite List BLoC Tutorial,” 2019. \[Online\]. Available: <https://felangel.github.io/bloc/#/flutterinfinitelisttutorial>. \[Accessed: 13-Sep-2019\]

</div>

<div id="ref-flutterdevteamStreamBuilderClass2018">

\[52\] Flutter Dev Team, “StreamBuilder class,” 2018. \[Online\]. Available: <https://api.flutter.dev/flutter/widgets/StreamBuilder-class.html>. \[Accessed: 03-Oct-2019\]

</div>

<div id="ref-sullivanTechnicalDebtStreams2018">

\[53\] *Technical Debt and Streams/BLoC*, vol. 4. 2018 \[Online\]. Available: <https://www.youtube.com/watch?v=fahC3ky_zW0>. \[Accessed: 09-Sep-2019\]

</div>

<div id="ref-lecuonaJSONDartConverter2019">

\[54\] J. Lecuona, *JSON to Dart converter*. Buenos Aires, Argentina, 2019 \[Online\]. Available: <https://javiercbk.github.io/json_to_dart/>. \[Accessed: 01-Oct-2019\]

</div>

<div id="ref-wikipediaStateManagement2019">

\[55\] Wikipedia, “State management,” *Wikipedia*. 04-Sep-2019 \[Online\]. Available: <https://en.wikipedia.org/w/index.php?title=State_management&oldid=914027862>. \[Accessed: 15-Oct-2019\]

</div>

<div id="ref-isoISOIECIEEE2011">

\[56\] ISO, “ISO/IEC/IEEE 42010:2011 Systems and software engineering — Architecture description.” ISO, 2011 \[Online\]. Available: <http://www.iso.org/cms/render/live/en/sites/isoorg/contents/data/standard/05/05/50508.html>. \[Accessed: 15-Oct-2019\]

</div>

<div id="ref-eganFlutterArchitectureSamples2017">

\[57\] B. Egan and Contributors, “Flutter Architecture Samples,” *fluttersamples*, 2017. \[Online\]. Available: <https://fluttersamples.com/>. \[Accessed: 28-Aug-2019\]

</div>

<div id="ref-rousseletProviderFlutterPackage2018">

\[58\] R. Rousselet and Flutter Dev Team, “Provider | Flutter Package,” *Dart packages*, 2018. \[Online\]. Available: <https://pub.dev/packages/provider>. \[Accessed: 06-Oct-2019\]

</div>

<div id="ref-hracekPragmaticStateManagement2019">

\[59\] *Pragmatic State Management Using Provider*, vol. 24. 2019 \[Online\]. Available: <https://www.youtube.com/watch?v=HrBiNHEqSYU>. \[Accessed: 09-Sep-2019\]

</div>

<div id="ref-sullivanPragmaticStateManagement2019">

\[60\] M. Sullivan and F. Hracek, “Pragmatic State Management in Flutter,” 09-May-2019 \[Online\]. Available: <https://www.youtube.com/watch?v=d_m5csmrf7I>. \[Accessed: 28-Aug-2019\]

</div>

<div id="ref-eganKeepItSimple2018">

\[61\] B. Egan, “Keep it Simple, State: Architecture for Flutter Apps,” 25-Jan-2018 \[Online\]. Available: <https://www.youtube.com/watch?v=zKXz3pUkw9A>. \[Accessed: 28-Aug-2019\]

</div>

<div id="ref-flutterdevteamChangeNotifierClass2018">

\[62\] Flutter Dev Team, “ChangeNotifier class,” 2018. \[Online\]. Available: <https://api.flutter.dev/flutter/foundation/ChangeNotifier-class.html>. \[Accessed: 06-Oct-2019\]

</div>

<div id="ref-boelensFlutterBLoCScopedModel2019">

\[63\] D. Boelens, “Flutter - BLoC - ScopedModel - Redux - Comparison,” *Didier Boelens*, 2019. \[Online\]. Available: <https://www.didierboelens.com/2019/04/bloc---scopedmodel---redux---comparison/>. \[Accessed: 09-Sep-2019\]

</div>

<div id="ref-savjolovsFlutterAppArchitecture2019">

\[64\] V. Savjolovs, “Flutter app architecture 101: Vanilla, Scoped Model, BLoC,” *Medium*, 2019. \[Online\]. Available: <https://medium.com/flutter-community/flutter-app-architecture-101-vanilla-scoped-model-bloc-7eff7b2baf7e>. \[Accessed: 28-Aug-2019\]

</div>

<div id="ref-abramovRedux2015">

\[65\] D. Abramov, “Redux,” 2015. \[Online\]. Available: <https://redux.js.org/>. \[Accessed: 06-Oct-2019\]

</div>

<div id="ref-eganFlutterReduxPackage2017">

\[66\] B. Egan, “Flutter Redux Package,” *Dart packages*, 2017. \[Online\]. Available: <https://pub.dev/packages/flutter_redux>. \[Accessed: 06-Oct-2019\]

</div>

<div id="ref-doughtieArchitectingReactiveFlutter2017">

\[67\] G. Doughtie, “Architecting the Reactive Flutter App,” 20-Nov-2017 \[Online\]. Available: <https://www.youtube.com/watch?v=n_5JULTrstU&feature=youtu.be>. \[Accessed: 29-Aug-2019\]

</div>

<div id="ref-abramovThreePrinciplesRedux2015">

\[68\] D. Abramov, “Three Principles of Redux,” 2015. \[Online\]. Available: <https://redux.js.org/>. \[Accessed: 08-Oct-2019\]

</div>

<div id="ref-googlellcAngular2016">

\[69\] Google LLC, “Angular,” 2016. \[Online\]. Available: <https://angular.io/>. \[Accessed: 06-Oct-2019\]

</div>

<div id="ref-sullivanBuildReactiveMobile2018">

\[70\] M. Sullivan and F. Hracek, “Build reactive mobile apps with Flutter,” 10-May-2018 \[Online\]. Available: <https://www.youtube.com/watch?v=RS36gBEp8OI>. \[Accessed: 04-Sep-2019\]

</div>

<div id="ref-suriArchitectYourFlutter2019">

\[71\] S. Suri, “Architect your Flutter project using BLOC pattern,” *Medium*, 2019. \[Online\]. Available: <https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1>. \[Accessed: 09-Sep-2019\]

</div>

<div id="ref-googlellcAngularDart2018">

\[72\] Google LLC, “AngularDart,” 2018. \[Online\]. Available: <https://angulardart.dev/>. \[Accessed: 07-Oct-2019\]

</div>

<div id="ref-flutterdevteamStateManagementRecommendations2019">

\[73\] Flutter Dev Team, “State Management Recommendations,” 2019. \[Online\]. Available: <https://flutter.dev/docs/development/data-and-backend/state-mgmt/options>. \[Accessed: 25-Nov-2019\]

</div>

<div id="ref-bizzottoWidgetAsyncBlocServicePracticalArchitecture2019">

\[74\] A. Bizzotto, “Widget-Async-Bloc-Service: A Practical Architecture for Flutter Apps,” *Medium*, 2019. \[Online\]. Available: <https://medium.com/coding-with-flutter/widget-async-bloc-service-a-practical-architecture-for-flutter-apps-250a28f9251b>. \[Accessed: 10-Oct-2019\]

</div>

<div id="ref-garlanIntroductionSoftwareArchitecture1994">

\[75\] D. Garlan and M. Shaw, “An Introduction to Software Architecture,” Carnegie Mellon University, Pittsburgh, PA, USA, 1994 \[Online\]. Available: <https://dl.acm.org/citation.cfm?id=865128>

</div>

<div id="ref-martinPrinciplesCleanArchitecture2015">

\[76\] B. Martin, “The Principles of Clean Architecture,” 2015 \[Online\]. Available: <https://www.youtube.com/watch?v=o_TH-Y78tt4>. \[Accessed: 13-Sep-2019\]

</div>

<div id="ref-googlellcSharedPreferences2011">

\[77\] Google LLC, “SharedPreferences,” *Android Developers*, 2011. \[Online\]. Available: <https://developer.android.com/reference/android/content/SharedPreferences>. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-georgeInitialInvestigationTest2003">

\[78\] B. George and L. Williams, “An Initial Investigation of Test Driven Development in Industry,” in *Proceedings of the 2003 ACM Symposium on Applied Computing*, Melbourne, Florida, 2003, pp. 1135–1139 \[Online\]. Available: <http://doi.acm.org/10.1145/952532.952753>. \[Accessed: 10-Oct-2019\]

</div>

<div id="ref-dartteamTestDartPackage2019">

\[79\] Dart Team, “Test | Dart Package,” *Dart packages*, 2019. \[Online\]. Available: <https://pub.dev/packages/test>. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-hracekTestingFlutterApps2019">

\[80\] *Testing Flutter Apps - Making Sure Your Code Works*, vol. Ep. 21. 2019 \[Online\]. Available: <https://www.youtube.com/watch?v=bj-oMYyLZEY&>. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-flutterdevteamTestingFlutterApps2018">

\[81\] Flutter Dev Team, “Testing Flutter apps,” 2018. \[Online\]. Available: <https://flutter.dev/docs/testing>. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-fibulwinterMockitoDartPackage2019">

\[82\] D. Fibulwinter and Dart Team, “Mockito | Dart Package,” *Dart packages*, 2019. \[Online\]. Available: <https://pub.dev/packages/mockito>. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-angelovUnitTestingBloc2019">

\[83\] F. Angelov, “Unit Testing with ‘Bloc’,” *Medium*, 2019. \[Online\]. Available: <https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86>. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-dartteamAsynchronoustestsTestDart2019">

\[84\] Dart Team, “Asynchronous-tests with the test Dart Package,” *Dart packages*, 2019. \[Online\]. Available: <https://pub.dev/packages/test#asynchronous-tests>. \[Accessed: 11-Oct-2019\]

</div>

<div id="ref-angelovEquatableDartPackage2019">

\[85\] F. Angelov, “Equatable | Dart Package,” *Dart packages*, 2019. \[Online\]. Available: <https://pub.dev/packages/equatable#-example-tab->. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-dartteamEffectiveDart2019">

\[86\] Dart Team, “Effective Dart,” 2019. \[Online\]. Available: <https://dart.dev/guides/language/effective-dart>. \[Accessed: 28-Aug-2019\]

</div>

<div id="ref-wikipediaHungarianNotation2019">

\[87\] Wikipedia, “Hungarian notation,” *Wikipedia*. 25-Jun-2019 \[Online\]. Available: <https://en.wikipedia.org/w/index.php?title=Hungarian_notation&oldid=903388598>. \[Accessed: 12-Oct-2019\]

</div>

<div id="ref-flutterdevteamStyleGuideFlutter2018">

\[88\] Flutter Dev Team, “Style Guide for Flutter repo,” *GitHub*, 2018. \[Online\]. Available: <https://github.com/flutter/flutter/wiki/Style-guide-for-Flutter-repo>. \[Accessed: 11-Oct-2019\]

</div>

<div id="ref-dartteamDartdocTool2019">

\[89\] Dart Team, *Dartdoc tool*. Dart, 2019 \[Online\]. Available: <https://github.com/dart-lang/dartdoc>. \[Accessed: 12-Oct-2019\]

</div>

<div id="ref-gruberMarkdown2004">

\[90\] J. Gruber and A. Swartz, “Markdown,” 2004. \[Online\]. Available: <https://daringfireball.net/projects/markdown/>. \[Accessed: 12-Oct-2019\]

</div>

<div id="ref-flutterdevteamOfficialFlutterExample2019">

\[91\] Flutter Dev Team, “Official Flutter Example,” *GitHub*, 2019. \[Online\]. Available: <https://github.com/flutter/flutter/blob/master/examples/flutter_gallery/lib/gallery/home.dart>. \[Accessed: 12-Oct-2019\]

</div>

<div id="ref-krankkaPuttingBuildMethods2018">

\[92\] I. Krankka, “Putting build methods on a diet,” *iirokrankka.com*, 2018. \[Online\]. Available: <https://iirokrankka.com/2018/06/18/putting-build-methods-on-a-diet/>. \[Accessed: 28-Aug-2019\]

</div>

<div id="ref-cluelessAmAlsoCreating2018">

\[93\] Clueless, “I am also creating my application with Flutter.” *Medium*. \[Online\]. Available: <https://medium.com/@blusea231/i-am-also-creating-my-application-with-flutter-8f25db889312>. \[Accessed: 12-Oct-2019\]

</div>

<div id="ref-u/the_aceixFlutterDevFlutterCode2018">

\[94\] u/the\_aceix, “R/FlutterDev - Is Flutter code style ugly ?” *reddit*, 2018. \[Online\]. Available: <https://www.reddit.com/r/FlutterDev/comments/9ei0dn/is_flutter_code_style_ugly/>. \[Accessed: 12-Oct-2019\]

</div>

<div id="ref-ieeeIEEEXploreDigital1963">

\[95\] IEEE, “IEEE Xplore Digital Library,” 1963. \[Online\]. Available: <https://ieeexplore.ieee.org/Xplore/home.jsp>. \[Accessed: 20-Sep-2018\]

</div>

<div id="ref-acmACMDigitalLibrary1947">

\[96\] ACM, “ACM Digital Library,” 1947. \[Online\]. Available: <https://dl.acm.org/>. \[Accessed: 20-Sep-2018\]

</div>

<div id="ref-capgeminiCapgeminiHomePage2019">

\[97\] Capgemini, “Capgemini - Home Page,” 2019. \[Online\]. Available: <https://www.capgemini.com/us-en/>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-linaresMyThaiStar2019">

\[98\] S. J. Linares, D. R. Gonzalez, and Contributors, *My Thai Star*. devonfw, 2019 \[Online\]. Available: <https://github.com/devonfw/my-thai-star>. \[Accessed: 13-Oct-2019\]

</div>

<div id="ref-biorn-hansenSurveyTaxonomyCore2018">

\[99\] A. Biørn-Hansen, T.-M. Grønli, and G. Ghinea, “A Survey and Taxonomy of Core Concepts and Research Challenges in Cross-Platform Mobile Development,” *ACM Comput. Surv.*, vol. 51, no. 5, pp. 108:1–108:34, Nov. 2018 \[Online\]. Available: <http://doi.acm.org/10.1145/3241739>. \[Accessed: 28-Aug-2019\]

</div>

<div id="ref-sommerEvaluationCrossplatformFrameworks2013">

\[100\] A. Sommer and S. Krusche, *Evaluation of cross-platform frameworks for mobile applications*. Gesellschaft für Informatik e.V., 2013 \[Online\]. Available: <http://dl.gi.de/handle/20.500.12116/17386>. \[Accessed: 13-Oct-2019\]

</div>

<div id="ref-ebonePerformanceEvaluationCrossPlatform2018">

\[101\] A. Ebone, Y. Tan, and X. Jia, “A Performance Evaluation of Cross-Platform Mobile Application Development Approaches,” in *2018 IEEE/ACM 5th International Conference on Mobile Software Engineering and Systems (MOBILESoft)*, 2018, pp. 92–93 \[Online\]. Available: <https://ieeexplore.ieee.org/document/8543442>

</div>

<div id="ref-flutterdevteamWhatFlutter2018">

\[102\] Flutter Dev Team, “What is Flutter,” 2018. \[Online\]. Available: <https://flutter.dev/docs/resources/technical-overview>. \[Accessed: 14-Oct-2019\]

</div>

<div id="ref-flutterdevteamFlutterYouTube2019">

\[103\] Flutter Dev Team, “Flutter - YouTube,” 2019. \[Online\]. Available: <https://www.youtube.com/channel/UCwXdFgeE9KYzlDdR7TG9cMw>. \[Accessed: 13-Oct-2019\]

</div>

<div id="ref-googlellcInjectRepo2019">

\[104\] Google LLC, *Inject Repo*. Google, 2019 \[Online\]. Available: <https://github.com/google/inject.dart>. \[Accessed: 13-Oct-2019\]

</div>

</div>
