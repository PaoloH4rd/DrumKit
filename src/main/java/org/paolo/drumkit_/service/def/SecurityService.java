package org.paolo.drumkit_.service.def;



public interface SecurityService {
    public void isAdmin (String email);
    public void isAdmin (long id);
    public void isCliente(String email);
    public void isCliente (long id);
    public void isSuperAdmin(String email);
    public void isSuperAdmin (long id);
}
