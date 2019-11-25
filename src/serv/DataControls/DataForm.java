package serv.DataControls;

import com.asoluter.litest.Objects.RegData;
import javafx.util.Pair;
import org.apache.logging.log4j.Logger;
import serv.DataQuery.MDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataForm extends JFrame {
    static Logger logger;

    public DataForm(){
        start();
    }

    private JComponent answersPanel;
    private JComponent contestsPanel;
    private JComponent resultsPanel;
    private JComponent testsPanel;
    private JComponent usersPanel;

    private JTable tableAns;
    private JTable tableCon;
    private JTable tableRes;
    private JTable tableTes;
    private JTable tableUse;

    private void start(){
        setTitle("ServLIT-Database");
        setSize(700,500);
        JPanel root=new JPanel(new BorderLayout());
        getContentPane().add(root);

        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        JTabbedPane tabbedPane=new JTabbedPane();

        answersPanel =new JPanel(new BorderLayout());
        setAnswersContent();
        contestsPanel=new JPanel(new BorderLayout());
        setContestsContent();
        resultsPanel=new JPanel(new BorderLayout());
        setResultsContent();
        testsPanel=new JPanel(new BorderLayout());
        setTestsContent();
        usersPanel=new JPanel(new BorderLayout());
        setUsersContent();

        tabbedPane.addTab("Contests",contestsPanel);
        tabbedPane.addTab("Tests",testsPanel);
        tabbedPane.addTab("Answers", answersPanel);
        tabbedPane.addTab("Results",resultsPanel);
        tabbedPane.addTab("Users",usersPanel);

        root.add(tabbedPane);
    }

    private Pair<String[], Integer[]> getNameIdPair(ResultSet resultSet, String nameCol, String idCol) {
        try {
            ArrayList<String> names = new ArrayList<>();
            ArrayList<Integer> ids = new ArrayList<>();

            while (resultSet.next()) {
                ids.add(resultSet.getInt(idCol));
                names.add(resultSet.getString(nameCol));
            }

            return new Pair<>(names.toArray(new String[0]), ids.toArray(new Integer[0]));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Pair<>(new String[0], new Integer[0]);
    }

    private void setUsersContent() {
        tableUse=new JTable();
        tableUse.setModel(getModel(MDB.getFUsers()));
        JScrollPane scrollPane=new JScrollPane(tableUse,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel controlPanel=new JPanel();

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            tableUse.setModel(getModel(MDB.getFUsers()));
        });

        JButton addButton=new JButton("Add");
        addButton.addActionListener(e -> {
            JTextField username = new JTextField();
            JTextField password = new JPasswordField();
            JTextField mail     = new JTextField();
            JTextField name     = new JTextField();
            JTextField date     = new JTextField();
            new GhostText(date,"yyyy-mm-dd");
            Object[] message = {
                    "Username:"  , username,
                    "Password:"  , password,
                    "Mail:"      , mail,
                    "Full name:" , name,
                    "Birth date:", date
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Add user", JOptionPane.OK_CANCEL_OPTION);
            if(option==JOptionPane.OK_OPTION) {
                MDB.checkRegister(new RegData(name.getText(), username.getText(), password.getText()
                        , mail.getText(), Date.valueOf(date.getText())));
                tableUse.setModel(getModel(MDB.getFUsers()));
            }
        });
        JButton deleteButton=new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int u_id=Integer.parseInt(JOptionPane.showInputDialog(deleteButton,"User ID","Delete user",JOptionPane.PLAIN_MESSAGE));
            MDB.deleteUser(u_id);
            tableUse.setModel(getModel(MDB.getFUsers()));
        });
        JButton exportButton=new JButton("Export");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setFileFilter(new CSVFileFilter());

                int ret_val = chooser.showSaveDialog(exportButton);

                if(ret_val==JFileChooser.APPROVE_OPTION){
                    ReportManager.makeUserCSV(MDB.getFUsers(),chooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        controlPanel.add(refreshButton);
        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        controlPanel.add(exportButton);

        usersPanel.add(scrollPane,BorderLayout.CENTER);
        usersPanel.add(controlPanel,BorderLayout.SOUTH);
    }

    private void setTestsContent() {
        tableTes=new JTable();
        tableTes.setModel(getModel(MDB.getFTests()));
        JScrollPane scrollPane=new JScrollPane(tableTes,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel controlPanel=new JPanel();

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            tableTes.setModel(getModel(MDB.getFTests()));
        });

        JButton addButton=new JButton("Add");
        addButton.addActionListener(e -> {
            Pair<String[], Integer[]> nameIdPair = getNameIdPair(MDB.getFContests(), "name", "cont_id");
            JComboBox contestComboBox = new JComboBox(nameIdPair.getKey());
            JTextField test_name = new JTextField();
            JTextField quest = new JTextField();

            Object[] message = {
                    "Contest Name:" , contestComboBox,
                    "Test name:" , test_name,
                    "Question:" , quest
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Add test", JOptionPane.OK_CANCEL_OPTION);
            if(option==JOptionPane.OK_OPTION) {
                int selectedIndex = contestComboBox.getSelectedIndex();
                Integer[] ids = nameIdPair.getValue();
                if (selectedIndex >= 0 && selectedIndex < ids.length) {
                    int contId = ids[selectedIndex];
                    MDB.addTest(contId, test_name.getText(), quest.getText());
                }
            }
            tableTes.setModel(getModel(MDB.getFTests()));
        });
        JButton deleteButton=new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int u_id=Integer.parseInt(JOptionPane.showInputDialog(deleteButton,"Test ID","Delete test",JOptionPane.PLAIN_MESSAGE));
            MDB.deleteTests(u_id);
            tableTes.setModel(getModel(MDB.getFTests()));
        });
        JButton exportButton=new JButton("Export");
        exportButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setFileFilter(new CSVFileFilter());

            int ret_val = chooser.showSaveDialog(exportButton);

            if(ret_val==JFileChooser.APPROVE_OPTION){
                ReportManager.makeTestCSV(MDB.getFTests(),chooser.getSelectedFile().getAbsolutePath());
            }
        });

        controlPanel.add(refreshButton);
        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        controlPanel.add(exportButton);

        testsPanel.add(scrollPane,BorderLayout.CENTER);
        testsPanel.add(controlPanel,BorderLayout.SOUTH);
    }

    private void setResultsContent() {
        tableRes=new JTable();
        tableRes.setModel(getModel(MDB.getFResults()));
        JScrollPane scrollPane=new JScrollPane(tableRes,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel controlPanel=new JPanel();

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            tableRes.setModel(getModel(MDB.getFResults()));
        });

        JButton exportButton=new JButton("Export");
        exportButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setFileFilter(new CSVFileFilter());

            int ret_val = chooser.showSaveDialog(exportButton);

            if(ret_val==JFileChooser.APPROVE_OPTION){
                ReportManager.makeResCSV(MDB.getFResults(),chooser.getSelectedFile().getAbsolutePath());
            }
        });

        controlPanel.add(refreshButton);
        controlPanel.add(exportButton);

        resultsPanel.add(scrollPane,BorderLayout.CENTER);
        resultsPanel.add(controlPanel,BorderLayout.SOUTH);
    }

    private void setContestsContent() {
        tableCon=new JTable();
        tableCon.setModel(getModel(MDB.getFContests()));
        JScrollPane scrollPane=new JScrollPane(tableCon,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel controlPanel=new JPanel();

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            tableCon.setModel(getModel(MDB.getFContests()));
        });

        JButton addButton=new JButton("Add");
        addButton.addActionListener(e -> {
            JTextField name = new JTextField();
            JTextField ending = new JTextField();
            new GhostText(ending,"____-__-__");

            Object[] message = {
                    "Contest name:" , name,
                    "Ending date:" , ending
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Add contest", JOptionPane.OK_CANCEL_OPTION);
            if(option==JOptionPane.OK_OPTION)
                MDB.addCont(name.getText(),Date.valueOf(ending.getText()));
            tableCon.setModel(getModel(MDB.getFContests()));
        });
        JButton deleteButton=new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int u_id=Integer.parseInt(JOptionPane.showInputDialog(deleteButton,"Contest ID","Delete contest",JOptionPane.PLAIN_MESSAGE));
            MDB.deleteContests(u_id);
            tableCon.setModel(getModel(MDB.getFContests()));
        });
        JButton exportButton=new JButton("Export");
        exportButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setFileFilter(new CSVFileFilter());

            int ret_val = chooser.showSaveDialog(exportButton);

            if(ret_val==JFileChooser.APPROVE_OPTION){
                ReportManager.makeContCSV(MDB.getFContests(),chooser.getSelectedFile().getAbsolutePath());
            }
        });

        controlPanel.add(refreshButton);
        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        controlPanel.add(exportButton);

        contestsPanel.add(scrollPane,BorderLayout.CENTER);
        contestsPanel.add(controlPanel,BorderLayout.SOUTH);
    }

    private void setAnswersContent() {
        tableAns=new JTable();
        tableAns.setModel(getModel(MDB.getFAnswers()));

        JScrollPane scrollPane=new JScrollPane(tableAns,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel controlPanel=new JPanel();

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            tableAns.setModel(getModel(MDB.getFAnswers()));
        });

        JButton addButton=new JButton("Add");
        addButton.addActionListener(e -> {
            Pair<String[], Integer[]> nameIdPair = getNameIdPair(MDB.getFTests(), "quest", "test_id");
            JComboBox testComboBox = new JComboBox(nameIdPair.getKey());
            JTextField ans_text = new JTextField();
            JCheckBox correct_check = new JCheckBox();

            Object[] message = {
                    "Test:" , testComboBox,
                    "Answer text:" , ans_text,
                    "Correct:", correct_check
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Add answer", JOptionPane.OK_CANCEL_OPTION);
            if(option==JOptionPane.OK_OPTION) {
                int selectedIndex = testComboBox.getSelectedIndex();
                Integer[] ids = nameIdPair.getValue();
                if (selectedIndex >= 0 && selectedIndex < ids.length) {
                    int contId = ids[selectedIndex];
                    MDB.addAns(contId, ans_text.getText(), correct_check.isSelected());
                }
            }
            tableAns.setModel(getModel(MDB.getFAnswers()));
        });
        JButton deleteButton=new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int u_id=Integer.parseInt(JOptionPane.showInputDialog(deleteButton,"Answer ID","Delete answer",JOptionPane.PLAIN_MESSAGE));
            MDB.deleteAnswers(u_id);
            tableAns.setModel(getModel(MDB.getFAnswers()));
        });
        JButton exportButton=new JButton("Export");
        exportButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setFileFilter(new CSVFileFilter());

            int ret_val = chooser.showSaveDialog(exportButton);

            if(ret_val==JFileChooser.APPROVE_OPTION){
                ReportManager.makeAnsCSV(MDB.getFAnswers(),chooser.getSelectedFile().getAbsolutePath());
            }
        });

        controlPanel.add(refreshButton);
        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        controlPanel.add(exportButton);

        answersPanel.add(scrollPane,BorderLayout.CENTER);
        answersPanel.add(controlPanel,BorderLayout.SOUTH);
    }

    DefaultTableModel getModel(ResultSet resultSet){
        ArrayList<Object> columnNames = new ArrayList<>();
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int cc=rsmd.getColumnCount();

            for(int i=0;i<cc;i++){
                columnNames.add(rsmd.getColumnLabel(i+1));
            }

            int j=0;
            while (resultSet.next()){
                data.add(new ArrayList<>());
                for(int i=0;i<cc;i++){

                    data.get(j).add(resultSet.getObject(i+1));
                }
                j++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] dataArr= data.size() == 0 ? new Object[0][0] : new Object[data.size()][data.get(0).size()];
        int j=0;
        for(ArrayList<Object> a:data){
            for(int i=0;i<a.size();i++){
                dataArr[j][i]=a.get(i);
            }
            j++;
        }

        return new DefaultTableModel(dataArr,columnNames.toArray()){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

}
