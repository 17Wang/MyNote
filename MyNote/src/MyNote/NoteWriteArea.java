package MyNote;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.color.ICC_Profile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NoteWriteArea extends JTextArea {

    private Note _note;
    NoteWriteArea(Note note){
        super();

        _note=note;
        //制表符设置成2个字符
        this.setTabSize(2);
        this.setText(null);

        this.addMouseListener(new MouseAdapter() {


            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(e.isMetaDown())
                {
                    showPopupMenu(e.getComponent(), e.getX(), e.getY());

                }

            }
        });
    }


    public void showPopupMenu(Component invoker, int x, int y) {
        // 创建 弹出菜单 对象
        JPopupMenu popupMenu = new JPopupMenu();

        // 创建 一级菜单
        JMenuItem copyMenuItem = new JMenuItem("复制");
        JMenuItem pasteMenuItem = new JMenuItem("粘贴");
        JMenu editMenu = new JMenu("编辑");   // 需要 添加 二级子菜单 的 菜单，使用 JMenu

        // 创建 二级菜单
        JMenuItem findMenuItem = new JMenuItem("查找");
        JMenuItem replaceMenuItem = new JMenuItem("替换");
        // 添加 二级菜单 到 "编辑"一级菜单
        editMenu.add(findMenuItem);
        editMenu.add(replaceMenuItem);

        // 添加 一级菜单 到 弹出菜单
        popupMenu.add(copyMenuItem);
        popupMenu.add(pasteMenuItem);
        popupMenu.addSeparator();       // 添加一条分隔符
        popupMenu.add(editMenu);

        // 添加菜单项的点击监听器
        findMenuItem.addActionListener(new ActionListener() {
            private boolean _czfirst=true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if(_czfirst)
                    _note.GETSElf().GetMenu().GetEditor().Search();
                _note.GETSElf().GetMenu().GetEditor().jd.setVisible(true);
                _czfirst=false;
            }
        });

        replaceMenuItem.addActionListener(new ActionListener() {
            private boolean _thfirst=true;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_thfirst)
                    _note.GETSElf().GetMenu().GetEditor().Replace();
                _note.GETSElf().GetMenu().GetEditor().jd0.setVisible(true);
                _thfirst=false;
            }
        });

        // ......

        // 在指定位置显示弹出菜单
        popupMenu.show(invoker, x, y);
    }

}
