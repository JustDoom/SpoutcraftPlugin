# SpoutcraftPlugin

SpoutcraftPlugin is a plugin for [Bukkit](http://www.bukkit.org), which extends the Bukkit API to provide features that aren't normally available.

Spoutcraft was abandoned years ago so the ReSpouted team is aiming to get it back to a usable state for preservation purposes! Check out the original README me [here](./OLD_README.md)

## Getting started

The client is being worked on but the plugin is possible to compile if you have a CraftBukkit 1.6.4 file. Already compiled versions will be released at some point.

This also REQUIRES Java 8. It will not work on modern Java versions. The error this causes is `java.lang.NoClassDefFoundError: Could not initialize class org.spoutcraft.lib.cglib.proxy.Enhancer`.

## Contributing

Coming Soon

## The license

SpoutcraftPlugin is licensed under the [GNU Lesser General Public License Version 3][License].

## Compiling the source

SpoutcraftPlugin uses Maven to handle its dependencies.

* Download and install [Maven 2 or 3](http://maven.apache.org/download.html)
* Checkout this repo and run: `mvn clean install`
* Create a new directory named `lib` inside the project directory
* Download CraftBukkit for Minecraft 1.6.4
* Move the downloaded server file into the `lib` directory
* Rename the file to `craftbukkit.jar`
* Compile the project by running: `mvn package`

[License]: http://www.gnu.org/licenses/lgpl.html