package unicon.rejoy

import java.net.InetAddress
import java.net.Socket
import java.nio.charset.Charset
import kotlin.concurrent.thread

class ReJoyConnect() {
    lateinit var socket: Socket

    fun connect(addr: InetAddress, port: Int) {
        thread {
            socket = Socket(addr, port)

            while (!socket.isOutputShutdown) {
                // ...
            }
        }
    }

    fun pressKey(key: String) {
        thread {
            socket.getOutputStream().write("DOWN;${key}".toByteArray(Charset.defaultCharset()))
        }
    }

    fun releaseKey(key: String) {
        thread {
            socket.getOutputStream().write("UP;${key}".toByteArray(Charset.defaultCharset()))
        }
    }
}