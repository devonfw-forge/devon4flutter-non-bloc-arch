Page Table of Contents
- [Introduction](#introduction)
- [Widgets in General](#widgets-in-general)
  - [The Widget Tree](#the-widget-tree)
  - [Buildcontext](#buildcontext)
- [2 types of Widgets](#2-types-of-widgets)
  - [Stateless Widgets](#stateless-widgets)
    - [Lifecycle](#lifecycle)
  - [Stateful Widgets](#stateful-widgets)
    - [Lifecycle](#lifecycle-1)
  - [When to use Stateful/Stateful Widgets](#when-to-use-statefulstateful-widgets)
- [How to access State](#how-to-access-state)
- [References](#references)

## Introduction
This section will give you a better understanding of how programming in [Flutter (Flutter Dev Team 2018)](https://flutter.dev/) actually works. You will be introduced the two of the most central topics of Flutter: [The _Widget Tree_ (Flutter Dev Team 2019)](https://flutter.dev/docs/development/ui/widgets-intro) and [_Sate_ (Flutter Dev Team 2019)](https://flutter.dev/docs/development/data-and-backend/state-mgmt).

## Widgets in General
One sentence you can simply not avoid when researching Flutter is:
> "In Flutter, everything is a Widget."

But that is not really helpful, is it? I would rephrase that and say, you can think of Widgets in Flutter as any visual component on screen. Let's have look at an example, this app displays an endless feed of Wisdoms combined with vaguely thought provoking stock images:

![Wisgen Widgets](https://github.com/Fasust/flutter-guide/wiki//.images/wisgen-widgets.png)

_Figure 1: [Wisgen Widgets (Faust 2019)](https://github.com/Fasust/wisgen)_

As you can see, all UI-Components of the app are widgets. From high level stuff like the App-Bar and ListView down to to the granular things like texts and buttons (I did not highlight every widget on screen to keep the figure from becoming over crowded). In code, the build method of a card Widget would look something like this:

```dart
@override
Widget build(BuildContext context) {
  return Card(
    shape: RoundedRectangleBorder( //Declare corners to be rounded
      borderRadius: BorderRadius.circular(7),
    ),
    elevation: 2, //Declare shadow drop
    child: Column( //The child of the card should be displayed in a column Widget
      children: <Widget>[ //The card contains an image and some content
        _image(context),
        _content(context),
      ],
    ),
  );
}
```
_Codesnippt 1: [Wisgen Card Widget (Faust 2019)](https://github.com/Fasust/wisgen)_

The functions _image() generates the stock image. The function _content() generate the wisdom text and the buttons on the card. 
Another important thing to note, is that:

| ⚠   | Widgets in Flutter are always immutable |
| --- | :------------------------------------------ |

The build method of any given Widget can be called multiple times a second. And how often it is called exactly is never under your control, it is controlled by the Flutter Framework. This also means that all variables in a Widget have to be final, because they can not change over time. But your app never consists out of exclusively immutable parts, does it? How flutter handles mutable _state_ will be covered in the section [Stateful Widgets](#stateful-widgets).

### The Widget Tree
I have used the term _Widget Tree_ a few times now, but what exactly does it mean? A UI in flutter is nothing more then a tree of nested widgets. Let's have a look of the widget tree for our example in Figure 1. Note the card Widgets on the right hand side of the diagram. Here you can see how the code from Snippet 1 translates to Widgets in the Widget Tree.

![Wisgen Widget Tree](https://github.com/Fasust/flutter-guide/wiki//.images/wisgen-widget-tree.PNG)

_Figure 2: [Wisgen Widget Tree (Faust 2019)](https://github.com/Fasust/wisgen)_

### Buildcontext
If you have previously build an App with Flutter, you have definitely encountered _BuildContext_. It is passed in as a variable in every Widget build methode in Flutter. But what exactly is _BuildContext_? As X puts it:
> "A BuildContext is nothing else but a reference to the location of a Widget within the tree structure of all the Widgets which are built."

The BuildContext contain information about each *ancestor* leading down to the widget that the context belongs to. So it is an easy way for a widget to access all its ancestors in the widget tree. accessing the *descendance* through the BuildContext is possible, but not advices and inefficient. So in short: For a widget at the bottom of the tree, it is very easy to get information from widgets at the top of the tree but not visversa. For example, the image Widget from Figure 2 could access it's ancestor card Widget like this:
```dart
//going up the widget tree: (Image [me]) -> (Column) -> (Card) [!] first match, so this one is returned
Card.of(context); 
```
Alright, but what does that mean for me as a Flutter developer? It is important to understand how data in Flutter flows through the Widget Tree, **downwards**. You want to place information that is required by multiple widgets above them in the tree, so they can both easily access it through there BuildContext. Keep this in mind for now, I will explain this in more detail in the chapter [Architekting a Flutter App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App).

## 2 types of Widgets
### Stateless Widgets
#### Lifecycle
### Stateful Widgets
- what is state 
  - mutable
  - "n properties that change over time"
  - long life span
  - sticks around
- why 2 party
#### Lifecycle

### When to use Stateful/Stateful Widgets

## How to access State
- Lifting up
- Inherited Widget
- 2 types of state
  - local and global
  - more in architecture

<p align="center"><a href="#">Back to Top</a></center></p>

---
## References 