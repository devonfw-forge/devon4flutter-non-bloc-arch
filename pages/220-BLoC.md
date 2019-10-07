Page Table of Contents
- [Introduction](#introduction)
- [Rules](#rules)
- [Implementation](#implementation)
- [Layered Architecure](#layered-architecure)
- [Architecture in Practice](#architecture-in-practice)

## Introduction

  - Goal:
      - Extract the Logic into a class that can be calls from 2 different independent interfaces (AngularDart and Flutter)
  - Streams
  - build by google engniers
  - used by google internally
  - UI only publishes and subscribes
  - NO BL in the UI
  - Keep it stupid so you don’t need to test it
  - All BL should be in BLoC
      - Buisnees Logic Objecs

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