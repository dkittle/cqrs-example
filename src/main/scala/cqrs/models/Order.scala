package cqrs.models

import java.time.ZonedDateTime

import spray.json._

import scala.util.{Failure, Success, Try}

case class Order(
    orderId: String,
    posId: String,
    date: ZonedDateTime,
    customerId: String,
    items: Seq[Item],
    amount: Double,
    taxAmount: Double
) {
  require(!orderId.isEmpty, "order id cannot be empty")
  require(!posId.isEmpty, "POS id cannot be empty")
  require(!customerId.isEmpty, "customer id cannot be empty")
}

case class Item(
    name: String,
    itemId: String,
    quantity: Double,
    unitPrice: Double,
    tags: Seq[String]
) {
  require(!itemId.isEmpty, "item id cannot be empty")
  require(quantity > 0.0d, "quantity cannot be zero")
}

trait OrderJsonProtocol extends DefaultJsonProtocol {
  def parseZonedDateTime(date: JsValue): Try[ZonedDateTime] = {
    Try(ZonedDateTime.parse(date.convertTo[String]))
  }

  implicit object ZonedDateTimeFormat extends RootJsonFormat[ZonedDateTime] {
    override def read(value: JsValue): ZonedDateTime = parseZonedDateTime(value) match {
      case Success(date) => date
      case Failure(e) => throw DeserializationException("Invalid date format")
    }

    override def write(date: ZonedDateTime): JsValue = JsString(date.toString)
  }

  implicit val itemFormat: RootJsonFormat[Item] = jsonFormat5(Item.apply)
  implicit val orderFormat: RootJsonFormat[Order] =
    jsonFormat(Order.apply, "orderId", "posId", "date", "customerId", "items", "amount", "taxAmount")

}
