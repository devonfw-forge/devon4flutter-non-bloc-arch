Page Table of Contents
- [Introduction](#introduction)
- [What are Widgets?](#what-are-widgets)
- [The Widget Tree](#the-widget-tree)
  - [Stateless Widgets](#stateless-widgets)
    - [Lifecycle](#lifecycle)
  - [Stateful Widgets](#stateful-widgets)
    - [Lifecycle](#lifecycle-1)
- [When to use Stateful/Stateful Widgets](#when-to-use-statefulstateful-widgets)
- [How to access State](#how-to-access-state)
- [References](#references)

## Introduction
This section will give you a better understanding of how programming in [Flutter (Flutter Dev Team 2018)](https://flutter.dev/) actually works. You will be introduced the two of the most central topics of Flutter: [The _Widget Tree_ (Flutter Dev Team 2019)](https://flutter.dev/docs/development/ui/widgets-intro) and [_Sate_ (Flutter Dev Team 2019)](https://flutter.dev/docs/development/data-and-backend/state-mgmt).

## What are Widgets?
One sentence you can simply not avoid when researching Flutter is:
> "In Flutter, everything is a Widget."

But that is not really helpful, is it? I would rephrase that and say, you can think of Widgets in Flutter as any visual component on screen. Let's have look at an example, this app displays an endless feed of Wisdoms combined with vaguely thought provoking stock images:

![Wisgen Widgets](https://github.com/Fasust/flutter-guide/wiki//.images/wisgen-widgets.png)

_Figure 1: [Wisgen Widgets (Faust 2019)](https://github.com/Fasust/wisgen)_

As you can see, all UI-Components of the app are widgets. From high level stuff like the App-Bar and ListView down to to the granular things like texts and buttons (I did not highlight every widget on screen keep the figure from becoming to crowded).

- Widgets in General
  - Imutable
  - Each widget does one specific thing

## The Widget Tree
- the tree
- Buildcontext

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

## When to use Stateful/Stateful Widgets

## How to access State
- Lifting up
- Inherited Widget
- 2 types of state
  - local and global
  - more in architecture

<p align="center"><a href="#">Back to Top</a></center></p>

---
## References 