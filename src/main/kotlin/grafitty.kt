data class Grafitty (
    val id: Int = 0,
    val ownerId: Int = 0,
    val url: String,
    val width: Int = 0,
    val height: Int = 0
)

class GrafittyAttachment(
    override val type: String = "Grafitty",
    override val attachment: Grafitty): Attachment() {}