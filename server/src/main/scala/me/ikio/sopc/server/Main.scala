package me.ikio.sopc.server

import java.net.InetSocketAddress

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

// TODO add test
// TODO parse options
object Main extends App {
  val port = if (args.size == 1) args(1).toInt else 12345
  val config =s"""
      akka {
        actor {
          provider = "akka.remote.RemoteActorRefProvider"
        }
        remote {
          enabled-transports = ["akka.remote.netty.tcp"]
          netty.tcp {
            hostname = "0.0.0.0"
            port = $port
          }
        }
      }
      """

  val system = ActorSystem("system", ConfigFactory.load(ConfigFactory.parseString(config)))
  sys.addShutdownHook(system.shutdown())

  val props = Props(classOf[SopcServer], new InetSocketAddress("0.0.0.0", port))
  system.actorOf(props, "server")

  def usage(): Unit = {
    println("usage: server localport")
  }
}
