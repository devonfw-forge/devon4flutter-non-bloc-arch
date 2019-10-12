# 400-Conventions
## Introduction
//Last Fig 25
//Last Snip 42

- Dart has own guide
- this is a summarie
  - focus on things that are unique to dart (Java, Javascript, C)
- "A surprisingly important part of good code is good style. Consistent naming, ordering, and formatting helps code that is the same look the same."

## Naming Conventions
- Table [@dartteamEffectiveDart2019]
- Last word should be the most descriptive one (pageCount, wisdomSink)


| Nameing Convention             | When to use                                                                                               |
| :----------------------------- | :-------------------------------------------------------------------------------------------------------- |
| _lowercase\_with\_underscores_ | libraries, packages, directories ,source files ,and import prefixes: `import 'package:js/js.dart' as js;` |
| _UpperCamelCase_               | classes, enums, type definitions, and type parameters                                                     |
| _lowerCamelCase_               | anything else: Class members, top-level definitions, variables, parameters, constants                     |

_Table 2: Nameing Convention_

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

# _Ref-Dump

