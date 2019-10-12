Page Table of Contents
- [Introduction](#introduction)
- [Types of Tests in Flutter](#types-of-tests-in-flutter)
    - [Unit Tests](#unit-tests)
    - [Widget Tests](#widget-tests)
    - [Integration Test (Driver Tests)](#integration-test-driver-tests)
- [Writing Unit Tests](#writing-unit-tests)
  - [Testing Streams](#testing-streams)
  - [Mockito](#mockito)
- [Equality in Dart](#equality-in-dart)

## Introduction

Testing has become an essential part of developing a large scale application and there is strong evidence that writing tests leads to a higher code quality [\[72\]](http://doi.acm.org/10.1145/952532.952753). This chapter aims to give you a brief introduction to how testing in Flutter [\[1\]](https://flutter.dev/) works and more specifically, how to test an app that implements the BLoC Pattern [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE).

## Types of Tests in Flutter

Flutters official test library [\[73\]](https://pub.dev/packages/test) differentiates between three types of tests:

#### Unit Tests

Unit Test can be run very quickly. They can test any function of your app, that does not require the rendering of a Widget [\[74\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). Their main use-case is to test business logic or in our case: BLoCs [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE).

#### Widget Tests

Widget Tests are used to test small Widget Sub-Trees of your application. They run relatively quickly and can test the behavior of a given UI [\[74\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

#### Integration Test (Driver Tests)

Integration Test/Driver Tests run your entire application in a virtual machine or on a physical device. They can test user-journeys and complete use-cases. They are very slow and *“prone to braking”*[\[74\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

![Flutter Test Comparison](https://github.com/Fasust/flutter-guide/wiki//images/test-comp.PNG)

*Figure 24: Flutter Test Comparison [\[75\]](https://flutter.dev/docs/testing)*

## Writing Unit Tests

I will focus on *Unit Tests* for this guide. The Flutter Team recommends that the majority of Flutter tests should be Unit Test \[74\], \[75\]. The fact that they are quick to write and quick to execute makes up for their relatively low *confidence*. In addition to this, because we are using the BLoC Pattern, our UI shouldn’t contain that much testable code anyways. Or to paraphrase the BLoC pattern creator: We keep our UI so *stupid* we don’t need to test it [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE). First we have to import the test library [\[73\]](https://pub.dev/packages/test) and the mockito package [\[76\]](https://pub.dev/packages/mockito) in our *pubspec.yaml*:

``` yaml
dev_dependencies:
  mockito: ^4.1.1
  flutter_test:
    sdk: flutter
```

*Code Snippet 37: Pubspec.yaml Test Imports*

*flutter\_test* offers the core testing capabilities of Flutter. *mockito* is used to mock up dependencies. All out tests should sit in a directory names *“test”* on the root level of our app directory. If we want to place them somewhere else, we have to specify their location every time we want to run them.

![Wisgen Test Directory](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-test-dir.PNG)

*Figure 25: Wisgen Test Directory [\[11\]](https://github.com/Fasust/wisgen)*

| ⚠ | All testfiles have to end with the postfix "\_test.dart" to be recognized by the framework [\[74\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). |
| - | :------------------------------------------------------------------------------------------------------------------------------------------------- |

Now we can start writing our tests. For this example, I will test the favorite BLoC of Wisgen [\[11\]](https://github.com/Fasust/wisgen):

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
    
    test('Initial State is an empty list', () {
      expect(favoriteBloc.currentState, List());
    });

    ...
  });
}
```

*Code Snippet 38: Wisgen Favorite BLoC Tests 1 [\[11\]](https://github.com/Fasust/wisgen)*

We can use the *group()* function to group related tests together. This way the output of our tests is more neatly formated [\[74\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). *setUp()* is called once before every test, so it is perfect for initializing our BLoC [\[77\]](https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86). *tearDown()* is called after every test, so we can use it to dispose of our BLoC. The *test()* function takes in a name and a callback with the actual test. In our test, we check if the state of the favorite BloC after initialization is an empty list. *expect()* takes in the actual value and the value that is expected: `expect(actual, matcher)`. We can run all our tests using the command `flutter test`.

### Testing Streams

Now a more relevant topic when working with the BLoC Pattern, the testing of Streams [\[38\]](https://dart.dev/tutorials/language/streams):

``` dart
void main() {

  group('Favorite Bloc', () {
    FavoriteBloc favoriteBloc;

    setUp((){...});

    tearDown((){...});
    
    
    test('Initial State is an empty list', () {...});

    test('Stream many events and see if the State is emitted in correct order', () {
      //Set Up
      Wisdom wisdom1 = Wisdom(id: 1, text: "Back up your Pictures", type: "tech");
      Wisdom wisdom2 = Wisdom(id: 2, text: "Wash your ears", type: "Mum's Advice");
      Wisdom wisdom3 = Wisdom(id: 3, text: "Travel while you're young", type: "Grandma's Advice");

      //Testing
      favoriteBloc.dispatch(FavoriteEventAdd(wisdom1));
      favoriteBloc.dispatch(FavoriteEventAdd(wisdom2));
      favoriteBloc.dispatch(FavoriteEventRemove(wisdom1));
      favoriteBloc.dispatch(FavoriteEventAdd(wisdom3));

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

*Code Snippet 39: Wisgen Favorite BLoC Tests 2 [\[11\]](https://github.com/Fasust/wisgen)*

In this test, we create three wisdoms and add/remove them from the favorite BLoC by sending the corresponding events. We use the *emitsInOrder()* *matcher* to tell the framework that we are working with a stream and looking for a specific set of events to be emitted in order [\[77\]](https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86). The Flutters test framework also offers many other stream matchers besides *emitsInOrder()* [\[78\]](https://pub.dev/packages/test#asynchronous-tests):

  - *emits()* matches a single data event.
  - *emitsError()* matches a single error event.
  - *emitsDone* matches a single done event.
  - *emitsAnyOf()* consumes events matching one (or more) of several possible matchers.
  - *emitsInAnyOrder()* works like emitsInOrder(), but it allows the matchers to match in any order.
  - *neverEmits()* matches a stream that finishes without matching an inner matcher.
  - And more [\[78\]](https://pub.dev/packages/test#asynchronous-tests)

### Mockito

As mentioned before, *Mockito* [\[76\]](https://pub.dev/packages/mockito) can be used to mock dependencies. The BLoC pattern forces us to make all plattform specific dependencies of our BLoCs injectable [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE). This comes in very handy when testing BLoCs. For example, the wisdom BLoC of Wisgen fetches data from a given Repository. Instead of testing the Wisdom BLoC in combination with it’s Repository, we can inject a mock Repository into the BLoC. In this example, we use *Mockito* to test if our wisdom BLoC emits new wisdoms after receiving a fetch event:

``` dart
//Creating Mocks using Mockito
class MockRepository extends Mock implements Supplier<Wisdom> {}
class MockBuildContext extends Mock implements BuildContext {}

void main() {
  group('Wisdom Bloc', () {
    WisdomBloc wisdomBloc;
    MockRepository mockRepository;
    MockBuildContext mockBuildContext;

    setUp(() {
      wisdomBloc = WisdomBloc();
      mockRepository = MockRepository();
      mockBuildContext = MockBuildContext();
      //Inject Mock
      wisdomBloc.repository = mockRepository;
    });

    tearDown(() {
      //Run after each test
      wisdomBloc.dispose();
    });

    test('Send Fetch Event and see if it emits correct wisdom', () {
      //Set Up ---
      List<Wisdom> fetchedWisdom = [
        Wisdom(id: 1, text: "Back up your Pictures", type: "tech"),
        Wisdom(id: 2, text: "Wash your ears", type: "Mum's Advice"),
        Wisdom(id: 3, text: "Travel while you're young", type: "Grandma's Advice")
      ];
      
      //Telling the Mock Repo how to behave
      when(mockRepository.fetch(20, mockBuildContext))
        .thenAnswer((_) async => fetchedWisdom);

      List expectedStates = [
        IdleWisdomState(new List()), //BLoC Library BLoCs emit their initial State on creation
        IdleWisdomState(fetchedWisdom)
      ];
    
      //Test ---
      wisdomBloc.dispatch(FetchEvent(mockBuildContext));

      //Result ---
      expect(wisdomBloc.state, emitsInOrder(expectedStates));
    });
  });
}
```

*Code Snippet 40: Wisgen Wisdom BLoC Tests with Mockito [\[11\]](https://github.com/Fasust/wisgen)*

First we create our Mock classes. For this test we need a mock *Supplier-Repository* and a mock *Buildcontext* [\[31\]](https://api.flutter.dev/flutter/widgets/BuildContext-class.html). In the *setUp()* function, we initialize our BLoC and our mocks and inject the mock Repository into our BLoC. In the *test()* function, we tell our mock Repository to send a set of wisdom when it’s *fetch()* function is called. Now we can send a fetch event to the BLoC, and check if it emits the correct states in order.

## Equality in Dart

| ⚠ | By default, all comparisons in Dart work based on references and not base on values \[77\], \[79\] |
| - | :------------------------------------------------------------------------------------------------- |

``` dart
Wisdom wisdom1 = Wisdom(id: 1, text: "Back up your Pictures", type: "tech");
Wisdom wisdom2 = Wisdom(id: 1, text: "Back up your Pictures", type: "tech");

print(wisdom1 == wisdom2); //false
```

*Code Snippet 41: Equality in Flutter*

This can be an easy thing to trip over during testing, especially when comparing States emitted by BLoCs. Luckily, Felix Angelov released the *Equatable* package in 2019 [\[79\]](https://pub.dev/packages/equatable#-example-tab-). It’s an easy way to overwrite how class equality is handled. If we make a class extend the *Equatable* class, we can set the properties it is compared by. We do this by overwriting it’s *props* attribute. This is used in Wisgen to make the States of the wisdom BLoC compare based on the wisdom they carry:

``` dart
@immutable
abstract class WisdomState extends Equatable {}

///Broadcasted from [WisdomBloc] on Network Error.
class WisdomStateError extends WisdomState {
  final Exception exception;
  WisdomStateError(this.exception);

  @override
  List<Object> get props => [exception];
}

///Gives Access to current list of [Wisdom] s in the [WisdomBloc].
///
///When BLoC receives a [WisdomEventFetch] during this State, 
///it fetches more [Wisdom] from it [Supplier]. 
///When done it emits a new [IdleSate] with more [Wisdom].
class WisdomStateIdle extends WisdomState {
  final List<Wisdom> wisdoms;
  WisdomStateIdle(this.wisdoms);

  @override
  List<Object> get props => wisdoms;
}
```

*Code Snippet 42: Wisgen Wisdom States with Equatable [\[11\]](https://github.com/Fasust/wisgen)*

If we wouldn’t use Equatable, the test form snippet 40 could not functions properly, as two states carrying the same wisdom would still be considers different by the test framework.

| 🕐 | TLDR | If you don’t want your classes to be compared base on their reference, use the Equatable package [\[79\]](https://pub.dev/packages/equatable#-example-tab-) |
| - | ---- | :---------------------------------------------------------------------------------------------------------------------------------------------------------- |

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/400-Conventions">Next Chapter: Conventions ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>