import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;

public class todo extends JFrame implements ActionListener{

    JPanel cardPanel;
    CardLayout layout;

    public static void main(String[] args) {
        todo frame = new todo();
        frame.setTitle("TodoKakeibo");
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public todo() {

        // panel01
        JPanel panel01 = new JPanel();
        JButton btnTodoHozon = new JButton("保存");
        panel01.add(btnTodoHozon);

        // panel02
        JPanel panel02 = new JPanel();
        JButton btnKakeiboHozon = new JButton("保存");
        
        JTextField amountField = new JTextField(50);
        JTextField dateField = new JTextField(50);
        JTextField contentField = new JTextField(50);
        //setLayout(new FlowLayout());
        
        panel02.add(new JLabel("金額"));
        panel02.add(amountField);
        panel02.add(new JLabel("日付"));
        panel02.add(dateField);
        panel02.add(new JLabel("内容"));
        panel02.add(contentField);
        panel02.add(btnKakeiboHozon,BorderLayout.CENTER);
        

        // panel03
        JPanel panel03 = new JPanel();
        panel03.setBackground(Color.LIGHT_GRAY);
        JButton btnCalender = new JButton("third");
        panel03.add(btnCalender);

        // CardLayout用パネル
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);

        cardPanel.add(panel01, "panel01");
        cardPanel.add(panel02, "panel02");
        cardPanel.add(panel03, "panel03");


        // カード移動用ボタン
        JButton firstButton = new JButton("Todo入力");
        firstButton.addActionListener(this);
        firstButton.setActionCommand("panel01");

        JButton secondButton = new JButton("家計簿入力");
        secondButton.addActionListener(this);
        secondButton.setActionCommand("panel02");

        JButton thirdButton = new JButton("カレンダー");
        thirdButton.addActionListener(this);
        thirdButton.setActionCommand("panel03");

        JPanel btnPanel = new JPanel();
        btnPanel.add(firstButton,BorderLayout.SOUTH);
        btnPanel.add(secondButton,BorderLayout.SOUTH);
        btnPanel.add(thirdButton,BorderLayout.SOUTH);

        // cardPanelとカード移動用ボタンをJFrameに配置
        Container contentPane = getContentPane();
        contentPane.add(cardPanel, BorderLayout.CENTER);
        contentPane.add(btnPanel, BorderLayout.PAGE_END);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        layout.show(cardPanel, cmd);
    }
}
