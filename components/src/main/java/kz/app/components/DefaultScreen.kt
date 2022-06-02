package kz.app.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DefaultScreen(
    queue: Queue<UIComponent>,
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    toolbarText: String? = null,
    navigationAction: () -> Unit = {},
    navigationIcon: ImageVector? = Icons.Rounded.ArrowBack,
    onRemoveHeadFromQueue: () -> Unit,
    floatingActionButton: @Composable () -> Unit = {},
    fabPosition: FabPosition = FabPosition.Center,
    content: @Composable () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            toolbarText?.let {
                TopAppBar(
                    backgroundColor = Color.White
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        navigationIcon?.let {
                            Image(
                                modifier = Modifier
                                    .align(alignment = Alignment.CenterStart)
                                    .padding(start = 16.dp)
                                    .clickable { navigationAction.invoke() },
                                imageVector = navigationIcon,
                                contentDescription = "Back",
                            )
                        }
                        Text(
                            modifier = Modifier
                                .align(alignment = Alignment.Center),
                            textAlign = TextAlign.Center,
                            text = toolbarText,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W500,
                            color = Color.Black
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            floatingActionButton.invoke()
        },
        floatingActionButtonPosition = fabPosition
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            content.invoke()

            AnimatedVisibility(visible = queue.isNotEmpty()) {
                queue.peek()?.let { uiComponent ->
                    if (uiComponent is UIComponent.Dialog) {
                        GenericDialog(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            title = uiComponent.title,
                            description = uiComponent.description,
                            onRemoveHeadFromQueue = onRemoveHeadFromQueue,
                            positive = uiComponent.positive,
                            positiveAction = uiComponent.positiveAction,
                            negative = uiComponent.negative,
                            negativeAction = uiComponent.negativeAction
                        )
                    }
                }
            }
        }
    }

    if (progressBarState == ProgressBarState.Loading) {
        LoaderView()
    }
}