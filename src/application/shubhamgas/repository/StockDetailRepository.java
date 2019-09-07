
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

    public void writeFile(String fileName, byte[] data, boolean override) throws Exception {
        String path = stockDetailRepo.getRootPath() + SharedConstants.PATH_DELIMITER + fileName;
        stockDetailRepo.CreateBinaryFile(path, data, override);
    }

    public long getFileSize(String fileName) {
        String path = stockDetailRepo.getRootPath() + SharedConstants.PATH_DELIMITER + fileName;
        return (new File(path).length());
    }
    
    public int getNumberOfFilesInDirectory() {
        return getRepoContents().length;        
    }
    
    public File[] getRepoContents() {
        File repository = new File(stockDetailRepo.getRootPath());
        return repository.listFiles();

    }
    
    public boolean isFileInRepo(String fileName) {
        File[] files = getRepoContents();
        for (final File fileEntry : files) {
            if (fileEntry.getName().equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}

