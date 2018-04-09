package controllers

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
  def save() = Action.async{ implicit request: MessagesRequest[AnyContent] =>
    form.bindFromRequest.fold(
      errorForm => {
        Future.successful(Ok(views.html.index()))
      },
      book => {
        bookService.create(book.title, book.price, book.author).map { _ =>
          Redirect(routes.BooksController.index())
        }
      }
    )
  }

  // for edit book
  def edit(id: Int) = Action.async { implicit request: MessagesRequest[AnyContent] =>
    val bookAndOptions = bookService.findById(id)
    bookAndOptions.map {
      case Some(book) => Ok(views.html.books.edit(book))
      case None => NotFound
    }
  }

  // for update book
  def update = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[Data] =>
      BadRequest(views.html.index())
    }
    val successFunction = { data: Data =>
      val book = Book(id = data.id, title = data.title, price = data.price, author = data.author)
      bookService.update(book)
      Redirect(routes.BooksController.index())
    }

    val formValidationResult = form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
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
    bookService.delete(id)
    Redirect(routes.BooksController.index())
  }

}
