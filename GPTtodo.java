import javax.swing.*;
//import javax.swing.event.*;
import java.awt.*;
//import java.awt.event.*;
//import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;
//import java.util.Calendar;

public class GPTtodo {
    private JFrame frame;
    private JPanel cardPanel;
    private DefaultListModel<String> todoListModel;


    public GPTtodo() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel(new CardLayout());

        // Todo List Panel
        JPanel todoPanel = createTodoPanel();
        cardPanel.add(todoPanel, "TODO");

        // Calendar Panel
        JPanel calendarPanel = createCalendarPanel();
        cardPanel.add(calendarPanel, "CALENDAR");

        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menu = new JMenu("Switch View");
        menuBar.add(menu);

        JMenuItem todoItem = new JMenuItem("Todo List");
        todoItem.addActionListener(e -> showCard("TODO"));
        menu.add(todoItem);

        JMenuItem calendarItem = new JMenuItem("Calendar");
        calendarItem.addActionListener(e -> showCard("CALENDAR"));
        menu.add(calendarItem);

        // Default View
        showCard("TODO");
    }

    private JPanel createTodoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        todoListModel = new DefaultListModel<>();
        JList<String> todoList = new JList<>(todoListModel);
        panel.add(new JScrollPane(todoList), BorderLayout.CENTER);

        JButton addButton = new JButton("Add Todo");
        addButton.addActionListener(e -> addTodo());
        panel.add(addButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCalendarPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JCalendar calendar = new JCalendar();
        panel.add(calendar, BorderLayout.CENTER);

        return panel;
    }

    private void addTodo() {
        String todoItem = JOptionPane.showInputDialog(frame, "Enter Todo:");
        if (todoItem != null && !todoItem.isEmpty()) {
            todoListModel.addElement(todoItem);
        }
    }

    private void showCard(String cardName) {
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
        cardLayout.show(cardPanel, cardName);
    }
}
