[//Build GFM]: # (pandoc --wrap=preserve --filter pandoc-citeproc --bibliography=.sources/guide.bib -f markdown -t gfm .raw-text/raw-guide.md -o .raw-text/gfm-guide.md)

[//Build PDF]: # (pandoc --wrap=preserve --filter pandoc-citeproc --bibliography=.sources/guide.bib --pdf-engine=xelatex --variable papersize=a4paper -s .raw-text/raw-guide.md -o paper.pdf)

[//Split GFM]: # (dart .\.tools\splitter.dart .\.raw-text\gfm-guide.md .\)

[intro]: https://github.com/Fasust/flutter-guide/wiki
[framework]: https://github.com/Fasust/flutter-guide/wiki/100-The-Flutter-Framework
[under-hood]: https://github.com/Fasust/flutter-guide/wiki/110-Under-the-Hood
[declarative]: https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively
[tree]: https://github.com/Fasust/flutter-guide/wiki/130-The-Widget-Tree
[async]: https://github.com/Fasust/flutter-guide/wiki/140-Asynchronous-Flutter
[web]: https://github.com/Fasust/flutter-guide/wiki/150-Communication-with-the-Web
[architecture]: https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App
[test]: https://github.com/Fasust/flutter-guide/wiki/300-Testing
[conventions]: https://github.com/Fasust/flutter-guide/wiki/400-Conventions
[conclusion]: https://github.com/Fasust/flutter-guide/wiki/500-Conclusion
[refs]: https://github.com/Fasust/flutter-guide/wiki/600-References

# 000-Introduction

## The Goal of this Guide
This guide aims to bridge the gap between the absolute Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) basics and clean, structured Flutter development. It should bring you from the basics of knowing how to build an app with Flutter to an understanding of how to do it _properly_. Or at least show you one possible way to make large scale Flutter projects clean and manageable.

## Who is this Guide for?
For people with a basic knowledge of the Flutter Framework. I recommend following this tutorial by the Flutter team [[@flutterdevteamWriteYourFirst2018]](https://flutter.dev/docs/get-started/codelab). It will walk you through developing your first flutter application. You should also have a basic understanding of the Dart programming language [[@dartteamDartProgrammingLanguage2019]](https://dart.dev/). No worries, it is very similar to Java [[@oracleJavaJDK1996]](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), Kotlin [[@jetbrainsKotlinSDK2017]](https://kotlinlang.org/) and JavaScript [[@ecmaJavaScriptECMAStandard1997]](https://www.ecma-international.org/publications/standards/Ecma-262.htm). So if you know 1 or 2 of those languages you should be fine. Lastly, for the Architecture chapter, you should have a basic understanding of Data Streams [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams).

## Topics that will be covered 
- A brief introduction to the Flutter Framework in general. How it works _under the hood_ and its underlying structure.
- One possible architecture for your Flutter app and how to implement it (BLoC [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE))
- How to test your app
- Some conventions and best practices for Dart, BLoC and the Flutter Framework
- My personal opinion of the framework

## Creation Context
This guide was written by a student in the Bachelor of Science Program “Computer Science and Media Technology” at Technical University Cologne [[@technicaluniversitycologneTechnicalUniversityCologne2019]](https://www.th-koeln.de/en/homepage_26.php), and it was created for one of the modules in that Bachelor. In addition to this, the guide was written in collaboration with Capgemini Cologne [[@capgeminiCapgeminiHomePage2019]](https://www.capgemini.com/us-en/). Capgemini released a guide on building an application in Angular [[@ambuludiCapgeminiAngularGuide2019]](https://github.com/devonfw/devon4ng) in May of 2019, this guide is meant to be the the flutter version of that.

## Structure
The guide is designed to be read in order, from Chapter 0 (this one) to Chapter 5. Code examples throughout the chapters will mainly be taken from Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen), an example Flutter Application that was specifically built for the purposes of this guide.

## My Sources 
I am basing this guide on a combination of conference talks, blog articles by respected Flutter developers, official documentaions, scientific papers that cover cross-platform mobile development in gerneral and many other sources. All sources used in the guide are listed in the chapter [_References_][refs]. To better understand all the theory, I also developed the Wisgen app [[@faustWisgen2019]](https://github.com/Fasust/wisgen) using the Flutter Framework and the BLoC Pattern [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE).

# 100-The-Flutter-Framework
## Introduction
This Chapter will give you a basic understanding of how the Flutter Framework [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) works as a whole. I will showcase the difference of Flutter to other Cross-Platform approaches and how Flutter works _under the hood_. You will also be introduced to the concepts of _state_ [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) and flutters way of rendering an app as a tree of _widgets_. In addition to this, You will gain an understanding of how Flutter Handels Asynchronous Programming. And Lastly, you will learn how to communicate with the Web within the Flutter Framework.

## Contents of the Chapter
* [Under The Hood][under-hood]
* [Thinking Declaratively][declarative]
* [The Widget Tree][tree]
* [Asynchronous Flutter][async]
* [Communication with the Web][web]

# 110-Under-The-Hood

## Introduction
Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) is a framework for cross-platform native development. But what exactly does that mean? It means that it promises Native App performance while still compiling apps for multiple platforms from a single codebase. The best way to understand how flutter achieves this, is to compare it to other mobile development approaches.

### Full Native Approach
![Native app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/native-rendering.png)

_Figure 1: Native app rendering [[@lelerWhatRevolutionaryFlutter2017]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

The classic way to build a mobile app, would be to write native code for each platform you want to support. I.E. One for IOS [[@appleIOSSDK2010]](https://developer.apple.com/ios/), one for Android [[@googlellcAndroidSDK2008]](https://developer.android.com/) and so on. In this approach your app will be written in a platform specific language and render through platform specific widgets and a platform specific engine. During the development you have direct access to platform specific services and sensors [@googlellcHowFlutterDifferent2019; @stollPlainEnglishWhat2018; @lelerWhatRevolutionaryFlutter2017]. But you will have to build the same app multiple times, which effectively doubles your workload.

### Embedded WebApp Approach
![Embedded Web App rendering](https://github.com/Fasust/flutter-guide/wiki//.images/webview-rendering.png)

_Figure 2: Embedded WebApp rendering [[@lelerWhatRevolutionaryFlutter2017]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

Embedded WebApps where the first approach to cross-platform development. You would simply build your application with HTML, CSS and JavaScript and then have it render through a native WebView[@googlellcHowFlutterDifferent2019; @lelerWhatRevolutionaryFlutter2017]. The Problem here is, that developers are limited to the web technology stack and that communication between the app and native services would always have to run through a _bridge_ [[@stollPlainEnglishWhat2018]](https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a).

#### Bridges
Bridges connect components with one another. These components can be build in the same or different programming languages [[@adinugrohoReviewMultiplatformMobile2015]](http://www.sciencedirect.com/science/article/pii/S1877050915020979).

### Reactive View Approach
![Reactive app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/reactive-rendering.png)

_Figure 3: Reactive app rendering [[@lelerWhatRevolutionaryFlutter2017]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

Apps build with reactive frameworks (like React Native [[@facebookReactNativeFramework2015]](https://facebook.github.io/react-native/)) are mostly written in a platform independent language like JavaScript [[@ecmaJavaScriptECMAStandard1997]](https://www.ecma-international.org/publications/standards/Ecma-262.htm). The JavaScript code then sends information on how UI components should be displayed to the native environment. This communication always runs through a _bridge_. So we end up with native widgets that are controller through JavaScript. The main problem here, is that the communication through the _bridge_ is a bottleneck which can lead to performance issus [@googlellcHowFlutterDifferent2019; @stollPlainEnglishWhat2018; @lelerWhatRevolutionaryFlutter2017; @kolPerformanceLimitationsReact2017].

### Flutters Approach
![Flutter app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/flutter-rendering.png)

_Figure 4: Flutter app rendering [[@lelerWhatRevolutionaryFlutter2017]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

Flutters approach is to move the entire rendering process into the app. The rendering runs through Flutters own engine and uses flutters own widgets. It only needs a canvas to display the rendered frames on and system events/input it can then forward to your app. The framework also provides a way to access services and sensors through platform independent interfaces. This way the _bridging_ between the app and the native environment is kept to a minimum which removes that bottleneck [@googlellcHowFlutterDifferent2019; @stollPlainEnglishWhat2018; @lelerWhatRevolutionaryFlutter2017].

You might think that keeping an entire rendering engine inside your app would lead to huge APKs, but as of 2019 the compressed framework is only 4.3MB [[@flutterdevteamFAQFlutter2019]](https://flutter.dev/docs/resources/faq). 

![Flutter Framework Architecture](https://github.com/Fasust/flutter-guide/wiki//.images/flutter-architecture.png)

_Figure 5: Flutter Framework Architecture [[@lelerWhatRevolutionaryFlutter2017]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

| 🕐 TLDR | Flutter uses it's own engine instead of using the native one. The native environment only renders the finished frames. |
| ------- | :--------------------------------------------------------------------------------------------------------------------- |

## Flutter Compiler
One additional advantage of Flutter, is that is comes with two different compilers. A JIT-Compiler (Just in time) and a AOT-Compiler (Ahead of Time). The following table will showcase the advantage of each:

| Compiler      | What is does                                                                                                                                                                                                                                                                                              | When it's used     |
| :------------ | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :----------------- |
| Just in Time  | Only re-compiles files that have changed. Preserves App State [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) during rebuilds. Enables _Hot Reload_ [[@flutterdevteamHotReloadFlutter2019]](https://flutter.dev/docs/development/tools/hot-reload). | During Development |
| Ahead of Time | Compiles all dependencies ahead of time. The output app is faster.                                                                                                                                                                                                                                        | For Release        |

_Table 1: Flutters 2 Compliers [@mooreDartProductiveFast2019; @googlellcHowFlutterDifferent2019]_

## Hot Reload
_Hot Reload_ [[@flutterdevteamHotReloadFlutter2019]](https://flutter.dev/docs/development/tools/hot-reload) is a feature web developers are already very familiar with. It essentially means, that your changes in the code are displayed in the running application near instantaneously. Thanks to Flutters JIT Complier, it is also able to provide this feature.

![Hot Reload](https://github.com/Fasust/flutter-guide/wiki//.images/hot-reload.gif)

_Figure 6: Hot Reload [[@flutterdevteamHotReloadFlutter2019]](https://flutter.dev/docs/development/tools/hot-reload)_

# 120-Thinking-Declaratively

## Introduction
If you come from the native mobile world and _imperative_ frameworks like IOS [[@appleIOSSDK2010]](https://developer.apple.com/ios/) and Android [[@googlellcAndroidSDK2008]](https://developer.android.com/), developing with Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) can take while to get used to. Flutter, other then those frameworks mentioned above, is a _declarative_ Framework. This section will teach you how to think about developing apps declaratively and one of the most important concepts of Flutter: _state_ [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt).

## Declarative Programming vs Imperative Programming
But what exactly is the difference between _declarative_ and _imperative_? I will try to explain this using a metaphor: For a second, let's think of programming as _talking_ to the underlying framework. In this context, an imperative approach is telling the framework **exactly** what you want it to do. "Imperium" (Latin) means "to command". A declarative approach, on the other hand, would be describing to the framework what kind of result you want to get and letting the framework decide on how to achieve that result. "Declaro" (Latin) means "to explain" [@flutterdevteamFlutterFramework2018; @flutterdevteamFlutterState2019; @flutterdevteamIntroductionDeclarativeUI2019; @bezerraDeclarativeProgramming2018]. Let's look at an example:

```dart
List numbers = [1,2,3,4,5]
for(int i = 0; i < numbers.length; i++){
    if(numbers[i] > 3 ) print(numbers[i]);     
}
```
_Codesnippt 1: Number List (Imperative)_

Here we want to print every entry in the list that is bigger then 3. We explicitly tell the framework to go through the List one by one and check each value. In the declarative version, we simply state how our result should look like, but not how to reach it:

```dart
List numbers = [1,2,3,4,5]
print(numbers.where((num) => num > 3));
```
_Codesnippt 2: Number List (Declarative)_

One important thing to note here is, that the difference between imperative and declarative is not black and white. One style might bleed over into the other. Prof. David Brailsford from the University of Nottingham argues that as soon as you start using libraries for your imperative projects, they become a tiny bit mor declarative. This is because you are then using functions that _describe_ what they do and you no longer care how they do it [[@computerphileHTMLProgrammingLanguage2016]](https://www.youtube.com/watch?v=4A2mWqLUpzw).

| 🕐 TLDR | Imperative Programming is telling the framework **exactly** what you want it to do. Declarative Programming is describing to the framework what kind of result you want to get and letting the framework decide on how to achieve that result. |
| ------- | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

## Declarative Programming in Flutter
Okay, now that we understand what declarative means, let's take a look at Flutter specifically. This is a quote from Flutters official documentation:

> "Flutter is declarative. This means that Flutter builds its user interface to reflect the current state of your app" [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)

![UI = f(State)](https://github.com/Fasust/flutter-guide/wiki//.images/ui-equals-function-of-state.png)

_Figure 7: UI = f(State) [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)_

This means that you never imperatively or explicitly call an UI element to change it. You rather _declare_ that the UI should look a certain way, given a certain _state_ [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). But what exactly is _state_? 

| ⚠   | State in Flutter is any data that can change over time |
| --- | :----------------------------------------------------- |

Typical State examples: User Data, the position of a scroll bar, a favorite List

Let's have a look at a classic UI problem and how we would solve it imperatively in Android and compare it to Flutters declarative approach. let's say we want to build a button that changes it's color to red when it is pressed. In Android we find the button by it's ID, attach a listener and tell that listener to change the background color when the button is pressed:

```java
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
_Codesnippt 3: Red button in Android (Imperative)_

In Flutter on the other hand, we never call the UI element directly, we instead declare that the button background should be red or blue depending on the App-Sate (here the bool "pressed"). We then declare that the onPressed function should update the app state and re-build the button:

```dart
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
_Codesnippt 4: Red button in Flutter (Declarative)_

## Efficiency of Re-Builds
Is it not very inefficient to re-render the entire Widget every time we change the state? That was the first questions I had when learning about this topic. But I was pleased to learn, that Flutter uses something called "RenderObjects" to improve performance similar to Reacts [[@facebookReactNativeFramework2015]](https://facebook.github.io/react-native/) virtual DOM.

> "RenderObjects persist between frames and Flutter’s lightweight Widgets tell the framework to mutate the RenderObjects between states. The Flutter framework handles the rest." [[@flutterdevteamIntroductionDeclarativeUI2019]](https://flutter.dev/docs/get-started/flutter-for/declarative)

# 130-The-Widget-Tree
## Introduction
This section will give you a better understanding of how programming in Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) actually works. You will learn what Widgets [[@flutterdevteamFlutterWidgets2019]](https://flutter.dev/docs/development/ui/widgets-intro) are, what types of Widgets Flutter has and lastly what exactly the _Widget Tree_ is.

## Widgets in General
One sentence you can simply not avoid when researching Flutter is:

> "In Flutter, everything is a Widget." [[@flutterdevteamFlutterWidgets2019]](https://flutter.dev/docs/development/ui/widgets-intro)

But that is not really helpful, is it? Personally, I like Didier Boelens definition of Flutter Widgets better:

> "Think of a Widget as a visual component (or a component that interacts with the visual aspect of an application)." [[@boelensWidgetStateBuildContext2018]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956)

Let's have look at an example, this app displays an endless feed of Wisdoms combined with vaguely thought provoking stock images:

![Wisgen Widgets](https://github.com/Fasust/flutter-guide/wiki//.images/wisgen-widgets.png)

_Figure 1: Wisgen Widgets [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

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
_Codesnippt 5: Wisgen Card Widget [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

The functions _image() generates a Widget that contains the stock image. The function _content() generate a Widget that displays the wisdom text and the buttons on the card. 
Another important thing to note is that:

| ⚠   | Widgets in Flutter are always immutable [[@flutterdevteamFlutterWidgets2019]](https://flutter.dev/docs/development/ui/widgets-intro) |
| --- | :------------------------------------------------------------------------------------------------------------------------ |

The build method of any given Widget can be called multiple times a second. And how often it is called exactly is never under your control, it is controlled by the Flutter Framework [[@flutterdevteamStatelessWidgetClass2018]](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html). To make this rapid rebuilding of Widgets efficient, Flutter forces us developers to keep the build methods light weight by making all Widgets immutable. This means that all variables in a Widget have to be declared as _final_. Which means they are initialized once and can not change over time. 
But your app never consists out of exclusively immutable parts, does it? Variables need to change, data needs to be fetched and stored. Almost any app needs some sort of mutable data. As mentioned in the [previous chapter][declarative], in Flutter such data is called _state_ [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). No worries, how Flutter handles mutable state will be covered in the section [Stateful Widgets](#stateful-widgets) down below, so just keep on reading.

### The Widget Tree
When working with Flutter, you will inevitably stumble over the term _Widget Tree_, but what exactly does it mean? A UI in flutter is nothing more then a tree of nested Widgets. Let's have a look at the Widget Tree for our example from Figure 1. Note the card Widgets on the right hand side of the diagram. You can see how the code from Codesnippt 5 translates to Widgets in the Widget Tree.

![Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//.images/wisgen-widget-tree.PNG)

_Figure 2: Wisgen Widget Tree [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

### Buildcontext
If you have previously build an App with Flutter, you have definitely encountered _BuildContext_ [[@flutterdevteamBuildContextClass2018]](https://api.flutter.dev/flutter/widgets/BuildContext-class.html). It is passed in as a variable in every Widget build methode in Flutter. But what exactly is _BuildContext_? As Didier Boelens puts it:

> "A BuildContext is nothing else but a reference to the location of a Widget within the tree structure of all the Widgets which are built." [[@boelensWidgetStateBuildContext2018]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956)

The BuildContext contain information about each *ancestor* leading down to the Widget that the context belongs to. So it is an easy way for a Widget to access all its ancestors in the Widget tree. Accessing a Widgets *descendance* through the BuildContext is possible, but not advised and inefficient. So in short: For a Widget at the bottom of the tree, it is very easy to get information from Widgets at the top of the tree but **not** visversa [[@boelensWidgetStateBuildContext2018]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956). For example, the image Widget from Figure 2 could access it's ancestor card Widget like this:
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
_Codesnippt 6: Hypothetical Wisgen Image Widget [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Alright, but what does that mean for me as a Flutter developer? It is important to understand how data in Flutter flows through the Widget Tree: **Downwards**. You want to place information that is required by multiple Widgets above them in the tree, so they can both easily access it through their BuildContext. Keep this in mind for now, I will explain this in more detail in the chapter [Architecting a Flutter App][architecture].

## The three types of Widgets
There are 3 types of Widgets in the Flutter framework. I will now showcase there differences, there lifecycles and their respective usecases.

### Stateless Widgets
This is the most basic of the Three an likely the one you'll use the most when developing an app with Flutter. Stateless Widgets [[@flutterdevteamStatelessWidgetClass2018]](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html) are initialized once with a set of parameters and those parameters will never change from there on out. Let's have a look at an example. This is the class of the card Widget from figure 1:

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
_Codesnippt 7: Wisgen Card Widget Class [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

As you can see, it has some const values for styling, a Wisdom object that is passed into the constructor and a build methode. The Wisdom object contains the wisdom text and the hyperlink for the stock image.

One thing I want to point out here is that even if all fields are final in a StatelessWidget, it can still change to a degree. A ListView Widget is also a Stateless for example. It has a final reference to a list. Things can be added or removed from that list without the reference in the ListView Widget changing. So the ListView remains immutable and Stateless while the things it displays are able to change [[@googlellcHowCreateStateless2018]](https://www.youtube.com/watch?v=wE7khGHVkYY).

The Lifecycle of Stateless Widgets is very straight forward [[@boelensWidgetStateBuildContext2018]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956):

```dart
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
_Codesnippt 8: Stateless Widget Lifecycle_

### Stateful Widgets
I have explained what State is in the Chapter [Thinking Declaratively][declarative]. But just as a reminder:

| ⚠   | State in Flutter is any data that can change over time |
| --- | :----------------------------------------------------- |

A Stateful Widget [[@flutterdevteamStatefulWidgetClass2018]](https://api.flutter.dev/flutter/widgets/StatefulWidget-class.html) always consist of two parts: An immutable Widget and a mutable state. The immutable Widgets responsibility is to hold onto that state, the state itself has the mutable data and builds the actual Widget [[@googlellcHowStatefulWidgets2018]](https://www.youtube.com/watch?v=AqCMFXEmf3w). Let's have a look at an example. This is a simplified version of the WisdomFeed from Figure 1. The _WisdomBloc_ is responsible for generating and cashing wisdoms that are then displayed in the Feed. More on that in the chapter [Architecting a Flutter App][architecture].

```dart
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
_Codesnippt 9: Wisgen WisdomFeed [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

If you are anything like me, you will ask yourself: "why is this split into 2 parts? The StatefulWidget is not really doing anything." Well, The Flutter Team wants to keep Widgets **always** immutable. The only way to keep this statement universally true, is to have the StatefulWidget hold onto the State but not actually be the State [@googlellcHowStatefulWidgets2018; @windmillStatefulWidgetLifecycle2019].

State objects have a long lifespan in Flutter. This means that they will stick around during rebuilds or even if the widget that they are linked to gets replaced [[@googlellcHowStatefulWidgets2018]](https://www.youtube.com/watch?v=AqCMFXEmf3w). So in this example, no matter how often the WisdomFeed gets rebuild and no matter if the user switches pages, the cashed list of wisdoms (WisdomBloc) will stay the same until the app is shut down.

The Lifecycle of State Objects/StatefulWidgets is a little bit more complex, here is a boiled down version of it with all the methods you'll need for this guide. You can read the full Lifecycle here: Lifecycle of StatefulWidgets [[@windmillStatefulWidgetLifecycle2019]](https://flutterbyexample.com//stateful-widget-lifecycle).

```dart
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
_Codesnippt 10: State Objects/StatefulWidgets Lifecycle_

### When to use Stateless & When to use Stateful
Keep in mind, to improve performance, you always want to rely on as few Stateful widgets as possible.
But There is essentially two reasons to choose a Stateful Widget over a Stateless one: 
1. The Widget needs to hold any kind of data that has to change during its lifetime.
2. The Widget needs to dispose of anything or cleanup after it self at the end of it's lifetime.

### Inherited Widgets
I will not go in detail on Inherited Widgets [[@flutterdevteamInheritedWidgetClass2018]](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html) here. When using the BLoC library [[@angelovBlocLibraryDart2019]](https://felangel.github.io/bloc/#/), which I will teach you in the chatper [Architecting a Flutter-App][architecture], you will most likely never create an Inherited Widgets yourself. But in short: They are a way to expose data from the top if the Widget Tree to all their descendance. And they are used as the underlying technologie of the BLoC library.

# 140-Asynchronous-Flutter
## Introduction
## Futures
## Async & Await
## FutureBuilder
## Yield

# 150-Communication-with-the-Web
## Introduction
In this chapter I will briefly show you how to communicate with the Web in Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/). I think most large scale application are dependant on the Web in one way or another, so it felt important to cover this topic.

## The HTTP Package
Communicating with the Web is very easy in Flutter. The Dart Team maintains an external package called _http_ [[@dartteamHttpDartPackage2019]](https://pub.dev/packages/http) which takes care of most of the work for you. Dart [[@dartteamDartProgrammingLanguage2019]](https://dart.dev/) also offers very good integration of asynchrones programming [[@dartteamAsynchronousProgrammingDart2018]](https://dart.dev/codelabs/async-await), which I covered in the [last chapter][async]. Let's look at an example, this is a simplified version is Wisgens Api Repository. It can make a request the AdviceSlip API [[@kissAdviceSlipAPI2019]](https://api.adviceslip.com/) to fetch some new advice texts.

```dart
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
_Codesnippt 11: Wisgen API Repository [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

As you can see, you simply call _get()_ on the HTTP module and give it the URL it should request. This is an asynchronous call, so you can use the _await_ keyword to wait till the request is complete. Once the request is finished, you can read out headers and the body from the http.Response object. 

The AdviceSlips class, is generated with a JSON to Dart converter [[@lecuonaJSONDartConverter2019]](https://javiercbk.github.io/json_to_dart/). The generated class has a fromJson function that makes it easy to populate it's data fields with the JSON response. This is the generated class, you don't need to read it all, I just want to give you an idea of how it looks like:

```dart
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
_Codesnippt 12: Wisgen AdviceSlips Class [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

# 200-Architecting-a-Flutter-App
## What options are there? 
  - Vanilla, Redux, Bloc, Provide/Scoped Model
  - Which one will I focus on and Why?
  - Google went bach and forth on this as well.

## BLoC
- Why this one?
- Origin
- UI only publishes and subscribes
- **Build Interface code how you want it to look like -> then make it work**
- **4 Rules for BLoCs**
  - Only Sinks In & Streams out
  - Dependencies Injectable
  - No Platform Branching
  - Implementation can be whatever you want
- **4 Rules for UI Classes**
  - "Complex Enough" views have a BLoC
  - Components do not format the inputs they send to the BLoC
  - Output are formated as little as possible
  - If you do have Platform Branching, It should be dependent on a single BLoC bool output
  
### Bloc Architecture
![Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//.images/bloc-architecture.png)

### Bloc Architecture with Layers
![Bloc Architecture with Layers](https://github.com/Fasust/flutter-guide/wiki//.images/bloc-layers.png)

### Wisgen Component Dependencies
![Wisgen Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//.images/wisgen-dependencies.png)

### Wisgen DataFlow
![Wisgen Bloc Architecture Dataflow](https://github.com/Fasust/flutter-guide/wiki//.images/wisgen-dataflow.png)

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

