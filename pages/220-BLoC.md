Page Table of Contents
- [Introduction](#introduction)
- [Rules](#rules)
- [Implementation](#implementation)
- [Layered Architecure](#layered-architecure)
- [Architecture in Practice](#architecture-in-practice)

## Introduction

The BLoC Pattern is a State Management solution originally designed by Paolo Soares in 2018 [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE). It’s original purpose was to enable code sharing between Flutter [(Flutter Dev Team 2018h)](%5B@flutterdevteamFlutterFramework2018%5D) and Angular [(Google LLC 2016)](https://angular.io/) Dart applications. Soares was working on applications in both frameworks at the time and he wanted a pattern that enabled him to hook up the same business logic to both Flutter and Angular apps. His Idea was to remove business logic from the UI as much as possible and extract it into it’s own class, a BLoC (Business Logic Component). The UI should only send events to the BLoC, and display the interface based on the state of the BLoC. Soares defined, that UI and BLoC should only communicate through streams [(Dart Team 2019b)](https://dart.dev/tutorials/language/streams). This way the developer does not need to worry about manually telling the UI to redraw. The UI can simply subscribe to a stream of State emitted by the BLoC and change based on the incoming state (Sullivan and Hracek 2018b, 2018a; Soares 2018; Boelens 2018a).

| BLoC | Business Logic Component |
| ---- | :----------------------- |

| TLDR | The UI should be kept free of business logic. The UI Only publishes *Events* to the BLoC and subscribes to a stream of *State* emitted by the BLoC |
| ---- | :------------------------------------------------------------------------------------------------------------------------------------------------- |

![Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/bloc-architecture.png)

*Figure XXX: Bloc Architecture [(Sullivan and Hracek 2018b)](https://www.youtube.com/watch?v=RS36gBEp8OI)*

## Rules

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

## Implementation

  - **Build Interface code how you want it to look like -\> then make it work**
  - Wisgen Exampels

## Layered Architecure

![Bloc Sink and Stream](https://github.com/Fasust/flutter-guide/wiki//images/bloc-sink-stream.png)

*Figure XXX: Bloc Sink and Stream [(Boelens 2018a)](https://www.didierboelens.com/2018/08/reactive-programming---streams---bloc/)*

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

## Architecture in Practice

![Wisgen Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dependencies.png)

*Figure XXX: Wisgen Bloc Architecture [(Faust 2019)](https://github.com/Fasust/wisgen)*

![Wisgen Bloc Architecture Dataflow](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dataflow.png)

*Figure XXX: Wisgen Bloc Architecture Dataflow [(Faust 2019)](https://github.com/Fasust/wisgen)*

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/300-Testing">Next Chapter: Testing ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>