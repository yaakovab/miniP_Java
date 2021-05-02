

package renderer;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 *
 * @author Yaacov
 *
 */
public class ImageWriterTest {

    @Test
    public void testWriteToImage() {
        ImageWriter imageWriter= new ImageWriter("TestBlue", 800, 500);
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {

                if (i % 50 == 0) {
                    imageWriter.writePixel(i, j, Color.BLACK);
                }
                else if (j % 50 == 0) {
                    imageWriter.writePixel(i, j, Color.BLACK);
                }
                else
                    imageWriter.writePixel(i, j, new Color(0, 0, 255));

            }

        }

        imageWriter.writeToImage();
    }

}