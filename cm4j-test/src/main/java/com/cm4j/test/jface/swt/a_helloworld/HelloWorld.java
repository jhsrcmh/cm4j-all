package com.cm4j.test.jface.swt.a_helloworld;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * <pre>
 * 1.创建一个 Display 对象   
 * 2.创建一个或者多个 Shell 对象，你可以认为 Shell 代表了程序的窗口。   
 * 3.在 Shell 内创建各种部件（widget）   
 * 4.对各个部件进行初始化（外观，状态等），同时为各种部件的事件创建监听器（listener）   
 * 5.调用 Shell 对象的 open()方法以显示窗体   
 * 6.各种事件进行监听并处理，直到程序发出退出消息   
 * 7.调用 Display 对象的 dispose()方法以结束程序。
 * </pre>
 * 
 * @author yanghao
 * 
 */
public class HelloWorld {

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell();
        shell.setSize(300, 200);
        
        Text helloText = new Text(shell, SWT.CENTER);
        helloText.setText("Hello World");
        helloText.pack();

        // shell.pack();
        shell.open();

        while (!shell.isDisposed()) {
            if (display.readAndDispatch()) {
                display.sleep();
            }
        }

        display.dispose();
    }
}
