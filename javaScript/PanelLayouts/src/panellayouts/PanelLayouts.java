/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panellayouts;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 *
 * @author frice
 */
public class PanelLayouts extends JFrame {
    
    static final String gapList[] = {"0", "10", "15", "20"};
    final static int maxGap = 20;
    JComboBox horGapComboBox;
    JComboBox verGapComboBox;
    JButton applyButton = new JButton("Apply gaps");
    GridLayout experimentLayout = new GridLayout(0,2);

    public PanelLayouts(String name){
        super(name);
        setResizable(false);
    }
    
}
