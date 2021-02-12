package daniel.videoclub.mainpack;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;



//MAIN FRAME
	public class Frame extends JFrame{

		private static final long serialVersionUID = 1L;
		
		private static Dimension resolution;
		
		private static Frame frame = null;
		
		static {
			
			resolution = Toolkit.getDefaultToolkit().getScreenSize();
		}
		
		public Frame() {
		    setIconImage(new ImageIcon("src/IMG/chessIcon.png").getImage());
		    
			setVisible(true);
			
			setResizable(false);
			
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			add(ActivityManager.getActualActivity());// Add JPanel
			setBounds(resWidth()/4,resHeight()/4,resWidth()/2, resHeight()/2);
			
		}
		// add the new panel to frame
		public static void refresh(){
			
			Frame.getInstance().add(ActivityManager.getActualActivity());
			ActivityManager.getActualActivity().repaint();
		}
		
		// Return width of the screen 
		public static int resWidth() {
			
			return (int)resolution.getWidth();
			
		}
		
		// Return height of the screen
		public static int resHeight() {
			
			return (int)resolution.getHeight();
		}
		// Return a rescaled image based on 1920 x 1080 resolution
		public static Image getScaledImage(Image image,int width,int height) {
			
			return image.getScaledInstance(resWidth()*width/1920,resHeight()* height/1080, 0);
			
		}
		
		public static Frame getInstance() {
			
			if (frame == null){
				
				frame = new Frame();
			}
			return frame;
		}
	}
