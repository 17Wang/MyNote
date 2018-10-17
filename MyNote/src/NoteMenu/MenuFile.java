package NoteMenu;

import MyNote.Note;
import MyNote.NoteWriteArea;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.*;
import java.io.*;

public class MenuFile extends JMenu{
    private JMenuItem _createNew=new JMenuItem("     新建");
    private JMenuItem _openOne=new JMenuItem("     打开...");
    private JMenuItem _save=new JMenuItem("     保存");
    private JMenuItem _saveIn=new JMenuItem("     另存为...");
    private JMenuItem _exit=new JMenuItem("     退出");

    //
    String _initialtext;
    private static final int FILE_IS_NEW=0;
    private static final int FILE_IS_OPEN=1;
    private int FILE_IS_WHAT=FILE_IS_NEW;
    private String FILE_PATH;

    //父类组件——向上走
    private Note _parentFrame;
    private NoteWriteArea _parentText;

    //文件选择器
    JFileChooser _jfc=new JFileChooser();

    //初始化
    public MenuFile(String name,Note frame,NoteWriteArea jTextPane) {
        super(name);

        _parentFrame=frame;
        _parentText=jTextPane;

        this.add(_createNew);
        this.add(_openOne);
        this.add(_save);
        this.add(_saveIn);
        this.add(_exit);

        //共用一个监听器
        _createNew.addActionListener(_listener);
        _openOne.addActionListener(_listener);
        _save.addActionListener(_listener);
        _saveIn.addActionListener(_listener);
        _exit.addActionListener(_listener);

        //初始化
        try {
            _initialtext=_parentText.getDocument().getText(0,_parentText.getDocument().getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        //文件窗口选择器
        _jfc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                String name=f.getName();
                return f.isDirectory()||name.toLowerCase().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "文本文档(*.txt)";
            }
        });
    }

    //监听器
    private MenuFileListener _listener=new MenuFileListener();
    private class MenuFileListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==_createNew)
                CreateNew();
            if(e.getSource()==_openOne)
                OpenOne();
            if(e.getSource()==_save)
                Save();
            if(e.getSource()==_saveIn)
                SaveIn();
            if(e.getSource()==_exit)
                Quit();
        }
    }

    //新建功能
    void CreateNew(){
        int i=JOptionPane.showConfirmDialog(null,"你确定要新建一个窗口吗？","新建",JOptionPane.YES_NO_OPTION);
        try {
            _initialtext=_parentText.getDocument().getText(0,_parentText.getDocument().getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        if(i==0)
        {
            Note _note=new Note();
            FILE_IS_WHAT=FILE_IS_NEW;
        }
    }

    //打开功能
    void OpenOne(){
        try {
            if(!_parentText.getDocument().getText(0,_parentText.getDocument().getLength()).equals(_initialtext))
            {
                int _option=JOptionPane.showConfirmDialog(_parentFrame,"是否需要保存","保存提示",JOptionPane.YES_NO_CANCEL_OPTION);
                if(_option==JOptionPane.YES_OPTION)
                    Save();
                else if(_option==JOptionPane.CANCEL_OPTION)
                    return;
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        int i=_jfc.showOpenDialog(this);
        _jfc.setMultiSelectionEnabled(false);


        if(i==JFileChooser.APPROVE_OPTION)
        {
            _parentText.setText(null);//
            String _opentxt;
            File file=_jfc.getSelectedFile();

            try {
                FileReader reader=new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                while((_opentxt=br.readLine())!=null)
                {
                    _parentText.append(_opentxt+"\n");
                }
                br.close();
                reader.close();
                _initialtext=_parentText.getDocument().getText(0,_parentText.getDocument().getLength());
                FILE_IS_WHAT=FILE_IS_OPEN;
                FILE_PATH=file.getPath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }


    }

    //保存功能
    void Save(){
        if(FILE_IS_WHAT==FILE_IS_NEW)
        {
            SaveIn();
        }
        else if(FILE_IS_WHAT==FILE_IS_OPEN)
        {
            try {
                Document doc=_parentText.getDocument();
                BufferedWriter fw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH)));
                String []strs=doc.getText(0,doc.getLength()).split("\n");
                for(String str:strs)
                {
                    fw.write(str+"\r\n");
                }

                fw.flush();
                fw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (BadLocationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //另存为功能
    void SaveIn() {
        try {
            int index = _jfc.showSaveDialog(this);
            if (index == JFileChooser.APPROVE_OPTION) {
                File file = _jfc.getSelectedFile();
                if (!file.exists()) {
                    file.createNewFile();
                }
                Document doc = _parentText.getDocument();
                BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getPath())));
                String[] strs = doc.getText(0, doc.getLength()).split("\n");
                for (String str : strs) {
                    fw.write(str + "\r\n");
                }
                fw.flush();
                fw.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //退出
    void Quit(){
        _parentFrame.CloseWindow();
    }
    //...
}

