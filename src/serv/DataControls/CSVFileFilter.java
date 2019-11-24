package serv.DataControls;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class CSVFileFilter extends FileFilter {
    private String ext="csv";

    @Override
    public boolean accept(File f) {
        return getExtension(f).equals(ext);
    }

    @Override
    public String getDescription() {
        return null;
    }

    private String getExtension(File pathname) {
        String filename = pathname.getPath();
        int i = filename.lastIndexOf('.');
        if ( i>0 && i<filename.length()-1 ) {
            return filename.substring(i+1).toLowerCase();
        }
        return "";
    }
}
