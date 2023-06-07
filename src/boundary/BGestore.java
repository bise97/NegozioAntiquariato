package boundary;

public class BGestore {

    private static BGestore bG = null;

    public static BGestore getInstance(){
        if(bG == null){
            bG = new BGestore();
        }
        return bG;
    }

    public boolean login(String username, String password) {
        BClienteRegistrato bCR;
        boolean f = false;

        if (username.equals("bise") && password.equals("pass1")) {
            bCR = BClienteRegistrato.getInstance();
            bCR.setUsername(username);
            f = true;
        }
        return f;
    }

}
