Page Table of Contents
- [Introduction](#introduction)
- [Declarative Programming vs Imperative Programming](#declarative-programming-vs-imperative-programming)
- [Declarative Programming in Flutter](#declarative-programming-in-flutter)
- [Efficiency of Re-Builds](#efficiency-of-re-builds)

## Introduction

If you are coming from the native mobile world and *imperative* frameworks like IOS [\[15\]](https://developer.apple.com/ios/) and Android [\[16\]](https://developer.android.com/), developing with Flutter [\[1\]](https://flutter.dev/) can take a while to get used to. Flutter, other then those frameworks mentioned above, is *declarative*. This section will teach you how to think about developing apps declaratively and one of the most important concepts of Flutter: *State* [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt).

## Declarative Programming vs Imperative Programming

I will explain the difference between *declarative* and *imperative* using a metaphor: For a second, let’s think of programming as *talking* to the underlying framework. In this context, an imperative approach is telling the framework **exactly** what you want it to do. “Imperium” (Latin) means “to command”. A declarative approach, on the other hand, would be describing to the framework what kind of result you want to get and then letting the framework decide on how to achieve that result. “Declaro” (Latin) means “to explain” \[1\], \[12\], \[27\], \[28\]. Let’s look at a code example:

``` dart
List numbers = [1,2,3,4,5];
for(int i = 0; i < numbers.length; i++){
    if(numbers[i] > 3 ) print(numbers[i]);     
}
```

*Code Snippet 1: Searching through a list (Imperative)*

Here we want to print every entry in the list that is bigger than 3. We explicitly tell the framework to go through the list one by one and check each value.
In the declarative version, we simply state how our result should look like, but not how to reach it:

``` dart
List numbers = [1,2,3,4,5];
print(numbers.where((num) => num > 3));
```

*Code Snippet 2: Searching through a list (Declarative)*

One important thing to note here is, that the difference between imperative and declarative is not black and white. One style might bleed over into the other. Prof. David Brailsford from the University of Nottingham argues that as soon as you start using libraries for your imperative projects, they become a tiny bit more declarative. This is because you are then using functions that *describe* what they do and you no longer care how they do it [\[29\]](https://www.youtube.com/watch?v=4A2mWqLUpzw).

| 🕐 | TLDR | Imperative Programming is telling the framework **exactly** what you want it to do. Declarative Programming is describing to the framework what kind of result you want to get and then letting the framework decide on how to achieve that result. |
| - | ---- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |

## Declarative Programming in Flutter

Okay, now that we understand what Declarative Programming is, let’s take a look at Flutter specifically. This is a quote from Flutter’s official documentation:

> “Flutter is declarative. This means that Flutter builds its user interface to reflect the current State of your app” [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)

![UI = f(State)](https://github.com/Fasust/flutter-guide/wiki//images/ui-equals-function-of-state.png)

*Figure 7: UI = f(State) [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt/declarative)*

In Flutter, you never imperatively or explicitly call a UI element to change it. You rather *declare* that the UI should look a certain way, given a certain *State* [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). But what exactly is *State*?

| 📙 | State | Any data that can change over time [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) |
| - | ----- | :------------------------------------------------------------------------------------------------------------ |

Typical State examples are user data, a user’s scroll position within a list, a favorite list.

Let’s have a look at a classic UI problem and how we would solve it imperatively in Android and compare it to Flutter’s declarative approach. let’s say we want to build a button that changes its color to red when it is pressed. In Android we find the button by its ID, attach a listener, and tell that listener to change the background color when the button is pressed:

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

*Code Snippet 3: Red button in Android (Imperative)*

In Flutter, on the other hand, we never call the UI element directly, we instead declare that the button background should be red or blue depending on the App-Sate (here the bool “pressed”). We then declare that the *onPressed()* function should update the App State and rebuild the button:

``` dart
bool pressed = false; //State

@override
Widget build(BuildContext context) {
    return FlatButton(
        color: pressed ? Colors.red : Colors.blue,
        onPressed: () {
            setState(){ // trigger rebuild of the button
                pressed = !pressed;
            } 
        }
    );
}
```

*Code Snippet 4: Red button in Flutter (Declarative)*

## Efficiency of Re-Builds

Is it not very inefficient to re-render the entire Widget every time we change the State? That was the first question I had when learning about this topic. But I was pleased to learn, that Flutter uses something called “RenderObjects” to improve performance similar to Reacts [\[22\]](https://facebook.github.io/react-native/) virtual DOM.

> “RenderObjects persist between frames and Flutter’s lightweight Widgets tell the framework to mutate the RenderObjects between States. The Flutter framework handles the rest.” [\[27\]](https://flutter.dev/docs/get-started/flutter-for/declarative)

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/130-The-Widget-Tree">Next Chapter: The Widget Tree ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>