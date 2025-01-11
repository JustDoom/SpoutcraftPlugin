plugins {
    id("java")
    id("maven-publish")
    id("com.gradleup.shadow") version "8.3.5"
}

group = "org.spoutcraft"
version = "1.6.4-SNAPSHOT"
base.archivesName = "SpoutcraftPlugin"

java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.maven.apache.org/maven2/")
    maven("https://oss.sonatype.org/content/repositories/releases")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://www.jitpack.io")
    maven("https://repo.md-5.net/content/repositories/public/")
}

dependencies {
    implementation("cglib:cglib:${"cglib_version"()}")
    implementation("commons-io:commons-io:${"commons_io_version"()}")
    implementation("net.sf.trove4j:trove4j:${"trove4j_version"()}")
    implementation("javax.xml.bind:jaxb-api:2.3.0")

    compileOnly("com.github.Bukkit:Bukkit:${"bukkit_version"()}")
    compileOnly("org.bukkit:craftbukkit:${"craftbukkit_version"()}")

    // If it breaks, use:
    // compileOnly(files("./lib/CraftBukkit-1.6.4-R2.1-SNAPSHOT.jar"))
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.processResources {
    val props = mapOf(
        "mainClass" to "org.getspout.spout.Spout",
        "buildNumber" to "plugin_build"(),
        "version" to project.version,
        "description" to "plugin_description"(),
        "url" to "plugin_website"(),
        "author" to "plugin_author"()
    )

    inputs.properties(props)

    filesMatching(listOf("plugin.yml")) {
        expand(props)
    }
}

tasks.shadowJar {
    isEnableRelocation = true
    relocationPrefix = "org.getspout.shadow"
    
    minimize {
        exclude(dependency("cglib:cglib:.*"))
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.build.get().finalizedBy(tasks.shadowJar.get())

operator fun String.invoke(): String = rootProject.ext[this] as? String ?: throw IllegalStateException("Property $this is not defined")
