package com.cm4j.test.designpattern.chain;

public abstract class Handler {
    protected abstract void response(IWoman woman);
    
    public static final int TYPE_FATHER = 1;
    public static final int TYPE_HUSBAND = 2;
    public static final int TYPE_SON = 3;

    private int type;

    private Handler next;

    public Handler(int type) {
        this.type = type;
    }

    public void setNext(Handler handler) {
        this.next = handler;
    }

    public void handleMessage(IWoman woman) {
        if (this.type == woman.getType())
            this.response(woman);
        else if (this.next != null)
            this.next.handleMessage(woman);
        else
            System.out.println("request not match");
    }
}
