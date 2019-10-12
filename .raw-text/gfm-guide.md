# 400-Conventions

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

# \_Ref-Dump

<div id="refs" class="references">

<div id="ref-dartteamEffectiveDart2019">

\[1\] Dart Team, “Effective Dart,” 2019. \[Online\]. Available: <https://dart.dev/guides/language/effective-dart>. \[Accessed: 28-Aug-2019\]

</div>

</div>
