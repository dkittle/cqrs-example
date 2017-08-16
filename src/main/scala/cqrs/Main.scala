package cqrs

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

object Main extends App {

  implicit val system = ActorSystem("webserver-actor-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val config = ConfigFactory.load()
  val server = new WebServer(system)
//  val supervisorActor = system.actorOf(SupervisorActor.props, "someSupervisorActor")
  val bindingFuture =
    Http().bindAndHandle(server.routes, config.getString("http.interface"), config.getInt("http.port"))
}
