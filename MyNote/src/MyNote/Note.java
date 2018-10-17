package MyNote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Note extends JFrame {
    private NoteWriteArea _noteWriterArea=new NoteWriteArea(this);
    private NoteStatus _toolBar=new NoteStatus(_noteWriterArea);
    private NoteMenu _noteMenu=new NoteMenu(this,_noteWriterArea,_toolBar);

    private BorderLayout _borderLayout=new BorderLayout();
    private JScrollPane _scroll=new JScrollPane(_noteWriterArea);

    public Note(){
        super();

        //_noteWriterArea

        //
        this.setLayout(_borderLayout);
        this.add(_noteMenu,BorderLayout.NORTH);
        this.add(_scroll,BorderLayout.CENTER);
        this.add(_toolBar,BorderLayout.SOUTH);

        this.setTitle("Husky Notepad");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Note.this.CloseWindow();
            }
        });

        this.setSize(600,600);
        this.setLocation(300,100);
        this.setVisible(true);
    }

    public void CloseWindow(){
        //如果文字改动

        dispose();
    }

    public Note GETSElf(){
        return this;
    }

    public NoteMenu GetMenu(){
        return _noteMenu;
    }

}
