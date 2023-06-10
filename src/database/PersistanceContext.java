package database;

import java.util.HashMap;
import java.util.Objects;

public class PersistanceContext {
    private final HashMap<PrimaryKey, Object> persistentHashMap;

    private static PersistanceContext persistanceContext = null;

    static class PrimaryKey{
        private final Class clasz;
        private final int id;

        public Class getClasz() {
            return clasz;
        }

        public int getId() {
            return id;
        }

        public PrimaryKey(Class clasz, int persistanceId){
            this.clasz = clasz;
            this.id = persistanceId;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof PrimaryKey){
                PrimaryKey pkOther = (PrimaryKey) obj;
                if(pkOther.getClasz().equals(this.clasz) && pkOther.getId() == this.id) return true;
                else return false;
            }
            else return false;
        }
    }

    protected PersistanceContext() {
        persistentHashMap = new HashMap<>();
    }

    public static PersistanceContext getInstance() {
        if(persistanceContext == null){
            persistanceContext = new PersistanceContext();
        }
        return persistanceContext;
    }

    public int calculatePersistanceId(Object identifier){
        return Objects.hashCode(identifier);
    }

    public <T> T getFromPersistanceContext(Class<T> clasz, Object identifier){
        System.out.println("get from persistance context");
        int persistanceId = calculatePersistanceId(identifier);
        Object objFound = persistentHashMap.get(new PrimaryKey(clasz, persistanceId));
        return clasz.cast(objFound);
    }

    public <T> void putInPersistanceContext(T obj, Object identifier) {
        System.out.println("put in persistance context");
        int persistanceId = calculatePersistanceId(identifier);
        persistentHashMap.put(new PrimaryKey(obj.getClass(),persistanceId),obj);
    }
}
