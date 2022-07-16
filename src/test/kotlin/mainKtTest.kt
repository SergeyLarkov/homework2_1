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
    fun test_getPostById() {
        // создаём целевой сервис
        val wall = WallService

        // заполняем несколькими постами
        wall.add(Post(ownerId = 0, fromId = 0, text = "test1"))
        wall.add(Post(ownerId = 0, fromId = 0, text = "test2"))

        assertTrue(wall.getPostById(2) != null)
    }
}