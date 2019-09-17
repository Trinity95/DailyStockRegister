
package application.shubhamgas.Model;

import java.io.File;
import java.time.LocalDate;

import application.Main;
import application.shubhamgas.repository.SharedConstants;
import application.shubhamgas.repository.StockDetailRepository;

public class StockDetails {

    private File partFile;

    private File finalFile;

    private int otherVersionCount;

    private LocalDate date;

    private String dirName;

    public File getPartFile() {
        return partFile;
    }

    public File getFinalFile() {
        return finalFile;
    }

    public int getOtherVersionCount() {
        return otherVersionCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDirName() {
        return dirName;
    }

    public StockDetails(LocalDate date) {
        partFile = null;
        finalFile = null;
        otherVersionCount = 0;
        this.date = date;
        dirName = date.toString();
        lookUpStockDetails();
    }

    private void lookUpStockDetails() {
        StockDetailRepository repo = Main.getInstance().getRepo();

        String partFileName = date.toString() + SharedConstants.HYPHEN + SharedConstants.PART + SharedConstants.EXCEL_EXTENSION;
        partFile = repo.getFile(getRepoPath(partFileName));

        String finalFileName = date.toString() + SharedConstants.HYPHEN + SharedConstants.FINAL + SharedConstants.EXCEL_EXTENSION;
        finalFile = repo.getFile(getRepoPath(finalFileName));
        repo.createDir(dirName);
        otherVersionCount = getTotalVersions();
    }

    private int getTotalVersions() {
        StockDetailRepository repo = Main.getInstance().getRepo();
        int count = 0;
        File[] files = repo.getRepoContents(dirName);
        for (File fileEntry : files) {
            if (fileEntry.getName().contains(SharedConstants.VERSION)) {
                count++;
            }
        }
        return count;
    }

    public boolean isStockFinalized() {
        if (finalFile != null) {
            return true;
        }
        return false;
    }

    public boolean isStockStarted() {
        if (partFile == null) {
            return false;
        }
        return true;
    }

    public void startStock() {
        StockDetailRepository repo = Main.getInstance().getRepo();
        String fileName = date.toString() + SharedConstants.HYPHEN + SharedConstants.PART + SharedConstants.EXCEL_EXTENSION;
        String defaultFile = SharedConstants.DEFAULT_FILE + SharedConstants.EXCEL_EXTENSION;
        try {
            repo.createDir(dirName);
            repo.writeFile(getRepoPath(fileName), repo.readFile(defaultFile), false);
            partFile = repo.getFile(getRepoPath(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resumeStock() {
        if (shouldCreateVersionStamp()) {
            createVersionStamp();
            otherVersionCount++;
        }
    }

    public void finishStock() {
        StockDetailRepository repo = Main.getInstance().getRepo();
        createVersionStamp();
        otherVersionCount++;
        String fileName = date.toString() + SharedConstants.HYPHEN + SharedConstants.FINAL + SharedConstants.EXCEL_EXTENSION;
        try {
            repo.writeFile(getRepoPath(fileName), repo.readFile(getRepoPath(getPartFile().getName())), true);
            finalFile = repo.getFile(getRepoPath(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File[] getVersionHistory() {
        StockDetailRepository repo = Main.getInstance().getRepo();
        return repo.getRepoContents(dirName);
    }

    // private methods

    private void createVersionStamp() {
        StockDetailRepository repo = Main.getInstance().getRepo();
        int newVersion = otherVersionCount + 1;
        String fileName = date.toString() + SharedConstants.HYPHEN + SharedConstants.VERSION + newVersion + SharedConstants.EXCEL_EXTENSION;
        try {
            repo.writeFile(getRepoPath(fileName), repo.readFile(getRepoPath(getPartFile().getName())), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean shouldCreateVersionStamp() {
        if (otherVersionCount == 0) {
            return true;
        }

        StockDetailRepository repo = Main.getInstance().getRepo();
        try {
            String latestVersionFileName =
                date.toString() + SharedConstants.HYPHEN + SharedConstants.VERSION + otherVersionCount + SharedConstants.EXCEL_EXTENSION;
            String partFileCheckSum = repo.getFileCheckSum(getRepoPath(getPartFile().getName()));
            String versionFileCheckSum = repo.getFileCheckSum(getRepoPath(latestVersionFileName));
            if (versionFileCheckSum.equals(partFileCheckSum)) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private String getRepoPath(String fileName) {
        return dirName + SharedConstants.PATH_DELIMITER + fileName;
    }
}
