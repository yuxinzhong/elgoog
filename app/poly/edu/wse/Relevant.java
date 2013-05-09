package poly.edu.wse;

import java.io.Serializable;

public class Relevant implements Serializable{
    private static final long serialVersionUID = -1466479389299512377L;
    public String query;
    public String responseUUID;
    public boolean[] relevant;
}
