pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "multimodule"
include(":app")
include(":core")
include(":core:database-api")
include(":core:database-impl")
include(":core:network-api")
include(":core:network-impl")
include(":feature")
include(":feature:productsListScreen-api")
include(":feature:productsListScreen-impl")
include(":feature:cartScreen-api")
include(":feature:cartScreen-impl")
include(":feature:pdpProductScreen-api")
include(":feature:pdpProductScreen-impl")
include(":navigation-api")
include(":navigation-impl")
include(":core:commonUi")
include(":di")
