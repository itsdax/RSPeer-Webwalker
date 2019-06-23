package com.dax.api.file_cache;

import com.dax.api.file_cache.data.Blob;
import com.dax.api.file_cache.data.CacheContainer;
import com.dax.api.io.FileManager;
import com.google.gson.Gson;
import org.rspeer.ui.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

/**
 * Synchronization is not handled. Use at your own risk.
 * Scales poorly.
 */
public class WebGrabber {

    private static final String CACHE_FILE = "cache.json";

    public static String getString(String url) {
        try {
            return new String(Files.readAllBytes(Paths.get(WebGrabber.grab(url).getPath())));
        } catch (IOException e) {
            return null;
        }

    }

    public static BufferedImage getImage(String url) {
        try {
            return ImageIO.read(grab(url));
        } catch (IOException | IllegalStateException e) {
            return null;
        }
    }

    public static File grab(String url) {
        CacheContainer cacheContainer = cacheContainer();

        Blob blob = getCachedBlob(url, cacheContainer);
        if (blob != null) return blob.getFile();

        blob = new Blob(url);

        if (!saveFile(blob)) {
            throw new IllegalStateException("Failed to retrieve from " + url);
        }

        cacheContainer.getList().add(blob);
        saveDataCache(cacheContainer);
        return blob.getFile();
    }

    private static Blob getCachedBlob(String url, CacheContainer cacheContainer) {
        Blob cachedBlobItem = cacheContainer.getList().stream().filter(cachedBlob -> cachedBlob.getUrl().equals(url) && !cachedBlob.isExpired()).findAny().orElse(null);
        if (cachedBlobItem != null) {
            return cachedBlobItem;
        }
        return null;
    }

    private static boolean saveFile(Blob blob) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(FileManager.getWorkingDir(blob.getFileName(), "cache"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        fileOutputStream.getChannel();
        ReadableByteChannel readableByteChannel = null;
        try {
            readableByteChannel = Channels.newChannel(new URL(blob.getUrl()).openStream());
        } catch (IOException e) {
            e.printStackTrace();
            Log.log(Level.SEVERE, "Cache", "Failed to read file from " + blob.getUrl());
            return false;
        }

        try {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
            Log.log(Level.SEVERE, "Cache", "Failed to save file " + blob.getFileName());
            return false;
        }

        return true;
    }

    private static void saveDataCache(CacheContainer cacheContainer) {
        cacheContainer.getList().removeIf(Blob::isExpired);
        FileManager.writeToFile(CACHE_FILE, new Gson().toJson(cacheContainer));
    }

    private static CacheContainer cacheContainer() {
        if (!initializeCache()) {
            throw new IllegalStateException("Failed to initialize file cache.");
        }
        return new Gson().fromJson(FileManager.open(CACHE_FILE), CacheContainer.class);
    }

    private static boolean initializeCache() {
        File cache = new File(FileManager.getWorkingDir(CACHE_FILE));
        try {
            cache.getParentFile().mkdirs();
            if (cache.createNewFile()) FileManager.writeToFile(cache, new Gson().toJson(new CacheContainer()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.log(Level.SEVERE, "Cache", "Error loading file cache.");
            return false;
        }
    }

}
