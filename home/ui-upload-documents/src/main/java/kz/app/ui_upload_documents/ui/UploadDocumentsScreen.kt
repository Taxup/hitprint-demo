package kz.app.ui_upload_documents.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import kz.app.components.DefaultScreen
import kz.app.hitprint_domain.Document
import kz.app.ui_upload_documents.R
import kz.app.ui_upload_documents.extension.getFileName
import kz.app.ui_upload_documents.extension.getFileSize
import kz.app.ui_upload_documents.ui.components.DocumentItem
import java.io.InputStream
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun UploadDocumentsScreen(
    state: UploadDocumentsState,
    events: (UploadDocumentsEvent) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            val files = uris.map {
                val inputStream: InputStream = context.contentResolver.openInputStream(it)
                    ?: return@rememberLauncherForActivityResult
                Document(
                    filename = context.getFileName(it),
                    size = context.getFileSize(it),
                    data = inputStream,
                )
            }
            events.invoke(UploadDocumentsEvent.AddDocumentsUri(files))
        }
    )
    DefaultScreen(
        progressBarState = state.progressBarState,
        queue = state.queue,
        toolbarText = stringResource(id = R.string.upload_docs),
        navigationAction = {
            events.invoke(UploadDocumentsEvent.OnBackPressed)
        },
        onRemoveHeadFromQueue = {
            events.invoke(UploadDocumentsEvent.OnRemoveHeadFromQueue)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.fillMaxWidth(.7f),
                backgroundColor = Color(0xFF199450),
                contentColor = Color.White,
                text = {
                    Text(text = stringResource(id = R.string.shared_next).uppercase(Locale.getDefault()))
                },
                onClick = {
                    events.invoke(UploadDocumentsEvent.OnNextClicked)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                modifier = Modifier.padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF19924F),
                    contentColor = Color.White
                ),
                onClick = {
                    launcher.launch(arrayOf("*/*"))
                }) {
                Image(
                    painterResource(id = R.drawable.ic_round_cloud_download_24),
                    contentDescription = "Download button icon",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(id = R.string.upload_files),
                    fontStyle = FontStyle.Italic
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            state.files.forEachIndexed { index, document ->
                DocumentItem(
                    document = document,
                    onComment = {
                        events.invoke(UploadDocumentsEvent.OnCommentDocument(document.id, it))
                    }
                )
            }
        }
    }
}
