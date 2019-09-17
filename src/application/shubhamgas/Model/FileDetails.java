
package application.shubhamgas.Model;

import java.io.File;
import java.util.Date;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class FileDetails {

    private final SimpleStringProperty fileName;

    private final SimpleLongProperty size;

    private SimpleStringProperty dateModified;

    public FileDetails(File file) {
        this.fileName = new SimpleStringProperty(file.getName());
        this.size = new SimpleLongProperty(file.length());
        this.dateModified = new SimpleStringProperty(new Date(file.lastModified()).toString());
    }

    public String getFileName() {
        return fileName.get();
    }

    public Long getSize() {
        return size.get();
    }

    public String getDateModified() {
        return dateModified.get();
    }

}
