package views.settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import app.AppSettings;
import utils.PopupListener;
import utils.WindowUtils;

public class Settings extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public Settings() {
		setResizable(true);
		setModal(true);
		setIconImage(WindowUtils.getIconImage());
		getContentPane().setBackground(new Color(73, 73, 73));
		setBackground(new Color(73, 73, 73));
		setTitle("Settings");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(73, 73, 73));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				JTree tree = new JTree();
				tree.setForeground(Color.WHITE);
				tree.setFont(new Font("Times New Roman", Font.PLAIN, 14));
				tree.setEditable(false);
				tree.setModel(new CustomTreeModel());
				tree.setBackground(new Color(73, 73, 73));
				tree.setCellRenderer(new CustomTreeCellRenderer());
				JPopupMenu popup = new JPopupMenu();
				JMenuItem item;
				
				item = new JMenuItem("Add");
				item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	if (tree.getSelectionCount() == 0) {
                    		JOptionPane.showMessageDialog(tree, "Select a category first.", "Error", JOptionPane.ERROR_MESSAGE);
                    	} else {
                    		JOptionPane.showMessageDialog(null, "Add");
                    	}
                    }
                });
				popup.add(item);
				
				item = new JMenuItem("Edit");
				item.addActionListener(null);
				popup.add(item);
				
				item = new JMenuItem("Delete");
				item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	if (tree.getSelectionCount() == 0) {
                    		JOptionPane.showMessageDialog(tree, "Select a category or setting first.", "Error", JOptionPane.ERROR_MESSAGE);
                    	} else {
                    		((CustomTreeModel)tree.getModel()).remove(tree.getSelectionPath());
                    		tree.updateUI();
                    	}
                    }
                });
				popup.add(item);
				tree.addMouseListener(new PopupListener(popup));
				scrollPane.setViewportView(tree);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(73, 73, 73));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	private class CustomTreeModel implements TreeModel {
		private AppSettings settings;
		private String root = "App Settings";
		
		public CustomTreeModel() {
			settings = AppSettings.getInstance();
		}
		
		public Object getRoot() {
			return root;
		}

		public int getChildCount(Object parent) {
			if (parent.equals(root)) return settings.size();
			return settings.categorySize(parent.toString());
		}

		public Object getChild(Object parent, int index) {
			if (parent.equals(root))
				return settings.getCategories().get(index);
			return settings.getCategory(parent.toString()).keySet().toArray()[index].toString();
		}

		public int getIndexOfChild(Object parent, Object child) {
			if (parent.equals(root))
                return settings.getCategories().indexOf(child);
            Object[] settingsInCategory = settings.getCategory(parent.toString()).keySet().toArray();
			for (int i = 0; i < settingsInCategory.length; i++) {
				if (settingsInCategory[i].equals(child)) {
					return i;
				}
			}
			return -1;
        }

		public boolean isLeaf(Object node) {
			if (node.equals(root)) return false;
            if (settings.getCategory(node.toString()) == null) return true;
            return false;
		}
		
		public void remove(TreePath treePath) {
			Object path[] = treePath.getPath();
			ArrayList<String> a =  new ArrayList<String>();
			for (Object o : path) {
                a.add(o.toString());
            }
			System.out.println(path.length + " " + String.join("; ", a));
			if (path.length == 1) {
                settings.clear();
            } else if (path.length == 2) {
				settings.removeCategory(path[1].toString());
			} else if (path.length == 3) {
				settings.removeSetting(path[1].toString(),
						path[2].toString());
			}
			System.out.println(settings.getCategories());
		}

		@Override
		public void valueForPathChanged(TreePath path, Object newValue) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addTreeModelListener(TreeModelListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeTreeModelListener(TreeModelListener l) {
			
		}		
	}

	private class CustomTreeCellRenderer implements TreeCellRenderer {
		
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			JLabel label = new JLabel();
			
			
			label.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					JOptionPane.showMessageDialog(null, "CLIKKK");
				}
			});
			
			label.setText(value.toString());
			label.setForeground(Color.WHITE);
			label.setOpaque(true);
			label.setBackground(new Color(73, 73, 73));
			label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			if (selected) {
                label.setBackground(new Color(150, 150, 150));
            } 
			if(leaf) {
                label.setIcon(new ImageIcon("./assets/icons/setting.png"));
            } else {
                label.setIcon(new ImageIcon("./assets/icons/settings.png"));
            }
			
			return label;
		}
	}
}


