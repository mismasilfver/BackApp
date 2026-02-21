package com.backpain.exercise.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.backpain.exercise.data.database.entity.ExerciseEntity
import com.backpain.exercise.data.database.entity.ExerciseSetEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExerciseDataSeederTest {

    private lateinit var database: AppDatabase
    private lateinit var seeder: ExerciseDataSeeder

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        seeder = ExerciseDataSeeder(database)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `should seed all 11 exercises`() = runTest {
        // When
        seeder.seedExercises()
        val exercises = database.exerciseDao().getAllExercises().first()

        // Then
        assertEquals(11, exercises.size)
        assertTrue(exercises.any { it.id == "plank_001" })
        assertTrue(exercises.any { it.id == "back_arch_001" })
        assertTrue(exercises.any { it.id == "bird_dog_001" })
        assertTrue(exercises.any { it.id == "table_pose_001" })
        assertTrue(exercises.any { it.id == "child_pose_001" })
        assertTrue(exercises.any { it.id == "extension_001" })
        assertTrue(exercises.any { it.id == "cat_pose_001" })
        assertTrue(exercises.any { it.id == "pelvic_tilt_001" })
        assertTrue(exercises.any { it.id == "gluteal_stretch_001" })
        assertTrue(exercises.any { it.id == "side_plank_001" })
        assertTrue(exercises.any { it.id == "bridge_001" })
    }

    @Test
    fun `should seed 3 exercise sets`() = runTest {
        // When
        seeder.seedExerciseSets()
        val exerciseSets = database.exerciseSetDao().getAllExerciseSets().first()

        // Then
        assertEquals(3, exerciseSets.size)
        assertTrue(exerciseSets.any { it.id == "beginner_set" })
        assertTrue(exerciseSets.any { it.id == "core_strength_set" })
        assertTrue(exerciseSets.any { it.id == "flexibility_set" })
    }

    @Test
    fun `should seed beginner set with correct exercises`() = runTest {
        // When
        seeder.seedExerciseSets()
        val beginnerSet = database.exerciseSetDao().getExerciseSetById("beginner_set").first()

        // Then
        assertNotNull(beginnerSet)
        assertEquals("Beginner Set", beginnerSet?.name)
        assertEquals(4, beginnerSet?.exerciseIds?.size)
        assertTrue(beginnerSet?.exerciseIds?.contains("child_pose_001") ?: false)
        assertTrue(beginnerSet?.exerciseIds?.contains("cat_pose_001") ?: false)
        assertTrue(beginnerSet?.exerciseIds?.contains("pelvic_tilt_001") ?: false)
        assertTrue(beginnerSet?.exerciseIds?.contains("gluteal_stretch_001") ?: false)
    }

    @Test
    fun `should seed core strength set with correct exercises`() = runTest {
        // When
        seeder.seedExerciseSets()
        val coreStrengthSet = database.exerciseSetDao().getExerciseSetById("core_strength_set").first()

        // Then
        assertNotNull(coreStrengthSet)
        assertEquals("Core Strength", coreStrengthSet?.name)
        assertEquals(4, coreStrengthSet?.exerciseIds?.size)
        assertTrue(coreStrengthSet?.exerciseIds?.contains("plank_001") ?: false)
        assertTrue(coreStrengthSet?.exerciseIds?.contains("side_plank_001") ?: false)
        assertTrue(coreStrengthSet?.exerciseIds?.contains("bridge_001") ?: false)
        assertTrue(coreStrengthSet?.exerciseIds?.contains("bird_dog_001") ?: false)
    }

    @Test
    fun `should seed flexibility set with correct exercises`() = runTest {
        // When
        seeder.seedExerciseSets()
        val flexibilitySet = database.exerciseSetDao().getExerciseSetById("flexibility_set").first()

        // Then
        assertNotNull(flexibilitySet)
        assertEquals("Flexibility", flexibilitySet?.name)
        assertEquals(4, flexibilitySet?.exerciseIds?.size)
        assertTrue(flexibilitySet?.exerciseIds?.contains("back_arch_001") ?: false)
        assertTrue(flexibilitySet?.exerciseIds?.contains("extension_001") ?: false)
        assertTrue(flexibilitySet?.exerciseIds?.contains("table_pose_001") ?: false)
        assertTrue(flexibilitySet?.exerciseIds?.contains("child_pose_001") ?: false)
    }

    @Test
    fun `should seed exercises with correct properties`() = runTest {
        // When
        seeder.seedExercises()
        val plank = database.exerciseDao().getExerciseById("plank_001").first()

        // Then
        assertNotNull(plank)
        assertEquals("Plank", plank?.title)
        assertEquals("Core strengthening exercise", plank?.description)
        assertEquals(30, plank?.durationSeconds)
        assertEquals("BEGINNER", plank?.difficultyLevel)
        assertFalse(plank?.isCompleted ?: true)
        assertTrue(plank?.instructions?.isNotEmpty() ?: false)
    }

    @Test
    fun `should handle duplicate seeding without errors`() = runTest {
        // When - seed twice
        seeder.seedExercises()
        seeder.seedExercises()
        val exercises = database.exerciseDao().getAllExercises().first()

        // Then - should still have 11 exercises (no duplicates)
        assertEquals(11, exercises.size)
    }
}
