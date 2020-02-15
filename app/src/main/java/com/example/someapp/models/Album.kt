import com.google.gson.annotations.SerializedName

data class Album (

	@SerializedName("name") val name : String,
	@SerializedName("playcount") val playCount : Int,
	@SerializedName("mbid") val mbid : String,
	@SerializedName("url") val url : String,
	@SerializedName("artist") val artist : Artist,
	@SerializedName("image") val images : List<Image>
)