# 300-Testing

## Introduction

//Last Snip 36
//Last Fig 23

Testing has become an essential part of developing a large scale application and there is strong evidence that writing tests leads to a higher code quality [\[1\]](http://doi.acm.org/10.1145/952532.952753). This chapter aims to give you a brief introduction to how testing in Flutter [\[2\]](https://flutter.dev/) works and more specifically, how to test an app that implements the BLoC Pattern [\[3\]](https://www.youtube.com/watch?v=PLHln7wHgPE).

## Types of Tests in Flutter

Flutters official test library [\[4\]](https://pub.dev/packages/test) differentiates between three types of tests:

#### Unit Tests

Unit Test can be run very quickly. They can test any function of your app, that does not require the rendering of a Widget [\[5\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). Their main use-case is to test business logic or in our case: BLoCs [\[3\]](https://www.youtube.com/watch?v=PLHln7wHgPE).

#### Widget Tests

Widget Tests are used to test small Widget Sub-Trees of your application. They run relatively quickly and can test the behavior of a given UI [\[5\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

#### Integration Test (Driver Tests)

Integration Test/Driver Tests run your entire application in a virtual machine or on a physical device. They can test user-journeys and complete use-cases. They are very slow and *“prone to braking”*[\[5\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

![Flutter Test Comparison](https://github.com/Fasust/flutter-guide/wiki//images/test-comp.PNG)

*Figure XXX: Flutter Test Comparison [\[6\]](https://flutter.dev/docs/testing)*

## Writing Unit Tests

I will focus on *Unit Tests* for this guide. The Flutter Team recommends that the majority of Flutter tests should be Unit Test \[5\], \[6\]. The fact that they are quick to write and quick to execute makes up for their relatively low *confidence*. In addition to this, because we are using the BLoC Pattern, our UI shouldn’t contain that much testable code anyways. Or to paraphrase the BLoC pattern creator: We keep our UI so *stupid* we don’t need to test it [\[3\]](https://www.youtube.com/watch?v=PLHln7wHgPE). First we have to import the test library [\[4\]](https://pub.dev/packages/test) and the mockito package [\[7\]](https://pub.dev/packages/mockito) in our *pubspec.yaml*:

``` yaml
dev_dependencies:
  mockito: ^4.1.1
  flutter_test:
    sdk: flutter
```

*Code Snippet XXX: Pubspec.yaml Test Imports*

*flutter\_test* offers the core testing capabilities of Flutter. *mockito* is used to mock up dependencies. All out tests should sit in a directory names *“test”* on the root level of our app directory. If we want to place them somewhere else, we have to specify their location every time we want to run them.

![Wisgen Test Directory](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-test-dir.PNG)

*Figure XXX: Wisgen Test Directory [\[8\]](https://github.com/Fasust/wisgen)*

| ⚠ | All testfiles have to end with the postfix "\_test.dart" to be recognized by the framework [\[5\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). |
| - | :------------------------------------------------------------------------------------------------------------------------------------------------ |

Now we can start writing our tests. For this example, I will test the favorite BLoC of Wisgen [\[8\]](https://github.com/Fasust/wisgen):

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

*Code Snippet XXX: Wisgen Favorite BLoC Tests 1 [\[8\]](https://github.com/Fasust/wisgen)*

We can use the *group()* function to group related tests together. This way the output if our tests is more neatly formated [\[5\]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). *setUp()* is called once before every test, so it is perfect for initializing our BLoC [\[9\]](https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86). *tearDown()* is called after every test, so we can use it to dispose of our BLoC. The *test()* function takes in a name and a callback with the actual test. In our test, we check if the state of the favorite BloC after initialization is an empty list. *expect()* takes in the actual value and the value that is expected: `expect(actual, matcher)`. We can run all our tests using the command `flutter test`.

### Testing Streams

Now a more relevant topic when working with the BLoC Pattern, the testing of Streams [\[10\]](https://dart.dev/tutorials/language/streams):

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

*Code Snippet XXX: Wisgen Favorite BLoC Tests 2 [\[8\]](https://github.com/Fasust/wisgen)*

In this test, we create three wisdoms and add/remove them from the favorite BLoC by sending the corresponding events. We use the *emitsInOrder()* *matcher* to tell the framework that we are working with a stream and looking for specific set of events to be emitted in order [\[9\]](https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86). The Flutters test framework also offers many other stream matchers besides *emitsInOrder()* [\[11\]](https://pub.dev/packages/test#asynchronous-tests):

  - *emits()* matches a single data event.
  - *emitsError()* matches a single error event.
  - *emitsDone* matches a single done event.
  - *emitsAnyOf()* consumes events matching one (or more) of several possible matchers.
  - *emitsInAnyOrder()* works like emitsInOrder(), but it allows the matchers to match in any order.
  - *neverEmits()* matches a stream that finishes without matching an inner matcher.
  - And more [\[11\]](https://pub.dev/packages/test#asynchronous-tests)

### Mockito

``` dart
void main() {
  group('Wisdom Bloc', () {
    WisdomBloc wisdomBloc;
    MockRepository mockRepository;
    MockBuildContext mockBuildContext;

    setUp(() {
      wisdomBloc = WisdomBloc();
      mockRepository = MockRepository();
      mockBuildContext = MockBuildContext();

      wisdomBloc.repository = mockRepository;
    });

    tearDown(() {
      //Run after each test
      wisdomBloc.dispose();
    });

    test('Send Fetch Event and see if it emits correct wisdom', () {
      List<Wisdom> fetchedWisdom = [
        Wisdom(id: 1, text: "Back up your Pictures", type: "tech"),
        Wisdom(id: 2, text: "Wash your ears", type: "Mum's Advice"),
        Wisdom(id: 3, text: "Travel while you're young", type: "Grandma's Advice")
      ];

      List expectedStates = [
        //BLoC Library BLoCs emit their initial State on creation
        IdleWisdomState(new List()), 
        IdleWisdomState(fetchedWisdom)
      ];

      when(mockRepository.fetch(20, mockBuildContext))
          .thenAnswer((_) async => fetchedWisdom);

      expectLater(wisdomBloc.state, emitsInOrder(expectedStates));

      wisdomBloc.dispatch(FetchEvent(mockBuildContext));
    });
  });
}
```

*Code Snippet XXX: Wisgen Wisdom BLoC Tests with Mockito [\[8\]](https://github.com/Fasust/wisgen)*

``` dart
///The Wisdom BLoC has 2 States: Loaded and Error
///We can infer it is loading when we are not reviving new items through the stream
@immutable
abstract class WisdomState extends Equatable {}

///Broadcasted on Network Error
class ErrorWisdomState extends WisdomState {
  final Exception exception;
  ErrorWisdomState(this.exception);

  @override
  List<Object> get props => [exception];
}

///Normal State that holds favorite list.
///When BLoC receives a FetchEvent during this State, 
///it fetches more wisdom and emits a new IdleWisdomState 
///with more wisdoms
class IdleWisdomState extends WisdomState {
  final List<Wisdom> wisdoms;
  IdleWisdomState(this.wisdoms);

  @override
  List<Object> get props => wisdoms;
}
```

*Code Snippet XXX: Wisgen Wisdom States with Equatable [\[8\]](https://github.com/Fasust/wisgen)*

  - testing a bloc
  - Dependency injection problems in Flutter

# \_Ref-Dump

<div id="refs" class="references">

<div id="ref-georgeInitialInvestigationTest2003">

\[1\] B. George and L. Williams, “An Initial Investigation of Test Driven Development in Industry,” in *Proceedings of the 2003 ACM Symposium on Applied Computing*, Melbourne, Florida, 2003, pp. 1135–1139 \[Online\]. Available: <http://doi.acm.org/10.1145/952532.952753>. \[Accessed: 10-Oct-2019\]

</div>

<div id="ref-flutterdevteamFlutterFramework2018">

\[2\] Flutter Dev Team, *The Flutter Framework*. Google LLC, 2018 \[Online\]. Available: <https://flutter.dev/>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-soaresFlutterAngularDartCode2018">

\[3\] P. Soares, “Flutter / AngularDart – Code sharing, better together,” 25-Jan-2018 \[Online\]. Available: <https://www.youtube.com/watch?v=PLHln7wHgPE>. \[Accessed: 12-Sep-2019\]

</div>

<div id="ref-dartteamTestDartPackage2019">

\[4\] Dart Team, “Test | Dart Package,” *Dart packages*, 2019. \[Online\]. Available: <https://pub.dev/packages/test>. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-hracekTestingFlutterApps2019">

\[5\] *Testing Flutter Apps - Making Sure Your Code Works*, vol. Ep. 21. 2019 \[Online\]. Available: <https://www.youtube.com/watch?v=bj-oMYyLZEY&>. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-flutterdevteamTestingFlutterApps2018">

\[6\] Flutter Dev Team, “Testing Flutter apps,” 2018. \[Online\]. Available: <https://flutter.dev/docs/testing>. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-fibulwinterMockitoDartPackage2019">

\[7\] D. Fibulwinter and Dart Team, “Mockito | Dart Package,” *Dart packages*, 2019. \[Online\]. Available: <https://pub.dev/packages/mockito>. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-faustWisgen2019">

\[8\] S. Faust, *Wisgen*. Germany, 2019 \[Online\]. Available: <https://github.com/Fasust/wisgen>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-angelovUnitTestingBloc2019">

\[9\] F. Angelov, “Unit Testing with ‘Bloc’,” *Medium*, 2019. \[Online\]. Available: <https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86>. \[Accessed: 09-Oct-2019\]

</div>

<div id="ref-dartteamDartStreams2019">

\[10\] Dart Team, “Dart Streams,” 2019. \[Online\]. Available: <https://dart.dev/tutorials/language/streams>. \[Accessed: 20-Sep-2019\]

</div>

<div id="ref-dartteamAsynchronoustestsTestDart2019">

\[11\] Dart Team, “Asynchronous-tests with the test Dart Package,” *Dart packages*, 2019. \[Online\]. Available: <https://pub.dev/packages/test#asynchronous-tests>. \[Accessed: 11-Oct-2019\]

</div>

</div>
