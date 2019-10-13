[intro]: https://github.com/Fasust/flutter-guide/wiki
[framework]: https://github.com/Fasust/flutter-guide/wiki/100-The-Flutter-Framework
[under-hood]: https://github.com/Fasust/flutter-guide/wiki/110-Under-the-Hood
[declarative]: https://github.com/Fasust/flutter-guide/wiki/120-Thinking-Declaratively
[tree]: https://github.com/Fasust/flutter-guide/wiki/130-The-Widget-Tree
[async]: https://github.com/Fasust/flutter-guide/wiki/140-Asynchronous-Flutter
[architecture]: https://github.com/Fasust/flutter-guide/wiki/200-Architecting-a-Flutter-App
[statemng]: https://github.com/Fasust/flutter-guide/wiki/210-State-Management-Alternatives
[bloc]: https://github.com/Fasust/flutter-guide/wiki/220-BLoC
[test]: https://github.com/Fasust/flutter-guide/wiki/300-Testing
[conventions]: https://github.com/Fasust/flutter-guide/wiki/400-Conventions
[conclusion]: https://github.com/Fasust/flutter-guide/wiki/500-Conclusion
[refs]: https://github.com/Fasust/flutter-guide/wiki/600-References

# 500-Conclusion
## Introduction
//Last Snip 48
//Last Fig 28

So, you've made it. This is the final chapter of the guide. I will use this chapter ro reflect on the Guide, evaluate it's strengths and weaknesses, and highlight how I and this project will move foreword from here on out. I will also take this opportunity to share my personal opinion of the Flutter Framework [[@flutterdevteamFlutterFramework2018]](https://flutter.dev/). That being said, the guide has already been quite opinionated up until now.

## Evaluation of the Guide
I am pretty happy with how the Guide turned out. I managed to find a diverse range of sources for most of the topics I wanted to cover. I kept close to the _informal_, _from developer for developer_-style of the original Angular guide [[@ambuludiCapgeminiAngularGuide2019]](https://github.com/devonfw/devon4ng) and I think it reads pretty well. The Guide did end up being a bit longer then I planned (~50 A4 Pages without figures and references). If I would write it again, I would choose a more narrow and clear scope for what the guide should include. For example, I might have excluded or shortened chapter [1 The Flutter Framework][framework] and instead focused even more on chapter [2 Architecting a Flutter App][architecture]. I would have also liked to include more scientific sources on Flutter. But as it is still such a new Framework, next to no scientific research has so far been conducted on it. As of the writing of this Guide their is not a single paper published on the Flutter Framework though IEEE [[@ieeeIEEEXploreDigital1963]](https://ieeexplore.ieee.org/Xplore/home.jsp) or ACM [[@acmACMDigitalLibrary1947]](https://dl.acm.org/).

All in all, I think I fulfilled the goal I set myself when when starting this guide. To bridge the gap between the basics of FLutter and clean, structured Flutter development. The result is a resource I would have liked to have when trying to bridge that gap myself.

## Future of the Guide and Next Steps
This Guide was commissioned by Capgemini Cologne [[@capgeminiCapgeminiHomePage2019]](https://www.capgemini.com/us-en/). It is supposed to introduce their mobile developer to the Flutter Framework and help them evaluate if it is a valid option for future projects. It will also be the basis for my Bachelor Thesis I am starting this November. In my thesis I will create and document a large scale Flutter application for Capgemini. It is supposed to be an example project that other developers can use as a guideline for creating their own large scale Flutter applications. More specifically, Capgemini has used the "My Thai Star" App [[@linaresMyThaiStar2019]](https://github.com/devonfw/my-thai-star) as a reference for Angular [[@googlellcAngular2016]](https://angular.io/). I will create a Flutter version of it. 

### Suggestion for Future Works
One thing I and peers in the mobile development community [[@biorn-hansenSurveyTaxonomyCore2018]](http://doi.acm.org/10.1145/3241739) would like to see is more scientific research on the Flutter Framework: Lagre scale benchmarking, usability tests, saleability tests, architecture evaluation and so on.

## My Thoughts on Flutter
- positive
  - very efficient and smooth rendering 
    - unlike other cross-plattform solutions
  - was not a fan of state handeling at the beginning
    - now with bloc I am
    - necessary for efficacy
  - Great community
  - Flutter Team involves and listens to the community
- negative
  - ugly code
  - injection requires interface dependency

All in all. I wont be doing native dev again any time soon.

# _Ref-Dump
