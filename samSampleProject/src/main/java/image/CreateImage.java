package image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CreateImage {
	
	private static String[] msgs = new String[] { "Name", "Sanket Modi", "Height", "5' 9\"", "Weight", "80 KG" };
	private static final int
    ROWS =  3,
    COLS =  2,
    PAD  = 10;

	public static void main(String[] args) throws IOException {

		int width = 250;
	    int height = 250;

	    // Constructs a BufferedImage of one of the predefined image types.
	    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	    // Create a graphics which can be used to draw into the buffered image
	    Graphics2D g2d = bufferedImage.createGraphics();

	    paintComponent(g2d);
	    
	    // fill all the image with white
	    /*g2d.setColor(Color.white);
	    g2d.fillRect(0, 0, width, height);

	    // create a circle with black
//	    g2d.setColor(Color.black);
//	    g2d.fillOval(0, 0, width, height);

	    // create a string with yellow
	    g2d.setColor(Color.black);
	    g2d.drawLine(2, 2, 2, 248);
	    g2d.drawLine(2, 2, 248, 2);
	    g2d.drawLine(2, 248, 248, 248);
	    g2d.drawLine(248, 2, 248, 248);
	    g2d.drawString("Java Code Geeks", 50, 120);*/

	    // Disposes of this graphics context and releases any system resources that it is using. 
	    g2d.dispose();

	    // Save as PNG
//	    File file = new File("D:/Work/RIATest/SonarQubePlugin/SMSPOC/src/main/java/image/myimage.png");
//	    ImageIO.write(bufferedImage, "png", file);

	    // Save as JPEG
	    File file = new File("D:/Work/RIATest/SonarQubePlugin/SMSPOC/src/main/java/image/myimage.jpg");
	    ImageIO.write(bufferedImage, "jpg", file);

	}
	
	protected static void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = 250;
        int h = 250;
        Insets insets = new Insets(2, 2, 2, 2);    // border info
        
        g2.setColor(Color.white);
        g2.fillRect(0, 0, w, h);
        
        double xInc = (w - insets.left - insets.right - 2*PAD)/COLS;
        double yInc = (h - insets.top - insets.bottom - 2*PAD)/ROWS;
        g2.setPaint(Color.black);
        // vertical lines
        double x1 = insets.left + PAD, y1 = insets.top + PAD,
               y2 = h - insets.bottom - PAD, x2;
        for(int j = 0; j <= COLS; j++)
        {
            g2.draw(new Line2D.Double(x1, y1, x1, y2));
            x1 += xInc;
        }
        // horizontal lines
        x1 = insets.left + PAD; x2 = w - insets.right - PAD;
        for(int j = 0; j <= ROWS; j++)
        {
            g2.draw(new Line2D.Double(x1, y1, x2, y1));
            y1 += yInc;
        }
        // try a couple of cell strings
        g2.setPaint(Color.black);
        Font font = g2.getFont().deriveFont(18f);
        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        y1 = 0;
        for(int j = 0; j < msgs.length; j+=2)
        {
        	x1 = 0;
        	for(int i = 0; i < COLS; i++){
	            float width = (float)font.getStringBounds(msgs[j + i%2], frc).getWidth();
	            LineMetrics lm = font.getLineMetrics(msgs[j + i%2], frc);
	            float ascent = lm.getAscent(), descent = lm.getDescent();
	            float sx = (float)(insets.left + PAD + x1 + (xInc - width)/2);
	            float sy = (float)(insets.top + PAD + + y1 + (yInc + ascent)/2 - descent);
	            g2.drawString(msgs[j + i%2], sx, sy);
	            x1 += xInc;
	        }
        	y1 += yInc;
        }
    }
  

}
