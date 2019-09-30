# Introduction
Page Table of Contents
- [Introduction](#introduction)
  - [The Goal of this Guide](#the-goal-of-this-guide)
  - [Who is this Guide for?](#who-is-this-guide-for)
  - [Topics that will be covered](#topics-that-will-be-covered)
  - [Creation Context](#creation-context)
  - [Structure](#structure)
  - [My Sources](#my-sources)
  - [References](#references)

## The Goal of this Guide
This guide aims to bridge the gap between the absolute [Flutter (Flutter Dev Team 2018a)](https://flutter.dev/) basics and clean, structured Flutter development. It should bring you from the basics of knowing how to build an app with Flutter to an understanding of how to do it _properly_. Or at least show you one possible way to make large scale Flutter projects clean and manageable.

## Who is this Guide for?
For people with a basic knowledge of the Flutter Framework. I recommend following this [tutorial by the Flutter team (Flutter Dev Team 2018b)](https://flutter.dev/docs/get-started/codelab). It will walk you through developing your first flutter application. You should also have a basic understanding of the [Dart programming language (Google LLC 2019a)](https://dart.dev/). No worries, it is very similar to [Java (Oracle 1996)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), [Kotlin (Jet Brains 2017)](https://kotlinlang.org/) and [JavaScript (ECMA 1997)](https://www.ecma-international.org/publications/standards/Ecma-262.htm). So if you know 1 or 2 of those languages you should be fine. Lastly, for the Architecture chapter, you should have a basic understanding of [Data Streams (Google LLC 2019b)](https://dart.dev/tutorials/language/streams).

## Topics that will be covered 
- A brief introduction to the Flutter Framework in general. How it works _under the hood_ and its underlying structure.
- One possible architecture for your Flutter app and how to implement it ([BLoC (Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE))
- How to test your app
- Some conventions and best practices for Dart, BLoC and the Flutter Framework
- My personal opinion of the framework

## Creation Context
This guide was written by a student in the Bachelor of Science Program “Computer Science and Media Technology” at [Technical University Cologne (Technical University Cologne 2019)](https://www.th-koeln.de/en/homepage_26.php), and it was created for one of the modules in that Bachelor. In addition to this, the guide was written in collaboration with [Capgemini Cologne (Capgemini 2019)](https://www.capgemini.com/us-en/). Capgemini released a [guide on building an application in Angular (Ambuludi, Linares, and Contributors 2019)](https://github.com/devonfw/devon4ng) in May of 2019, this guide is meant to be the the flutter version of that.

## Structure
The guide is designed to be read in order, from Chapter 0 (this one) to Chapter 5. Code examples throughout the chapters will mainly be taken from [Wisgen (Faust 2019)](https://github.com/Fasust/wisgen), an example Flutter Application that was specifically built for the purposes of this guide.

## My Sources 
I am basing this guide on a combination of conference talks, blog articles by respected Flutter developers, official documentaions, scientific papers that cover cross-platform mobile development in gerneral and many other sources. To better understand all the theory, I also developed my own app using the Flutter Framework and the [BLoC Pattern (Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE) ([Wisgen (Faust 2019)](https://github.com/Fasust/wisgen)). Every page in this guide has all the references I used for that specific page listed at the very bottom under the heading "References". I also listed all sources used in the entire guide in chapter [6 "References"](https://github.com/Fasust/flutter-guide/wiki/600-References).

<p align="center"><a href="#">Back to Top</a></center></p>

---
## References
Ambuludi, Juan Andrés, Santos Jiménez Linares, and Contributors. 2019. “Capgemini Angular Guide.” Guide. GitHub. 2019. https://github.com/devonfw/devon4ng.

Capgemini. 2019. “Capgemini - Home Page.” Home Page. 2019. https://www.capgemini.com/us-en/.

ECMA. 1997. JavaScript ECMA Standard (version 10). Cross-platform. JavaScript. ECMA. https://www.ecma-international.org/publications/standards/Ecma-262.htm.

Faust, Sebastian. 2019. Wisgen. Cross-platform. Dart. Germany. https://github.com/Fasust/wisgen.

Flutter Dev Team. 2018a. The Flutter Framework (version 1.9). Cross-platform. Dart. Google LLC. https://flutter.dev/.

———. 2018b. “Write Your First Flutter App.” Guide. 2018. https://flutter.dev/docs/get-started/codelab.

Google LLC. 2019a. “Dart Programming Language.” Documentation. 2019. https://dart.dev/.

———. 2019b. “Dart Streams.” Documentation. 2019. https://dart.dev/tutorials/language/streams.

Jet Brains. 2017. Kotlin SDK (version 1.3). Cross-paltform. Kotlin. Jet Brains. https://kotlinlang.org/.

Oracle. 1996. Java JDK (version 8). Cross-platform. Java. Oracle. https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html.

Soares, Paolo. 2018. “Flutter / AngularDart – Code Sharing, Better Together.” Conference Talk presented at the DartConf 2018, Google Campus, LA, January 25. https://www.youtube.com/watch?v=PLHln7wHgPE.

Technical University Cologne. 2019. “Technical University Cologne.” Home Page. 2019. https://www.th-koeln.de/en/homepage_26.php.

