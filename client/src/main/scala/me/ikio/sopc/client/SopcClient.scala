package me.ikio.sopc.client

import java.net.InetSocketAddress

import akka.actor.{PoisonPill, ActorRef, Actor}
import akka.io.{UdpConnected, IO}
import akka.util.ByteString

class SopcClient(remote: InetSocketAddress) extends Actor {
  import context.system
  IO(UdpConnected) ! UdpConnected.Connect(self, remote)

  def receive = {
    case UdpConnected.Connected =>
      println("Connected.")
      context.become(ready(sender()))
      self ! "ping"
    case _: String =>
      println("Not Connected yet.")
  }

  def ready(connection: ActorRef): Receive = {
    case _: String => // TODO create message class
      val now = System.currentTimeMillis.toString
      connection ! UdpConnected.Send(ByteString(now))
    case UdpConnected.Received(resp) =>
      val currentMillis = System.currentTimeMillis
      val sentMillis = resp.decodeString("US-ASCII").toLong
      println(s"Received response. RTT: ${currentMillis - sentMillis} millis.")
    case d @ UdpConnected.Disconnect =>
      connection ! d
    case UdpConnected.Disconnected =>
      context.stop(self)
  }
}
