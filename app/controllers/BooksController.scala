package controllers

import java.util.concurrent.TimeUnit
import scala.concurrent.duration._
import javax.inject.{Inject, Singleton}

import models.Book
import play.api.data._
import play.api.mvc._
import forms.BookForm._
import services.BookService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BooksController @Inject()(bookService: BookService, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  // for all books
  def index = Action.async {
    val books = bookService.list()
    books.map(book => Ok(views.html.books.index(book)))
  }

  def create = Action{ implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.books.create(form))
  }

  // for save book
  def save() = Action { implicit request: MessagesRequest[AnyContent] =>
//    val errorFunction = { errorForm: Form[Data] =>
//      Future.successful(Ok(views.html.index))
//    }
//
//    val successFunction = { data: Data =>
//      bookService.create(data.title, data.price, data.author).map { _ =>
//        Redirect(routes.BooksController.index)
//      }
//    }
//
//    val formValidationResult = form.bindFromRequest
//    formValidationResult.fold(errorFunction, successFunction)
    NotFound
  }

  // for edit book
  def edit(id: Int) = Action { implicit request: MessagesRequest[AnyContent] =>
//    val book: Book = bookService.findById(id).result(1 seconds).head
//    if (book == null) {
//      BadRequest("Not Found the Book !")
//    } else {
//      Ok(views.html.books.edit(book))
//    }
    NotFound
  }

  // for update book
  def update = Action { implicit request: MessagesRequest[AnyContent] =>
//    val errorFunction = { formWithErrors: Form[Data] =>
//      BadRequest(views.html.books.index(
//        bookService.list().result(1 seconds)
//      ))
//    }
//    val successFunction = { data: Data =>
//      val book = Book(id = data.id, title = data.title, price = data.price, author = data.author)
//      Redirect(routes.BooksController.index)
//    }
//
//    val formValidationResult = form.bindFromRequest
//    formValidationResult.fold(errorFunction, successFunction)
    NotFound
  }

  // for book detail
  def show(id: Int) = Action.async {
    val bookAndOptions = bookService.findById(id)
    bookAndOptions.map {
      case Some(book) => Ok(views.html.books.show(book))
      case None => NotFound
    }
  }

  // for destroy book
  def destroy(id: Int) = Action {
    Redirect(routes.BooksController.index)
  }

}
