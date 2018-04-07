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

//  def save() = Action(parse.form(bookForm)) { implicit request =>
//    val bookData = request.body
//    Book.add(bookData)
//    Redirect("/books/index")
//  }

  // for edit book
  def edit(id: Int) = Action {
    val book: Book = Book.findById(id)
    if (book == null) {
      BadRequest("Not Found the Book !")
    } else {
      Ok(views.html.books.edit(book))
    }
  }

  // for update book
  def update = Action {
    Redirect("/books/index")
  }

  // for book detail
  def show(id: Int) = Action {
    val book: Book = Book.findById(id)
    if (book == null)
      BadRequest("Not Found the Book !")
    else
      Ok(views.html.books.show(book))
  }

  // for destory book
  def destory(id: Int) = Action {
    Book.deleteById(id)
    Redirect("/books/index")
  }

}
