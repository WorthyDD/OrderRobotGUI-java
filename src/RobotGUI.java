import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RobotGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> welcomeMessages = new ArrayList<String>();
	List<Food> fishList = new ArrayList<Food>();
	List<Food> meatList = new ArrayList<Food>();
	List<Food> riceList = new ArrayList<Food>();
	List<Food> noodleList = new ArrayList<Food>();
	List<Food> drinkList = new ArrayList<Food>();
	Map<String, List<Food>> map = new HashMap<String,List<Food>>();
	
	JLabel welcomeLabel;		//欢迎语
	JPanel mainDishPanel;		//点菜主页面
	JPanel subDishPanel;		//点菜二级界面
	
	
	public RobotGUI()
	{
		this.setTitle("RobotGUI");
		this.setSize(600,400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args){
		RobotGUI robot = new RobotGUI();
		robot.loadFile();
		robot.showWelcome();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robot.orderDish();
	}
	
	
	
	
	//加载files文件到内存中(欢迎语, dish, joke)
	public void loadFile()
	{
		//读取欢迎语
		File file = new File("src/Files/WelcomeMessages.txt");
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
                welcomeMessages.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        
        //读取dish文件
        String[] foodFileNames = new String[5];
        foodFileNames[0] = "FishDishes.txt";
        foodFileNames[1] = "MeatDishes.txt";
        foodFileNames[2] = "RiceDishes.txt";
        foodFileNames[3] = "NoodleDishes.txt";
        foodFileNames[4] = "DrinkDishes.txt";
        List<List<Food>> list = new ArrayList<List<Food>>();
        list.add(fishList);
        list.add(meatList);
        list.add(riceList);
        list.add(noodleList);
        list.add(drinkList);
        for(int i = 0;i < 5;i++)
        {
        	file = new File("src/Files/"+foodFileNames[i]);
            try {
                System.out.println("以行为单位读取文件内容，一次读一整行：");
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    // 显示行号
                    System.out.println("line " + line + ": " + tempString);
                    line++;
                    String[] str = tempString.split(",");
                    Food food = new Food(str[0], str[1]);
                    List<Food> arr = list.get(i);
                    arr.add(food);
                 }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }
        
        map.put("fish", fishList);
        map.put("meat", meatList);
        map.put("rice", riceList);
        map.put("noodle", noodleList);
        map.put("drink", drinkList);
        
	}
	
	//展示欢迎语
	public void showWelcome()
	{
		if(welcomeLabel == null){
			welcomeLabel = new JLabel("",JLabel.CENTER);
			welcomeLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			welcomeLabel.setFont(new Font("TimesRoman", Font.BOLD, 44));
			this.add(welcomeLabel, BorderLayout.CENTER);
			welcomeLabel.setVisible(false);
		}
		int index = new Random().nextInt(welcomeMessages.size());
		System.out.println("index:"+index);
		String message = welcomeMessages.get(index);
		welcomeLabel.setText(message);
		welcomeLabel.setVisible(true);
	}
	
	//点菜
	public void orderDish()
	{
		if(mainDishPanel == null){
			mainDishPanel = new JPanel(new BorderLayout());
			JLabel tipLabel = new JLabel("Please select option:");
			tipLabel.setFont(new Font("TimeRoman",Font.BOLD,16));
			mainDishPanel.add(tipLabel, BorderLayout.NORTH);
			JPanel dishPanel = new JPanel(new GridLayout(2,3));
			
			for(String key:map.keySet())
			{
				JButton b = new JButton(key);
				b.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(subDishPanel == null){
							subDishPanel = new JPanel(new BorderLayout());
						}
						subDishPanel.removeAll();
						subDishPanel = new JPanel();
						JLabel tipLabel = new JLabel("Please select a dish order:");
						tipLabel.setFont(new Font("TimeRoman",Font.BOLD,16));
						JPanel buttonPanel = new JPanel(new GridLayout(2,3));
						List<Food> foodList = map.get(key);
						for(Food food: foodList)
						{
							JButton b = new JButton(food.name+"\n"+food.price);
							buttonPanel.add(b);
						}
						subDishPanel.add(tipLabel, BorderLayout.NORTH);
						subDishPanel.add(buttonPanel, BorderLayout.CENTER);
						subDishPanel.setVisible(true);
					}
				});
				dishPanel.add(b);
				dishPanel.setBackground(Color.GREEN);
			}
			mainDishPanel.add(dishPanel, BorderLayout.CENTER);
			this.remove(welcomeLabel);
			this.add(mainDishPanel, BorderLayout.CENTER);
		}
	}
	
	
}
