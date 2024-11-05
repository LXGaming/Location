listOf(
    "api",
    "bungee",
    "common",
    "velocity"
).forEach {
    include(it)
    findProject(":${it}")?.name = "location-${it}"
}