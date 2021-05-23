package data_layer.model

import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType
import com.dferreira.numbersteach.datalayer.model.ExerciseResult

class FakeExerciseResultFactory {
    fun createExerciseResult(): ExerciseResult {
        return ExerciseResult(
            fakeId,
            fakeActivity,
            fakeLanguage,
            fakeFinalScore,
            fakeMaxScore,
            fakeCreatedDate,
            fakeExerciseType.toString()
        )
    }

    companion object {
        const val fakeId = 121L
        const val fakeActivity = "fakeActivity"
        const val fakeLanguage = "fakeLanguage"
        const val fakeFinalScore = 12L
        const val fakeMaxScore = 20L
        const val fakeCreatedDate = "Fake Date"
        val fakeExerciseType = ExerciseType.Listen
    }
}