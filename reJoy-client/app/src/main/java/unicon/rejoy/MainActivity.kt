package unicon.rejoy

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*
import java.net.InetAddress

class MainActivity : AppCompatActivity() {
    val SERVER_IP = "192.168.0.116"
    val SERVER_PORT = 9090

    val KEY_UP = "w"
    val KEY_DOWN = "s"
    val KEY_LEFT = "a"
    val KEY_RIGHT = "d"
    val KEY_A = "w"
    val KEY_B = "space"

    lateinit var client: ReJoyConnect

    lateinit var vibrator: Vibrator
    var canVibrate: Boolean = false
    val milliseconds = 12L

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        client = ReJoyConnect()
        client.connect(InetAddress.getByName(SERVER_IP), SERVER_PORT)

        vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        canVibrate = vibrator.hasVibrator()

        button_up.setOnTouchListener { v, event ->
            touchKey(event, KEY_UP)
            true
        }
        button_down.setOnTouchListener { v, event ->
            touchKey(event, KEY_DOWN)
            true
        }
        button_left.setOnTouchListener { v, event ->
            touchKey(event, KEY_LEFT)
            true
        }
        button_right.setOnTouchListener { v, event ->
            touchKey(event, KEY_RIGHT)
            true
        }
        button_a.setOnTouchListener { v, event ->
            touchKey(event, KEY_A)
            true
        }
        button_b.setOnTouchListener { v, event ->
            touchKey(event, KEY_B)
            true
        }
    }

    fun touchKey(motionEvent: MotionEvent, key: String) {
        if (canVibrate) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // API 26
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        milliseconds,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                // This method was deprecated in API level 26
                vibrator.vibrate(milliseconds)
            }
        }

        when(motionEvent.action) {
            MotionEvent.ACTION_DOWN -> client.pressKey(key)
            MotionEvent.ACTION_UP -> client.releaseKey(key)
        }
    }
}