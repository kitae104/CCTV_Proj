package vlcj.player;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class MainWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane ;
    EmbeddedMediaPlayerComponent playerComponent ;  // 定义一个播放器界面
    private JPanel panel;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private JButton btnStop;
    private JPanel controlPanel;
    private JProgressBar progress;
    private JMenuBar menuBar;
    private JMenu mnFile;
    private JMenuItem mntmOpen;
    private JMenuItem mntmOpenSubtitle;
    private JMenuItem mntmExit;
    private JSlider slider;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        new MainWindow();
    }

    /**
     * Create the frame.
     */
    public MainWindow() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("play.png")));
        setTitle("MyPlayer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 614, 456);        
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);        
        mnFile = new JMenu("File");
        menuBar.add(mnFile);        
        mntmOpen = new JMenuItem("Open Video...");
        mnFile.add(mntmOpen);        
        mntmOpenSubtitle = new JMenuItem("Open Subtitle...");
        mnFile.add(mntmOpenSubtitle);        
        mntmExit = new JMenuItem("Exit");
        mnFile.add(mntmExit);     
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);        
        
        JPanel videopanel = new JPanel();
        contentPane.add(videopanel, BorderLayout.CENTER);
        videopanel.setLayout(new BorderLayout(0, 0));        
        playerComponent = new EmbeddedMediaPlayerComponent() ;
        videopanel.add(playerComponent) ;
        
        panel = new JPanel();
        videopanel.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout(0, 0));        
        controlPanel = new JPanel();
        
        panel.add(controlPanel, BorderLayout.CENTER);        
        btnStop = new JButton("Stop");
        controlPanel.add(btnStop);        
        btnNewButton = new JButton("Play");
        controlPanel.add(btnNewButton);        
        btnNewButton_1 = new JButton("Pause");
        controlPanel.add(btnNewButton_1);        
        
        slider = new JSlider();
        slider.setValue(100); 
        slider.setMaximum(120);        
        controlPanel.add(slider);        
        
        progress = new JProgressBar();        
        progress.setStringPainted(true);
        panel.add(progress, BorderLayout.NORTH);
        
    }
    
    public EmbeddedMediaPlayer getMediaPlayer() {
        return playerComponent.getMediaPlayer() ;
    }
    
    public JProgressBar getProgressBar() {
        return progress ;
    }
    
}
