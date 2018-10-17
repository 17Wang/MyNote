package MyNote;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NotePopup extends JPopupMenu {

    private Note _parentFrame;
    private NoteWriteArea _parentText;

    //Search功能里不得不需要的对象
    private String _searchString;
    private int _searchLength;
    private ArrayList<Integer> _position=new ArrayList<Integer>();
    private int _index_begin=-1;

    //Replace功能里不得不需要的对象
    private String _replaceString;
    private int _replaceLength;
    private String _changeString;
    private int _changeLength;
    private boolean can_change=false;

    private JDialog jd;
    private JDialog jd0;


    public NotePopup(Note _frame,NoteWriteArea area){
        super();

        _parentFrame=_frame;
        _parentText=area;


    }

    public void Search(){
        //父类界面==================================================================
        jd=new JDialog(_parentFrame);
        jd.setAlwaysOnTop(true);
        jd.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        jd.setSize(450,180);
        jd.setResizable(false);
        jd.setLocation(_parentFrame.getX()+100,_parentFrame.getY()+100);
        jd.setVisible(false);

        //控件们===================================================================
        JLabel _jl=new JLabel("查找内容：");
        JTextField _jtf=new JTextField(20);

        JButton _fineNext=new JButton("查找下一个");
        JButton _quit=new JButton("取消");

        JCheckBox _jrb=new JCheckBox("区分大小写");

        JPanel _jp=new JPanel();
        _jp.setBorder(BorderFactory.createTitledBorder("方向"));
        JRadioButton _up=new JRadioButton("向上");
        JRadioButton _down=new JRadioButton("向下");
        ButtonGroup _bg=new ButtonGroup();
        _down.setSelected(true);
        _bg.add(_up);
        _bg.add(_down);
        _jp.setLayout(new FlowLayout());
        _jp.add(_up);
        _jp.add(_down);

        //图形化==================================================================
        jd.setLayout(null);

        _jl.setSize(65,20);
        _jl.setLocation(10,10);
        jd.add(_jl);

        _jtf.setSize(220,25);
        _jtf.setLocation(80,10);
        jd.add(_jtf);

        _fineNext.setSize(110,25);
        _fineNext.setLocation(315,10);
        _fineNext.setEnabled(false);
        jd.add(_fineNext);

        _quit.setSize(110,25);
        _quit.setLocation(315,40);
        jd.add(_quit);

        _jrb.setSize(120,35);
        _jrb.setLocation(10,100);
        jd.add(_jrb);

        _jp.setSize(150,70);
        _jp.setLocation(150,60);
        jd.add(_jp);

        jd.setTitle("查找");

        /*===============================================================
                                 功能实现
          ===============================================================*/
        //

        _jtf.getDocument().addDocumentListener(new DocumentListener() {
            String _text;

            String tmp_str=null;
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(e.getDocument().getLength()!=0)
                    _fineNext.setEnabled(true);
                try {
                    _searchString=e.getDocument().getText(0,e.getDocument().getLength());
                    _searchLength=e.getDocument().getLength();

                    //======================================================

                    if(tmp_str!=_searchString) {
                        tmp_str = new String(_searchString);

                        _text = _parentText.getText();
                        _position.clear();
                        while (true) {
                            _index_begin = _text.indexOf(_searchString, _index_begin + 1);//定位第一位是0
                            if (_index_begin >= 0) {
                                Integer tmp = _index_begin;
                                _position.add(tmp);
                            }
                            else
                                break;
                        }
                    }
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(e.getDocument().getLength()==0)
                    _fineNext.setEnabled(false);
                try {
                    _searchString=e.getDocument().getText(0,e.getDocument().getLength());
                    _searchLength=e.getDocument().getLength();
                    //======================================================

                    if(tmp_str!=_searchString) {
                        tmp_str = new String(_searchString);


                        _text = _parentText.getText();
                        _position.clear();
                        while (true) {
                            _index_begin = _text.indexOf(_searchString, _index_begin + 1);//定位第一位是0
                            if (_index_begin < 0 || _searchString.equals("")) {
                                break;
                            }
                            else
                            {
                                Integer tmp = _index_begin;
                                _position.add(tmp);
                            }

                        }
                    }
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });


        //查找下一个 按钮
        _fineNext.addActionListener(new ActionListener() {
            int index=-1;

            @Override
            public void actionPerformed(ActionEvent e) {
                int _length=_position.size();
                if(_down.isSelected() && index<_length-1)
                {
                    index++;
                    _parentText.setSelectionStart(_position.get(index));
                    _parentText.setSelectionEnd(_position.get(index)+_searchLength);
                }

                else if(_up.isSelected() && index>0)
                {
                    index--;
                    _parentText.setSelectionStart(_position.get(index));
                    _parentText.setSelectionEnd(_position.get(index)+_searchLength);
                }


            }
        });
        //取消 按钮
        _quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.setVisible(false);
            }
        });

        //区分大小写按钮

    }

    public void Replace(){
        //父类界面==================================================================
        jd0=new JDialog(_parentFrame);
        jd0.setAlwaysOnTop(true);

        jd0.setSize(450,200);
        jd0.setResizable(false);
        jd0.setVisible(false);
        jd0.setLocation(_parentFrame.getX()+100,_parentFrame.getY()+100);

        //控件们===================================================================
        JLabel _jl=new JLabel("查找内容：");
        JLabel _jl2=new JLabel("替换为：");
        JTextField _jtf=new JTextField(20);
        JTextField _jtf2=new JTextField(20);


        JButton _fineNext=new JButton("查找下一个");
        JButton _change=new JButton("替换");
        JButton _changeAll=new JButton("全部替换");
        _fineNext.setEnabled(false);
        _change.setEnabled(false);
        _changeAll.setEnabled(false);

        JButton _quit=new JButton("取消");

        JCheckBox _jrb=new JCheckBox("区分大小写");



        //图形化==================================================================
        jd0.setLayout(null);

        _jl.setSize(65,20);
        _jl.setLocation(10,10);
        jd0.add(_jl);

        _jl2.setSize(65,20);
        _jl2.setLocation(10,40);
        jd0.add(_jl2);

        _jtf.setSize(220,25);
        _jtf.setLocation(80,10);
        jd0.add(_jtf);

        _jtf2.setSize(220,25);
        _jtf2.setLocation(80,40);
        jd0.add(_jtf2);

        _fineNext.setSize(110,25);
        _fineNext.setLocation(315,10);
        jd0.add(_fineNext);

        _change.setSize(110,25);
        _change.setLocation(315,40);
        jd0.add(_change);

        _changeAll.setSize(110,25);
        _changeAll.setLocation(315,70);
        jd0.add(_changeAll);

        _quit.setSize(110,25);
        _quit.setLocation(315,100);
        jd0.add(_quit);

        _jrb.setSize(120,35);
        _jrb.setLocation(10,100);
        jd0.add(_jrb);

        jd0.setTitle("替换");

        /*功能实现===============================================================*/
        _jtf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(e.getDocument().getLength()!=0)
                {
                    _fineNext.setEnabled(true);
                    //_change.setEnabled(true);
                    //_changeAll.setEnabled(true);
                }

                try {
                    _replaceString=e.getDocument().getText(0,e.getDocument().getLength());
                    _replaceLength=e.getDocument().getLength();

                } catch (BadLocationException e2) {
                    e2.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(e.getDocument().getLength()==0)
                {
                    _fineNext.setEnabled(false);
                    _change.setEnabled(false);
                    _changeAll.setEnabled(false);
                }

                try {
                    _replaceString=e.getDocument().getText(0,e.getDocument().getLength());
                    _replaceLength=e.getDocument().getLength();

                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        _jtf2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(e.getDocument().getLength()!=0)
                {
                    _change.setEnabled(true);
                    _changeAll.setEnabled(true);
                }

                try {
                    _changeString=e.getDocument().getText(0,e.getDocument().getLength());
                    _changeLength=e.getDocument().getLength();

                } catch (BadLocationException e2) {
                    e2.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(e.getDocument().getLength()==0)
                {
                    _change.setEnabled(false);
                    _changeAll.setEnabled(false);
                }

                try {
                    _changeString=e.getDocument().getText(0,e.getDocument().getLength());
                    _changeLength=e.getDocument().getLength();

                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        //查找下一个 按钮
        _fineNext.addActionListener(new ActionListener() {
            private int index=-1;
            @Override
            public void actionPerformed(ActionEvent e) {
                index=_parentText.getCaretPosition();
                if(_parentText.getText().indexOf(_replaceString,index+1)!=-1)
                {
                    can_change=true;
                    index=_parentText.getText().indexOf(_replaceString,index+1);
                    _parentText.setSelectionStart(index);
                    _parentText.setSelectionEnd(index+_replaceLength);
                }

            }
        });

        //替换 按钮
        _change.addActionListener(new ActionListener() {
            private int _changeindex0;
            private int _changeindex1;
            @Override
            public void actionPerformed(ActionEvent e) {
                _changeindex0=_parentText.getSelectionStart();
                _changeindex1=_parentText.getSelectionEnd();
                if(_changeindex0!=_changeindex1)
                    _parentText.replaceRange(_changeString,_changeindex0,_changeindex1);
            }
        });

        //全部替换 按钮
        _changeAll.addActionListener(new ActionListener() {
            private int index=-1;
            private int _changeindex0;
            private int _changeindex1;
            @Override
            public void actionPerformed(ActionEvent e) {
                index=-1;
                while(_parentText.getText().indexOf(_replaceString,index+1)!=-1)
                {
                    index=_parentText.getText().indexOf(_replaceString,index+1);
                    _parentText.setSelectionStart(index);
                    _parentText.setSelectionEnd(index+_replaceLength);
                    _changeindex0=_parentText.getSelectionStart();
                    _changeindex1=_parentText.getSelectionEnd();
                    if(_changeindex0!=_changeindex1)
                        _parentText.replaceRange(_changeString,_changeindex0,_changeindex1);

                }
            }
        });

        //取消 按钮
        _quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd0.setVisible(false);
            }
        });

        //区分大小写按钮


    }
}
