package NoteMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuHelp extends JMenu {
    private JMenuItem _help = new JMenuItem("     查看帮助");
    private JMenuItem _log = new JMenuItem("     关于笔记本");
    private ImageIcon _icon = new ImageIcon("H:\\IDEA project java\\MyNote\\src\\NoteMenu\\timg.jpg");


    //初始化
    public MenuHelp(String name) {
        super(name);
        this.add(_help);
        this.add(_log);
        Image img=_icon.getImage();
        img=img.getScaledInstance(100,100,Image.SCALE_DEFAULT);
        _icon.setImage(img);

        _help.addActionListener(_listner);
        _log.addActionListener(_listner);
    }

    //监听器
    private MenuHelpListner _listner = new MenuHelpListner();

    private class MenuHelpListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == _log)
            {
                JOptionPane.showMessageDialog(null, "Husky Notepad version 1.0.0 内测版\n\n\n\n\n开发者拥有一切解释权", "关于笔记本", 0, _icon);
            }
            if(e.getSource()==_help)
            {

            }
        }
    }
}
