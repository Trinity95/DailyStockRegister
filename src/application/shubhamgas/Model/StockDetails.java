
package application.shubhamgas.Model;

import java.io.File;
import java.time.LocalDate;

import application.Main;
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
        StockDetailRepository stockDetailRepository = Main.getInstance().getRepo();
    }

    public boolean isStockFinalized() {
        return false;
    }

    public boolean isStockStarted() {
        return false;
    }

    public void startStock() {
    }

    public void resumeStock() {
    }

    public void finishStock() {
    }

    public File[] getVersionHistory() {
        return null;
    }

    // private methods

    private void createVersionStamp() {

    }

    private boolean shouldCreateVersionStamp() {
        return true;
    }
}
