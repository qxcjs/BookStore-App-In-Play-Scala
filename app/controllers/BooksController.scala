package controllers

import javax.inject.{Inject, Singleton}

import models.Book
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

@Singleton
class BooksController @Inject()(messagesAction: MessagesActionBuilder,components: ControllerComponents) extends AbstractController(components) {

  val bookForm = Form(
    mapping(
      "id" -> number,
      "title" -> text,
      "price" -> number,
      "author" -> text
    )(Book.apply)(Book.unapply)
  )

  // for all books
  def index = Action {
    Ok(views.html.books.index(Book.allBooks()))
  }

  //  def create = Action {
  //    val bookData = bookForm.bindFromRequest().get
  //    Ok(views.html.books.create())
  //  }


  // for create book
  def create = messagesAction { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.books.create(bookForm))
  }

  // for save book
  def save() =  messagesAction { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[Book] =>
      BadRequest(views.html.books.index(Book.allBooks()))
    }

    val successFunction = { book: Book =>
      Book.add(book)
      Redirect("/books")
    }

    val formValidationResult = bookForm.bindFromRequest
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
