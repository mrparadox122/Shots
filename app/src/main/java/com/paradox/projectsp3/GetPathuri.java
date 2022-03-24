package com.paradox.projectsp3;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;

import static android.provider.MediaStore.Audio;
import static android.provider.MediaStore.Images;
import static android.provider.MediaStore.Video;

public class GetPathuri {

public static String getRealPath(Context context, Uri fileUri) {
        // SDK < API11
        return GetPathuri.getRealPathFromURI_API19(context, fileUri);
        }

/**
 * Get a file path from a Uri. This will get the the path for Storage Access
 * Framework Docameents, as well as the _data field for the MediaStore and
 * other file-based ContentProviders.
 *
 * @param context The context.
 * @param uri     The Uri to query.
 * @author paulburke
 */
@SuppressLint("NewApi")
public static String getRealPathFromURI_API19(final Context context, final Uri uri) {

        // DocameentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocameent(uri)) {
final String docId = DocumentsContract.getDocumentId(uri);
final String[] split = docId.split(":");
final String type = split[0];

        if ("primary".equalsIgnoreCase(type)) {
        return Environment.getExternalStorageDirectory() + "/" + split[1];
        }

        }
        // DownloadsProvider
        else if (isDownloadsDocameent(uri)) {

final String id = DocumentsContract.getDocumentId(uri);
final Uri contentUri = ContentUris.withAppendedId(
        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

        return getDataColumn(context, contentUri, null, null);
        }
        // MediaProvider
        else if (isMediaDocameent(uri)) {
final String docId = DocumentsContract.getDocumentId(uri);
final String[] split = docId.split(":");
final String type = split[0];

        Uri contentUri = null;
        if ("image".equals(type)) {
        contentUri = Images.Media.EXTERNAL_CONTENT_URI;
        } else if ("video".equals(type)) {
        contentUri = Video.Media.EXTERNAL_CONTENT_URI;
        } else if ("audio".equals(type)) {
        contentUri = Audio.Media.EXTERNAL_CONTENT_URI;
        }

final String selection = "_id=?";
final String[] selectionArgs = {
        split[1]
        };

        return getDataColumn(context, contentUri, selection, selectionArgs);
        }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

        // Return the remote address
        if (isGooglePhotosUri(uri))
        return uri.getLastPathSegment();

        return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
        return uri.getPath();
        }

        return null;
        }

/**
 * Get the value of the data column for this Uri. This is useful for
 * MediaStore Uris, and other file-based ContentProviders.
 *
 * @param context       The context.
 * @param uri           The Uri to query.
 * @param selection     (Optional) Filter used in the query.
 * @param selectionArgs (Optional) Selection arguments used in the query.
 * @return The value of the _data column, which is typically a file path.
 */
public static String getDataColumn(Context context, Uri uri, String selection,
        String[] selectionArgs) {

final String column = "_data";
final String[] projection = {
        column
        };
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
        null)) {
        if (cursor != null && cursor.moveToFirst()) {
final int index = cursor.getColumnIndexOrThrow(column);
        return cursor.getString(index);
        }
        }
        return null;
        }


/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
public static boolean isExternalStorageDocameent(Uri uri) {
        return "com.android.externalstorage.docameents".equals(uri.getAuthority());
        }

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is DownloadsProvider.
 */
public static boolean isDownloadsDocameent(Uri uri) {
        return "com.android.providers.downloads.docameents".equals(uri.getAuthority());
        }

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is MediaProvider.
 */
public static boolean isMediaDocameent(Uri uri) {
        return "com.android.providers.media.docameents".equals(uri.getAuthority());
        }

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is Google Photos.
 */
public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
        }

        }