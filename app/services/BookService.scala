package services

import javax.inject.Inject

import models.Book
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.JdbcProfile

class BookService @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._

  /**
    * Here we define the table. It will have a name of book
    */
  private class BookTable(tag: Tag) extends Table[Book](tag, "book") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def title = column[String]("title")

    def price = column[Int]("price")

    def author = column[String]("author")

    /**
      * This is the tables default "projection".
      *
      * It defines how the columns are converted to and from the Book object.
      *
      * In this case, we are simply passing the id, title,price and author parameters to the Book case classes
      * apply and unapply methods.
      */
    def * = (id, title, price, author) <> ((Book.apply _).tupled, Book.unapply)
  }

  /**
    * The starting point for all queries on the people table.
    */
  private val books = TableQuery[BookTable]

  /**
    * Create a book with the given title,price and author
    *
    * This is an asynchronous operation, it will return a future of the created book, which can be used to obtain the
    * id for that person.
    */
  def create(title: String, price: Int, author: String): Future[Book] = db.run {
    // We create a projection of just the title,price and author columns, since we're not inserting a value for the id column
    (books.map(b => (b.title, b.price, b.author))
      // Now define it to return the id, because we want to know what id was generated for the person
      returning books.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((nameAge, id) => Book(id, nameAge._1, nameAge._2, nameAge._3))
      // And finally, insert the book into the database
      ) += (title, price, author)
  }

  /**
    * List all the people in the database.
    */
  def list(): Future[Seq[Book]] = db.run {
    books.result
  }

  def findById(id: Int): Future[Option[Book]] = db.run(books.filter(_.id === id).result.headOption)

  def insert(book: Book): Future[Unit] = db.run(this.books += book).map(_ => ())

  def insert(book: Seq[Book]): Future[Unit] = db.run(this.books ++= book).map(_ => ())

  def update(id: Int, book: Book): Future[Unit] = {
    val bookToUpdate: Book = book.copy(id)
    db.run(books.filter(_.id === id).update(bookToUpdate)).map(_ => ())
  }

  def delete(id: Int): Future[Unit] = db.run(books.filter(_.id === id).delete).map(_ => ())

}
