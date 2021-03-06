package com.loitp.model

import androidx.annotation.Keep
import org.greenrobot.eventbus.EventBus

@Keep
class MessageEvent {
    var msg: String? = null

    companion object {
        const val CREATE_TASK = "CREATE_TASK"
        const val UPDATE_TASK = "UPDATE_TASK"
        const val DELETE_TASK = "DELETE_TASK"

        fun postMsg(msg: String) {
            val messageEvent = MessageEvent().apply {
                this.msg = msg
            }
            EventBus.getDefault().post(messageEvent)
        }
    }
}
