import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class todo extends JFrame implements ActionListener {
    JPanel cardPanel;
    CardLayout layout;
    //panel01で使用する変数の宣言
    JPanel panel01 = new JPanel(new GridBagLayout());
    JButton btnTodoHozon = new JButton("保存");
    JTextField TodoField = new JTextField("");
    List JuyouList = new List(5);
    JDateChooser datechooser1 = new JDateChooser();

   //panel02で使用する変数の宣言
    JPanel panel02 = new JPanel(new GridBagLayout());
    JButton MinusButton = new JButton("支出");
    JButton PlusButton = new JButton("収入");
    JButton btnKakeiboHozon = new JButton("保存");
    JTextField amountField = new JTextField("");
    JTextField contentField = new JTextField("");
    
    JDateChooser datechooser = new JDateChooser();

   //panel03で使用する変数の宣言
    JPanel panel03 = new JPanel(new GridBagLayout());
    List YarukotoList = new List(5);
    List KakeiboList = new List();
    JList<String> KakeiboJList = new JList<>(new DefaultListModel<>());
    JList<String> YarukotoJList = new JList<>(new DefaultListModel<>());
    JButton editButton = new JButton("編集");
    JButton deleteButton = new JButton("削除");
    JCalendar calendar;
    Boolean KakeiboFT = false;

    //ActionListenerで使う変数
    String todo = TodoField.getText();
    String importance = JuyouList.getSelectedItem();
    Date deadlineDate = datechooser1.getDate();
    String deadline = formatDate(deadlineDate);
    String todoItem = "(重要度： "+ importance + ", 日付： "+ deadline + ")" + todo;
    //日付を保存するMap
    Map<String, String> todoDeadlines = new HashMap<>();
    //削除ボタン管理するMap
    Map<String, JButton> deleteButtonsMap = new HashMap<>();
    public static void main(String[] args) {
        //frameの作成
        todo frame = new todo();
        frame.setTitle("TodoKakeibo");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }

    todo() {
        // panel01
        for (int i = 1; i <= 10; i++) {
            JuyouList.add(String.valueOf(i));
        }
        //配置でGBCを使う。
        GridBagConstraints gbc = new GridBagConstraints();
        panel01.setSize(500,600);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(15, 15, 25, 15);
        gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=200;gbc.gridheight=100;
        panel01.add(new JLabel("やること"),gbc);

        //やることを記入するフィールドの作成
        gbc.gridx=200;gbc.gridy=0;gbc.gridwidth=300;gbc.gridheight=100;
        panel01.add(TodoField,gbc);
        TodoField.addActionListener(this);

        gbc.gridx=0;gbc.gridy=100;gbc.gridwidth=200;gbc.gridheight=100;
        panel01.add(new JLabel("重要度"),gbc);

        //重要度を指定するリストの作成
        gbc.gridx=200;gbc.gridy=100;gbc.gridwidth=300;gbc.gridheight=100;
        panel01.add(JuyouList,gbc);
        JuyouList.addActionListener(this);
        

        gbc.gridx=0;gbc.gridy=200;gbc.gridwidth=200;gbc.gridheight=100;
        panel01.add(new JLabel("期限"),gbc);

        //期限を決めるdatechooserの設定
        gbc.gridx=200;gbc.gridy=200;gbc.gridwidth=300;gbc.gridheight=100;
        panel01.add(datechooser1,gbc);

        //保存ボタンの作成
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.5; 
        btnTodoHozon.setBounds(0, 0, 100, 50);
        gbc.gridx=0;gbc.gridy=300;gbc.gridwidth=500;gbc.gridheight=150;
        panel01.add(btnTodoHozon,gbc);
        btnTodoHozon.addActionListener(this);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // panel02
        gbc.insets = new Insets(15, 15, 25, 15);
        panel02.setSize(500,600);
        
        //収入ボタン
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.weightx =0.5;
        PlusButton.setPreferredSize(new Dimension(100,50));
        panel02.add(PlusButton,gbc);
        PlusButton.addActionListener(this);

        //支出ボタン
        gbc.gridx=1;gbc.gridy=0;gbc.gridwidth=1;gbc.gridheight=1;gbc.weightx =0.5; 
        MinusButton.setPreferredSize(new Dimension(100,50));
        panel02.add(MinusButton,gbc);
        MinusButton.addActionListener(this);

        //金額
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx=0;gbc.gridy=2;gbc.gridwidth=2;gbc.gridheight=1;
        panel02.add(new JLabel("金額"),gbc);

        gbc.gridx=1;gbc.gridy=2;gbc.gridwidth=2;gbc.gridheight=1;
        panel02.add(amountField,gbc);
        amountField.addActionListener(this);

        //内容
        gbc.gridx=0;gbc.gridy=3;gbc.gridwidth=2;gbc.gridheight=1;
        panel02.add(new JLabel("内容"),gbc);

        gbc.gridx=1;gbc.gridy=3;gbc.gridwidth=2;gbc.gridheight=1;
        panel02.add(contentField,gbc);
        contentField.addActionListener(this);

        //日付
        gbc.gridx=0;gbc.gridy=4;gbc.gridwidth=1;gbc.gridheight=1;
        panel02.add(new JLabel("日付"),gbc);

        gbc.gridx=1;gbc.gridy=4;gbc.gridwidth=1;gbc.gridheight=1;
        panel02.add(datechooser,gbc);

        //保存ボタン
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx=0;gbc.gridy=5;gbc.gridwidth=2;gbc.gridheight=1;
        btnKakeiboHozon.setPreferredSize(new Dimension(100,50));
        panel02.add(btnKakeiboHozon,gbc);
        btnKakeiboHozon.addActionListener(this);


        // panel03
        calendar = new JCalendar();
        gbc.insets = new Insets(10, 10, 5, 10);
        panel03.setSize(500,600);
        panel03.setBackground(Color.LIGHT_GRAY);

        //Calendarの設置
        gbc.weightx=1;gbc.weighty=1;
        gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=100;gbc.gridheight=60;
        calendar.setPreferredSize(new Dimension(450,180));
        panel03.add(calendar,gbc);

        //YarukotoListに対する削除ボタンの実装
        gbc.gridx=20;gbc.gridy=60;gbc.gridwidth=10;gbc.gridheight=5;
        panel03.add(deleteButton,gbc);
        deleteButton.addActionListener(this);

        //やることリスト(YarukotoList)と家計簿(KakeiboList)の追加
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx=0;gbc.gridy=60;gbc.gridwidth=50;gbc.gridheight=5;
        panel03.add(new JLabel("やること一覧"),gbc);
        gbc.gridx=50;
        panel03.add(new JLabel("今日の収支"),gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx=0;gbc.gridy=65;gbc.gridwidth=50;gbc.gridheight=30;
        YarukotoList.setPreferredSize(new Dimension(400,150));
        panel03.add(YarukotoList,gbc);
        YarukotoList.addActionListener(this);
        gbc.gridx=50;
        KakeiboList.setPreferredSize(new Dimension(400,150));
        panel03.add(KakeiboJList,gbc);

        //JListの初期化
        KakeiboJList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                super.setSelectionInterval(-1, -1);
            }
        });

        YarukotoJList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                super.setSelectionInterval(-1, -1);
            }
        });

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
    public void clearTodo(){
        //TodoListの保存ボタンが押された際、中身をリセットするクラス。
        TodoField.setText("");
        JuyouList.select(0); 
        datechooser1.setDate(null);
    }
    public void clearKakeibo(){
        //家計簿の保存ボタンが押された際、中身をリセットするクラス。
        amountField.setText("");
        contentField.setText("");
        datechooser.setDate(null);
    }
    private void UpdateYarukotoListForSelectedDate() {
        String selectedDate = formatDate(calendar.getDate());
        if (todoDeadlines.containsKey(selectedDate)) {
            YarukotoList.removeAll();
            YarukotoList.add(todoDeadlines.get(selectedDate));
        } else {
            YarukotoList.removeAll();
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return (date != null) ? sdf.format(date) : "";
    }

    //それぞれのボタン等が押されたときのアクションを記載
    public void actionPerformed(ActionEvent e) {
        //カードパネルの部分のアクションの設定。
        String cmd = e.getActionCommand();
        layout.show(cardPanel, cmd);
        //収入ボタンと支出ボタンが押されたとき
        if(e.getSource() == PlusButton){
            KakeiboFT=true;
        }
        else if(e.getSource()==MinusButton){
            KakeiboFT = false;
        }

        else if(e.getSource() == btnKakeiboHozon){
            // 家計簿の保存ボタンが押されたとき
            String amount = amountField.getText();
            String content = contentField.getText();
            Date Hiduke = datechooser.getDate();
            String date = formatDate(Hiduke);
  
              // TODO: 家計簿リストに追加する処理を実装
              //falseなら赤字で、trueなら青字で。
            if(KakeiboFT==false){
                ((DefaultListModel<String>) KakeiboJList.getModel()).addElement("<html>"+"("+content+")"+"－"
                +"<font color='red'>"+amount+"</font>"+"</html>");
            }
            else if(KakeiboFT ==true){
                ((DefaultListModel<String>) KakeiboJList.getModel()).addElement("<html>"+"("+content+")"+"＋"
                +"<font color='blue'>"+amount+"</font>"+"</html>");
            }
              // テキストフィールドをクリア
            clearKakeibo();
        }
        // Todoの保存ボタンが押されたとき
        else if(e.getSource() == btnTodoHozon){
            String todo = TodoField.getText();
            String importance = JuyouList.getSelectedItem();
            Date deadlineDate = datechooser1.getDate();
            String deadline = formatDate(deadlineDate);
            // TODO: Todoリストに追加する処理を実装
            String todoItem = "(重要度： "+ importance + ", 日付： "+ deadline + ")" + todo;
            YarukotoList.add(todoItem);
            todoDeadlines.put(deadline,todoItem);
            // カレンダーの日付が選択されている場合、その日のTodoリストを表示
            if (deadline != null && calendar.getDate() != null && deadline.equals(formatDate(calendar.getDate()))) {
                UpdateYarukotoListForSelectedDate();
            }
            // テキストフィールドをクリア
            clearTodo();     
        }
        if(e.getSource()==deleteButton){
             // 選択されたアイテムを取得
            String selectTodoItem = YarukotoJList.getSelectedValue();
            if (selectTodoItem != null){
            YarukotoList.remove(selectTodoItem);
            }
        }
    }
}
