package models

import scala.collection.mutable.ArrayBuffer

case class Book(
                 id: Int,
                 title: String,
                 price: Int,
                 author: String
               ) {
}

object Book {
  private val books = ArrayBuffer[Book](
    Book(1, "Java", 16, "liss"),
    Book(2, "C++", 20, "jane"),
    Book(3, "PHP", 12, "matin"),
  )

  def allBooks() = books

  def add(book: Book) = {
    books += book
  }

  def findById(id: Int): Book = {
    val allBooks = for (book <- books if book.id==id) yield book
    if(allBooks.isEmpty) null else allBooks(0)
  }

  def updateById(oldBook:Book) = {
    val book = findById(oldBook.id)
    if(book!=null){
      deleteById(book.id)
      add(book)
    }
  }

  def deleteById(id:Int) = {
    for(i <- 0 until books.length){
      if(findById(id)!=null) books.remove(i)
    }
  }
}