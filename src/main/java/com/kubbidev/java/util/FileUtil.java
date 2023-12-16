package com.kubbidev.java.util;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class FileUtil {

    private FileUtil() {
        throw new AssertionError("No com.kubbidev.java.util.FileUtil instances for you!");
    }

    /**
     * Copies a file from the source path to the destination path.
     *
     * @param source The source file to be copied.
     * @param destination   The destination file where the source file should be copied to.
     * @throws IOException If an I/O error occurs during the file copying process.
     */
    public static void copy(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdir();
            }

            for (String file : Objects.requireNonNull(source.list())) {

                File newSource = new File(source, file);
                File newDestination = new File(destination, file);

                copy(newSource, newDestination);
            }
        } else {
            InputStream in = Files.newInputStream(source.toPath());
            OutputStream out = Files.newOutputStream(destination.toPath());

            byte[] buffer = new byte[1024];

            int bytesRead;
            // copy the file content in bytes
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            in.close();
            out.close();
        }
    }

    /**
     * Recursively deletes a directory and all its contents.
     *
     * @param file The file or directory to be deleted.
     */
    public static void delete(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null) return;
                for (File child : files) {
                    delete(child);
                }
            }

            file.delete();
        }
    }

    /**
     * Creates a file at the specified path if it does not exist.
     *
     * @param path The path of the file to be created.
     * @return The path of the created file.
     * @throws IOException If an I/O error occurs during file creation.
     */
    public static Path createFileIfNotExists(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        return path;
    }

    /**
     * Creates a directory at the specified path if it does not exist.
     *
     * @param path The path of the directory to be created.
     * @return The path of the created directory.
     * @throws IOException If an I/O error occurs during directory creation.
     */
    public static Path createDirectoryIfNotExists(Path path) throws IOException {
        if (Files.exists(path) && (Files.isDirectory(path) || Files.isSymbolicLink(path))) {
            return path;
        }

        try {
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException e) {
            // ignore
        }

        return path;
    }

    /**
     * Creates directories at the specified path if they do not exist.
     *
     * @param path The path of the directories to be created.
     * @return The path of the created directories.
     * @throws IOException If an I/O error occurs during directories creation.
     */
    public static Path createDirectoriesIfNotExists(Path path) throws IOException {
        if (Files.exists(path) && (Files.isDirectory(path) || Files.isSymbolicLink(path))) {
            return path;
        }

        try {
            Files.createDirectories(path);
        } catch (FileAlreadyExistsException e) {
            // ignore
        }

        return path;
    }

    /**
     * Recursively deletes a directory and all its contents.
     *
     * @param path The path of the directory to be deleted.
     * @throws IOException If an I/O error occurs during the directory deletion process.
     */
    public static void deleteDirectory(Path path) throws IOException {
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            return;
        }

        try (DirectoryStream<Path> contents = Files.newDirectoryStream(path)) {
            for (Path file : contents) {
                if (Files.isDirectory(file)) {
                    deleteDirectory(file);
                } else {
                    Files.delete(file);
                }
            }
        }

        Files.delete(path);
    }
}