package controllers

import javax.inject.{Inject, Singleton}

import models.Book
import play.api.data._
import play.api.mvc._
import forms.BookForm._

@Singleton
class BooksController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  // for all books
  def index = Action {
    Ok(views.html.books.index(Book.allBooks()))
  }

  def create = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.books.create(form))
  }

  // for save book
  def save() =  Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[Data] =>
      BadRequest(views.html.books.index(Book.allBooks()))
    }

    val successFunction = { data: Data =>
      val book = Book(id = data.id,title=data.title, price = data.price,author = data.author)
      Book.add(book)
      Redirect(routes.BooksController.index)
    }

    val formValidationResult = form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

  // for edit book
  def edit(id: Int) = Action {implicit request: MessagesRequest[AnyContent] =>
    val book: Book = Book.findById(id)
    if (book == null) {
      BadRequest("Not Found the Book !")
    } else {
      Ok(views.html.books.edit(book))
    }
  }

  // for update book
  def update = Action {implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[Data] =>
      BadRequest(views.html.books.index(Book.allBooks()))
    }
    val successFunction = { data: Data =>
      val book = Book(id = data.id,title=data.title, price = data.price,author = data.author)
      Book.updateById(book)
      Redirect(routes.BooksController.index)
    }

    val formValidationResult = form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

  // for book detail
  def show(id: Int) = Action {
    val book: Book = Book.findById(id)
    if (book == null)
      BadRequest("Not Found the Book !")
    else
      Ok(views.html.books.show(book))
  }

  // for destroy book
  def destroy(id: Int) = Action {
    Book.deleteById(id)
    Redirect(routes.BooksController.index)
  }

}
