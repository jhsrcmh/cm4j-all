package com.cm4j.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class GridLayoutDemo {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("GridLayout演示");
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.makeColumnsEqualWidth = true;
		shell.setLayout(layout);
		// 建立左上角的按钮
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = 200; // 按钮的初始宽度为200
		Button one = new Button(shell, SWT.PUSH);
		one.setText("按钮1");
		one.setLayoutData(data);
		// 建立一个Composite对象，并在上面放三个按钮
		Composite composite = new Composite(shell, SWT.NONE);
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 2; // Composite的占两个Cell
		composite.setLayoutData(data);
		layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 15;
		layout.marginRight = 150;
		composite.setLayout(layout);

		// 建立第二个按钮
		data = new GridData(GridData.FILL_BOTH);
		Button two = new Button(composite, SWT.PUSH);
		two.setText("按钮2");
		two.setLayoutData(data);
		// 建立第三个按钮
		data = new GridData(GridData.HORIZONTAL_ALIGN_END);
		Button three = new Button(composite, SWT.PUSH);
		three.setText("按钮3");
		three.setLayoutData(data);
		// 建立第四个按钮
		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		Button four = new Button(composite, SWT.PUSH);
		four.setText("按钮4");
		four.setLayoutData(data);
		// 建立下面的一个长按钮
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = 3;
		data.verticalSpan = 2;
		data.heightHint = 150;
		Button five = new Button(shell, SWT.PUSH);
		five.setText("按钮5");
		five.setLayoutData(data);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
