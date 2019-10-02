Page Table of Contents
- [Introduction](#introduction)
  - [Full Native Approach](#full-native-approach)
  - [Embedded WebApp Approach](#embedded-webapp-approach)
    - [Bridges](#bridges)
  - [Reactive View Approach](#reactive-view-approach)
  - [Flutters Approach](#flutters-approach)
- [Flutter Compiler](#flutter-compiler)
- [Hot Reload](#hot-reload)

## Introduction

Flutter [(Flutter Dev Team 2018e)](https://flutter.dev/) is a framework for cross-platform native development. But what exactly does that mean? It means that it promises Native App performance while still compiling apps for multiple platforms from a single codebase. The best way to understand how flutter achieves this, is to compare it to other mobile development approaches.

### Full Native Approach

![Native app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/native-rendering.png)

*Figure 1: Native app rendering [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

The classic way to build a mobile app, would be to write native code for each platform you want to support. I.E. One for IOS [(Apple 2010)](https://developer.apple.com/ios/), one for Android [(Google LLC 2008)](https://developer.android.com/) and so on. In this approach your app will be written in a platform specific language and render through platform specific widgets and a platform specific engine. During the development you have direct access to platform specific services and sensors (Google LLC 2019; Stoll 2018; Leler 2017). But you will have to build the same app multiple times, which effectively doubles your workload.

### Embedded WebApp Approach

![Embedded Web App rendering](https://github.com/Fasust/flutter-guide/wiki//.images/webview-rendering.png)

*Figure 2: Embedded WebApp rendering [(Leler 2017)](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Embedded WebApps where the first approach to cross-platform development. You would simply build your application with HTML, CSS and JavaScript and then have it render through a native WebView(Google LLC 2019; Leler 2017). The Problem here is, that developers are limited to the web technology stack and that communication between the app and native services would always have to run through a *bridge* [(Stoll 2018)](https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a).

#### Bridges

Bridges connect components with one another. These components can be build in the same or different programming languages [(Adinugroho, Reina, and Gautama 2015)](http://www.sciencedirect.com/science/article/pii/S1877050915020979).

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

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively">Next Chapter: Thinking Declaratively ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>