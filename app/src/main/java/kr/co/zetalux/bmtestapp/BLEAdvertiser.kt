package kr.co.zetalux.bmtestapp

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.os.ParcelUuid
import android.util.Log
import android.widget.Toast

class BLEAdvertiser(private val context: Context) : AdvertiseCallback(),
    HashUUID {

    override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
        super.onStartSuccess(settingsInEffect)
        Toast.makeText(context, "ADVERTISE_START", Toast.LENGTH_SHORT).show()
    }

    override fun onStartFailure(errorCode: Int) {
        super.onStartFailure(errorCode)
        Log.d("trigger_BLE", "onStartFailure: $errorCode")
    }

    @SuppressLint("MissingPermission")
    fun startBLEAdvertising(uuid: ParcelUuid, duration: Int) {
        val adapter =
            (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
        isBLEAvailable(adapter) ?: return

        val advertiser = adapter.bluetoothLeAdvertiser

        val settingsBuilder = AdvertiseSettings.Builder().run {
            setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
            setTimeout(duration)
            setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
            setConnectable(false)
        }

        val dataBuilder = AdvertiseData.Builder().run {
            addServiceUuid(uuid)
            setIncludeTxPowerLevel(false)
            setIncludeDeviceName(false)
        }

        advertiser.startAdvertising(
            settingsBuilder.build(),
            dataBuilder.build(),
            this@BLEAdvertiser
        )
    }


    private fun isBLEAvailable(adapter: BluetoothAdapter): Boolean? {
        var count = 0
        val checkingArray =
            arrayListOf("Le2MPhy", "LeCodedPhy", "LeExtendedAdvertising", "LePeriodicAdvertising")

        val array =
            arrayListOf(
                adapter.isLe2MPhySupported,
                adapter.isLeCodedPhySupported,
                adapter.isLeExtendedAdvertisingSupported,
                adapter.isLePeriodicAdvertisingSupported
            )

        for ((i, isPossible) in array.withIndex()) {
            if (isPossible)
                Log.d("trigger_isPossible_BLE", "${checkingArray[i]} 가능")
            else {
                Log.d("trigger_isPossible_BLE", "${checkingArray[i]} 없음")
                count++
            }
        }
        return if (count == 4) {
            Log.d("trigger_ERROR_BLE", "NOT SUPPORTED")
            null
        } else true
    }

}