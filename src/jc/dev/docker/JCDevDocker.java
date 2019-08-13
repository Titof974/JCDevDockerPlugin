package jc.dev.docker;

import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.ToolWindow;

import jc.dev.docker.manager.*;
import jc.dev.docker.manager.Action;
import jc.dev.docker.manager.Container;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class JCDevDocker {
    private JPanel windowContent;
    private SSH ssh;
    private JTree tree;
    private JButton refreshButton;
    private Manager manager;
    DefaultMutableTreeNode root;
    DefaultTreeModel model;

    public JCDevDocker(ToolWindow toolWindow, SSH ssh, Manager manager) {
        this.ssh = ssh;
        this.manager = manager;
        this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        root = new DefaultMutableTreeNode("Docker containers");
        model = new DefaultTreeModel(root);
        this.tree.setModel(model);

        SSH _ssh = ssh;
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                if(e.getClickCount() == 2 && selPath.getPathCount() == 3) {
                    System.out.println(selPath.getPath()[selPath.getPathCount()-2]);
                    List<String> commands = ((ActionTreeNode) selPath.getLastPathComponent()).getAction().getCommands();
                    manager.execCommands(commands);
                    drawTree();
                }
            }
        };

        this.tree.addMouseListener(ml);
        tree.setCellRenderer(new CustomTreeCellRenderer());

        this.refreshButton.addActionListener((action) -> {
            this.drawTree();
        });

        this.drawTree();
    }

    public void drawTree() {
        List<Container> containers = manager.filterContainersWithActions();

        tree.setModel(null);
        this.root = this.buildTree(containers);
        model = new DefaultTreeModel(root);
        tree.setModel(this.model);
    }

    public DefaultMutableTreeNode buildTree(List<Container> containers) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new NodeIcon("Docker Containers", null));
        for(Container container : containers) {
            DefaultMutableTreeNode container1 = new DefaultMutableTreeNode(new NodeIcon(container.getName(), container.getStatus() ? "green.png" : "red.png"));
            for (Action action : container.getActions()) {
                container1.add(new ActionTreeNode(action));
            }
            root.add(container1);
        }
        return root;
    }

    public void addActionToContainer(Container container, DefaultMutableTreeNode node, List<Action> actions) {
        for(Action action : actions) {
            ActionTreeNode actionNode = new ActionTreeNode(action.setParams(container.toMap()));
            node.add(actionNode);
        }
    }

    public JPanel getContent() {
        return windowContent;
    }

    public class NodeIcon {
        final String name;
        final String icon;

        public NodeIcon(String name, String icon) {
            this.name = name;
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public String getIcon() {
            return icon;
        }
    }

    class CustomTreeCellRenderer implements TreeCellRenderer {
        private JLabel label;

        CustomTreeCellRenderer() {
            label = new JLabel();
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            Object o = ((DefaultMutableTreeNode) value).getUserObject();
            if (value instanceof ActionTreeNode) {
                label.setIcon(null);
                label.setText("" + value);
            }
            else if (o instanceof NodeIcon) {
                NodeIcon nodeIcon = (NodeIcon) o;
                if (nodeIcon.getIcon() == null) {
                    label.setIcon(null);
                    label.setText(nodeIcon.getName());
                    return label;
                }
                label.setIcon(IconLoader.getIcon("/icons/" + nodeIcon.getIcon()));
                label.setText(nodeIcon.getName());
            }
            else {
                label.setIcon(null);
                label.setText("" + value);
            }
            return label;
        }
    }

}
