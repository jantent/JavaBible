package com.current.future;

/**
 * @author: tangJ
 * @Date: 2018/11/7 10:28
 * @description:
 */
public class MsgResult {
    private String name;

    private int goal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    @Override
    public String toString() {
        return "MsgResult{" +
                "name='" + name + '\'' +
                ", goal=" + goal +
                '}';
    }
}
