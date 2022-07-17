import org.junit.Test

import org.junit.Assert.*

class mainKtTest {

    @Test
    fun test_add() {
        val wall = WallService

        val post = Post(
            ownerId = 0,
            fromId = 0,
            text = "test!",
        )
        assertTrue(wall.add(post).id != 0)
    }

    @Test
    fun test_update() {
        // создаём целевой сервис
        val wall = WallService

        // заполняем несколькими постами
        wall.add(Post(ownerId = 0, fromId = 0, text = "test1"))
        val update = wall.add(Post(ownerId = 0, fromId = 0, text = "test2"))
        wall.add(Post(ownerId = 0, fromId = 0, text = "test3"))

        assertTrue(wall.update(update))
    }

    @Test
    fun test_not_update() {
        // создаём целевой сервис
        val wall = WallService
        // заполняем несколькими постами
        wall.add(Post(ownerId = 0, fromId = 0, text = "test1"))
        wall.add(Post(ownerId = 0, fromId = 0, text = "test2"))
        val post = Post(ownerId = 0, fromId = 0, text = "test3")
        wall.add(post)

        assertFalse(wall.update(post))
    }

    @Test
    fun test_fintPostById() {
        // создаём целевой сервис
        val wall = WallService

        // заполняем несколькими постами
        wall.add(Post(ownerId = 0, fromId = 0, text = "test1"))
        wall.add(Post(ownerId = 0, fromId = 0, text = "test2"))

        assertTrue(wall.findPostById(2) != null)
    }

    @Test
    fun test_findCommentById() {
        // создаём целевой сервис
        val wall = WallService

        // заполняем несколькими постами
        val postId = wall.add(Post(ownerId = 0, fromId = 0, text = "test1")).id
        val commentid = wall.createComment(postId, Comment(text = "test")).id

        assertTrue(wall.findPostById(commentid) != null)
    }

    @Test
    fun test_createComment() {
        // создаём целевой сервис
        val wall = WallService
        // заполняем несколькими постами
        val postId = wall.add(Post(ownerId = 0, fromId = 0, text = "test1")).id

        assertTrue(wall.createComment(postId, Comment(text = "Test")) != null)
    }

    @Test(expected = PostNotFoundException::class)
    fun test_createComment_shouldThrow() {
        // создаём целевой сервис
        val wall = WallService
        // вызов с Exception
        wall.createComment(999, Comment(text = "Test"))
    }

    @Test
    fun test_createReportComment() {
        // создаём целевой сервис
        val wall = WallService
        // заполняем несколькими постами
        val postId = wall.add(Post(ownerId = 0, fromId = 0, text = "test1")).id
        val commentId = wall.createComment(postId, Comment(text = "test")).id

        assertTrue(wall.createReportComment(commentId, reportComment(1, commentId, 0)) != null)
    }

    @Test(expected = CommentNotFoundException::class)
    fun test_createComment_shouldThrow_CommentNotFoundException() {
        // здесь код с вызовом функции, которая должна выкинуть PostNotFoundException
        val wall = WallService
        // вызов с Exception
        wall.createReportComment(999, reportComment(1, 1, 0))
    }

    @Test(expected = BadReportIdException::class)
    fun test_createComment_shouldThrow_BadReportIdException() {
        // здесь код с вызовом функции, которая должна выкинуть PostNotFoundException
        val wall = WallService
        // заполняем несколькими постами
        val postId = wall.add(Post(ownerId = 0, fromId = 0, text = "test1")).id
        val commentId = wall.createComment(postId, Comment(text = "test")).id
        // вызов с Exception
        wall.createReportComment(commentId, reportComment(1, commentId, 10))
    }

}