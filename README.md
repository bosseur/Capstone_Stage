[![Build Status]]
# Capstone Stage 2
Final project for android developer nano degree

# App info
##Beach volley tour
App provides users with the possibility to vie information about beach volleyball tournaments.
 
App uses the [Fivb SDK](www.fivb.org/VisSDK/#VisSdk.html) to obtain data.

The App has 2 screens and a simple widget

##Tournaments screen

##Matches screen

##Current / next tournament widget
Shows the next tournament that wil com up if one is available or the 

# Developer info
I am using [lombok](https://projectlombok.org/) I recommend to install the plugin in Android studio

### Libraries used:
 * com.android.support **27.1.1**
    * support-v4
    * support-v13
    * appcompat-v7
    * recyclerview-v7
    * cardview-v7
    * design
 * com.android.support.constraint:constraint-layout **1.1.0**
 * com.google.android.gms:play-services-ads **15.0.1**
 * com.squareup.retrofit2 **2.4.0**
    * retrofit
    * converter-simplexml
 * com.squareup.picasso:picasso **2.5.2**
 * com.jakewharton:butterknife **8.8.1**
 * com.jakewharton.timber:timber **4.7.0**
 * com.google.firebase:firebase-core **16.0.0**
 * com.google.guava:guava **19.0**
 
 ###License
 
 MIT License
 
 Copyright (c) 2018 Ernst
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.

 
 ## Known issues
 Unfortunately teh data provided by th FIVB is not very consistent, so some matches from tournaments might not be displayed.
 
 The ordering of the matches can be of at sometimes. 
 
     


