package dev.gbenga.inaread.data

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.gbenga.inaread.domain.date.CalendarProvider
import dev.gbenga.inaread.domain.providers.ImagePickerProvider
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ImagePickerProviderImpl @Inject constructor(
    private val context: Context,
    private val ioDispatcher : CoroutineContext,
): ImagePickerProvider {

    private val contentResolver: ContentResolver get() = context.contentResolver

    override fun uriToBytes(uri: Uri): ByteArray? {
        val inputStream = contentResolver.openInputStream(uri)
        return inputStream.use {
            uri.grantReadPermissionUri()
            inputStream?.readBytes()
        }
    }

    override suspend fun getAbsolutePathFor(uri: Uri): String {
        val completable = CompletableDeferred<String>()
        withContext(ioDispatcher){
            val imageFile = context.cacheDir
            val meterImageFile = File(imageFile, "InaRead_MeterImage.jpeg")
            if(meterImageFile.exists()){
                meterImageFile.delete()
            }
            val imageBytes = uriToBytes(uri = uri)
            val fos = FileOutputStream(meterImageFile.path)
            try{
                fos.write(imageBytes)
                completable.complete(meterImageFile.absolutePath)
            }catch (ex: Exception){
                completable.completeExceptionally(ex)
            }finally {
                fos.close()
            }
        }
        return completable.await()
    }

    private fun Uri.grantReadPermissionUri(){
        contentResolver.takePersistableUriPermission(this, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

}