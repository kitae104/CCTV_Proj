package vlcj.multi_player;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class MyAVPlayer extends JFrame {

	AVPanel avPanel1, avPanel2, avPanel3, avPanel4;
	
	public AVPanel getAvPanel1() {
		return avPanel1;
	}

	public AVPanel getAvPanel2() {
		return avPanel2;
	}
	
	public AVPanel getAvPanel3() {
		return avPanel3;
	}

	public AVPanel getAvPanel4() {
		return avPanel4;
	}

	public MyAVPlayer(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 레이아웃
		setLayout(new GridLayout(2, 2));
		
		avPanel1 = new AVPanel();
		add(avPanel1);
		
		avPanel2 = new AVPanel();
		add(avPanel2);
		
		avPanel3 = new AVPanel();
		add(avPanel3);
		
		avPanel4 = new AVPanel();
		add(avPanel4);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		MyAVPlayer avPlayer = new MyAVPlayer("My AV Player", 600, 500);
		String[] fnames1 = {"D:\\영상\\동키카\\동키카.mp4"}; 
		String[] fnames2 = {"D:\\영상\\동키카\\test.mp4"}; 
		String[] fnames3 = {"D:\\영상\\동키카\\동키카.mp4"}; 
		String[] fnames4 = {"D:\\영상\\동키카\\test.mp4"}; 
		avPlayer.getAvPanel1().setAV(fnames1);
		avPlayer.getAvPanel2().setAV(fnames2);
		avPlayer.getAvPanel3().setAV(fnames3);
		avPlayer.getAvPanel4().setAV(fnames4);
		
	}

}
