Page Table of Contents
- [What options are there?](#what-options-are-there)
- [BLoC](#bloc)
  - [Bloc Architecture](#bloc-architecture)
  - [Bloc Architecture with Layers](#bloc-architecture-with-layers)
  - [Wisgen Component Dependencies](#wisgen-component-dependencies)
  - [Wisgen DataFlow](#wisgen-dataflow)

## Introduction

  - Central topic of State handling

## Types of state

  - ephemeral state
      - no need to get fancy
  - app state
      - this is the stuff we architect for

## What options are there?

  - Flutter does not impose an approach
  - Showcase the most popular ones
  - Explain why I choose to go with BLoC
  - Options

### Lifting State up

  - Putting App state up in the Widget Tree
  - Pasing it down through constructors
  - Or Using inhereted widgets
  - Use for tiny apps

### Provide/Scoped Model

  - One Approach advicate by Google
  - Uising a package to hide Inherted widgets behind a nice interface
  - Access through context
  - used by google internally
  - Simple but not really an architecture

### Redux

  - Port from React
  - Good approach if you are already familiar
  - Uses a store for BL
  - Not that easy to understand

### Bloc

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

## BLoC in depth

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

*Figure XXX: Bloc Architecture with Layers [(Suri n.d.)](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1)*

## BLoC in Wisgen

![Wisgen Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dependencies.png)

*Figure XXX: Wisgen Bloc Architecture [(Faust 2019)](https://github.com/Fasust/wisgen)*

![Wisgen Bloc Architecture Dataflow](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dataflow.png)

*Figure XXX: Wisgen Bloc Architecture Dataflow [(Faust 2019)](https://github.com/Fasust/wisgen)*

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/300-Testing">Next Chapter: Testing ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>