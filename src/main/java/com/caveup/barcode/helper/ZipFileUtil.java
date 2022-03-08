package com.caveup.barcode.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author xw80329
 * @Date 2019/11/30
 */
@Slf4j
public final class ZipFileUtil {

    /**
     * Add all files from the source directory to the destination zip file
     *
     * @param source      the directory with files to add
     * @param destination the zip file that should contain the files
     * @throws IOException      if the io fails
     * @throws ArchiveException if creating or adding to the archive fails
     */
    public static void addFilesToZip(Collection<File> source, File destination) throws IOException, ArchiveException {
        try (OutputStream archiveStream = new FileOutputStream(destination);
             ArchiveOutputStream archive = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, archiveStream)) {
            for (File file : source) {
                ZipArchiveEntry entry = new ZipArchiveEntry(file.getName());
                archive.putArchiveEntry(entry);

                BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));

                IOUtils.copy(input, archive);
                input.close();
                archive.closeArchiveEntry();
            }
        }
    }

    public static void addFilesToZip(File source, File destination) throws IOException, ArchiveException {
        addFilesToZip(Arrays.asList(source), destination);
    }

    /**
     * 从zip包中读取文件
     *
     * @param source
     * @param path
     * @param destination
     */
    public static void extractFromZip(File source, String path, String destination) {
        extractFromZip(source, path, new File(destination));
    }

    /**
     * 从zip包中读取文件
     *
     * @param source
     * @param path
     * @param destinationFile
     */
    public static void extractFromZip(File source, String path, File destinationFile) {
        try (ZipFile zipFile = new ZipFile(source, ZipFile.OPEN_READ)) {
            ZipEntry zipEntry = zipFile.getEntry(path);
            if (Objects.isNull(zipEntry)) {
                log.error("No Entry was found for {}", path);
                return;
            }
            FileUtils.copyInputStreamToFile(zipFile.getInputStream(zipEntry), destinationFile);
            log.info("DestinationFile:{}, size:{}", destinationFile.getAbsolutePath(), destinationFile.length());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * 从zip包中读取文件
     *
     * @param source
     * @param pathLevel1
     * @param pathLevel2
     * @param destinationFile
     */
    public static void extractFromZipOfZip(File source, String pathLevel1, String pathLevel2, File destinationFile) {
        try (ZipFile zipFile = new ZipFile(source, ZipFile.OPEN_READ)) {
            if (!pathLevel1.endsWith(".zip")) {
                pathLevel1 = pathLevel1 + ".zip";
            }
            ZipEntry zipEntry = zipFile.getEntry(pathLevel1);
            if (Objects.isNull(zipEntry)) {
                return;
            }

            File zipTempFile = new File(pathLevel1);
            if (!zipTempFile.exists()) {
                FileUtils.copyInputStreamToFile(zipFile.getInputStream(zipEntry), zipTempFile);
            }
            extractFromZip(zipTempFile, pathLevel2, destinationFile);
            log.info("DestinationFile:{}, size:{}", destinationFile.getAbsolutePath(), destinationFile.length());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
