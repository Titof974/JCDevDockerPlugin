package jc.dev.docker;

import com.intellij.openapi.wm.ToolWindow;
import io.reactivex.internal.util.LinkedArrayList;

import jc.dev.docker.manager.Action;
import jc.dev.docker.manager.ActionTreeNode;
import jc.dev.docker.manager.Container;
import jc.dev.docker.manager.SSH;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class JCDevDocker {
    private JPanel windowContent;
    private SSH ssh;
    private JTree tree;

    public JCDevDocker(ToolWindow toolWindow, SSH ssh) {
        this.ssh = ssh;
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Docker containers");
        List<Container> containers = new ArrayList<>();
        containers.add(new Container("b2fe22f1b884", "www1", "nginx"));
        containers.add(new Container("b2fe22f1b884", "www2", "nginx"));
        containers.add(new Container("b2fe22f1b884", "www3", "nginx"));
        Map<String, List<Action>> actions = new HashMap<>();
        actions.put("www1", new ArrayList<>( Arrays.asList(Action.DOCKER_STOP)));
        DefaultTreeModel model = new DefaultTreeModel(this.buildTree(containers, actions));
        this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        SSH _ssh = ssh;
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                if(e.getClickCount() == 2 && selPath.getPathCount() == 3) {
                    System.out.println(selPath.getPath()[selPath.getPathCount()-2]);
                    String command = ((ActionTreeNode) selPath.getLastPathComponent()).getAction().getCommand();
                    System.out.println(_ssh.execCommand(command));
                }
            }
        };

        this.tree.addMouseListener(ml);

        this.tree.setModel(model);
    }

    public DefaultMutableTreeNode buildTree(List<Container> containers, Map<String, List<Action>> actions) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Docker containers");
        for(Container container : containers) {
            DefaultMutableTreeNode container1 = new DefaultMutableTreeNode(container.getName());
            if (actions.containsKey(container.getName())) {
                this.addActionToContainer(container, container1, actions.get(container.getName()));
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
}
