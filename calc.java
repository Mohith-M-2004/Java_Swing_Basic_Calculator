import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class calc {

    static JTextField display;
    static double firstNumber = 0;
    static String operator = "";
    static boolean startNewNumber = true;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Simple Calculator");
        frame.setSize(300,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10,10));
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        
        ImageIcon image=new ImageIcon("calc.png");
		frame.setIconImage(image.getImage());
		

        display = new JTextField("0");
        display.setFont(new Font("Arial",Font.BOLD,24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        display.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        frame.add(display,BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(40,40,40));
        panel.setLayout(new GridLayout(5,4,5,5));

        String[] buttons = {
                "C","DEL","%","/",
                "7","8","9","*",
                "4","5","6","-",
                "1","2","3","+",
                "±","0",".","="
        };

        for(String text : buttons){

            JButton btn = new JButton(text);
            btn.setBackground(new Color(60,60,60));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFocusable(false);
            btn.setFont(new Font("Arial",Font.BOLD,16));

            btn.addActionListener(e -> handleButton(text));

            panel.add(btn);
        }
        
        frame.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {

                char key = e.getKeyChar();
                int code = e.getKeyCode();

                if("0123456789.+-*/".indexOf(key) >= 0){
                    handleButton(String.valueOf(key));
                }

                else if(code == KeyEvent.VK_ENTER){
                    handleButton("=");
                }

                else if(code == KeyEvent.VK_BACK_SPACE){
                    handleButton("DEL");
                }

                else if(code == KeyEvent.VK_DELETE){
                    handleButton("C");
                }

            }
        });
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    static void handleButton(String value){

        if("0123456789.".contains(value)){

            if(startNewNumber){
                display.setText(value);
                startNewNumber=false;
            }else{
                display.setText(display.getText()+value);
            }
        }
        
        else if(value.equals("±")){

            double number = Double.parseDouble(display.getText());
            number = number * -1;

            if(number == (long) number)
                display.setText(String.valueOf((long) number));
            else
                display.setText(String.valueOf(number));
        }
        
        else if(value.equals("%")){

            double number = Double.parseDouble(display.getText());
            number = number / 100;

            if(number == (long) number)
                display.setText(String.valueOf((long) number));
            else
                display.setText(String.valueOf(number));
        }

        else if("+-*/".contains(value)){

            firstNumber=Double.parseDouble(display.getText());
            operator=value;
            startNewNumber=true;

        }

        else if(value.equals("=")){

            double secondNumber=Double.parseDouble(display.getText());
            double result=0;

            switch(operator){

                case "+": result=firstNumber+secondNumber; break;
                case "-": result=firstNumber-secondNumber; break;
                case "*": result=firstNumber*secondNumber; break;
                case "/": result=firstNumber/secondNumber; break;
            }

            if(result==(long)result)
                display.setText(String.valueOf((long)result));
            else
                display.setText(String.valueOf(result));

            startNewNumber=true;
        }

        else if(value.equals("C")){

            display.setText("0");
            firstNumber=0;
            operator="";
            startNewNumber=true;

        }

        else if(value.equals("DEL")){

            String current=display.getText();

            if(current.length()>1)
                display.setText(current.substring(0,current.length()-1));
            else
                display.setText("0");

        }
    }
}
