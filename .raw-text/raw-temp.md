# 400-Conventions
## Introduction
//Last Fig 25
//Last Snip 42

I want to start this chapter of with a great quote from Dart's official style guide:

> "A surprisingly important part of good code is good style. Consistent naming, ordering, and formatting helps code that is the same look the same." [[@dartteamEffectiveDart2019]](https://dart.dev/guides/language/effective-dart)

This chapter will teach you some of the current best practices and conventions when wirting Dart [[@dartteamDartProgrammingLanguage2019]](https://dart.dev/) code and Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) applications in general. That being said, the Dart team has published their own comprehensive guide on writing effective Dart. I will be highlighting some of the information of that guide here, but I will mainly be focusing on the aspects that are unique to Dart and might be a bit counter intuitive when coming from a languages like Java [[@oracleJavaJDK1996]](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

## Naming Conventions

- Last word should be the most descriptive one (pageCount, wisdomSink)

| Nameing Convention             | When to use                                                                                               |
| :----------------------------- | :-------------------------------------------------------------------------------------------------------- |
| _lowercase\_with\_underscores_ | libraries, packages, directories, source files, and import prefixes: `import 'package:js/js.dart' as js;` |
| _UpperCamelCase_               | classes, enums, type definitions, and type parameters                                                     |
| _lowerCamelCase_               | anything else: Class members, top-level definitions, variables, parameters, **constants**                 |

_Table 2: Nameing Convention [[@dartteamEffectiveDart2019]](https://dart.dev/guides/language/effective-dart)_

## Comments

## Strings
- ${year + day}

## Bracket Hell 
- Own widgets (performance)
- Callback functions

## File structure with BLoC
- no consensus, I would recommend a folder / layer + models
- ui into pages and widgets

```
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
```
_Figure XXX: Possible Project File Structure_

## Performance Pitfalls
- short list of tips

# _Ref-Dump

