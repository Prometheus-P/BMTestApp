package kr.co.zetalux.bmtestapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.ParcelUuid
import android.provider.Settings
import android.util.Log
import java.security.DigestException
import java.security.MessageDigest
import java.util.*

interface HashUUID {

    fun getHashUuid(context: Context): ParcelUuid {
        @SuppressLint("HardwareIds")
        val hashUUID = fun(): UUID {
            val id = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            val hashByteArray: ByteArray
            try {
                val md = MessageDigest.getInstance("SHA-256")
                md.update(id.toByteArray())
                hashByteArray = md.digest()
            } catch (e: CloneNotSupportedException) {
                e.printStackTrace()
                throw DigestException("couldn't make digest of partial content")
            }
            return UUID.nameUUIDFromBytes(hashByteArray)
        }
        val uuid = hashUUID()
        Log.d("BLE_hash_UUID_DeviceName", Build.DEVICE)
        Log.d("BLE_hash_UUID", "hash 로 만든 UUID 값: $uuid")
        return ParcelUuid(uuid)
    }
}
