package com.cm4j.test.jface.swt.d_event;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EventDemo {

	private Text logText;

	public EventDemo() {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		shell.setLayout(layout);
		shell.setText("Event demo");

		Label label1 = new Label(shell, SWT.RIGHT);
		label1.setText("Text1:");
		Text text1 = new Text(shell, SWT.NONE);

		// 接受按键时间，并记录到logText中
		text1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Text t = getLogText();
				String s = t.getText();
				t.setText(s + String.valueOf(e.character));
			}
		});

		Label label2 = new Label(shell, SWT.RIGHT);
		label2.setText("Text2:");
		Text text2 = new Text(shell, SWT.NONE);
		text2.setEditable(false);
		text2.setBackground(new Color(display, 255, 255, 255));
		// 设置text2 = logText
		setLogText(text2);

		shell.pack();
		shell.open();

		while (!display.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	public static void main(String[] args) {
		new EventDemo();
	}

	public Text getLogText() {
		return logText;
	}

	public void setLogText(Text logText) {
		this.logText = logText;
	}
}
