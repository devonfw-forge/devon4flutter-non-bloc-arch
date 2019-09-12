# What options are there? 
  - Vanilla, Redux, Bloc, Provide/Scoped Model
  - Which one will I focus on and Why?
  - Google went bach and forth on this as well.

# BLoC
- Why this one?
- Origin
- UI only publishes and subscribes
- **Build Interface code how you want it to look like -> then make it work**
- 4 Rules for BLoCs
  - Only Sinks In & Streams out
  - Dependencies Injectable
  - No Plattform Branching
  - Implementation can be whatever you want
- 4 Rules for UI Classes
  - "Complex Enough" views have a BLoC
  - Components do not format the inputs they send to the BLoC
  - Output are formated as little as possible
  - If you do have Platform Branching, It should be dependent on a single BLoC bool output
 
---
# References 