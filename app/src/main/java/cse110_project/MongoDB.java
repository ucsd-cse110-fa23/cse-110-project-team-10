package cse110_project;

interface MongoDB {
    void CreateAccount(String username, String password);

    void Delete(String username);

    void Update(String username);

    void Read(String username);
}
