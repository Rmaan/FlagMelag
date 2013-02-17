package javachallenge.graphics.components;

import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Screen extends JFrame {
	public Screen(String title) {
		super();
		setLayout(null);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setUndecorated(true);
		//setExtendedState(Frame.MAXIMIZED_BOTH);
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//setBounds(0,0,screenSize.width, screenSize.height);
		//getContentPane().setBackground(ColorMaker.background);
	}
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		pack();
	}
}
