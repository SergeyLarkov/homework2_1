data class Audio (
    val id: Int = 0,
    val ownerId: Int = 0,
    val artist: String = "",
    val title: String = "",
    val duration: Int = 0,
    val url: String,
    val lyricsId: Int = 0,
    val albumId: Int = 0,
    val genreId: Int = 0,
    val date: Int = 0,
    val noSearch: Boolean = false,
    val isHq: Boolean = false
)

class AudioAttachment(
    override val type: String = "Audio",
    override val attachment: Audio): Attachment() {}