package com.cm4j.test.designpattern.observer.one2one;

public class HanFeiZiImpl implements IHanFeiZi{
	
	/**
	 * @uml.property  name="lisi"
	 * @uml.associationEnd  
	 */
	private ILisi lisi;
	
	public HanFeiZiImpl(){
		this.lisi = new LisiImpl();
	}

	@Override
	public void haveBreakfast() {
		System.out.println("HanFeiZi haveBreakfast()");
		lisi.update("HanFeiZi is having breakfast");
	}

	@Override
	public void haveFun() {
		System.out.println("HanFeiZi haveFun()");
		lisi.update("HanFeiZi is having fun");
	}

}
