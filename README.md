========================================
Xposed mod for Samsung Galaxy devices
========================================

Running Touchwiz-based ROMs
Tested on AT&T S3 and GN2 but others have reported it working (in some capacity) on other Samsung devices.

The code is messy and most likely amateurish, as this was basically my first Xposed module & Android application.

I released it in binary form via the [Xposed Repo](http://repo.xposed.info/module/com.dr8.sbicons.sgs), [Google Play](https://play.google.com/store/apps/details?id=com.dr8.sbicons.sgs), and [XDA forums](http://forum.xda-developers.com/showthread.php?t=2546180). I no longer have the time to maintain
this project, so instead of letting it die a quiet death, I'm releasing the last version of my code to the public
for anyone to use. I only ask that you provide proper attribution if you plan on using any of this code in your
module or application. That should also include any code that I've received permission to use from others.

Requirements
============

Built with API v16, but might work with newer
Needs the ColorPickerPreference class that I commited to my github. There are slight changes that I made to allow
for manual hex color entry.

Screens
=======
![ScreenShot](https://raw.githubusercontent.com/agentdr8/xsbm_sgs/master/xsbm_sgs.png)

=====================
The MIT License (MIT)
=====================

Copyright (c) [2014] [@agentdr8]

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
