package com.cm4j.swt.ve;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class VEPExample {

	private Shell _shell = null; // @jve:decl-index=0:visual-constraint="-137,4"
	private Text text = null;

	/**
	 * @param args
	 */
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

	/**
	 * This method initializes _shell
	 */
	private void create_shell() {
		_shell = new Shell();
		_shell.setText("罗罗邮件发送系统");
		_shell.setLayout(new GridLayout());
		_shell.setSize(new Point(800, 600));
		text = new Text(_shell, SWT.BORDER);
	}

}
