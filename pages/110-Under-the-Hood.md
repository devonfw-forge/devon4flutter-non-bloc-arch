Page Table of Contents
- [Introduction](#introduction)
  - [Full Native Approach](#full-native-approach)
  - [Embedded WebApp Approach](#embedded-webapp-approach)
    - [Bridges](#bridges)
  - [Reactive View Approach](#reactive-view-approach)
  - [Flutter’s Approach](#flutters-approach)
- [Flutter Compiler](#flutter-compiler)
- [Hot Reload](#hot-reload)

## Introduction

Flutter [\[1\]](https://flutter.dev/) is a framework for cross-platform native development. That means that it promises native app performance while still compiling apps for multiple platforms from a single codebase. The best way to understand how Flutter achieves this is to compare it to other mobile development approaches. This chapter will showcase how three of the most popular cross-platform approaches function and then compare those technics to the one Flutter uses. Lastly, I will highlight some of the unique features that Flutters approach enables.

### Full Native Approach

![Native app rendering](https://github.com/Fasust/flutter-guide/wiki//images/native-rendering.png)

*Figure 1: Native app rendering [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

The classic way to build a mobile app would be to write native code for each platform you want to support. I.E. One for IOS [\[15\]](https://developer.apple.com/ios/), one for Android [\[16\]](https://developer.android.com/) and so on. In this approach, your app will be written in a platform-specific language and render through platform-specific Widgets and a platform-specific engine. During the development, you have direct access to platform-specific services and sensors [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514), [\[17\]](https://www.youtube.com/watch?v=l-YO9CmaSUM&feature=youtu.be), [\[18\]](https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a). But you will have to build the same app multiple times, which multiplies your workload by however many platforms you want to support.

### Embedded WebApp Approach

![Embedded WebApp rendering](https://github.com/Fasust/flutter-guide/wiki//images/webview-rendering.png)

*Figure 2: Embedded WebApp rendering [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Embedded WebApps where the first approach to cross-platform development. For this approach, you build your application with HTML [\[19\]](https://www.w3.org/html/), CSS [\[20\]](https://www.w3.org/Style/CSS/Overview.de.html), and JavaScript [\[6\]](https://www.ecma-international.org/publications/standards/Ecma-262.htm) and then render it through a native WebView [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514), [\[17\]](https://www.youtube.com/watch?v=l-YO9CmaSUM&feature=youtu.be). The problem here is, that developers are limited to the web technology stack and that communication between the app and native services always has to run through a *bridge* [\[18\]](https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a).

#### Bridges

Bridges connect components with one another. These components can be built in the same or different programming languages [\[21\]](http://www.sciencedirect.com/science/article/pii/S1877050915020979).

### Reactive View Approach

![Reactive app rendering](https://github.com/Fasust/flutter-guide/wiki//images/reactive-rendering.png)

*Figure 3: Reactive app rendering [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Apps build with reactive frameworks (like React Native [\[22\]](https://facebook.github.io/react-native/)) are mostly written in a platform-independent language like JavaScript [\[6\]](https://www.ecma-international.org/publications/standards/Ecma-262.htm). The JavaScript code then sends information on how UI components should be displayed to the native environment. This communication always runs through a *bridge*. So we end up with native Widgets that are controller through JavaScript. The main problem here is that the communication through the *bridge* can be a bottleneck that can lead to performance issues [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514), [\[17\]](https://www.youtube.com/watch?v=l-YO9CmaSUM&feature=youtu.be), [\[18\]](https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a), [\[23\]](https://www.youtube.com/watch?v=psZLAHQXRsI).

### Flutter’s Approach

![Flutter app rendering](https://github.com/Fasust/flutter-guide/wiki//images/flutter-rendering.png)

*Figure 4: Flutter app rendering [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

Flutter’s approach is to move the entire rendering process into the app. The rendering runs through Flutter’s own engine and uses Flutter’s own Widgets. Flutter only needs a canvas to display the rendered frames on. Any user inputs on the canvas or system events happening on the device are forwarded to the Flutter app. This limits the *bridging* between the app and native environment to frames and events which removes that potential bottleneck [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514), [\[17\]](https://www.youtube.com/watch?v=l-YO9CmaSUM&feature=youtu.be), [\[18\]](https://medium.com/flutter-community/in-plain-english-so-what-the-heck-is-flutter-and-why-is-it-a-big-deal-7a6dc926b34a).

You might think that keeping an entire rendering engine inside an app would lead to huge APKs, but as of 2019, the compressed framework is only 4.3MB [\[24\]](https://flutter.dev/docs/resources/faq).

![Flutter Framework architecture](https://github.com/Fasust/flutter-guide/wiki//images/flutter-architecture.png)

*Figure 5: Flutter Framework architecture [\[14\]](https://hackernoon.com/whats-revolutionary-about-flutter-946915b09514)*

| 🕐 | TLDR | Flutter uses its own engine instead of using the native one. The native environment only renders the finished frames. |
| - | ---- | :-------------------------------------------------------------------------------------------------------------------- |

## Flutter Compiler

One additional advantage of Flutter is that it comes with two different compilers. A JIT-Compiler (just in time) and an AOT-Compiler (ahead of time). The following table will showcase the advantage of each:

| Compiler      | What is does                                                                                                                                                                                                                                      | When it’s used     |
| :------------ | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | :----------------- |
| Just in Time  | Only re-compiles files that have changed. Preserves *App State* [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) during rebuilds. Enables *Hot Reload* [\[25\]](https://flutter.dev/docs/development/tools/hot-reload). | During Development |
| Ahead of Time | Compiles all dependencies ahead of time. The output app is faster.                                                                                                                                                                                | For Release        |

*Table 1: Flutter’s 2 Compilers [\[17\]](https://www.youtube.com/watch?v=l-YO9CmaSUM&feature=youtu.be), [\[26\]](https://www.youtube.com/watch?v=J5DQRPRBiFI)*

## Hot Reload

*Hot Reload* [\[25\]](https://flutter.dev/docs/development/tools/hot-reload) is a feature that Web developers are already very familiar with. It essentially means that changes in the code are displayed in the running application near instantaneously. Thanks to its JIT Compiler, The Flutter Framework is also able to provide this feature.

![Hot Reload](https://github.com/Fasust/flutter-guide/wiki//images/hot-reload.gif)

*Figure 6: Hot Reload [\[25\]](https://flutter.dev/docs/development/tools/hot-reload)*

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively">Next Chapter: Thinking Declaratively ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>