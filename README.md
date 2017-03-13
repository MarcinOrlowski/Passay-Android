[![Release](https://jitpack.io/v/MarcinOrlowski/passay-android.svg)]
(https://jitpack.io/#MarcinOrlowski/passay-android)
[![Dependency Status](https://dependencyci.com/github/MarcinOrlowski/Passay-Android/badge)](https://dependencyci.com/github/MarcinOrlowski/Passay-Android)



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
In short original Passay builds are created using Java 1.8 (this also includes dependencies like `Cryptacular`) so if you are building your Android project using Java 1.7 you will face compilation errors trying to add original Passay. And this is where this `passay-android` package comes handy - it is simply Java 1.7 friendly and comes as drop-in replacement using original `org.passay` package namepace.

Usage
=====

Edit your master `gradle.build` file and add jitpack repository (if you use other jitpack hosted artefacts,
then you have this already and this step can safely be skipped):

    allprojects {
        repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

then edit your module's `build.gradle` and add the following as your dependency:

    compile 'com.github.MarcinOrlowski:passay-android:1.2.0'


Important notes
===============

`passay-android` is built from recent `1.2.0` sources, however you should be informed that:

 * If you use `Java 1.8` in your project, try using [upstream Passay](http://www.passay.org/) **first**,
 * Due to code requirements, `minSdk` for this library is `19` which means `KitKat` or newer,
 * This package uses original `org.passay` namespace so it comes as drop-in replacement, but can also cause conflics (if so, raise the issue ticket please!),
 * `JDBCDictionary` is removed due to missing recent Java components on Android.
