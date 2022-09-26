import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


/**  Client class for displaying images recieved from a decoder
 * it recieves an original image and a JPEG decoded image
 * it has a JFrame and 2 JLabels has instance variables
 * it is also responsible for refreshing the frame every time it recieves an image matrix
 */

public class Image {
  BufferedImage image;
  int imageX = 0;
  int imageY = 0;
  JLabel label;
  JFrame frame;
  
  public Image(String imgPath) {
    this.image = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
    readImageRGB(512, 512, imgPath, this.image);
    showImage();

  }
  	/** Read Image RGB
	  *  Reads the image of given width and height at the given imgPath into the provided BufferedImage.
	  */
  public BufferedImage readImageRGB(int width, int height, String imgPath, BufferedImage img)
	{
		try
		{
			int frameLength = width * height*3;

			File file = new File(imgPath);
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			raf.seek(0);

			long len = frameLength;
			byte[] bytes = new byte[(int) len];

			raf.read(bytes);

			int ind = 0;
			for(int y = 0; y < height; y++)
			{
				for (int x = 0; x < width; x++) {
					// byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind + height * width];
					byte b = bytes[ind + height * width * 2];
					

					int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					
					img.setRGB(x, y, pix);
					ind++;
				}
			}
			raf.close();
			
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return img;
	}


  public void refresh() {
    frame.repaint();
  }

    
  private void showImage() {
    frame = new JFrame();
		GridBagLayout gLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gLayout);

		label = new JLabel(new ImageIcon(image));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 200;
        c.ipady = 150;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
    
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		
		    frame.getContentPane().add(label, c);

		frame.pack();
    frame.setVisible(true);

	}
}
