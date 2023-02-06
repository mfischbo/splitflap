package net.fischboeck.splitflap.provider.weather

object WMODecoder {

    // TODO: Localization :)
    fun decode(wmoCode: Int): String {
        when (wmoCode) {
            0 -> return "CLEAR SKY"
            1 -> return "MAINLY CLEAR"
            2 -> return "PART. CLOUDY"
            3 -> return "OVERCAST"
            45,48 -> return "FOG"
            51,52,53 -> return "DRIZZLE"
            56,57 -> return "FREEZ. DRIZZLE"
            61 -> return "SLIGHT RAIN"
            62 -> return "MOD. RAIN"
            63 -> return "HEAVY RAIN"
            66,67 -> return "FREEZ. RAIN"
            71 -> return "SLIGHT SNOW"
            73 -> return "MOD. SNOW"
            75 -> return "HEAVY SNOW"
            77 -> return "SNOW GRAINS"
            80 -> return "SLIGHT RAIN"
            81 -> return "MOD. RAIN"
            82 -> return "VIOLENT RAIN"
            85,86 -> return "SNOW SHOWER"
            95,96,99 -> return "THUNDERSTORM"
        }
        return ""
    }
}