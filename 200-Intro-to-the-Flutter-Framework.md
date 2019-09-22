Page Table of Contents
- [[TEMP] Requirements for this Chapter](#temp-requirements-for-this-chapter)
- [Introduction](#introduction)
- [Under the Hood](#under-the-hood)
  - [Full Native](#full-native)
  - [Embedded Web apps](#embedded-web-apps)
  - [Reactive Views](#reactive-views)
  - [Flutter](#flutter)
- [The Widget Tree](#the-widget-tree)
- [Stateless vs. Stateful](#stateless-vs-stateful)
- [References](#references)

## [TEMP] Requirements for this Chapter
- Flutter Architecture
- Widget Tree
- Stateless vs. Stateful
- State
- Idempotent

## Introduction
This Chapter will give you a basic understanding of how the Flutter Framework works as a whole. I will showcase the difference of Flutter to other Cross-Platform approaches and how Flutter works _under the hood_. You will also be introduced to the concepts of _state_ and flutters way of rendering an app as a tree of _widgets_.
  
## Under the Hood
Flutter is a framework for cross-plattform native development. But what exactly does that mean? It means that it promises Native App performance while still compiling apps for multiple platforms from a single codebase. The best way to understand how flutter achieves this, is to compare it to other mobile development approaches.

### Full Native 
![Native app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/native-rendering.png)
_Figure 1: [Native app rendering](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

The classic way to build a mobile app, would be to write native code for each plattform you want to support. I.E. One for IOS, one for Android and so on. In this approach your app will be written in a plattform specific language and render through plattform specific widgets and a plattform specific engine. During the development you have direct access to Platform specific services and sensors.

### Embedded Web apps
![Embedded Web app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/webview-rendering.png)
_Figure 1: [Embedded Web app rendering](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

### Reactive Views 
![Reactive app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/reactive-rendering.png)
_Figure 1: [Reactive app rendering](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

### Flutter
![Flutter app rendering](https://github.com/Fasust/flutter-guide/wiki//.images/flutter-rendering.png)
_Figure 2: [Flutter app rendering](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_

Flutters approach is to move the entire rendering into your app. This way it does not relay on plattform specific widgets. It only needs a canvas to display the rendered frames on and system events/input it can then forward to your app. The framework also provides a way to access services and Sensors through platform independent interfaces.

![Flutter Architecture](https://github.com/Fasust/flutter-guide/wiki//.images/flutter-architecture.png)
_Figure 1: [The Flutter Architecture](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)_


## The Widget Tree
- Mostly idempotent 
- _App = buildMethods(state)_
  
## Stateless vs. Stateful
- What is it?
- Where is it?

<p align="center"><a href="#">Back to Top</a></center></p>

---
## References 