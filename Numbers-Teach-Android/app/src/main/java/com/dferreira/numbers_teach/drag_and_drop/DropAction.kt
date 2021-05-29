package com.dferreira.numbers_teach.drag_and_drop

/**
 * Action to take when is drop a view
 */
enum class DropAction {
    MOVE_VIEW,  //Move the dragged view
    NOTHING,  //In the end just restore
    MOVE_CHANGE_BACKGROUND //Move the dragged view and set the background of the destination
}