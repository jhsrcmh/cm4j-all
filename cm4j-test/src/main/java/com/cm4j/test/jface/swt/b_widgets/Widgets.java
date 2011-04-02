package com.cm4j.test.jface.swt.b_widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Widgets {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(SWT.SHELL_TRIM);
        shell.setSize(300, 200);

        RowLayout layout = new RowLayout(SWT.VERTICAL);
        shell.setLayout(layout);
        shell.setText("文档");

        // 标签
        Label label = new Label(shell, SWT.CENTER);
        label.setText("Label Demo");
        // 分隔符形式的标签~
        new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);

        // 按钮
        Button pushbutton = new Button(shell, SWT.PUSH | SWT.CENTER);
        pushbutton.setText("SWT.PUSH");

        // 默认选中 它在被按下以后会保持按下的形状（而不会弹起来），直到鼠标再次在上面按一下才会回复弹起的形状。
        Button togglebutton = new Button(shell, SWT.TOGGLE | SWT.LEFT);
        togglebutton.setText("SWT.TOGGLE");
        togglebutton.setSelection(true);

        // 带箭头的按钮
        new Button(shell, SWT.ARROW | SWT.LEFT);

        // 复选
        Button checkbox = new Button(shell, SWT.CHECK);
        checkbox.setText("SWT.CHECK");

        // 单选
        Button radio = new Button(shell, SWT.RADIO);
        radio.setText("SWT.RADIO");
        radio.setSelection(true);

        shell.pack();
        shell.open();

        while (!shell.isDisposed()) {
            if (display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }
}
