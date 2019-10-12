Page Table of Contents
- [Introduction](#introduction)
- [Naming Conventions](#naming-conventions)
- [Comments](#comments)
- [Strings](#strings)
- [Bracket Hell](#bracket-hell)
- [File structure with BLoC](#file-structure-with-bloc)
- [Performance Pitfalls](#performance-pitfalls)

## Introduction

//Last Fig 25
//Last Snip 42

  - Dart has own guide
  - this is a summarie
      - focus on things that are unique to dart (Java, Javascript, C)
  - “A surprisingly important part of good code is good style. Consistent naming, ordering, and formatting helps code that is the same look the same.”

## Naming Conventions

  - Last word should be the most descriptive one (pageCount, wisdomSink)

| Nameing Convention             | When to use                                                                                               |
| :----------------------------- | :-------------------------------------------------------------------------------------------------------- |
| *lowercase\_with\_underscores* | libraries, packages, directories, source files, and import prefixes: `import 'package:js/js.dart' as js;` |
| *UpperCamelCase*               | classes, enums, type definitions, and type parameters                                                     |
| *lowerCamelCase*               | anything else: Class members, top-level definitions, variables, parameters, **constants**                 |

*Table 2: Nameing Convention [\[1\]](https://dart.dev/guides/language/effective-dart)*

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

## Performance Pitfalls

  - short list of tips

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/500-Conclusion">Next Chapter: Conclusion ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>