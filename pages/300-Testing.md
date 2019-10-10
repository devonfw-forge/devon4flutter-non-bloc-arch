Page Table of Contents
- [Introduction](#introduction)
- [Types of Tests in Flutter](#types-of-tests-in-flutter)
    - [Unit Tests](#unit-tests)
    - [Widget Tests](#widget-tests)
    - [Integration Test (Driver Tests)](#integration-test-driver-tests)
- [Writing Tests](#writing-tests)

## Introduction

//Last Snip 36
//Last Fig 23

Testing has become an essential part of developing a large scale application and there is strong evidence that writing tests leads to a higher code quality [\[71\]](http://doi.acm.org/10.1145/952532.952753). This chapter aims to give you a brief introduction to how testing in Flutter [\[1\]](https://flutter.dev/) works.

## Types of Tests in Flutter

Flutters official test library [\[72\]](https://pub.dev/packages/test) differentiates between three types of tests:

#### Unit Tests

Unit Test can be run very quickly. They can test any function of your app, that does not require the rendering of a Widget [\[73\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). Their main use-case is to test business logic or in our case: BLoCs [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE).

#### Widget Tests

Widget Tests are used to test small Widget Sub-Trees of your application. They run relatively quickly and can test the behavior of a given UI [\[73\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

#### Integration Test (Driver Tests)

Integration Test/Driver Tests run your entire application in a virtual machine or on a physical device. They can test user-journeys and complete use-cases. They are very slow and *“prone to braking”*[\[73\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

![Flutter Test Comparison](https://github.com/Fasust/flutter-guide/wiki//images/test-comp.PNG)

*Figure XXX: Flutter Test Comparison [\[74\]](https://flutter.dev/docs/testing)*

## Writing Tests

I will focus on *Unit Tests* for this guide. The Flutter Team recommends that the majority of Flutter tests should be Unit Test, as they are quick to write, quick to execute and yield relatively high *confidence* \[73\], \[74\]. In addition to this, because we are using the BLoC Pattern, our UI shouldn’t contain that much testable code anyways. Or to paraphrase the BLoC pattern creator: We keep our UI so *stupid* we don’t need to test it [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE). First we have to import the test library [\[72\]](https://pub.dev/packages/test) and the mockito package [\[75\]](https://pub.dev/packages/mockito) in our *pubspec.yaml*:

``` yaml
dev_dependencies:
  mockito: ^4.1.1
  flutter_test:
    sdk: flutter
```

*Code Snippet XXX: Pubspec.yaml Test Imports*

*flutter\_test* offers the core testing capabilities of Flutter, *mockito* is used to mock up dependencies. Now place a new directory called *“tests”* on the root level of our app directory:

![Wisgen Test Directory](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-test-dir.PNG)

*Figure XXX: Wisgen Test Directory [\[11\]](https://github.com/Fasust/wisgen)*

| ⚠ | All testfiles have to end with the postfix "\_test.dart" to be recognized by the framework [\[73\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). |
| - | :------------------------------------------------------------------------------------------------------------------------------------------------- |

Now we can start writing our tests. For this example, I will test the favortie BLoC of Wisgen [\[11\]](https://github.com/Fasust/wisgen).

``` dart
void main() {

  ///Related test are grouped together 
  ///to get a more readable output 
  group('Favorite Bloc', () {
    FavoriteBloc favoriteBloc;

    setUp((){
      //Run before each test
      favoriteBloc = new FavoriteBloc();
    });

    tearDown((){
      //Run after each test
      favoriteBloc.dispose();
    });
    
    test('Add a Favorite and see if it is emitted as state', () {
      //Set Up
      Wisdom wisdom = Wisdom(id: 1, text: "Back up your Pictures", type: "tech");

      //Testing
      favoriteBloc.dispatch(AddFavoriteEvent(wisdom));

      //Result
      favoriteBloc.state.listen((data) => () {
            expect(wisdom, data);
          });
    });

    test('Add and Remove a Favorite and see if the state is empty', () {
      //Set Up
      Wisdom wisdom = Wisdom(id: 1, text: "Back up your Pictures", type: "tech");

      //Testing
      favoriteBloc.dispatch(AddFavoriteEvent(wisdom));
      favoriteBloc.dispatch(RemoveFavoriteEvent(wisdom));

      //Result
      expect(0, favoriteBloc.currentState.length);
    });

    test('Stream many events and see if the State is emitted in correct order', () {
      //Set Up
      Wisdom wisdom1 = Wisdom(id: 1, text: "Back up your Pictures", type: "tech");
      Wisdom wisdom2 = Wisdom(id: 2, text: "Wash your ears", type: "Mum's Advice");
      Wisdom wisdom3 = Wisdom(id: 3, text: "Travel while you're young", type: "Grandma's Advice");

      //Testing
      favoriteBloc.dispatch(AddFavoriteEvent(wisdom1));
      favoriteBloc.dispatch(AddFavoriteEvent(wisdom2));
      favoriteBloc.dispatch(RemoveFavoriteEvent(wisdom1));
      favoriteBloc.dispatch(AddFavoriteEvent(wisdom3));

      //Result
      expect( 
          favoriteBloc.state,
          emitsInOrder([
            List(), //BLoC Library BLoCs emit their initial State on creation
            List()..add(wisdom1),
            List()..add(wisdom1)..add(wisdom2),
            List()..add(wisdom2),
            List()..add(wisdom2)..add(wisdom3)
          ]));
    });
  });
}
```

*Code Snippet XXX: Wisgen Favorite BLoC Tests [\[11\]](https://github.com/Fasust/wisgen)*

Let’s go though these tests one by one.

  - testing a bloc
  - Dependency injection problems in Flutter

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/400-Conventions">Next Chapter: Conventions ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>