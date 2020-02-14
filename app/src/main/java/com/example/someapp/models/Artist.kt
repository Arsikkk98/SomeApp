import com.google.gson.annotations.SerializedName

data class Artist (

	@SerializedName("@attr") val attr : Attr,
	@SerializedName("mbid") val mbid : String,
	@SerializedName("playcount") val playCount : Int,
	@SerializedName("name") val name : String,
	@SerializedName("url") val url : String
)