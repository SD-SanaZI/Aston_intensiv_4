package com.example.aston_intensiv_4

import java.io.Serializable

class FragmentDataAdapter {
    companion object {
        fun getViewDataList(currentFragment: String, textString: String): ViewDataList {
            return when (currentFragment) {
                "A" -> ViewDataList(
                    "A", listOf(
                        ViewData.ButtonData("To B", "B")
                    )
                )

                "B" -> ViewDataList(
                    "B", listOf(
                        ViewData.ButtonData("Back", "back"),
                        ViewData.ButtonData("To C", "C")
                    )
                )

                "C" -> ViewDataList(
                    "C", listOf(
                        ViewData.TextData(textString),
                        ViewData.ButtonData("To D", "D"),
                        ViewData.ButtonData("To A", "A")
                    )
                )

                "D" -> ViewDataList(
                    "D", listOf(
                        ViewData.ButtonData("To B", "B")
                    )
                )

                else -> ViewDataList("", listOf())
            }
        }
    }
}

sealed class ViewData {
    data class TextData(val text: String) : Serializable, ViewData()
    data class ButtonData(
        val text: String,
        val tag: String
    ) : Serializable, ViewData()
}

data class ViewDataList(
    val tag: String,
    val list: List<ViewData>
) : Serializable