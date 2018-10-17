package MyNote;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

public class NoteStatus extends JToolBar {
    private NoteWriteArea _TextArea;
    private int Xp=1;
    private int Yp=1;
    private int pos;
    private JLabel _jl=new JLabel("第"+Yp+"行，第"+Xp+"列");

    public NoteStatus(NoteWriteArea _parentText){
        super();

        setVisible(false);
        _TextArea=_parentText;
        Add_Listener();

        add(_jl);
    }

    private void Add_Listener() {
        _TextArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                pos=_TextArea.getCaretPosition();
                try {
                    Yp=_TextArea.getLineOfOffset(pos)+1;
                    Xp=pos-_TextArea.getLineStartOffset(Yp-1)+1;
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
                _jl.setText("第"+Yp+"行，第"+Xp+"列");
            }
        });
    }
}
