package dk.fitfit.phonefinder

import android.Manifest.permission.*
import android.arch.persistence.room.Room
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import dk.fitfit.phonefinder.domain.Contact
import dk.fitfit.phonefinder.repository.AppDatabase

// Inspiration: https://android.jlelse.eu/detecting-sending-sms-on-android-8a154562597f

class MainActivity : AppCompatActivity() {
    private val SMS_PERMISSION_CODE = 0

    private fun showRequestPermissionsInfoAlertDialog() {
        showRequestPermissionsInfoAlertDialog(true)
    }

    private fun isSmsPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this, RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, SEND_SMS) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(this, arrayOf(RECEIVE_SMS, READ_SMS, SEND_SMS), SMS_PERMISSION_CODE)
    }

    private fun showRequestPermissionsInfoAlertDialog(makeSystemRequest: Boolean) {
        val builder = AlertDialog.Builder(this)
        // TODO: Write better dialog messages
        builder.setTitle(R.string.permission_alert_dialog_title)
        builder.setMessage(R.string.permission_dialog_message)

        // TODO: There should be a build in string for ok
        builder.setPositiveButton(R.string.action_ok) { dialog, _ ->
            dialog.dismiss()
            if (makeSystemRequest) {
                requestReadAndSendSmsPermission()
            }
        }

        builder.setCancelable(false)
        builder.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            SMS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted. Update ui accordingly
                    Toast.makeText(this, "Granted!", Toast.LENGTH_SHORT).show()
                } else {
                    // Permission denied...
                    Toast.makeText(this, "Denied!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(!isSmsPermissionGranted()) {
            showRequestPermissionsInfoAlertDialog()
        }
/*
        val database = Room.databaseBuilder(this, AppDatabase::class.java, "Sample.db")
                .allowMainThreadQueries()
                .build()
        val repository = database.contactRepository()
        val contact1 = repository.insert(Contact("snot", 1))
        val contact2 = repository.insert(Contact("snot2", 2))
        val contact3 = repository.insert(Contact("snot3", 3))
        val contacts = repository.findAll()
        for (contact in contacts) {
            Log.e("MyTag", contact.contactId)
            Toast.makeText(this, contact.contactId, Toast.LENGTH_SHORT).show()
        }
*/
/*
        val smsBroadcastReceiver = SmsBroadcastReceiver()
        smsBroadcastReceiver.setListener(object : SmsBroadcastReceiver.Listener {
            override fun onTextReceived(sender: String, message: String) {
                // TODO: If white listed number and command found... execute command
                Toast.makeText(this@MainActivity, "$sender: $message", Toast.LENGTH_SHORT).show()
                Log.e("MyTag", "$sender: $message")
            }
        })

        // TODO: Add else part of if statement
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            registerReceiver(smsBroadcastReceiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
        }
*/
    }
}
