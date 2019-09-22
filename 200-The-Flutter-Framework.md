Page Table of Contents
- [Introduction](#introduction)
- [Under the Hood](#under-the-hood)
  - [Full Native Approach](#full-native-approach)
  - [Embedded WebApp Approach](#embedded-webapp-approach)
    - [Bridges](#bridges)
  - [Reactive View Approach](#reactive-view-approach)
  - [Flutters Approach](#flutters-approach)
- [Flutter Compiler](#flutter-compiler)
- [Hot Reload](#hot-reload)
- [The Widget Tree](#the-widget-tree)
- [Stateless vs. Stateful](#stateless-vs-stateful)
- [[TEMP] Requirements for this Chapter](#temp-requirements-for-this-chapter)
- [References](#references)

## Introduction
This Chapter will give you a basic understanding of how the Flutter Framework works as a whole. I will showcase the difference of Flutter to other Cross-Platform approaches and how Flutter works _under the hood_. You will also be introduced to the concepts of _state_ and flutters way of rendering an app as a tree of _widgets_.
  
## Under the Hood
Flutter is a framework for cross-plattform native development. But what exactly does that mean? It means that it promises Native App performance while still compiling apps for multiple platforms from a single codebase. The best way to understand how flutter achieves this, is to compare it to other mobile development approaches.

### Full Native Approach
![Native app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/native-rendering.png)

_Figure 1: [Native app rendering](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

The classic way to build a mobile app, would be to write native code for each plattform you want to support. I.E. One for IOS, one for Android and so on. In this approach your app will be written in a plattform specific language and render through plattform specific widgets and a plattform specific engine. During the development you have direct access to Platform specific services and sensors.

### Embedded WebApp Approach
![Embedded Web App rendering](https://github.com/Fasust/flutter-guide/wiki//.images/webview-rendering.png)

_Figure 2: [Embedded WebApp rendering](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

Embedded WebApps where the first approach to cross-plattform development. You would simply build your application with HTML, CSS and JS and then have it render through a Native WebView. The Problem here is, that developers are limited to the Web Technology Stack and that communication between the app and native services would always have to run through a _bridge_. 

#### Bridges
Bridges connect components with one another. These components can be build in the same or different programming languages.

### Reactive View Approach
![Reactive app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/reactive-rendering.png)

_Figure 3: [Reactive app rendering](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

Apps build with reactive Frameworks (like React Native) are mostly written in a plattform independent language like JavaScript. The JavaScript code then sends information on how UI components should be displayed to the native environment. This communication runs through a _bridge_. So we end up with native widgets that are controller through JavaScript. The main problem here, is that the communication through the _bridge_ is a bottleneck which can lead to performance issus.

### Flutters Approach
![Flutter app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/flutter-rendering.png)

_Figure 4: [Flutter app rendering](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

Flutters approach is to move the entire rendering process into the app. The rendering runs through Flutters own engine and uses flutters own widgets. It only needs a canvas to display the rendered frames on and system events/input it can then forward to your app. The framework also provides a way to access services and sensors through platform independent interfaces. This way the _bridging_ between the app and the native environment is kept to a minimum which removes that bottleneck. 
You might think that keeping an entire rendering engine inside your app would lead to huge APKs, but as of 2019 the compressed framework is only 4.3MB (Flutter FAQ). 

![Flutter Framework Architecture](https://github.com/Fasust/flutter-guide/wiki//.images/flutter-architecture.png)

_Figure 5: [Flutter Framework Architecture](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

| 🕐 TLDR | Flutter uses it's own engine instead of using the native one. The native environment only renders the finished frames. |
| ------- | :--------------------------------------------------------------------------------------------------------------------- |

## Flutter Compiler
One additional advantage of Flutter, is that is comes with two different compilers. A JIT Compiler (Just in time) and a AOT Compiler (Ahead of Time). The following table will showcase the advantage of each:

| Compiler      | What is does                                                                                         | When it's used     |
| :------------ | :--------------------------------------------------------------------------------------------------- | :----------------- |
| Just in Time  | Only re-compiles files that have changed. Preserves App-State during rebuilds. Enables _Hot-reload_. | During Development |
| Ahead of Time | Compiles all dependencies ahead of time. The output app is faster.                                   | For Release        |

_Table 1: Flutters 2 Compliers_
## Hot Reload
Hot Reload is a feature Web Developers are already very familiar with. It essentially means, that your changes in the code are displayed in the running application near instantaneously. Thanks to Flutters JIT Complier, it is also able to provide this feature.

![Hot Reload](https://github.com/Fasust/flutter-guide/wiki//.images/hot-reload.gif)

_Figure 6: [Hot Reload](https://flutter.dev/docs/development/tools/hot-reload)_

## The Widget Tree
- Mostly idempotent 
- _App = buildMethods(state)_
  
## Stateless vs. Stateful
- What is it?
- Where is it?

<p align="center"><a href="#">Back to Top</a></center></p>

## [TEMP] Requirements for this Chapter
- Flutter Architecture
- Widget Tree
- Stateless vs. Stateful
- State
- Idempotent

---
## References 
How Is Flutter Different for App Development. Google Developers Official Youtube Channel, 2019. https://www.youtube.com/watch?v=l-YO9CmaSUM&feature=youtu.be.

Kol, Tal. “Performance Limitations of React Native and How to Overcome Them.” Conference Talk presented at the React Amsterdam, Amsterdam, 2017. https://www.youtube.com/watch?v=psZLAHQXRsI.

Leler, Wm. “What’s Revolutionary about Flutter.” Blog. hackernoon, 2017. https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514.

Moore, Kevin, and Bob Nystrom. “Dart: Productive, Fast, Multi-Platform - Pick 3.” Conference Talk presented at the Google I/O’19, Mountain View, CA, May 9, 2019. https://www.youtube.com/watch?v=J5DQRPRBiFI.

Stoll, Scott. “In Plain English: So What the Heck Is Flutter and Why Is It a Big Deal?” Blog. Medium, 2018. https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a.
