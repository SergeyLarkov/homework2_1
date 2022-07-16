data class File (
        val id: Int = 0,
        val ownerId: Int = 0,
        val title: String = "",
        val size: Int = 0,
        val ext: String = "",
        val url: String,
        val type: Int
)

class FileAttachment(
        override val type: String = "File",
        override val attachment: File): Attachment() {}
