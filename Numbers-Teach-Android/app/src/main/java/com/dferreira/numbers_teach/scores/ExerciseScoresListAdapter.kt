package com.dferreira.numbers_teach.scores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.data_layer.repositories.ExerciseResultRepositoryFactory
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType
import com.dferreira.numbersteach.datalayer.model.ExerciseResult

/**
 * List adapter that is going to be responsible for provide a list of scores got by the user in a
 * specific exercise
 */
class ExerciseScoresListAdapter(context: Context) :
    ArrayAdapter<ExerciseScoreIconHolder?>(context, R.layout.activity_item) {
    private val inflater: LayoutInflater = getContext()
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var scores: List<ExerciseResult> = listOf()

    /**
     * Update the list of scores got by the user in a specific exercise
     *
     * @param language     Language which were got the results
     * @param activity     Activity where was got the score
     * @param exerciseType Indicates if the user was seeing the correct (picture,audio, text) when got the result
     */
    fun updateListOfItems(language: String?, activity: String?, exerciseType: ExerciseType?) {
        val exerciseResultRepositoryFactory = ExerciseResultRepositoryFactory()
        val exerciseResultRepository =
            exerciseResultRepositoryFactory.createExerciseResultRepository(
                context
            )
        scores = exerciseResultRepository.getExerciseScoresList(
            language!!,
            activity!!, exerciseType!!
        )
        notifyDataSetChanged()
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * [LayoutInflater.inflate]
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     * we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     * is non-null and of an appropriate type before using. If it is not possible to convert
     * this view to display the correct data, this method can create a new view.
     * Heterogeneous lists can specify their number of view types, so that this View is
     * always of the right type (see [.getViewTypeCount] and
     * [.getItemViewType]).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ExerciseScoreIconHolder
        val (_, _, _, finalScore, maxScore, dateStr) = scores[position]
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exercise_score_item, parent, false)
            val date = convertView.findViewById<View>(R.id.date) as TextView
            val score = convertView.findViewById<View>(R.id.score) as TextView
            holder = ExerciseScoreIconHolder(
                null,
                date,
                score
            )
            convertView.tag = holder
        } else {
            holder = convertView.tag as ExerciseScoreIconHolder
        }
        val scoresFormat = context.resources.getString(R.string.scores_format)
        val scoreLabel = String.format(scoresFormat, finalScore, maxScore)
        holder.date.text = dateStr
        holder.score?.text = scoreLabel
        return convertView!!
    }

    /**
     * The number of activities to show to the user
     *
     * @return Number of activities
     */
    override fun getCount(): Int {
        return scores.size
    }

}