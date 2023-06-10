package database;

import java.util.HashMap;
import java.util.Objects;

public class PersistanceContext {
    private final HashMap<PrimaryKey, Object> persistentHashMap;

    private static PersistanceContext persistanceContext = null;

    private static final class PrimaryKey{
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
        public int hashCode() {
            int hash = 3;
            hash = 71 * hash + Objects.hashCode(this.clasz);
            hash = 71 * hash + Objects.hashCode(this.id);
            return hash;
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
        int persistanceId = calculatePersistanceId(identifier);
        PrimaryKey pk = new PrimaryKey( clasz, persistanceId);
        return clasz.cast(persistentHashMap.get(pk));
    }

    public <T> void putInPersistanceContext(T obj, Object identifier) {
        int persistanceId = calculatePersistanceId(identifier);
        PrimaryKey pk = new PrimaryKey(obj.getClass(),persistanceId);
        persistentHashMap.put(pk,obj);
    }

    public <T> void removeFromPersistanceContext(Class<T> clasz, Object identifier){
        int persistanceId = calculatePersistanceId(identifier);
        PrimaryKey pk = new PrimaryKey(clasz,persistanceId);
        persistentHashMap.remove(pk);
    }
}
