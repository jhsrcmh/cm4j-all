package com.cm4j.test.spring.extend_point.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

@Service
public class AnimalSpeakEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public AnimalSpeakEvent(Object source) {
        super(source);
    }
    
    private Object[] selfDefinedArgs;

    public AnimalSpeakEvent(Object source, Object... selfDefinedArgs) {
        super(source);
        this.selfDefinedArgs = selfDefinedArgs;
    }

    public Object[] getSelfDefinedArgs() {
        return selfDefinedArgs;
    }

}
