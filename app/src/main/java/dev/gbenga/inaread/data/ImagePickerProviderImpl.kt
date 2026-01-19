package dev.gbenga.inaread.data

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.compose.ui.unit.dp
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.gbenga.inaread.domain.providers.CalendarProvider
import dev.gbenga.inaread.domain.providers.ImagePickerProvider
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ImagePickerProviderImpl @Inject constructor(
    private val context: Context,
    private val ioDispatcher : CoroutineContext,
): ImagePickerProvider {

    companion object{
        val RESIZED_IMG_SIZE = 700
    }

    private val contentResolver: ContentResolver get() = context.contentResolver

    fun InputStream.resizeBitmap(uri: Uri){
        val bitmap = BitmapFactory.decodeStream(this)

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeStream(this, null, options)
        options.inSampleSize = calculateInSampleSize(options, RESIZED_IMG_SIZE, RESIZED_IMG_SIZE)
        options.inJustDecodeBounds = false

        val scaledInputStream = contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(scaledInputStream, null, options)

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream)
    }

    override fun uriToBytes(uri: Uri): ByteArray? {
        val inputStream = contentResolver.openInputStream(uri)
//        val bitmap = BitmapFactory.decodeStream(inputStream)
//        val outputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream)
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

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun Uri.grantReadPermissionUri(){
        contentResolver.takePersistableUriPermission(this, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

}