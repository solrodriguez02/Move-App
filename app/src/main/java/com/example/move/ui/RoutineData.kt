package com.example.move.ui

data class Exercise(
    val title :String,
    val imageUrl :String,
    val secs :Int,
    val reps :Int,
    val description :String
)

data class Cycle(
    val name :String,
    val exercises :List<Exercise>,
    val reps :Int
)

data class RoutineItem(
    val id :Int,
    val title: String,
    val imageUrl :String,
    val difficulty :String,
    val elements :List<String>,
    val approach :List<String>,
    val space :String,
    val description :String,
    val cycles :List<Cycle>,
    val time :Int,
    val score :Float
)

val exercises :List<Exercise> = listOf(
    Exercise("Jump rope", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/jump-rope-hop.jpg", 5, 15, "This exercise is fun!"),
    Exercise("Switch Kick", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/switch-kick.jpg", 2, 0, "This exercise is fun!"),
    Exercise("Rest", "", 5, 0, "This exercise is fun!"),
    Exercise("Windmill", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/windmill.jpg", 0, 14, "This exercise is fun!"),
    Exercise("Rest", "", 5, 0, "This exercise is fun!"),
    Exercise("Squat Jacks", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/squat-jack.jpg", 0, 15, "This exercise is fun!"),

    )

val exercises1 :List<Exercise> = listOf(
    Exercise("Switch Kick", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/switch-kick.jpg", 3, 0, "This exercise is fun!"),
    Exercise("Windmill", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/windmill.jpg", 4, 0, "This exercise is fun!"),
    Exercise("Rest", "", 5, 0, "This exercise is fun!"),
    Exercise("Jump rope", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/jump-rope-hop.jpg", 5, 0, "This exercise is fun!"),
    Exercise("Pivoting", "https://storage.googleapis.com/sworkit-assets/images/exercises/standard/middle-frame/pivoting-upper-cut.jpg", 3, 15, "This exercise is fun!"),
)

val cycles :List<Cycle> = listOf(
    Cycle("Warm up", exercises, 1),
    Cycle("Cycle 1", exercises, 2),
    Cycle("Cycle 2", exercises1, 3),
    Cycle("Cooling", exercises, 1)
)

val routine :RoutineItem = RoutineItem(14, "Senta-Senta", "https://s3.abcstatics.com/media/bienestar/2020/11/17/abdominales-kfHF--620x349@abc.jpeg",
    "Medium", listOf("Dumbells", "Rope"), listOf("Cardio"), "Ideal for reduced spaces", "Very fun exercise", cycles, 15, 3.4f)
