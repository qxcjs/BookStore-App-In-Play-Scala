package models

import play.api.libs.json.Json

case class Book(
                 id: Int,
                 title: String,
                 price: Int,
                 author: String
               )
object Book {
  implicit val bookFormat = Json.format[Book]
}
