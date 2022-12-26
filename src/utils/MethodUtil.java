package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MethodUtil {
    public static int showMessageConfirm(String message) {
        Object[] options = { "Có", "Không" };
        int confirm = JOptionPane.showOptionDialog(
                null,
                message,
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        return confirm;

    }

    public static void copyFile(String source, String dest) throws IOException {

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;

        // kiem tra thu muc chua hinh anh co ton tai, khong co thi tao thu muc
        File directory = new File("src/main/resources/images/");
        if (!directory.exists()) {
            directory.mkdir();
        }

        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(dest);

            int c;
            while ((c = inputStream.read()) != -1) {
                outputStream.write(c);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static boolean deleteFile(String filePath) {

        File file = new File(filePath);
        return file.delete();
    }

    public static File chooseFile(JPanel view) {
        File file = null;

        JFileChooser chooseFile = new JFileChooser();
        chooseFile.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
        int value = chooseFile.showOpenDialog(view);
        if (value == JFileChooser.APPROVE_OPTION) {
            file = chooseFile.getSelectedFile();
        }
        return file;
    }
}
