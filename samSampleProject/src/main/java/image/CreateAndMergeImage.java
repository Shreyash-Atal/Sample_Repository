package image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class CreateAndMergeImage {
	/*private static String[] msgs = new String[] { "Name", "<Name>", "Height", "<Height>", "Weight", "<Weight>" };
	private static final int
    ROWS =  3,
    COLS =  2,
    PAD  = 10;*/
	
	private static final int IMG_HEIGHT = 600;
	private static final int IMG_WIDTH = 600;
	private static final int BUFFER = IMG_WIDTH / 2;

	private static void drawTable(Graphics g, Table t){
		Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = t.getWidth();
        int h = t.getHeight();
        Insets insets = t.getInset();    // border info
        String[] msgs = t.getTableData();
        
        // Variables to decide the position of all the table lines.
        
        double pad = t.getPadding(),
        	   rows = t.getRows(),
        	   cols = t.getColumns(),
        	   xInc = (w - insets.left - insets.right - 2*pad)/cols,
        	   yInc = (h - insets.top - insets.bottom - 2*pad)/rows,
        	   xBuf = t.getBufferWidth(),
        	   yBuf = t.getBufferHeight();

        // Setting the colour of table lines.
        g2.setPaint(Color.black);
        
        // table column width
        double x1 = insets.left + pad + xBuf,
        	   y1 = insets.top + pad + yBuf,
        	   y2 = h - insets.bottom - pad + yBuf, 
        	   x2;
        
        // Vertical lines
        for(int j = 0; j <= cols; j++)
        {
            g2.draw(new Line2D.Double(x1, y1, x1, y2));
            x1 += xInc;
        }
        
        // horizontal lines
        x1 = insets.left + pad + xBuf; x2 = w - insets.right - pad + xBuf;
        for(int j = 0; j <= rows; j++)
        {
            g2.draw(new Line2D.Double(x1, y1, x2, y1));
            y1 += yInc;
        }
        
        // try a couple of cell strings
//        g2.setPaint(Color.black);
        Font font = g2.getFont().deriveFont(18f);
        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        y1 = yBuf;
        
//        writeString(g2, "Demographics Data", font, frc, insets, xBuf+10, xInc, yBuf - 40, yInc, pad);
        
        g2.drawString(t.getTitle(),(float)(xBuf + (w/5)), (float)(yBuf - 10));
        
        for(int j = 0; j < msgs.length; j+=2)
        {
        	x1 = xBuf;
        	for(int i = 0; i < cols; i++){
	            float width = (float)font.getStringBounds(msgs[j + i%2], frc).getWidth();
	            LineMetrics lm = font.getLineMetrics(msgs[j + i%2], frc);
	            float ascent = lm.getAscent(), descent = lm.getDescent();
	            float sx = (float)(insets.left + pad + x1 + (xInc - width)/2);
	            float sy = (float)(insets.top + pad + + y1 + (yInc + ascent)/2 - descent);
	            g2.drawString(msgs[j + i%2], sx, sy);
        		
//        		writeString(g2, msgs[j+i%2], font, frc, insets, x1, xInc, y1, yInc, pad);
        		
	            x1 += xInc;
	        }
        	y1 += yInc;
        }
        
	}
	
	/*private static void writeString(Graphics2D g2, String message, Font font, FontRenderContext frc, Insets insets, double x, double xInc, double y, double yInc, double pad){
		float width = (float)font.getStringBounds(message, frc).getWidth();
        LineMetrics lm = font.getLineMetrics(message, frc);
        float ascent = lm.getAscent(), descent = lm.getDescent();
        float sx = (float)(insets.left + pad + x + (xInc - width)/2);
        float sy = (float)(insets.top + pad + + y + (yInc + ascent)/2 - descent);
        g2.drawString(message, sx, sy);
	}
	
	private static void createImage(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = IMG_WIDTH / 2;
        int h = IMG_HEIGHT;
        Insets insets = new Insets(2, 2, 2, 2);    // border info
        
        g2.setColor(Color.white);
        g2.fillRect(0, 0, w + BUFFER, h);
        
        double xInc = (w - insets.left - insets.right - 2*PAD)/COLS;
        double yInc = (h - insets.top - insets.bottom - 2*PAD)/ROWS;
        g2.setPaint(Color.black);
        // vertical lines
        double x1 = insets.left + PAD + BUFFER, y1 = insets.top + PAD,
               y2 = h - insets.bottom - PAD, x2;
        for(int j = 0; j <= COLS; j++)
        {
            g2.draw(new Line2D.Double(x1, y1, x1, y2));
            x1 += xInc;
        }
        // horizontal lines
        x1 = insets.left + PAD + BUFFER; x2 = w - insets.right - PAD + BUFFER;
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
        	x1 = BUFFER;
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
    }*/
	
	private static void addImage(Graphics g) throws IOException{
		/*
    	 * 1. How to convert an image file to  byte array?
    	 */
 
        File file = new File("C:\\rose.jpg");
 
        FileInputStream fis = new FileInputStream(file);
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.
 
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum); 
            }
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
 
        byte[] bytes = bos.toByteArray();
 
        /*
         * 2. How to convert byte array back to an image file?
         */
 
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
 
        //ImageIO is a class containing static methods for locating ImageReaders
        //and ImageWriters, and performing simple encoding and decoding. 
 
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; 
        ImageInputStream iis = ImageIO.createImageInputStream(source); 
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
 
        Image image = reader.read(0, param);
        image.getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ((Graphics2D)g).drawImage(image.getScaledInstance(BUFFER, IMG_HEIGHT, Image.SCALE_DEFAULT), 0, 0, BUFFER, IMG_HEIGHT, 0, 0, BUFFER, IMG_HEIGHT, null);
	}
	
	public static void main(String[] args) throws IOException {
		/*BufferedImage bufferedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g1 = bufferedImage.createGraphics();
		createImage(g1);
		editImage(g1);
		g1.dispose();
		File file = new File("D:/Work/RIATest/SonarQubePlugin/SMSPOC/src/main/java/image/myimage.jpg");
	    ImageIO.write(bufferedImage, "jpg", file);*/
		
		/*Table t = new Table();
		t.setTitle("Demographics Data");
		t.setBufferHeight(50);
		t.setBufferWidth(300);
		t.setColumns(2);
		t.setRows(3);
		t.setHeight(250);
		t.setWidth(250);
		t.setInset(new Insets(2, 2, 2, 2));
		t.setPadding(10);
		t.setTableData(new String[] { "Name", "<Name>", "Height", "<Height>", "Weight", "<Weight>" });
		
		BufferedImage bufferedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g1 = bufferedImage.createGraphics();
		
		g1.setColor(Color.white);
        g1.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
		
		drawTable(g1, t);
		
		t.setBufferHeight(350);
		t.setTitle("Camera Details");
		t.setTableData(new String[] { "Name", "<Name>", "Location", "<Location>", "Terminal", "<Terminal>" });
		
		drawTable(g1, t);
		
//		createImage(g1);
//		editImage(g1);
		g1.dispose();
		File file = new File("D:/Work/RIATest/SonarQubePlugin/SMSPOC/src/main/java/image/myimage.jpg");
	    ImageIO.write(bufferedImage, "jpg", file);
		
		System.out.println(file.getPath());*/
	}
}
