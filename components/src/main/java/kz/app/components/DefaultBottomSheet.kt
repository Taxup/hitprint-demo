package kz.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun DefaultBottomSheet(
    sheetState: ModalBottomSheetState,
    sheetShape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = sheetShape,
        sheetContent = {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                modifier = Modifier
                    .size(60.dp, 6.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(3.dp)),
                painter = ColorPainter(Color(0xFFBDBDBD)),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            sheetContent.invoke()
        }
    ) {
        content.invoke()
    }
}