package com.cm4j.drools.agendafilter;

import org.drools.runtime.rule.Activation;
import org.drools.runtime.rule.AgendaFilter;

public class AgendaFilterImpl implements AgendaFilter {

	private String startPrefix;
	
	public AgendaFilterImpl(String startPrefix) {
		super();
		this.startPrefix = startPrefix;
	}

	@Override
	public boolean accept(Activation activation) {
		String ruleName = activation.getRule().getName();
		if (ruleName.startsWith(this.startPrefix)) {
			return true;
		}
		return false;
	}

}
