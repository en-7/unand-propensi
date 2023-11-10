package protensi.sita.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class ByteArrayMultipartFile implements MultipartFile {
    private byte[] bytes;
    private String name;
    private String originalFilename;
    private String contentType;

    public ByteArrayMultipartFile(byte[] bytes) {
        this.bytes = bytes;
        this.name = "byteArrayFile";
        this.originalFilename = "byteArrayFile";
        this.contentType = "application/octet-stream";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return bytes == null || bytes.length == 0;
    }

    @Override
    public long getSize() {
        return (long) (bytes != null ? bytes.length : 0);
    }

    @Override
    public byte[] getBytes() throws IOException {
        return bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        if (dest.exists() && !dest.delete()) {
            throw new IOException("Failed to delete the existing file: " + dest.getName());
        }

        if (!dest.createNewFile()) {
            throw new IOException("Failed to create the new file: " + dest.getName());
        }

        try (OutputStream output = new FileOutputStream(dest)) {
            output.write(bytes);
        }
    }
}
