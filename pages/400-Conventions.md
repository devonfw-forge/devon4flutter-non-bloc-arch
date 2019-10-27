Page Table of Contents
- [Introduction](#introduction)
- [Naming Conventions](#naming-conventions)
- [Doc Comments](#doc-comments)
- [Bracket Hell](#bracket-hell)
- [Directory Structure](#directory-structure)

## Introduction

I want to start this chapter with a great quote from Dart’s official style guide:

> “A surprisingly important part of good code is good style. Consistent naming, ordering, and formatting helps code that is the same look the same.” [\[85\]](https://dart.dev/guides/language/effective-dart)

This chapter will teach you some of the current best practices, conventions, and tips when writing Dart code [\[3\]](https://dart.dev/) and Flutter applications [\[1\]](https://flutter.dev/) in general. That being said, the Dart team has published its own comprehensive guide [\[85\]](https://dart.dev/guides/language/effective-dart) on writing effective Dart. I will be highlighting some of the information in that guide here, but I will mainly be focusing on the aspects that are unique to Dart and might be a bit counter-intuitive when coming from other languages or frameworks.

## Naming Conventions

There are three types of naming schemes in Dart. The following table is a summary of when to use which of those schemes:

| Naming Scheme                  | When to use                                                                                               |
| :----------------------------- | :-------------------------------------------------------------------------------------------------------- |
| *lowercase\_with\_underscores* | libraries, packages, directories, source files, and import prefixes: `import 'package:js/js.dart' as js;` |
| *UpperCamelCase*               | classes, enums, type definitions, and type parameters                                                     |
| *lowerCamelCase*               | anything else: Class members, top-level definitions, variables, parameters, **constants**                 |

*Table 2: Naming Convention [\[85\]](https://dart.dev/guides/language/effective-dart)*

There are two things I want to highlight about constant values in Dart:

Firstly, the Dart style guide discourages the use of all uppercase or *SCREENING\_CAPS*. In most other languages all uppercase is used for constant values. The Dart team argues that during development you often end up changing constant variables to no longer be constant. Using all uppercase for constant values thus leads to a lot of renaming. So the convention in Dart is to use the same scheme for every variable.

Secondly, the official style guide forbids the use of prefixes like “k” for constants or any other variation of Hungarian Notation [\[86\]](https://en.wikipedia.org/w/index.php?title=Hungarian_notation&oldid=903388598). They argue that we are now able to see the type, scope, mutability, and other properties of our variables through our IDEs and/or frameworks, and we no longer need to embed such information into the names of our variables. It is interesting to note that the official Flutter repository uses and encourages the use of a “k” prefix for constants in their style guide [\[87\]](https://github.com/flutter/flutter/wiki/Style-guide-for-Flutter-repo). So I would argue that either approach is fine as long as you are consistent.

A few additional things to note about naming conventions in Dart [\[85\]](https://dart.dev/guides/language/effective-dart):

  - A leading "\_" is reserved to define a private scope, so you can’t use it for other purposes than that.
  - Only capitalize the first letter of an abbreviation For Example: `ApiSupplier`
  - Whenever naming anything, ask your self: “Does each word in that name tell me something critical or prevent a name collision?”, If not, shorten it.
  - The last word in class- or variable-name should always be the most descriptive of what it is: `PageCount & DataSink` are better then `NumberOfPage & DataIn`

## Doc Comments

In the snippets up until now, you might have noticed the use of `///` for comments above classes, functions, and members. In Dart triple-dash or “Doc Comments” are a replacement for the classical `/** ... */` block comment from other languages. The Dart team argues, that Doc Comments don’t take up two additional lines when using them as a block comment:

``` dart
/**
* Holds one pice of supreme [Wisdom]
*
* [Wisdom.id] is only unique in the scope of its [Wisdom.type].
*/
class Wisdom {...}
```

*Code Snippet 43: Classic block comment (5 lines)*

``` dart
///Holds one pice of supreme [Wisdom]
///
///[Wisdom.id] is only unique in the scope of its [Wisdom.type].
class Wisdom {...}
```

*Code Snippet 44: Triple-Dash block comment (3 lines)*

Whether you agree with that reasoning or not. You should definitely use them because they can be used to auto-generate a documentation for your project with the Dartdoc tool [\[88\]](https://github.com/dart-lang/dartdoc) and they are shown as tooltips in your IDE:

![Wisgen wisdom tooltip](https://github.com/Fasust/flutter-guide/wiki//images/wisdom-tool-tip.png)

*Figure 26: Wisgen wisdom tooltip [\[11\]](https://github.com/Fasust/wisgen)*

So in short: Use `//` for inline comments, or to explain some specific code within a function. Use `///` to document the top-level behavior of classes, variables, and function.
Some additional things to note about Doc Comments in Dart are [\[85\]](https://dart.dev/guides/language/effective-dart):

  - They should always start with a one sentence description of what the commented thing **dose**. Preferably starting with a present tense, verbs in third person like *supplies*, *holds* or *models*.
  - That initial line should be followed by one empty line to make it stand out.
  - Highlight relevant classes, functions or members by surrounding them with *\[…\]*.
      - They will be linked in the auto-generated docs
  - Markdown [\[89\]](https://daringfireball.net/projects/markdown/) is supported for Doc Comments, so consider adding code snippets as examples.
  - Don’t document information that is already obvious by class name and parameter:

<!-- end list -->

``` dart
///Adds the [int] values of [Adder.a] and [Adder.b] together.
Adder {
  int a;
  int b;
  Adder(this.a, this.b);
  ...
}
```

*Code Snippet 45: Redundant Doc Comment*

## Bracket Hell

One thing you might have already encountered when building an app with Flutter is how easily you end up with a very deeply needed build method that might look a little something like this:

``` dart
...
                          },
                        ),
                      );
                    }),
                  );
                }),
              ),
            );
          },
        ),
      ),
    );
  }
}
...
```

*Code Snippet 46: Flutter Gallery App [\[90\]](https://github.com/flutter/flutter/blob/master/examples/flutter_gallery/lib/gallery/home.dart)*

This phenomenon is known as “Bracket Hell” in the Flutter community \[91\]–\[93\]. And to a degree, this is just what Flutter code looks like. Snippet 46 is from one of Flutter’s official example projects. But we can still try to minimize the problem if we …

| ⚠ | Extract any *distinct enough* Widget into its own class [\[91\]](https://iirokrankka.com/2018/06/18/putting-build-methods-on-a-diet/) |
| - | :------------------------------------------------------------------------------------------------------------------------------------ |

and

| ⚠ | Extract any *callback* into its own function [\[91\]](https://iirokrankka.com/2018/06/18/putting-build-methods-on-a-diet/) |
| - | :------------------------------------------------------------------------------------------------------------------------- |

Let’s look at an example. This is what the *WisdomCard* in Wisgen would look like with one build method. You don’t need to read it all, just look at the top-level *form* of the code:

``` dart
///Displays a given [Wisdom].
///
///Images are loaded from the given [Wisdom.imgUrl] once and then cashed.
///All [Wisdom]s displayed in a [WisdomCard] *have* to contain an imgUrl.
///The like button subscribes to the global [FavoriteBLoC] to change its appearance
///based on on the [FavoriteBLoC]s current State.
///The button also publishes [FavoriteEventAdd]/[FavoriteEventRemove] to 
///the [FavoriteBLoC] when it is pressed.
class WisdomCard extends StatelessWidget {
  static const double _cardElevation = 2;
  static const double _cardBorderRadius = 7;
  static const double _imageHeight = 300;
  static const double _smallPadding = 4;
  static const double _largePadding = 8;

  final Wisdom _wisdom;

  WisdomCard(this._wisdom);

  @override
  Widget build(BuildContext context) {
    return Card(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(_cardBorderRadius),
      ),
      clipBehavior: Clip.antiAlias,
      elevation: _cardElevation,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          CachedNetworkImage(
              imageUrl: _wisdom.imgUrl,
              fit: BoxFit.cover,
              height: _imageHeight,
              errorWidget: (context, url, error) => Container(
                    child: Icon(Icons.error),
                    height: _imageHeight,
                  ),
              placeholder: (context, url) => Container(
                    alignment: Alignment(0.0, 0.0),
                    height: _imageHeight,
                    child: LoadingSpinner(),
                  )),
          Container(
            padding: EdgeInsets.only(top: _largePadding, bottom: _largePadding),
            child: Row(
              children: <Widget>[
                Expanded(
                    flex: 5,
                    child: ListTile(
                      title: Text(_wisdom.text),
                      subtitle: Container(
                          padding: EdgeInsets.only(top: _smallPadding),
                          child: Text(_wisdom.type + ' #' + '${_wisdom.id}',
                              textAlign: TextAlign.left)),
                    )),
                Expanded(
                  flex: 1,
                  child: IconButton(
                    icon: Icon(Icons.share),
                    color: Colors.grey,
                    onPressed: () {
                      Share.share(_wisdom.shareAsString());
                    },
                  ),
                ),
                Expanded(
                  flex: 1,
                  //This is where we Subscribe to the FavoriteBLoC
                  child: BlocBuilder<FavoriteBloc, List<Wisdom>>(
                    builder: (context, favorites) => IconButton(
                      icon: Icon(favorites.contains(_wisdom)
                          ? Icons.favorite
                          : Icons.favorite_border),
                      color: favorites.contains(_wisdom)
                          ? Colors.red
                          : Colors.grey,
                      onPressed: () {
                        final FavoriteBloc favoriteBloc =
                            BlocProvider.of<FavoriteBloc>(context);

                        if (favorites.contains(_wisdom)) {
                          favoriteBloc.dispatch(FavoriteEventRemove(_wisdom));
                        } else {
                          favoriteBloc.dispatch(FavoriteEventAdd(_wisdom));
                        }
                      },
                      padding: EdgeInsets.only(right: _smallPadding),
                    ),
                  ),
                )
              ],
            ),
          ),
        ],
      ),
    );
  }
}
```

*Code Snippet 47: Wisgen WisdomCard in one Widget [\[11\]](https://github.com/Fasust/wisgen)*

<img src="https://github.com/Fasust/flutter-guide/wiki//images/wisgen-card.png" height="350" alt="Wisgen Wisdom Card">

*Figure 27: Wisgen Wisdom Card [\[11\]](https://github.com/Fasust/wisgen)*

And this is what it looks like if we extract the callback function and split the Widget [\[32\]](https://api.flutter.dev/flutter/widgets/StatelessWidget-class.html) into Card, Image, Content and LikeButton:

``` dart
///Displays a given [Wisdom].
///
///Images are loaded from the given [Wisdom.imgUrl] once and then cashed.
///All [Wisdom]s displayed in a [WisdomCard] *have* to contain an imgUrl.
///The like button subscribes to the global [FavoriteBLoC] to change its appearance
///based on on the [FavoriteBLoC]s current State.
///The button also publishes [FavoriteEventAdd]/[FavoriteEventRemove] to 
///the [FavoriteBLoC] when it is pressed.
class WisdomCard extends StatelessWidget {
  static const double _cardElevation = 2;
  static const double _cardBorderRadius = 7;

  final Wisdom _wisdom;

  WisdomCard(this._wisdom);

  @override
  Widget build(BuildContext context) {
    return Card(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(_cardBorderRadius),
      ),
      clipBehavior: Clip.antiAlias,
      elevation: _cardElevation,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          _Image(_wisdom.imgUrl),
          _Content(_wisdom),
        ],
      ),
    );
  }
}

///[CachedNetworkImage] with formating and loading animation.
class _Image extends StatelessWidget {
  static const double _imageHeight = 300;
  const _Image(this._url);

  final String _url;

  @override
  Widget build(BuildContext context) {
    return CachedNetworkImage(
      imageUrl: _url,
      fit: BoxFit.cover,
      height: _imageHeight,
      errorWidget: (context, url, error) => Container(
        child: Icon(Icons.error),
        height: _imageHeight,
      ),
      placeholder: (context, url) => Container(
          alignment: Alignment(0.0, 0.0),
          height: _imageHeight,
          child: LoadingSpinner(),
      )
    );
  }
}

///Displays [Wisdom.text], [Wisdom.type], [Wisdom.id] and a [_LikeButton].
class _Content extends StatelessWidget {
  static const double _smallPadding = 4;
  static const double _largePadding = 8;
  final Wisdom _wisdom;

  const _Content(this._wisdom);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(top: _largePadding, bottom: _largePadding),
      child: Row(
        children: <Widget>[
          Expanded(
              flex: 5,
              child: ListTile(
                title: Text(_wisdom.text),
                subtitle: Container(
                    padding: EdgeInsets.only(top: _smallPadding),
                    child: Text(_wisdom.type + ' #' + '${_wisdom.id}',
                        textAlign: TextAlign.left)),
              )),
          Expanded(
            flex: 1,
            child: IconButton(
              icon: Icon(Icons.share),
              color: Colors.grey,
              onPressed: () {
                Share.share(_wisdom.shareAsString());
              },
            ),
          ),
          _LikeButton(wisdom: _wisdom, smallPadding: _smallPadding)
        ],
      ),
    );
  }

}

///Displays if a given [Wisdom] is a favorite and gives the option 
///to change that fact.
class _LikeButton extends StatelessWidget {
  const _LikeButton({
    Key key,
    @required Wisdom wisdom,
    @required double smallPadding,
  }) : _wisdom = wisdom, _smallPadding = smallPadding, super(key: key);

  final Wisdom _wisdom;
  final double _smallPadding;

  @override
  Widget build(BuildContext context) {
    return Expanded(
      flex: 1,
      //This is where we subscribe to the FavoriteBLoC
      child: BlocBuilder<FavoriteBloc, List<Wisdom>>(
        builder: (context, favorites) => IconButton(
          icon: Icon(favorites.contains(_wisdom)
              ? Icons.favorite
              : Icons.favorite_border),
          color: favorites.contains(_wisdom) ? Colors.red : Colors.grey,
          onPressed: () {_onLike(context,favorites);},
          padding: EdgeInsets.only(right: _smallPadding),
        ),
      ),
    );
  }

  ///Figures out if a [Wisdom] is already liked or not and 
  ///then sends the corresponding Event to the [FavoriteBloc].
  void _onLike(BuildContext context, List<Wisdom> favorites) {
    final FavoriteBloc favoriteBloc = BlocProvider.of<FavoriteBloc>(context);

    if (favorites.contains(_wisdom)) {
      favoriteBloc.dispatch(FavoriteEventRemove(_wisdom));
    } else {
      favoriteBloc.dispatch(FavoriteEventAdd(_wisdom));
    }
  }
}
```

*Code Snippet 48: Wisgen Wisdom Card in four Widgets and with an extracted callback [\[11\]](https://github.com/Fasust/wisgen)*

As you can see, splitting your code into multiple smaller Widgets does lead to a lot more boilerplate. But it has both readability and performance advantages [\[33\]](https://flutter.dev/docs/testing/best-practices), [\[91\]](https://iirokrankka.com/2018/06/18/putting-build-methods-on-a-diet/). Extracting Widgets into private functions removes the boilerplate, but also has no performance advantages.

## Directory Structure

As of the writing of this guide, there is not really any agreement or best practice regarding directory structure in the Flutter community. The closest thing I could find was a popular Blog post by Sagar Suri on Medium in 2019 [\[71\]](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1). My recommendation would be very close to his. One directory per layer plus one directory for *model* classes, which are domain-specific entities like *user* or *wisdom*:

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

*Figure 28: Possible Project Directory Structure*

I would also recommend splitting up the UI directory into pages and widgets. This way you have the highest level of your interface in one place. Suri combines the repository- and data-directory into one, this also a perfectly valid option.

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/500-Conclusion">Next Chapter: Conclusion ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>