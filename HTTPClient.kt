package RC_Client

import java.io.*
import java.net.ConnectException
import java.net.Socket
import java.net.UnknownHostException
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory



    fun main(args: Array<String>) {
        try {
            val factory = SSLSocketFactory.getDefault() as SSLSocketFactory // create SSL socket
            println("Method Example >> GET / PROTOCOL/VERSION")
            println("Request Method: ")
            val request = readLine()!! // read method
            println("Host (Either Domain Name or IP): ")
            val host = readLine()!! //read host
            println("port: ")
            val port = readLine()!!.toInt() //read port
            val socket = if (port == 80) Socket("$host", port) //if it is http just create a socket
                        else if (port == 443) {  //if it is https create a Socket from SSLSocket type
                            factory.createSocket("$host", port) as SSLSocket
                        }
                        else {
                            println("Try with port 443 or 80") //if the port isn't either 80 or 443
                        return
                        }


            val out = PrintWriter(
                BufferedWriter(OutputStreamWriter(socket.outputStream))) //get the Response
            out.println("$request") // passing the response with the method
            out.println()
            out.flush()

            if (out.checkError()) println( //Error Handler
                "SSLSocketClientGET / HTTP/1.0:  java.io.PrintWriter error"
            )
            val `in` = BufferedReader( InputStreamReader(socket.inputStream)) // read the response
            var inputLine: String?

            while (`in`.readLine().also { inputLine = it } != null) println(inputLine) //print the headers
            `in`.close()
            out.close()
            socket.close() // close the sockets
        }
        catch (e: ConnectException){
            println("400 Bad Request")
        }
        catch (e: UnknownHostException){
            println("UnknownHost")
        }
    }
