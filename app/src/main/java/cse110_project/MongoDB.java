package cse110_project;

interface MongoDB {
    void CreateAccount(String username, String password);

    void Delete(String username);

    void Update(String username);

    void LookUpAccount(String username);
}
