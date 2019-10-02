Page Table of Contents
- [Introduction](#introduction)
- [Declarative Programming vs Imperative Programming](#declarative-programming-vs-imperative-programming)
- [Declarative Programming in Flutter](#declarative-programming-in-flutter)
- [Efficiency of Re-Builds](#efficiency-of-re-builds)

## Introduction

If you come from the native mobile world and *imperative* frameworks like IOS [(Apple 2010)](https://developer.apple.com/ios/) and Android [(Google LLC 2008)](https://developer.android.com/), developing with Flutter [(Flutter Dev Team 2018e)](https://flutter.dev/) can take while to get used to. Flutter, other then those frameworks mentioned above, is a *declarative* Framework. This section will teach you how to think about developing apps declaratively and one of the most important concepts of Flutter: *state* [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt).

## Declarative Programming vs Imperative Programming

But what exactly is the difference between *declarative* and *imperative*? I will try to explain this using a metaphor: For a second, let’s think of programming as *talking* to the underlying framework. In this context, an imperative approach is telling the framework **exactly** what you want it to do. “Imperium” (Latin) means “to command”. A declarative approach, on the other hand, would be describing to the framework what kind of result you want to get and letting the framework decide on how to achieve that result. “Declaro” (Latin) means “to explain” (Flutter Dev Team 2018e, 2019b, 2019e; Bezerra 2018). Let’s look at an example:

``` dart
List numbers = [1,2,3,4,5]
for(int i = 0; i < numbers.length; i++){
    if(numbers[i] > 3 ) print(numbers[i]);     
}
```

*Codesnippt 1: Number List (Imperative)*

Here we want to print every entry in the list that is bigger then 3. We explicitly tell the framework to go through the List one by one and check each value. In the declarative version, we simply state how our result should look like, but not how to reach it:

``` dart
List numbers = [1,2,3,4,5]
print(numbers.where((num) => num > 3));
```

*Codesnippt 2: Number List (Declarative)*

One important thing to note here is, that the difference between imperative and declarative is not black and white. One style might bleed over into the other. Prof. David Brailsford from the University of Nottingham argues that as soon as you start using libraries for your imperative projects, they become a tiny bit mor declarative. This is because you are then using functions that *describe* what they do and you no longer care how they do it [(Computerphile 2016)](https://www.youtube.com/watch?v=4A2mWqLUpzw).

| 🕐 TLDR | Imperative Programming is telling the framework **exactly** what you want it to do. Declarative Programming is describing to the framework what kind of result you want to get and letting the framework decide on how to achieve that result. |
| ------ | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

## Declarative Programming in Flutter

Okay, now that we understand what declarative means, let’s take a look at Flutter specifically. This is a quote from Flutters official documentation:

> “Flutter is declarative. This means that Flutter builds its user interface to reflect the current state of your app” [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)

![UI = f(State)](https://github.com/Fasust/flutter-guide/wiki//.images/ui-equals-function-of-state.png)

*Figure 7: UI = f(State) [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)*

This means that you never imperatively or explicitly call an UI element to change it. You rather *declare* that the UI should look a certain way, given a certain *state* [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt). But what exactly is *state*?

| ⚠ | State in Flutter is any data that can change over time |
| - | :----------------------------------------------------- |

Typical State examples: User Data, the position of a scroll bar, a favorite List

Let’s have a look at a classic UI problem and how we would solve it imperatively in Android and compare it to Flutters declarative approach. let’s say we want to build a button that changes it’s color to red when it is pressed. In Android we find the button by it’s ID, attach a listener and tell that listener to change the background color when the button is pressed:

``` java
Button button = findViewById(R.id.button_id);
button.setBackground(blue);
boolean pressed = false;

button.setOnClickListener(new View.OnClickListener() { 
    @Override
    public void onClick(View view) 
    { 
        button.setBackground(pressed ? red : blue);
    } 
}); 
```

*Codesnippt 3: Red button in Android (Imperative)*

In Flutter on the other hand, we never call the UI element directly, we instead declare that the button background should be red or blue depending on the App-Sate (here the bool “pressed”). We then declare that the onPressed function should update the app state and re-build the button:

``` dart
bool pressed = false; //State

@override
Widget build(BuildContext context) {
    return FlatButton(
        color: pressed ? Colors.red : Colors.blue,
        onPressed: () {
            setState(){ // trigger re-build of the button
                pressed = !pressed;
            } 
        }
    );
}
```

*Codesnippt 4: Red button in Flutter (Declarative)*

## Efficiency of Re-Builds

Is it not very inefficient to re-render the entire Widget every time we change the state? That was the first questions I had when learning about this topic. But I was pleased to learn, that Flutter uses something called “RenderObjects” to improve performance similar to Reacts [(Facebook 2015)](https://facebook.github.io/react-native/) virtual DOM.

> “RenderObjects persist between frames and Flutter’s lightweight Widgets tell the framework to mutate the RenderObjects between states. The Flutter framework handles the rest.” [(Flutter Dev Team 2019e)](https://flutter.dev/docs/get-started/flutter-for/declarative)


<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/130-The-Widget-Tree">Next Chapter: The Widget Tree ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>