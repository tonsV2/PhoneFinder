package dk.fitfit.phonefinder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log

// TODO: Only react to messages from "list of numbers" and starts with list of commands
class SmsBroadcastReceiver : BroadcastReceiver() {
    private var listener: Listener? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            var sender = ""
            var message = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    sender = smsMessage.displayOriginatingAddress
                    message += smsMessage.messageBody
                }
            } else {
                val smsBundle = intent.extras
                if (smsBundle != null) {
                    val pdus = smsBundle.get("pdus") as Array<Any>
                    if (pdus == null) {
                        // Display some error to the user
                        Log.e(TAG, "SmsBundle had no pdus key")
                        return
                    }
                    val messages = arrayOfNulls<SmsMessage>(pdus.size)
                    for (i in messages.indices) {
                        messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                        message += messages[i]?.messageBody
                    }
                    sender = messages[0]?.originatingAddress.toString()
                }
            }

            // TODO: Add condition for calling the listener here
            // Should this condition be a lambda expression passed to SmsBroadcastReceiver upon instantiation?
            listener?.onTextReceived(sender, message)
/*
            if (sender == serviceProviderNumber && message.startsWith(serviceProviderSmsCondition)) {
                if (listener != null) {
                    listener.onTextReceived(message)
                }
            }
*/
        }
    }

    internal fun setListener(listener: Listener) {
        this.listener = listener
    }

    internal interface Listener {
        fun onTextReceived(sender: String, message: String)
    }

    companion object {
        private val TAG = "SmsBroadcastReceiver"
    }
}
