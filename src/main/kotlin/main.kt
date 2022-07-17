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
    val userLikes: Boolean = false,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)

data class Reposts (
    val count: Int = 0,
    val userReposted: Boolean = false
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
    val comments: Comments? = null,
    val copyright: Copyright? = null,
    val postType: String = "",
    val signerId: Int = 0,
    val canPin: Int = 0,
    val canDelete: Int = 0,
    val canEdit: Int = 0,
    val isPinned: Int = 0,
    val markedAsAds: Int = 0,
    val isFavorite: Boolean = false,
    val donut: Donut? = null,
    val postponedId: Int = 0) {

    val likes: Likes = Likes()
    val reposts: Reposts = Reposts()
    val views: Views = Views()
    var attachments = emptyArray<Attachment>()
}

data class Comment (
    val id: Int = 0,
    val fromId: Int = 0,
    val date: Int = 0,
    val text: String = "",
    val postId: Int = 0,
    val replyToUser: Int = 0,
    val replyToComment: Int = 0,
    val replyToUid: Int = 0,
    val replyToCid: Int = 0) {
    var attachments = emptyArray<Attachment>()
}

data class reportComment(
    val ownerId:Int,
    val commentId:Int,
    val reason: Int
)

object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reportComments = emptyArray<reportComment>()

    fun add(post: Post): Post {
        val postId = if (posts.isEmpty()) 1 else posts.last().id + 1
        posts += post.copy(id = postId)
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, p) in posts.withIndex()) {
            if (p.id == post.id) {
                posts[index] = p.copy(
                    createdBy = post.createdBy,
                    text = post.text,
                    replyOwnerId = post.replyOwnerId,
                    replyPostId = post.replyPostId,
                    friendsOnly = post.friendsOnly,
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

    fun findPostById(id: Int): Post? {
        for (post in posts) {
            if (post.id == id) {
                return post
            }
        }
        return null
    }

    fun findCommentById(id: Int): Comment? {
        for (comment in comments) {
            if (comment.id == id) {
                return comment
            }
        }
        return null
    }

    fun createComment(postId: Int, comment: Comment): Comment {
       val post = findPostById(postId)?: throw PostNotFoundException("Post with id $postId not found")
       val commentId = if (comments.isEmpty()) 1 else comments.last().id + 1
       comments += comment.copy(id = commentId, postId = post.id)
       return comments.last();
    }

    fun createReportComment(commentId: Int, reportComment: reportComment): reportComment {
        val comment = findCommentById(commentId) ?: throw CommentNotFoundException("Post with id $commentId not found")
        if (reportComment.reason in 0..8) {
            reportComments += reportComment.copy(commentId = comment.id)
            return reportComments.last();
        } else {
            throw BadReportIdException(reportComment.reason.toString() + " is bad reason")
        }
    }
}

fun main() {

    val wall = WallService

    val post = Post(
        ownerId = 1,
        fromId = 1,
        text = "Hello!",
    )
    wall.add(post);

    wall.add(post.copy(text = "Good day!"))

    val newPost: Post = post.copy(id = 1, text = "Good morning!")

    if (wall.update(newPost)) {
        println("Post updated")
    } else {
        println("Post not updated!")
    }

    val attachmentPost: Post? = wall.findPostById(1)

    if (attachmentPost != null) {
        attachmentPost.attachments += FileAttachment(attachment = File(id = 1, ext = ".jpg", url = "www.yandex.ru/file.jpg", type = 4))
    }

    val comment = wall.createComment(2, Comment(text = "New Comment!"))
    println("Add new comment to post with id " + comment.postId)

    println("Add new report to comment with id " +
            wall.createReportComment(comment.id, reportComment(1, comment.id , 0)).commentId)

    // заведомо ложные данные для проверки throw
    //println("Add new comment to post with id "+wall.createComment(999, Comment(text = "Try to add Comment!")).postId)
    //wall.createReportComment(999,reportComment(1, comment.id , 0))
    //wall.createReportComment(1,reportComment(1, comment.id , 10))

}