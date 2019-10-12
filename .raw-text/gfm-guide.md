# 400-Conventions

## Introduction

//Last Fig 25
//Last Snip 42

I want to start this chapter of with a great quote from Dart’s official style guide:

> “A surprisingly important part of good code is good style. Consistent naming, ordering, and formatting helps code that is the same look the same.” [\[1\]](https://dart.dev/guides/language/effective-dart)

This chapter will teach you some of the current best practices and conventions when wirting Dart [\[2\]](https://dart.dev/) code and Flutter [\[3\]](https://flutter.dev/) applications in general. That being said, the Dart team has published their own comprehensive guide [\[1\]](https://dart.dev/guides/language/effective-dart) on writing effective Dart. I will be highlighting some of the information of that guide here, but I will mainly be focusing on the aspects that are unique to Dart and might be a bit counter intuitive when coming from a languages like Java [\[4\]](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

## Naming Conventions

There is three types of naming schemes in Dart. The following table is a summarie of when to use which of those schemes:

| Nameing Scheme                 | When to use                                                                                               |
| :----------------------------- | :-------------------------------------------------------------------------------------------------------- |
| *lowercase\_with\_underscores* | libraries, packages, directories, source files, and import prefixes: `import 'package:js/js.dart' as js;` |
| *UpperCamelCase*               | classes, enums, type definitions, and type parameters                                                     |
| *lowerCamelCase*               | anything else: Class members, top-level definitions, variables, parameters, **constants**                 |

*Table 2: Nameing Convention [\[1\]](https://dart.dev/guides/language/effective-dart)*

Most of those cases should look very familiar. But there are two things I want to highlight about constant values:
Firstly, the Dart style guide discourages the use of all uppercase or *SCREENING\_CAPS*. In most other languages all uppercase is used for constant values. The Dart team argues that during development you often end up changing constant variables to no longer be constant. When using all uppercase this leads to a lot of renaming. So the convention in Dart is to use the same scheme for every variable.
Secondly, the official style guide forbids the use of prefixed like “k” for constants or any other variation of Hungarian Notaion [\[5\]](https://en.wikipedia.org/w/index.php?title=Hungarian_notation&oldid=903388598). They argue we are now able to see the type, scope, mutability, and other properties of our variables through the IDE and/or framework, and we no longer need to imbed such information into the name. It is iteresting to note that the official Flutter repository uses and encourages the use of a “k” prefix for constants in their style guide [\[6\]](https://github.com/flutter/flutter/wiki/Style-guide-for-Flutter-repo). So I would argue that either approach is fine as long as you are consistent.

A few additional things to note about naming conventions in Dart [\[1\]](https://dart.dev/guides/language/effective-dart):

  - a leading "\_" is reserved to define a private scope, so you can’t use it for other purposes then that.
  - Only capitalize the first letter of an Abbreviation For Example: `ApiSupplier`
  - Whenever naming anything, ask your self: “Does each word in that type name tell me something critical or prevent a name collision?”, If not, shorten it.
  - The last word of a class or variable should always be the most descriptive of what it is: `PageCount & DataSink` are better then `NumberOfPage & DataIn`

## Comments

  - /// for doc
      - auto detect by IDE
      - auto gen doc
      - replacement for /\*\* … \*/
          - adds 2 more lines in multi line
          - bullet points look bad
      - should alway start with one sentace deitiption
      - the emty line, then rest
      - dont be reduntatn with class name
      - prefer to start with 3rd perosn verb … what it does
      - \[name\] for classes \[class.var\] for fars and \[class.func()\] for functions
      - no **???**
      - be brief
      - code snippets ?

In the snippets up until now you might have noticed the us of `///` for comments. In Dart `///` is a replacement for the classical `/** ... */` bloc comment from other language. The Dart team argues, that tripple-dash comments don’t take up two additional lines when suing them as a block comment:

``` dart
/**
* Holds one pice of supreme [Wisdom]
*
* [Wisdom.id] is only unique in the scope of its [Wisdom.type].
*/
class Wisdom {...}
```

*Code Snippet XXX: Classic Block comment*

``` dart
///Holds one pice of supreme [Wisdom]
///
///[Wisdom.id] is only unique in the scope of its [Wisdom.type].
class Wisdom {...}
```

*Code Snippet XXX: Tripple-Dash Block comment*

![Wisdom Tool Tip](https://github.com/Fasust/flutter-guide/wiki//images/wisdom-tool-tip.png)

*Figure XXX: Wisgen Wisdom Tool Tip [\[7\]](https://github.com/Fasust/wisgen)*

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

<div id="ref-wikipediaHungarianNotation2019">

\[5\] Wikipedia, “Hungarian notation,” *Wikipedia*. 25-Jun-2019 \[Online\]. Available: <https://en.wikipedia.org/w/index.php?title=Hungarian_notation&oldid=903388598>. \[Accessed: 12-Oct-2019\]

</div>

<div id="ref-flutterdevteamStyleGuideFlutter2018">

\[6\] Flutter Dev Team, “Style Guide for Flutter repo,” *GitHub*, 2018. \[Online\]. Available: <https://github.com/flutter/flutter/wiki/Style-guide-for-Flutter-repo>. \[Accessed: 11-Oct-2019\]

</div>

<div id="ref-faustWisgen2019">

\[7\] S. Faust, *Wisgen*. Germany, 2019 \[Online\]. Available: <https://github.com/Fasust/wisgen>. \[Accessed: 20-Sep-2019\]

</div>

</div>
