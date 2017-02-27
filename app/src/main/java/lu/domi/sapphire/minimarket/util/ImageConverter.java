package lu.domi.sapphire.minimarket.util;


public class ImageConverter {

//    private final static String IMAGE_CONVERTER_TAG = ImageConverter.class.getSimpleName();
//    private static int thumbnailHeight = 0;
//    private static int thumbnailWidth = 0;
//    private static DisplayMetrics metrics;
//    public static final String DEFAULT_IMG_PATH = "/products";
//
//    private ImageConverter() {}
//
//    public static Bitmap getThumbnailOf(String originImgPath, Context context) {
//        Bitmap img = ImageConverter.getImage(originImgPath);
//        if (thumbnailHeight == 0) {
//            double dpi = getDisplayMetrics(context).densityDpi;
//            double factor = dpi / DisplayMetrics.DENSITY_DEFAULT;
//            thumbnailHeight = (int) Math.round(52 * factor);
//            double imgWidth = img.getWidth();
//            double lengthWidthFactor = Math.round(imgWidth / img.getHeight());
//            thumbnailWidth = (int) Math.round(52 * factor * lengthWidthFactor);
//        }
//        return ThumbnailUtils.extractThumbnail(img, thumbnailWidth, thumbnailHeight);
//    }
//
//    public static Bitmap getThumbnailOf(Bitmap img, Context context) {
//        if (thumbnailHeight == 0) {
//            double dpi = getDisplayMetrics(context).densityDpi;
//            double factor = dpi / DisplayMetrics.DENSITY_DEFAULT;
//            thumbnailHeight = (int) Math.round(52 * factor);
//            double imgWidth = img.getWidth();
//            double lengthWidthFactor = Math.round(imgWidth / img.getHeight());
//            thumbnailWidth = (int) Math.round(52 * factor * lengthWidthFactor);
//        }
//        return ThumbnailUtils.extractThumbnail(img, thumbnailWidth, thumbnailHeight);
//    }
//
//    public static Bitmap getImage(String imgPath) {
//        return BitmapFactory.decodeFile(imgPath);
//    }
//
//    public static Bitmap getDefaultImage(Context context) {
//        String defaultImagePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + DEFAULT_IMG_PATH;
//        File defaultImg = new File(defaultImagePath + DEFAULT_IMG);
//        Bitmap headerImage;
//        if (!defaultImg.exists()) {
//            headerImage = ImageConverter.cropDefaultHeaderImage(context, R.drawable.default_group_img);
//            ImageConverter.saveDefaultImage(headerImage, defaultImagePath, DEFAULT_IMG);
//        } else {
//            headerImage = ImageConverter.getImage(defaultImagePath + DEFAULT_IMG);
//        }
//        return headerImage;
//    }
//
//    public static Bitmap getMainImage(Context context) {
//        String mainImagePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + DEFAULT_IMG_PATH;
//        File mainImg = new File(mainImagePath + MAIN_IMG);
//        Bitmap headerImage;
//        if (!mainImg.exists()) {
//            headerImage = ImageConverter.cropDefaultHeaderImage(context, R.drawable.default_main);
//            ImageConverter.saveDefaultImage(headerImage, mainImagePath, MAIN_IMG);
//        } else {
//            headerImage = ImageConverter.getImage(mainImagePath + MAIN_IMG);
//        }
//        return headerImage;
//    }
//
//    public static Bitmap cropDefaultHeaderImage(Context context, int drawableId) {
//        int targetW = ImageConverter.getDisplayMetrics(context).widthPixels;
//        int targetH = Math.round(context.getResources().getDimension(R.dimen.header_size));
//
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(context.getResources(), drawableId, bmOptions);
//
//        int imgW = bmOptions.outHeight;
//        int imgH = bmOptions.outWidth;
//
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = ImageConverter.sampleSize(imgW, imgH, targetW, targetH);
//
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId, bmOptions);
//        bitmap = ImageConverter.scaleBitmap(bitmap, targetW, targetH);
//        return ImageConverter.cropBitmap(bitmap, targetW, targetH);
//    }
//
//    public static Bitmap cropHeaderImage(Context context, String imgPath) {
//        int targetW = ImageConverter.getDisplayMetrics(context).widthPixels;
//        int targetH = Math.round(context.getResources().getDimension(R.dimen.header_size));
//
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(imgPath, bmOptions);
//
//        int orientation = ImageConverter.getOrientation(imgPath);
//
//        int imgW;
//        int imgH;
//
//        if (orientation == ExifInterface.ORIENTATION_ROTATE_90 ||
//                orientation == ExifInterface.ORIENTATION_ROTATE_270) {
//            imgW = bmOptions.outHeight;
//            imgH = bmOptions.outWidth;
//        } else {
//            imgW = bmOptions.outWidth;
//            imgH = bmOptions.outHeight;
//        }
//
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = ImageConverter.sampleSize(imgW, imgH, targetW, targetH);
//
//        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, bmOptions);
//        if (bitmap == null) {
//            return null;
//        }
//        bitmap = ImageConverter.rotateImage(bitmap, orientation);
//        bitmap = ImageConverter.scaleBitmap(bitmap, targetW, targetH);
//        return ImageConverter.cropBitmap(bitmap, targetW, targetH);
//    }
//
//    private static int getOrientation(String imgPath) {
//        int orientation = ExifInterface.ORIENTATION_UNDEFINED;
//        try {
//            ExifInterface exif = new ExifInterface(imgPath);
//            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//        } catch (IOException e) {
//            Log.w(IMAGE_CONVERTER_TAG, "Could not determine image orientation. " + e.getMessage());
//        }
//        return orientation;
//    }
//
//    private static int sampleSize(int imgW, int imgH, int targetW, int targetH) {
//        int inSampleSize = 1;
//
//        if (imgH > targetH || imgW > targetW) {
//            final int halfHeight = imgH / 2;
//            final int halfWidth = imgW / 2;
//
//            while ((halfHeight / inSampleSize) >= targetH
//                    && (halfWidth / inSampleSize) >= targetW) {
//                inSampleSize *= 2;
//            }
//        }
//        return inSampleSize;
//    }
//
//    private static Bitmap scaleBitmap(Bitmap bitmap, int targetW, int targetH) {
//        double factorW = (bitmap.getWidth() * 1.0) / targetW;
//        double factorH = (bitmap.getHeight() * 1.0) / targetH;
//        double scaleFactor = Math.min(factorW, factorH);
//
//        int scaledWidth = (int) Math.round(bitmap.getWidth() / scaleFactor);
//        int scaledHeight = (int) Math.round(bitmap.getHeight() / scaleFactor);
//
//        return Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
//    }
//
//    private static Bitmap cropBitmap(Bitmap bitmap, int targetW, int targetH) {
//        int cropX = 0;
//        int cropY = 0;
//
//        if (bitmap.getWidth() > targetW) {
//            cropX = (int) Math.round(bitmap.getWidth() / 2.0 - targetW / 2.0);
//        } else {
//            cropY = (int) Math.round(bitmap.getHeight() / 2.0 - targetH / 2.0);
//        }
//
//        return Bitmap.createBitmap(bitmap, cropX, cropY, targetW, targetH);
//    }
//
//    private static Bitmap rotateImage(Bitmap bitmap, int orientation) {
//        switch(orientation) {
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                return ImageConverter.rotate(bitmap, 90);
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                return ImageConverter.rotate(bitmap, 180);
//            case ExifInterface.ORIENTATION_ROTATE_270:
//                return ImageConverter.rotate(bitmap, 270);
//            default:
//                return bitmap;
//        }
//    }
//
//
//
//    private static Bitmap rotate(Bitmap source, float angle) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(angle);
//        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
//    }
//
//    public static void saveImageBitmap(Bitmap image, String path) {
//        File imgFile = new File(path);
//        try {
//            FileOutputStream fos = new FileOutputStream(imgFile);
//            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.close();
//        } catch (FileNotFoundException e) {
//            Log.d(IMAGE_CONVERTER_TAG, "File not found: " + e.getMessage());
//        } catch (IOException e) {
//            Log.d(IMAGE_CONVERTER_TAG, "Error accessing file: " + e.getMessage());
//        }
//    }
//
//    private static void saveDefaultImage(Bitmap image, String imageDir, String imgName) {
//        File folder = new File(imageDir);
//        boolean success = folder.exists();
//        if (!success) {
//            success = folder.mkdirs();
//        }
//        if (success) {
//            ImageConverter.saveImageBitmap(image, imageDir + imgName);
//        }
//    }
//
//    public static void deleteImage(String oldImagePath) {
//        // do not delete default picture
//        if (!oldImagePath.cartContains(ImageConverter.DEFAULT_IMG)) {
//            File file = new File(oldImagePath);
//            boolean deleted = file.delete();
//            if (!deleted) {
//                Log.w(IMAGE_CONVERTER_TAG, "WARNING: Dead data. Image not deleted!");
//            }
//        }
//    }
//
//    public static String getRealPathFromDocumentUri(final Context context, final Uri uri) {
//        // DocumentProvider
//        if (DocumentsContract.isDocumentUri(context, uri)) {
//            // ExternalStorageProvider
//            if (isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                if ("primary".equalsIgnoreCase(type)) {
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }
//                // INFO handle non-primary volumes
//            }
//            // DownloadsProvider
//            else if (isDownloadsDocument(uri)) {
//
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//
//                return getDataColumn(context, contentUri, null, null);
//            }
//            // MediaProvider
//            else if (isMediaDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                Uri contentUri = null;
//                if ("image".equals(type)) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//
//                final String selection = "_id=?";
//                final String[] selectionArgs = new String[] {
//                        split[1]
//                };
//
//                return getDataColumn(context, contentUri, selection, selectionArgs);
//            }
//        }
//        // MediaStore (and general)
//        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            return getDataColumn(context, uri, null, null);
//        }
//        // File
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;
//    }
//
//    public static String getDataColumn(Context context, Uri uri, String selection,
//                                       String[] selectionArgs) {
//
//        Cursor cursor = null;
//        final String column = "_data";
//        final String[] projection = {
//                column
//        };
//
//        try {
//            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
//                    null);
//            if (cursor != null && cursor.moveToFirst()) {
//                final int column_index = cursor.getColumnIndexOrThrow(column);
//                return cursor.getString(column_index);
//            }
//        } finally {
//            if (cursor != null)
//                cursor.close();
//        }
//        return null;
//    }
//
//    private static DisplayMetrics getDisplayMetrics(Context context) {
//        if (metrics == null) {
//            metrics = context.getResources().getDisplayMetrics();
//        }
//        return metrics;
//    }
//
//    public static boolean isExternalStorageDocument(Uri uri) {
//        return "com.android.externalstorage.documents".equals(uri.getAuthority());
//    }
//
//    public static boolean isDownloadsDocument(Uri uri) {
//        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
//    }
//
//    public static boolean isMediaDocument(Uri uri) {
//        return "com.android.providers.media.documents".equals(uri.getAuthority());
//    }
}
