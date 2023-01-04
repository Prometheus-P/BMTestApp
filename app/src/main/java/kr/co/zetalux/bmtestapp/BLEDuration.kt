package kr.co.zetalux.bmtestapp

enum class BLEDuration(
    val duration: Int
) {
    BLE_ALARM_DELAY(2 * 60 * 1000),
    BLE_DEFAULT_DURATION(8 * 1000),
    BLE_FULL_DURATION(3 * 60 * 1000),
    BLE_1_MINUTES(1 * 60 * 1000),
    BLE_2_MINUTES(2 * 60 * 1000),
    BLE_30_SECONDS(30 * 1000),
    DELAY_ADVERTISE(20 * 1000)
}