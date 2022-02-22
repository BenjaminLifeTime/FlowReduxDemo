# FlowReduxDemo

This simple KMM project helps to perceive the problems of FlowRedux with iOS.
When removing the lines 20-25 in settings.gradle.kts the current release of FlowRedux (v0.1.1)
is taken as a dependency. This causes the iOS App to crash at the very start due to an InvalidMutabilityException.
To make it work again, add the removed lines in settings.gradle.kts again and move the FlowRedux project to a 
directory parallel to this project (or just adjust the path). Then checkout the 'feature/atomics_usage' branch.
