import com.google.gson.annotations.SerializedName

data class WeeklyArtistChart (

	@SerializedName("artist") val artists : List<Artist>,
	@SerializedName("@attr") val attr : Attr
)