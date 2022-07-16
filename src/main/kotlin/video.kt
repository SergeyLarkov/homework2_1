data class Video (
    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val description: String = "",
    val duration: Int = 0,
    val date: Int = 0,
    val views: Int = 0,
    val comments: Int = 0,
    val player: String ="",
    val platform: String ="",
    val canAdd: Boolean = true,
    val isPrivate: Boolean = false
)

class VideoAttachment(
    override val type: String = "Video",
    override val attachment: Video): Attachment() {}