package NoteMenu;

import MyNote.Note;
import MyNote.NoteMenu;
import MyNote.NoteWriteArea;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuForm extends JMenu {
    //父类组件——向上走
    private Note _parentFrame;
    private NoteWriteArea _parentText;

    private JCheckBox _zdhh=new JCheckBox("自动换行");
    private JMenuItem _zt=new JMenuItem("     字体...");

    //子类窗口
    private JDialog _jd;

    public MenuForm(String name,Note frame,NoteWriteArea writeArea){
        super(name);
        _parentFrame=frame;
        _parentText=writeArea;

        //监听器
        ActionListener _menuEditorListner=new ActionListener() {
            private boolean first=true;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==_zdhh)
                    AutoNextLine();
                if(e.getSource()==_zt)
                {
                    if(first)
                    {
                        FontPane();
                        first=false;
                    }
                    else
                    {
                        _jd.setVisible(true);
                    }

                }

            }
        };

        _zdhh.addActionListener(_menuEditorListner);
        _zt.addActionListener(_menuEditorListner);
        this.add(_zdhh);
        this.add(_zt);

    }

    private void FontPane(){
        _jd=new JDialog(_parentFrame,true);
        _jd.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        _jd.setTitle("字体");
        _jd.setSize(480,510);
        _jd.setLocation(_parentFrame.getX()+80,_parentFrame.getY()+40);
        _jd.setLayout(null);

        //JButton
        JButton _buttonConfirm=new JButton("确认");
        JButton _buttonCancel=new JButton("取消");

       //JLabel
        JLabel _LabelF=new JLabel("字体：");
        JLabel _LabelY=new JLabel("字形：");
        JLabel _LabelS=new JLabel("大小：");
        JLabel _LabelShow=new JLabel("<html><body>文本字体演示<br>Example</body></html>");
        JPanel _jp=new JPanel();
        _jp.add(_LabelShow);
        _jp.setBorder(BorderFactory.createTitledBorder("示例"));
        Font _LabelShowfont=new Font("微软雅黑",0,_parentText.getFont().getSize());
        _LabelShow.setFont(_LabelShowfont);

        //JTextField
        JTextField _textF=new JTextField("微软雅黑");
        JTextField _textY=new JTextField("常规");
        JTextField _textS=new JTextField(String.valueOf(_parentText.getFont().getSize()));

        //JList
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontName = e.getAvailableFontFamilyNames();
        JList<String> _listF=new JList<String>(fontName);
        JScrollPane _ScrollF=new JScrollPane(_listF);
        _listF.setSelectionMode(0);
        _listF.setSelectedValue("微软雅黑",true);

        String[] fontStyle={"常规","粗体","倾斜","粗偏斜体"};
        JList<String> _listY=new JList<String>(fontStyle);
        JScrollPane _ScrollY=new JScrollPane(_listY);
        _listY.setSelectionMode(0);
        _listY.setSelectedValue("常规",true);

        Integer[] fontSize={8,9,10,11,12,14,16,18,20,22,24,26,28,36,48,72};
        JList<Integer> _listS=new JList<Integer>(fontSize);
        JScrollPane _ScrollS=new JScrollPane(_listS);
        _listS.setSelectionMode(0);
        _listS.setSelectedValue(_parentText.getFont().getSize(),true);

        //设置绝对位置
        _LabelF.setSize(65,30);
        _LabelF.setLocation(10,10);
        _textF.setSize(200,25);
        _textF.setLocation(10,35);
        _ScrollF.setSize(200,150);
        _ScrollF.setLocation(10,60);

        _LabelY.setSize(65,30);
        _LabelY.setLocation(220,10);
        _textY.setSize(120,25);
        _textY.setLocation(220,35);
        _ScrollY.setSize(120,150);
        _ScrollY.setLocation(220,60);

        _LabelS.setSize(65,30);
        _LabelS.setLocation(350,10);
        _textS.setSize(100,25);
        _textS.setLocation(350,35);
        _ScrollS.setSize(100,150);
        _ScrollS.setLocation(350,60);

        _jp.setSize(300,150);
        _jp.setLocation(150,230);

        _buttonConfirm.setSize(80,30);
        _buttonConfirm.setLocation(250,400);

        _buttonCancel.setSize(80,30);
        _buttonCancel.setLocation(340,400);

        _jd.add(_LabelF);
        _jd.add(_textF);
        _jd.add(_ScrollF);
        _jd.add(_LabelY);
        _jd.add(_textY);
        _jd.add(_ScrollY);
        _jd.add(_LabelS);
        _jd.add(_textS);
        _jd.add(_ScrollS);
        _jd.add(_jp);
        _jd.add(_buttonConfirm);
        _jd.add(_buttonCancel);


        /*===================================================
                              功能实现
         ====================================================*/

        _listF.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                _textF.setText(_listF.getSelectedValue());
                if(_textY.getText().equals("常规"))
                {
                    Font tmp=new Font(_textF.getText(),Font.PLAIN,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
                else if(_textY.getText().equals("粗体"))
                {
                    Font tmp=new Font(_textF.getText(),Font.BOLD,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
                else if(_textY.getText().equals("倾斜"))
                {
                    Font tmp=new Font(_textF.getText(),Font.ITALIC,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
                else if(_textY.getText().equals("粗偏斜体"))
                {
                    Font tmp=new Font(_textF.getText(),3,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
            }
        });

        _listY.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                _textY.setText(_listY.getSelectedValue());
                if(_textY.getText().equals("常规"))
                {
                    Font tmp=new Font(_textF.getText(),Font.PLAIN,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
                else if(_textY.getText().equals("粗体"))
                {
                    Font tmp=new Font(_textF.getText(),Font.BOLD,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
                else if(_textY.getText().equals("倾斜"))
                {
                    Font tmp=new Font(_textF.getText(),Font.ITALIC,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
                else if(_textY.getText().equals("粗偏斜体"))
                {
                    Font tmp=new Font(_textF.getText(),3,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
            }
        });

        _listS.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                _textS.setText(_listS.getSelectedValue().toString());
                if(_textY.getText().equals("常规"))
                {
                    Font tmp=new Font(_textF.getText(),Font.PLAIN,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
                else if(_textY.getText().equals("粗体"))
                {
                    Font tmp=new Font(_textF.getText(),Font.BOLD,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
                else if(_textY.getText().equals("倾斜"))
                {
                    Font tmp=new Font(_textF.getText(),Font.ITALIC,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
                else if(_textY.getText().equals("粗偏斜体"))
                {
                    Font tmp=new Font(_textF.getText(),3,Integer.parseInt(_textS.getText()));_LabelShow.setFont(tmp);
                }
            }
        });

        _buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Font font=new Font(_listF.getSelectedValue(),_listY.getSelectedIndex(),Integer.parseInt(_textS.getText()));
                _parentText.setFont(font);
            }
        });

        _buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _jd.setVisible(false);
            }
        });


        _jd.setResizable(false);
        _jd.setVisible(true);


    }

    private void AutoNextLine(){
        if(_zdhh.isSelected())
        {
            _parentText.setLineWrap(true);
            NoteMenu.getJM().setEnabled(false);
        }
        else
        {
            _parentText.setLineWrap(false);
            NoteMenu.getJM().setEnabled(true);
        }
    }
}
