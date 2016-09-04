package com.dferreira.numbers_teach.exercise_icons.helpers;

import android.text.TextUtils;
import android.view.MenuItem;

import com.dferreira.numbers_teach.exercise_icons.R;
import com.dferreira.numbers_teach.exercise_icons.models.ExerciseType;

import java.util.HashMap;

/**
 * Helper to get the existing icons of the exercises
 */
public class ExerciseIconsHelper {

    private static final ExerciseType selectImages = new ExerciseType(false, true, true, R.mipmap.ic_wallpaper_black_48dp);
    private static final ExerciseType listen = new ExerciseType(true, true, false, R.mipmap.ic_hearing_black_48dp);
    private static final ExerciseType read = new ExerciseType(true, false, true, R.mipmap.read);

    /**
     * @return Return the identifier to the menu of exercises
     */
    public static int getExerciseIconsMenu() {
        return R.menu.exercises_menu;
    }

    /**
     * Gets one menu item and return matching type of exercise
     *
     * @param item Menu item of the exercise
     * @return matching type of exercise
     */
    public static ExerciseType getTypeOfExercise(MenuItem item) {
        if (item == null) {
            return null;
        } else {
            int id = item.getItemId();
            if (id == R.id.listening_and_read_exercise) {
                return selectImages;
            } else if (id == R.id.select_picture_listening_exercise) {
                return listen;
            } else if (id == R.id.select_picture_read_exercise) {
                return read;
            } else {
                return null;
            }
        }
    }

    /**
     * Giving the type of exercise returns the respective icon
     *
     * @param exerciseType Type of exercise
     * @return The icon of the type of exercise
     */
    public static Integer getExerciseIcon(ExerciseType exerciseType) {
        HashMap<String, ExerciseType> typesOfExercise = new HashMap<>(3);
        typesOfExercise.put(selectImages.toString(), selectImages);
        typesOfExercise.put(listen.toString(), listen);
        typesOfExercise.put(read.toString(), read);

        if ((exerciseType == null) || (TextUtils.isEmpty(exerciseType.toString()))) {
            return null;
        } else {
            String exerciseTypeStr = exerciseType.toString();
            if (typesOfExercise.containsKey(exerciseTypeStr)) {
                return typesOfExercise.get(exerciseTypeStr).getExerciseIcon();
            } else {
                return null;
            }
        }

    }
}
