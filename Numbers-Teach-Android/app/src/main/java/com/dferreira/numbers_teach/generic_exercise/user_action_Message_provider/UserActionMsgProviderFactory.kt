package com.dferreira.numbers_teach.generic_exercise.user_action_Message_provider

object UserActionMsgProviderFactory {
    private var instance: UserActionMsgProvider? = null

    /**
     * Object get via factory
     *
     * @return An instance of the UserActionMsgProvider
     */
    fun createUserActionMsgProvider(): UserActionMsgProvider {
        val localInstance = instance ?: UserActionMsgProviderImpl()
        if(instance == null) {
            instance = localInstance
        }
        return localInstance
    }
}