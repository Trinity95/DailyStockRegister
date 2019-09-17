
package application.shubhamgas.repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class FileRepository {
    String name;

    public FileRepository(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRootPath() {
        try {
            File repositoryRootDirectory = new File(System.getProperty("user.dir") + "/Resources" + SharedConstants.PATH_DELIMITER + getName());
            return repositoryRootDirectory.getCanonicalPath();
        } catch (Exception e) {
            throw new RuntimeException("Unable to determine file repository root path", e);
        }
    }

    public synchronized void CreateBinaryFile(String path, byte[] data, Boolean overwrite) throws Exception {

        if (!overwrite) {
            checkFileAlreadyExists(getChild(path));
        }
        if (data == null) {
            throw new Exception("No data provided");
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(getChild(path));
            fos.write(data);
        } catch (Exception ex) {
            throw new Exception("Unable to Create File [" + path + "]");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception eClose) {
                    // Intentionally consume this exception
                }
            }
        }

    }

    private void checkFileAlreadyExists(String path) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            throw new Exception("File Already Exists");
        }
    }

    protected static byte[] readStreamToByteArray(InputStream stream) throws Exception {
        byte[] buffer = new byte[1024 * 64];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int bytesRead;
        while ((bytesRead = stream.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        stream.close();
        byte[] data = bos.toByteArray();
        bos.flush();
        bos.close();
        return data;
    }

    public byte[] LoadBinary(String path) throws Exception {
        File file = new File(getChild(path));
        checkFileExists(file);
        checkFileIsNotADirectory(file);
        FileInputStream fis = new FileInputStream(file);
        byte[] result = readStreamToByteArray(fis);
        fis.close();
        return result;
    }

    protected void checkFileExists(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("File Does Not Exist");
        }
    }

    protected void checkFileIsNotADirectory(File file) throws Exception {
        if (file.isDirectory()) {
            throw new Exception("File Path is a directory");
        }
    }

    private String getChild(String path) {
        return (getRootPath() + SharedConstants.PATH_DELIMITER + path);
    }

    public String generateFileChecksum(String path, String algorithm) throws Exception {
        InputStream fis = null;
        String result = "";
        File file = new File(getChild(path));
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8096];
            MessageDigest complete = MessageDigest.getInstance(algorithm);
            int numRead = 0;
            while (numRead != -1) {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            }
            byte[] b = complete.digest();
            // Generate hex string of SHA
            for (int i = 0; i < b.length; i++) {
                result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
            }
        } catch (Exception e) {
            throw new Exception("Could not generate checksum for file " + file.getAbsolutePath(), e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception eClose) {
                    // Intentionally consume this exception
                }
            }
        }
        return result;
    }

    public File getFile(String path) {
        File file = new File(getChild(path));
        try {
            checkFileExists(file);
        } catch (Exception e) {
            return null;
        }
        return file;
    }

    public File[] getRepoContents(String folderName) {
        File repository = new File(getChild(folderName));
        return repository.listFiles();
    }

    public boolean createDirectory(String dirName) {
        File file = new File(getChild(dirName));
        return file.mkdirs();
    }

}
