What is Passay?
===============
Passay is password policy enforcement for Java. It builds on the success of [vt-password](https://code.google.com/p/vt-middleware/wiki/vtpassword) and provides a comprehensive and extensible feature set like password validation or password generation. 

 * Password Validation enforces password policy by validating candidate passwords against a configurable rule set. Passay provides a comprehensive set of rules for common cases and supports extension through a simple [rule interface](http://www.passay.org/javadocs/org/passay/Rule.html).
 
 * [Password generator](http://www.passay.org/javadocs/org/passay/PasswordGenerator.html) creates new passwords using a configurable rule set. The password generator is extensible like all Passay components.

Resources
=========

 * Passay website: http://www.passay.org/
 * Passay repository: https://github.com/vt-middleware/passay
 * Documentation: http://www.passay.org/

What is this Passay for Android for?
====================================
In short - if you are building your Android project using Java 1.8 and Jack, you should first to try
to use original Passay artefacts. However as these are build using Java 1.8 you will face issues trying
to add it to your project built with Java 1.7. And this is where `passay-android` package comes handy - it
simply is Java 1.7 friendly and comes as drop-in replacement reusing original `org.passay` package namepace.

Differences?
============

`passay-android` is built from recent `1.2.0` sources. There're however some slight differences:

* Due to requirements, `minSdk` is 19 (API Level: 19 is `KitKat`)
* This package uses original `org.passay` namespace
* `JDBCDictionary` is removed due to missing recent Java components on Android.

Usage
=====

This package is available from both jcenter and jitpack.io.


jitpack.io
==========

If you prefer `jitpack`, then edit your master `gradle.build` file and add jitpack repository (if
you use other jitpack hosted artefacts, then you have this already and this step can safely be
skipped):

    allprojects {
        repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

then edit your module's `build.gradle` and add the following as your dependency:

    compile 'com.github.MarcinOrlowski:passay-android:1.2.0'


bintray
=======

If you prefer `jcenter`, then edit your module `gradle.build` and add:

    compile "com.marcinorlowski:passay:1.2.0"
