import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Board extends JPanel implements ActionListener{
	public int key;
	private int speed=1;
	private Image apple,dot,righthead,lefthead,uphead,downhead,head;
	
	private int length=5,score=0;
	
	private final int randomposition=21;//between 1 to 29 (frame size accordingly)
	private int applex,appley;
	
	private final int dotsize=40;
	private final int alldotsize=900;//1800*800=1440000  40*40=1600(x*y)--->1440000/1600=900
	
	private final int x[]=new int[alldotsize];//1000*800=800000/100=8000
	private final int y[]=new int[alldotsize]; 
	private int dots;
	
	private Timer timer;
	private boolean inGame=true;
	
	private boolean left=false,right=true,up=false,down=false;
	
	Board()
	{
		addKeyListener(new TAdapter());
		setPreferredSize(new Dimension(1800,800));
		setBackground(Color.BLACK);
		setFocusable(true);//all keylistener work only if they are setfocusable true
		loadImage();
		initGame();
		
	}
	
	
	
	public void loadImage()
	{
		ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/s40.png"));
		apple=i1.getImage();
		ImageIcon i2= new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/sb40.png"));
		dot=i2.getImage();
		ImageIcon i3= new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/shd40.png"));
		downhead=i3.getImage();
		ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/shl40.png"));
		lefthead=i4.getImage();
		ImageIcon i5= new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/shr40.png"));
		righthead=i5.getImage();
		ImageIcon i6= new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/shu40.png"));
		uphead=i6.getImage();
		
		head=righthead;
	}
	
	
	public void initGame()
	{
		dots=5;
		for(int i=0;i<dots;i++)
		{
			x[i]=400-i*dotsize;//1:50  2:50-d0tsize  3:50-2*dotsize
			y[i]=400;
		}
		locateApple();
		
		timer=new Timer(100,this);
		timer.start();
		
	}
	
	
	public void locateApple()
	{
		int r=(int)(Math.random()*randomposition);
		applex=r*dotsize;
		r=(int)(Math.random()*randomposition);
		appley=r*dotsize;
	}
	
	public void checkApple()
	{
		if((x[0]==applex)&&(y[0]==appley))
		{
			dots++;
			locateApple();
			length++;
			score=score+5;
		}
	}
	
	public void checkCollision()
	{
		for(int i=dots;i>0;i--)
		{
			if((i>0)&&(x[0]==x[i])&&(y[0]==y[i]))
			{
				inGame=false;
			}
		}
		if(x[0]<0)
		{
			x[0]=1800;
		}
		if(x[0]>1800)
		{
			x[0]=0;
		}
		if(y[0]<0)
		{
			y[0]=800;
		}
		if(y[0]>800)
		{
			y[0]=0;
		}
		if(!inGame)
		{
			timer.stop();
		}
	}
	
	public void move()
	{
		
		for( int z=dots;z>0;z--)
		{
			x[z]=x[z-1];
			y[z]=y[z-1];
		}
		if(left)
		{
			x[0] =x[0] - dotsize;
		}
		if(right)
		{
			x[0]=x[0] + dotsize;
		}
		if(up)
		{
			y[0] =y[0] -dotsize;
		}
		if(down)
		{
			y[0] = y[0] + dotsize;
		}
		
		
	}
	
	public void paintComponent(Graphics g) //graphics function override
	{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		
		draw(g);
		g.setFont(new Font("SAN_SERIF",Font.BOLD,24));
		g.drawString("Score: "+score,1600,50);
		g.drawString("Length: "+length, 1600, 80);
		
	}
	
	public void draw(Graphics g)
	{
		if(inGame)
		{
			g.drawImage(apple,applex,appley,this);
			
			for(int i=0;i<dots;i++)
			{
				if(i==0)
				{
					g.drawImage(head,x[i],y[i],this);
				}
				else
				{
					g.drawImage(dot,x[i],y[i],this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
		}
		else
			gameOver(g);
	}
	
	public void gameOver(Graphics g)
	{
		String msg="Game Over";
		Font font =new Font("SAN_SERIF",Font.BOLD,50);
		FontMetrics matrices = getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(msg,(1800-matrices.stringWidth(msg))/2,800/2);
	}
	{
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(inGame)
		{
			checkApple();
			checkCollision();
			move();
		}
		repaint();//comes with graphics so i have to override the function of graphics 
	}
	
	private class TAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			 key = e.getKeyCode();
			 if(key==KeyEvent.VK_SPACE)//to pause
			 {
				 if(speed==1)
				 {
					 timer.stop();
					 speed=0;
				 }else
				 {
					 timer.start();
					 speed=1;
				 }
			 }
			 if(key==KeyEvent.VK_R)//to restart
			 {
				 score=0;length=5;dots=5;key=KeyEvent.VK_RIGHT;
				 for(int i=0;i<dots;i++)
					{
						x[i]=400-i*dotsize;//1:50  2:50-d0tsize  3:50-2*dotsize
						y[i]=400;
					}
				 
				 repaint();
			 }
			if(key==KeyEvent.VK_LEFT && (!right))
			{
				head=lefthead;
				left=true;up=false;down=false;
			}
			
			if(key==KeyEvent.VK_RIGHT && (!left))
			{
				head=righthead;
				right=true;up=false;down=false;
			}
			
			if(key==KeyEvent.VK_UP && (!down))
			{
				head=uphead;
				left=false;right=false;up=true;
			}
			
			if(key==KeyEvent.VK_DOWN && (!up))
			{
				head=downhead;
				left=false;right=false;down=true;
			}
		}
		
	}
}