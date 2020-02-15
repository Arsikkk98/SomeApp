import com.google.gson.annotations.SerializedName

data class TopAlbums (

	@SerializedName("album") val albums : List<Album>,
	@SerializedName("@attr") val attr : Attr
)