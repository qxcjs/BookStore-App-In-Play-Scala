package services

import javax.inject.Inject

import models.Book
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.{GetResult, JdbcProfile}

class BookService @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class BookTable(tag: Tag) extends Table[Book](tag, "book") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def title = column[String]("title")

    def price = column[Int]("price")

    def author = column[String]("author")

    def * = (id, title, price, author) <> ((Book.apply _).tupled, Book.unapply)
  }

  implicit val getBookResult = GetResult(r => Book(r.<<, r.<<, r.<<, r.<<))

  private val books = TableQuery[BookTable]

  def list(): Future[Seq[Book]] =
    db.run {
      sql"""select * from "book" """.as[Book]
    }

  def findById(id: Int): Future[Option[Book]] =
    db.run {
      sql"""select * from "book" where "id"=${id} """.as[Book].headOption
    }

  def update(b: Book): Future[Int] =
    db.run {
      sqlu"""update "book" set "title"=${b.title}, "price"=${b.price}, "author"=${b.author} where "id"=${b.id}"""
    }

  def delete(id: Int): Future[Int] =
    db.run {
      sqlu"""delete from "book" where "id"=${id}"""
    }

  def insert(b: Book): Future[Int] =
    db.run {
      sqlu"""insert into "book" values (${b.title}, ${b.price}, ${b.author})"""
    }

  def create(title: String, price: Int, author: String): Future[Int] =
    db.run {
      sql"""insert into "book"("title","price","author") values (${title}, ${price}, ${author})"""
    }
}
