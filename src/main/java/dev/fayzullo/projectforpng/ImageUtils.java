package dev.fayzullo.projectforpng;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;


@Component
public class ImageUtils {
    private static final int COVER_WIDTH = 100;
    private static final int COVER_HEIGHT = 100;

    public static void getResize(InputStream source, Path target, int img_width, int img_height) throws IOException {

        resize(source, target, img_width, img_height);

    }

    public static OutputStream resize(InputStream input, Path target, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(input);
        Image newResizedImage = originalImage
                .getScaledInstance(width, height, Image.SCALE_SMOOTH);
        String fileName = target.getFileName().toString();
        String fileExtension = StringUtils.getFilenameExtension(fileName);
        OutputStream outputStream = Files.newOutputStream(target);
        ImageIO.write(convertToBufferedImage(newResizedImage), fileExtension, outputStream);
        return outputStream;
    }

    public static void resizeForCover(BufferedImage input, Path target) throws IOException {
        Image newResizedImage = input.getScaledInstance(COVER_WIDTH, COVER_HEIGHT, Image.SCALE_DEFAULT);
        String s = target.getFileName().toString();
        ImageIO.write(convertToBufferedImage(newResizedImage), StringUtils.getFilenameExtension(target.getFileName().toString()), target.toFile());
    }

    private static BufferedImage convertToBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();
        return bufferedImage;
    }

}
