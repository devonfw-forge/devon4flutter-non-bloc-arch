Page Table of Contents
- [Introduction](#introduction)
- [State Management vs Architecture](#state-management-vs-architecture)
- [Types of State](#types-of-state)
- [Contents of this Chapter](#contents-of-this-chapter)

## Introduction

The Most central topic of architecting a Flutter app [\[1\]](https://flutter.dev/) is *State Management* [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). **Where** does my State sit, **who** needs access to it, and **how** do they access it? This chapter aims to answer those questions. You will learn about the two types of State, you will be introduced to the three most popular [State Management Solutions](https://github.com/devonfw-forge/devonfw4flutter/wiki/210-State-Management-Alternatives) and you will learn one of those State Management Solutions ([BLoC](https://github.com/devonfw-forge/devonfw4flutter/wiki/220-BLoC) [\[7\]](https://www.youtube.com/watch?v=PLHln7wHgPE)) in detail. You will also learn how to use the BLoC State Management Solution in a clean and scalable [four-layered architecture](https://github.com/devonfw-forge/devonfw4flutter/wiki/220-BLoC).

## State Management vs Architecture

I want to differentiate these two terms. Within the Flutter community, *State Management* and *Architecture* are often used synonymously, but I think we should be careful to do so. State Management is, as the name indicates, managing the State of an application. This can be done with the help of a technology or a framework [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt), [\[55\]](https://en.wikipedia.org/w/index.php?title=State_management&oldid=914027862). Architecture, on the other hand, is the overarching structure of an app. A set of rules that an app conforms to [\[56\]](http://www.iso.org/cms/render/live/en/sites/isoorg/contents/data/standard/05/05/50508.html). Any architecture for a Flutter application will have some sort of State Management, but State Management is not an architecture by itself. I just want you to keep this in mind for the following chapters.

## Types of State

The Flutter documentation [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt) differentiates between two types of State: *Ephemeral State* & *App State*.
Ephemeral State is State that is only required in one location IE inside of one Widget. Examples would be: scroll position in a list, highlighting of selected elements or the color change of a pressed button. This is the type of State that we don’t need to worry about that much or in other words, there is no need for a fancy State Management Solution for Ephemeral State. We can simply use a Stateful Widget with some variables and manage Ephemeral State that way [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt). The more interesting type of State is App State. This is information that is required in multiple locations/by multiple Widgets. Examples would be user data, a list of favorites or a shopping cart. App State management is going to be the focus of this chapter.

![Ephemeral State vs App State decision tree](https://github.com/devonfw-forge/devonfw4flutter/wiki//images/ephemeral-vs-app-state.png)

*Figure 12: Ephemeral State vs App State decision tree [\[12\]](https://flutter.dev/docs/development/data-and-backend/state-mgmt)*

## Contents of this Chapter

  - [State Management Alternatives](https://github.com/devonfw-forge/devonfw4flutter/wiki/210-State-Management-Alternatives)
  - [BLoC](https://github.com/devonfw-forge/devonfw4flutter/wiki/220-BLoC)

<p align="right"><a href="https://github.com/devonfw-forge/devonfw4flutter/wiki/210-State-Management-Alternatives">Next Chapter: State Management Alternatives ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>