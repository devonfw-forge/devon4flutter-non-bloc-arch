Page Table of Contents
- [What options are there?](#what-options-are-there)
  - [Lifting State up](#lifting-state-up)
  - [Provide/Scoped Model](#providescoped-model)
  - [Redux](#redux)
  - [Bloc](#bloc)

## Introduction

Other then many mobile development frameworks, Flutter does not impose any kind of architecture on it’s developers. This openended approach has lead to multiple architectures and

  - Flutter does not impose an approach
  - Showcase the most popular ones
  - Explain why I choose to go with BLoC
  - Options

## Lifting State up

  - Putting App state up in the Widget Tree
  - Pasing it down through constructors
  - Or Using inhereted widgets
  - Use for tiny apps

## Provide/Scoped Model

  - One Approach advicate by Google
  - Uising a package to hide Inherted widgets behind a nice interface
  - Access through context
  - used by google internally
  - Simple but not really an architecture

## Redux

  - Port from React
  - Good approach if you are already familiar
  - Uses a store for BL
  - Not that easy to understand

## Bloc

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

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/220-BLoC">Next Chapter: BLoC ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>