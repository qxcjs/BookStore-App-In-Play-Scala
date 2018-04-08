package controllers

import javax.inject.Inject
import views.html.booksv1._

import play.api.mvc.{AbstractController, ControllerComponents}

class BooksV1Controller @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

    // for all books
    def index = {
      TODO
    }

    // for create book
    def create ={
      TODO
    }

    // for save book
    def save = {
      TODO
    }

    // for edit book
    def edit(id:Int) = {
      TODO
    }

    // for update book
    def update = {
      TODO
    }

    // for destroy book
    def destroy(id:Int) = {
      TODO
    }

    // for book details
    def show(id:Int) = {
      TODO
    }

}
