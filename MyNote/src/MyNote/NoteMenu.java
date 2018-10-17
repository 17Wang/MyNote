package MyNote;

import javax.swing.*;
import NoteMenu.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteMenu extends JMenuBar {
    private Note _parentFrame;
    private NoteWriteArea _parentText;
    private NoteStatus _parentStatus;

    private MenuFile jm1;
    private MenuEditor jm2;
    private MenuForm jm3;
    private JMenu jm4;
    private MenuHelp jm5;
    private static JCheckBox _jcb=new JCheckBox("状态栏");


    NoteMenu(Note frame,NoteWriteArea jTextPane,NoteStatus status){
        super();

        _parentFrame=frame;
        _parentText=jTextPane;
        _parentStatus=status;

        jm1=new MenuFile("文件",_parentFrame,_parentText);
        jm2=new MenuEditor("编辑",_parentFrame,_parentText);
        jm3=new MenuForm("格式",_parentFrame,_parentText);
        jm4=new JMenu("查看");
        jm5=new MenuHelp("帮助");

        Initial_Jm4();
        this.add(jm1);
        this.add(jm2);
        this.add(jm3);
        this.add(jm4);
        this.add(jm5);
    }

    private void Initial_Jm4(){
        _jcb.setEnabled(false);
        _jcb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_jcb.isSelected())
                    _parentStatus.setVisible(true);
                else
                    _parentStatus.setVisible(false);
            }
        });
        jm4.add(_jcb);
    }

    public static JCheckBox getJM(){
        return _jcb;
    }

    public MenuEditor GetEditor(){
        return jm2;
    }
}
