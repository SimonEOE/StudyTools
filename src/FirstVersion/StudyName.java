package FirstVersion;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class StudyName extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PicPanel picturePanel;
	JPanel resultPanel;
	JButton button;
	JTextField answerText;
	JLabel resultLabel;
	JTextField resultText;
	File[] files; //目录下所有文件
	File file; //当前图片文件
	
	public StudyName(File f) throws IOException {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(300, 0, 855, 800);
		
		this.files = f.listFiles(); //获取图片目录下所有图片文件
		Random r = new Random();
		this.file = files[r.nextInt(files.length)]; //随机生成初始化图片
		picturePanel = new PicPanel(getImage(file)); //获得显示图片的面板
		
		resultPanel = new JPanel();
		button = new JButton("提交");
		answerText = new JTextField("                                 ");
		resultText = new JTextField("                                 ");
		resultLabel = new JLabel("回答结果");
		
		//布局组件
		this.add(picturePanel);
		this.add(resultPanel, "South");
		resultPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		resultPanel.add(answerText);
		resultPanel.add(button);
		resultPanel.add(resultLabel);
		resultPanel.add(resultText);
		
		//给提交按钮增加监听器
		ButtonListener listener = new ButtonListener();
		button.addActionListener(listener);
		//给按钮注册快捷键
		button.registerKeyboardAction(listener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		this.setVisible(true); //使窗体可见
		
	}
	
	//按钮监听器类
	public class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name = file.getName().split("\\.")[0].trim(); //获取当前图片的名字
			if(name.equals(answerText.getText().trim())) {
				answerText.setText("");
				resultText.setText("回答正确！");
				file = files[new Random().nextInt(files.length)];
				try {
					picturePanel.image = getImage(file);
					picturePanel.repaint();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				answerText.setText("");
				resultText.setText("回答错误!");
			}
		}
	}
	//获取图像文件
	public Image getImage(File f) throws IOException {
		return ImageIO.read(f);
	}
	
	//创建画图面板
	public class PicPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Image image;
		
		public PicPanel(Image image) {
			this.image = image;
			this.setSize(800, 600);
		}
		//覆盖画图方法
		public void paintComponent(Graphics g) {
			g.drawImage(image, 20, 20, 800, 600, this);
		}
		public void setImage(Image image) {
			this.image = image;
		}
		public Image getImage() {
			return this.image;
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("D:\\E盘\\2 个人\\2 学习\\记忆工厂\\汽车标志");
		new StudyName(file);
	}

}
