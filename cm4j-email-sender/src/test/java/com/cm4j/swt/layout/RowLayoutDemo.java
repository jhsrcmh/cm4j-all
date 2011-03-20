package com.cm4j.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * RowLayout的功能和FillLayout差不多。只是它和FillLayout的最大区别是每个控件并不一定是一样大小。而且RowLayout是按行排列
 * ，这一点和FillLayout是不同的。在一行排满后，就从下一行开始排列。和RowLayout配合使用的还有一个RowData类。
 * 这个类可以设置每一个控件的大小。
 * 
 * @author Administrator
 * 
 */
public class RowLayoutDemo {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.DIALOG_TRIM);
		shell.setText("RowLayout演示");
		shell.setSize(220, 200);
		// 将Shell的布局设置成RowLayout
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.spacing = 30;
		layout.marginLeft = 30;
		layout.marginTop = 30;
		shell.setLayout(layout);
		RowData rowData = new RowData();
		rowData.height = 50;
		rowData.width = 100;
		// 向shell添加控件
		Button button1 = new Button(shell, SWT.PUSH);
		button1.setText("按钮1");
		button1.setLayoutData(rowData);
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
