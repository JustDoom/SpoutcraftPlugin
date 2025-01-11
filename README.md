# SpoutcraftPlugin

SpoutcraftPlugin is a plugin for [Bukkit](http://www.bukkit.org), which extends the Bukkit API to provide features that aren't normally available.

Spoutcraft was abandoned years ago, so the ReSpouted team is aiming to get it back to a usable state for preservation purposes! Check out the original README me [here](./OLD_README.md)

## Getting started

The client is being worked on but the plugin should be possible to compile. Precompiled versions will be released at some point.

This REQUIRES Java 8. It will NOT work on modern Java versions. It will cause an error along the lines of `java.lang.NoClassDefFoundError: Could not initialize class org.spoutcraft.lib.cglib.proxy.Enhancer`.

## Contributing

Coming Soon

## The license

SpoutcraftPlugin is licensed under the [GNU Lesser General Public License Version 3][LICENSE.txt].

## Compiling the source

SpoutcraftPlugin uses Maven to handle its dependencies.

* Download and install [Gradle](https://gradle.org/)
* Checkout this repo and run: `./gradlew build`
* Look for the file ending in `-all.jar` in `build/libs`!

[License (GNU LGPL v3)]: http://www.gnu.org/licenses/lgpl.html
