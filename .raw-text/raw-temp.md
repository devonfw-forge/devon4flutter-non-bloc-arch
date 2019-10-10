# 300-Testing

## Introduction

//Last Snip 36 
//Last Fig 23

Testing has become an essential part of developing a large scale application and there is strong evidence that writing tests leads to a higher code quality [[@georgeInitialInvestigationTest2003]](http://doi.acm.org/10.1145/952532.952753). This chapter aims to give you a brief introduction to how testing in Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) works. 

## Types of Tests in Flutter
Flutters official test library [[@dartteamTestDartPackage2019]](https://pub.dev/packages/test) differentiates between three types of tests:

#### Unit Tests 
Unit Test can be run very quickly. They can test any function of your app, that does not require the rendering of a Widget [[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). Their main use-case is to test business logic or in our case: BLoCs [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE).

#### Widget Tests
Widget Tests are used to test small Widget Sub-Trees of your application. They run relatively quickly and can test the behavior of a given UI [[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

#### Integration Test (Driver Tests)
Integration Test/Driver Tests run your entire application in a virtual machine or on a physical device. They can test user-journeys and complete use-case. They are very slow and _"prone to braking"_[[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

![Flutter Test Comparison](https://github.com/Fasust/flutter-guide/wiki//images/test-comp.PNG)

_Figure XXX: Flutter Test Comparison [[@flutterdevteamTestingFlutterApps2018]](https://flutter.dev/docs/testing)_

## Writing Tests
I will focus on _Unit Tests_ for this guide. The Flutter Team recommends that the majority of Flutter tests should be Unit Test, as they are quick to write, quick to execute and yield relatively high _confidence_ [@hracekTestingFlutterApps2019; @flutterdevteamTestingFlutterApps2018]. In addition to this, because we are using the BLoC Pattern, our UI shouldn't contain that much testable code anyways. Or to paraphrase the BLoC pattern creator: We keep our UI so _stupid_ we don't need to test it [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE). 

- Set up
- packages
- testing a bloc
- Dependency injection problems in Flutter