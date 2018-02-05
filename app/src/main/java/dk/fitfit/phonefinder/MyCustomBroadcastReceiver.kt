package dk.fitfit.phonefinder

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Build
import android.provider.Telephony
import android.provider.Telephony.Sms.Intents.getMessagesFromIntent
import android.util.Log

// Inspiration: https://stackoverflow.com/a/38365231/672009
class MyCustomBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (action != null) {
                if (action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
                    // TODO: Create SmsHandlerInterface
                    // TODO: TextMessage class and possible TextMessageParser class
                    val (sender, message) = getSenderAndMessage(intent)
                    Log.e("MyTag", "MyCustomBroadcastReceiver: $sender: $message")
                    // if sender is on white list
                    // if message is command
                    // execute command
                }
            }
        }
    }

    private fun getSenderAndMessage(intent: Intent): Pair<String, String> {
        var sender = ""
        var message = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            for (smsMessage in getMessagesFromIntent(intent)) {
                sender = smsMessage.displayOriginatingAddress
                message += smsMessage.messageBody
            }
        }
        return Pair(sender, message)
    }
}
