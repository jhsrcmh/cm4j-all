package com.cm4j.swt.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

/**
 * TabFolder的用法
 * 
 * 我们怎么设定各个标签呢？ 可以使用 TabItem 的setControl 方法设定标签页上的控件。
 * 但是这样又出现了一个疑问，setControl中的参数不是数组，如果我们想设定好几个控件应该怎么办呢？ 这个时候就用得上Composite
 * 了。我们可以首先建一个 Composite 实例，然后在里面添加控件， 再把这个 Composite 实例本身作为参数传递给 TabItem 的
 * setControl 方法。
 * 
 * @author Administrator
 * 
 */
public class TabFolderDemo {

	public static void main(String[] args) {
		Display display = new Display();

		Shell shell = new Shell(display, SWT.DIALOG_TRIM);
		shell.setLayout(new RowLayout(SWT.HORIZONTAL));
		shell.setText("TabFolder Demo");

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setLayout(new FillLayout());
		TabItem ti = new TabItem(tabFolder, SWT.NONE);
		ti.setText("A simple item");
		// 建一个 Composite 实例，然后在里面添加控件
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		for (int i = 0; i < 3; i++) {
			Label label = new Label(composite, SWT.NONE);
			new Text(composite, SWT.BORDER);
			label.setText("TEXT" + i + ":");
		}
		// 使用 TabItem 的setControl 方法设定标签页上的控件
		ti.setControl(composite);

		TabItem ti2 = new TabItem(tabFolder, SWT.NONE);
		ti2.setText("A simple item2");
		Composite composite2 = new Composite(tabFolder, SWT.NONE);
		composite2.setLayout(new GridLayout(2, true));
		for (int i = 0; i < 3; i++) {
			Label label = new Label(composite2, SWT.NONE);
			new Text(composite2, SWT.BORDER);
			label.setText("TEXT" + i + ":");
		}

		ti2.setControl(composite2);

		shell.pack();
		shell.open();

		while (!display.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
