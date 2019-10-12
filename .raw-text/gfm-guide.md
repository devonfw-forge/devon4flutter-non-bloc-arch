# 400-Conventions

## Introduction

//Last Fig 25
//Last Snip 42

I want to start this chapter of with a great quote from Dart’s official style guide:

> “A surprisingly important part of good code is good style. Consistent naming, ordering, and formatting helps code that is the same look the same.” [\[1\]](https://dart.dev/guides/language/effective-dart)

This chapter will teach you some of the current best practices and conventions when wirting Dart [\[2\]](https://dart.dev/) code and Flutter [\[3\]](https://flutter.dev/) applications in general. That being said, the Dart team has published their own comprehensive guide on writing effective Dart. I will be highlighting some of the information of that guide here, but I will mainly be focusing on the aspects that are unique to Dart and might be a bit counter intuitive when coming from a languages like Java [\[4\]](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

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

# \_Ref-Dump

<div id="refs" class="references">

<div id="ref-dartteamEffectiveDart2019">

\[1\] Dart Team, “Effective Dart,” 2019. \[Online\]. Available: <https://dart.dev/guides/language/effective-dart>. \[Accessed: 28-Aug-2019\]

</div>

<div id="ref-dartteamDartProgrammingLanguage2019">

\[2\] Dart Team, “Dart programming language,” 2019. \[Online\]. Available: <https://dart.dev/>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-flutterdevteamFlutterFramework2018">

\[3\] Flutter Dev Team, *The Flutter Framework*. Google LLC, 2018 \[Online\]. Available: <https://flutter.dev/>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-oracleJavaJDK1996">

\[4\] Oracle, *Java JDK*. Oracle, 1996 \[Online\]. Available: <https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>. \[Accessed: 26-Sep-2019\]

</div>

</div>
