package com.example.season6.Objects;

public class E04_Object {
    String name , message , search;
    int id , seen;
    Boolean multiAction = false;

    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}

    public void setMessage(String message){this.message = message;}
    public String getMessage(){return this.message;}

    public void setId(int id){this.id = id;}
    public int getId(){return this.id;}

    public void setSeen(int seen){this.seen = seen;}
    public int getSeen(){return this.seen;}

    public void setSearch(String search){
        this.search = search;
    }
    public String getSearch(){
        return this.search;
    }

    public void setMultiAction(Boolean multiAction){
        this.multiAction = multiAction;
    }
    public Boolean getMultiAction(){return this.multiAction;}


}
