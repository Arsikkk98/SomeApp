import com.google.gson.annotations.SerializedName

data class JsonWeeklyArtists (

	@SerializedName("weeklyartistchart") val weeklyArtistChart : WeeklyArtistChart
)