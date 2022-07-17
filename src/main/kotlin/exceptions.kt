class PostNotFoundException(override val message: String) : RuntimeException()

class CommentNotFoundException(override val message: String) : RuntimeException()

class BadReportIdException(override val message: String) : RuntimeException()
