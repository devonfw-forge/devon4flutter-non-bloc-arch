
# 300-Testing

## Introduction

//Last Snip 36 
//Last Fig 23

Testing has become an essential part of developing a large scale application and there is strong evidence that writing tests leads to a higher code quality [[@georgeInitialInvestigationTest2003]](http://doi.acm.org/10.1145/952532.952753). This chapter aims to give you a brief introduction to how testing in Flutter [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/) works and more specifically, how to test an app that implements the BLoC Pattern [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE).

## Types of Tests in Flutter
Flutters official test library [[@dartteamTestDartPackage2019]](https://pub.dev/packages/test) differentiates between three types of tests:

#### Unit Tests 
Unit Test can be run very quickly. They can test any function of your app, that does not require the rendering of a Widget [[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). Their main use-case is to test business logic or in our case: BLoCs [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE).

#### Widget Tests
Widget Tests are used to test small Widget Sub-Trees of your application. They run relatively quickly and can test the behavior of a given UI [[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

#### Integration Test (Driver Tests)
Integration Test/Driver Tests run your entire application in a virtual machine or on a physical device. They can test user-journeys and complete use-cases. They are very slow and _"prone to braking"_[[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&).

![Flutter Test Comparison](https://github.com/Fasust/flutter-guide/wiki//images/test-comp.PNG)

_Figure XXX: Flutter Test Comparison [[@flutterdevteamTestingFlutterApps2018]](https://flutter.dev/docs/testing)_

## Writing Unit Tests
I will focus on _Unit Tests_ for this guide. The Flutter Team recommends that the majority of Flutter tests should be Unit Test [@hracekTestingFlutterApps2019; @flutterdevteamTestingFlutterApps2018]. The fact that they are quick to write and quick to execute makes up for their relatively low _confidence_. In addition to this, because we are using the BLoC Pattern, our UI shouldn't contain that much testable code anyways. Or to paraphrase the BLoC pattern creator: We keep our UI so _stupid_ we don't need to test it [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE). First we have to import the test library [[@dartteamTestDartPackage2019]](https://pub.dev/packages/test) and the mockito package [[@fibulwinterMockitoDartPackage2019]](https://pub.dev/packages/mockito) in our _pubspec.yaml_:

```yaml
dev_dependencies:
  mockito: ^4.1.1
  flutter_test:
    sdk: flutter
```
_Code Snippet XXX: Pubspec.yaml Test Imports_

_flutter\_test_ offers the core testing capabilities of Flutter. _mockito_ is used to mock up dependencies. All out tests should sit in a directory names _"test"_ on the root level of our app directory. If we want to place them somewhere else, we have to specify their location every time we want to run them.

![Wisgen Test Directory](https://github.com/Fasust/flutter-guide/wiki//images/wisgen-test-dir.PNG)

_Figure XXX: Wisgen Test Directory [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

| ⚠   | All testfiles have to end with the postfix "_test.dart" to be recognized by the framework [[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). |
| --- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

Now we can start writing our tests. For this example, I will test the favorite BLoC of Wisgen [[@faustWisgen2019]](https://github.com/Fasust/wisgen):

```dart
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
_Code Snippet XXX: Wisgen Favorite BLoC Tests 1 [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

We can use the _group()_ function to group related tests together. This way the output of our tests is more neatly formated [[@hracekTestingFlutterApps2019]](https://www.youtube.com/watch?v=bj-oMYyLZEY&). _setUp()_ is called once before every test, so it is perfect for initializing our BLoC [[@angelovUnitTestingBloc2019]](https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86). _tearDown()_ is called after every test, so we can use it to dispose of our BLoC. The _test()_ function takes in a name and a callback with the actual test. In our test, we check if the state of the favorite BloC after initialization is an empty list. _expect()_ takes in the actual value and the value that is expected: `expect(actual, matcher)`. We can run all our tests using the command `flutter test`.

### Testing Streams
Now a more relevant topic when working with the BLoC Pattern, the testing of Streams [[@dartteamDartStreams2019]](https://dart.dev/tutorials/language/streams):

```dart
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
_Code Snippet XXX: Wisgen Favorite BLoC Tests 2 [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

In this test, we create three wisdoms and add/remove them from the favorite BLoC by sending the corresponding events. We use the _emitsInOrder()_ _matcher_ to tell the framework that we are working with a stream and looking for specific set of events to be emitted in order [[@angelovUnitTestingBloc2019]](https://medium.com/flutter-community/unit-testing-with-bloc-b94de9655d86). The Flutters test framework also offers many other stream matchers besides _emitsInOrder()_ [[@dartteamAsynchronoustestsTestDart2019]](https://pub.dev/packages/test#asynchronous-tests):

- _emits()_ matches a single data event.
- _emitsError()_ matches a single error event.
- _emitsDone_ matches a single done event.
- _emitsAnyOf()_ consumes events matching one (or more) of several possible matchers.
- _emitsInAnyOrder()_ works like emitsInOrder(), but it allows the matchers to match in any order.
- _neverEmits()_ matches a stream that finishes without matching an inner matcher.
- And more [[@dartteamAsynchronoustestsTestDart2019]](https://pub.dev/packages/test#asynchronous-tests)

### Mockito
As mentioned before, _Mockito_ [[@fibulwinterMockitoDartPackage2019]](https://pub.dev/packages/mockito) can be used to mock dependencies. The BLoC pattern forces us to make all plattform specific dependencies of our BLoCs injectable [[@soaresFlutterAngularDartCode2018]](https://www.youtube.com/watch?v=PLHln7wHgPE). This comes in very handy when testing them. For example, the wisdom BLoC of Wisgen fetches data from a given Repository. Instead of testing the Wisdom BLoC in combination with it's Repository, we can inject a mock Repository into the BLoC. In this example, we use _Mockito_ to test if our wisdom BLoC emits new wisdom after receiving a fetch event:

```dart
//Creating Mocks using Mockito
class MockRepository extends Mock implements Repository<Wisdom> {}
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
      //Set Up
      List<Wisdom> fetchedWisdom = [
        Wisdom(id: 1, text: "Back up your Pictures", type: "tech"),
        Wisdom(id: 2, text: "Wash your ears", type: "Mum's Advice"),
        Wisdom(id: 3, text: "Travel while you're young", type: "Grandma's Advice")
      ];

			when(mockRepository.fetch(20, mockBuildContext))
				//Telling the Mock Repo how to behave
				.thenAnswer((_) async => fetchedWisdom);


      List expectedStates = [
        //BLoC Library BLoCs emit their initial State on creation
        IdleWisdomState(new List()), 
        IdleWisdomState(fetchedWisdom)
      ];
    
			//Test
			wisdomBloc.dispatch(FetchEvent(mockBuildContext));

			//Result
      expect(wisdomBloc.state, emitsInOrder(expectedStates));
    });
  });
}
```
_Code Snippet XXX: Wisgen Wisdom BLoC Tests with Mockito [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

First we create our Mock classes. For this test we need a mock _Repository_ and a mock _Buildcontext_ [[@flutterdevteamBuildContextClass2018]](https://api.flutter.dev/flutter/widgets/BuildContext-class.html). In the _setUp()_ function, we initialize our BLoC and our mocks and inject the mock Repository into our BLoC. In the _test()_ function, we tell our mock Repository to send a set of wisdom when it's _fetch()_ function is called. Now we can send a fetch event to the BLoC, and check if it emits the correct states in order.

## Equality in Dart

| ⚠   | By default, all comparisons in Dart work based on references and not base on values [@angelovUnitTestingBloc2019; @angelovEquatableDartPackage2019] |
| --- | :-------------------------------------------------------------------------------------------------------------------------------------------------- |

```dart
Wisdom wisdom1 = Wisdom(id: 1, text: "Back up your Pictures", type: "tech");

print(wisdom1 ==  Wisdom(id: 1, text: "Back up your Pictures", type: "tech")); //false
```
_Code Snippet XXX: Equality in Flutter_

This can be an easy thing to trip over during testing, especially when comparing States emitted by BLoCs. Luckily, Felix Angelov released the _Equatable_ package in 2019 [[@angelovEquatableDartPackage2019]](https://pub.dev/packages/equatable#-example-tab-). It's an easy way to overwrite how class equality is handled. If we make a class extend the _Equatable_ class, we can set the properties it is compared by. We do this by overwriting it's _props_ attribute. This is used in Wisgen to make the States of the wisdom BLoC compare based on the wisdom the carry:

```dart
///The Wisdom BLoC has 2 States: Loaded and Error
///We can infer it is loading when we are not reviving new items through the stream
@immutable
abstract class WisdomState extends Equatable {}

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

///Broadcasted on Network Error
class ErrorWisdomState extends WisdomState {
  final Exception exception;
  ErrorWisdomState(this.exception);

  @override
  List<Object> get props => [exception];
}
```
_Code Snippet XXX: Wisgen Wisdom States with Equatable [[@faustWisgen2019]](https://github.com/Fasust/wisgen)_

If we wouldn't use Equatable, the test form snippet XXX could not functions properly, as two states carrying the same wisdom would still be considers different by the test framework.

| 🕐  | TLDR | If you don't want your classes to be compared base on their reference, use the Equatable package [[@angelovEquatableDartPackage2019]](https://pub.dev/packages/equatable#-example-tab-) |
| --- | ---- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

- testing a bloc
- Dependency injection problems in Flutter

# _Ref-Dump
