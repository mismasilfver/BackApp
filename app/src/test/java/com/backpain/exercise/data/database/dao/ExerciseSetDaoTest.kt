package com.backpain.exercise.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.backpain.exercise.data.database.AppDatabase
import com.backpain.exercise.data.database.entity.ExerciseSetEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExerciseSetDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var exerciseSetDao: ExerciseSetDao

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        exerciseSetDao = database.exerciseSetDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun `should insert and retrieve exercise set`() = runTest {
        // Given
        val exerciseSet = ExerciseSetEntity(
            id = "beginner_set",
            name = "Beginner Set",
            description = "Gentle movements for pain relief",
            iconResourceId = 2131165187,
            exerciseIds = listOf("child_pose_001", "cat_pose_001", "pelvic_tilt_001", "gluteal_stretch_001"),
            estimatedDurationMinutes = 15
        )

        // When
        exerciseSetDao.insertExerciseSet(exerciseSet)
        val retrieved = exerciseSetDao.getExerciseSetById("beginner_set").first()

        // Then
        assertNotNull(retrieved)
        assertEquals("beginner_set", retrieved?.id)
        assertEquals("Beginner Set", retrieved?.name)
        assertEquals(4, retrieved?.exerciseIds?.size)
        assertEquals(15, retrieved?.estimatedDurationMinutes)
    }

    @Test
    fun `should get all exercise sets`() = runTest {
        // Given
        val exerciseSets = listOf(
            ExerciseSetEntity(
                id = "beginner_set", name = "Beginner Set", description = "Gentle movements",
                iconResourceId = 1, exerciseIds = listOf("child_pose_001"), estimatedDurationMinutes = 15
            ),
            ExerciseSetEntity(
                id = "core_strength_set", name = "Core Strength", description = "Building support",
                iconResourceId = 2, exerciseIds = listOf("plank_001"), estimatedDurationMinutes = 20
            ),
            ExerciseSetEntity(
                id = "flexibility_set", name = "Flexibility", description = "Range of motion",
                iconResourceId = 3, exerciseIds = listOf("back_arch_001"), estimatedDurationMinutes = 18
            )
        )

        // When
        exerciseSetDao.insertExerciseSets(exerciseSets)
        val allSets = exerciseSetDao.getAllExerciseSets().first()

        // Then
        assertEquals(3, allSets.size)
        assertTrue(allSets.any { it.id == "beginner_set" })
        assertTrue(allSets.any { it.id == "core_strength_set" })
        assertTrue(allSets.any { it.id == "flexibility_set" })
    }

    @Test
    fun `should handle empty exercise sets list`() = runTest {
        // When
        val emptySets = exerciseSetDao.getAllExerciseSets().first()

        // Then
        assertTrue(emptySets.isEmpty())
    }

    @Test
    fun `should return null for non-existent exercise set`() = runTest {
        // When
        val result = exerciseSetDao.getExerciseSetById("non_existent").first()

        // Then
        assertNull(result)
    }

    @Test
    fun `should support exercise sets with overlapping exercises`() = runTest {
        // Given - child_pose_001 appears in both beginner and flexibility sets
        val exerciseSets = listOf(
            ExerciseSetEntity(
                id = "beginner_set", name = "Beginner Set", description = "Gentle movements",
                iconResourceId = 1, exerciseIds = listOf("child_pose_001", "cat_pose_001"), estimatedDurationMinutes = 15
            ),
            ExerciseSetEntity(
                id = "flexibility_set", name = "Flexibility", description = "Range of motion",
                iconResourceId = 2, exerciseIds = listOf("back_arch_001", "child_pose_001"), estimatedDurationMinutes = 18
            )
        )

        // When
        exerciseSetDao.insertExerciseSets(exerciseSets)
        val beginnerSet = exerciseSetDao.getExerciseSetById("beginner_set").first()
        val flexibilitySet = exerciseSetDao.getExerciseSetById("flexibility_set").first()

        // Then
        assertNotNull(beginnerSet)
        assertNotNull(flexibilitySet)
        assertTrue(beginnerSet?.exerciseIds?.contains("child_pose_001") ?: false)
        assertTrue(flexibilitySet?.exerciseIds?.contains("child_pose_001") ?: false)
    }
}
