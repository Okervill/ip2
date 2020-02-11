package ip2;

import org.mindrot.jbcrypt.BCrypt;


public class Hash {

    private final int logRounds = 11;
    
    public Hash(){
    }

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
