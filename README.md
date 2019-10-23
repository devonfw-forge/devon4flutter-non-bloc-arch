![Banner](https://github.com/Fasust/flutter-guide/wiki//images/banner.png)

> The Guide is published in the [Wiki of this repository](https://github.com/Fasust/flutter-guide/wiki). This Repository just hold a copy of the Wiki to make the commit history more readable. The README.md is a copy of the [Introduction chapter](https://github.com/Fasust/flutter-guide/wiki).

# Introduction
Page Table of Contents
- [Introduction](#introduction)
  - [The Goal of this Guide](#the-goal-of-this-guide)
  - [Who is this Guide for?](#who-is-this-guide-for)
  - [Topics that will be covered](#topics-that-will-be-covered)
  - [Creation Context](#creation-context)
  - [Structure](#structure)
  - [My Sources](#my-sources)

## The Goal of this Guide

This guide aims to bridge the gap between the absolute Flutter [\[1\]](https://flutter.dev/) basics and clean, structured Flutter development. It should bring you from the basics of knowing how to build an app with Flutter to an understanding of how to do it *properly*. Or at least show you one possible way to make large-scale Flutter projects clean and manageable.

## Who is this Guide for?

For people with a basic knowledge of the Flutter Framework. I recommend following this tutorial by the Flutter team [\[2\]](https://flutter.dev/docs/get-started/codelab). It will walk you through developing your first Flutter application. You should also have a basic understanding of the Dart programming language [\[3\]](https://dart.dev/). No worries, it is very similar to Java [\[4\]](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), Kotlin [\[5\]](https://kotlinlang.org/) and JavaScript [\[6\]](https://www.ecma-international.org/publications/standards/Ecma-262.htm). So if you know 1 or 2 of those languages you should be fine.

## Topics that will be covered

  - A brief introduction to the [Flutter Framework](https://github.com/Fasust/flutter-guide/wiki/100-The-Flutter-Framework) in general:
      - How the [underlying technology](https://github.com/Fasust/flutter-guide/wiki/110-Under-the-Hood) works,
      - how it’s [programming style](https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively) is little different from other frameworks,
      - how Flutter apps are [structured](https://github.com/Fasust/flutter-guide/wiki/130-The-Widget-Tree) on an abstract level and,
      - how [asynchrony](https://github.com/Fasust/flutter-guide/wiki/140-Asynchronous-Flutter) and communication with the web can be implemented.
  - A showcase of possible [architectural styles](https://github.com/Fasust/flutter-guide/wiki/210-State-Management-Alternatives) you can use to build your app and
      - an [in-depth guide](https://github.com/Fasust/flutter-guide/wiki/220-BLoC) on one of those possibilities (BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE)).
  - How to [test](https://github.com/Fasust/flutter-guide/wiki/300-Testing) your app.
  - Some [conventions and best practices](https://github.com/Fasust/flutter-guide/wiki/400-Conventions) for Dart, and the Flutter Framework in general.
  - My personal [evaluation of the framework](https://github.com/Fasust/flutter-guide/wiki/500-Conclusion).

## Creation Context

This guide was written by a student in the Bachelor of Science Program “Computer Science and Media Technology” at Technical University Cologne [\[8\]](https://www.th-koeln.de/en/homepage_26.php), and it was created for one of the modules in that Bachelor. In addition to this, the guide was written in collaboration with Capgemini Cologne [\[9\]](https://www.capgemini.com/us-en/). Capgemini released a guide on building an application with Angular [\[10\]](https://github.com/devonfw/devon4ng) in May of 2019, this guide is meant to be the Flutter version of that.

## Structure

The guide is designed to be read in order, from chapter 0 (this one) to chapter 5. Code examples throughout the chapters will mainly be taken from Wisgen [\[11\]](https://github.com/Fasust/wisgen), an example Flutter application that was specifically built for the purposes of this guide. If you want to search for any specific terms in the guide, you can use [this page](https://github.com/Fasust/flutter-guide/wiki/gfm-guide). It is all chapters of the guide combined into one page. There is going to be a few common symbols throughout the guide, this is what they stand for:

| Symbol | Meaning                  |
| :----: | :----------------------- |
|   📙    | Definition               |
|   🕐    | Shortened version (TLDR) |
|   ⚠    | Important                |

## My Sources

I am basing this guide on a combination of conference talks, blog articles by respected Flutter developers, the official documentation, scientific papers that cover cross-platform mobile development in general and many other sources. All sources used in the guide are listed in chapter [*6 References*](https://github.com/Fasust/flutter-guide/wiki/600-References). To put that theoretical knowledge into practice, I built the Wisgen application [\[11\]](https://github.com/Fasust/wisgen) using the Flutter Framework, the BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE), and a four-layered architecture.

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/100-The-Flutter-Framework">Next Chapter: The Flutter Framework ></a></p>
<p align="center"><a href="#">Back to Top</a></p>