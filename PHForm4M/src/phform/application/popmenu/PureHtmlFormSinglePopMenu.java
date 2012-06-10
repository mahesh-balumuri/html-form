package phform.application.popmenu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import phform.application.canvas.PureHtmlFormSingleCanvas;
import phform.application.dialog.PureHtmlFormEleProperDialog;
import phform.application.dialog.PureHtmlFormFontProperDialog;
import phform.application.dialog.PureHtmlFormSingleSplitColDialog;
import phform.application.dialog.PureHtmlFormSingleSplitDialog;
import phform.application.dialog.PureHtmlFormSingleSplitRowDialog;
import phform.application.frame.PureHtmlFormSingleFrame;
import phform.application.html.FormObject;
import phform.application.html.bean.FontBean;
import phform.application.html.bean.TableBean;
import phform.application.html.bean.TdBean;
import phform.application.html.util.TdUtil;

public class PureHtmlFormSinglePopMenu extends JPopupMenu
{
    private PureHtmlFormSingleFrame frame;
    
    private JMenu tableSubMenu;
    
    private JMenuItem splitTable;
    
    private JMenuItem mergeTable;
    
    private JMenuItem addTrBelow;
    
    private JMenuItem addTrAbove;
    
    private JMenuItem deleteTr;
    
    private JMenuItem splitRow;
    
    private JMenuItem splitCol;
    
    private JMenuItem property;
    
    private JMenuItem delete;
    
    public PureHtmlFormSinglePopMenu(PureHtmlFormSingleFrame f)
    {
        super();
        frame = f;
        property = new JMenuItem("属性");
        property.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                PureHtmlFormSingleCanvas canvas = frame.getCanvas();
                List beanSelected = canvas.getBeanSelected();
                Object selected = beanSelected.get(0);
                if (selected instanceof FontBean)
                {
                    PureHtmlFormFontProperDialog dialog =
                        new PureHtmlFormFontProperDialog(frame,
                                (FontBean) selected);
                    dialog.show();
                }
                else
                {
                    PureHtmlFormEleProperDialog dialog =
                        new PureHtmlFormEleProperDialog(frame);
                    dialog.show();
                }
            }
        });
        add(property);
        
        tableSubMenu = new JMenu("表格");
        splitTable = new JMenuItem("拆分单元格...");
        splitTable.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                PureHtmlFormSingleSplitDialog dialog =
                    new PureHtmlFormSingleSplitDialog(frame);
                dialog.show();
            }
        });
        tableSubMenu.add(splitTable);
        
        mergeTable = new JMenuItem("合并单元格");
        mergeTable.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                PureHtmlFormSingleCanvas canvas = frame.getCanvas();
                List beanSelected = canvas.getBeanSelected();
                TdBean selected = (TdBean) beanSelected.get(0);
                selected.getTableBean().mergeTd(canvas.getBeanSelected());
                canvas.setSaved(false);
                canvas.getBeanSelected().clear();
                canvas.repaint();
            }
        });
        tableSubMenu.add(mergeTable);
        
        addTrBelow = new JMenuItem("下方增加行");
        addTrBelow.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                PureHtmlFormSingleCanvas canvas = frame.getCanvas();
                List beanSelected = canvas.getBeanSelected();
                TdBean selected = (TdBean) beanSelected.get(0);
                selected.getTableBean().addTrBelow(selected);
                canvas.setSaved(false);
                canvas.repaint();
            }
        });
        tableSubMenu.add(addTrBelow);
        
        addTrAbove = new JMenuItem("上方增加行");
        addTrAbove.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                PureHtmlFormSingleCanvas canvas = frame.getCanvas();
                List beanSelected = canvas.getBeanSelected();
                TdBean selected = (TdBean) beanSelected.get(0);
                selected.getTableBean().addTrAbove(selected);
                canvas.setSaved(false);
                canvas.repaint();
            }
        });
        tableSubMenu.add(addTrAbove);
        
        deleteTr = new JMenuItem("删除行");
        deleteTr.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                PureHtmlFormSingleCanvas canvas = frame.getCanvas();
                List beanSelected = canvas.getBeanSelected();
                TdBean selected = (TdBean) beanSelected.get(0);
                selected.getTableBean().deleteTr(selected);
                if (!selected.getTableBean().valideTable())
                {
                    canvas.removeInvalidTable();
                }
                canvas.setSaved(false);
                canvas.getBeanSelected().clear();
                canvas.repaint();
            }
        });
        tableSubMenu.add(deleteTr);
        
        splitRow = new JMenuItem("拆分行...");
        splitRow.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                PureHtmlFormSingleSplitRowDialog dialog =
                    new PureHtmlFormSingleSplitRowDialog(frame);
                dialog.show();
            }
        });
        tableSubMenu.add(splitRow);
        
        splitCol = new JMenuItem("拆分列...");
        splitCol.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                PureHtmlFormSingleSplitColDialog dialog =
                    new PureHtmlFormSingleSplitColDialog(frame);
                dialog.show();
            }
        });
        tableSubMenu.add(splitCol);
        add(tableSubMenu);
        
        delete = new JMenuItem("删除");
        delete.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                PureHtmlFormSingleCanvas canvas = frame.getCanvas();
                List beanSelected = canvas.getBeanSelected();
                Object selected = beanSelected.get(0);
                if (selected instanceof TableBean)
                {
                    canvas.deleteTable((TableBean) selected);
                }
                else
                {
                    canvas.deleteFormBean((FormObject) selected);
                }
                canvas.getBeanSelected().clear();
                canvas.setSaved(false);
                canvas.repaint();
            }
        });
        add(delete);
    }
    
    @Override
    public void show(Component invoker, int x, int y)
    {
        splitTable.setEnabled(false);
        mergeTable.setEnabled(false);
        addTrBelow.setEnabled(false);
        addTrAbove.setEnabled(false);
        deleteTr.setEnabled(false);
        splitRow.setEnabled(false);
        splitCol.setEnabled(false);
        
        property.setEnabled(false);
        delete.setEnabled(false);
        if (null != frame)
        {
            PureHtmlFormSingleCanvas canvas = frame.getCanvas();
            List beanSelected = canvas.getBeanSelected();
            if (null != beanSelected && !beanSelected.isEmpty())
            {
                if (beanSelected.size() == 1)
                {
                    Object selected = beanSelected.get(0);
                    if (!(selected instanceof TdBean)
                            && !(selected instanceof TableBean))
                    {
                        property.setEnabled(true);
                    }
                    if (selected instanceof TdBean
                            && (((TdBean) selected).diagnoseSplit(2, 1) || ((TdBean) selected)
                                    .diagnoseSplit(1, 2)))
                    {
                        splitTable.setEnabled(true);
                    }
                    if (selected instanceof TdBean
                            && ((TdBean) selected).getTableBean()
                                    .diagnoseAddTrBelow((TdBean) selected))
                    {
                        addTrBelow.setEnabled(true);
                    }
                    if (selected instanceof TdBean
                            && ((TdBean) selected).getTableBean()
                                    .diagnoseAddTrAbove((TdBean) selected))
                    {
                        addTrAbove.setEnabled(true);
                    }
                    if (selected instanceof TdBean
                            && ((TdBean) selected).getTableBean()
                                    .diagnoseTrDelete((TdBean) selected))
                    {
                        deleteTr.setEnabled(true);
                    }
                    if (selected instanceof TdBean
                            && ((TdBean) selected).getTableBean()
                                    .diagnoseSplitRow((TdBean) selected, 2))
                    {
                        splitRow.setEnabled(true);
                    }
                    if (selected instanceof TdBean
                            && ((TdBean) selected).getTableBean()
                                    .diagnoseSplitCol((TdBean) selected, 2))
                    {
                        splitCol.setEnabled(true);
                    }
                    if (!(selected instanceof TdBean))
                    {
                        delete.setEnabled(true);
                    }
                }
                else if (beanSelected.size() > 1)
                {
                    boolean isAllTd = true;
                    for (int i = 0; i < beanSelected.size(); i++)
                    {
                        Object selected = beanSelected.get(i);
                        if (!(selected instanceof TdBean))
                        {
                            isAllTd = false;
                            break;
                        }
                    }
                    if (isAllTd && TdUtil.diagnoseMerge(beanSelected))
                    {
                        mergeTable.setEnabled(true);
                    }
                }
            }
        }
        super.show(invoker, x, y);
    }
    
}
