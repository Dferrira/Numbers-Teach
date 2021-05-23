package data_layer.repositories

import com.dferreira.numbers_teach.data_layer.repositories.ExerciseResultRepository
import com.dferreira.numbers_teach.data_layer.repositories.ExerciseResultRepositoryImpl
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType
import com.dferreira.numbersteach.datalayer.model.ExerciseResult
import com.dferreira.numbersteach.datalayer.model.ExerciseResultQueries
import data_layer.database_driver.FakeDataBaseFactory
import data_layer.model.FakeExerciseResultFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ExerciseResultRepositoryTest {
    @Test
    fun exerciseResultRepository_can_save_score() {
        val repository = createExerciseResultRepository()

        val exerciseResult = createExerciseResult()
        val newExerciseResult = repository.insert(exerciseResult)
        assertNotNull(newExerciseResult)
        assertEquals(FakeExerciseResultFactory.fakeActivity, newExerciseResult!!.activity)
    }

    @Test
    fun exerciseResultRepository_can_get_global_scores_list() {
        val numberExerciseResult = 4
        val repository = createExerciseResultRepository()

        (1..numberExerciseResult).forEach { _ ->
            val exerciseResult = createExerciseResult()
            repository.insert(exerciseResult)
        }
        val exercisesOfLanguageList = repository
            .getGlobalScoresList(FakeExerciseResultFactory.fakeLanguage)
        assertEquals(numberExerciseResult, exercisesOfLanguageList.size)
    }

    @Test
    fun exerciseResultRepository_can_get_exercise_scores_list() {
        val numberExerciseResult = 2
        val repository = createExerciseResultRepository()

        (1..numberExerciseResult).forEach { _ ->
            val exerciseResult = createExerciseResult()
            repository.insert(exerciseResult)
        }
        val exercisesOfLanguageList = repository
            .getExerciseScoresList(
                FakeExerciseResultFactory.fakeLanguage,
                FakeExerciseResultFactory.fakeActivity,
                FakeExerciseResultFactory.fakeExerciseType
            )
        assertEquals(numberExerciseResult, exercisesOfLanguageList.size)
    }

    private fun createExerciseResult(): ExerciseResult {
        val exerciseResultFactory = FakeExerciseResultFactory()
        return exerciseResultFactory.createExerciseResult()
    }

    private fun createExerciseResultRepository(): ExerciseResultRepository {
        val exerciseResultDao: ExerciseResultQueries = createExerciseResultDao()
        return ExerciseResultRepositoryImpl(exerciseResultDao)
    }

    private fun ExerciseResultRepository.insert(exerciseResult: ExerciseResult): ExerciseResult? {
        return this.saveScore(
            exerciseResult.activity,
            exerciseResult.language,
            exerciseResult.final_score,
            exerciseResult.max_score,
            ExerciseType.Listen
        )
    }

    private fun createExerciseResultDao(): ExerciseResultQueries {
        val databaseFactory = FakeDataBaseFactory()
        val database = databaseFactory.createDatabase()
        return database.exerciseResultQueries
    }


}