package jc.dev.docker.manager;

import javax.swing.tree.DefaultMutableTreeNode;

public class ActionTreeNode extends DefaultMutableTreeNode {
    final Action action;

    public ActionTreeNode(Action action) {
        super(action.getName());
        this.action = action;
    }

    public Action getAction() {
        return this.action;
    }
}
