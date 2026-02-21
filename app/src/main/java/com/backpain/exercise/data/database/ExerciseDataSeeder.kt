package com.backpain.exercise.data.database

import com.backpain.exercise.data.database.entity.ExerciseEntity
import com.backpain.exercise.data.database.entity.ExerciseSetEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseDataSeeder @Inject constructor(
    private val database: AppDatabase
) {

    suspend fun seedExercises() {
        val exercises = listOf(
            ExerciseEntity(
                id = "plank_001",
                title = "Plank",
                description = "Core strengthening exercise",
                illustrationResourceId = 2131165184, // TODO: Add actual drawable resources
                youtubeVideoId = "ASdvN_XEl_c",
                durationSeconds = 30,
                difficultyLevel = "BEGINNER",
                instructions = listOf(
                    "Start in push-up position with hands shoulder-width apart",
                    "Engage your core muscles and keep your body in a straight line",
                    "Hold the position without letting your hips drop",
                    "Breathe normally throughout the exercise"
                ),
                isCompleted = false
            ),
            ExerciseEntity(
                id = "back_arch_001",
                title = "Back Arch",
                description = "Extension and flexibility exercise",
                illustrationResourceId = 2131165185,
                youtubeVideoId = "Rm3sEjqXpLk",
                durationSeconds = 45,
                difficultyLevel = "BEGINNER",
                instructions = listOf(
                    "Lie on your stomach with hands under your shoulders",
                    "Gently press up, lifting your chest off the floor",
                    "Keep your hips on the ground and look forward",
                    "Hold for a few seconds, then slowly lower back down"
                ),
                isCompleted = false
            ),
            ExerciseEntity(
                id = "bird_dog_001",
                title = "Bird Dog",
                description = "Balance and core stability exercise",
                illustrationResourceId = 2131165186,
                youtubeVideoId = "LrBQPOQ3QGs",
                durationSeconds = 60,
                difficultyLevel = "BEGINNER",
                instructions = listOf(
                    "Start on all fours with hands under shoulders and knees under hips",
                    "Extend your right arm forward and left leg backward",
                    "Keep your back straight and core engaged",
                    "Hold for 3 seconds, then return to start position",
                    "Alternate sides and repeat"
                ),
                isCompleted = false
            ),
            ExerciseEntity(
                id = "table_pose_001",
                title = "Table Pose",
                description = "Strength and stability exercise",
                illustrationResourceId = 2131165187,
                youtubeVideoId = "M9kA8X3k-wE",
                durationSeconds = 40,
                difficultyLevel = "BEGINNER",
                instructions = listOf(
                    "Start on all fours with hands under shoulders",
                    "Lift your knees off the floor, keeping your back parallel to ground",
                    "Hold the position while maintaining core engagement",
                    "Slowly lower knees back to starting position"
                ),
                isCompleted = false
            ),
            ExerciseEntity(
                id = "child_pose_001",
                title = "Child Pose",
                description = "Gentle stretch and relaxation",
                illustrationResourceId = 2131165188,
                youtubeVideoId = "9kGLBpoIFQ8",
                durationSeconds = 60,
                difficultyLevel = "BEGINNER",
                instructions = listOf(
                    "Kneel on the floor with big toes touching",
                    "Sit back on your heels and fold forward",
                    "Rest your forehead on the floor and extend arms forward",
                    "Breathe deeply and relax into the stretch"
                ),
                isCompleted = false
            ),
            ExerciseEntity(
                id = "extension_001",
                title = "Extension Exercise",
                description = "Back extension movement",
                illustrationResourceId = 2131165189,
                youtubeVideoId = "Gb98g5V8Ie4",
                durationSeconds = 50,
                difficultyLevel = "INTERMEDIATE",
                instructions = listOf(
                    "Lie face down with hands behind your head",
                    "Gently lift your chest off the floor using back muscles",
                    "Keep your neck in line with your spine",
                    "Lower slowly and repeat the movement"
                ),
                isCompleted = false
            ),
            ExerciseEntity(
                id = "cat_pose_001",
                title = "Cat Pose",
                description = "Spinal flexibility exercise",
                illustrationResourceId = 2131165190,
                youtubeVideoId = "YRQk6TmPjSc",
                durationSeconds = 45,
                difficultyLevel = "BEGINNER",
                instructions = listOf(
                    "Start on all fours with hands under shoulders",
                    "Arch your back up toward the ceiling like a cat",
                    "Tuck your chin to your chest",
                    "Hold for a few seconds, then return to neutral",
                    "Repeat the movement rhythmically"
                ),
                isCompleted = false
            ),
            ExerciseEntity(
                id = "pelvic_tilt_001",
                title = "Pelvic Tilt",
                description = "Core and lower back engagement",
                illustrationResourceId = 2131165191,
                youtubeVideoId = "M7Z13tj_9e0",
                durationSeconds = 35,
                difficultyLevel = "BEGINNER",
                instructions = listOf(
                    "Lie on your back with knees bent and feet flat on floor",
                    "Tighten your abdominal muscles and flatten your lower back against floor",
                    "Hold for 5 seconds, then relax",
                    "Repeat the movement in a controlled manner"
                ),
                isCompleted = false
            ),
            ExerciseEntity(
                id = "gluteal_stretch_001",
                title = "Gluteal Stretch",
                description = "Hip and glute flexibility",
                illustrationResourceId = 2131165192,
                youtubeVideoId = "pexiK2j9I_4",
                durationSeconds = 55,
                difficultyLevel = "BEGINNER",
                instructions = listOf(
                    "Lie on your back with knees bent",
                    "Cross one ankle over the opposite knee",
                    "Gently pull the bottom leg toward your chest",
                    "Feel the stretch in your glute and hip area",
                    "Hold and switch sides"
                ),
                isCompleted = false
            ),
            ExerciseEntity(
                id = "side_plank_001",
                title = "Side Plank",
                description = "Lateral core strengthening",
                illustrationResourceId = 2131165193,
                youtubeVideoId = "EiU1i6sYb8M",
                durationSeconds = 30,
                difficultyLevel = "INTERMEDIATE",
                instructions = listOf(
                    "Lie on your side with legs straight",
                    "Prop yourself up on your forearm",
                    "Lift your hips to create a straight line",
                    "Engage your core and hold the position",
                    "Switch sides and repeat"
                ),
                isCompleted = false
            ),
            ExerciseEntity(
                id = "bridge_001",
                title = "Bridge",
                description = "Glute and lower back strengthening",
                illustrationResourceId = 2131165194,
                youtubeVideoId = "A5-tE0c_5V0",
                durationSeconds = 60,
                difficultyLevel = "INTERMEDIATE",
                instructions = listOf(
                    "Lie on your back with knees bent and feet flat",
                    "Lift your hips off the floor until body forms straight line",
                    "Squeeze your glutes at the top of the movement",
                    "Hold for a few seconds, then slowly lower back down"
                ),
                isCompleted = false
            )
        )

        database.exerciseDao().insertExercises(exercises)
    }

    suspend fun seedExerciseSets() {
        val exerciseSets = listOf(
            ExerciseSetEntity(
                id = "beginner_set",
                name = "Beginner Set",
                description = "Gentle movements for pain relief",
                iconResourceId = 2131165195, // TODO: Add actual drawable resources
                exerciseIds = listOf(
                    "child_pose_001",
                    "cat_pose_001", 
                    "pelvic_tilt_001",
                    "gluteal_stretch_001"
                ),
                estimatedDurationMinutes = 15
            ),
            ExerciseSetEntity(
                id = "core_strength_set",
                name = "Core Strength",
                description = "Building support muscles",
                iconResourceId = 2131165196,
                exerciseIds = listOf(
                    "plank_001",
                    "side_plank_001",
                    "bridge_001",
                    "bird_dog_001"
                ),
                estimatedDurationMinutes = 20
            ),
            ExerciseSetEntity(
                id = "flexibility_set",
                name = "Flexibility",
                description = "Improving range of motion",
                iconResourceId = 2131165197,
                exerciseIds = listOf(
                    "back_arch_001",
                    "extension_001",
                    "table_pose_001",
                    "child_pose_001"
                ),
                estimatedDurationMinutes = 18
            )
        )

        database.exerciseSetDao().insertExerciseSets(exerciseSets)
    }

    suspend fun seedAllData() {
        seedExercises()
        seedExerciseSets()
    }
}
