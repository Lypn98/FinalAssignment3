import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class todo extends JFrame implements ActionListener {
    //CardLayoutでの変数の宣言
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
    JList<String> KakeiboJList = new JList<>(new DefaultListModel<>());
    JList<String> YarukotoJList = new JList<>(new DefaultListModel<>());
    JButton editButton = new JButton("編集");
    JButton deleteButton = new JButton("削除");
    JButton deleteButton1 = new JButton("削除");
    JCalendar calendar;
    Boolean KakeiboFT = false;

    //ActionListenerのTodoで使うアイテム
    String todo = null;
    String importance = null;
    Date deadlineDate ;
    String deadline = null;
    String todoItem = null;

    //ActionListenerの家計簿で使うアイテム
    String amount = null;
    String content = null;
    String date = null;
    Date Hiduke;
    String MinusItem = null;
    String PlusItem = null;

    //日付が一致しているかどうか判断する
    boolean SameDate = false;
    Date selectedDate;
    /*//Todoに関する据え置きのリスト
    JList<String> SueokiTodoList = new JList<>(new DefaultListModel<>());
    JList<String> SueokiKakeiboList = new JList<>(new DefaultListModel<>());*/
    ArrayList<Data> data = new ArrayList<>() , minus = new ArrayList<>();    
    int count=0;
    int count2 = 0;

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
        //backgroundはグレーに設定。
        panel03.setBackground(Color.LIGHT_GRAY);

        //Calendarの設置
        gbc.fill = GridBagConstraints.BOTH;
        //上に詰める設定
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx=1;gbc.weighty=1;
        gbc.gridx=0;gbc.gridy=0;gbc.gridwidth=80;gbc.gridheight=50;
        panel03.add(calendar,gbc);

        //YarukotoJListに対する削除ボタンの実装
        gbc.fill = GridBagConstraints.NONE;
        //左上につめる設定。
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx=20;gbc.gridy=50;gbc.gridwidth=10;gbc.gridheight=1;
        panel03.add(deleteButton,gbc);
        deleteButton.addActionListener(this);
        //KakeiboJListに対する削除ボタン
        gbc.gridx=60;gbc.gridy=50;gbc.gridwidth=10;gbc.gridheight=1;
        panel03.add(deleteButton1,gbc);
        //ActionListenerの設定。
        deleteButton1.addActionListener(this);

        //Labelの設定
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx=0;gbc.gridy=50;gbc.gridwidth=10;gbc.gridheight=1;
        panel03.add(new JLabel("やること一覧"),gbc);
        gbc.gridx=40;
        panel03.add(new JLabel("今日の収支"),gbc);

        //YarukotoJListとKakieboJListのGUIの配置を設定。
        gbc.fill = GridBagConstraints.BOTH;
        //上につめるanchor
        gbc.anchor = GridBagConstraints.NORTH;
        //sizeの設定
        YarukotoJList.setPreferredSize(new Dimension(200,150));
        KakeiboJList.setPreferredSize(new Dimension(200,150));
        //上下左右の余白の設定
        gbc.insets = new Insets(0, 5, 5, 5);
        //比率の設定
        gbc.gridx=0;gbc.gridy=51;gbc.gridwidth=40;gbc.gridheight=60;
        //panel03にYarukotoJListを追加する
        panel03.add(YarukotoJList,gbc);
        gbc.gridx=40;
        panel03.add(KakeiboJList,gbc);
        YarukotoJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // panel03のコンストラクタ内でcalendarのPropertyChangeListenerを追加
        calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // 日付が変更されたときの処理
                if ("calendar".equals(evt.getPropertyName())) {
                    ((DefaultListModel<String>) YarukotoJList.getModel()).removeAllElements();
                    ((DefaultListModel<String>) KakeiboJList.getModel()).removeAllElements();
                    //カレンダーで選択した日を取得
                    selectedDate = calendar.getDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(selectedDate);
                    for(int i=0;i<data.size();i++){
                        if(data.get(i).month == cal.get(Calendar.MONTH) && data.get(i).day == cal.get(Calendar.DAY_OF_MONTH)-1){
                            ((DefaultListModel<String>) YarukotoJList.getModel()).addElement(data.get(i).task);
                        }
                    }
                    /*int todoListSize = SueokiTodoList.getModel().getSize();
                    int KakeiboListSize = SueokiKakeiboList.getModel().getSize();
                    
                    //SueokiTodoListの長さでfor文を回して、deadlineDateとselectedDateが一致していればYarukotoJListに追加
                    for(int i=0;i<todoListSize;i++){
                        if(isSameDate(deadlineDate, selectedDate)){
                            String todoItem = SueokiTodoList.getModel().getElementAt(i);
                            ((DefaultListModel<String>) YarukotoJList.getModel()).addElement(todoItem);
                        }
                    }*/
                    for(int i=0;i<minus.size();i++){
                        if(minus.get(i).month == cal.get(Calendar.MONTH) && minus.get(i).day == cal.get(Calendar.DAY_OF_MONTH)-1){
                            ((DefaultListModel<String>) KakeiboJList.getModel()).addElement(minus.get(i).task);
                        }
                    }                
                }
            }
        });
        
    
        // CardLayout用パネル
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);

        //panel01～panel03をcardpanelに追加
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

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return (date != null) ? sdf.format(date) : "";
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
    /*private boolean isSameDate(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date1).equals(sdf.format(date2));
    }*/


    //それぞれのボタン等が押されたときのアクションを記載
    public void actionPerformed(ActionEvent e) {
        //カードパネルの部分のアクションの設定。
        String cmd = e.getActionCommand();
        layout.show(cardPanel, cmd);
        //収入ボタンと支出ボタンが押されたときフラッグを切り替える。
        if(e.getSource() == PlusButton){
            KakeiboFT=true;
        }
        else if(e.getSource()==MinusButton){
            KakeiboFT = false;
        }

        if(e.getSource() == btnKakeiboHozon){
            // 家計簿の保存ボタンが押されたとき
            Calendar cal = Calendar.getInstance();

            amount = amountField.getText();
            content = contentField.getText();
            Hiduke = datechooser.getDate();
            cal.setTime(Hiduke);
            date = formatDate(Hiduke);
            MinusItem = "<html>"+"("+content+")"+"－"
            +"<font color='red'>"+amount+"</font>"+"</html>";
            PlusItem = "<html>"+"("+content+")"+"＋"
            +"<font color='blue'>"+amount+"</font>"+"</html>";
              // TODO: 家計簿リストに追加する処理を実装
              //falseなら赤字で、trueなら青字で。
            if(KakeiboFT==false){
                Data m = new Data();
                m.month = cal.get(Calendar.MONTH);
                m.day = cal.get(Calendar.DAY_OF_MONTH)-1;
                m.task = MinusItem;
                minus.add(m);
                //((DefaultListModel<String>) SueokiKakeiboList.getModel()).addElement(MinusItem);
            }
            else if(KakeiboFT ==true){
                Data m = new Data();
                m.month = cal.get(Calendar.MONTH);
                m.day = cal.get(Calendar.DAY_OF_MONTH)-1;
                m.task = PlusItem;
                minus.add(m);
                //((DefaultListModel<String>) SueokiKakeiboList.getModel()).addElement(PlusItem);
            }
              // テキストフィールドをクリア
            clearKakeibo();
        }
        // Todoの保存ボタンが押されたとき
        if(e.getSource() == btnTodoHozon){
            //calendarからInstanceの取得
            Calendar cal = Calendar.getInstance();
            //やることをテキストフィールドから取得
            todo = TodoField.getText();
            //重要度を取得
            importance = JuyouList.getSelectedItem();
            //日付も取得する。
            deadlineDate = datechooser1.getDate();
            cal.setTime(deadlineDate);
            deadline = formatDate(deadlineDate);
            // TODO: Todoリストに追加する処理を実装
            todoItem = "(重要度： "+ importance +  ")" + todo;
            //Date型infのmanth day taskを設定
            //((DefaultListModel<String>) SueokiTodoList.getModel()).addElement(todoItem);
            Data inf = new Data();
            inf.month = cal.get(Calendar.MONTH);
            inf.day = cal.get(Calendar.DAY_OF_MONTH)-1;
            inf.task = todoItem;
            data.add(inf);
            // テキストフィールドをクリア
            clearTodo();
        }
        //deleteBunttonをおしたときの処理(Todoの要素の削除)
        if (e.getSource() == deleteButton) {
            int selectedIndex = YarukotoJList.getSelectedIndex();
            System.out.println(selectedIndex);
            if (selectedIndex != -1) {
                // 選択された要素があれば削除
                ((DefaultListModel<String>) YarukotoJList.getModel()).removeElementAt(selectedIndex);
                //calendarから日付を取得
                selectedDate = calendar.getDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(selectedDate);
                //要素を削除するだけでなく、dataも削除するプログラム
                for(int i=0;i<data.size();i++){
                    if(data.get(i).month == cal.get(Calendar.MONTH) && data.get(i).day == cal.get(Calendar.DAY_OF_MONTH)-1){
                        if(count==selectedIndex){
                            data.remove(i);
                            count = -1;
                        }
                        count++;
                    }
                }
            }
        }
        //deleteButton1がおされたときの処理（Kakeiboの要素の削除）
        //deleteButtonと同じような処理。変数名が違ったりするだけ。
        if(e.getSource() == deleteButton1){
            int KakeiboIndex = KakeiboJList.getSelectedIndex();
            if(KakeiboIndex!=-1){
                ((DefaultListModel<String>) KakeiboJList.getModel()).removeElementAt(KakeiboIndex);
                selectedDate = calendar.getDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(selectedDate);
                for(int i=0;i<minus.size();i++){
                    if(minus.get(i).month == cal.get(Calendar.MONTH) && minus.get(i).day == cal.get(Calendar.DAY_OF_MONTH)-1){
                        if(count2 == KakeiboIndex){
                            minus.remove(i);
                            count2 = -1;
                        }
                        count2++;
                    }
                }
            }
        }
    }
}

class Data{
    int day;
    int month;
    String task;
}