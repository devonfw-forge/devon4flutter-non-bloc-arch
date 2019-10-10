---
Show Stats: dart .\tools\stats.dart .\.raw-text\gfm-guide.md

Build & Split GFM IEEE: pandoc --wrap=preserve --filter pandoc-citeproc --bibliography=sources/guide.bib -f markdown -t gfm .raw-text/raw-guide.md -o .raw-text/gfm-guide.md --csl=sources/ieee.csl ; dart .\tools\splitter.dart .\.raw-text\gfm-guide.md .\pages\

Build & Split GFM Chicago: pandoc --wrap=preserve --filter pandoc-citeproc --bibliography=sources/guide.bib -f markdown -t gfm .raw-text/raw-guide.md -o .raw-text/gfm-guide.md ; dart .\tools\splitter.dart .\.raw-text\gfm-guide.md .\pages\

Build PDF: pandoc --wrap=preserve --filter pandoc-citeproc --csl=sources/ieee.csl --bibliography=sources/guide.bib --pdf-engine=xelatex --variable papersize=a4paper -s .raw-text/raw-guide.md -o paper.pdf
---
[intro]: https://github.com/Fasust/flutter-guide/wiki
[framework]: https://github.com/Fasust/flutter-guide/wiki/100-The-Flutter-Framework
[under-hood]: https://github.com/Fasust/flutter-guide/wiki/110-Under-the-Hood
[declarative]: https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively
[tree]: https://github.com/Fasust/flutter-guide/wiki/130-The-Widget-Tree
[async]: https://github.com/Fasust/flutter-guide/wiki/140-Asynchronous-Flutter
[architecture]: https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App
[statemng]: https://github.com/Fasust/flutter-guide/wiki/210-State-Management-Alternatives
[bloc]: https://github.com/Fasust/flutter-guide/wiki/220-BLoC
[test]: https://github.com/Fasust/flutter-guide/wiki/300-Testing
[conventions]: https://github.com/Fasust/flutter-guide/wiki/400-Conventions
[conclusion]: https://github.com/Fasust/flutter-guide/wiki/500-Conclusion
[refs]: https://github.com/Fasust/flutter-guide/wiki/600-References

# 000-Introduction

## The Goal of this Guide
This guide aims to bridge the gap between the absolute Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) basics and clean, structured Flutter development. It should bring you from the basics of knowing how to build an app with Flutter to an understanding of how to do it _properly_. Or at least show you one possible way to make large scale Flutter projects clean and manageable.

## Who is this Guide for?
For people with a basic knowledge of the Flutter Framework. I recommend following this tutorial by the Flutter team [[@flutterdevteamWriteYourFirst2018]](https://flutter.dev/docs/get-started/codelab). It will walk you through developing your first Flutter application. You should also have a basic understanding of the Dart programming language [[@dartteamDartProgrammingLanguage2019]](https://dart.dev/). No worries, it is very similar to Java [[@oracleJavaJDK1996]](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), Kotlin [[@jetbrainsKotlinSDK2017]](https://kotlinlang.org/) and JavaScript [[@ecmaJavaScriptECMAStandard1997]](https://www.ecma-international.org/publications/standards/Ecma-262.htm). So if you know 1 or 2 of those languages you should be fine.

## Topics that will be covered 
- A brief introduction to the Flutter Framework in general. How it works _under the hood_ and its underlying structure.
- One possible architecture for your Flutter app and how to implement it (BLoC [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE))
- How to test your app
- Some conventions and best practices for Dart, BLoC and the Flutter Framework
- My personal opinion of the framework

## Creation Context
This guide was written by a student in the Bachelor of Science Program “Computer Science and Media Technology” at Technical University Cologne [[@technicaluniversitycologneTechnicalUniversityCologne2019]](https://www.th-koeln.de/en/homepage_26.php), and it was created for one of the modules in that Bachelor. In addition to this, the guide was written in collaboration with Capgemini Cologne [[@capgeminiCapgeminiHomePage2019]](https://www.capgemini.com/us-en/). Capgemini released a guide on building an application in Angular [[@ambuludiCapgeminiAngularGuide2019]](https://github.com/devonfw/devon4ng) in May of 2019, this guide is meant to be the Flutter version of that.

## Structure
The guide is designed to be read in order, from Chapter 0 (this one) to Chapter 5. Code examples throughout the chapters will mainly be taken from Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen), an example Flutter Application that was specifically built for the purposes of this guide. If you want to search for any specific terms in the guide, you can use [this page](https://github.com/Fasust/flutter-guide/wiki/gfm-guide). It is all chapters of the guide combine into one page. There is going to be a few common symbols throughout the guide, this is what they stand for:

| Symbol | Meaning                  |
| :----: | :----------------------- |
|   📙   | Definition               |
|   🕐   | Shortened version (TLDR) |
|   ⚠    | Important                |

## My Sources 
I am basing this guide on a combination of conference talks, blog articles by respected Flutter developers, the official documentation, scientific papers that cover cross-platform mobile development in general and many other sources. All sources used in the guide are listed in the chapter [_References_][refs]. To better understand all the theory, I also developed the Wisgen app [[@faustWisgen2019]](https://github.com/Fasust/wisgen) using the Flutter Framework and the BLoC Pattern [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE).

# 100-The-Flutter-Framework
## Introduction
This Chapter will give you a basic understanding of how the Flutter Framework [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) works as a whole. I will showcase the difference of Flutter to other Cross-Platform approaches and how Flutter works _under the hood_. You will also be introduced to the concepts of _State_ [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) and Flutter's way of rendering an app as a tree of _Widgets_. In addition to this, you will gain an understanding of how Flutter Handels Asynchronous Programming. And Lastly, you will learn how to communicate with the Web within the Flutter Framework.

## Contents of the Chapter
* [Under The Hood][under-hood]
* [Thinking Declaratively][declarative]
* [The Widget Tree][tree]
* [Asynchronous Flutter][async]

# 110-Under-The-Hood

## Introduction
Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) is a framework for cross-platform native development. But what exactly does that mean? It means that it promises Native App performance while still compiling apps for multiple platforms from a single codebase. The best way to understand how Flutter achieves this is to compare it to other mobile development approaches.

### Full Native Approach
![Native app rendering](https://github.com/Fasust/flutter-guide/wiki//images/native-rendering.png)

_Figure 1: Native app rendering [[@lelerWhatRevolutionaryFlutter2017]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

The classic way to build a mobile app would be to write native code for each platform you want to support. I.E. One for IOS [[@appleIOSSDK2010]](https://developer.apple.com/ios/), one for Android [[@googlellcAndroidSDK2008]](https://developer.android.com/) and so on. In this approach, your app will be written in a platform-specific language and render through platform-specific Widgets and a platform-specific engine. During the development, you have direct access to platform-specific services and sensors [@googlellcHowFlutterDifferent2019; @stollPlainEnglishWhat2018; @lelerWhatRevolutionaryFlutter2017]. But you will have to build the same app multiple times, which effectively doubles your workload.

### Embedded WebApp Approach
![Embedded Web App rendering](https://github.com/Fasust/flutter-guide/wiki//images/webview-rendering.png)

_Figure 2: Embedded WebApp rendering [[@lelerWhatRevolutionaryFlutter2017]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

Embedded WebApps where the first approach to cross-platform development. You would simply build your application with HTML, CSS, and JavaScript and then have it render through a native WebView[@googlellcHowFlutterDifferent2019; @lelerWhatRevolutionaryFlutter2017]. The problem here is, that developers are limited to the web technology stack and that communication between the app and native services would always have to run through a _bridge_ [[@stollPlainEnglishWhat2018]](https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a).

#### Bridges
Bridges connect components with one another. These components can be built in the same or different programming languages [[@adinugrohoReviewMultiplatformMobile2015]](http://www.sciencedirect.com/science/article/pii/S1877050915020979).

### Reactive View Approach
![Reactive app rendering](https://github.com/Fasust/flutter-guide/wiki//images/reactive-rendering.png)

_Figure 3: Reactive app rendering [[@lelerWhatRevolutionaryFlutter2017]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

Apps build with reactive frameworks (like React Native [[@facebookReactNativeFramework2015]](https://facebook.github.io/react-native/)) are mostly written in a platform-independent language like JavaScript [[@ecmaJavaScriptECMAStandard1997]](https://www.ecma-international.org/publications/standards/Ecma-262.htm). The JavaScript code then sends information on how UI components should be displayed to the native environment. This communication always runs through a _bridge_. So we end up with native Widgets that are controller through JavaScript. The main problem here is that the communication through the _bridge_ is a bottleneck which can lead to performance issues [@googlellcHowFlutterDifferent2019; @stollPlainEnglishWhat2018; @lelerWhatRevolutionaryFlutter2017; @kolPerformanceLimitationsReact2017].

### Flutter's Approach
![Flutter app rendering](https://github.com/Fasust/flutter-guide/wiki//images/flutter-rendering.png)

_Figure 4: Flutter app rendering [[@lelerWhatRevolutionaryFlutter2017]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

Flutter's approach is to move the entire rendering process into the app. The rendering runs through Flutter's own engine and uses Flutter's own Widgets. It only needs a canvas to display the rendered frames on and system events/input it can then forward to your app. The framework also provides a way to access services and sensors through platform-independent interfaces. This way the _bridging_ between the app and the native environment is kept to a minimum which removes that bottleneck [@googlellcHowFlutterDifferent2019; @stollPlainEnglishWhat2018; @lelerWhatRevolutionaryFlutter2017].

You might think that keeping an entire rendering engine inside your app would lead to huge APKs, but as of 2019, the compressed framework is only 4.3MB [[@flutterdevteamFAQFlutter2019]](https://flutter.dev/docs/resources/faq). 

![Flutter Framework Architecture](https://github.com/Fasust/flutter-guide/wiki//images/flutter-architecture.png)

_Figure 5: Flutter Framework Architecture [[@lelerWhatRevolutionaryFlutter2017]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

| 🕐  | TLDR | Flutter uses its own engine instead of using the native one. The native environment only renders the finished frames. |
| --- | ---- | :-------------------------------------------------------------------------------------------------------------------- |

## Flutter Compiler
One additional advantage of Flutter is that it comes with two different compilers. A JIT-Compiler (Just in time) and an AOT-Compiler (Ahead of Time). The following table will showcase the advantage of each:

| Compiler      | What is does                                                                                                                                                                                                                                                                                              | When it's used     |
| :------------ | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :----------------- |
| Just in Time  | Only re-compiles files that have changed. Preserves App State [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) during rebuilds. Enables _Hot Reload_ [[@flutterdevteamHotReloadFlutter2019]](https://flutter.dev/docs/development/tools/hot-reload). | During Development |
| Ahead of Time | Compiles all dependencies Ahead of time. The output app is faster.                                                                                                                                                                                                                                        | For Release        |

_Table 1: Flutter's 2 Compilers [@mooreDartProductiveFast2019; @googlellcHowFlutterDifferent2019]_

## Hot Reload
_Hot Reload_ [[@flutterdevteamHotReloadFlutter2019]](https://flutter.dev/docs/development/tools/hot-reload) is a feature that web developers are already very familiar with. It essentially means, that your changes in the code are displayed in the running application near instantaneously. Thanks to Flutter's JIT Complier, it is also able to provide this feature.

![Hot Reload](https://github.com/Fasust/flutter-guide/wiki//images/hot-reload.gif)

_Figure 6: Hot Reload [[@flutterdevteamHotReloadFlutter2019]](https://flutter.dev/docs/development/tools/hot-reload)_

# 120-Thinking-Declaratively

## Introduction
If you come from the native mobile world and _imperative_ frameworks like IOS [[@appleIOSSDK2010]](https://developer.apple.com/ios/) and Android [[@googlellcAndroidSDK2008]](https://developer.android.com/), developing with Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) can take a while to get used to. Flutter, other then those frameworks mentioned above, is a _declarative_ framework. This section will teach you how to think about developing apps declaratively and one of the most important concepts of Flutter: _State_ [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt).

## Declarative Programming vs Imperative Programming
But what exactly is the difference between _declarative_ and _imperative_? I will try to explain this using a metaphor: For a second, let's think of programming as _talking_ to the underlying framework. In this context, an imperative approach is telling the framework **exactly** what you want it to do. "Imperium" (Latin) means "to command". A declarative approach, on the other hand, would be describing to the framework what kind of result you want to get and then letting the framework decide on how to achieve that result. "Declaro" (Latin) means "to explain" [@flutterdevteamFlutterFramework2018; @flutterdevteamFlutterState2019; @flutterdevteamIntroductionDeclarativeUI2019; @bezerraDeclarativeProgramming2018]. Let's look at an example:

```dart
List numbers = [1,2,3,4,5]
for(int i = 0; i < numbers.length; i++){
    if(numbers[i] > 3 ) print(numbers[i]);     
}
```
_Code Snippet 1: Number List (Imperative)_

Here we want to print every entry in the list that is bigger than 3. We explicitly tell the framework to go through the List one by one and check each value. In the declarative version, we simply State how our result should look like, but not how to reach it:

```dart
List numbers = [1,2,3,4,5]
print(numbers.where((num) => num > 3));
```
_Code Snippet 2: Number List (Declarative)_

One important thing to note here is, that the difference between imperative and declarative is not black and white. One style might bleed over into the other. Prof. David Brailsford from the University of Nottingham argues that as soon as you start using libraries for your imperative projects, they become a tiny bit more declarative. This is because you are then using functions that _describe_ what they do and you no longer care how they do it [[@computerphileHTMLProgrammingLanguage2016]](https://www.youtube.com/watch?v=4A2mWqLUpzw).

| 🕐  | TLDR | Imperative Programming is telling the framework **exactly** what you want it to do. Declarative Programming is describing to the framework what kind of result you want to get and then letting the framework decide on how to achieve that result. |
| --- | ---- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

## Declarative Programming in Flutter
Okay, now that we understand what declarative means, let's take a look at Flutter specifically. This is a quote from Flutter's official documentation:

> "Flutter is declarative. This means that Flutter builds its user interface to reflect the current State of your app" [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)

![UI = f(State)](https://github.com/Fasust/flutter-guide/wiki//images/ui-equals-function-of-state.png)

_Figure 7: UI = f(State) [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)_

This means that you never imperatively or explicitly call a UI element to change it. You rather _declare_ that the UI should look a certain way, given a certain _State_ [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). But what exactly is _State_? 

| 📙  | State | Any data that can change over time [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) |
| --- | ----- | :--------------------------------------------------------------------------------------------------------------------------------------- |

Typical State examples: User Data, the position of a scroll bar, a favorite List

Let's have a look at a classic UI problem and how we would solve it imperatively in Android and compare it to Flutter's declarative approach. let's say we want to build a button that changes its color to red when it is pressed. In Android we find the button by its ID, attach a listener and tell that listener to change the background color when the button is pressed:

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
_Code Snippet 3: Red button in Android (Imperative)_

In Flutter, on the other hand, we never call the UI element directly, we instead declare that the button background should be red or blue depending on the App-Sate (here the bool "pressed"). We then declare that the _onPressed()_ function should update the app State and re-build the button:

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
_Code Snippet 4: Red button in Flutter (Declarative)_

## Efficiency of Re-Builds
Is it not very inefficient to re-render the entire Widget every time we change the State? That was the first question I had when learning about this topic. But I was pleased to learn, that Flutter uses something called "RenderObjects" to improve performance similar to Reacts [[@facebookReactNativeFramework2015]](https://facebook.github.io/react-native/) virtual DOM.

> "RenderObjects persist between frames and Flutter’s lightweight Widgets tell the framework to mutate the RenderObjects between States. The Flutter framework handles the rest." [[@flutterdevteamIntroductionDeclarativeUI2019]](https://flutter.dev/docs/get-started/flutter-for/declarative)

# 130-The-Widget-Tree
## Introduction
This section will give you a better understanding of how programming in Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) actually works. You will learn what Widgets [[@flutterdevteamFlutterWidgets2019]](https://flutter.dev/docs/development/ui/widgets-intro) are, what types of Widgets Flutter has and lastly what exactly the _Widget Tree_ is.

## Widgets in General
One sentence you can simply not avoid when researching Flutter is:

> "In Flutter, everything is a Widget." [[@flutterdevteamFlutterWidgets2019]](https://flutter.dev/docs/development/ui/widgets-intro)

But that is not really helpful, is it? Personally, I like Didier Boelens definition of Flutter Widgets better:


| 📙  | Widget | A visual component (or a component that interacts with the visual aspect of an application) [[@boelensWidgetStateBuildContext2018]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956) |
| --- | ------ | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |



Let's have look at an example, this app displays an endless feed of Wisdoms combined with vaguely thought-provoking stock images:

![Wisgen Widgets](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-widgets.png)

_Figure 8: Wisgen Widgets [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

As you can see, all UI-Components of the app are Widgets. From high-level stuff like the App-Bar and the ListView down to to the granular things like texts and buttons (I did not highlight every Widget on the screen to keep the figure from becoming overcrowded). In code, the build method of a card Widget would look something like this:

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
_Code Snippet 5: Wisgen Card Widget [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

The functions _image() generates a Widget that contains the stock image. The function _content() generates a Widget that displays the wisdom text and the buttons on the card. 
Another important thing to note is that:

| ⚠   | Widgets in Flutter are always immutable [[@flutterdevteamFlutterWidgets2019]](https://flutter.dev/docs/development/ui/widgets-intro) |
| --- | :----------------------------------------------------------------------------------------------------------------------------------- |

The build method of any given Widget can be called multiple times a second. And how often it is called exactly is never under your control, it is controlled by the Flutter Framework [[@flutterdevteamStatelessWidgetClass2018]](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html). To make this rapid rebuilding of Widgets efficient, Flutter forces us developers to keep the build methods lightweight by making all Widgets immutable. This means that all variables in a Widget have to be declared as _final_. Which means they are initialized once and can not change over time. 
But your app never consists out of exclusively immutable parts, does it? Variables need to change, data needs to be fetched and stored. Almost any app needs some sort of mutable data. As mentioned in the [previous chapter][declarative], in Flutter such data is called _State_ [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). No worries, how Flutter handles mutable State will be covered in the section [Stateful Widgets](#stateful-widgets) down below, so just keep on reading.

### The Widget Tree
When working with Flutter, you will inevitably stumble over the term _Widget Tree_, but what exactly does it mean? A UI in Flutter is nothing more than a tree of nested Widgets. Let's have a look at the Widget Tree for our example from Figure 8. Note the card Widgets on the right-hand side of the diagram. You can see how the code from snippet 5 translates to Widgets in the Widget Tree.

![Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-widget-tree.PNG)

_Figure 9: Wisgen Widget Tree [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

### Buildcontext
If you have previously built an App with Flutter, you have definitely encountered _BuildContext_ [[@flutterdevteamBuildContextClass2018]](https://api.flutter.dev/flutter/widgets/BuildContext-class.html). It is passed in as a variable in every Widget build method in Flutter. But what exactly is _BuildContext_?

| 📙  | BuildContext | A reference to the location of a Widget within the tree structure of all the Widgets that have been built [[@boelensWidgetStateBuildContext2018]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956) |
| --- | ------------ | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

The BuildContext contains information about each *ancestor* leading down to the Widget that the context belongs to. So it is an easy way for a Widget to access all its ancestors in the Widget Tree. Accessing a Widgets *descendants* through the BuildContext is possible, but not advised and inefficient. So in short: For a Widget at the bottom of the tree, it is very easy to get information from Widgets at the top of the tree but **not** vice-versa [[@boelensWidgetStateBuildContext2018]](https://medium.com/flutter-community/widget-state-buildcontext-inheritedwidget-898d671b7956). For example, the image Widget from Figure 9 could access its ancestor card Widget like this:
```dart
Widget build(BuildContext context) {

  //going up the Widget Tree: 
  //(Image [me]) -> (Column) -> (Card) [!] first match, so this one is returned
  Card ancestorCard = Card.of(context); 

  return CachedNetworkImage(
    ...
  );
}
```
_Code Snippet 6: Hypothetical Wisgen Image Widget [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Alright, but what does that mean for me as a Flutter developer? It is important to understand how data in Flutter flows through the Widget Tree: **Downwards**. You want to place information that is required by multiple Widgets above them in the tree, so they can both easily access it through their BuildContext. Keep this in mind, for now, I will explain this in more detail in the chapter [Architecting a Flutter App][architecture].

## The three types of Widgets
There are three types of Widgets in the Flutter framework. I will now showcase their differences, their lifecycles, and their respective use-cases.

### Stateless Widgets
This is the most basic of the three and likely the one you'll use the most when developing an app with Flutter. Stateless Widgets [[@flutterdevteamStatelessWidgetClass2018]](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html) are initialized once with a set of parameters and those parameters will never change from there on out. Let's have a look at an example. This is the class of the card Widget from figure 8:

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
_Code Snippet 7: Wisgen Card Widget Class [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

As you can see, it has some const values for styling, a wisdom object that is passed into the constructor and a build method. The wisdom object contains the wisdom text and the hyperlink for the stock image.

One thing I want to point out here is that even if all fields are final in a StatelessWidget, it can still change to a degree. A ListView Widget is also a Stateless for example. It has a final reference to a list. Things can be added or removed from that list without the reference in the ListView Widget changing. So the ListView remains immutable and Stateless while the things it displays can change [[@googlellcHowCreateStateless2018]](https://www.youtube.com/watch?v=wE7khGHVkYY).

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
_Code Snippet 8: Stateless Widget Lifecycle_

### Stateful Widgets
I have explained what State is in the Chapter [Thinking Declaratively][declarative]. But just as a reminder:

| ⚠   | State in Flutter is any data that can change over time |
| --- | :----------------------------------------------------- |

A Stateful Widget [[@flutterdevteamStatefulWidgetClass2018]](https://api.flutter.dev/flutter/widgets/StatefulWidget-class.html) always consists of two parts: An immutable Widget and a mutable State. The immutable Widget's responsibility is to hold onto that State, the State itself has the mutable data and builds the actual Widget [[@googlellcHowStatefulWidgets2018]](https://www.youtube.com/watch?v=AqCMFXEmf3w). Let's have a look at an example. This is a simplified version of the WisdomFeed from Figure 8. The _WisdomBloc_ is responsible for generating and cashing wisdoms that are then displayed in the Feed. More on that in the chapter [Architecting a Flutter App][architecture].

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
_Code Snippet 9: Wisgen WisdomFeed [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

If you are anything like me, you will ask yourself: "why is this split into 2 parts? The StatefulWidget is not really doing anything." Well, The Flutter Team wants to keep Widgets **always** immutable. The only way to keep this statement universally true is to have the StatefulWidget hold onto the State but not actually be the State [@googlellcHowStatefulWidgets2018; @windmillStatefulWidgetLifecycle2019].

State objects have a long lifespan in Flutter. This means that they will stick around during rebuilds or even if the Widget that they are linked to gets replaced [[@googlellcHowStatefulWidgets2018]](https://www.youtube.com/watch?v=AqCMFXEmf3w). So in this example, no matter how often the WisdomFeed gets rebuild and no matter if the user switches pages, the cashed list of wisdoms (WisdomBloc) will stay the same until the app is shut down.

The Lifecycle of State Objects/StatefulWidgets is a little bit more complex, here is a boiled-down version of it with all the methods you'll need for this guide. You can read the full Lifecycle here: Lifecycle of StatefulWidgets [[@windmillStatefulWidgetLifecycle2019]](https://flutterbyexample.com//stateful-widget-lifecycle).

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

  //Called once before the State is disposed(app shut down)
  //Use this for your clean up and to unsubscribe from streams
  @override
  dispose(){...}
}
```
_Code Snippet 10: State Objects/StatefulWidgets Lifecycle_

### When to use Stateless & When to use Stateful
Keep in mind, to improve performance, you always want to rely on as few Stateful Widgets as possible.
But there are essentially two reasons to choose a Stateful Widget over a Stateless one: 

1. The Widget needs to hold any kind of data that has to change during its lifetime.
2. The Widget needs to dispose of anything or clean up after itself at the end of its lifetime.

### Inherited Widgets
I will not go in detail on Inherited Widgets [[@flutterdevteamInheritedWidgetClass2018]](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html) here. When using the BLoC library [[@angelovBlocLibraryDart2019]](https://felangel.github.io/bloc/#/), which I will teach you in the chapter [Architecting a Flutter-App][architecture], you will most likely never create an Inherited Widgets yourself. But in short: They are a way to expose data from the top of the Widget Tree to all their descendants. And they are used as the underlying technology of the BLoC library.

# 140-Asynchronous-Flutter
## Introduction
Asynchronous Programming is an essential part of any modern application. There will always be network calls, user input or any number of other unpredictable things that your app has to wait for. Luckily Dart [[@dartteamDartProgrammingLanguage2019]](https://dart.dev/) and Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) have a very good integration of Asynchronous Programming. This chapter will teach you the basics of Futures, async/await [[@dartteamDartProgrammingLanguage2019]](https://dart.dev/) and Streams [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams). Throughout this chapter, I will be using the _HTTP package_ [[@dartteamHttpDartPackage2019]](https://pub.dev/packages/http) to make network requests. Communication with the web is one of the most common use-cases for Asynchronous Programming, so I thought it would only be fitting.

## Futures
Futures [[@dartteamDartProgrammingLanguage2019]](https://dart.dev/) are the most basic way of dealing with asynchronous code in Flutter. If you have ever worked with JavaScripts [[@ecmaJavaScriptECMAStandard1997]](https://www.ecma-international.org/publications/standards/Ecma-262.htm) Promises before, they are basically the exact same thing. Here is a small example: This is a simplified version of the Wisgen API class. It can make requests to the AdviceSlip API [[@kissAdviceSlipAPI2019]](https://api.adviceslip.com/) to fetch some new advice texts.

```dart
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
_Code Snippet 11: Wisgen API class (Futures) [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

As you can see, you simply call _get()_ on the HTTP module and give it the URL it should request. The get() method returns a Future. A Future object is a reference to an event that will take place at some point in the _future_. We can give it a callback function with _then()_, that will execute once that event is resolved. The callback we define will get access to the result of the Future IE it's type: `Future<Type>`. So here, the Future object _"apiCall"_ is a reference to when the API call will be resolved. Once the call is complete, _then()_ will be called and we get access to the _http.Response_. We tell the future to transform the Response into a wisdom object and return the result, by adding this instruction as a callback to _then()_ [@googlellcDartFutures2019; @googlellcIsolatesEventLoops2019]. We can also handle errors with the _catchError()_ function:

```dart
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
_Code Snippet 12: Wisgen API Class (Futures with Error) [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

### Async & Await
If you have ever worked with Promises or Futures before, you know that this can get really ugly really quickly: callbacks nested in callbacks. Luckily Dart supports the _async & await_ keywords [[@dartteamAsynchronousProgrammingDart2018]](https://dart.dev/codelabs/async-await), which give us the ability to structure our asynchronous  code the same way we would if it was synchronous. Let's take the same example as in 
Snippet 11:

```dart
class Api {
  //Delivers 1 random advice as JSON
  static const _adviceURI = 'https://api.adviceslip.com/advice'; 

  Future<Wisdom> fetch() async {
    http.Response response = await http.get(_adviceURI);
    return Wisdom.fromResponse(response);
  }
}
```
_Code Snippet 13: Wisgen API Class (Async) [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

We can use the _await_ keyword to tell Flutter to wait at on specific point until a Future is resolved. In this example, Flutter waits until the _http.Response_ has arrived and then proceeds to transform it into a Wisdom. If we want to use the await keyword in a function, we have to mark the function as _async_. This forces the return type to be a Future. This makes sense because if we wait during the function, the function will never return instantly, thus it **has** to return a Future [[@googlellcAsyncAwait2019]](https://www.youtube.com/watch?v=SmTCmDMi4BY). Error handling in async function can be done with _try/catch_:

```dart
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
_Code Snippet 14: Wisgen API Class (Async with Error) [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

## Streams
Streams [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams) are one of the core technologies behind reactive programming [[@boelensFlutterReactiveProgramming2018]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/). And we'll use them heavily in the chapter [Architecting a Flutter app][architecture]. But what exactly are _streams_? As Andrew Brogdon put's it in one of Google's official Dart tutorials, Streams are to Future what Iterables are to synchronous data types [[@googlellcDartStreams2019]](https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17&t=345s). You can think of streams as one continuous flow of data. Data can be put into the stream, other parties can subscribe/listen to a given stream and be notified once a new piece of data enters the stream.

![Data Stream](https://github.com/Fasust/flutter-guide/wiki//images/stream.PNG)

_Figure 10: Data Stream_

Okay, but how does it look in Dart code? First, we initialize a SteamBuilder [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams) to generate a new stream. The StreamBuilder gives us access to a _sink_, that we can use to put data into the stream and the actual _stream_, which we can use to read data from the stream:

```dart
main(List<String> arguments) {
  StreamController<int> _controller = StreamController();
  for(int i = 0; i < 5 ; i++){
    _controller.sink.add(i);
  }

  _controller.stream.listen((i) => print(i));

  _controller.close(); //don't forget to close the stream once you are done
}
```
_Code Snippet 15: Stream of Ints_

```bash
0
1
2
3
4
```
_Code Snippet 16: Stream of Ints Output_

Important Side Note: 

| ⚠   | Streams are single subscription by default. So if you want multiple subscribers you need to add `StreamController streamController = new StreamController.broadcast();` |
| --- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------- |


Let's have a look at a more complex example: In Wisgen, our wisdoms are delivered to the Interface via a stream. Whenever we run out of wisdoms to display, a request is sent to a class that fetches new wisdoms form our API [[@kissAdviceSlipAPI2019]](https://api.adviceslip.com/) and publishes them in a stream. Once those new wisdoms come in, the UI gets notified and receives them. This approach is called _BLoC Pattern_ [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE) and I will explain it in detail in the chapter [Architecting a Flutter app][architecture]. For now, this is a simplified version of how that could look like:

```dart
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

    //Appending the new Wisdoms to the current State
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
_Code Snippet 17: Simplified Wisgen WisdomBLoC [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

We create a stream builder in the beginning and expose the stream itself to enable the UI to subscribe to it. We also open up a private sink, so we can easily add thinks to the stream. Whenever the _publishMoreWisdom()_ function is called, the BLoC request more wisdom from the API waits until they are fetched and then publishes them to the stream. Let's look at the UI side of things. This is a simplified version of the WisdomFeed in Wisgen:

```dart
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
_Code Snippet 18: Simplified Wisgen WisdomFeed with StreamBuilder [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Alright, let's go through this step by step. First, we initialize our WisdomBloc in the _initSate()_ method. This is also where we set up a ScrollController [[@flutterdevteamScrollControllerClass2018]](https://api.flutter.dev/flutter/widgets/ScrollController-class.html) that we can use to determine how far down the list we have scrolled [[@angelovFlutterInfiniteList2019]](https://felangel.github.io/bloc/#/flutterinfinitelisttutorial). I won't go into the details here, but the controller enables us to call _publishMoreWisdom()_ on the WisdomBloc whenever we are near the end of our list. This way we get infinite scrolling. In the _build()_ method, we use Flutter's StreamBuilder [[@flutterdevteamStreamBuilderClass2018]](https://api.flutter.dev/flutter/widgets/StreamBuilder-class.html) to link our UI to our stream. We give it our stream and it provides a builder method. This builder has a snapshot containing the current State of the stream. We can use the snapshot to determine when the UI needs to display a loading animation, an error message or the actual list. When we receive the actual list of wisdoms from our stream through the snapshot, we continue to the _listView()_ method. Here we just use the list of wisdoms to create a ListView with WisdomCards. You might have wondered why we stream a List of wisdoms and not just individual wisdoms. This ListView is the reason. If we where streaming individual Wisdoms we would need to combine them into a list here. Streaming a complete list is also recommended by the Flutter team for this use-case [[@sullivanTechnicalDebtStreams2018]](https://www.youtube.com/watch?v=fahC3ky_zW0). Finally, once the app is closed down, the _dispose()_ method is called and we dispose of our stream and ScrollController.

![Streaming Wisdom from BLoC to WisdomFeed](https://github.com/Fasust/flutter-guide/wiki//images/wisdomBloc-stream.PNG)

_Figure 11: Streaming Wisdom from BLoC to WisdomFeed [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

### Async* & yield
Streams have two keywords that are very similar to the _async & await_ of Futures: _async* & yield_ [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams). If we mark a function as async* the return type **has** to be a stream. In an async* function we get access to the async keyword (which we already know) and the yield keyword, which is very similar to a return, only that yield does not terminate the function but instead adds a value to the stream. This is what an implementation of the WisdomBloc from snippet 17 could look like when using async*:

```dart
Stream<List<Wisdom>> streamWisdoms() async* {
  List<Wisdom> fetchedWisdoms = await _api.fetch(20);

  //Appending the new Wisdoms to the current State
  List<Wisdom> newWisdoms = _oldWisdoms + fetchedWisdoms;

  yield newWisdoms; //publish to stream
  _oldWisdoms = newWisdoms;
}
```
_Code Snippet 19: Simplified Wisgen WisdomBLoC with async* [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

This marks the end of my introduction to streams. It can be a challenging topic wrap your head around at first so if you still feel like you want to learn more I can highly recommend this article by Didier Boelens [[@boelensFlutterReactiveProgramming2018]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/) or this 8-minute tutorial video by the Flutter Team [[@googlellcDartStreams2019]](https://www.youtube.com/watch?v=nQBpOIHE4eE&list=PLjxrf2q8roU2HdJQDjJzOeO6J3FoFLWr2&index=17&t)

## Side Note on Communication with the Web
I just wanted to end this chapter by showing you how the API class of Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen) actually looks like and give some input of why it looks the way it does:

```dart
import 'dart:convert';
import 'dart:math';

import 'package:flutter/src/widgets/framework.dart';
import 'package:wisgen/models/advice_slips.dart';
import 'package:wisgen/models/wisdom.dart';
import 'package:wisgen/repositories/repository.dart';
import 'package:http/http.dart' as http;

///Cashes data it fetches from an API and
///then Provides a given amount of random entries.
class Api implements DataProvider<Wisdom> {
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
_Code Snippet 20: Actual Wisgen API Class [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

The _AdviceSlips_ class is generated with a JSON to Dart converter [[@lecuonaJSONDartConverter2019]](https://javiercbk.github.io/json_to_dart/). The generated class has a fromJson function that makes it easy to populate it's data fields with the JSON response. I used this class instead of implementing a method in the _Wisdom_ class because I did not want a direct dependency from my entity class to the AdviceSlip JSON structure. This is the generated class, you don't need to read it all, I just want to give you an idea of how it looks like:

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
_Code Snippet 21: Wisgen AdviceSlips Class [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_


# 200-Architecting-a-Flutter-App
## Introduction
The Most central topic of architecting a Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) app is _State Management_ [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). **Where** does my State sit, **who** needs access to it and **how** do parts of the app access it? This chapter aims to answer those questions. You will learn about the two types of State, you will be introduced to the three most popular State Management solutions and you will learn one of those State Management solutions (BLoC [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE)) in detail. You will also learn how to use the BLoC State Management solution in a clean and scalable Four-Layered architecture.

## State Management vs Architecture
I want to differentiate these two terms. Within the Flutter community, _State Management_ and _Architecture_ are often used synonymously, but I think we should be careful to do so. State Management is a set of tools or a pattern with which we can manage the State within our app. Architecture, on the other hand, is the overarching structure of our app. A set of rules that our app conforms to. Any architecture for a Flutter application will have some sort of State Management, but State Management is not an architecture by itself. I just want you to keep this in mind for the following chapters.

## Types of State
The Flutter documentation [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) differentiates between two types of State: _Ephemeral State_ & _App State_.
Ephemeral State is State that is only required in one location IE inside of one Widget. Examples would be: scroll position in a list, highlighting of selected elements or the color change of a pressed button. This is the type of State that we don't need to worry about that much or in other words, there is no need for a fancy State Management solution for Ephemeral State. We can simply use a Stateful Widget with some variables and manage Ephemeral State that way [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). The more interesting type of State is App State. This is information that is required in multiple locations / by multiple Widgets. Examples would be user data, a list of favorites or a shopping car. App State management is going to be the focus of this chapter.

![Ephemeral State vs App State Decision Tree](https://github.com/Fasust/flutter-guide/wiki//images/ephemeral-vs-app-state.png)

_Figure 12: Ephemeral State vs App State Dession Tree [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt)_

## Contents of this Chapter

- [State Management Alternatives][statemng]
- [BLoC][bloc]

# 210-State-Management-Alternatives

## Introduction
Other than many mobile development frameworks, Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) does not impose any kind of architecture or State Management solution on its developers. This open-ended approach has lead to multiple State Management solution and a hand full of architectural approaches spawning from the community [[@eganFlutterArchitectureSamples2017]](https://fluttersamples.com/). Some of these approaches have even been endorsed by the Flutter Team itself [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). I decided to focus on the BLoC pattern [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE) for this Guide. But I do want to showcase some alternatives and explain why exactly I ended up choosing BLoC.

## Example App State
I will showcase the State Management solutions using one example of _App State_ from the Wisgen App [[@faustWisgen2019]](https://github.com/Fasust/wisgen). We have a list of favorite wisdoms in the Wisgen App. This State is needed by 2 parties: 

1. The ListView on the favorite page, so it can display all favorites
2. The button on every wisdom card so it can add a new favorite to the list and show if a given wisdom is a favorite.

![Wisgen WidgetTree Favorites](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-fav-mock.png)

_Figure 13: Wisgen Favorites [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

So whenever the favorite button on any card is pressed, several Widgets [[@flutterdevteamFlutterWidgets2019]](https://flutter.dev/docs/development/ui/widgets-intro) have to update. This is a simplified version of the Wisgen Widget Tree, the red highlights show the Widgets that need access to the favorite list, the heart shows a possible location from where a new favorite could be added.

![Wisgen WidgetTree Favorites](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-fav.PNG)

_Figure 14: Wisgen WidgetTree Favorites [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

## Provider Package
The Provider Package [[@rousseletProviderFlutterPackage2018]](https://pub.dev/packages/provider) is an open-source package for Flutter developed by Remi Rousselet in 2018. It has since then been endorsed by the Flutter Team on multiple occasions [@hracekPragmaticStateManagement2019; @sullivanPragmaticStateManagement2019] and they are now devolving it in cooperation. The package is basically a prettier interface for Inherited Widgets [[@flutterdevteamInheritedWidgetClass2018]](https://api.flutter.dev/flutter/widgets/InheritedWidget-class.html). You can use Provider to expose State from a Widget at the top of the tree to any number of Widgets below it in the tree.

As a quick reminder: Data in Flutter always flows **downwards**. If you want to access data from multiple locations within your Widget Tree, you have to place it at one of their common ancestors so they can both access it through their build contexts. This practice is called _"lifting State up"_ and it is a common practice within declarative frameworks [[@eganKeepItSimple2018]](https://www.youtube.com/watch?v=zKXz3pUkw9A).


| 📙  | Lifting State up | Placing State at the lowest common ancestor of all Widgets that need access to it [[@eganKeepItSimple2018]](https://www.youtube.com/watch?v=zKXz3pUkw9A) |
| --- | ---------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------- |

The Provider Package is an easy way for us to lift State up. Let's look at our example from figure 14: The first common ancestor of all Widgets in need of the favorite list is _MaterialApp_. So we will need to lift the State up to the MaterialApp and then have our Widgets access it from there:

![Wisgen WidgetTree Favorites with Provider](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-provider.PNG)

_Figure 15: Wisgen WidgetTree Favorites with Provider [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

To minimize re-builds the Provider Package uses ChangeNotifiers [[@flutterdevteamChangeNotifierClass2018]](https://api.flutter.dev/flutter/foundation/ChangeNotifier-class.html). This way Widgets can subscribe/listen to the Sate and get notified whenever the State changes. This is how an implementation of Wisgen's favorite list would look like using Provider: _Favorites_ is the class we will use to provide our favorite list globally. The _notifyListeners()_ function will trigger rebuilds on all Widgets that listen to it.

```dart
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
_Code Snippet 22: Favorites Class that will be exposed through Provider Package [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Here we expose our Favorite class globally above _MaterialApp_ in the WidgetTree using the _ChangeNotifierProvider_ Widget:

```dart
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
_Code Snippet 23: Providing Favorites Globally [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

This is how listening to the Favorite class looks like. We use the _Consumer Widget_ to get access to the favorite list and everything below the Consumer Widget will be rebuild when the favorites list changes.

```dart
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
_Code Snippet 24: Consuming Provider in Favorite Button of Wisdom Card [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

### Why I decided against it
All in all, Provider is a great and easy solution to distribute State in a small Flutter application. But it is just that, a State Management solution and not an architecture [@hracekPragmaticStateManagement2019; @boelensFlutterBLoCScopedModel2019; @savjolovsFlutterAppArchitecture2019; @sullivanPragmaticStateManagement2019]. Just the Provider package alone with no pattern to follow or an architecture to obey will not lead to a clean and manageable application. But no worries, I did not teach you about the package for nothing. Because Provider is such an efficient and easy way to distribute State, the BLoC package [[@angelovBlocLibraryDart2019]](https://felangel.github.io/bloc/#/) uses it as an underlying technology for their approach.

## Redux
Redux [[@abramovRedux2015]](https://redux.js.org/) is an Architectural Pattern with a State Management solution. It was originally built for React [[@facebookReactNativeFramework2015]](https://facebook.github.io/react-native/) in 2015 by Dan Abramov. It was late ported to Flutter by Brian Egan in 2017 [[@eganFlutterReduxPackage2017]](https://pub.dev/packages/flutter_redux). In Redux, we use a _Store_ as one central location for all our Business Logic. This Store is put at the very top of our Widget Tree and then globally provided to all Widgets using an Inherited Widget. We extract as much logic from the UI as possible. It should only send actions to the store (such as user input) and display the interface dependant on the Current State of the Store. The Store has _reducer_ functions, that take in the previous State and an _action_ and return a new State. [@boelensFlutterBLoCScopedModel2019; @doughtieArchitectingReactiveFlutter2017; @eganKeepItSimple2018] So in Wisgen, the Dataflow would look something like this:

![ Wisgen Favorite List with Redux](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-redux.PNG)

_Figure 16: Wisgen Favorite List with Redux [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Our possible _actions_ are adding a new wisdom and removing a wisdom. So this is what our Action classes would look like:

```dart
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
_Code Snippet 25: Wisgen Redux Actions [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

This what the reducer function would look like:

```dart
List<Wisdom> favoriteReducer(List<Wisdom> state, FavoriteAction action) {
  if (action is AddFavoriteAction) state.add(action.favorite);
  if (action is RemoveFavoriteAction) state.remove(action.favorite);
  return state;
}
```
_Code Snippet 26: Wisgen Redux Reducer [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

And this is how you would make the Store globally available:

```dart
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
_Code Snippet 27: Providing Redux Store globally in Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Now the Favorite button from snippet 24 would be implemented like this:

```dart
...
Expanded(
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
        //Add/remove Wisdom to/from Favorites
        if (favorites.contains(wisdom)) store.dispatch(AddFavoriteAction(wisdom));
        else store.dispatch(RemoveFavoriteAction(wisdom));
      },
    ),
  ),
)
...
```
_Code Snippet 28: Consuming Redux Store in Favorite Button of Wisdom Card [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

### Why I decided against it
I went back and forth on this decision a lot. Redux is a great State Management solution with some clear guidelines on how to integrate it into a Reactive application [[@abramovThreePrinciplesRedux2015]](https://redux.js.org/introduction/three-principles). It also enables the implementation of a clean four-layered architecture (View - Store - Data) [[@eganKeepItSimple2018]](https://www.youtube.com/watch?v=zKXz3pUkw9A). Didier Boelens recommends to just stick to a Redux architecture if you are already familiar with its approach from other cross-platform development frameworks like React [[@facebookReactNativeFramework2015]](https://facebook.github.io/react-native/) and Angular [[@googlellcAngular2016]](https://angular.io/) and I very much agree with this advice [[@boelensFlutterBLoCScopedModel2019]](https://www.didierboelens.com/2019/04/bloc---scopedmodel---redux---comparison/). I have previously never worked with Redux and I decided to use BLoC over Redux because:

1. It was publicly endorsed by the Flutter Team on multiple occasions [@sullivanBuildReactiveMobile2018; @hracekPragmaticStateManagement2019; @sullivanTechnicalDebtStreams2018; @soaresFlutterAngularDartCode2018; @flutterdevteamFlutterState2019]
2. It also has clear architectural rules [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE)
3. It also enables the implementation of a clean four-layered architecture [[@suriArchitectYourFlutter2019]](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1)
4. It was developed by one of Flutter's Engineers [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE)
5. We don't end up with one giant store for the business logic out with multiple blocs with separate responsibilities [[@boelensFlutterBLoCScopedModel2019]](https://www.didierboelens.com/2019/04/bloc---scopedmodel---redux---comparison/)

# 220-BLoC

## Introduction
BLoC is an architectural pattern with a State Management solution originally designed by Paolo Soares in 2018 [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE). Its original purpose was to enable code sharing between Flutter [[@flutterdevteamFlutterFramework2018]]([@flutterdevteamFlutterFramework2018]) and Angular Dart [[@googlellcAngularDart2018]](https://angulardart.dev/) applications. Soares was working on applications in both frameworks at the time and he wanted a pattern that enabled him to hook up the same business logic to both Flutter and Angular Dart apps. His idea was to remove business logic from the UI as much as possible and extract it into its own classes, into BLoCs (Business Logic Components). The UI should only send events to BLoCs and display the interface based on the State of the BLoCs. Soares defined, that UI and BLoCs should only communicate through streams [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams). This way the developer would not need to worry about manually telling the UI to redraw. The UI can simply subscribe to a stream of State [[@flutterdevteamFlutterState2019]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) emitted by a BLoC and change based on the incoming State [@sullivanBuildReactiveMobile2018; @sullivanTechnicalDebtStreams2018; @soaresFlutterAngularDartCode2018; @boelensFlutterReactiveProgramming2018].

| 📙  | BLoC | Business Logic Component [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE) |
| --- | ---- | :---------------------------------------------------------------------------------------------------------- |

| 🕐  | TLDR | The UI should be kept free of business logic. The UI Only publishes _Events_ to a BLoC and subscribes to a stream of _State_ emitted by a BLoC |
| --- | ---- | :--------------------------------------------------------------------------------------------------------------------------------------------- |

![Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/bloc-architecture.png)

_Figure 17: Bloc turning input events to a stream of State [[@sullivanBuildReactiveMobile2018]](https://www.youtube.com/watch?v=RS36gBEp8OI)_

## Advantages of BLoC
That's all well and good, but why should you care? An application that follows the rules defined by the BLoC pattern will...

1. have all its business logic in one place 
2. have business logic that functions independently of the UI
3. have UI that can be changed without affecting the business Logic
4. have business logic that is easily testable
5. rely on few rebuilds, as the UI only rebuilds when the State related to that UI changes

[@boelensFlutterBLoCScopedModel2019; @savjolovsFlutterAppArchitecture2019; @soaresFlutterAngularDartCode2018; @boelensFlutterReactiveProgramming2018]

## Rules of the BLoC Pattern
To gain those promised advantages, you will have to follow these 8 rules Soares defined for the BLoC Pattern [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE):

#### Rules for the BLoCs

   1. Input/Outputs are simple **Sinks/Streams**
   2. All **dependencies** must be **injectable** and platform agnostic
   3. **No platform branching**
      - No `if(IOS) then doThis()`
   4. The actual implementation can be whatever you want if you follow 1-3

#### Rules for UI Classes

  1. Each _"Complex Enough"_ Widget has a related BLoC
     - You will have to define what _"Complex Enough"_ means for your app.
  2. Widgets **do not format the inputs** they send to the BLoC
     - Because formating is Business Logic
  3. Widgets should display the BLoCs **State/output with as little formatting as possible**
     - Sometimes a little formatting is inevitable, but things like currency formating is business logic and should be done in the BLoC 
  4. If you do have **platform branching**, It should be dependent on **a single BLoC bool State/output**

![Bloc Sink and Stream](https://github.com/Fasust/flutter-guide/wiki//images/bloc-sink-stream.png)

_Figure 18: How a BLoC looks like [[@boelensFlutterReactiveProgramming2018]](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/)_

## Implementation
Alright, Now that you know what the BLoC pattern is, let's have a look at how it looks in practice. You will see some strong similarities to the implementation of Redux [[@abramovRedux2015]](https://redux.js.org/) here. That is just because the two patterns are very similar in general. I am using the BLoC package [[@angelovBlocLibraryDart2019]](https://felangel.github.io/bloc/#/) for Flutter by Felix Angelov, as it removes a lot of the boilerplate code we would have to write if we would implement our own BLoCs from scratch. I am going to use the same example of _App State_ as I did in the [previous chapter][statemng]: The favorite list in Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen). First, let's have a look at how the Bloc Pattern will interact with Wisgen on a more abstract scale:

![Bloc and Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-pagetree-bloc.PNG)

_Figure 19: Bloc and Wisgen Widget Tree [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

These are the events that can be sent to the BLoC by the UI. Again, this is very similar to the _actions_ in our Redux implementation:

```dart
///The Favorite BLoC can handle 2 types of Events: Add and Remove.
///These events add and remove Wisdoms from the Favorite List respectively.
@immutable
abstract class FavoriteEvent {
  final Wisdom _favorite;
  get favorite => _favorite;

  FavoriteEvent(this._favorite);
}

class AddFavoriteEvent extends FavoriteEvent {
  AddFavoriteEvent(Wisdom favorite) : super(favorite);
}

class RemoveFavoriteEvent extends FavoriteEvent {
  RemoveFavoriteEvent(Wisdom favorite) : super(favorite);
}
```
_Code Snippet 29: Favorite Event in Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Now the arguably most interesting part of an implementation of the BLoC patter, the BLoC class itself. We extend the BLoC class provided by the Flutter BLoC package. It takes in the type of the _events_ that will be sent to the BLoC and the type of the _State_ that should be emitted by the BLoC `Bloc<Event, State>`:

```dart
///The FavoriteBLoC is Responsible for Keeping track of the
///Favorite List. It receives Events to Add and remove Favorite
///Wisdoms and Broadcasts the Complete List of Favorites.
class FavoriteBloc extends Bloc<FavoriteEvent, List<Wisdom>> {

  @override
  List<Wisdom> get initialState => List<Wisdom>();

  ///Takes in each event that is send to the BLoC and emits new State
  ///based on that event.
  @override
  Stream<List<Wisdom>> mapEventToState(FavoriteEvent event) async* {
    List<Wisdom> newFavorites = new List()..addAll(currentState);

    if (event is AddFavoriteEvent) newFavorites.add(event.favorite);
    if (event is RemoveFavoriteEvent) newFavorites.remove(event.favorite);

    yield newFavorites;
  }
}
```
_Code Snippet 30: Favorite BLoC in Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

As I mentioned before, the BLoC package for Flutter uses the Provider package [[@rousseletProviderFlutterPackage2018]](https://pub.dev/packages/provider). This means that we can provide our BLoC to the rest of our Widget Tree in the same way we learned in the chapter [State Management Alternatives][statemng]. By the rule of _"lifting State up"_ we have to place the favorite BLoC at the lowest common ancestor of all Widgets that need access to it. So in our case at _MaterialApp_:

```dart
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
_Code Snippet 31: Providing a BLoC Globally in Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Now we can dispatch events and subscribe to a BLoC. This is the favorite button in Wisgen. It changes shape and color based on the State emitted by the FavoriteBLoC and it dispatches events to the BLoC to add and remove favorites. The _wisdom_ object is the wisdom displayed on the Card Widget.

```dart
...
Expanded(
  flex: 1,
  //This is where we Subscribe to the FavoriteBLoC
  child: BlocBuilder<FavoriteBloc, List<Wisdom>>(
    builder: (context, favorites) => IconButton(
      //Display Icon Button depending on current State
      //Re-Build when favorite list changes
      icon: Icon(favorites.contains(wisdom)
          ? Icons.favorite
          : Icons.favorite_border),
      color: favorites.contains(wisdom) 
          ? Colors.red 
          : Colors.grey,
      onPressed: () {
        //Grab FavoriteBloc though Buildcontext
        FavoriteBloc favoriteBloc = BlocProvider.of<FavoriteBloc>(context);
        
        //Add/remove Wisdom to/from Favorites (dispatch events)
        if (favorites.contains(wisdom)) favoriteBloc.dispatch(RemoveFavoriteEvent(wisdom));
        else favoriteBloc.dispatch(AddFavoriteEvent(wisdom));  
      },
      padding: EdgeInsets.only(right: _smallPadding),
    ),
  ),
)
...
```
_Code Snippet 32: Accessing a BLoC in Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

## Layered Architecure
Now that we understand how to implement the BLoC pattern [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE), lets' have a look at how we can use it to achieve a four-layered architecture with one way dependencies [[@suriArchitectYourFlutter2019]](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1):

<img src="https://github.com/Fasust/flutter-guide/wiki//images/bloc-my-layers.png" height="500" alt="Bloc Architecture with Layers">

_Figure 20: Four-Layered BLoC Architecture_

### UI Layer
This is the layer that our user directly interacts with. It is the Widget Tree of our Application, all Widgets of our app sit here. We need to keep this layer as _stupid_ as possible, No business logic and only minor formating.

### Business Logic Layer
This is where all our BLoCs reside. All our business logic sits in this layer. The communication between this layer and the _UI Layer_ should be limited to sinks and streams:

![Widget BLoC Communication](https://github.com/Fasust/flutter-guide/wiki//images/widget-bloc-communication.PNG)

_Figure 21: Widget BLoC Communication_

For this Layer, all plattform specific dependencies should be injectable. To achieve this, the Flutter community [@suriArchitectYourFlutter2019; @eganFlutterArchitectureSamples2017; @angelovBlocLibraryDart2019; @bizzottoWidgetAsyncBlocServicePracticalArchitecture2019] mostly uses the _Repository Patter_ [[@garlanIntroductionSoftwareArchitecture1994]](https://dl.acm.org/citation.cfm?id=865128) or as _"Uncle Bob"_ would say: _Boundary Objects_ [[@martinPrinciplesCleanArchitecture2015]](https://www.youtube.com/watch?v=o_TH-Y78tt4). Even if this pattern is not an explicit part of BLoC, I personally think it is a very clean solution. Instead of having BLoCs directly depend on plattform specific interfaces, we create simple _Repository_ interfaces for the BLoCs to depend on:

```dart
///Interface for a Generic List Provider that fetches a given amount of T
abstract class DataRepository<T>{
  Future<List<T>> fetch(int amount);
}
```
_Code Snippet 33: Wisgen Plattform Agnostic Repository [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

The actual implementation of the _Repository_ can then be injected into the BLoC.

### Repository Layer
This Layer consist of plattform agnostic interfaces. Things like _Data Base_ or _Service_.

### Data Layer
These are the actual implementations of our _Repositories_. Platform specific things like a Database connector or a class to make API calls.

## Architecture in Practice 
To give you a better understanding of how this architecture works in practice, I will walk you through how Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen) is build using the BLoC Pattern and a Four-layered architecture.

![Wisgen Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/wisgen_depencies.PNG)

_Figure 22: Wisgen Architecture with Dependencies [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

In the UI Layer, we have all the Widgets that make up Wisgen. Three of those actually consume State from the BLoC Layer, so those are the only ones I put in figure 22. The _Wisdom Feed_ displays an infinite list of wisdoms. Whenever the user scrolls close to the bottom of the list, the Wisdom Feed sends a _Request-Event_ to the Wisdom BLoC [[@angelovFlutterInfiniteList2019]](https://felangel.github.io/bloc/#/flutterinfinitelisttutorial). This event causes the _Wisdom BLoC_ to fetch more data from its Repository. You can see the _Repository_ interface in snippet 33. This way the Wisdom BLoC just knows it can fetch some data with its Repository and it does not care where the data comes from or how the data is fetched. In our case, the Repository could be implemented to either load some wisdoms from a local list or fetch some wisdoms from an API. I already covered the implementation of the API Repository class in the chapter [Asynchronous Flutter][async] if you want to remind yourself again. When the Wisdom BLoC receives a response from it's Repository, it publishes the new wisdoms to its Stream [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams) and all listening Widgets will be notified. 

![Wisgen Bloc Architecture Dataflow](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dataflow.png)

_Figure 23: Wisgen Dataflow [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

I already covered how the favorite list works in detail in this chapter, so I won't go over it again. The _Storage BLoC_ keeps a persistant copy of the favorite list on the device. It recievce a _Load-Event_ once on start-up, loads the old favorite list from its _Storage_, and adds it to the _Favortie BLoC_ though _Add-Events_. It also listens to the _Favorite BLoC_ and updates the persistant copy of the favorite list every time the _Favorite Bloc_ emits a new State:

```dart
//Because this BLoC doesn't need to emit Sate, I used a Single Enum
enum StorageState {idle} 
//Only 2 events that both don't need to carry additional data
enum StorageEvent {load, wipe} 

///The StorageBLoC is injected with a FavoriteBLoC on Creation.
///It subscribes to the FavoriteBLoC and writes the Favorite List
///to a given Storage device every time a new State is emitted by the FavoriteBLoC.
///
///When the StorageBLoC receives a load Event, it loads a list of Wisdoms from a given
///Storage device and pipes it into the FavoriteBLoC
///
///Used to keep a Persistent copy of the Favorite List on the Device
class StorageBloc extends Bloc<StorageEvent, StorageState> {
  Storage _storage = new SharedPreferenceStorage();
  FavoriteBloc _observedBloc;

  StorageBloc(this._observedBloc) {
    //Subscribe to BLoC
    _observedBloc.state.listen((state) async {
      await _storage.save(state);
    });
  }

  @override
  StorageState get initialState => StorageState.idle;

  @override
  Stream<StorageState> mapEventToState(StorageEvent event) async* {
    if (event == StorageEvent.load) await _load();
    if (event == StorageEvent.wipe) _storage.wipeStorage();
  }

  _load() async {
    List<Wisdom> loaded = await _storage.load();

    if (loaded == null || loaded.isEmpty) return;

    loaded.forEach((f) {
      _observedBloc.dispatch(AddFavoriteEvent(f));
    });
  }

  //Injection
  set storage(Storage storage) => _storage = storage;
  set observedBloc(FavoriteBloc observedBloc) => _observedBloc = observedBloc;
}
```
_Code Snippet 34: Wisgen Storage BLoC [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

_Storage_ is also a plattform agnostic interface and it looks like this:

```dart
///Interface for a Generic List Provider
abstract class Storage<T>{
  Future<List<T>> load();
  save(List<T> data);

  ///Wipe the Storage Medium
  wipeStorage();
}
```
_Code Snippet 35: Wisgen Plattform Agnostic Interface Storage [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

In Wisgen, I built an implementaion of _Storage_ that communicates with Androids Shared Preferences [[@googlellcSharedPreferences2011]](https://developer.android.com/reference/android/content/SharedPreferences) and saves the favorite list as a JSON:

```dart
///A Provider of Shared Preferences, a small, local, persistent key value store
class SharedPreferenceStorage implements Storage<Wisdom> {
  ///Key is used to access store
  static const String _sharedPrefKey = "wisgen_storage";

  @override
  Future<List<Wisdom>> load() async {
    final prefs = await SharedPreferences.getInstance();
    List<String> strings = prefs.getStringList(_sharedPrefKey);

    if (strings == null || strings.isEmpty) return null;

    //Decode all JSON Strings we fetched from the Preferences and add them to the Result
    List<Wisdom> wisdoms = new List();
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
    List<String> strings = new List();
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
_Code Snippet 36: Wisgen Plattform Agnostic Interface Storage [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_


# 300-Testing

## Introduction

//Last Snip 36 
//Last Fig 23

Testing has become an essential part of developing a large scale application and there is strong evidence that writing tests leads to a higher code quality [[@georgeInitialInvestigationTest2003]](http://doi.acm.org/10.1145/952532.952753). This chapter aims to give you a brief introduction to how testing in Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) works and more specifcally, how to test an app that implements the BLoC Pattern [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE).

## Types of Tests in Flutter
Flutters official test library [[@dartteamTestDartPackage2019]](https://pub.dev/packages/test) differentiates between three types of tests:

#### Unit Tests 
Unit Test can be run very quickly. They can test any function of your app, that does not require the rendering of a Widget [[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). Their main use-case is to test business logic or in our case: BLoCs [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE).

#### Widget Tests
Widget Tests are used to test small Widget Sub-Trees of your application. They run relatively quickly and can test the behavior of a given UI [[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

#### Integration Test (Driver Tests)
Integration Test/Driver Tests run your entire application in a virtual machine or on a physical device. They can test user-journeys and complete use-cases. They are very slow and _"prone to braking"_[[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

![Flutter Test Comparison](https://github.com/Fasust/flutter-guide/wiki//images/test-comp.PNG)

_Figure XXX: Flutter Test Comparison [[@flutterdevteamTestingFlutterApps2018]](https://flutter.dev/docs/testing)_

## Writing Unit Tests
I will focus on _Unit Tests_ for this guide. The Flutter Team recommends that the majority of Flutter tests should be Unit Test [@hracekTestingFlutterApps2019; @flutterdevteamTestingFlutterApps2018]. This is because the fact that they are quick to write and quick to execute makes up for their relatively low _confidence_. In addition to this, because we are using the BLoC Pattern, our UI shouldn't contain that much testable code anyways. Or to paraphrase the BLoC pattern creator: We keep our UI so _stupid_ we don't need to test it [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE). First we have to import the test library [[@dartteamTestDartPackage2019]](https://pub.dev/packages/test) and the mockito package [[@fibulwinterMockitoDartPackage2019]](https://pub.dev/packages/mockito) in our _pubspec.yaml_:

```yaml
dev_dependencies:
  mockito: ^4.1.1
  flutter_test:
    sdk: flutter
```
_Code Snippet XXX: Pubspec.yaml Test Imports_

_flutter\_test_ offers the core testing capabilities of Flutter, _mockito_ is used to mock up dependencies. Now place a new directory called _"tests"_ on the root level of our app directory:

![Wisgen Test Directory](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-test-dir.PNG)

_Figure XXX: Wisgen Test Directory [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

| ⚠   | All testfiles have to end with the postfix "_test.dart" to be recognized by the framework [[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). |
| --- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

Now we can start writing our tests. For this example, I will test the favortie BLoC of Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen):
```dart
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
_Code Snippet XXX: Wisgen Favorite BLoC Tests 1 [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

We can use the _group()_ function to group realted tests together. This way the output if our tests is more neatly formated [[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). _setUp()_ is called once before every test, so it is perfect for initializing our BLoC [[@angelovUnitTestingBloc2019]](https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86). _tearDown()_ is called after every test, so we can use it to dispose our BLoC. The _test()_ function takes in a name and a callback with the test. In our test, we check if the state of the favorite BloC after initalization is an empty list. _expect()_ takes in the actual value and the value that is expected: `expect(actual, matcher)`. 

### Testing Streams
Now a more relevant topic when working with the BLoC Pattern, the testing of Streams [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams):

```dart
void main() {

  group('Favorite Bloc', () {
    FavoriteBloc favoriteBloc;

    setUp((){...});

    tearDown((){...});
    
    
    test('Initial State is an empty list', () {...});

    test('Stream many events and see if the State is emitted in correct order', () {
      //Set Up
      Wisdom wisdom1 = Wisdom(id: 1, text: "Back up your Pictures", type: "tech");
      Wisdom wisdom2 = Wisdom(id: 2, text: "Wash your ears", type: "Mum's Advice");
      Wisdom wisdom3 = Wisdom(id: 3, text: "Travel while you're young", type: "Grandma's Advice");

      //Testing
      favoriteBloc.dispatch(AddFavoriteEvent(wisdom1));
      favoriteBloc.dispatch(AddFavoriteEvent(wisdom2));
      favoriteBloc.dispatch(RemoveFavoriteEvent(wisdom1));
      favoriteBloc.dispatch(AddFavoriteEvent(wisdom3));

      //Result
      expect( 
          favoriteBloc.state,
          emitsInOrder([
            List(), //BLoC Library BLoCs emit their initial State on creation
            List()..add(wisdom1),
            List()..add(wisdom1)..add(wisdom2),
            List()..add(wisdom2),
            List()..add(wisdom2)..add(wisdom3)
          ]));
    });
  });
}
```
_Code Snippet XXX: Wisgen Favorite BLoC Tests 2 [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

In this test, we create three wisdoms and add/remove them from the favorite BLoC by sending the corresponding events. We then wrap our _matcher_ in the _emitsInOrder()_ function. This tells the framework that we are working with a stream and looking for specific set of events to be emitted in order [[@angelovUnitTestingBloc2019]](https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86).

### Mockito

```dart
void main() {
  group('Wisdom Bloc', () {
    WisdomBloc wisdomBloc;
    MockRepository mockRepository;
    MockBuildContext mockBuildContext;

    setUp(() {
      wisdomBloc = WisdomBloc();
      mockRepository = MockRepository();
      mockBuildContext = MockBuildContext();

      wisdomBloc.repository = mockRepository;
    });

    tearDown(() {
      //Run after each test
      wisdomBloc.dispose();
    });

    test('Send Fetch Event and see if it emits correct wisdom', () {
      List<Wisdom> fetchedWisdom = [
        Wisdom(id: 1, text: "Back up your Pictures", type: "tech"),
        Wisdom(id: 2, text: "Wash your ears", type: "Mum's Advice"),
        Wisdom(id: 3, text: "Travel while you're young", type: "Grandma's Advice")
      ];

      List expectedStates = [
        //BLoC Library BLoCs emit their initial State on creation
        IdleWisdomState(new List()), 
        IdleWisdomState(fetchedWisdom)
      ];

      when(mockRepository.fetch(20, mockBuildContext))
          .thenAnswer((_) async => fetchedWisdom);

      expectLater(wisdomBloc.state, emitsInOrder(expectedStates));

      wisdomBloc.dispatch(FetchEvent(mockBuildContext));
    });
  });
}
```
_Code Snippet XXX: Wisgen Wisdom BLoC Tests with Mockito [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

```dart
///The Wisdom BLoC has 2 States: Loaded and Error
///We can infer it is loading when we are not reviving new items through the stream
@immutable
abstract class WisdomState extends Equatable {}

///Broadcasted on Network Error
class ErrorWisdomState extends WisdomState {
  final Exception exception;
  ErrorWisdomState(this.exception);

  @override
  List<Object> get props => [exception];
}

///Normal State that holds favorite list.
///When BLoC receives a FetchEvent during this State, 
///it fetches more wisdom and emits a new IdleWisdomState 
///with more wisdoms
class IdleWisdomState extends WisdomState {
  final List<Wisdom> wisdoms;
  IdleWisdomState(this.wisdoms);

  @override
  List<Object> get props => wisdoms;
}
```
_Code Snippet XXX: Wisgen Wisdom States with Equatable [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

- testing a bloc
- Dependency injection problems in Flutter

# 400-Conventions
## Mastering the Widget Tree
## Naming conventions
## File structure with BLoC

# 500-Conclusion
## My Opinion of Flutter
## Should you use it?

# 600-References

