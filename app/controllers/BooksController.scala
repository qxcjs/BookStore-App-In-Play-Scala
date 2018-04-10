package controllers

import javax.inject.{Inject, Singleton}

import models.Book
import play.api.data._
import play.api.mvc._
import forms.BookForm._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BooksController @Inject()(cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {
  // for all books
  def index = TODO

  def create = TODO

  // for save book
  def save() = TODO

  // for edit book
  def edit(id: Int) = TODO

  // for update book
  def update = TODO

  // for book detail
  def show(id: Int) = TODO

  // for destroy book
  def destroy(id: Int) = TODO

}
