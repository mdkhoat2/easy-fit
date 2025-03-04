package com.example.jetpackcompose.presentation.di

import com.example.jetpackcompose.R

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: Int
){
    data object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.icon_home
    )
    data object Plan: BottomBarScreen(
        route = "plan",
        title = "Plan",
        icon = R.drawable.icon_plan
    )
    data object Forum: BottomBarScreen(
        route = "forum",
        title = "Forum",
        icon = R.drawable.icon_forum
    )
    data object Account: BottomBarScreen(
        route = "account",
        title = "Account",
        icon = R.drawable.icon_account
    )
}

object Routes {
    var selectWorkout = "selectWorkout"
    var sessionTracking = "session_tracking/{workoutId}"
    var wellDone = "wellDone"
    var newWorkout = "newWorkout"
    var login = "login"
    var forgotEmail = "forgotEmail"
    var forgotOTP = "forgotOTP"
    var register = "register"
    var CustomizeExercise = "CustomizeExercise"
    var EditWorkout = "EditWorkout"
    var editPlan = "EditPlan"
    var newExercise = "NewExercise"
    var changePassword = "ChangePassword"
    var notificationSetting = "NotificationSetting"
    var forumNotifications = "ForumNotifications"
    var forumReply = "ForumReplyScreen"
    var forumReportDetail = "ForumReportDetail?postContent={postContent}&timestamp={timestamp}"
    var guidance = "Guidance"
}