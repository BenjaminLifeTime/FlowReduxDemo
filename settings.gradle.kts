pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "FlowReduxDemo"
include(":androidApp")
include(":shared")
include(":iosApp")

/*****************************************************************************
 * Remove the following lines to use the proper release 0.11.0.
 * Move the FlowRedux project to a directory parallel to this project (or just adjust the path)
 * and checkout the 'feature/atomics_usage' branch to see how it works on iOS (and Android) without
 * crashing.
 */
includeBuild("../FlowRedux") {
    dependencySubstitution {
        substitute(module("com.freeletics.flowredux:flowredux")).with(project(":flowredux"))
        substitute(module("com.freeletics.flowredux:dsl")).with(project(":dsl"))
    }
}