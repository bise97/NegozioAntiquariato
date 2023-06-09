package database;

import java.util.HashMap;

public class PersistanceContext {
    private final HashMap<PrimaryKey, Persistent> persistentHashMap;

    private static PersistanceContext persistanceContext = null;

    class PrimaryKey{
        private Class<? extends Persistent> clasz;
        private long id;

        public PrimaryKey(Persistent obj){
            this.clasz = obj.getClass();
            this.id = obj.getPersistanceId();
        }

        public PrimaryKey(Class<? extends Persistent> clasz, long persistanceId){
            this.clasz = clasz;
            this.id = persistanceId;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof PrimaryKey){
                PrimaryKey pkOther = (PrimaryKey) obj;
                if(pkOther.clasz.equals(this.clasz) && pkOther.id == this.id) return true;
                else return false;
            }
            else return false;
        }
    }

    protected PersistanceContext() {
        persistentHashMap = new HashMap<>();
    }

    public PersistanceContext getInstance() {
        if(persistanceContext == null){
            persistanceContext = new PersistanceContext();
        }
        return persistanceContext;
    }

    public Persistent getFromPersistanceContext(Class<? extends Persistent> clasz, long persistanceId){
        return persistentHashMap.get(new PrimaryKey(clasz,persistanceId));
    }

    public void putInPersistanceContext(Persistent obj){
        persistentHashMap.put(new PrimaryKey(obj),obj);
    }
}
