Page Table of Contents
- [Introduction](#introduction)
- [Evaluation of the Guide](#evaluation-of-the-guide)
- [Future of the Guide and Next Steps](#future-of-the-guide-and-next-steps)
  - [Suggestion for Future Works](#suggestion-for-future-works)
- [My Thoughts on Flutter](#my-thoughts-on-flutter)

## Introduction

So, you’ve made it. This is the final chapter of the guide. I will use this chapter ro reflect on the Guide, evaluate it’s strengths and weaknesses, and highlight how I and this project will move foreword from here on out. I will also take this opportunity to share my personal opinion of the Flutter Framework [\[1\]](https://flutter.dev/). That being said, the guide has already been quite opinionated up until now.

## Evaluation of the Guide

I am pretty happy with how the Guide turned out. I managed to find a diverse range of sources for most of the topics I wanted to cover. I kept close to the *informal*, *from developer for developer*-style of the original Angular guide [\[10\]](https://github.com/devonfw/devon4ng) and I think it reads pretty well. The Guide did end up being a bit longer then I planned (\~50 A4 Pages without figures and references). If I would write it again, I would choose a more narrow and clear scope for what the guide should include. For example, I might have excluded or shortened chapter [1 The Flutter Framework](https://github.com/Fasust/flutter-guide/wiki/100-The-Flutter-Framework) and instead focused even more on chapter [2 Architecting a Flutter App](https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App). I would have also liked to include more scientific sources on Flutter. But as it is still such a new Framework, next to no scientific research has so far been conducted on it. As of the writing of this Guide their is not a single paper published on the Flutter Framework though IEEE [\[89\]](https://ieeexplore.ieee.org/Xplore/home.jsp) or ACM [\[90\]](https://dl.acm.org/).

All in all, I think I fulfilled the goal I set myself when when starting this guide. To bridge the gap between the basics of FLutter and clean, structured Flutter development. The result is a resource I would have liked to have when trying to bridge that gap myself.

## Future of the Guide and Next Steps

This Guide was commissioned by Capgemini Cologne [\[9\]](https://www.capgemini.com/us-en/). It is supposed to introduce their mobile developer to the Flutter Framework and help them evaluate if it is a valid option for future projects. It will also be the basis for my Bachelor Thesis I am starting this November. In my thesis I will create and document a large scale Flutter application for Capgemini. It is supposed to be an example project that other developers can use as a guideline for creating their own large scale Flutter applications. More specifically, Capgemini has used the “My Thai Star” App [\[91\]](https://github.com/devonfw/my-thai-star) as a reference for Angular [\[64\]](https://angular.io/). I will create a Flutter version of it.

### Suggestion for Future Works

One thing I and peers in the mobile development community [\[92\]](http://doi.acm.org/10.1145/3241739) would like to see is more scientific research on the Flutter Framework: Lagre scale benchmarking, usability tests, saleability tests, architecture evaluation and so on.

## My Thoughts on Flutter

I am guessing you could already pick up on my position regarding Flutter in the previous chapters. I am a Fan. The applications it produces run remarkably smooth and performance is usually one of the main drawbacks of cross-plattform frameworks [\[93\]](http://dl.gi.de/handle/20.500.12116/17386). The trade-off used to be *more convenience* for *less performance*. With flutter this is no longer the case.
I originally was not a fan of how State Management [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) in Flutter is handled. I am coming from native Android [\[15\]](https://developer.android.com/) development an the switch from declarative to imperative thinking took a little while for me. I went though multiple iterations of the Wisgen app [\[11\]](https://github.com/Fasust/wisgen) with different architectural styles and none of them really seemed like a great option. Until I tried out BLoC [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE), and suddandy the whole framework started to make more sense. I now understand that the desing of Flutter has one central goal, maximize performance and minimize locations of State.
Another great thing I noticed during the writing of this Guide is how open the Flutter Team is towards the community. The Framework is completely open-source and many features have been implemented by developers outside of Google. The Flutter team is also very active on social media, with their own weekly podcast and Youtube channel [\[94\]](https://www.youtube.com/channel/UCwXdFgeE9KYzlDdR7TG9cMw).

One thing I don’t like about Flutter is how easy it is to write ugly code. I already touched on this topic in [the last chapter](https://github.com/Fasust/flutter-guide/wiki/400-Conventions). Having deeply nested code is, to a degree, unavoidable in Flutter. Splitting Widgets into smaller parts helps but adds a lot of boilerplate. I hope there will be a better solution to mitigate this problem in the future.
Another thing I am not a fan of is dependency injection in Flutter, at the moment, the only solution is to inject dependencies from the UI into the BLoC. This created an ugly dependency between UI- and Data-Layer. But Google is currently working on a solution for this, so the problem is known and will be addressed [\[95\]](https://github.com/google/inject.dart).

All that being said, I wont be going back to native app development any time soon.

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/600-References">References ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>