package com.dferreira.numbers_teach.activities_list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.util.Pair
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.helpers.ImageHelper
import com.dferreira.numbers_teach.languages_dashboard.views.UIHelper
import com.dferreira.numbers_teach.lesson.LessonActivity
import com.dferreira.numbers_teach.preferences.PreferencesActivity
import com.dferreira.numbers_teach.scores.GlobalScoresListActivity
import com.dferreira.numbers_teach.select_images_exercise.SelectImagesExerciseActivity
import com.dferreira.numbers_teach.ui_models.ActivityItem
import com.dferreira.numbers_teach.ui_models.ActivityItemFactory

/**
 * List adapter that is going to be responsible for provide a list of activities to the user learn the language
 */
class ActivitiesListAdapter(
        private val activity: Activity,
        private val language: String) :
    ArrayAdapter<ViewHolder?>(activity, R.layout.activity_item),
    View.OnClickListener {
    private val activities: List<ActivityItem>
    private val inflater: LayoutInflater = context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var menu: Menu? = null



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
        var holder: ViewHolder
        val item = activities[position]
        if (convertView == null) {
            convertView = createItemView(item, parent)
        }
        val tag = convertView.tag as Pair<ViewHolder, ActivityItem>
        holder = tag.first
        holder.text.text = context.getText(item.labelResourceId)
        val drawable = ImageHelper.getDrawable(
            context, item.iconResourceId
        )
        holder.icon.setImageDrawable(drawable)
        return convertView
    }

    private fun createItemView(item: ActivityItem, parent: ViewGroup): View {
        val convertView = inflater.inflate(R.layout.activity_item, parent, false)
        val icon = convertView.findViewById<ImageView>(R.id.icon)
        val text = convertView.findViewById<TextView>(R.id.text)

        val holder = ViewHolder(icon, text)
        val tag = Pair(holder, item)
        convertView.tag = tag
        convertView.setOnClickListener(this)

        return convertView
    }

    /**
     * The number of activities to show to the user
     *
     * @return Number of activities
     */
    override fun getCount(): Int {
        return activities.size
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    override fun onClick(view: View) {
        val tag = view.tag as Pair<ViewHolder, ActivityItem>
        val item = tag.second
        menu!!.setGroupVisible(R.id.exercises_group, item.showMenu)
        if (item.showMenu) {
            return
        }

        val intent = Intent(context, item.activityToLaunch)
        intent.putExtra(LANGUAGE_KEY, language)
        UIHelper.startNewActivity(activity, view, intent)
    }

    /**
     * @param menu Menu that inflated in the activity
     */
    fun setMenu(menu: Menu) {
        this.menu = menu
    }

    companion object {
        private const val LANGUAGE_KEY = "language"
    }

    /**
     */
    init {
        val activityItemFactory = ActivityItemFactory()
        activities = activityItemFactory.createActivityItemList()
    }
}