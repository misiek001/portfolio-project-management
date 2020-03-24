package com.mbor.dataloader;

import javax.persistence.PostRemove;

public class RemoveLogger {

    @PostRemove
    public void listen(Object o){
        System.out.println(o);
    }

}
