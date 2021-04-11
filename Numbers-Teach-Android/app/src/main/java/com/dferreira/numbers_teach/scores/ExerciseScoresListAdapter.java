package com.dferreira.numbers_teach.scores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType;
import com.dferreira.numbers_teach.models.ExerciseResult;
import com.dferreira.numbers_teach.repositories.ExerciseResultRepository;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * List adapter that is going to be responsible for provide a list of scores got by the user in a
 * specific exercise
 */
@SuppressWarnings("WeakerAccess")
public class ExerciseScoresListAdapter extends ArrayAdapter<ExerciseScoreIconHolder> {

    private final LayoutInflater inflater;
    private List<ExerciseResult> scores;

    /**
     * @param context Context where the list of activities will be used
     */
    public ExerciseScoresListAdapter(Context context) {
        super(context, R.layout.activity_item);
        this.inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Update the list of scores got by the user in a specific exercise
     *
     * @param language     Language which were got the results
     * @param activity     Activity where was got the score
     * @param exerciseType Indicates if the user was seeing the correct (picture,audio, text) when got the result
     */
    public void updateListOfItems(String language, String activity, ExerciseType exerciseType) {

        ExerciseResultRepository exerciseResultRepository = new ExerciseResultRepository(getContext());
        this.scores = exerciseResultRepository.getExerciseScoresList(language, activity, exerciseType);
        this.notifyDataSetChanged();
    }


    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExerciseScoreIconHolder holder;


        ExerciseResult item = scores.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exercise_score_item, parent, false);
            holder = new ExerciseScoreIconHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.score = (TextView) convertView.findViewById(R.id.score);
            convertView.setTag(holder);
        } else {
            holder = (ExerciseScoreIconHolder) convertView.getTag();
        }

        String dateStr = SimpleDateFormat.getDateTimeInstance().format(item.getCreatedDate());
        int finalScore = item.getFinalScore();
        int maxScore = item.getMaxScore();
        String scoresFormat = getContext().getResources().getString(R.string.scores_format);
        String scoreLabel = String.format(scoresFormat, finalScore, maxScore);
        holder.date.setText(dateStr);
        holder.score.setText(scoreLabel);

        return convertView;
    }

    /**
     * The number of activities to show to the user
     *
     * @return Number of activities
     */
    @Override
    public int getCount() {
        return (this.scores == null) ? 0 : this.scores.size();
    }
}
