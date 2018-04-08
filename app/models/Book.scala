package models

case class Book(
                 id: Int,
                 title: String,
                 price: Int,
                 author: String
               )

object Book {
  private val books = scala.collection.mutable.HashMap[Int,Book](
    1 -> Book(1, "Java", 16, "liss"),
    2 -> Book(2, "C++", 20, "jane"),
    3 -> Book(3, "PHP", 12, "matin")
  )

  def allBooks() = books.values.toSeq

  def add(book: Book) = {
    books+=(book.id -> book)
  }

  def findById(id: Int): Book = {
    books.get(id).getOrElse(null)
  }

  def updateById(book: Book) = {
    add(book)
  }

  def deleteById(id: Int) = {
    books.remove(id)
  }
}