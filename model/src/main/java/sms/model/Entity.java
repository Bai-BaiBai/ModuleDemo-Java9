package sms.model;

/**
 * 所有领域对象的父类，只包含id
 */
public abstract class Entity {

    protected String id;

    public Entity(String id) {
        this.id = id;
    }
    public Entity(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
