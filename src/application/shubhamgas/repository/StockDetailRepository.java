
package application.shubhamgas.repository;

import java.io.File;

public class StockDetailRepository {

    FileRepository stockDetailRepo;

    public StockDetailRepository(String repoName) {
        stockDetailRepo = new FileRepository(repoName);
    }

    public byte[] readFile(String filePath) throws Exception {
        try {
            return stockDetailRepo.LoadBinary(filePath);
        } catch (Exception e) {
            throw new Exception("Could not read File");
        }
    }

    public void writeFile(String path, byte[] data, boolean override) throws Exception {
        try {
            stockDetailRepo.CreateBinaryFile(path, data, override);
        } catch (Exception e) {
            throw new Exception("Could not write to File");
        }
    }

    public String getFileCheckSum(String path) throws Exception {
        try {
            return stockDetailRepo.generateFileChecksum(path, SharedConstants.MD5_ALGO);
        } catch (Exception e) {
            throw new Exception("Could not generate file checksum");
        }
    }

    public File getFile(String path) {
        return stockDetailRepo.getFile(path);
    }

    public File[] getRepoContents(String folderName) {
        return stockDetailRepo.getRepoContents(folderName);
    }

    public void createDir(String dirName) {
        stockDetailRepo.createDirectory(dirName);
    }
}
