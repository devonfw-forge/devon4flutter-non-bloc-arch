Page Table of Contents
- [Introduction](#introduction)
- [Naming conventions](#naming-conventions)
- [Comments](#comments)
- [Strings](#strings)
- [Bracket Hell](#bracket-hell)
- [File structure with BLoC](#file-structure-with-bloc)

## Introduction

//Last Fig 25
//Last Snip 42

  - Dart has own guide
  - this is a summarie
      - focus on things that are unique to dart (Java, Javascript, C)
  - “A surprisingly important part of good code is good style. Consistent naming, ordering, and formatting helps code that is the same look the same.”

## Naming conventions

  - Table \[1\]
  - Last word should be the most descriptive one (pageCount, wisdomSink)

## Comments

## Strings

  - ${year + day}

## Bracket Hell

  - Own widgets (performance)
  - Callback functions

## File structure with BLoC

  - no consensus, I would recommend a folder / layer + models
  - ui into pages and widgets

<!-- end list -->

    lib
    |
    ├── blocs
    ├── data
    ├── models
    ├── repositories
    ├── ui 
    |   ├── pages
    |   |   ├── home_page.dart
    |   |   └── ...
    |   └── widgets
    |       ├── image_card.dart
    |       └── ...
    └── main.dart

*Figure XXX: Possible Project File Structure*

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/500-Conclusion">Next Chapter: Conclusion ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>