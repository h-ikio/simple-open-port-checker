package me.ikio.sopc.server

import java.net.InetSocketAddress

import akka.actor.{ActorRef, Actor}
import akka.io.{Udp, IO}

class SopcServer(isa: InetSocketAddress) extends Actor {
  import context.system
  IO(Udp) ! Udp.Bind(self, isa)

  def receive = {
    case Udp.Bound(local) =>
      context.become(ready(sender()))
  }

  def ready(socket: ActorRef): Receive = {
    case Udp.Received(data, remote) =>
      val processed = data.decodeString("US-ASCII")
      println(s"Received request.")
      socket ! Udp.Send(data, remote) // echo back
    case Udp.Unbind => socket ! Udp.Unbind
    case Udp.Unbound => context.stop(self)
  }
}
