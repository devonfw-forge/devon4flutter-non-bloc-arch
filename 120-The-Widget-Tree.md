Page Table of Contents
- [Introduction](#introduction)
- [Thinking Declaratively](#thinking-declaratively)
- [The Widget Tree](#the-widget-tree)
  - [Stateless Widgets](#stateless-widgets)
    - [Lifecycle](#lifecycle)
  - [Stateful Widgets](#stateful-widgets)
    - [Lifecycle](#lifecycle-1)
- [When to use Stateful/Stateful Widgets](#when-to-use-statefulstateful-widgets)
- [How to access State](#how-to-access-state)
- [References](#references)

## Introduction
This section will give you a better understanding of how programming in [Flutter (Flutter Dev Team 2018)](https://flutter.dev/) actually works. You will learn how a Declarative framework like Flutter differs form an Imperative Framework like Android or IOS. And lastly, you will be introduced to two of the most central topics of Flutter: The [_Widget Tree_ (Flutter Dev Team 2019)](https://flutter.dev/docs/development/ui/widgets-intro) and [_Sate_ (Flutter Dev Team 2019)](https://flutter.dev/docs/development/data-and-backend/state-mgmt).

## Thinking Declaratively
If you come from the native mobile world and frameworks like IOS and Android, developing with flutter can take while to get used to. Flutter, other then those frameworks mentioned above, is a _Declarative_ Framework. The difference becomes clear with some code:

```java
Button button = findViewById(R.id.button_id);
button.setBackground(red);
button.setOnClickListener(new View.OnClickListener() { 
    @Override
    public void onClick(View view) 
    { 
        // Do Something
    } 
}); 
```
_Code Snippet 1: Red button in Android (Imperative)_

```dart
return FlatButton(
  color: Colors.red,
  onPressed: () {
    // Do Something
  }
)
```
_Code Snippet 2: Red button in Flutter (Declarative)_

In Code Snippet 1, we get the button by it's ID and then we specifically tell the button that he should be red and what happens when it is pressed. In Code Snippet 2, we return the button and _declare_ that the button should be red and what happens when it is pressed.

## The Widget Tree
- the tree
- Buildcontext
- Widgets in General
  - Imutable
  - Each widget does one specific thing

### Stateless Widgets
#### Lifecycle
### Stateful Widgets
- what is state 
  - mutable
  - "n properties that change over time"
  - long life span
  - sticks around
- why 2 party
#### Lifecycle

## When to use Stateful/Stateful Widgets

## How to access State
- Lifting up
- Inherited Widget
- 2 types of state
  - local and global
  - more in architecture

<p align="center"><a href="#">Back to Top</a></center></p>

---
## References 