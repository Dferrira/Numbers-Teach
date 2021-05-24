package com.dferreira.numbers_teach.lesson;


/**
 * Handles the press of the buttons of the menu
 */
public class LessonMenuDelegator {


    /**
     * Reload the current slide
     */
    public void reload() {
        LessonService.getInstance().reload();
    }

    /**
     * Goes to the previous slide
     */
    public void previous() {
        LessonService.getInstance().previous();
    }

    /**
     * Depending if the slide is playing or not is going to change state
     */
    public void playOrPause() {
        LessonService.getInstance().setIsPlaying(!LessonService.getInstance().isPlaying());
    }

    /**
     * Goes to the next slide
     */
    public void forward() {
        LessonService.getInstance().next();
    }
}
