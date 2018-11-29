package com.prop;

/**
 * @author: tangJ
 * @Date: 2018/11/29 14:20
 * @description:
 */
public abstract class IConfig {


    protected IConfig(){
        init();
    }

    public void save(){

    };

    protected void init(){
        ConfigStore.getFromStore(this);
    }

}
