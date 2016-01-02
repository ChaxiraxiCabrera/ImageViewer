package application;

import model.Image;
import view.ImageReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FileImageReader implements ImageReader {

    private static final String[] IMAGE_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif"};
    private final File[] listFiles;

    public FileImageReader(String dirPath){
        this(new File(dirPath));
    }
    
    public FileImageReader(File directory) {
        this.listFiles = directory.listFiles(imageFileFilter());
    }
            
    @Override
    public Image read() {
        return imageAt(0);
    }

    private FilenameFilter imageFileFilter() {
        return new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                for (String imageExtension : IMAGE_EXTENSIONS) 
                    if (name.endsWith(imageExtension)) return true;
                
                return false;
            }
        };
    }

    private Image imageAt(final int index) {
        return new Image() {

            @Override
            public Object bitmap() {
                try {
                    return ImageIO.read(listFiles[index]);
                } catch (IOException e){
                    return null;
                }
            }

            @Override
            public Image prev() {
                return imageAt(index > 0 ? index - 1 : listFiles.length - 1);
            }

            @Override
            public Image next() {
                return imageAt(index < listFiles.length - 1 ? index + 1 : 0);
            }
        };
    }

}
