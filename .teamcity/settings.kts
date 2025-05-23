import jetbrains.buildServer.configs.kotlin.*

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2022.10"

project {

    subProject(ProjectSimple)
    subProject(ProjectMaven)
}


object ProjectMaven : Project({
    name = "projectMaven"

    buildType(ProjectMaven_Build)
})

object ProjectMaven_Build : BuildType({
    name = "Build"

    params {
        param("teamcity.tool.mavena", "%teamcity.tool.maven.DEFAULT%")
    }

    steps {
        step {
            type = "Projectparent_ProjectMaven_Recipe1"
            executionMode = BuildStep.ExecutionMode.DEFAULT
            param("env.PATH", "%env.PATH%:%teamcity.tool.mavena%/bin")
        }
    }
})


object ProjectSimple : Project({
    name = "projectSimple"

    buildType(ProjectSimple_Build)
})

object ProjectSimple_Build : BuildType({
    name = "Build"

    params {
        param("teamcity.tool.maven", "formbuild")
    }

    steps {
        step {
            type = "Projectparent_ProjectSimple_Build1"
            executionMode = BuildStep.ExecutionMode.DEFAULT
            param("env.PATH", "%env.PATH%:%teamcity.tool.maven%/bin")
        }
    }
})
