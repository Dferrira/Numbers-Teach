package com.dferreira.numbers_teach.scores

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.data_layer.model.getExerciseType
import com.dferreira.numbers_teach.data_layer.repositories.ExerciseResultRepositoryFactory
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType
import com.dferreira.numbers_teach.exercise_icons.models.exercise_type_icon.ExerciseTypeIconFactory
import com.dferreira.numbers_teach.generic.ui.IScoreExerciseActivity
import com.dferreira.numbersteach.datalayer.model.ExerciseResult

/**
 * List adapter that is going to be responsible for provide a list of activities to the user learn the language
 */
class GlobalScoresListAdapter(
        val activity: Activity,
        val language: String
) :
    ArrayAdapter<ExerciseScoreIconHolder?>(activity, R.layout.activity_item),
    OnItemClickListener {
    private val scores: List<ExerciseResult>
    private val inflater: LayoutInflater

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
        val (_, _, _, finalScore, maxScore, dateStr, exerciseTypeString) = scores!![position]
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.global_score_item, parent, false)
            val icon = convertView.findViewById<View>(R.id.icon) as ImageView
            val date = convertView.findViewById<View>(R.id.date) as TextView
            val score = convertView.findViewById<View>(R.id.score) as TextView
            holder = ExerciseScoreIconHolder(
                icon,
                date,
                score
            )
            convertView.tag = holder
        } else {
            holder = convertView.tag as ExerciseScoreIconHolder
        }
        val scoresFormat = context.resources.getString(R.string.scores_format)
        val scoreLabel = String.format(scoresFormat, finalScore, maxScore)
        val exerciseType = stringToExerciseType(exerciseTypeString)
        val exerciseTypeIconFactory = ExerciseTypeIconFactory()
        val icon = exerciseTypeIconFactory.createIcon(exerciseType)
        val imageResourceId = icon.exerciseIcon
        if (imageResourceId == null) {
            holder.icon!!.visibility = View.INVISIBLE
        } else {
            holder.icon!!.visibility = View.VISIBLE
            holder.icon.setImageResource(imageResourceId)
        }
        holder.date.text = dateStr
        holder.score!!.text = scoreLabel
        return convertView!!
    }

    private fun stringToExerciseType(exerciseTypeString: String): ExerciseType {
        return ExerciseType.valueOf(exerciseTypeString)
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     *
     *
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     * will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        val exerciseResult = scores[position]
        if (activity is IScoreExerciseActivity) {
            val scoreExerciseActivity = activity as IScoreExerciseActivity
            scoreExerciseActivity.setExerciseProperties(
                exerciseResult.activity,
                exerciseResult.getExerciseType(),
                view
            )
        }
    }

    /**
     * The number of activities to show to the user
     *
     * @return Number of activities
     */
    override fun getCount(): Int {
        return scores.size
    }


    init {
        inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val exerciseResultRepositoryFactory = ExerciseResultRepositoryFactory()
        val exerciseResultRepository =
            exerciseResultRepositoryFactory.createExerciseResultRepository(
                context
            )
        scores = exerciseResultRepository.getGlobalScoresList(language)
    }
}