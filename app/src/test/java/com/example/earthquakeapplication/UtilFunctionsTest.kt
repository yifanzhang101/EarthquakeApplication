package com.example.earthquakeapplication
import android.graphics.Color
import com.example.earthquakeapplication.util.convertTimeToReadableFormat
import com.example.earthquakeapplication.util.getColorCode
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilFunctionsTest {

    @Test
    fun `convertTimeToReadableFormat should format timestamp correctly`() {
        // Arrange
        val timestamp = 1643990400000 // Replace with an actual timestamp
        val expectedFormat = "2022-02-05 12:00:00" // Replace with the expected format

        // Act
        val result = convertTimeToReadableFormat(timestamp)

        // Assert
        assertEquals(expectedFormat, result)
    }

    @Test
    fun `getColorCode should return correct color code for known color names`() {
        val colorNames = listOf("black", "white", "red", "green", "blue", "yellow", "purple", "brown", "pink", "gray")
        val expectedColorCodes = listOf(
            Color.parseColor("#000000"), Color.parseColor("#FFFFFF"), Color.parseColor("#FF0000"),
            Color.parseColor("#00FF00"), Color.parseColor("#0000FF"), Color.parseColor("#FFFF00"),
            Color.parseColor("#800080"), Color.parseColor("#A52A2A"), Color.parseColor("#FFC0CB"),
            Color.parseColor("#808080")
        )

        colorNames.forEachIndexed { index, colorName ->
            assertEquals(expectedColorCodes[index], getColorCode(colorName))
        }
    }

    @Test
    fun `getColorCode should return default color for unknown color names`() {
        val unknownColorName = "unknownColorName"
        val defaultColorCode = Color.parseColor("#FFFFFF")

        assertEquals(defaultColorCode, getColorCode(unknownColorName))
    }
}