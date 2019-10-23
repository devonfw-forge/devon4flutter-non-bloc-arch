Page Table of Contents
- [Introduction](#introduction)
- [Evaluation of the Guide](#evaluation-of-the-guide)
- [Future of the Guide and Next Steps](#future-of-the-guide-and-next-steps)
  - [Suggestion for Future Works](#suggestion-for-future-works)
- [My Thoughts on Flutter](#my-thoughts-on-flutter)

## Introduction

So, you’ve made it. This is the final chapter of the guide. I will use this chapter to reflect on the guide, evaluate its strengths and weaknesses, and highlight how I and this project will move foreword from here on out. I will also take this opportunity to share my personal opinion of the current state of the Flutter Framework [\[1\]](https://flutter.dev/). That being said, the guide has already been quite opinionated up until now.

## Evaluation of the Guide

I am pretty happy with how the guide turned out. I managed to find a diverse range of sources for most of the topics I wanted to cover. I kept close to the *informal*, “*from developer for developer*”-style of the original Angular guide [\[10\]](https://github.com/devonfw/devon4ng) and I think it reads pretty well. The guide did end up being a bit longer then I planned (\~50 A4 pages without figures and references). If I would write it again, I would choose a more narrow and clear scope for what the guide should include. For example, I might have excluded or shortened chapter [1 The Flutter Framework](https://github.com/Fasust/flutter-guide/wiki/100-The-Flutter-Framework) and instead focused even more on chapter [2 Architecting a Flutter App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App). I would have also liked to include more scientific sources on Flutter. But as it is still such a new Framework, next to no scientific research has so far been conducted on it. As of the writing of this guide, there has not been a single publication on the Flutter Framework through IEEE [\[94\]](https://ieeexplore.ieee.org/Xplore/home.jsp) or ACM [\[95\]](https://dl.acm.org/).

All in all, I think I fulfilled the goal I set myself when starting this guide. To bridge the gap between the basics of Flutter and clean, structured Flutter development. The result is a resource I would have liked to have when bridging that gap myself.

## Future of the Guide and Next Steps

This guide was commissioned by Capgemini Cologne [\[9\]](https://www.capgemini.com/us-en/). It is supposed to aid their mobile developers when building large-scale application using the Flutter Framework. It will also be the basis for the Bachelor Thesis I am starting this November. In my Thesis, I will create and document a large scale Flutter application for Capgemini. It is supposed to be an example project that other developers can use as a guideline for creating their own large scale Flutter applications. More specifically, Capgemini has used the “My Thai Star” app [\[96\]](https://github.com/devonfw/my-thai-star) as a reference project for Angular [\[69\]](https://angular.io/). I will create a Flutter version of it.

### Suggestion for Future Works

One thing I and peers in the mobile development community [\[97\]](http://doi.acm.org/10.1145/3241739) would like to see is more scientific research on the Flutter Framework: Lagre scale benchmarking, usability tests, scalability tests, architecture evaluation and so on.

## My Thoughts on Flutter

I am guessing you could already pick up on my position regarding Flutter in the previous chapters. I am a Fan. The applications it produces run remarkably smooth and performance is usually one of the main drawbacks of cross-platform frameworks \[98\], \[99\]. The trade-off used to be *less development cost* for *less performance*. With Flutter this is no longer the case.

I was originally not a fan of how State Management [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) in Flutter is handled. I am coming from native Android [\[16\]](https://developer.android.com/) development and the switch from imperative to declarative thinking took a little while for me. I now understand that the design of Flutter has one central goal: Maximize performance [\[100\]](https://flutter.dev/docs/resources/technical-overview). And the way that State is handled is a direct consequence of that goal. State Management also becomes a lot less of an issue once you start following a clearly defined architectural style. I had to build a few iterations of Wisgen [\[11\]](https://github.com/Fasust/wisgen) to figure this out for myself. But once I implemented it with BLoC [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE), I began to understand how Flutter could work in a large scale application.

Another great thing I noticed during the writing of this guide is how open the Flutter Team is towards the community. The Framework is completely open-source and many features have been implemented by developers outside of Google. The Flutter team is also very active on social media, with its own weekly podcast and Youtube channel [\[101\]](https://www.youtube.com/channel/UCwXdFgeE9KYzlDdR7TG9cMw).

One thing I don’t like about Flutter is how easy it is to write ugly code. I already touched on this topic in [the last chapter](https://github.com/Fasust/flutter-guide/wiki/400-Conventions). Having deeply nested code is, to a degree, unavoidable in Flutter. Splitting Widgets into smaller parts helps but adds a lot of boilerplate. I hope there will be a better solution to mitigate this problem in the future.

Another thing I am not a fan of is dependency injection in Flutter. At the moment the only solution is to inject dependencies from the UI into the BLoC. This created an ugly dependency between UI- and Data-Layer. But Google is currently working on a solution for this, so the problem is known and will be addressed [\[102\]](https://github.com/google/inject.dart).

All that being said, I won’t be going back to native app development any time soon.

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/600-References">References ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>