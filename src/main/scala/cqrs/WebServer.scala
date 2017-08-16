package cqrs

import java.time.ZonedDateTime

import akka.actor.ActorSystem
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import cqrs.models.{Item, Order, OrderJsonProtocol}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

class WebServer(system: ActorSystem) extends OrderJsonProtocol {

//  private val actor = system.actorSelection("/user/someSupervisorActor")
  val routes: Route =
    path("item") {
      get {
        val item = Item("salt & vinegar chips", "83451", 2.0, 1.25, Seq("snacks", "chips"))
        complete(OK, item)
      }
    } ~
      path("order") {
        get {
          val chips = Item("salt & vinegar chips", "83451", 2.0, 1.25, Seq("snacks", "chips"))
          val soda = Item("club soda", "9988563", 1.0, 2.55, Seq("drinks", "carbonated", "2l"))
          val order = Order("83123", "145a", ZonedDateTime.now(), "8300", Seq(chips, soda), 4.75, 0.00)
          complete(OK, order)
        }
        (post & entity(as[Order])) { order =>
          complete(Created, s"order ${order.orderId} created.")
        }
      } ~
      path("quit") {
        post {
          system.terminate()
          complete(OK, s"order system exiting\n")
        }
      }
}
