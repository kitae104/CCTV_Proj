package vlcj.multi_player;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class AVPanel extends JPanel implements ActionListener {
	private EmbeddedMediaPlayerComponent component;
	private EmbeddedMediaPlayer player;
	private int file_index = 0;
	private String[] av_list;
	private JButton btnLeft, btnRight, btnStop;
	private JPanel panelButtom;

	public AVPanel() {
		boolean found = new NativeDiscovery().discover();
		System.out.println(found);
		System.out.println(LibVlc.INSTANCE.libvlc_get_version());

		component = new EmbeddedMediaPlayerComponent();
		player = component.getMediaPlayer();

		setLayout(new BorderLayout());
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setPreferredSize(new Dimension(640, 480));
		setVisible(true);

		add(component, BorderLayout.CENTER);
		player.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void finished(MediaPlayer mediaPlayer) {
				endAV();
			}
		});

		panelButtom = new JPanel();
		btnLeft = new JButton("<-");
		btnLeft.addActionListener(this);
		btnRight = new JButton("->");
		btnRight.addActionListener(this);
		btnStop = new JButton("O");
		btnStop.addActionListener(this);
		
		panelButtom.add(btnLeft);
		panelButtom.add(btnStop);
		panelButtom.add(btnRight);

		add(panelButtom, BorderLayout.SOUTH);
	}

	public void setAV(final String fnames[]) {
		av_list = fnames;
		file_index = 0;
		runAV();
	}

	private void runAV() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				player.playMedia(av_list[file_index]);
			}
		}).start();
	}

	private void endAV() {
		component.release();
		remove(component);
		component = new EmbeddedMediaPlayerComponent();
		player = component.getMediaPlayer();
		add(component, BorderLayout.CENTER);
		player.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void finished(MediaPlayer mediaPlayer) {
				endAV();
			}
		});
		file_index = ++file_index % av_list.length;
		runAV();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		URL url = null;
		HttpURLConnection con = null;
		BufferedReader in = null;
		try {
			if (obj == btnLeft) {
				url = new URL("http://114.71.137.134:18081/decoder_control.cgi?loginuse=admin&loginpas=ipcam8282&command=4&onestep=0");
			} else if (obj == btnRight) {
				url = new URL("http://114.71.137.134:18081/decoder_control.cgi?loginuse=admin&loginpas=ipcam8282&command=6&onestep=0");
			} else if (obj == btnStop) {
				url = new URL("http://114.71.137.134:18081/decoder_control.cgi?loginuse=admin&loginpas=ipcam8282&command=1&onestep=0");
			}
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String line; 
			while((line = in.readLine()) != null) { // response를 차례대로 출력 
				System.out.println(line); 
			}			
		} catch (Exception ex) {

		}
	}
}
