package cropulse.io.serviceimpl;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class UserStatusService {
	
    private final Set<String> loggedOutUsers = new HashSet<>();

  
    public void logUserOut(String userId) {
        loggedOutUsers.add(userId);
    }
    public void logUserIn(String userId) {
        loggedOutUsers.remove(userId); 
    }

    public boolean isUserLoggedIn(String userId) {
        return !loggedOutUsers.contains(userId);
    }
}
