package models

case class BookV1(
              id: Int,
              title: String,
              price: Int,
              anthor: String
            )


object BookV1 {
  private val books = scala.collection.mutable.ArrayBuffer.empty[BookV1]

  books += BookV1(1, "JAVA核心技术卷I", 20, "liss")
  books += BookV1(2, "JAVA核心技术卷II", 22, "wt")
  books += BookV1(3, "C++", 30, "lisi")
  books += BookV1(4, "PHP", 18, "zhangsan")

  def allBooks = books

  def findById(id: Int) = {
    val matchBooks = books.filter(_.id==id)
    if (matchBooks.isEmpty) null else matchBooks.head
  }

  def add(book: BookV1) = {
    books += book
  }

  def remove(book:BookV1) = {
    for(i <- 0 until books.length){
      if(findById(book.id)!=null) books.remove(i)
    }
  }
}
