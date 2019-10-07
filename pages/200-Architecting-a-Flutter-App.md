Page Table of Contents
- [Introduction](#introduction)
- [State Management vs Architecture](#state-management-vs-architecture)
- [Types of State](#types-of-state)
- [Contents of this Chapter](#contents-of-this-chapter)

## Introduction

The Most central topic of architecting a Flutter [(Flutter Dev Team 2018h)](https://flutter.dev/) app is *State Management* [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt). **Where** does my State sit, **who** need access to it and **how** do parts of the app access it? This chapter aims to answer those questions. You will learn about the two types of state, you will be introduced to the most three most popular State Management solutions and you will learn one of those State Management solutions (BLoC [(Soares 2018)](https://www.youtube.com/watch?v=PLHln7wHgPE)) in detail. You will also learn how to use the BLoC State Management solution in a clean and scalable 3-Layered architecture.

## State Management vs Architecture

I want to differentiate these two terms. Within the Flutter community *State Management* and *Architecture* are often used synonymously, but I think we should be careful to do so. State Management is a set of tools or a pattern with which we can manage the State within our app. Architecture on the other hand, is the over arching structure of our app. A set of rules that our app conforms to. Any architecture for a Flutter application will have some sort of State Management, but State Management is not an architecture by it self. I just want you to keep this in mind for the following chapters.

## Types of State

The Flutter documentaion [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt) differentiates between two types of State: *Ephemeral State* & *App State*.
Ephemeral State is State that is only required in one location IE inside of one Widget. Examples would be: scroll position in a list, highlighting of selected elements or the color change of a pressed button. This is the type of state that we don’t need to worry about that much or in other word, there is no need for a fancy State Management solution for Ephemeral State. We can simply use a Stateful Widget with some variables and manage Ephemeral State that way [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt). The more interesting type of State is App State. This is information that is required in multiple locations / by multiple Widgets. Examples would be: User data, a list of favorites or a shopping car. App State management is going to be the focus of this chapter.

![Ephemeral State vs App State Dession Tree](https://github.com/Fasust/flutter-guide/wiki//images/ephemeral-vs-app-state.png)

*Figure XXX: Ephemeral State vs App State Dession Tree [(Flutter Dev Team 2019b)](https://flutter.dev/docs/development/data-and-backend/state-mgmt)*

## Contents of this Chapter

  - [State Management Alternatives](https://github.com/Fasust/flutter-guide/wiki/210-State-Management-Alternatives)
  - [BLoC](https://github.com/Fasust/flutter-guide/wiki/220-BLoC)
  - [BLoC in Practice](https://github.com/Fasust/flutter-guide/wiki/230-BLoC-In-Practice)

<p align="right"><a href="https://github.com/Fasust/flutter-guide/wiki/210-State-Management-Alternatives">Next Chapter: State Management Alternatives ></a></p>
<p align="center"><a href="#">Back to Top</a></center></p>