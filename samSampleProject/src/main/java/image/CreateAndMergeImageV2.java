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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class CreateAndMergeImageV2 {
//	private static final int IMG_HEIGHT = 1000;
//	private static final int IMG_WIDTH = 1000;
	private static final int IMG_BUFFER = 50;
	private static final int TABLE_BUFFER = 10;
	private static final int IMG_FOOTER = 50;
	
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
        Font font = g2.getFont().deriveFont(18f);
        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        y1 = yBuf;
        
        // Put title of the table
        // TODO Need to improve logic of positioning
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
        		
	            x1 += xInc;
	        }
        	y1 += yInc;
        }
        
	}
	
	private static byte[] getBytesFromImage(String fileLocation) throws FileNotFoundException{
		/*
    	 * 1. How to convert an image file to  byte array?
    	 */
 
        //File file = new File("C:\\rose.jpg");
		File file = new File(fileLocation);
 
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
 
        //byte[] bytes = bos.toByteArray();
        return bos.toByteArray();
	}
	
	private static void addImage(Graphics g, byte[] bytes, image.Image img) throws IOException{
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
        //image.getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        //((Graphics2D)g).drawImage(image.getScaledInstance(BUFFER, IMG_HEIGHT, Image.SCALE_DEFAULT), 0, 0, BUFFER, IMG_HEIGHT, 0, 0, BUFFER, IMG_HEIGHT, null);
        g.drawString(img.getTitle(),img.getX1()+(img.getWidth()/5), img.getY1() - 20);
        ((Graphics2D)g).drawImage(image.getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_DEFAULT), img.getX1(), img.getY1(), img.getX2(), img.getY2(), 0, 0, img.getWidth(), img.getHeight(), null);
	}
	
	public static void main(String[] args) throws IOException {
		/*BufferedImage bufferedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g1 = bufferedImage.createGraphics();
		createImage(g1);
		editImage(g1);
		g1.dispose();
		File file = new File("D:/Work/RIATest/SonarQubePlugin/SMSPOC/src/main/java/image/myimage.jpg");
	    ImageIO.write(bufferedImage, "jpg", file);*/
		
		ResourceBundle rb = ResourceBundle.getBundle("image.image");
		
		int height = Integer.parseInt(rb.getObject("image_height").toString());
		int width = Integer.parseInt(rb.getObject("image_width").toString());
		String[] demographicDetail = rb.getObject("demographic_details").toString().split(",");
		String[] cameraDetail = rb.getObject("camera_details").toString().split(",");
		
		
		/*t.setTitle("Demographics Data");
		t.setBufferHeight(50);
		t.setBufferWidth(300);
		t.setColumns(2);
		t.setRows(3);
		t.setHeight(250);
		t.setWidth(250);
		t.setInset(new Insets(2, 2, 2, 2));
		t.setPadding(10);
		t.setTableData(new String[] { "Name", "<Name>", "Height", "<Height>", "Weight", "<Weight>" });*/
		
		BufferedImage bufferedImage = new BufferedImage(width, height+IMG_FOOTER, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g1 = bufferedImage.createGraphics();
		
		g1.setColor(Color.white);
        g1.fillRect(0, 0, height, width+IMG_FOOTER);
		
        // Draw demographics table.
        //Table t = new Table("Demographics Data", width/2-IMG_BUFFER, height/2-IMG_BUFFER, IMG_BUFFER, width/2, demographicDetail.length/2, 2, TABLE_BUFFER, new Insets(2, 2, 2, 2), demographicDetail);
        Table table = new Table("Demographics Data", width/2-IMG_BUFFER, height/2-IMG_BUFFER, height/2+IMG_BUFFER, width/2, demographicDetail.length/2, 2, TABLE_BUFFER, new Insets(2, 2, 2, 2), demographicDetail);
		drawTable(g1, table);
		
		// Draw Camera Details table
		//t.setBufferHeight(IMG_BUFFER);
		table.setBufferWidth(IMG_BUFFER);
		table.setTitle("Camera Details");
		table.setRows(cameraDetail.length/2);
		table.setTableData(cameraDetail);
		
		drawTable(g1, table);
		
		image.Image probeImage = new image.Image("Probe Image", width/2-IMG_BUFFER, height/2-IMG_BUFFER, IMG_BUFFER+TABLE_BUFFER, IMG_BUFFER+TABLE_BUFFER, width/2-TABLE_BUFFER, height/2-TABLE_BUFFER);
		image.Image candidateImage = new image.Image("Candidate Image", width/2-IMG_BUFFER, height/2-IMG_BUFFER, width/2+TABLE_BUFFER, IMG_BUFFER+TABLE_BUFFER, width-IMG_BUFFER-TABLE_BUFFER, height/2-TABLE_BUFFER);
		
		addImage(g1, getBytesFromImage("C:\\rose.jpg"), candidateImage);
		addImage(g1, getBytesFromImage("C:\\rose.jpg"), probeImage);
		
//		addImage(g1, "Candidate Image", getBytesFromImage("C:\\rose.jpg"), 250, 250, 60, 60, 290, 290);
//		addImage(g1, "Probe Image", getBytesFromImage("C:\\rose.jpg"), 250, 250, 60, 360, 290, 590);
		
//		createImage(g1);
//		editImage(g1);
		
		// Write image to specified location
		g1.dispose();
		File file = new File("D:/Work/RIATest/SonarQubePlugin/SMSPOC/src/main/java/image/myimage.jpg");
	    ImageIO.write(bufferedImage, "jpg", file);
		
		System.out.println(file.getPath());
	}
}
