import com.google.gson.annotations.SerializedName

data class Attr (

	@SerializedName("user") val user : String,
	@SerializedName("from") val from : Int,
	@SerializedName("to") val to : Int,

	@SerializedName("rank") val rank : Int
)