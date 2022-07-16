data class Present (
    val id: Int = 0,
    val thumb_256: String = "",
    val thumb_96: String = "",
    val thumb_48: String = "",
)

class PresentAttachment(
    override val type: String = "Present",
    override val attachment: Present): Attachment() {}
