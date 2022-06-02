package kz.app.ui_login.ui

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class MaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return maskFilter(text)
    }
}


fun maskFilter(text: AnnotatedString): TransformedText {

    // +7 (NNN) NNN NN NN
    val trimmed = if (text.text.length >= 10) text.text.substring(0..9) else text.text
    var out = "+7 ("
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i == 2) out += ") "
        if (i == 5) out += " "
        if (i == 7) out += " "
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset + 4
            if (offset <= 5) return offset + 6
            if (offset <= 7) return offset + 7
            if (offset <= 10) return offset + 8
            return 18
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 7) return offset - 4
            if (offset <= 11) return offset - 6
            if (offset <= 14) return offset - 7
            if (offset <= 18) return offset - 8
            return 10
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}