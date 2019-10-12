# 400-Conventions
## Introduction
//Last Fig 25
//Last Snip 42

I want to start this chapter of with a great quote from Dart's official style guide:

> "A surprisingly important part of good code is good style. Consistent naming, ordering, and formatting helps code that is the same look the same." [[@dartteamEffectiveDart2019]](https://dart.dev/guides/language/effective-dart)

This chapter will teach you some of the current best practices and conventions when wirting Dart [[@dartteamDartProgrammingLanguage2019]](https://dart.dev/) code and Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) applications in general. That being said, the Dart team has published their own comprehensive guide [[@dartteamEffectiveDart2019]](https://dart.dev/guides/language/effective-dart) on writing effective Dart. I will be highlighting some of the information of that guide here, but I will mainly be focusing on the aspects that are unique to Dart and might be a bit counter intuitive when coming from a languages like Java [[@oracleJavaJDK1996]](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

## Naming Conventions
There is three types of naming schemes in Dart. The following table is a summarie of when to use which of those schemes:

| Nameing Scheme                 | When to use                                                                                               |
| :----------------------------- | :-------------------------------------------------------------------------------------------------------- |
| _lowercase\_with\_underscores_ | libraries, packages, directories, source files, and import prefixes: `import 'package:js/js.dart' as js;` |
| _UpperCamelCase_               | classes, enums, type definitions, and type parameters                                                     |
| _lowerCamelCase_               | anything else: Class members, top-level definitions, variables, parameters, **constants**                 |

_Table 2: Nameing Convention [[@dartteamEffectiveDart2019]](https://dart.dev/guides/language/effective-dart)_

Most of those cases should look very familiar. But there are two things I want to highlight about constant values: 
Firstly, the Dart style guide discourages the use of all uppercase or _SCREENING\_CAPS_. In most other languages all uppercase is used for constant values. The Dart team argues that during development you often end up changing constant variables to no longer be constant. When using all uppercase this leads to a lot of renaming. So the convention in Dart is to use the same scheme for every variable. 
Secondly, the official style guide forbids the use of prefixed like "k" for constants or any other variation of Hungarian Notaion [[@wikipediaHungarianNotation2019]](https://en.wikipedia.org/w/index.php?title=Hungarian_notation&oldid=903388598). They argue we are now able to see the type, scope, mutability, and other properties of our variables through the IDE and/or framework, and we no longer need to imbed such information into the name. It is iteresting to note that the official Flutter repository uses and encourages the use of a "k" prefix for constants in their style guide [[@flutterdevteamStyleGuideFlutter2018]](https://github.com/flutter/flutter/wiki/Style-guide-for-Flutter-repo). So I would argue that either approach is fine as long as you are consistent.

A few additional things to note about naming conventions in Dart [[@dartteamEffectiveDart2019]](https://dart.dev/guides/language/effective-dart):

- a leading "_" is reserved to define a private scope, so you can't use it for other purposes then that.
- Only capitalize the first letter of an Abbreviation For Example: `ApiSupplier`
- Whenever naming anything, ask your self: "Does each word in that type name tell me something critical or prevent a name collision?”, If not, shorten it.
- The last word of a class or variable should always be the most descriptive of what it is: `PageCount & DataSink` are better then `NumberOfPage & DataIn`

## Doc Comments
In the snippets up until now you might have noticed the us of `///` for comments. In Dart tripple-dash or "Doc Comments" are a replacement for the classical `/** ... */` bloc comment from other language. The Dart team argues, that Doc Comments don't take up two additional lines when suing them as a block comment:

```dart
/**
* Holds one pice of supreme [Wisdom]
*
* [Wisdom.id] is only unique in the scope of its [Wisdom.type].
*/
class Wisdom {...}
```
_Code Snippet XXX: Classic Block comment_

```dart
///Holds one pice of supreme [Wisdom]
///
///[Wisdom.id] is only unique in the scope of its [Wisdom.type].
class Wisdom {...}
```
_Code Snippet XXX: Tripple-Dash Block comment_

Wether you agree with that reasoning or not. You should definitely use them, because they can be used to auto generate a documentaion for your project with the Dartdoc tool [[@dartteamDartdocTool2019]](https://github.com/dart-lang/dartdoc) and they are shown as tooltips in your IDE:

![Wisdom Tool Tip](https://github.com/Fasust/flutter-guide/wiki//images/wisdom-tool-tip.png)

_Figure XXX: Wisgen Wisdom Tool Tip [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

Some additional things to note about Doc Comments in Dart are [[@dartteamEffectiveDart2019]](https://dart.dev/guides/language/effective-dart):

- They should always start with a one sentence description of what the commented thing **dose**. Preferably starting with a third person verb like _Supplies_, _Holds_, _Models_.
- That initial line should be followed by one empty line to make it stand out.
- Highlight relevant classes, functions or members by surrounding them with _\[...\]_.
  - They will be linked in the auto-generated docs
- Markdown [[@gruberMarkdown2004]](https://daringfireball.net/projects/markdown/) is supported for tripple-dash comments, so consider adding code snippets as examples.
- Don't document information that is already obvious by class name and parameter:

```dart
///Adds the [int] values of [Adder.a] and [Adder.b] together.
Adder {
  int a;
  int b;
  Adder(this.a, this.b);
  ...
}
```
_Code Snippet XXX: Redundant Doc Comment_

## Bracket Hell 
One thing you might have already encountered when building an app with Flutter, is how easily you end up with a very deeply needed build methode that might look a little something like this:

```dart
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
_Code Snippet XXX: Flutter Gallery App [[@flutterdevteamOfficialFlutterExample2019]](https://github.com/flutter/flutter/blob/master/examples/flutter_gallery/lib/gallery/home.dart)_

This phenomenon is none as "Bracket Hell" in the Flutter community [@krankkaPuttingBuildMethods2018; @cluelessAmAlsoCreating2018; @u/robertpro01FlutterDevFlutterCode2018]. And to a degree, this is just what Flutter code looks like. Snippet XXX is from one of Flutters official example projects. But we can still try to minimize the problem if we ...

| ⚠   | Extract any _distinct enough_ widget into its own class [[@krankkaPuttingBuildMethods2018]](https://iirokrankka.com/2018/06/18/putting-build-methods-on-a-diet/) |
| --- | :--------------------------------------------------------------------------------------------------------------------------------------------------------------- |

and

| ⚠   | Extract any _callback_ into its own function [[@krankkaPuttingBuildMethods2018]](https://iirokrankka.com/2018/06/18/putting-build-methods-on-a-diet/) |
| --- | :---------------------------------------------------------------------------------------------------------------------------------------------------- |

Let's look at an example. This is what the WisdomCard in Wisgen would look like with one build methode. You don't need to read it all, just look at the top level _form_ of the code:

```dart
///Displays a given [Wisdom].
///
///Images are Loaded from the given [Wisdom.imgUrl] once and then cashed.
///All [Wisdom]s displayed in a [WisdomCard] *have* to contain an imgUrl.
///The [_LikeButton] Subscribes to the Global [FavoriteBLoC] to change it's appearance
///based on on its current state.
///The Button also publishes [FavoriteEventAdd]/[FavoriteEventRemove] to 
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
_Code Snippet XXX: Wisgen Wisdom Card in one Widget [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

And this is what it looks like if we extract the callback function and slit th widget into Card, Image, Content and LikeButton:

```dart
///Displays a given [Wisdom].
///
///Images are Loaded from the given [Wisdom.imgUrl] once and then cashed.
///All [Wisdom]s displayed in a [WisdomCard] *have* to contain an imgUrl.
///The [_LikeButton] Subscribes to the Global [FavoriteBLoC] to change it's appearance
///based on on its current state.
///The Button also publishes [FavoriteEventAdd]/[FavoriteEventRemove] to 
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

///[CachedNetworkImage] with formating and loading animation
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

///Displays [Wisdom.text], [Wisdom.type], [Wisdom.id] and
///a [_LikeButton]
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

///Display if a given [Wisdom] is a favorite and gives the option 
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
      //This is where we Subscribe to the FavoriteBLoC
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

  ///Figures out if a Wisdom is already liked or not.
  ///Then send corresponding Event.
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
_Code Snippet XXX: Wisgen Wisdom Card in four Widgets and with an extracted callback [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

As you can see, splitting your code into multiple smaller Widgets, does lead to a lot more boiler plate. But it has both readability and performance advantages [@krankkaPuttingBuildMethods2018; @dartteamPerformanceBestPractices2018]. Extracting Widgets into private functions removes the boiler plate, but has also no performance advantages.

## Directory Structure
As of the writing of this guide there is not really any agreement or best practice regarding directory structure in the Flutter community. The closest thing I could find was a popular Blog post by Sagar Suri on Medium in 2019 [[@suriArchitectYourFlutter2019]](https://medium.com/flutterpub/architecting-your-flutter-project-bd04e144a8f1). My recommendation would be very close to his. One directory per layer plus one directory for _model_ classes, which are domain specific entities like _user_ or _wisdom_:

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
_Figure XXX: Possible Project Directory Structure_

I would also recommend splitting up the UI directory into pages and widgets. This way you have the highest level of your interface in one place. Suri combines the repository and data directory into one, this also a perfectly valid option.

# _Ref-Dump

