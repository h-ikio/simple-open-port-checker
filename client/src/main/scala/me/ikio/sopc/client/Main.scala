package me.ikio.sopc.client

import java.net.InetSocketAddress

import akka.actor.{ActorRef, Props, ActorSystem}
import com.typesafe.config.ConfigFactory

// TODO parse options
object Main extends App {
  val (hostname, port, timeout) =
    if (args.size == 3) {
      (args(0), args(1).toInt, args(2).toLong)
    } else {
      ("127.0.0.1", 12345, 10000L)
    }

  val config =s"""
      akka {
        actor {
          provider = "akka.remote.RemoteActorRefProvider"
        }
        remote {
          enabled-transports = ["akka.remote.netty.tcp"]
          netty.tcp {
            hostname = $hostname
            port = $port
          }
        }
      }
      """

  val system = ActorSystem("system", ConfigFactory.load(ConfigFactory.parseString(config)))
  sys.addShutdownHook(system.shutdown())

  ConfigFactory.parseString("")
  val clientProps = Props(classOf[SopcClient], new InetSocketAddress(hostname, port))
  val client = system.actorOf(clientProps, "client")

  while (true) {
    ping(client, timeout)
  }

  def ping(client: ActorRef, duration: Long): Unit = {
    val f = client ! "ping"

    Thread.sleep(duration)

    println(s"Elpsed $duration millis.")
  }

  def usage(): Unit = {
    println("usage: client remotehost remoteport")
  }
}
