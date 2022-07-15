import kotlin.jvm.internal.Intrinsics

data class Comments (
    val count: Int = 0,
    val canPost: Boolean = false,
    val groupsCanPost: Int = 0,
    val canClose: Boolean = false,
    val canOpen: Boolean = false
)

data class Copyright (
    val id: Int = 0,
    val link: String = "",
    val name: String = "",
    val type: String = ""
)

data class Likes (
    val count: Int = 0,
    val userLikes: Boolean = true,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)

data class Reposts (
    val count: Int = 0,
    val userPeposted: Boolean = false
)

data class Views (val count: Int = 0)

data class Donut(
    val isDonut: Boolean = false,
    val paidDuration: Int = 0,
    val canPublishFreeCopy:Boolean = true,
    val editMode:String = "all"
)

data class Post (
    val id: Int = 0,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int = 0,
    val date: Int = 0,
    val text: String,
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    val friendsOnly: Int = 0,
    val postType: String = "",
    val signerId: Int = 0,
    val canPin: Int = 0,
    val canDelete: Int = 0,
    val canEdit: Int = 0,
    val isPinned: Int = 0,
    val markedAsAds: Int = 0,
    val isFavorite: Boolean = false,
    val postponedId: Int = 0) {

    val comments: Comments = Comments()
    val copyright: Copyright = Copyright()
    val likes: Likes = Likes()
    val reposts: Reposts = Reposts()
    val views: Views = Views()
    val donut: Donut = Donut()
}

object WallService {
    private var posts = emptyArray<Post>()

    fun add(post: Post): Post {
        val postId = if (posts.isEmpty()) 1 else posts.last().id + 1
        posts += post.copy(id = postId)
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index,p) in posts.withIndex()) {
            if (p.id == post.id) {
                posts[index] = p.copy(
                    createdBy = post.createdBy,
                    text = post.text,
                    replyOwnerId = post.replyOwnerId,
                    replyPostId = post.replyPostId,
                    friendsOnly= post.friendsOnly,
                    postType = post.postType,
                    signerId = post.signerId,
                    canPin = post.canPin,
                    canDelete = post.canDelete,
                    canEdit = post.canEdit,
                    isPinned = post.isPinned,
                    markedAsAds = post.markedAsAds,
                    isFavorite = post.isFavorite,
                    postponedId = post.postponedId
                )
                return true
            }
        }
        return false
    }
}

fun main() {

    val wall = WallService

    val post = Post(
        ownerId = 1,
        fromId = 1,
        text = "Hello!",
    )

    val newPost = wall.add(post).copy(text = "Good day!")

    if (wall.update(newPost)) {
        println("Post updated")
    } else {
        println("Post not found!")
    }

}