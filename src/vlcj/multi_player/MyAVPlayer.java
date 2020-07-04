package vlcj.multi_player;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class MyAVPlayer extends JFrame {

	private AVPanel[] avPanels; 
		
	public MyAVPlayer(String title, int width, int height, int size) {
		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// 패널 갯수 설정 
		avPanels = new AVPanel[size];
		
		// 레이아웃
		switch(size) {
		case 1:
			setLayout(new GridLayout(1, 1));
			break;
		case 2:
			setLayout(new GridLayout(1, 2));
			break;
		case 4:
			setLayout(new GridLayout(2, 2));
			break;
		case 8:
			setLayout(new GridLayout(2, 4));
			break;
		case 16:
			setLayout(new GridLayout(4, 4));
			break;
		}
		
		for (int i = 0; i < avPanels.length; i++) {
			avPanels[i] = new AVPanel();
			add(avPanels[i]);
		}
		setVisible(true);
	}
	
	public AVPanel[] getAvPanels() {
		return avPanels;
	}

	public static void main(String[] args) {
		MyAVPlayer avPlayer = new MyAVPlayer("My AV Player", 600, 500, 1);
		String[][] fnames = {{"D:\\영상\\동키카\\동키카.mp4"}, 
				{"D:\\영상\\동키카\\test.mp4"}, 
				{"D:\\영상\\동키카\\동키카.mp4"},
				{"rtsp://admin:ipcam8282@114.71.137.134:11554/udp/av0_0"},
				{"D:\\영상\\동키카\\동키카.mp4"}, 
				{"D:\\영상\\동키카\\test.mp4"}, 
				{"D:\\영상\\동키카\\동키카.mp4"},
				{"rtsp://admin:ipcam8282@114.71.137.134:11554/udp/av0_0"},
				{"D:\\영상\\동키카\\동키카.mp4"}, 
				{"D:\\영상\\동키카\\test.mp4"}, 
				{"D:\\영상\\동키카\\동키카.mp4"},
				{"rtsp://admin:ipcam8282@114.71.137.134:11554/udp/av0_0"},
				{"D:\\영상\\동키카\\동키카.mp4"}, 
				{"D:\\영상\\동키카\\test.mp4"}, 
				{"D:\\영상\\동키카\\동키카.mp4"},
				{"rtsp://admin:ipcam8282@114.71.137.134:11554/udp/av0_0"}				
		}; 
		
		// 화면 설정 
		for (int i = 0; i < fnames.length; i++) {
			avPlayer.getAvPanels()[i].setAV(fnames[i]);
		}
				
	}
}

// String id = "admin";
// String pw = "ipcam8282";
// String ip = "114.71.137.134";
// String port = "11554";
// String command = "7";
// String url = "http://"+ ip +":"+ port +"/decoder_control.cgi?loginuse="+ id +"&loginpas="+ pw +"&command="+ command +"&onestep=0";