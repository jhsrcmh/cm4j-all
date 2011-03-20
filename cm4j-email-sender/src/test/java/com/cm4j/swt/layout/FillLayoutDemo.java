package com.cm4j.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * <pre>
 * FillLayout是最简单的布局。它可以将控件横向或纵向进行排列，并且其中每个控件都有同样的宽度或高度。
 * 如果想要Shell上的控件纵向排列，可以在将type属性设置成SWT.VERTICAL 
 * FillLayout layout = new FillLayout(); 
 * layout.type = SWT.VERTICAL; shell.setLayout(layout);
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class FillLayoutDemo {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.DIALOG_TRIM);
		shell.setText("FillLayout演示");
		shell.setSize(400, 300);
		// 设置shell的布局
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);
		// 向shell添加控件
		Button button1 = new Button(shell, SWT.PUSH);
		button1.setText("按钮1");
		Button button2 = new Button(shell, SWT.PUSH);
		button2.setText("按钮2");
		Button button3 = new Button(shell, SWT.PUSH);
		button3.setText("按钮3");
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
