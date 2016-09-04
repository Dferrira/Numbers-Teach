package com.dferreira.numbers_teach.scores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.exercise_icons.helpers.ExerciseIconsHelper;
import com.dferreira.numbers_teach.generic.ui.IScoreExerciseActivity;
import com.dferreira.numbers_teach.models.ExerciseResult;
import com.dferreira.numbers_teach.repositories.ExerciseResultRepository;

import java.text.SimpleDateFormat;


/**
 * List adapter that is going to be responsible for provide a list of activities to the user learn the language
 */
@SuppressWarnings("WeakerAccess")
public class GlobalScoresListAdapter extends ArrayAdapter<ExerciseScoreIconHolder> implements AdapterView.OnItemClickListener {

    private final java.util.List<ExerciseResult> scores;
    private final LayoutInflater inflater;

    private final Activity activity;

    /**
     * @param activity Context where the list of activities will be used
     * @param language Language that was selected by the user to use
     */
    public GlobalScoresListAdapter(Activity activity, String language) {
        super(activity, R.layout.activity_item);
        this.inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ExerciseResultRepository exerciseResultRepository = new ExerciseResultRepository(activity);
        this.scores = exerciseResultRepository.getGlobalScoresList(language);
        this.activity = activity;
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
            convertView = inflater.inflate(R.layout.global_score_item, parent, false);
            holder = new ExerciseScoreIconHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
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
        Integer imageResourceId = ExerciseIconsHelper.getExerciseIcon(item.getExerciseType());
        if (imageResourceId == null) {
            holder.icon.setVisibility(View.INVISIBLE);
        } else {
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(imageResourceId);
        }
        holder.date.setText(dateStr);
        holder.score.setText(scoreLabel);

        return convertView;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ExerciseResult exerciseResult = scores.get(position);
        if ((exerciseResult != null) && (activity != null)) {
            if (activity instanceof IScoreExerciseActivity) {
                IScoreExerciseActivity scoreExerciseActivity = (IScoreExerciseActivity) activity;
                scoreExerciseActivity.setExerciseProperties(exerciseResult.getActivity(), exerciseResult.getExerciseType(), view);
            }
        }
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
