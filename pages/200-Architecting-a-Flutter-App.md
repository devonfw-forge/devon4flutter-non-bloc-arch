Page Table of Contents
- [What options are there?](#what-options-are-there)
- [BLoC](#bloc)
  - [Bloc Architecture](#bloc-architecture)
  - [Bloc Architecture with Layers](#bloc-architecture-with-layers)
  - [Wisgen Component Dependencies](#wisgen-component-dependencies)
  - [Wisgen DataFlow](#wisgen-dataflow)

## What options are there?

  - Vanilla, Redux, Bloc, Provide/Scoped Model
  - Which one will I focus on and Why?
  - Google went bach and forth on this as well.

## BLoC

  - Why this one?
  - Origin
  - UI only publishes and subscribes
  - **Build Interface code how you want it to look like -\> then make it work**
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

![Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/bloc-architecture.png)

*Figure XXX: Bloc Architecture*

![Bloc Architecture with Layers](https://github.com/Fasust/flutter-guide/wiki//images/bloc-layers.png)

*Figure XXX: Bloc Architecture with Layers*

![Wisgen Bloc Architecture](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dependencies.png)

*Figure XXX: Wisgen Bloc Architecture*

![Wisgen Bloc Architecture Dataflow](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-dataflow.png)

*Figure XXX: Wisgen Bloc Architecture Dataflow*

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/300-Testing">Next Chapter: Testing ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>