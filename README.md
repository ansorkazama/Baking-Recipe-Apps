# Baking Time
 Android Baking Apps, presents content from Udacity http://go.udacity.com/android-baking-app-json

## History
This application was submitted by Muhammad Ansharullah (ansorkazama@gmail.com) as the project:<b>Baking App Project</b> to compete the requirements of the Associate Android Developer Fast Track Nanodegree Program course.

## Criteria

The app spesification checks off the following requirements that were demanded by the Udacity Review teams:

**General App Usage**

 - [x] Display recipes: App should display recipes from provided network resource.
 - [x] App Navigation: App should allow navigation between individual recipes and recipe steps.
 - [x] Utilization of RecyclerView: App uses RecyclerView and can handle recipe steps that include videos or images.
 - [x] App conforms to common standards found in the Android Nanodegree General Project Guidelines.

**Components and Libraries**
- [x] Master Detail Flow and Fragments: Application uses Master Detail Flow to display recipe steps and navigation between them.
- [x] Exoplayer(MediaPlayer) to display videos: Application uses Exoplayer to display videos.
- [x] Proper utilization of video assets: Application properly initializes and releases video assets when appropriate.
- [x] Proper network asset utilization: Application should properly retrieve media assets from the provided network links. It should properly handle network requests.
- [x] UI Testing: Application makes use of Espresso to test aspects of the UI.
- [x] Third-party libraries: Application sensibly utilizes a third-party library to enhance the app's features. That could be helper library to interface with ContentProviders if you choose to store the recipes, a UI binding library to avoid writing findViewById a bunch of times, or something similar.

**Homescreen Widget**
- [x] Application has a companion homescreen widget: Application has a companion homescreen widget.
- [x] Widget displays ingredient list for desired recipe: Widget displays ingredient list for desired recipe.

## Features
Dynamically created tabbed viewpager
Make remote networking calls to retrieve data
Display video via ExoPlayer
Espresso testing
Homescreen widget
Recyclerviews
Activity/Fragment lifecycles rotation handling
Multi-pane for tablets
Activity intents

## Libraries
- ExoPlayer
- Butterknife
- Retrofit 2.2
- Espresso 2.2.2
- Cardview 25.3.1

  ## License and Disclaimers

Portions of this page are modifications based on work created and
shared by Google and used according to terms described in the Creative Commons 3.0 Attribution License.
See also the LICENSE.txt and LICENSECREATIVECOMMON.txt file at the top level of the repo.

** and **

Copyright (C) 2015 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
