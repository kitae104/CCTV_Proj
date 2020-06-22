package vlcj.player;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/*****************************************
 * 파일명 : MyPlayer.java
 * 설명 : VLC를 이용한 동영상 플레이어 
 * @author 김기태
 * @version : 1.0
 * @date : 2020. 6. 22. 
 *****************************************
 */
public class MyPlayer extends JFrame implements ActionListener, Runnable {
	private MediaPlayerFactory mediaPlayerFactory;
	private Canvas c;
	private JPanel panCenter, panSouth;
	private JButton btnPlay, btnPause, btnFile;
	private JFileChooser fileChooser;
	private EmbeddedMediaPlayer mediaPlayer;
	private JLabel lblTime;	
	private Thread thread;
	
	private ImageIcon[] imgs = {
			new ImageIcon("images/play.png"),
			new ImageIcon("images/pause.png"),
			new ImageIcon("images/file.png")			
	};
	
	public MyPlayer() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		mediaPlayerFactory = new MediaPlayerFactory();
		
		makeCanvas();
        
        makeCenterPanel();        
        makeSouthPanel();
        
        add(panCenter, BorderLayout.NORTH);
        add(panSouth, BorderLayout.SOUTH);
        
		setLocation(100, 100);
        setSize(1050, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        setVisible(true);
	}

	private void makeCanvas() {
		c = new Canvas();
        c.setBackground(Color.black);
        c.setBounds(100, 500, 1050, 500);
	}

	private void makeSouthPanel() {
		panSouth = new JPanel();
        
        btnPlay = new JButton(imgs[0]); 
        btnPlay.setOpaque(false);
        btnPlay.addActionListener(this);
        panSouth.add(btnPlay);
        
        btnPause = new JButton(imgs[1]);
        btnPause.addActionListener(this);
        panSouth.add(btnPause);
        
        btnFile = new JButton(imgs[2]);
        btnFile.addActionListener(this);
        panSouth.add(btnFile);
        
        lblTime = new JLabel("00:00:00");
        panSouth.add(lblTime);
	}

	private void makeCenterPanel() {
		panCenter = new JPanel();        
        panCenter.setLayout(new BorderLayout());
        panCenter.add(c, BorderLayout.CENTER);     
	}
	
	public static void main(String[] args) {
		
		boolean found = new NativeDiscovery().discover();
		System.out.println(found);
		System.out.println(LibVlc.INSTANCE.libvlc_get_version());
		
		
		// libvlc.dll 경로 설정 
		String libraryName = RuntimeUtil.getLibVlcLibraryName();
		String path = "C:\\Program Files\\VideoLAN\\VLC";
		NativeLibrary.addSearchPath(libraryName, path);
		Native.load(libraryName, LibVlc.class);
		
		MyPlayer mp = new MyPlayer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btnFile) {
			File file;
			fileChooser = new JFileChooser();		// 디렉토리 설정 
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// 필터 추가 
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MP4", "mp4"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("AVI", "avi"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MPEG", "mpg", "mpeg"));
			
			fileChooser.showOpenDialog(null);
			
	        file = fileChooser.getSelectedFile();
	        String mediaPath = file.getAbsolutePath();
	        
	        // 플레이어 설정 
	        mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
	        mediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(c));
	        mediaPlayer.playMedia(mediaPath);
	        thread = new Thread(this);
	        thread.start();
		} else if(obj == btnPlay) {
			mediaPlayer.play();
			thread.resume();
			
		} else if(obj == btnPause) {
			mediaPlayer.pause();
			thread.suspend();
			
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
				long millis = mediaPlayer.getTime();
				String curTime = String.format("%02d:%02d:%02d", 
						TimeUnit.MILLISECONDS.toHours(millis),
					    TimeUnit.MILLISECONDS.toMinutes(millis),
					    TimeUnit.MILLISECONDS.toSeconds(millis) - 
					    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
					);
				lblTime.setText(curTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	

}
